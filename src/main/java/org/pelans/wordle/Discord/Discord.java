package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Discord {

    private static JDA jda;

    public static void configureDiscordBot(String token) {
        try {
            jda = JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES)
                    .enableCache(CacheFlag.VOICE_STATE).setMemberCachePolicy(MemberCachePolicy.VOICE)
                    .build();
            jda.awaitReady();

            //Events
            jda.addEventListener(new SlashCommands());

            //Update commands
            jda.updateCommands().queue();

            jda.upsertCommand("wordle", "Play today's wordle.")
                    .addOptions(new OptionData(OptionType.STRING, "word", "Write the today's wordle word", false))
                    .setGuildOnly(true)
                    .queue();

            jda.upsertCommand("stats", "Show your current statics in this server.")
                    .setGuildOnly(true)
                    .queue();

            jda.upsertCommand("announce_results", "Select a channel to announce the result of each user.")
                    .addOptions(new OptionData(OptionType.CHANNEL, "channel", "Select the channel to announce the results", false)
                            .setChannelTypes(ChannelType.TEXT))
                    .setGuildOnly(true)
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .queue();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JDA getJda() {
        return jda;
    }
}
