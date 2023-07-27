package net.blitzsg.blitz.command.blood;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.player.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Topu
 * @date 7/28/2023
 */
public class BloodCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public BloodCommand() {
        super("blood", ImmutableList.of("bloodeffect"), "");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }


    @Override
    public void onExecute(CommandSender sender, String[] args) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
        if (iPlayer.isBlood()) {
            sender.sendMessage(ChatColor.RED + "Blood effect disabled!");
            iPlayer.setBlood(false);
        } else {
            sender.sendMessage(ChatColor.GREEN + "Blood effect enabled!");
            iPlayer.setBlood(true);
        }
    }

    private List<SubCommand> getAvailableSubs(CommandSender sender) {
        return subcommands.stream()
                .filter(c -> sender.hasPermission(c.getPermission()))
                .collect(Collectors.toList());
    }

    private SubCommand getSubCommand(String name) {
        String a = name.toLowerCase();
        return subcommands.stream()
                .filter(sub -> (sub.getName().equals(a) || sub.getAliases().contains(a)))
                .findFirst().orElse(null);
    }
}
