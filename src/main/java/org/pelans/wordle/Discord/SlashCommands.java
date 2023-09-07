package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.Database.Entities.UserStats;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.Database.Services.ServerConfigService;
import org.pelans.wordle.Database.Services.UserStatsService;
import org.pelans.wordle.Database.Services.UserWordService;
import org.pelans.wordle.util.Language;
import org.pelans.wordle.util.Wordle;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String guildId = event.getGuild() != null ? event.getGuild().getId() : null;
        ServerConfig serverConfig = ServerConfigService.getServerConfig(guildId);
        Language lan = new Language(serverConfig.getLanguage());

        switch (event.getFullCommandName()) {
            case "wordle" -> {
                OptionMapping optionMapping = event.getOption("word");
                String word = optionMapping != null ? optionMapping.getAsString() : null;

                UserWord userWord = UserWordService.getUserWord(new MemberId(guildId, event.getUser().getId()));

                //If the user wants to know the actual results
                if (word == null) {
                    //Show actual results
                    ReplyCallbackAction replyEmbeds = event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false)).setEphemeral(true);
                    if (userWord.hashWon() || userWord.isComplete()) {
                        replyEmbeds.addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), lan.get("View meaning")),
                                Button.secondary("wordle_playagain", "Play again"));
                        if(serverConfig.isShareWordle())
                            replyEmbeds.addActionRow(Button.secondary("share_wordle", lan.get("Share"))
                                    .withEmoji(Emoji.fromFormatted("\uD83D\uDCE3")));
                    }
                    replyEmbeds.queue();
                } else {
                    String additionalMessage = "";
                    //Verify if the user has ended the wordle
                    if (userWord.isComplete() || userWord.hashWon()) {
                        //Show actual results + Error: You've already played today's wordle
                        additionalMessage = String.format(":x: **%s: __%s__**",
                                lan.get("Error"), lan.get("You have already played today's wordle"));
                    }
                    //Verify if the word has the exact amount of characters
                    else if (word.length() != userWord.getCorrectWord().length()) {
                        //Show actual results + Error: You have to enter a word of X characters
                        additionalMessage = String.format(":x: **%s: __%s %s %s__**",
                                lan.get("Error"), lan.get("You have to enter a word of"),
                                userWord.getCorrectWord().length(), lan.get("characters"));
                    }
                    //Verify if the word is well wrote
                    else if (!Wordle.exists(word)) {
                        //Show actual results + Error: You have to enter a valid word
                        additionalMessage = String.format(":x: **%s: __%s__**",
                                lan.get("Error"), lan.get("You have to enter a valid word"));
                    }
                    //If checks all the requirements
                    else {
                        userWord.addWord(word);
                        UserWordService.putUserWord(userWord);
                        //Show actual results + A message if he has won or loss
                        if (userWord.hashWon())
                            additionalMessage = String.format(":trophy: **__%s__**", lan.get("Congratulations you have won!"));
                        else if (userWord.isComplete()) {
                            additionalMessage = String.format(":skull_crossbones: **__%s__**", lan.get("You have lost!"));
                        } else {
                            additionalMessage = String.format(":x: **__%s__**", lan.get("That word is not correct, try again!"));
                        }

                        if (userWord.hashWon() || userWord.isComplete()) {
                            ServerConfig serverConfigService = ServerConfigService.getServerConfig(guildId);
                            String channelId = serverConfigService.getAnnounceChannelId();
                            TextChannel textChannel = channelId != null ? event.getGuild().getTextChannelById(channelId) : null;
                            if (textChannel != null) {
                                if (serverConfig.isWordRandomForEachUser() || !userWord.isFirstGame())
                                    textChannel.sendMessageEmbeds(EmbedWordle.shareWordle(userWord, lan, false))
                                            .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), lan.get("View meaning"))).queue();
                                else
                                    textChannel.sendMessageEmbeds(EmbedWordle.shareWordle(userWord, lan, true)).queue();
                            }
                            if (!userWord.isSaved() && userWord.isFirstGame()) { //This means is not the first wordle of the day
                                UserStats userStats = UserStatsService.getUserStats(userWord.getMemberId());
                                userStats.add(userWord);
                                UserStatsService.putUserStats(userStats);
                                userWord.setSaved(true);
                                UserWordService.putUserWord(userWord);
                            }
                        }
                    }
                    ReplyCallbackAction replyEmbeds = event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false, additionalMessage)).setEphemeral(true);
                    if (userWord.hashWon() || userWord.isComplete()) {
                        replyEmbeds.addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), lan.get("View meaning")),
                                Button.secondary("wordle_playagain", "Play again"));
                        if(serverConfig.isShareWordle())
                            replyEmbeds.addActionRow(Button.secondary("share_wordle", lan.get("Share"))
                                    .withEmoji(Emoji.fromFormatted("\uD83D\uDCE3")));
                    }
                    replyEmbeds.queue();

                }
            }
            case "stats" -> {
                UserStats userStats = UserStatsService.getUserStats(new MemberId(guildId, event.getUser().getId()));
                ReplyCallbackAction replyEmbeds = event.replyEmbeds(EmbedStats.stats(userStats, lan)).setEphemeral(true);
                if (serverConfig.isShareStatus())
                    replyEmbeds.addActionRow(Button.secondary("share_stats", lan.get("Share"))
                            .withEmoji(Emoji.fromFormatted("\uD83D\uDCE3")));
                replyEmbeds.queue();
            }
            case "config announce_results" -> {
                OptionMapping optionMapping = event.getOption("channel");
                String channelId = optionMapping != null ? optionMapping.getAsChannel().getId() : null;
                serverConfig.setAnnounceChannelId(channelId);
                ServerConfigService.putServerConfig(serverConfig);
                String message = channelId != null ? String.format("%s <#%s>", lan.get("Channel updated to"), channelId)
                        : lan.get("Channel announce disabled");
                event.reply(message).setEphemeral(true).queue();
            }
            case "config mode" -> {
                OptionMapping optionMapping = event.getOption("mode");
                String mode = optionMapping != null ? optionMapping.getAsString() : "SAME";
                if (mode.equals("SAME")) {
                    serverConfig.setWordRandomForEachUser(false);
                    event.reply(lan.get("Now all users will have the same word!")).setEphemeral(true).queue();
                } else {
                    serverConfig.setWordRandomForEachUser(true);
                    event.reply(lan.get("Now all users will have different words!")).setEphemeral(true).queue();
                }
                ServerConfigService.putServerConfig(serverConfig);
            }
            case "suggestion" -> {
                OptionMapping optionMapping = event.getOption("info");
                String info = optionMapping != null ? optionMapping.getAsString() : "";
                String message = String.format("<@%s> ha sugerido: %s", event.getUser().getId(), info);
                event.getJDA().getTextChannelById("1149286853703389294").sendMessage(message).queue();
                event.reply(lan.get("Suggestion sent!")).setEphemeral(true).queue();
            }
            case "bug" -> {
                OptionMapping optionMapping = event.getOption("info");
                String info = optionMapping != null ? optionMapping.getAsString() : "";
                String message = String.format("<@%s> ha reportado: %s", event.getUser().getId(), info);
                event.getJDA().getTextChannelById("1149286822741024889").sendMessage(message).queue();
                event.reply(lan.get("Bug reported!")).setEphemeral(true).queue();
            }
            case "help" -> {
                event.reply("Work in progress!").setEphemeral(true).queue();
            }
            case "config sharewordle" -> {
                OptionMapping optionMapping = event.getOption("share");
                String share = optionMapping != null ? optionMapping.getAsString() : "ALLOW";
                serverConfig.setShareWordle(share.equals("ALLOW"));
                ServerConfigService.putServerConfig(serverConfig);
                if(share.equals("ALLOW"))
                    event.reply(lan.get("Now you can share the result of your wordle in any channel")).setEphemeral(true).queue();
                else
                    event.reply(lan.get("Now you can not share the result of your wordle")).setEphemeral(true).queue();
            }
            case "config sharestatus" -> {
                OptionMapping optionMapping = event.getOption("share");
                String share = optionMapping != null ? optionMapping.getAsString() : "ALLOW";
                serverConfig.setShareStatus(share.equals("ALLOW"));
                ServerConfigService.putServerConfig(serverConfig);
                if(share.equals("ALLOW"))
                    event.reply(lan.get("Now you can share your status in any channel")).setEphemeral(true).queue();
                else
                    event.reply(lan.get("Now you can not share your status")).setEphemeral(true).queue();
            }
            case "config length" -> {
                event.reply("Work in progress!").setEphemeral(true).queue();
            }
            case "config language" -> {
                OptionMapping optionMapping = event.getOption("language");
                String language = optionMapping != null ? optionMapping.getAsString() : "es-ES";
                serverConfig.setLanguage(language);
                ServerConfigService.putServerConfig(serverConfig);
                lan = new Language(language);
                event.reply(lan.get("Language changed!")).setEphemeral(true).queue();
            }
        }

    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String guildId = event.getGuild() != null ? event.getGuild().getId() : null;
        ServerConfig serverConfig = ServerConfigService.getServerConfig(guildId);
        Language lan = new Language(serverConfig.getLanguage());

        if (event.getComponentId().equals("wordle_playagain")) {
            UserWord userWord = UserWordService.getUserWord(new MemberId(guildId, event.getUser().getId()));
            if(!userWord.hashWon() && !userWord.isComplete()) {
                event.reply(lan.get("You need to end your wordle first!")).setEphemeral(true).queue();
                return;
            }
            userWord = new UserWord(userWord.getMemberId(), Wordle.getWord(), false);
            UserWordService.putUserWord(userWord);
            event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false)).setEphemeral(true).queue();
        }
        else if(event.getComponentId().equals("share_wordle")) {
            if(!serverConfig.isShareWordle()) {
                event.reply(lan.get("The share button is disabled on this server!")).setEphemeral(true).queue();
                return;
            }
            UserWord userWord = UserWordService.getUserWord(new MemberId(guildId, event.getUser().getId()));
            if(!userWord.hashWon() && !userWord.isComplete()) {
                event.reply(lan.get("You need to end your wordle first!")).setEphemeral(true).queue();
                return;
            }
            ReplyCallbackAction messageEmbeds = event.replyEmbeds(EmbedWordle.shareWordle(userWord, lan, false));
            if (userWord.hashWon() || userWord.isComplete()) {
                messageEmbeds.addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), lan.get("View meaning")));
            }
                messageEmbeds.queue();
        }
        else if(event.getComponentId().equals("share_stats")) {
            if(!serverConfig.isShareStatus()) {
                event.reply(lan.get("The share button is disabled on this server!")).setEphemeral(true).queue();
                return;
            }
            UserStats userStats = UserStatsService.getUserStats(new MemberId(guildId, event.getUser().getId()));
            ReplyCallbackAction replyEmbeds = event.replyEmbeds(EmbedStats.stats(userStats, lan));
            replyEmbeds.queue();
        }
    }
}
