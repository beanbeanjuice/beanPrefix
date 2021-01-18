package beanbeanjuice.beanprefix.managers.hooks;

import beanbeanjuice.beanprefix.BeanPrefix;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Chat chat = null;

    public static boolean setupChat(BeanPrefix plugin) {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public static Chat getChat() {
        return chat;
    }
}
