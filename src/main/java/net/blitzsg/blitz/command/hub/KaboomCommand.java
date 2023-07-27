package net.blitzsg.blitz.command.hub;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.command.SubCommand;
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
public class KaboomCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public KaboomCommand() {
        super("kaboom", ImmutableList.of("kaboomadmin"), "blitz.admin");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }


    @Override
    public void onExecute(CommandSender sender, String[] args) {
        for (Player target : Bukkit.getOnlinePlayers()) {
                target.getWorld().strikeLightningEffect(target.getLocation());
                target.setVelocity(new Vector(0, 64, 0));
                target.setFallDistance(-65.0F);
                target.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "KABOOM!");
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
