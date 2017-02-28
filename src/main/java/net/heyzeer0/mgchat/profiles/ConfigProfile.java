package net.heyzeer0.mgchat.profiles;

/**
 * Created by HeyZeer0 on 02/12/2016.
 * Copyright © HeyZeer0 - 2016
 */

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.yaml.InvalidConfigurationException;
import net.heyzeer0.mgchat.config.yaml.file.YamlConfiguration;
import net.heyzeer0.mgchat.enums.ConfigResult;

import java.io.File;
import java.io.IOException;


public class ConfigProfile {

    String nome;
    String pasta;

    File config_file;
    YamlConfiguration config;


    boolean loaded = false;

    public ConfigProfile(String nome, String pasta) {
        this.nome = nome;
        this.pasta = pasta;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public ConfigResult loadConfig() {

        ConfigResult resultado = ConfigResult.CONFIG_LOADED;

        if (!Main.getPlugin().getConfigDir().exists()) {
            Main.getPlugin().getConfigDir().mkdirs();
        }

        if (pasta == null) {
            config_file = new File(Main.getPlugin().getConfigDir(), nome + ".yml");

        } else {

            if (!new File(Main.getPlugin().getConfigDir() + File.separator + pasta).exists()) {
                new File(Main.getPlugin().getConfigDir() + File.separator + pasta).mkdir();
            }

            config_file = new File(Main.getPlugin().getConfigDir() + File.separator + pasta, nome + ".yml");
        }

        if (!config_file.exists()) {
            try {
                config_file.createNewFile();
                resultado = ConfigResult.NEW_CONFIG;
            } catch (IOException e) {
                e.printStackTrace();
                return ConfigResult.ERROR;
            }
        }

        try{
            config = YamlConfiguration.loadConfiguration(config_file);
        }catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return ConfigResult.ERROR;
        }catch (Exception e) {
            return ConfigResult.ERROR;
        }

        loaded = true;

        return resultado;
    }

    public void saveConfig() {
        try {
            config.save(config_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

