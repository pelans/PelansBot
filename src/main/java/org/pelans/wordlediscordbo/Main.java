package org.pelans.wordlediscordbo;

import org.pelans.wordlediscordbo.Discord.Discord;

import java.io.InputStream;
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

            //Load discord bot
            Discord.configureDiscordBot(prop.getProperty("DiscordBotToken"));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }


}