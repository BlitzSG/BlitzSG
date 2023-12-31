package net.blitzsg.blitz.gamestar.stars;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.game.Game;
import net.blitzsg.blitz.gamestar.Star;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Wobbuffet extends Star {
    public Wobbuffet() {
        super("Wobbuffet", Material.SPONGE, "Reflects all damage onto an opponent for 30 seconds!", 10000);
    }

    private Game game;

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        user.setWobbuffet(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (game != null && game == user.getGame()) {
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&aWobbuffet wore off!"));
                }
                user.setWobbuffet(false);
            }
        }.runTaskLater(BlitzSG.getInstance(), 30 * 20);
    }
}