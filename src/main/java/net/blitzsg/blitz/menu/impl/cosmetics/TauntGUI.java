package net.blitzsg.blitz.menu.impl.cosmetics;


import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.cosmetic.Taunt;
import net.blitzsg.blitz.menu.MenuContainer;
import net.blitzsg.blitz.menu.MenuItem;
import net.blitzsg.blitz.menu.impl.shop.ShopGUI;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.util.ChatUtil;
import net.blitzsg.blitz.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TauntGUI {


    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        MenuContainer gui = new MenuContainer(ChatUtil.color("&8Taunts"), 6);
        int firstItem = 11;

        for (Taunt aura : BlitzSG.getInstance().getCosmeticsManager().getTaunts()) {
            ItemStack icon = new ItemBuilder(aura.getIcon()).name(ChatColor.GOLD + aura.getName()).lores(getFullDescription(iPlayer, aura)).make();
            MenuItem menuItem = new MenuItem(icon, e -> {
                e.setCancelled(true);
                if (iPlayer.isInGame()) {
                    return;
                }
                if (iPlayer.getRank().getPosition() < aura.getRequiredRank().getPosition()) {
                    p.sendMessage("§cYou must be " + aura.getRequiredRank().getRankFormatted() + " §cor higher to use that!");
                    return;
                }
                if (iPlayer.getRank().getPosition() == 0 && aura.getRequiredRank().getPosition() == 0 && iPlayer.getTaunt() == null) {
                    if (iPlayer.getCoins() < 2000) {
                        p.sendMessage("§cYou don't have enough coins to buy this!");
                        return;
                    }
                    iPlayer.removeCoins(2000);
                    iPlayer.setTaunt(aura);
                    p.sendMessage("§aYou unlocked the default taunt!");

                    return;
                }
                p.sendMessage(ChatColor.GREEN + "You selected the " + ChatColor.GOLD + aura.getName() + " Taunt" + ChatColor.GREEN + "!");
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

                iPlayer.setTaunt(aura);
                p.closeInventory();
            });
            if (firstItem == 16 || firstItem == 25 || firstItem == 34) {
                firstItem = firstItem + 4;
            }
            gui.setItem(firstItem, menuItem);
            firstItem++;
        }

        MenuItem back = new MenuItem(new ItemBuilder(new ItemStack(Material.ARROW)).name("&aBack").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(48, back);

        MenuItem emerald = new MenuItem(new ItemBuilder(new ItemStack(Material.EMERALD)).name("&7Total Coins: &6" + format(iPlayer.getCoins())).lore("&6http://store.blitzsg.net").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(49, emerald);

        gui.show(p);
    }


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Taunt p) {
        ArrayList<String> desc = new ArrayList<>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (iPlayer.getTaunt() == p) {
            desc.add("§aSELECTED!");
            return desc;
        } else if (iPlayer.getTaunt() == null && iPlayer.getRank().getPosition() == 0) {
            desc.add("§7Price: §1,000");
            return desc;
        }
        if (p.getRequiredRank().getPosition() == 0)
            desc.add("§7Available for everyone");

        else
            desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");
        return desc;
    }

    private static String format(int i) {
        return NumberFormat.getNumberInstance(Locale.US).format(i);
    }

}
