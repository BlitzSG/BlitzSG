package net.blitzsg.blitz.command.taunt;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TauntCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public TauntCommand() {
        super("taunt", ImmutableList.of("t"), null);


    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

        if(iPlayer.getGame() == null && !player.hasPermission("blitz.admin")){
            player.sendMessage(ChatUtil.color("&cYou must be in a game to use this command!"));
            return;
        }



        BlitzSG.getInstance().getGameManager().taunt(player);





    }
}