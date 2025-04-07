package com.hermesbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.hermesbot.Main.logger;

public class SetReminderCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("setreminder")) {
            String timeStr = event.getOption("time").getAsString();
            String reminderMessage = event.getOption("message").getAsString();

            long delayInMillis = parseTimeToMillis(timeStr);

            if (delayInMillis > 0) {
                scheduleReminder(event, reminderMessage, delayInMillis);
                event.reply("⏰ Reminder set for **" + timeStr + "**!").queue();
            } else {
                event.reply("❌ Invalid time format. Use formats like `10m`, `1h`, `30s`.").setEphemeral(true).queue();
            }
        }
    }

    private long parseTimeToMillis(String timeStr) {
        try {
            char unit = timeStr.charAt(timeStr.length() - 1);
            int time = Integer.parseInt(timeStr.substring(0, timeStr.length() - 1));

            return switch (unit) {
                case 's' -> time * 1000L;
                case 'm' -> time * 60_000L;
                case 'h' -> time * 3_600_000L;
                case 'd' -> time * 86_400_000L;
                default -> -1;
            };
        } catch (Exception e) {
            return -1;
        }
    }

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private void scheduleReminder(SlashCommandInteractionEvent event, String message, long delayMillis) {
        scheduler.schedule(() -> {
            event.getChannel().sendMessage("🔔 <@" + event.getUser().getId() + "> Reminder: " + message).queue();
        }, delayMillis, TimeUnit.MILLISECONDS);
    }
}
