package net.heyzeer0.mgchat.listeners.discord;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.profiles.discord.DiscordCommandSender;
import net.heyzeer0.mgchat.profiles.MagiMessageChannel;
import net.heyzeer0.mgchat.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class MessageListeners extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor().getId() == e.getJDA().getSelfUser().getId() || e.getAuthor().isFake()) {
            return;
        }

        String message = e.getMessage().getContent();

        if(message == null || message.equalsIgnoreCase("") || message.equalsIgnoreCase(" ")) {
            return;
        }

        //handle execute command
        if(message.startsWith(ConfigValues.DISCORD_COMMAND_PREFIX + ConfigValues.DISCORD_EXECUTE_COMMAND) && e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            DiscordCommandSender cd = new DiscordCommandSender(Sponge.getServer().getConsole(), e.getChannel().getId());
            Sponge.getCommandManager().process(cd, message.replace(ConfigValues.DISCORD_COMMAND_PREFIX + ConfigValues.DISCORD_EXECUTE_COMMAND + " ", ""));
            cd.sendAllMessages();
            return;
        }

        //chat formations
        if(message.length() > 120) {
            message = message.substring(0, message.length() - 120);
        }
        if(message.startsWith("```")) {
            message = message.substring(0, message.length() - 3).substring(3);
        }
        if(message.startsWith("``")) {
            message = message.substring(0, message.length() - 2).substring(2);
        }

        //handle global and staff channels
        if(e.getChannel().getId().equals(Main.discord.getGlobal().getId())) {
            //handle player command
            if(message.startsWith(ConfigValues.DISCORD_COMMAND_PREFIX + ConfigValues.DISCORD_PLAYERS_COMMAND)) {
                List<String> s = new ArrayList<>();

                Sponge.getServer().getOnlinePlayers().forEach(plr -> s.add(plr.getName()));

                e.getChannel().sendMessage(String.format(ConfigValues.DISCORD_PLAYERS_MESSAGE, (Sponge.getServer().getOnlinePlayers().size() > 0 ? StringUtils.join(s, ", ") : ConfigValues.DISCORD_PLAYERS_MESSAGE_NOPLAYERS))).queue();
                return;
            }

            Sponge.getServer().setBroadcastChannel(MessageChannel.TO_ALL);
            Sponge.getServer().getBroadcastChannel().send(Utils.deserializeText(String.format(ConfigValues.DISCORD_TO_SERVER_GLOBAL, e.getMember().getEffectiveName(), message)));

            return;
        }

        if(e.getChannel().getId().equals(Main.discord.getStaff().getId())) {
            //handle player command
            if(message.startsWith(ConfigValues.DISCORD_COMMAND_PREFIX + ConfigValues.DISCORD_PLAYERS_COMMAND)) {
                List<String> s = new ArrayList<>();

                Sponge.getServer().getOnlinePlayers().forEach(plr -> s.add(plr.getName()));

                e.getChannel().sendMessage(String.format(ConfigValues.DISCORD_PLAYERS_MESSAGE, (Sponge.getServer().getOnlinePlayers().size() > 0 ? StringUtils.join(s, ", ") : ConfigValues.DISCORD_PLAYERS_MESSAGE_NOPLAYERS))).queue();
                return;
            }

            MutableMessageChannel channel = new MagiMessageChannel();

            for(MessageReceiver b : channel.getMembers()) {
                if(b instanceof Player) {
                    Player recipient = (Player)b;

                    if(!recipient.hasPermission("magichat.staff")) {
                        channel.removeMember(b);
                    }
                }
            }
            channel.clearMembers();

            channel.send(Utils.deserializeText(String.format(ConfigValues.DISCORD_TO_SERVER_STAFF, e.getMember().getEffectiveName(), message)));

            return;
        }

    }

}
