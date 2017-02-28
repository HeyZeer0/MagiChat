package net.heyzeer0.mgchat.profiles;

import net.heyzeer0.mgchat.enums.UserChannel;
import org.spongepowered.api.Sponge;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class UserProfile {

    UUID uuid;
    UserChannel channel;
    boolean spying = false;
    boolean vanished = false;

    public UserProfile(String username, UUID useruuid) {
        uuid = useruuid;
        channel = UserChannel.GLOBAL;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public UserChannel getSelectedChannel() {
        return channel;
    }

    public void setSelectedChannel(UserChannel select) {
        channel = select;
    }

    public String getPrefix() {
        if(!Sponge.getServer().getPlayer(uuid).isPresent()) {
            return "";
        }
        Optional<String>op = Sponge.getServer().getPlayer(uuid).get().getOption("prefix");
        if(op.isPresent()) {
            return op.get();
        }
        return "";
    }

    public boolean isSpying() {
        return spying;
    }

    public void setSpying(boolean select) {
        spying = select;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean select) {
        vanished = select;
    }

}
