package net.heyzeer0.mgchat.commands.channel;

import net.heyzeer0.mgchat.Main;
import net.heyzeer0.mgchat.config.ConfigValues;
import net.heyzeer0.mgchat.profiles.MagiMessageChannel;
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
public class StaffCommand implements CommandExecutor {

    public static CommandSpec command = CommandSpec.builder().permission("magichat.staff").arguments(GenericArguments.remainingJoinedStrings(TextTemplate.of("message").toText())).executor(new StaffCommand()).build();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if ((src instanceof ConsoleSource)) {
            throw new CommandException(Text.of(TextColors.RED, "A console não pode executar este comando."), false);
        }

        if(!args.hasAny("message")) {
            throw new CommandException(Text.of(TextColors.RED, "Use: /staff (mensagem)"));
        }

        String message = StringUtils.join(args.getAll("message"), " ");
        Player p = (Player)src;

        MutableMessageChannel channel = new MagiMessageChannel();

        if(!src.hasPermission("magichat.color")) {
            message = Utils.removeColor(message);
        }

        for(MessageReceiver rsc : channel.getMembers()) {
            if(rsc instanceof Player) {
                Player recipient = (Player)rsc;

                if(!recipient.hasPermission("magichat.staff")) {
                    channel.removeMember(rsc);
                }
            }
        }

        channel.send(Utils.deserializeText(String.format(ConfigValues.STAFF_HEADER, p.getName()) + String.format(ConfigValues.STAFF_BODY, message)));

        Main.discord.sendStaffMessage(p.getName(), message);

        channel.clearMembers();

        return CommandResult.success();
    }

}
