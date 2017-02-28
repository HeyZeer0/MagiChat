package net.heyzeer0.mgchat.commands;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigManager;
import net.heyzeer0.mgchat.enums.ConfigResult;
import net.heyzeer0.mgchat.managers.UserManager;
import net.heyzeer0.mgchat.profiles.UserProfile;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class MgChatCommand implements CommandExecutor {

    public static CommandSpec command = CommandSpec.builder().arguments(GenericArguments.optional(GenericArguments.string(TextTemplate.of("option").toText()))).permission("magichat.staff").executor(new MgChatCommand()).build();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!args.hasAny("option")) {
            throw new CommandException(Text.of(TextColors.RED, "Use: /mgchat (reload|spy|vanish)"));
        }

        String option = (String)args.getOne("option").get();

        if(option.equalsIgnoreCase("reload")) {
            if(!src.hasPermission("magichat.admin")) {
                throw new CommandException(Text.of(TextColors.RED, "Você não possui permissão para realizar este comando."));
            }
            src.sendMessage(Text.of(TextColors.GREEN, "Trying to reload the configuration..."));

            List<ConfigResult> resultados = ConfigManager.loadConfig();

            for(int i = 0; i < resultados.size(); i++) {
                String config = "";
                if(i == 0) { config = "main"; }
                if(i == 1) { config = "message"; }
                if(i == 2) { config = "command"; }

                if(resultados.get(i) == ConfigResult.ERROR) {
                    src.sendMessage(Text.of(TextColors.RED, "The " + config + " configuration was not loaded correctly."));
                }
                else if(resultados.get(i) == ConfigResult.CONFIG_LOADED) {
                    src.sendMessage(Text.of(TextColors.DARK_GREEN, "The " + config + " configuration was loaded successfully."));
                }
                else if(resultados.get(i) == ConfigResult.NEW_CONFIG) {
                    src.sendMessage(Text.of(TextColors.AQUA, "The " + config + " configuration was created successfully."));
                }
            }

            if(!Main.discord.isLoaded() && resultados.get(0) == ConfigResult.CONFIG_LOADED) {
                Main.loadDiscord();
            }
        }

        if(option.equalsIgnoreCase("spy")) {
            UserProfile p = UserManager.getProfile((Player) src);

            if(p.isSpying()) {
                p.setSpying(false);
                src.sendMessage(Text.of(TextColors.GREEN, "Você desabilitou o modo espião."));
            }else{
                p.setSpying(true);
                src.sendMessage(Text.of(TextColors.GREEN, "Você habilitou o modo espião."));
            }
        }

        if(option.equalsIgnoreCase("vanish")) {
            UserProfile p = UserManager.getProfile((Player) src);

            if(p.isVanished()) {
                p.setVanished(false);
                src.sendMessage(Text.of(TextColors.GREEN, "Você desabilitou o modo escondido."));
            }else{
                p.setVanished(true);
                src.sendMessage(Text.of(TextColors.GREEN, "Você habilitou o modo escondido."));
            }
        }

        return CommandResult.success();
    }


}
