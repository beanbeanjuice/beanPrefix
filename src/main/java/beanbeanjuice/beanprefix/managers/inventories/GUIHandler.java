package beanbeanjuice.beanprefix.managers.inventories;

import beanbeanjuice.beanprefix.BeanPrefix;
import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.creating.PrefixCreator;
import beanbeanjuice.beanprefix.managers.editing.PrefixEditor;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.files.Prefixes;
import beanbeanjuice.beanprefix.managers.hooks.Vault;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIHandler implements Listener {

    BeanPrefix plugin;
    public GUIHandler(BeanPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(GeneralHelper.translateColors(plugin.getConfig().getString("prefix")))) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }
            for (String string : Prefixes.getConfig().getKeys(false)) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralHelper.translateColors(Prefixes.getConfig().getString(string + ".gui-name")))) {
                    player.closeInventory();
                    player.openInventory(new PrefixGUI(player, string, 1).getGUI());
                }
            }
        }

        for (String string : Prefixes.getConfig().getKeys(false)) {
            if (event.getView().getTitle().equals(GeneralHelper.translateColors(Prefixes.getConfig().getString(string + ".gui-name")))) {
                event.setCancelled(true);

                if (event.getCurrentItem() == null) {
                    return;
                }

                for (String prefixes : Prefixes.getConfig().getConfigurationSection(string + ".prefixes").getKeys(false)) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralHelper.translateColors(Prefixes.getConfig().getString(string + ".prefixes." + prefixes)))) {
                        if (event.getClick() == ClickType.LEFT) {
                            Vault.getChat().setPlayerPrefix(player, event.getCurrentItem().getItemMeta().getDisplayName());
                            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("successful-change").replace("{prefix-converted}", event.getCurrentItem().getItemMeta().getDisplayName())));
                            player.closeInventory();
                        }
                        if (event.getClick() == ClickType.RIGHT) {
                            if (player.hasPermission("beanprefix." + prefixes + ".edit") || player.hasPermission("beanprefix.*")) {
                                player.closeInventory();
                                player.openInventory(new EditGUI(player, event.getCurrentItem().getItemMeta().getDisplayName()).getGUI());
                            }
                        }
                    }

                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralHelper.translateColors(Messages.getConfig().getString("add")))) {
                        player.closeInventory();
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("add-prefix-path")));
                        PrefixCreator.addPlayer(player, string, plugin);
                        break;
                    }
                }
            }

            for (String prefixSection : Prefixes.getConfig().getConfigurationSection(string + ".prefixes").getKeys(false)) {
                if (event.getView().getTitle().equals(GeneralHelper.translateColors(Prefixes.getConfig().getString(string + ".prefixes." + prefixSection)))) {
                    event.setCancelled(true);

                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralHelper.translateColors(Messages.getConfig().getString("edit")))) {
                        player.closeInventory();
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("edit-ask")));
                        String guipath = (string + ".prefixes." + prefixSection);
                        PrefixEditor.addPlayer(player, guipath, plugin);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralHelper.translateColors(Messages.getConfig().getString("remove")))) {
                        player.closeInventory();
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("remove-success").replace("{prefix}", event.getView().getTitle())));
                        Prefixes.getConfig().set(string + ".prefixes." + prefixSection, null);
                        Prefixes.save();
                        player.openInventory(new MainGUI(player, plugin).getGUI());
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(GeneralHelper.translateColors(Messages.getConfig().getString("cancel")))) {
                        player.closeInventory();
                        player.openInventory(new MainGUI(player, plugin).getGUI());
                    }
                }
            }
        }
    }
}
