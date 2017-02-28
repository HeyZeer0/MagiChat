package net.heyzeer0.mgchat.commands.channel;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.managers.UserManager;
import net.heyzeer0.mgchat.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class GlobalCommand implements CommandExecutor {

    public static CommandSpec command = CommandSpec.builder().arguments(GenericArguments.remainingJoinedStrings(TextTemplate.of("message").toText())).executor(new GlobalCommand()).build();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if ((src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "A console não pode executar este comando."), false);
        }

        if(!args.hasAny("message")) {
            throw new CommandException(Text.of(TextColors.RED, "Use: /global (mensagem)"));
        }

        String message = StringUtils.join(args.getAll("message"), " ");
        Player p = (Player)src;

        if(!src.hasPermission("magichat.color")) {
            message = Utils.removeColor(message);
        }

        Sponge.getServer().setBroadcastChannel(MessageChannel.TO_ALL);
        Sponge.getServer().getBroadcastChannel().send(Utils.deserializeText(String.format(ConfigValues.GLOBAL_HEADER, UserManager.getProfile(p).getPrefix(), p.getName()) + String.format(ConfigValues.GLOBAL_BODY, message)));

        Main.discord.sendGlobalMessage(p.getName(), message);

        return CommandResult.success();
    }

}
