package net.heyzeer0.mgchat.commands.channel;

import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.managers.UserManager;
import net.heyzeer0.mgchat.profiles.MagiMessageChannel;
import net.heyzeer0.mgchat.profiles.UserProfile;
import net.heyzeer0.mgchat.utils.Utils;
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
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by HeyZeer0 on 27/02/17.
 * Copyright © HeyZeer0 - 2016 ~ 2017
 */
public class LocalCommand implements CommandExecutor {

    public static CommandSpec command = CommandSpec.builder().arguments(GenericArguments.remainingJoinedStrings(TextTemplate.of("message").toText())).executor(new LocalCommand()).build();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if ((src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "A console não pode executar este comando."), false);
        }

        if(!args.hasAny("message")) {
            throw new CommandException(Text.of(TextColors.RED, "Use: /local (mensagem)"));
        }

        String message = StringUtils.join(args.getAll("message"), " ");
        Player p = (Player)src;

        MutableMessageChannel channel = new MagiMessageChannel();

        if(!src.hasPermission("magichat.color")) {
            message = Utils.removeColor(message);
        }

        Integer players = 0;

        for(MessageReceiver rsc : channel.getMembers()) {
            if(rsc instanceof Player) {
                Player recipient = (Player)rsc;

                double distancia = p.getLocation().getPosition().distance(recipient.getLocation().getPosition());

                UserProfile repP = UserManager.getProfile(recipient);

                if(distancia > 50 ) {
                    channel.removeMember(rsc);
                    if(repP.isSpying()) {
                        recipient.sendMessage(Utils.deserializeText(String.format(ConfigValues.LOCAL_SPY, p.getName(), message)));
                    }
                    continue;
                }

                if(!repP.isVanished()) {
                    players++;
                }
            }
        }

        channel.clearMembers();

        if(players > 1) {
            channel.send(Utils.deserializeText(String.format(ConfigValues.LOCAL_HEADER, UserManager.getProfile(p).getPrefix(), p.getName()) + String.format(ConfigValues.LOCAL_BODY, message)));
        }else{
            channel.send(Utils.deserializeText(String.format(ConfigValues.LOCAL_HEADER, UserManager.getProfile(p).getPrefix(), p.getName()) + String.format(ConfigValues.LOCAL_BODY_NO_PLAYERS, message)));
        }

        return CommandResult.success();
    }

}
