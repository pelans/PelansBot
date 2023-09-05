package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
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


        if(event.getName().equals("wordle")) {
            OptionMapping optionMapping = event.getOption("word");
            String word = optionMapping != null ? optionMapping.getAsString() : null;

            UserWord userWord = UserWordService.getUserWord(new MemberId(guildId, event.getUser().getId()));

            //If the user wants to know the actual results
            if(word == null) {
                //Show actual results
                if(userWord.hashWon() || userWord.isComplete())
                    event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false)).setEphemeral(true)
                            .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), "View meaning"),
                                    Button.secondary("wordle_playagain","Play again"))
                            .queue();
                else
                    event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false)).setEphemeral(true).queue();
            } else {
                String additionalMessage = "";
                //Verify if the user has ended the wordle
                if(userWord.isComplete() || userWord.hashWon()) {
                    //Show actual results + Error: You've already played today's wordle
                    additionalMessage = String.format(":x: **%s: __%s__**",
                            lan.get("Error"),lan.get("You have already played today's wordle"));
                }
                //Verify if the word has the exact amount of characters
                else if(word.length() != userWord.getCorrectWord().length() ) {
                    //Show actual results + Error: You have to enter a word of X characters
                    additionalMessage = String.format(":x: **%s: __%s %s %s__**",
                            lan.get("Error"), lan.get("You have to enter a word of"),
                            userWord.getCorrectWord().length(), lan.get("characters"));
                }
                //Verify if the word is well wrote
                else if(!Wordle.exists(word)) {
                    //Show actual results + Error: You have to enter a valid word
                    additionalMessage = String.format(":x: **%s: __%s__**",
                            lan.get("Error"), lan.get("You have to enter a valid word"));
                }
                //If checks all the requirements
                else {
                    userWord.addWord(word);
                    UserWordService.putUserWord(userWord);
                    //Show actual results + A message if he has won or loss
                    if(userWord.hashWon())
                        additionalMessage = String.format(":trophy: **__%s__**", lan.get("Congratulations you have won!"));
                    else if (userWord.isComplete()) {
                        additionalMessage = String.format(":skull_crossbones: **__%s__**", lan.get("You have lost!"));
                    }
                    else {
                        additionalMessage = String.format(":x: **__%s__**", lan.get("That word is not correct, try again!"));
                    }

                    if(userWord.hashWon() || userWord.isComplete()) {
                        ServerConfig serverConfigService = ServerConfigService.getServerConfig(guildId);
                        String channelId = serverConfigService.getAnnounceChannelId();
                        TextChannel textChannel = channelId != null ? event.getGuild().getTextChannelById(channelId) : null;
                        if(textChannel != null) {
                            if(serverConfig.isWordRandomForEachUser())
                                textChannel.sendMessageEmbeds(EmbedWordle.shareWordle(userWord, lan, false))
                                        .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), "View meaning")).queue();
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
                if(userWord.hashWon() || userWord.isComplete())
                    event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false, additionalMessage)).setEphemeral(true)
                            .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), lan.get("View meaning")),
                                    Button.secondary("wordle_playagain",lan.get("Play again")))
                            .queue();
                else
                    event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false, additionalMessage)).setEphemeral(true).queue();
            }
        } else if (event.getName().equals("stats")) {
            UserStats userStats = UserStatsService.getUserStats(new MemberId(guildId, event.getUser().getId()));
            event.replyEmbeds(EmbedStats.stats(userStats, lan)).setEphemeral(true).queue();
        } else if (event.getName().equals("announce_results")) {
            OptionMapping optionMapping = event.getOption("channel");
            String channelId = optionMapping != null ?  optionMapping.getAsChannel().getId() : null;
            serverConfig.setAnnounceChannelId(channelId);
            ServerConfigService.putServerConfig(serverConfig);
            String message = channelId != null ? String.format("%s <#%s>",lan.get("Channel updated to") ,channelId)
                    : lan.get("Channel announce disabled");
            event.reply(message).setEphemeral(true).queue();
        } else if (event.getName().equals("mode")) {
            OptionMapping optionMapping = event.getOption("mode");
            String mode = optionMapping != null ?  optionMapping.getAsString() : "SAME";
            if(mode.equals("SAME")) {
                serverConfig.setWordRandomForEachUser(false);
                event.reply(lan.get("Now all users will have the same word!")).setEphemeral(true).queue();
            } else {
                serverConfig.setWordRandomForEachUser(true);
                event.reply(lan.get("Now all users will have different words!")).setEphemeral(true).queue();
            }
            ServerConfigService.putServerConfig(serverConfig);
        } else if (event.getName().equals("suggestion")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("help")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        }

    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (event.getComponentId().equals("wordle_playagain")) {
            String guildId = event.getGuild() != null ? event.getGuild().getId() : null;
            ServerConfig serverConfig = ServerConfigService.getServerConfig(guildId);
            Language lan = new Language(serverConfig.getLanguage());
            UserWord userWord = UserWordService.getUserWord(new MemberId(guildId, event.getUser().getId()));
            if(!userWord.hashWon() && !userWord.isComplete()) {
                event.reply(lan.get("You need to end your wordle first!")).setEphemeral(true).queue();
                return;
            }
            userWord = new UserWord(userWord.getMemberId(), Wordle.getWord(), false);
            UserWordService.putUserWord(userWord);
            event.replyEmbeds(EmbedWordle.wordle(userWord, lan, false)).setEphemeral(true).queue();

        }
    }
}
