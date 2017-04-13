package net.heyzeer0.mgchat.config;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.enums.ConfigResult;
import net.heyzeer0.mgchat.profiles.ConfigProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class ConfigManager {

    public static ConfigProfile main_config = new ConfigProfile("config", null);
    public static ConfigProfile message_config = new ConfigProfile("messages", null);
    public static ConfigProfile command_config = new ConfigProfile("commands", null);

    public static List<ConfigResult> loadConfig() {
        ConfigResult main_result = main_config.loadConfig();

        if(main_result == ConfigResult.NEW_CONFIG) {
            Main.getPlugin().getLogger().warn("A new configuration was generated, some functions have been disabled.");
            Main.getPlugin().getLogger().warn("You can reload the configuration by typing: /mgchat reload.");

            main_config.getConfig().set("bot-token", ConfigValues.BOT_TOKEN);
            main_config.getConfig().set("bot-game", ConfigValues.BOT_GAME);
            main_config.getConfig().set("global-channel-id", ConfigValues.GLOBAL_CHANNEL_ID);
            main_config.getConfig().set("staff-channel-id", ConfigValues.STAFF_CHANNEL_ID);
            main_config.getConfig().set("webhook-url", ConfigValues.WEBHOOK_URL);
            main_config.getConfig().set("server-name", ConfigValues.SERVER_NAME);

            main_config.saveConfig();
        }
        if(main_result == ConfigResult.CONFIG_LOADED) {
            ConfigValues.BOT_TOKEN = main_config.getConfig().getString("bot-token");
            ConfigValues.BOT_GAME = main_config.getConfig().getString("bot-game");
            ConfigValues.GLOBAL_CHANNEL_ID = main_config.getConfig().getString("global-channel-id");
            ConfigValues.STAFF_CHANNEL_ID = main_config.getConfig().getString("staff-channel-id");
            ConfigValues.WEBHOOK_URL = main_config.getConfig().getString("webhook-url");
            ConfigValues.SERVER_NAME = main_config.getConfig().getString("server-name");
        }

        ConfigResult message_result = message_config.loadConfig();

        if(message_result == ConfigResult.NEW_CONFIG) {
            message_config.getConfig().set("server-start", ConfigValues.SERVER_START);
            message_config.getConfig().set("server-stop", ConfigValues.SERVER_STOP);
            message_config.getConfig().set("player-join", ConfigValues.PLAYER_JOIN);
            message_config.getConfig().set("player-first-join", ConfigValues.PLAYER_FIRST_JOIN);
            message_config.getConfig().set("player-leave", ConfigValues.PLAYER_LEAVE);
            message_config.getConfig().set("player-achievement-get", ConfigValues.PLAYER_ACHIEVEMENT_GET);

            message_config.getConfig().set("discord-to-server-global", ConfigValues.DISCORD_TO_SERVER_GLOBAL);
            message_config.getConfig().set("discord-to-server-staff", ConfigValues.DISCORD_TO_SERVER_STAFF);
            message_config.getConfig().set("server-to-discord", ConfigValues.SERVER_TO_DISCORD);

            message_config.getConfig().set("local-spy", ConfigValues.LOCAL_SPY);
            message_config.getConfig().set("local-header", ConfigValues.LOCAL_HEADER);
            message_config.getConfig().set("local-body", ConfigValues.LOCAL_BODY);
            message_config.getConfig().set("local-body-noplayers", ConfigValues.LOCAL_BODY_NO_PLAYERS);
            message_config.getConfig().set("global-header", ConfigValues.GLOBAL_HEADER);
            message_config.getConfig().set("global-body", ConfigValues.GLOBAL_BODY);
            message_config.getConfig().set("staff-header", ConfigValues.STAFF_HEADER);
            message_config.getConfig().set("staff-body", ConfigValues.STAFF_BODY);

            message_config.saveConfig();
        }

        if(message_result == ConfigResult.CONFIG_LOADED) {
            ConfigValues.SERVER_START = message_config.getConfig().getString("server-start");
            ConfigValues.SERVER_STOP = message_config.getConfig().getString("server-stop");
            ConfigValues.PLAYER_JOIN = message_config.getConfig().getString("player-join");
            ConfigValues.PLAYER_FIRST_JOIN = message_config.getConfig().getString("player-first-join");
            ConfigValues.PLAYER_LEAVE = message_config.getConfig().getString("player-leave");
            ConfigValues.PLAYER_ACHIEVEMENT_GET = message_config.getConfig().getString("player-achievement-get");

            ConfigValues.DISCORD_TO_SERVER_GLOBAL = message_config.getConfig().getString("discord-to-server-global");
            ConfigValues.DISCORD_TO_SERVER_STAFF = message_config.getConfig().getString("discord-to-server-staff");
            ConfigValues.SERVER_TO_DISCORD = message_config.getConfig().getString("server-to-discord");

            ConfigValues.LOCAL_SPY = message_config.getConfig().getString("local-spy");
            ConfigValues.LOCAL_HEADER = message_config.getConfig().getString("local-header");
            ConfigValues.LOCAL_BODY = message_config.getConfig().getString("local-body");
            ConfigValues.LOCAL_BODY_NO_PLAYERS = message_config.getConfig().getString("local-body-noplayers");
            ConfigValues.GLOBAL_HEADER = message_config.getConfig().getString("global-header");
            ConfigValues.GLOBAL_BODY = message_config.getConfig().getString("global-body");
            ConfigValues.STAFF_HEADER = message_config.getConfig().getString("staff-header");
            ConfigValues.STAFF_BODY = message_config.getConfig().getString("staff-body");
        }

        ConfigResult command_result = command_config.loadConfig();

        if(command_result == ConfigResult.NEW_CONFIG) {
            command_config.getConfig().set("discord-command-prefix", ConfigValues.DISCORD_COMMAND_PREFIX);
            command_config.getConfig().set("discord-execute-command", ConfigValues.DISCORD_EXECUTE_COMMAND);
            command_config.getConfig().set("discord-players-command", ConfigValues.DISCORD_PLAYERS_COMMAND);
            command_config.getConfig().set("discord-topic", ConfigValues.DISCORD_TOPIC);

            command_config.getConfig().set("discord-execute-message", ConfigValues.DISCORD_EXECUTE_MESSAGE);
            command_config.getConfig().set("discord-execute-message-noreturn", ConfigValues.DISCORD_EXECUTE_MESSAGE_NORETURN);
            command_config.getConfig().set("discord-players-message", ConfigValues.DISCORD_PLAYERS_MESSAGE);
            command_config.getConfig().set("discord-players-message-noplayers", ConfigValues.DISCORD_PLAYERS_MESSAGE_NOPLAYERS);

            command_config.saveConfig();
        }

        if(command_result == ConfigResult.CONFIG_LOADED) {
            ConfigValues.DISCORD_COMMAND_PREFIX = command_config.getConfig().getString("discord-command-prefix");
            ConfigValues.DISCORD_EXECUTE_COMMAND = command_config.getConfig().getString("discord-execute-command");
            ConfigValues.DISCORD_PLAYERS_COMMAND = command_config.getConfig().getString("discord-players-command");
            ConfigValues.DISCORD_TOPIC = command_config.getConfig().getString("discord-topic");

            ConfigValues.DISCORD_EXECUTE_MESSAGE = command_config.getConfig().getString("discord-execute-message");
            ConfigValues.DISCORD_EXECUTE_MESSAGE_NORETURN = command_config.getConfig().getString("discord-execute-message-noreturn");
            ConfigValues.DISCORD_PLAYERS_MESSAGE = command_config.getConfig().getString("discord-players-message");
            ConfigValues.DISCORD_PLAYERS_MESSAGE_NOPLAYERS = command_config.getConfig().getString("discord-players-message-noplayers");
        }

        List<ConfigResult> result = new ArrayList<>();
        result.add(main_result);
        result.add(message_result);
        result.add(command_result);


        return result;
    }

}
