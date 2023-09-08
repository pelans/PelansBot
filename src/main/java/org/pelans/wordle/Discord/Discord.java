package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Discord {

    private static JDA jda;

    public static void configureDiscordBot(String token) {
        try {
            jda = JDABuilder.createLight(token)
                    .build();
            jda.awaitReady();

            //Events
            jda.addEventListener(new SlashCommands());

            //Status
            jda.getPresence().setActivity(Activity.playing("/help /wordle**"));

            //Update commands
            //jda.updateCommands().queue();
            jda.awaitReady().upsertCommand("wordle", "Play today's wordle.")
                    .addOptions(new OptionData(OptionType.STRING, "word", "Write the today's wordle word", false))
                    .setGuildOnly(true)
                    .queue();

            jda.awaitReady().upsertCommand("stats", "Show your current statics in this server.")
                    .addOptions(new OptionData(OptionType.USER, "user", "User stats", false))
                    .setGuildOnly(true)
                    .queue();

            jda.awaitReady().upsertCommand("help", "Show the information about this bot.")
                    .queue();

            jda.awaitReady().upsertCommand("bug", "Report a bug.")
                    .addOptions(new OptionData(OptionType.STRING, "info", "Write the bug", true))
                    .queue();

            jda.awaitReady().upsertCommand("suggest", "Suggest an improvement.")
                    .addOptions(new OptionData(OptionType.STRING, "info", "Write the improvement", true))
                    .queue();

            jda.awaitReady().upsertCommand("config","configure the bot")
                    .addSubcommands(new SubcommandData("mode","Select if the DAILY WORDLE is random to everyone or the same")
                            .addOptions(new OptionData(OptionType.STRING, "mode", "Select if the word of the day is random to every user or the same",
                                    true).addChoice("RANDOM", "RANDOM").addChoice("SAME", "SAME")))
                    .addSubcommands(new SubcommandData("autoshare_dailywordle","Select a channel to announce the result of each user")
                            .addOptions(new OptionData(OptionType.CHANNEL, "channel", "Select the channel to announce the result of DAILY WORDLE", false)
                                    .setChannelTypes(ChannelType.TEXT)))
                    .addSubcommands(new SubcommandData("autoshare_practicewordle","Select a channel to announce the result of each user")
                            .addOptions(new OptionData(OptionType.CHANNEL, "channel", "Select the channel to announce the results of PRACTICE WORDLE", false)
                                    .setChannelTypes(ChannelType.TEXT)))
                    .addSubcommands(new SubcommandData("language","Select the language of the WORDLE (This will reset unfinished wordle)")
                            .addOptions(new OptionData(OptionType.STRING, "language", "Select the language of the WORDLE",
                                    true).addChoice("Español", "es-ES").addChoice("English", "en-US")))
                    .addSubcommands(new SubcommandData("sharewordle","Allow to share WORDDLE results to any channel")
                            .addOptions(new OptionData(OptionType.STRING, "share", "Allow to share WORDDLE results to any channel",
                                    true).addChoice("ALLOW", "ALLOW").addChoice("DENY", "DENY")))
                    .addSubcommands(new SubcommandData("sharestatus","Allow to share STATUS to any channel")
                            .addOptions(new OptionData(OptionType.STRING, "share", "Allow to share STATUS results to any channel",
                                    true).addChoice("ALLOW", "ALLOW").addChoice("DENY", "DENY")))
                    .addSubcommands(new SubcommandData("length","Allow to change the DAILY WORDLE length (Leave empty to be unlimited)")
                            .addOptions(new OptionData(OptionType.INTEGER, "min", "Write the min length of DAILY WORDLE (min 4)", false))
                            .addOptions(new OptionData(OptionType.INTEGER, "max", "Write the max length of DAILY WORDLE (max 15)", false)))
                    .setGuildOnly(true)
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .queue();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JDA getJda() {
        return jda;
    }
}
