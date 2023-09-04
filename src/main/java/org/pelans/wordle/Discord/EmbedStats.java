package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.pelans.wordle.Database.Entities.UserStats;
import org.pelans.wordle.util.Emojis;

public class EmbedStats {

    private static EmbedBuilder base(UserStats userStats) {
        EmbedBuilder eb = new EmbedBuilder();
        User user = Discord.getJda().retrieveUserById(userStats.getMemberId().getUserId()).complete();
        if(user != null) {
            eb.setThumbnail(user.getAvatarUrl());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(":bust_in_silhouette: <@%s>\n",userStats.getMemberId().getUserId()));
        sb.append(String.format(":books: **Games Played:** `%d`\n", userStats.gamesPlayed()));
        sb.append(String.format(":white_check_mark: **Words Solved:** `%d`\n", userStats.gamesSolved()));
        sb.append(String.format(":fire: **Current Streak:** `%d`\n", userStats.getCurrentStreak()));
        sb.append(String.format(":trophy: **Max Streak:** `%d`\n", userStats.getMaxStreak()));
        sb.append("\n");
        sb.append(String.format("__**Guess distribution:**__ `%.2f` **average**\n",userStats.avgGuess()));
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

    public static MessageEmbed stats(UserStats userStats){
        EmbedBuilder eb = base(userStats);
        return eb.build();
    }
}
