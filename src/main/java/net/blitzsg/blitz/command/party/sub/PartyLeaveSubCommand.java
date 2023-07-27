package net.blitzsg.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyLeaveSubCommand extends SubCommand {
    public PartyLeaveSubCommand() {
        super("leave", ImmutableList.of(), null, "/party leave");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(s.getUniqueId());
        if (iPlayer.getParty() == null) {
            s.sendMessage(ChatUtil.color("&cYou are not in a party!"));
            return;
        }
        iPlayer.getParty().leave(s);
    }
}
