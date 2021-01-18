package beanbeanjuice.beanprefix.managers.inventories;

import beanbeanjuice.beanprefix.BeanPrefix;
import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.files.Prefixes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class MainGUI {

    private Inventory GUI;
    private BeanPrefix plugin;

    public MainGUI(Player player, BeanPrefix plugin) {
        this.plugin = plugin;
        GUI = createGUI(player);
        addItems(player, GUI);
    }

    Inventory createGUI(Player player) {
        Set<String> prefixGroups = Prefixes.getConfig().getKeys(false);

        int highestPos = 0;
        for (String string : prefixGroups) {
            if (Prefixes.getConfig().getInt(string + ".gui-position") > highestPos) {
                highestPos = Prefixes.getConfig().getInt(string + ".gui-position");
            }
        }

        if (getSize(highestPos) == -1) {
            return null;
        }

        return Bukkit.createInventory(player, getSize(highestPos), GeneralHelper.translateColors(plugin.getConfig().getString("prefix")));
    }

    int getSize(int highestPos) {
        if (highestPos <= 8) {
            return 9;
        } else if (highestPos <= 17) {
            return 18;
        } else if (highestPos <= 26) {
            return 27;
        } else if (highestPos <= 35) {
            return 36;
        } else if (highestPos <= 44) {
            return 45;
        } else if (highestPos <= 53) {
            return 54;
        } else {
            return -1;
        }
    }

    void addItems(Player player, Inventory GUI) {
        for (String string : Prefixes.getConfig().getKeys(false)) {
            if (player.hasPermission("beanprefix.gui." + string) || player.hasPermission("beanprefix.*")) {
                ItemStack item = new ItemStack(Material.NAME_TAG, 1);
                ItemMeta item_meta = item.getItemMeta();
                item_meta.setDisplayName(GeneralHelper.translateColors(Prefixes.getConfig().getString(string + ".gui-name")));
                item.setItemMeta(item_meta);
                GUI.addItem(item);
            }
        }
    }

    public Inventory getGUI() {
        return GUI;
    }

}
