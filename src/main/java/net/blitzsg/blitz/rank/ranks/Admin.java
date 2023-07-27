package net.blitzsg.blitz.rank.ranks;

import net.blitzsg.blitz.rank.Rank;
import org.bukkit.ChatColor;

public class Admin extends Rank {
    public Admin(){
    super("Admin",ChatColor.RED + "[ADMIN] ", ChatColor.RED + "", 5,9);
    }
}
