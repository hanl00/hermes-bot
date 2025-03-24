package com.hermesbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AddCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("add")) {
            String numbersString = event.getOption("numbers").getAsString();

            try {
                String[] numberArray = numbersString.split("\\s+");

                double sum = 0.0;
                for (String numStr : numberArray) {
                    sum += Double.parseDouble(numStr);
                }

                event.reply("🧮 The total is: **" + sum + "**").queue();

            } catch (NumberFormatException e) {
                event.reply("❌ Invalid input! Please enter valid numbers separated by spaces.").setEphemeral(true).queue();
            }
        }
    }
}