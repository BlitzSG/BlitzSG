package net.blitzsg.blitz.cosmetic.cosmetics.aura;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SnowParticle extends Aura {
    public SnowParticle() {
        super("SnowParticle", "Snow Particle Aura", "Show off your cold hearted side!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.SNOW_BLOCK, 1),10);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().add(0, 1, 0);

        //p.playEffect(p.getLocation(),Effect.TILE_DUST, Material.WOOL.getId(),3,4,2);

        Bukkit.getOnlinePlayers().forEach(player1 -> player1.spigot().playEffect(loc, Effect.SNOWBALL_BREAK, 0, 0, (float) .2, (float) 0.5, (float) .2, 0, 1, 64));


    }
}
