package beanbeanjuice.beanprefix;

import beanbeanjuice.beanprefix.commands.ChangePrefix;
import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.creating.CreateChecking;
import beanbeanjuice.beanprefix.managers.creating.PrefixCreator;
import beanbeanjuice.beanprefix.managers.editing.EditChecking;
import beanbeanjuice.beanprefix.managers.editing.PrefixEditor;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.files.Prefixes;
import beanbeanjuice.beanprefix.managers.hooks.Vault;
import beanbeanjuice.beanprefix.managers.inventories.GUIHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanPrefix extends JavaPlugin {

    @Override
    public void onEnable() {
        setupConfigs();
        if (!Vault.setupChat(this)) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        new PrefixEditor();
        new PrefixCreator();
        new GeneralHelper(this);
        new ChangePrefix(this);

        getServer().getPluginManager().registerEvents(new GUIHandler(this), this);
        getServer().getPluginManager().registerEvents(new EditChecking(), this);
        getServer().getPluginManager().registerEvents(new CreateChecking(), this);
        getLogger().info("BeanPrefix.jar has been enabled...");

    }

    private void setupConfigs() {
        saveDefaultConfig();
        Messages.createConfig(this);
        Prefixes.createConfig(this);
    }
}