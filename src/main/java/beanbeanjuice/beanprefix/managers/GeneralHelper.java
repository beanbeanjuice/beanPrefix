package beanbeanjuice.beanprefix.managers;

import beanbeanjuice.beanprefix.BeanPrefix;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.files.Prefixes;

public class GeneralHelper {

    private static BeanPrefix plugin;

    public GeneralHelper(BeanPrefix plugin) {
        this.plugin = plugin;
    }

    public static String translateColors(String string) {
        return string.replaceAll("&", "§");
    }

    public static String getPrefix() {
        return translateColors(plugin.getConfig().getString("prefix")) + " ";
    }

    public static String getNoPermission() {
        return translateColors(Messages.getConfig().getString("no-permission"));
    }

    public static void reloadConfigs() {
        plugin.reloadConfig();
        Messages.reloadConfig();
        Prefixes.reloadConfig();
    }

}
