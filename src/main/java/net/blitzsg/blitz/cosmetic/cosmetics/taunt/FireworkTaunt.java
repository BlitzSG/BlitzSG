package net.blitzsg.blitz.cosmetic.cosmetics.taunt;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.command.fireworks.FireworkCommand;
import net.blitzsg.blitz.cosmetic.Taunt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FireworkTaunt extends Taunt {
    public FireworkTaunt(){
        super("fireworkexplosion", "Firework Explosion", "Oh, so many colors!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.FIREWORK,1));
    }
    public void run(Player p){
        new FireworkCommand().launchFirework(p.getLocation());
    }
}
