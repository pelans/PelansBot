package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.Database.Services.ServerConfigService;
import org.pelans.wordle.Database.Services.ServerWordService;
import org.pelans.wordle.Database.Services.UserWordService;
import org.pelans.wordle.Wordle;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("wordle")) {
            OptionMapping optionMapping = event.getOption("word");
            String word = optionMapping != null ? optionMapping.getAsString() : null;

            UserWord userWord = UserWordService.getUserWord(new MemberId(event.getGuild().getId(), event.getUser().getId()));
            ServerWord serverWord = ServerWordService.getServerWord(event.getGuild().getId());

            //If the user wants to know the actual results
            if(word == null) {
                //Show actual results
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord(),false)).setEphemeral(true).queue();
            } else {
                String additionalMessage = "";
                //Verify if the user has ended the wordle
                if(userWord.isComplete() || userWord.hashWon(serverWord.getWord())) {
                    //Show actual results + Error: You've already played today's wordle
                    additionalMessage = "Error: You have already played today's wordle";
                }
                //Verify if the word has the exact amount of characters
                else if(word.length() != serverWord.getWord().length() ) {
                    //Show actual results + Error: You have to enter a word of X characters
                    additionalMessage = String.format("Error: You have to enter a word of %s characters",serverWord.getWord().length());
                }
                //Verify if the word is well wrote
                else if(!Wordle.exists(word)) {
                    //Show actual results + Error: You have to enter a valid word
                    additionalMessage = "Error: You have to enter a valid word";
                }
                //If checks all the requirements
                else {
                    userWord.addWord(word);
                    UserWordService.putUserWord(userWord);
                    //Show actual results + A message if he has won or loss
                    if(userWord.hashWon(serverWord.getWord()))
                        additionalMessage = "Congratulations you have won!";
                    else if (userWord.isComplete()) {
                        additionalMessage = "You have lost!";
                    }
                    else {
                        additionalMessage = "That word is not correct, try again!";
                    }

                    if(userWord.hashWon(serverWord.getWord()) || userWord.isComplete()) {
                        ServerConfig serverConfigService = ServerConfigService.getServerConfig(event.getGuild().getId());
                        String channelId = serverConfigService.getAnnounceChannelId();
                        TextChannel textChannel = channelId != null ? event.getGuild().getTextChannelById(channelId) : null;
                        if(textChannel != null) {
                            textChannel.sendMessageEmbeds(Embeds.shareWordle(userWord, serverWord.getWord(), true)).queue();
                        }
                    }
                }
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord(), false, additionalMessage)).setEphemeral(true).queue();
            }
        } else if (event.getName().equals("stats")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("announce_results")) {
            OptionMapping optionMapping = event.getOption("channel");
            String channelId = optionMapping != null ?  optionMapping.getAsChannel().getId() : null;
            ServerConfig serverConfig = ServerConfigService.getServerConfig(event.getGuild().getId());
            serverConfig.setAnnounceChannelId(channelId);
            ServerConfigService.putServerConfig(serverConfig);
            String message = channelId != null ? String.format("Channel updated to <#%s>",channelId) : "Channel announce disabled.";
            event.reply(message).setEphemeral(true).queue();
        }
    }
}
