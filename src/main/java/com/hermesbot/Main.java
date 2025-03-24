package com.hermesbot;

import com.hermesbot.commands.AddCommand;
import com.hermesbot.config.Config;
import com.hermesbot.listeners.MyListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        String TOKEN = Config.getDiscordToken();

        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MyListener(), new AddCommand());

        jdaBuilder.setActivity(Activity.customStatus("Ready to help!"));

        jdaBuilder.build().updateCommands().addCommands(
                Commands.slash("add", "Adds numbers together")
                        .addOption(OptionType.STRING, "numbers", "Enter numbers separated by spaces", true)
        ).queue();
    }
}