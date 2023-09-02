package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Database.Entities.UserWord;
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
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord())).setEphemeral(true).queue();
            }
            //Verify if the user has ended the wordle
            else if(userWord.isComplete() || userWord.hashWon(serverWord.getWord())) {
                //Show actual results + Error: You've already played today's wordle
                String additionalMessage = "Error: You have already played today's wordle";
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord(), additionalMessage)).setEphemeral(true).queue();
            }
            //Verify if the word has the exact amount of characters
            else if(word.length() != serverWord.getWord().length() ) {
                //Show actual results + Error: You have to enter a word of X characters
                String additionalMessage = String.format("Error: You have to enter a word of %s characters",serverWord.getWord());
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord(), additionalMessage)).setEphemeral(true).queue();
            }
            //Verify if the word is well wrote
            else if(!Wordle.exists(word)) {
                //Show actual results + Error: You have to enter a valid word
                String additionalMessage = "Error: You have to enter a valid word";
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord(), additionalMessage)).setEphemeral(true).queue();
            }
            //If checks all the requirements
            else {
                userWord.addWord(word);
                UserWordService.putUserWord(userWord);
                //Show actual results + A message if he has won or loss
                String additionalMessage = "";
                if(userWord.hashWon(serverWord.getWord()))
                    additionalMessage += "Congratulations you have won!";
                else if (userWord.isComplete()) {
                    additionalMessage += "You have lost!";
                }
                else {
                    additionalMessage += "That word is not correct, try again!";
                }
                event.replyEmbeds(Embeds.wordle(userWord, serverWord.getWord(), additionalMessage)).setEphemeral(true).queue();

            }

            //event.reply(String.format("The word is %s!", serverWord.getWord())).setEphemeral(true).queue();
            //event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("stats")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("announce_results")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        }
    }
}
