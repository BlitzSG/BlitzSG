package net.blitzsg.blitz.gamestar.stars;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.game.Game;
import net.blitzsg.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Robinhood extends Star {
    public Robinhood() {
        super("Robinhood", Material.BOW, "1 shot kill!", 10000);
    }
    private Game game;
    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        user.setRobinhood(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(game != null && game == user.getGame())
                    BlitzSG.send(p, BlitzSG.CORE_NAME+"&aRobinhood wore off!");
                user.setRobinhood(false);
            }
        }.runTaskLater(BlitzSG.getInstance(), 30 * 20);
    }
}
