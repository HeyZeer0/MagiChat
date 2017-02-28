package net.heyzeer0.mgchat.managers;

import net.heyzeer0.mgchat.profiles.UserProfile;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class UserManager {

    private static HashMap<String, UserProfile> users = new HashMap<>();

    public static UserProfile getProfile(Player p) {
        if(!users.containsKey(p.getName())) {
            users.put(p.getName(), new UserProfile(p.getName(), p.getUniqueId()));
        }
        return users.get(p.getName());
    }

}
