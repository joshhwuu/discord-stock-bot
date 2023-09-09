package me.okk;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    public static void main(String[] args) throws LoginException {

        JDA bot = JDABuilder.createDefault("MTE1MDE4MjYxNjc3NjA2NTE4NQ.GxbMQg.uUn-V6nvwZTzo6saR-JH17brDsEcylDnk6f0lE")
                .setActivity(Activity.playing("VALORANT"))
                .build();
    }
}