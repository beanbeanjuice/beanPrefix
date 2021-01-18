package beanbeanjuice.beanprefix.managers.creating;

import beanbeanjuice.beanprefix.managers.editing.PrefixEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.security.acl.Owner;

public class CreatingPlayer implements Runnable {

    private Player player;
    private String prefixPath;
    private String guiPath;
    private String prefix;
    private int ID;

    public CreatingPlayer(Player player, String guiPath) {
        this.player = player;
        this.guiPath = guiPath;
    }

    public Player getPlayer() {
        return player;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefixPath(String prefixPath) {
        this.prefixPath = prefixPath;
    }

    public String getPrefixPath() {
        return prefixPath;
    }

    public String getGuiPath() {
        return guiPath;
    }

    @Override
    public void run() {
        if (Bukkit.getPlayer(player.getName()) == null) {
            PrefixEditor.removePlayer(player);
        }
    }

}
