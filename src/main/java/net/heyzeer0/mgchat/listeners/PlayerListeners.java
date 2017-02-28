package net.heyzeer0.mgchat.listeners;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.achievement.GrantAchievementEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class PlayerListeners {


    @Listener(order = Order.LAST)
    public void playerJoin(ClientConnectionEvent.Join e) {
        if(!e.getTargetEntity().hasPlayedBefore()) {
            Main.discord.getGlobal().sendMessage(String.format(ConfigValues.PLAYER_FIRST_JOIN, e.getTargetEntity().getName(), ConfigValues.SERVER_NAME)).queue();
            return;
        }
        Main.discord.getGlobal().sendMessage(String.format(ConfigValues.PLAYER_JOIN, e.getTargetEntity().getName())).queue();
    }

    @Listener(order = Order.LAST)
    public void playerLeave(ClientConnectionEvent.Disconnect e) {
        Main.discord.getGlobal().sendMessage(String.format(ConfigValues.PLAYER_LEAVE, e.getTargetEntity().getName())).queue();
    }

    @Listener(order = Order.LAST)
    public void death(DestructEntityEvent.Death e) {
        if (e.getTargetEntity() instanceof Player) {
            Main.discord.getGlobal().sendMessage("**" + e.getMessage().toPlain() + "**").queue();
        }
    }

    @Listener(order = Order.LAST)
    public void achievementGet(GrantAchievementEvent.TargetPlayer e) {
        if(ConfigValues.PLAYER_ACHIEVEMENT_GET.equalsIgnoreCase("") || ConfigValues.PLAYER_ACHIEVEMENT_GET.equalsIgnoreCase("<optional>")) {
            Main.getPlugin().getLogger().warn("Contem aqui");
            return;
        }
        Main.getPlugin().getLogger().warn("n contem");
        Main.discord.getGlobal().sendMessage(String.format(ConfigValues.PLAYER_ACHIEVEMENT_GET, e.getTargetEntity().getName(), e.getAchievement().getName())).queue();
    }


}
