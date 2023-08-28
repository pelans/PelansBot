package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("wordle")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("stats")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        } else if (event.getName().equals("announce_results")) {
            event.reply("Work in progress!").setEphemeral(true).queue();
        }
    }
}
