package beanbeanjuice.beanprefix.managers.creating;

import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.files.Prefixes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CreateChecking implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (PrefixCreator.isCreating(player)) {
            event.setCancelled(true);

            if (event.getMessage().equalsIgnoreCase("cancel")) {
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("cancel-create")));
                PrefixCreator.removePlayer(player);
            } else {
                CreatingPlayer cp = PrefixCreator.getCreatingPlayer(player);
                if (cp.getPrefixPath() == null) {
                    if (Prefixes.getConfig().getString(cp.getGuiPath() + ".prefixes." + event.getMessage()) == null) {
                        cp.setPrefixPath(event.getMessage());
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("add-prefix")));
                    } else {
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("add-found").replace("{prefix-name}", event.getMessage())));
                    }
                } else {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("add-success").replace("{prefix}", GeneralHelper.translateColors(event.getMessage()))));
                    Prefixes.getConfig().set(cp.getGuiPath() + ".prefixes." + cp.getPrefixPath(), event.getMessage());
                    Prefixes.save();
                    PrefixCreator.removePlayer(player);
                }
            }
        }
    }
}
