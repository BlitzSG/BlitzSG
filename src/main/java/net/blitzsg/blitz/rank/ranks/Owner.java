package net.blitzsg.blitz.rank.ranks;

import net.blitzsg.blitz.rank.Rank;
import org.bukkit.ChatColor;

public class Owner extends Rank {
    public Owner(){
    super("Owner",ChatColor.RED + "[OWNER] ", ChatColor.RED + "", 5,10);
    }
}
