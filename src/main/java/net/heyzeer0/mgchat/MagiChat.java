package net.heyzeer0.mgchat;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

/**
 * Created by HeyZeer0 on 26/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */

@Plugin(name = "MagiChat", id = "mgchat", version = "3.0.0", authors = {"HeyZeer0"}, description = "A chat plugin + discord.")
public class MagiChat {

    //interface
    private static MagiChat instance;
    private Logger logger;

    @Inject @ConfigDir(sharedRoot = false)
    private File configDir;


    @Inject
    public MagiChat(Logger log) {
        logger = log;
        instance = this;
    }

    public static MagiChat getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public File getConfigDir() {
        return configDir;
    }

    //Listener
    @Listener
    public void serverStart(GameStartedServerEvent e) {
        Main.onEnable();
    }

    @Listener
    public void serverStop(GameStoppingEvent e) {
        Main.onDisable();
    }

}
