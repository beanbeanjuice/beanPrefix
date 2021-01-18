package beanbeanjuice.beanprefix.managers.editing;

import beanbeanjuice.beanprefix.managers.GeneralHelper;
import beanbeanjuice.beanprefix.managers.files.Messages;
import beanbeanjuice.beanprefix.managers.files.Prefixes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EditChecking implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (PrefixEditor.isEditing(player)) {
            event.setCancelled(true);

            if (event.getMessage().equalsIgnoreCase("cancel")) {
                PrefixEditor.removePlayer(player);
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("cancel-edit")));
            } else {
                if (PrefixEditor.getEditingPlayer(player) != null) {
                    EditingPlayer ep = PrefixEditor.getEditingPlayer(player);
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("edit-success").replace("{old-prefix}", Prefixes.getConfig().getString(ep.getPrefixPath())).replace("{new-prefix}", event.getMessage())));
                    Prefixes.getConfig().set(ep.getPrefixPath(), event.getMessage());
                    Prefixes.save();
                }
                PrefixEditor.removePlayer(player);
            }
        }
    }
}
