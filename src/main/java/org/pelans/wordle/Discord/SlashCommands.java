package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.pelans.wordle.Database.Services.ServerWordService;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("wordle")) {
            event.reply(String.format("The word is %s!", ServerWordService.getServerWord(event.getGuild().getId()).getWord())).setEphemeral(true).queue();
            //event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("stats")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("announce_results")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        }
    }
}
