package me.hardstyles.blitz.command.list;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public ListCommand() {
        super("list", ImmutableList.of("players"), null);


    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        ArrayList<IPlayer> players = new ArrayList<>();
        // sort players by rank and then by name
        for (Player p : Bukkit.getOnlinePlayers()) {
            IPlayer ip = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
            if (ip == null) continue;
            players.add(ip);
        }

        players.sort((o1, o2) -> {
            if (o1.getRank().getPosition() == o2.getRank().getPosition()) {
                return o1.getName().compareTo(o2.getName());

            }
            return o2.getRank().getPosition() - o1.getRank().getPosition();
        });

        sender.sendMessage(ChatUtil.color("&e&m----------------------------------------"));
        sender.sendMessage(ChatUtil.color("&7&lOnline Players: &f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers()));
        sender.sendMessage(ChatUtil.color("&e&m----------------------------------------"));
        for (IPlayer p : players) {
            if (p.getGame() == null) {
                sender.sendMessage(ChatUtil.color("&7- &f" + p.getRank().getPrefix() + p.getName()));
            } else {
                sender.sendMessage(ChatUtil.color("&7- &f" + p.getRank().getPrefix() + p.getName() + " &r- &e" + p.getGame().getMap().getMapName()));
            }
        }
        sender.sendMessage(ChatUtil.color("&e&m----------------------------------------"));

    }
}