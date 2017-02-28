package net.heyzeer0.mgchat.listeners;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.enums.UserChannel;
import net.heyzeer0.mgchat.managers.UserManager;
import net.heyzeer0.mgchat.profiles.MagiMessageChannel;
import net.heyzeer0.mgchat.profiles.UserProfile;
import net.heyzeer0.mgchat.utils.Utils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;

import java.util.Optional;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class ChatListeners {

    @Listener(order = Order.FIRST)
    public void onMessage(MessageChannelEvent.Chat e, @First Player p) {
        Optional<MessageChannel> optionalChannel = e.getChannel();
        if (!optionalChannel.isPresent()) {
            return;
        }

        MessageEvent.MessageFormatter formatter = e.getFormatter();
        MutableMessageChannel channel = new MagiMessageChannel();

        UserProfile profile = UserManager.getProfile(p);

        String message = formatter.getBody().toText().toPlain();

        if(!p.hasPermission("magichat.color")) {
            message = Utils.removeColor(message);
        }

        //global
        if(profile.getSelectedChannel() == UserChannel.GLOBAL) {
            formatter.setHeader(Utils.deserializeText(String.format(ConfigValues.GLOBAL_HEADER, profile.getPrefix(), p.getName())));
            formatter.setBody(Utils.deserializeText(String.format(ConfigValues.GLOBAL_BODY, message)));

            Main.discord.sendGlobalMessage(p.getName(), message);

            e.setChannel(channel);
            return;
        }
        //staff
        if(profile.getSelectedChannel() == UserChannel.STAFF) {
            for(MessageReceiver rsc : channel.getMembers()) {
                if(rsc instanceof Player) {
                    Player recipient = (Player)rsc;

                    if(!recipient.hasPermission("magichat.staff")) {
                        channel.removeMember(rsc);
                    }
                }
            }

            Main.discord.sendStaffMessage(p.getName(), message);

            formatter.setHeader(Utils.deserializeText(String.format(ConfigValues.STAFF_HEADER, p.getName())));
            formatter.setBody(Utils.deserializeText(String.format(ConfigValues.STAFF_BODY, message)));

            channel.clearMembers();

            e.setChannel(channel);
            return;
        }
        if(profile.getSelectedChannel() == UserChannel.LOCAL) {
            Integer players = 0;

            for(MessageReceiver rsc : channel.getMembers()) {
                if(rsc instanceof Player) {
                    Player recipient = (Player)rsc;

                    double distancia = p.getLocation().getPosition().distance(recipient.getLocation().getPosition());

                    UserProfile repP = UserManager.getProfile(recipient);

                    if(distancia > 50 ) {
                        channel.removeMember(rsc);
                        if(repP.isSpying()) {
                            recipient.sendMessage(Utils.deserializeText(String.format(ConfigValues.LOCAL_SPY, p.getName(), message)));
                        }
                        continue;
                    }

                    if(!repP.isVanished()) {
                        players++;
                    }
                }
            }

            channel.clearMembers();

            formatter.setHeader(Utils.deserializeText(String.format(ConfigValues.LOCAL_HEADER, profile.getPrefix(), p.getName())));
            if(players > 1) {
                formatter.setBody(Utils.deserializeText(String.format(ConfigValues.LOCAL_BODY, message)));
            }else{
                formatter.setBody(Utils.deserializeText(String.format(ConfigValues.LOCAL_BODY_NO_PLAYERS, message)));
            }

            e.setChannel(channel);

        }

    }

}
