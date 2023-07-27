package net.blitzsg.blitz.rank.ranks;

import net.blitzsg.blitz.rank.Rank;
import org.bukkit.ChatColor;

public class Moderator extends Rank {
    public Moderator(){
    super("Moderator",ChatColor.DARK_GREEN + "[MOD] ", ChatColor.DARK_GREEN + "", 5,8);
    }
}
