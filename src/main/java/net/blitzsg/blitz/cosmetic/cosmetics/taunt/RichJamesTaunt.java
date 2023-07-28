package net.blitzsg.blitz.cosmetic.cosmetics.taunt;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.cosmetic.Taunt;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.util.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class RichJamesTaunt extends Taunt {
    public RichJamesTaunt() {
        super("richjames", "Rich James", "While taunting, you flaunt your greed by showing off your diamonds.", BlitzSG.getInstance().getRankManager().getRankByName("MVP"), new ItemStack(Material.DIAMOND, 1));
    }


    public void run(Player player) {
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

        for (int t = 0; t < 20; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    drop(player.getLocation(), player);
                    drop(player.getLocation(), player);
                    player.getWorld().getPlayers().forEach(player1 -> player1.playSound(player.getLocation(), Sound.ORB_PICKUP, 2,1));
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 5);
        }

    }

    private void drop(Location location, Player player) {
        ItemStack toDrop = ItemStackUtil.createItem(Material.DIAMOND, String.valueOf(Math.random() * 100.0D));
        final Item item = location.getWorld().dropItem(player.getEyeLocation().add(0,1,0), toDrop);
        item.setVelocity(new Vector(Math.random() - Math.random(), 0.5D, Math.random() - Math.random()));
        item.setPickupDelay(Integer.MAX_VALUE);

        new BukkitRunnable() {
            @Override
            public void run() {
                item.remove();
            }
        }.runTaskLater(BlitzSG.getInstance(), 15);
    }
}
