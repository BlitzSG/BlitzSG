package net.blitzsg.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.party.Party;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyCreateSubCommand extends SubCommand {
    public PartyCreateSubCommand() {
        super("create", ImmutableList.of(), null, "/party create");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getParty() != null) {
            player.sendMessage(ChatUtil.color("&cYou are already in a party!"));
            return;
        }

        player.sendMessage(ChatUtil.color("&9Party > &eYou have created a party!"));
        iPlayer.setParty(new Party(player.getUniqueId()));
    }
}
