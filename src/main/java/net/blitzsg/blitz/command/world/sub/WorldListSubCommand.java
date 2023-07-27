package net.blitzsg.blitz.command.world.sub;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldListSubCommand extends SubCommand {

    public WorldListSubCommand() {
        super("list", ImmutableList.of("l"), "blitz.rank.worldlist", "/world list");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        if (args.length == 1) {
            Bukkit.getWorlds().forEach(str -> sender.sendMessage(ChatUtil.color(("&7- &a" + str.getName() + " &e" + str.getLoadedChunks().length + " chunk(s)" + " &6" + str.getEntities().size() + " entities &c" + str.getPlayers().size() + " player(s)"))));
        }
    }
}