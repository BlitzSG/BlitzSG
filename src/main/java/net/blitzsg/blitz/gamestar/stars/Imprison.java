package net.blitzsg.blitz.gamestar.stars;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Imprison extends Star {
    public Imprison() {
        super("Imprison", Material.IRON_FENCE, "Prevent all opponents from moving!", 5000);
    }

    @Override
    public void run(Player p) {
        //BlitzSG.getInstance().getAnticheat().whitelistPlayer(p, 20* 25);
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        for (Player alivePlayer : user.getGame().getAlivePlayers()) {
            if (alivePlayer != p)
                alivePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 9));
        }

    }
}