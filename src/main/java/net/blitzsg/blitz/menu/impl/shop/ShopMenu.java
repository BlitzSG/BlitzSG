package net.blitzsg.blitz.menu.impl.shop;

import net.blitzsg.blitz.menu.MenuContainer;
import net.blitzsg.blitz.menu.MenuItem;
import net.blitzsg.blitz.menu.impl.cosmetics.SelectAuraMenu;
import net.blitzsg.blitz.menu.impl.cosmetics.SelectTauntMenu;
import net.blitzsg.blitz.util.ChatUtil;
import net.blitzsg.blitz.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopMenu {

    public static void openGUI(Player p) {
        MenuContainer gui = new MenuContainer(ChatUtil.color("&8Blitz Shop"), 6);
        MenuItem basic = new MenuItem(new ItemBuilder(new ItemStack(Material.IRON_INGOT)).name("&aKit Upgrades").lore("&7Unlock and upgrade your").lore("&7kits.").make(), e -> KitShopMenu.openGUI(p, true));
        MenuItem advanced = new MenuItem(new ItemBuilder(new ItemStack(Material.GOLD_INGOT)).name("&aAdvanced Kit Upgrades").lore("&7Unlock and upgrade your").lore("&7kits.").make(), e -> KitShopMenu.openGUI(p, false));
        MenuItem stars = new MenuItem(new ItemBuilder(new ItemStack(Material.NETHER_STAR)).name("&aBlitz Powerups").lore("&7Unlock Blitz powerups to").lore("&7change the gameplay.").make(), e -> StarShopMenu.openGUI(p));
        MenuItem customizer = new MenuItem(new ItemBuilder(new ItemStack(Material.ANVIL)).name("&aKit Customizer").lore("&7Customize your kits to optimize").lore("&7your performance.").make(), e -> p.sendMessage(ChatColor.RED + "Coming soon!"));
        MenuItem taunts = new MenuItem(new ItemBuilder(new ItemStack(Material.MAGMA_CREAM)).name("&aTaunts").lore("&7Unlock and customize the Taunt").lore("&7ability.").make(), e -> SelectTauntMenu.openGUI(p));
        MenuItem auras = new MenuItem(new ItemBuilder(new ItemStack(Material.BLAZE_POWDER)).name("&aAuras").lore("&7Cosmetic particle auras that make you").lore("&7stand out!").make(), e -> SelectAuraMenu.openGUI(p));
        MenuItem victory = new MenuItem(new ItemBuilder(new ItemStack(Material.CAKE)).name("&aVictory Dances").lore("&7Nothing said winner like doing").lore("&7the sprinkler.").make(), e -> p.sendMessage(ChatColor.RED + "Coming soon!"));
        MenuItem finishers = new MenuItem(new ItemBuilder(new ItemStack(Material.SKULL_ITEM)).name("&aFinishers").lore("&7Unlock unique Finishers to").lore("&7humiliate your opponents.").make(), e -> p.sendMessage(ChatColor.RED + "Coming soon!"));

        MenuItem close = new MenuItem(new ItemBuilder(new ItemStack(Material.REDSTONE_BLOCK)).name("&cClose").make(), e -> p.closeInventory());

        gui.setItem(11, basic);
        gui.setItem(13, advanced);
        gui.setItem(15, customizer);
        gui.setItem(28, taunts);
        gui.setItem(30, auras);
        gui.setItem(32, victory);
        gui.setItem(34, finishers);
        gui.setItem(49, stars);

        gui.show(p);
    }
}
