package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.util.Language;

import java.util.Calendar;
import java.util.TimeZone;

public class EmbedHelp {

    private static EmbedBuilder base(ServerConfig serverConfig, Language lan) {
        EmbedBuilder eb = new EmbedBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("**__%s__**\n\n",lan.get("How to play")));
        sb.append(String.format("- %s: `/wordle` %s. %s: `/wordle \"%s\"` %s.\n",
                lan.get("To play, use the command"), lan.get("to see the number of letters"), lan.get("Then use"),
                lan.get("word"), lan.get("to guess the word")));

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        sb.append(String.format("- %s: <t:%s:R>.\n",lan.get("The wordle will restart in"), c.getTimeInMillis()/1000));
        sb.append(String.format("- %s. `/stats`\n\n",lan.get("Only the first daily wordle will count for statistics")));

        sb.append(String.format("**__%s__**\n\n",lan.get("Sever Settings")));
        String aux = serverConfig.isWordRandomForEachUser() ? lan.get("is different") : lan.get("is the same");
        sb.append(String.format("- %s `%s` %s. `/config mode`\n", lan.get("The daily wordle"), aux, lan.get("for the entire server")));

        Integer min = serverConfig.getMinWordLength();
        Integer max = serverConfig.getMaxWordLength();
        aux = "";
        if(min == null && max == null)
            aux = lan.get("The length of the wordle has been set without limits");
        else if (max == null)
            aux = String.format("%s `%s`", lan.get("The length of the wordle has been limited to a minimum length of"), min);
        else if (min == null)
            aux = String.format("%s `%s`", lan.get("The length of the wordle has been limited to a maximum length of"), max);
        else if (min.equals(max))
            aux = String.format("%s `%s`", lan.get("The length of the wordle has been limited to"), max);
        else
            aux = String.format("%s `%s` %s `%s`", lan.get("The length of the wordle has been limited between"), min, lan.get("and"), max);
        sb.append(String.format("- %s. `/config length`\n", aux));

        aux = serverConfig.getDailyAnnounceChannelId() != null ? "<#" + serverConfig.getDailyAnnounceChannelId() + ">" : "`" + lan.get("none") + "`";
        sb.append(String.format("- %s: %s. `/config autoshare_dailywordle`\n",lan.get("The results of the daily wordle are announced on"), aux));

        aux = serverConfig.getPracticeAnnounceChannelId() != null ? "<#" + serverConfig.getPracticeAnnounceChannelId() + ">" : "`" + lan.get("none") + "`";
        sb.append(String.format("- %s: %s. `/config autoshare_practicewordle`\n",lan.get("The results of the practice wordle are announced on"), aux));

        aux = serverConfig.isShareWordle() ? lan.get("You can share the result of your wordle in any channel") : lan.get("You can not share the result of your wordle");
        sb.append(String.format("- %s. `/config sharewordle`\n", aux));

        aux = serverConfig.isShareStatus() ? lan.get("You can share your status in any channel") : lan.get("You can not share your status");
        sb.append(String.format("- %s. `/config sharestatus`\n", aux));

        sb.append(String.format("- %s: `%s`. `/config language`",lan.get("The current language is"), serverConfig.getLanguage()));



        eb.setDescription(sb);
        return eb;
    }


    public static MessageEmbed stats(ServerConfig serverConfig, Language lan){
        EmbedBuilder eb = base(serverConfig, lan);
        return eb.build();
    }
}
