package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.pelans.wordle.Database.Entities.UserStats;
import org.pelans.wordle.util.Emojis;
import org.pelans.wordle.util.Language;

public class EmbedStats {

    private static EmbedBuilder base(UserStats userStats, Language lan) {
        EmbedBuilder eb = new EmbedBuilder();
        User user = Discord.getJda().retrieveUserById(userStats.getMemberId().getUserId()).complete();
        if(user != null) {
            eb.setThumbnail(user.getAvatarUrl());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(":bust_in_silhouette: <@%s>\n",userStats.getMemberId().getUserId()));
        sb.append(String.format(":books: **%s:** `%d`\n", lan.get("Games Played"), userStats.gamesPlayed()));
        sb.append(String.format(":white_check_mark: **%s:** `%d`\n", lan.get("Words Solved"), userStats.gamesSolved()));
        sb.append(String.format(":fire: **%s:** `%d`\n", lan.get("Current Streak"), userStats.getCurrentStreak()));
        sb.append(String.format(":trophy: **%s:** `%d`\n", lan.get("Max Streak"), userStats.getMaxStreak()));
        sb.append("\n");
        sb.append(String.format("__**%s:**__ `%.2f` **%s**\n",lan.get("Guess distribution"), userStats.avgGuess(), lan.get("average")));
        sb.append(String.format("**1** %s \t`%d`\n", Emojis.getBlueProggressionBar(userStats.getCorrect1(),userStats.mostFrequent()), userStats.getCorrect1()));
        sb.append(String.format("**2** %s `%d`\n", Emojis.getBlueProggressionBar(userStats.getCorrect2(),userStats.mostFrequent()), userStats.getCorrect2()));
        sb.append(String.format("**3** %s `%d`\n", Emojis.getBlueProggressionBar(userStats.getCorrect3(),userStats.mostFrequent()), userStats.getCorrect3()));
        sb.append(String.format("**4** %s `%d`\n", Emojis.getBlueProggressionBar(userStats.getCorrect4(),userStats.mostFrequent()), userStats.getCorrect4()));
        sb.append(String.format("**5** %s `%d`\n", Emojis.getBlueProggressionBar(userStats.getCorrect5(),userStats.mostFrequent()), userStats.getCorrect5()));
        sb.append(String.format("**6** %s `%d`\n", Emojis.getBlueProggressionBar(userStats.getCorrect6(),userStats.mostFrequent()), userStats.getCorrect6()));
        sb.append(String.format("**F** %s `%d`\n", Emojis.getBlueProggressionBar(userStats.getFailed(),userStats.mostFrequent()), userStats.getFailed()));
        eb.setDescription(sb);
        return eb;
    }

    public static MessageEmbed stats(UserStats userStats, Language lan){
        EmbedBuilder eb = base(userStats, lan);
        return eb.build();
    }
}
