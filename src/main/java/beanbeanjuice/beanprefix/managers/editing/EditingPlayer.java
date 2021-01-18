package beanbeanjuice.beanprefix.managers.editing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EditingPlayer implements Runnable {

    private Player player;
    private String prefixPath;
    private int ID;

    public EditingPlayer(Player player, String prefixPath) {
        this.player = player;
        this.prefixPath = prefixPath;
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

    public String getPrefixPath() {
        return prefixPath;
    }

    @Override
    public void run() {
        if (Bukkit.getPlayer(player.getName()) == null) {
            PrefixEditor.removePlayer(player);
        }
    }
}
