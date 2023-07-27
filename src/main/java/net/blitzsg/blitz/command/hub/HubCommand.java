package net.blitzsg.blitz.command.hub;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HubCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public HubCommand() {
        super("hub", ImmutableList.of("spawn", "l", "lobby"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.isInGame()) {
            if (iPlayer.getGame().getGameMode() == Game.GameMode.INGAME || iPlayer.getGame().getGameMode() == Game.GameMode.STARTING) {
                if(!iPlayer.isSpectating()){
                    BlitzSG.getInstance().getGameHandler().onPlayerDeath(player, iPlayer.getLastAttacker());
                    iPlayer.getGame().exit(player);
                } else {
                    iPlayer.getGame().exit(player);
                }
            } else if(iPlayer.getGame().getGameMode() == Game.GameMode.WAITING) {
                iPlayer.getGame().removePlayer(player);
            }
        }

        BlitzSG.getInstance().getIPlayerManager().toLobby(player);
    }


    private List<SubCommand> getAvailableSubs(CommandSender sender) {
        return subcommands.stream()
                .filter(c -> sender.hasPermission(c.getPermission()))
                .collect(Collectors.toList());
    }

    private SubCommand getSubCommand(String name) {
        String a = name.toLowerCase();
        return subcommands.stream()
                .filter(sub -> (sub.getName().equals(a) || sub.getAliases().contains(a)))
                .findFirst().orElse(null);
    }
}