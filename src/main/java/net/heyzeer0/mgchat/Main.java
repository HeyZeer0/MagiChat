package net.heyzeer0.mgchat;

import net.heyzeer0.mgchat.commands.ChannelCommand;
import net.heyzeer0.mgchat.commands.MgChatCommand;
import net.heyzeer0.mgchat.commands.channel.GlobalCommand;
import net.heyzeer0.mgchat.commands.channel.LocalCommand;
import net.heyzeer0.mgchat.commands.channel.StaffCommand;
import net.heyzeer0.mgchat.config.ConfigManager;
import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.enums.ConfigResult;
import net.heyzeer0.mgchat.enums.JDAConnection;
import net.heyzeer0.mgchat.listeners.ChatListeners;
import net.heyzeer0.mgchat.listeners.PlayerListeners;
import net.heyzeer0.mgchat.profiles.discord.JDALoader;
import org.spongepowered.api.Sponge;

import java.util.List;
import java.util.Timer;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class Main {

    public static JDALoader discord = new JDALoader();
    public static Timer timer = new Timer();

    public static void onEnable() {

        //registering some commands
        Sponge.getCommandManager().register(Main.getPlugin(), MgChatCommand.command, "mgchat");
        Sponge.getCommandManager().register(Main.getPlugin(), ChannelCommand.command, "channel", "ch", "canal");
        Sponge.getCommandManager().register(Main.getPlugin(), GlobalCommand.command, "global", "geral", "g");
        Sponge.getCommandManager().register(Main.getPlugin(), LocalCommand.command, "local", "l");
        Sponge.getCommandManager().register(Main.getPlugin(), StaffCommand.command, "staff", "s");

        //registering some listeners
        Sponge.getEventManager().registerListeners(Main.getPlugin(), new ChatListeners());

        //loading config
        List<ConfigResult> resultados = ConfigManager.loadConfig();

        for(int i = 0; i < resultados.size(); i++) {
            String config = "";
            if(i == 0) { config = "main"; }
            if(i == 1) { config = "message"; }
            if(i == 2) { config = "command"; }

            if(resultados.get(i) == ConfigResult.ERROR) {
                getPlugin().getLogger().warn("The " + config + " configuration was not loaded correctly.");
            }
        }

        //loading discord module
        if(resultados.get(0) == ConfigResult.CONFIG_LOADED) {
            loadDiscord();
        }

    }

    public static void onDisable() {
        if(discord.isLoaded()) {
            discord.getGlobal().sendMessage(ConfigValues.SERVER_STOP).queue();
        }
        Sponge.getEventManager().unregisterPluginListeners(MagiChat.getInstance());
    }

    public static MagiChat getPlugin() {
        return MagiChat.getInstance();
    }

    public static void loadDiscord() {
        JDAConnection result = discord.loadJDA();
        if(result == JDAConnection.MISSING_CONFIGURATION) {
            getPlugin().getLogger().warn("Some essential settings are not complete, some functions have been disabled.");
            getPlugin().getLogger().warn("You can reload the configuration by typing: /mgchat reload.");
            return;
        }
        if(result == JDAConnection.INVALID_CHANNEL) {
            getPlugin().getLogger().warn("Some or all channels defined in the configuration are invalid, some functions have been disabled.");
            getPlugin().getLogger().warn("You can reload the configuration by typing: /mgchat reload.");
            return;
        }
        if(result == JDAConnection.FAILURE) {
            getPlugin().getLogger().warn("Could not connect to discord, please try again later.");
            getPlugin().getLogger().warn("You can reload the configuration by typing: /mgchat reload.");
            return;
        }
        getPlugin().getLogger().info("Connection with discord stabilized successfully.");
        Sponge.getEventManager().registerListeners(MagiChat.getInstance(), new PlayerListeners());
    }

}
