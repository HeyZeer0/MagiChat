package net.heyzeer0.mgchat.profiles.discord;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectCollection;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.util.Tristate;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class DiscordCommandSender implements ConsoleSource {

    String channel;
    CommandSource src;
    List<String> msgs = new ArrayList<>();
    Integer tentativas = 0;

    public DiscordCommandSender(CommandSource src, String channel) {
        this.channel = channel;
        this.src = src;
    }

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public Locale getLocale() {
        return src.getLocale();
    }

    @Override
    public void sendMessage(Text message) {
        msgs.add(message.toPlain());
    }

    @Override
    public void sendMessages(Text... messages) {
        checkNotNull(messages, "messages");

        for (Text message : messages) {
            this.sendMessage(message);
        }
    }

    @Override
    public boolean hasPermission(Set<Context> contexts, String permission) {
        return true;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public MessageChannel getMessageChannel() {
        return MessageChannel.TO_ALL;
    }

    @Override
    public void setMessageChannel(MessageChannel channel) {
        src.setMessageChannel(channel);
    }

    @Override
    public Optional<CommandSource> getCommandSource() {
        return src.getCommandSource();
    }

    @Override
    public SubjectCollection getContainingCollection() {
        return src.getContainingCollection();
    }

    @Override
    public SubjectData getSubjectData() {
        return src.getSubjectData();
    }

    @Override
    public SubjectData getTransientSubjectData() {
        return src.getTransientSubjectData();
    }

    @Override
    public Tristate getPermissionValue(Set<Context> contexts, String permission) {
        return src.getPermissionValue(contexts, permission);
    }

    @Override
    public List<Subject> getParents(Set<Context> contexts) {
        return src.getParents();
    }

    @Override
    public Optional<String> getOption(Set<Context> contexts, String key) {
        return src.getOption(contexts, key);
    }

    @Override
    public boolean isChildOf(Set<Context> contexts, Subject parent) {
        return src.isChildOf(contexts, parent);
    }

    @Override
    public String getIdentifier() {
        return src.getIdentifier();
    }

    @Override
    public Set<Context> getActiveContexts() {
        return src.getActiveContexts();
    }

    public void sendAllMessages() {
        if(msgs == null || msgs.size() <= 0) {
            if(tentativas <= 2) {
                tentativas++;

                Main.timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                sendAllMessages();
                            }
                        }, 250l);

            }else{
                Main.discord.getJda().getTextChannelById(channel).sendMessage(ConfigValues.DISCORD_EXECUTE_MESSAGE_NORETURN).queue();
            }
            return;
        }
        Main.discord.getJda().getTextChannelById(channel).sendMessage(String.format(ConfigValues.DISCORD_EXECUTE_MESSAGE, StringUtils.join(msgs, "\n"))).queue();
    }


}
