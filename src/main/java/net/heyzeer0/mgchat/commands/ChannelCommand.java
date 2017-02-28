package net.heyzeer0.mgchat.commands;

import net.heyzeer0.mgchat.enums.UserChannel;
import net.heyzeer0.mgchat.managers.UserManager;
import org.apache.commons.lang3.StringUtils;
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
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class ChannelCommand implements CommandExecutor {

    public static CommandSpec command = CommandSpec.builder().arguments(GenericArguments.optional(GenericArguments.string(TextTemplate.of("channel").toText()))).executor(new ChannelCommand()).build();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if ((src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "A console não pode executar este comando."), false);
        }

        List<UserChannel> canais = new ArrayList<>(Arrays.asList(UserChannel.values()));
        if(!src.hasPermission("magichat.staff")) {
            canais.remove(UserChannel.STAFF);
        }

        if(!args.hasAny("channel")) {
            throw new CommandException(Text.of(TextColors.RED, "Use: /channel (canal)\n", TextColors.GREEN, "Canais disponiveis: ", TextColors.WHITE, StringUtils.join(canais, ", ")));
        }

        String canal = ((String)args.getOne("channel").get()).toUpperCase();

        if(canal.equalsIgnoreCase("L")) {
            canal = "LOCAL";
        }
        if(canal.equalsIgnoreCase("G")) {
            canal = "GLOBAL";
        }
        if(canal.equalsIgnoreCase("S")) {
            canal = "STAFF";
        }

        try{
            UserChannel ch = UserChannel.valueOf(canal);
            UserManager.getProfile((Player) src).setSelectedChannel(ch);
            src.sendMessage(Text.of(TextColors.GREEN, "Você alterou seu canal para ", TextColors.WHITE, ch.toString()));
        }catch (Exception e) {
            throw new CommandException(Text.of(TextColors.RED, "O canal inserido é invalido.\n", TextColors.GREEN, "Canais disponiveis: ", TextColors.WHITE, StringUtils.join(canais, ", ")));
        }

        return CommandResult.success();
    }

}
