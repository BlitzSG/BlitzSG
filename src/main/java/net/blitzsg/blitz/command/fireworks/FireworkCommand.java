package net.blitzsg.blitz.command.fireworks;


import com.google.common.collect.ImmutableList;
import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.command.Command;
import net.blitzsg.blitz.command.SubCommand;
import net.blitzsg.blitz.game.Game;
import net.blitzsg.blitz.util.ChatUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworkCommand extends Command {

    public FireworkCommand() {
        super("fireworks", ImmutableList.of("firework", "fw"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        if (iPlayer.isInGame() && iPlayer.getGame().getGameMode() != Game.GameMode.STARTING) {
            p.sendMessage(ChatUtil.color("&cYou can't do this after the game has started!"));
            return;
        }

        long remainingTime = (iPlayer.getLastFirework() + 10000 - System.currentTimeMillis()) / 1000;
        if (remainingTime > 0) {
            p.sendMessage(ChatUtil.color("&cYou must wait " + remainingTime + " seconds before using this again!"));
            return;
        }

        iPlayer.setLastFirework(System.currentTimeMillis());
        launchFirework(p.getEyeLocation());
    }


    private List<SubCommand> getAvailableSubs(CommandSender sender) {
        return null;
    }

    private SubCommand getSubCommand(String name) {
        return null;
    }

    public void launchFirework(Location location) {
        Firework fw = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        Random random = new Random();
        List<Color> colors = new ArrayList<>();
        colors.add(randomColor());
        if (random.nextBoolean()) {
            colors.add(randomColor());
        }
        if (random.nextBoolean()) {
            colors.add(randomColor());
        }
        FireworkEffect effect = FireworkEffect.builder()
                .withColor(colors)
                .withFade(randomColor())
                .with(randomType())
                .trail(true)
                .withFlicker()
                .build();
        meta.addEffect(effect);
        meta.setPower(1);
        fw.setFireworkMeta(meta);
    }

    public Color[] fireworkEffectColor = new Color[]{Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW};
    public FireworkEffect.Type[] fireworkEffectType = new FireworkEffect.Type[]{FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST, FireworkEffect.Type.CREEPER, FireworkEffect.Type.STAR};

    public FireworkEffect.Type randomType() {
        int rnd = new Random().nextInt(fireworkEffectType.length);
        return fireworkEffectType[rnd];
    }

    public Color randomColor() {
        int rnd = new Random().nextInt(fireworkEffectColor.length);
        return fireworkEffectColor[rnd];
    }
}