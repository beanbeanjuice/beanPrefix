package beanbeanjuice.beanprefix.managers.inventories;

import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EditGUI {

    private Inventory GUI;
    private String prefix;

    public EditGUI(Player player, String prefix) {
        this.prefix = prefix;
        GUI = createGUI(player);
        addItems(GUI);
    }

    Inventory createGUI(Player player) {
        return Bukkit.createInventory(player, 36, GeneralHelper.translateColors(prefix));
    }

    void addItems(Inventory GUI) {
        ItemStack edit = new ItemStack(Material.PAPER, 1);
        ItemMeta edit_meta = edit.getItemMeta();
        edit_meta.setDisplayName(GeneralHelper.translateColors(Messages.getConfig().getString("edit")));
        edit_meta.addEnchant(Enchantment.DURABILITY, (-1), true);
        ArrayList<String> editlore = new ArrayList<>();
        editlore.add(GeneralHelper.translateColors(Messages.getConfig().getString("edit-lore")));
        edit_meta.setLore(editlore);
        edit.setItemMeta(edit_meta);

        GUI.setItem(12, edit);

        ItemStack remove = new ItemStack(Material.TNT, 1);
        ItemMeta remove_meta = remove.getItemMeta();
        remove_meta.setDisplayName(GeneralHelper.translateColors(Messages.getConfig().getString("remove")));
        remove_meta.addEnchant(Enchantment.DURABILITY, (-1), true);
        ArrayList<String> removelore = new ArrayList<>();
        removelore.add(GeneralHelper.translateColors(Messages.getConfig().getString("remove-lore")));
        remove_meta.setLore(removelore);
        remove.setItemMeta(remove_meta);

        GUI.setItem(14, remove);

        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancel_meta = cancel.getItemMeta();
        cancel_meta.setDisplayName(GeneralHelper.translateColors(Messages.getConfig().getString("cancel")));
        ArrayList<String> cancellore = new ArrayList<>();
        cancellore.add(GeneralHelper.translateColors(Messages.getConfig().getString("cancel-lore")));
        cancel_meta.setLore(cancellore);
        cancel.setItemMeta(cancel_meta);

        GUI.setItem(22, cancel);
    }

    public Inventory getGUI() {
        return GUI;
    }

}
