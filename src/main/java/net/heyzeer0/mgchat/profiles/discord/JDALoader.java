package net.heyzeer0.mgchat.profiles.discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.GameImpl;
import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.enums.JDAConnection;
import net.heyzeer0.mgchat.listeners.discord.MessageListeners;
import net.heyzeer0.mgchat.utils.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class JDALoader {

    private static final Pattern mentionPattern = Pattern.compile("(@\\S*)");
    public static final Pattern colorPattern = Pattern.compile("(&[a-fA-Fl-oL-O1-9])");

    JDA jda;
    boolean loaded = false;
    boolean useWebhook = false;

    Integer uptime = 0;

    public JDALoader() {}

    public JDAConnection loadJDA() {

        if(ConfigValues.BOT_GAME.equalsIgnoreCase("<game>") || ConfigValues.BOT_TOKEN.equalsIgnoreCase("<token>") || ConfigValues.SERVER_NAME.equalsIgnoreCase("<server-name>") || ConfigValues.GLOBAL_CHANNEL_ID.equalsIgnoreCase("<id>") || ConfigValues.STAFF_CHANNEL_ID.equalsIgnoreCase("<id>")) {
            return JDAConnection.MISSING_CONFIGURATION;
        }

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(ConfigValues.BOT_TOKEN)
                    .setGame(new GameImpl(ConfigValues.BOT_GAME, "", Game.GameType.DEFAULT))
                    .setAutoReconnect(true)
                    .addListener(new MessageListeners())
                    .setAudioEnabled(false)
                    .buildBlocking();

            if(!ConfigValues.WEBHOOK_URL.equalsIgnoreCase("") && !ConfigValues.WEBHOOK_URL.equalsIgnoreCase("<optional>")) {
                useWebhook = true;
                Main.getPlugin().getLogger().info("Use of Webhook has been enabled.");
            }

            TextChannel staff = jda.getTextChannelById(ConfigValues.STAFF_CHANNEL_ID);
            TextChannel global = jda.getTextChannelById(ConfigValues.GLOBAL_CHANNEL_ID);

            if(global == null || staff == null) {
                return JDAConnection.INVALID_CHANNEL;
            }

            loaded = true;

        } catch (Exception x) {
            x.printStackTrace();
            return JDAConnection.FAILURE;
        }

        jda.getTextChannelById(ConfigValues.GLOBAL_CHANNEL_ID).sendMessage(ConfigValues.SERVER_START).queue();

        updateTopic();
        return JDAConnection.SUCCESSFUL;
    }

    public JDA getJda() {
        if(!loaded) {
            return null;
        }
        return jda;
    }

    public void sendGlobalMessage(String nick, String message) {
        if(!loaded) {
            return;
        }

        if(!ConfigValues.WEBHOOK_URL.equalsIgnoreCase("<optional>") && !ConfigValues.WEBHOOK_URL.equalsIgnoreCase("")) {
            useWebhook = true;
        }

        if(useWebhook) {

            if(ConfigValues.WEBHOOK_URL.equalsIgnoreCase("<optional>") || ConfigValues.WEBHOOK_URL.equalsIgnoreCase("")) {
                useWebhook = false;
                sendGlobalMessage(nick, message);
                return;
            }

            //run in async
            Utils.runAsync(() -> {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(ConfigValues.WEBHOOK_URL);
                try {
                    post.setHeader("Charset","UTF-8");
                    List<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("content", convertMessage(message)));
                    nameValuePairs.add(new BasicNameValuePair("username", nick));
                    nameValuePairs.add(new BasicNameValuePair("avatar_url", "https://crafatar.com/avatars/" + nick + "?default=MHF_Alex"));
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
                    post.setEntity(urlEncodedFormEntity);
                    httpClient.execute(post);
                } catch (IOException e) {}
            });

            return;
        }

        jda.getTextChannelById(ConfigValues.GLOBAL_CHANNEL_ID).sendMessage(String.format(ConfigValues.SERVER_TO_DISCORD, nick, convertMessage(message))).queue();
    }

    public void sendStaffMessage(String nick, String message) {
        if(!loaded) {
            return;
        }
        jda.getTextChannelById(ConfigValues.STAFF_CHANNEL_ID).sendMessage(String.format(ConfigValues.SERVER_TO_DISCORD, nick, convertMessage(message))).queue();
    }

    public TextChannel getGlobal() {
        return jda.getTextChannelById(ConfigValues.GLOBAL_CHANNEL_ID);
    }

    public TextChannel getStaff() {
        return jda.getTextChannelById(ConfigValues.STAFF_CHANNEL_ID);
    }

    private String convertMessage(String msg) {
        String message = msg;

        Matcher usermention = mentionPattern.matcher(message);
        while (usermention.find()) {
            String mention = usermention.group();
            String mentionName = mention.replace("@", "");

            User user = jda.getUsersByName(mentionName, false).get(0);
            if (user != null) {
                message = message.replaceFirst(mention, user.getAsMention());
            }
        }

        Matcher m = colorPattern.matcher(message);

        while(m.find()) {
            message = message.replace(m.group(), "");
        }

        return message;
    }

    private void updateTopic() {
        Main.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String mensagem = ConfigValues.DISCORD_TOPIC;
                if(mensagem.contains("%tps%")) {
                    mensagem = mensagem.replace("%tps%", "" + Math.round(Sponge.getServer().getTicksPerSecond()));
                }
                if(mensagem.contains("%uptime%")) {
                    mensagem = mensagem.replace("%uptime%", Utils.getUptime(uptime));
                }
                if(mensagem.contains("%online%")) {
                    mensagem = mensagem.replace("%online%", Sponge.getServer().getOnlinePlayers().size() + "/" + Sponge.getServer().getMaxPlayers());
                }
                if(mensagem.contains("%server_status%")) {
                    mensagem = mensagem.replace("%server_status%", "Online");
                }

                uptime+=5;

                jda.getTextChannelById(ConfigValues.GLOBAL_CHANNEL_ID).getManager().setTopic(mensagem).queue();

                if(loaded) {
                    updateTopic();
                }
            }
        }, 5000);
    }

    public boolean isLoaded() {
        return loaded;
    }

}
