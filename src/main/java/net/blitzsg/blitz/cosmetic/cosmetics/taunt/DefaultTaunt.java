package net.blitzsg.blitz.cosmetic.cosmetics.taunt;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.cosmetic.Taunt;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultTaunt extends Taunt {
    public DefaultTaunt(){
        super("default", "Default", "The default taunt", BlitzSG.getInstance().getRankManager().getRankByName("Default"), new ItemStack(Material.MAGMA_CREAM,1));
    }
    public void uh(){

    }
}
