package net.heyzeer0.mgchat.utils;


import net.heyzeer0.mgchat.profiles.discord.JDALoader;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.awt.*;
import java.util.regex.Matcher;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class Utils {

    public static void runAsync(Runnable r) {
        new Thread(() -> r.run()) {}.start();
    }

    public static String getUptime(long up) {
        final long
                duration = up,
                years = duration / 31104000L,
                months = duration / 2592000L % 12,
                days = duration / 86400L % 30,
                hours = duration / 3600L % 24,
                minutes = duration / 60L % 60,
                seconds = duration % 60;
        String uptime = (years == 0 ? "" : years + " Anos, ") + (months == 0 ? "" : months + " Meses, ")
                + (days == 0 ? "" : days + " Dias, ") + (hours == 0 ? "" : hours + " Horas, ")
                + (minutes == 0 ? "" : minutes + " Minutos, ") + (seconds == 0 ? "" : seconds + " Segundos, ");

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " e");

        return uptime;
    }

    private static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public static Text deserializeText(String x) {
        return TextSerializers.FORMATTING_CODE.deserialize(x);
    }

    public static String removeColor(String back) {
        Matcher m = JDALoader.colorPattern.matcher(back);

        while(m.find()) {
            back = back.replace(m.group(), "");
        }

        return back;
    }

    public static String convertColor(Color r) {
        if(r == null) {
            return "&f";
        }
        String hex = Integer.toHexString(r.getRGB());
        switch (hex) {
            case "ffe74c3c":
                return "&c";
            case "ff2ecc71":
                return "&a";
            case "ff1f8b4c":
                return "&2";
            case "fff1c40f":
                return "&e";
            case "ffe67e22":
                return "&6";
            case "ff3498db":
                return "&b";
            case "ff206694":
                return "&1";
            case "ffe91e63":
                return "&d";
            case "ff71368":
                return "&5";
            case "fffffff":
                return "&f";
            case "ff95a5a6":
                return "&7";
            case "ff607d8b":
                return "&8";
            default:
                return "";
        }
    }

}
