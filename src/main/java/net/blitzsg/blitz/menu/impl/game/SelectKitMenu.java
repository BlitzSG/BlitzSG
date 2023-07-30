package net.blitzsg.blitz.menu.impl.game;

import net.blitzsg.blitz.BlitzSG;
import net.blitzsg.blitz.menu.MenuContainer;
import net.blitzsg.blitz.menu.MenuItem;
import net.blitzsg.blitz.player.IPlayer;
import net.blitzsg.blitz.kit.Kit;
import net.blitzsg.blitz.util.ChatUtil;
import net.blitzsg.blitz.util.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static net.blitzsg.blitz.menu.impl.shop.KitShopMenu.addAllItemFlags;

public class SelectKitMenu {

    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        MenuContainer gui = new MenuContainer("§8Kit Selector", 3);
        ArrayList<Kit> kits = BlitzSG.getInstance().getKitManager().getKits();
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (index >= kits.size()) {
                    break;
                }
                Kit kit = kits.get(index);
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatUtil.color("&7" + kit.getDescription()));
//                ItemStack icon = new ItemBuilder(kit.getIcon()).name((iPlayer.getKitLevel(kit) == 0 && !iPlayer.getGame().isGodGame() ? "&c" : "&a") + kit.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(kit))).lores(lore).make();
                ItemStack icon = new ItemBuilder(kit.getIcon()).name((iPlayer.getKitLevel(kit) == 0 && !iPlayer.getGame().isGodGame() ? "&c" : "&a") + kit.getName()).lores(lore).make();
                addAllItemFlags(icon);
                MenuItem item = new MenuItem(icon, e -> {
                    if (!iPlayer.isInGame()) {
                        return;
                    }

                    if (iPlayer.getKitLevel(kit) == 0 && !iPlayer.getGame().isGodGame()) {
                        if (!(kit.getPrice(0) == 0) && !((kit.getPrice(0) == 125000) && iPlayer.getRank().getMultiplier() >= 2) && !((kit.getPrice(0) == 250000) && iPlayer.getRank().getMultiplier() >= 3)) {
                            p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cYou don't have this kit!"));
                            p.closeInventory();
                            return;
                        }
                    }
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&eYou have chosen the &a" + kit.getName() + " &ekit, You will get your items 60 seconds after the game starts."));
                    iPlayer.setSelectedKit(kit);
                    p.closeInventory();

                });
                gui.setItem(row * 9 + col, item);
                index++;
            }
        }
        gui.show(p);
    }
}
