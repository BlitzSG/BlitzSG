package net.blitzsg.blitz.command.party;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyChatCommand extends Command {
    public PartyChatCommand() {
        super("pc", ImmutableList.of("partychat"), null);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getParty() == null) {
            player.sendMessage(ChatUtil.color("&cYou are not in a party!"));
            return;
        }

        if (args.length > 0) {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }
           iPlayer.getParty().message(ChatUtil.color("&9Party > " + iPlayer.getRank().getPrefix() + player.getName() + "&f: " + message.toString()));


        } else {
            sender.sendMessage(ChatUtil.color("&cUsage: /pc <message>"));
        }
    }
}