package net.blitzsg.blitz.gamestar.stars;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.gamestar.Star;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Assassin extends Star {
    public Assassin() {
        super("Assassin", Material.BONE, "Teleport to a random player and do 10 hearts damage.", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        List<Player> targets = new ArrayList<>(user.getGame().getAlivePlayers());

        targets.remove(p);
        int index = new Random().nextInt(targets.size());
        Player target = targets.get(index);

        p.teleport(target);

        if (target.getHealth() / 2 <= 0) {
            IPlayer iTarget = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
            BlitzSG.getInstance().getGameHandler().onPlayerDeath(target, iTarget.getLastAttacker());
        } else {
            target.setHealth(target.getHealth() / 2);
        }
        target.sendMessage(ChatColor.RED + "You were assassined by " + p.getName());
        p.sendMessage(ChatColor.RED + "You assassined " + target.getName());

    }
}
