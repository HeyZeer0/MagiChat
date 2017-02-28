package net.heyzeer0.mgchat.profiles;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.channel.AbstractMutableMessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.*;

/**
 * Created by HeyZeer0 on 21/01/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class MagiMessageChannel extends AbstractMutableMessageChannel {

    private Set<MessageReceiver> members;
    private List<MessageReceiver> removed = new ArrayList<>();

    public MagiMessageChannel() {
        this.members = Collections.newSetFromMap(new WeakHashMap<MessageReceiver, Boolean>());
        this.members.addAll(Sponge.getServer().getOnlinePlayers());
        this.members.add(Sponge.getServer().getConsole());
    }

    @Override
    public Collection<MessageReceiver> getMembers() {
        return Collections.unmodifiableSet(this.members);
    }

    @Override
    public boolean addMember(MessageReceiver member) {
        return this.members.add(member);
    }

    @Override
    public boolean removeMember(MessageReceiver member) {
        removed.add(member);
        return true;
    }

    @Override
    public void clearMembers() {
        if(removed != null && removed.size() > 0) {
            for(MessageReceiver rsc : removed) {
                members.remove(rsc);
            }
        }
    }

}
