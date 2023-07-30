package net.blitzsg.blitz.menu.impl.cosmetics;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.menu.MenuContainer;
import net.blitzsg.blitz.menu.MenuItem;
import net.blitzsg.blitz.menu.impl.shop.ShopGUI;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.cosmetic.Aura;
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
import java.util.Set;

public class AuraGUI {

    public static int get(int a) {
        if (a >= 1 && a <= 7) {
            return a + 9;
        }
        if (a >= 8 && a <= 14) {
            return a + 11;
        } else return a + 13;
    }

    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        //Add Items

        MenuContainer gui = new MenuContainer(ChatUtil.color("&8Auras"), 6);
        int index = 10;

        Set<Aura> auras = BlitzSG.getInstance().getCosmeticsManager().getAuras();
        for (Aura aura : auras) {
            ItemStack icon = new ItemBuilder(aura.getIcon()).name(ChatColor.GREEN + aura.getName()).lores(getFullDescription(iPlayer, aura)).make();
            MenuItem item = new MenuItem(icon, e -> {
                if (iPlayer.getRank().getPosition() < aura.getRequiredRank().getPosition()) {
                    p.sendMessage("§cYou must be " + aura.getRequiredRank().getRankFormatted() + " §cor higher to purchase that!");
                    return;
                }
                p.sendMessage(ChatColor.GREEN + "You selected " + ChatColor.GOLD + aura.getName());
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                iPlayer.setAura(aura);
                p.closeInventory();
            });
            gui.setItem(index, item);
            if ((index + 2) % 9 == 0) {
                index += 3;
                continue;
            }
            index++;
        }
        MenuItem back = new MenuItem(new ItemBuilder(new ItemStack(Material.ARROW)).name("&aBack").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(48, back);

        MenuItem emerald = new MenuItem(new ItemBuilder(new ItemStack(Material.EMERALD)).name("&7Total Coins: &6" + format(iPlayer.getCoins())).lore("&6http://store.blitzsg.net").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(49, emerald);

        gui.show(p);
    }


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Aura p) {
        ArrayList<String> desc = new ArrayList<String>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (iPlayer.getAura() == p) {
            desc.add("§aSELECTED!");
            return desc;
        }
        desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");

        return desc;
    }

    private static String format(int i) {
        return NumberFormat.getNumberInstance(Locale.US).format(i);
    }

}
