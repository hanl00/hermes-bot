package com.hermesbot;

import com.hermesbot.config.Config;
import com.hermesbot.listeners.MyListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        String TOKEN = Config.getDiscordToken();

        JDABuilder jdaBuilder =  JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MyListener());

        jdaBuilder.build();
    }
}