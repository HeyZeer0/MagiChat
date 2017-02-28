package net.heyzeer0.mgchat.config;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class ConfigValues {

    //Bot info
    public static String BOT_TOKEN = "<token>";
    public static String BOT_GAME = "<game>";
    public static String GLOBAL_CHANNEL_ID = "<id>";
    public static String STAFF_CHANNEL_ID = "<id>";
    public static String WEBHOOK_URL = "<optional>";
    public static String SERVER_NAME = "<server-name>";

    //Server Messages
    public static String SERVER_START = "**O servidor está iniciando!**";
    public static String SERVER_STOP = "**O servidor foi desligado!**";
    public static String PLAYER_JOIN = "**%s entrou no servidor**";
    public static String PLAYER_FIRST_JOIN = "**Bem-Vindo %s ao %s**";
    public static String PLAYER_LEAVE = "**%s saiu do servidor**";
    public static String PLAYER_ACHIEVEMENT_GET = "**%s adquiriu um nova conquista: %s**";

    //Discord Command Messages
    public static String DISCORD_EXECUTE_MESSAGE_NORETURN = "Executado sem erros e sem retorno.";
    public static String DISCORD_EXECUTE_MESSAGE = "Executado com retorno:```%s```";
    public static String DISCORD_PLAYERS_MESSAGE = "**Jogadores online:**```%s```";
    public static String DISCORD_PLAYERS_MESSAGE_NOPLAYERS = "Nenhum jogador online.";

    //Discord Chat Prefixes
    public static String DISCORD_TO_SERVER_GLOBAL = "&7[&a&lG&7] &7[&bDiscord&7] &f%s&7: &7%s";
    public static String DISCORD_TO_SERVER_STAFF = "&7[&c&lS&7] &7[&bDiscord&7] &f%s&7: &7%s";
    public static String SERVER_TO_DISCORD = "**%s:** %s";

    //Chat prefixes
    public static String LOCAL_HEADER = "&7[&e&lL&7] %s %s&7: ";
    public static String LOCAL_SPY = "&7[&8S&7] &8%s&7: %s";
    public static String LOCAL_BODY = "&e%s";
    public static String LOCAL_BODY_NO_PLAYERS = "&e%s\nNão há ninguém para te escutar.";
    public static String GLOBAL_HEADER = "&7[&a&lG&7] %s %s&7: ";
    public static String GLOBAL_BODY = "&7%s";
    public static String STAFF_HEADER = "&7[&c&lS&7] %s&7: ";
    public static String STAFF_BODY = "&f%s";

    //Discord Commands
    public static String DISCORD_COMMAND_PREFIX = "!";
    public static String DISCORD_EXECUTE_COMMAND = "m";
    public static String DISCORD_PLAYERS_COMMAND = "online";
    public static String DISCORD_TOPIC = "%online% Jogadores | TPS: %tps% | Servidor %server_status% | Uptime: %uptime%";


}
