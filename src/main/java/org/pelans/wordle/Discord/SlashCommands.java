package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
import org.pelans.wordle.util.Wordle;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String guildId = event.getGuild() != null ? event.getGuild().getId() : null;
        ServerConfig serverConfig = ServerConfigService.getServerConfig(guildId);


        if(event.getName().equals("wordle")) {
            OptionMapping optionMapping = event.getOption("word");
            String word = optionMapping != null ? optionMapping.getAsString() : null;

            UserWord userWord = UserWordService.getUserWord(new MemberId(guildId, event.getUser().getId()));

            //If the user wants to know the actual results
            if(word == null) {
                //Show actual results
                if(userWord.hashWon() || userWord.isComplete())
                    event.replyEmbeds(EmbedWordle.wordle(userWord, false)).setEphemeral(true)
                            .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), "View meaning")).queue();
                else
                    event.replyEmbeds(EmbedWordle.wordle(userWord, false)).setEphemeral(true).queue();
            } else {
                String additionalMessage = "";
                //Verify if the user has ended the wordle
                if(userWord.isComplete() || userWord.hashWon()) {
                    //Show actual results + Error: You've already played today's wordle
                    additionalMessage = ":x: **Error: __You have already played today's wordle__**";
                }
                //Verify if the word has the exact amount of characters
                else if(word.length() != userWord.getCorrectWord().length() ) {
                    //Show actual results + Error: You have to enter a word of X characters
                    additionalMessage = String.format(":x: **Error: __You have to enter a word of %s characters__**",userWord.getCorrectWord().length());
                }
                //Verify if the word is well wrote
                else if(!Wordle.exists(word)) {
                    //Show actual results + Error: You have to enter a valid word
                    additionalMessage = ":x: **Error: __You have to enter a valid word__**";
                }
                //If checks all the requirements
                else {
                    userWord.addWord(word);
                    UserWordService.putUserWord(userWord);
                    //Show actual results + A message if he has won or loss
                    if(userWord.hashWon())
                        additionalMessage = ":trophy: **__Congratulations you have won!__**";
                    else if (userWord.isComplete()) {
                        additionalMessage = ":skull_crossbones: **__You have lost!__**";
                    }
                    else {
                        additionalMessage = ":x: **__That word is not correct, try again!__**";
                    }

                    if(userWord.hashWon() || userWord.isComplete()) {
                        ServerConfig serverConfigService = ServerConfigService.getServerConfig(guildId);
                        String channelId = serverConfigService.getAnnounceChannelId();
                        TextChannel textChannel = channelId != null ? event.getGuild().getTextChannelById(channelId) : null;
                        if(textChannel != null) {
                            if(serverConfig.isWordRandomForEachUser())
                                textChannel.sendMessageEmbeds(EmbedWordle.shareWordle(userWord, false))
                                        .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), "View meaning")).queue();
                            else
                                textChannel.sendMessageEmbeds(EmbedWordle.shareWordle(userWord, true)).queue();
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
                    event.replyEmbeds(EmbedWordle.wordle(userWord, false, additionalMessage)).setEphemeral(true)
                            .addActionRow(Button.link("https://dle.rae.es/" + userWord.getCorrectWord(), "View meaning")).queue();
                else
                    event.replyEmbeds(EmbedWordle.wordle(userWord, false, additionalMessage)).setEphemeral(true).queue();
            }
        } else if (event.getName().equals("stats")) {
            UserStats userStats = UserStatsService.getUserStats(new MemberId(guildId, event.getUser().getId()));
            event.replyEmbeds(EmbedStats.stats(userStats)).setEphemeral(true).queue();
        } else if (event.getName().equals("announce_results")) {
            OptionMapping optionMapping = event.getOption("channel");
            String channelId = optionMapping != null ?  optionMapping.getAsChannel().getId() : null;
            serverConfig.setAnnounceChannelId(channelId);
            ServerConfigService.putServerConfig(serverConfig);
            String message = channelId != null ? String.format("Channel updated to <#%s>",channelId) : "Channel announce disabled.";
            event.reply(message).setEphemeral(true).queue();
        } else if (event.getName().equals("mode")) {
            OptionMapping optionMapping = event.getOption("mode");
            String mode = optionMapping != null ?  optionMapping.getAsString() : "SAME";
            if(mode.equals("SAME")) {
                serverConfig.setWordRandomForEachUser(false);
                event.reply("Now all users will have the same word!").setEphemeral(true).queue();
            } else {
                serverConfig.setWordRandomForEachUser(true);
                event.reply("Now all users will have different words!").setEphemeral(true).queue();
            }
            ServerConfigService.putServerConfig(serverConfig);
        } else if (event.getName().equals("suggestion")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("help")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        }

    }
}
