package beanbeanjuice.beanprefix.commands;

import beanbeanjuice.beanprefix.BeanPrefix;
import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.inventories.MainGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChangePrefix implements CommandExecutor {

    private BeanPrefix plugin;

    public ChangePrefix(BeanPrefix plugin) {
        this.plugin = plugin;
        plugin.getCommand("beanprefix").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("must-be-player")));
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("beanprefix.gui") || player.hasPermission("beanprefix.*")) {
                MainGUI mainGUI = new MainGUI(player, plugin);
                Inventory GUI = mainGUI.getGUI();

                if (GUI == null) {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors("incorrect-setup"));
                }

                player.openInventory(GUI);
                return true;
            } else {
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                return false;
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("beanprefix.reload") || player.hasPermission("beanprefix.*")) {
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("successful-reload")));
                GeneralHelper.reloadConfigs();
                return true;
            } else {
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                return false;
            }
        } else {
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("incorrect-syntax")));
            return false;
        }
    }
}
