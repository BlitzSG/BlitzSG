package net.blitzsg.blitz.punishments.commands.mute;

import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.punishments.PlayerMute;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MuteCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public MuteCommand() {
        super("mute", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else if (args.length == 2) {
            return ImmutableList.of("1h", "6h", "1d", "7d", "14d", "30d");
        } else if (args.length > 2) {
            return ImmutableList.of("Verbal Abuse", "Flooding Chat", "Advertising", "Toxic Behaviour", "Other");
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        String executor = "Console";
        if (sender instanceof Player) {
            IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if (!(p.getRank().isStaff())) {
                sender.sendMessage(ChatUtil.color("&cYou do not have permission to use this command!"));
                return;
            }
            executor = ((Player) sender).getName();
        }

        if (args.length == 0) {
            help(sender);
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtil.color("&cThat player is not online!"));
            return;
        }
        if (args.length == 1) {
            help(sender);
            return;
        }
        String duration = args[1];
        long futureTime = calculateFutureTime(duration);
        if (futureTime == -1) {
            sender.sendMessage(ChatUtil.color("&cInvalid duration!"));
            return;
        }

        String reason = "No reason specified.";
        if (args.length > 2) {
            reason = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
        }

        String finalReason = reason;
        String finalExecutor = executor;

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            try {
                MongoDatabase database = BlitzSG.getInstance().getDb().getDatabase();
                MongoCollection<Document> collection = database.getCollection("mutes");

                Document muteDoc = new Document("uuid", target.getUniqueId().toString())
                        .append("reason", finalReason)
                        .append("expires", futureTime)
                        .append("executor", finalExecutor);
                collection.insertOne(muteDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
        p.setMute(new PlayerMute(futureTime, finalReason));

        target.sendMessage(ChatUtil.color("&7&m-------------------------------"));
        target.sendMessage(ChatUtil.color("&cYou have been muted for " + duration + " for " + reason + "."));
        target.sendMessage(ChatUtil.color("&7&m-------------------------------"));
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&cUsage: /mute <player> <duration> [<reason>]"));
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


    public long calculateFutureTime(String durationStr) {
        long currentTimeMillis = System.currentTimeMillis();
        long durationInMillis = 0;
        try {
            int duration = Integer.parseInt(durationStr.substring(0, durationStr.length() - 1));
            char unit = durationStr.charAt(durationStr.length() - 1);
            switch (unit) {
                case 's':
                    durationInMillis = duration * 1000L;
                    break;
                case 'm':
                    durationInMillis = duration * 60L * 1000L;
                    break;
                case 'h':
                    durationInMillis = duration * 60L * 60L * 1000L;
                    break;
                case 'd':
                    durationInMillis = duration * 24L * 60L * 60L * 1000L;
                    break;
                default:
                    return -1;
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1;
        }
        return currentTimeMillis + durationInMillis;
    }
}