package org.pelans.wordle;

import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.Database.Services.ServerWordService;
import org.pelans.wordle.Database.Services.UserWordService;
import org.pelans.wordle.Discord.Discord;
import org.pelans.wordle.Taks.DailyReset;
import org.pelans.wordle.Taks.ResetDailyWordleTask;
import org.pelans.wordle.util.HibernateUtil;
import org.pelans.wordle.util.Wordle;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            //Load config.properties Must be configured before executing this program.
            Properties prop =new Properties();
            InputStream ip = Main.class.getResourceAsStream("/config.properties");
            prop.load(ip);

            //Load wordle words
            Wordle.init();

            //Load database
            HibernateUtil.init();

            //Load daily reset
            DailyReset.init();

            //Load discord bot
            Discord.configureDiscordBot(prop.getProperty("DiscordBotToken"));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }


}