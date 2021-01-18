package beanbeanjuice.beanprefix.managers.inventories;

import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.files.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PrefixGUI {

    private Inventory GUI;
    private String guiPath;
    private int page;
    private boolean needMore = false;

    public PrefixGUI(Player player, String guiPath, int page) {
        this.guiPath = guiPath;
        this.page = page;
        GUI = createGUI(player);
        addItems(player, GUI);
    }

    Inventory createGUI(Player player) {
        Set<String> prefixes = Prefixes.getConfig().getConfigurationSection(guiPath + ".prefixes").getKeys(false);
        int size = getSize(player, prefixes);
        if (size == 56) {
            size = 54;
            needMore = true;
        } else if (!player.hasPermission("beanprefix.create") && !player.hasPermission("beanprefix.*")) {
            size = size-9;
        }
        return Bukkit.createInventory(player, size, GeneralHelper.translateColors(Prefixes.getConfig().getString(guiPath + ".gui-name")));
    }

    int getSize(Player player, Set<String> prefixes) {
        int highestPos = 0;
        for (String prefix : prefixes) {
            if (player.hasPermission("beanprefix." + prefix) || player.hasPermission("beanprefix.*")) {
                highestPos++;
            }
        }

        if (highestPos <= 8) {
            return 18;
        } else if (highestPos <= 17) {
            return 27;
        } else if (highestPos <= 26) {
            return 36;
        } else if (highestPos <= 35) {
            return 45;
        } else if (highestPos <= 44) {
            return 54;
        } else if (highestPos <= 53) {
            return 56;
        } else {
            return -1;
        }
    }

    void addItems(Player player, Inventory GUI) {
        Set<String> prefixes = Prefixes.getConfig().getConfigurationSection(guiPath + ".prefixes").getKeys(false);
        if (needMore) {
            int startNum = (page-1) * 44;
            int endCount = startNum + 44;
            int count = 0;
            Set<String> newPrefixes = new HashSet<>();

            for (String string : prefixes) {
                if (count >= startNum && count <= endCount) {
                    newPrefixes.add(string);
                }
                count++;
            }
            prefixes = newPrefixes;

            ItemStack item = new ItemStack(Material.REDSTONE_TORCH, 1);
            ItemMeta item_meta = item.getItemMeta();
            item_meta.setDisplayName(GeneralHelper.translateColors(Messages.getConfig().getString("next")));
            item_meta.addEnchant(Enchantment.DURABILITY, (-1), true);
            ArrayList<String> lore = new ArrayList<>();
            lore.add(GeneralHelper.translateColors(Messages.getConfig().getString("next-lore")));
            item_meta.setLore(lore);
            item.setItemMeta(item_meta);
            GUI.setItem((GUI.getSize() - 3), item);

            ItemStack item2 = new ItemStack(Material.REDSTONE_TORCH, 1);
            ItemMeta item_meta2 = item2.getItemMeta();
            item_meta2.setDisplayName(GeneralHelper.translateColors(Messages.getConfig().getString("previous")));
            item_meta2.addEnchant(Enchantment.DURABILITY, (-1), true);
            ArrayList<String> lore2 = new ArrayList<>();
            lore2.add(GeneralHelper.translateColors(Messages.getConfig().getString("previous-lore")));
            item_meta2.setLore(lore2);
            item2.setItemMeta(item_meta2);
            GUI.setItem((GUI.getSize() - 7), item2);
        }

        for (String prefix : prefixes) {
            if (player.hasPermission("beanprefix." + prefix) || player.hasPermission("beanprefix.*")) {
                ItemStack item = new ItemStack(Material.NAME_TAG, 1);
                ItemMeta item_meta = item.getItemMeta();
                item_meta.setDisplayName(GeneralHelper.translateColors(Prefixes.getConfig().getString(guiPath + ".prefixes." + prefix)));
                item_meta.addEnchant(Enchantment.DURABILITY, (-1), true);
                ArrayList<String> lore = new ArrayList<>();
                lore.add(GeneralHelper.translateColors(Messages.getConfig().getString("left-click")));
                if (player.hasPermission("beanprefix." + prefix + ".edit") || player.hasPermission("beanprefix.*")) {
                    lore.add(GeneralHelper.translateColors(Messages.getConfig().getString("right-click")));
                }
                item_meta.setLore(lore);
                item.setItemMeta(item_meta);
                GUI.addItem(item);
            }
        }

        if (player.hasPermission("beanprefix.create") || player.hasPermission("beanprefix.*")) {
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta item_meta = item.getItemMeta();
            item_meta.setDisplayName(GeneralHelper.translateColors(Messages.getConfig().getString("add")));
            item_meta.addEnchant(Enchantment.DURABILITY, (-1), true);
            ArrayList<String> lore = new ArrayList<>();
            lore.add(GeneralHelper.translateColors(Messages.getConfig().getString("add-lore")));
            item_meta.setLore(lore);
            item.setItemMeta(item_meta);
            GUI.setItem((GUI.getSize() - 5), item);
        }
    }

    public Inventory getGUI() {
        return GUI;
    }

}
