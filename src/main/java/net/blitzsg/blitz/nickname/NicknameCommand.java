package net.blitzsg.blitz.nickname;

import net.blitzsg.blitz.BlitzSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if ((BlitzSG.getInstance().getRankManager().getRank((Player) sender).isYT())) {
            sender.sendMessage(BlitzSG.CORE_NAME + "You are not allowed to do this.");

            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("unnick") || args[0].equalsIgnoreCase("reset")){
                BlitzSG.send(p, "&aUnnicking");
                new Nickname().unnick(p);
                return true;
            }
            BlitzSG.send(p, "&aChanging your nickname to &e" + args[0]);
            new Nickname().setNick(p, args[0]);
            return true;
        }
        p.sendMessage(BlitzSG.CORE_NAME + "&e");
        return true;
    }
}

