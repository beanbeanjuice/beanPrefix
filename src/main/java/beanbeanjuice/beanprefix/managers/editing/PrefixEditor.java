package beanbeanjuice.beanprefix.managers.editing;

import beanbeanjuice.beanprefix.BeanPrefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PrefixEditor {

    private static ArrayList<EditingPlayer> editingPlayersArrayList;

    public PrefixEditor() {
        setupPrefixEditor();
    }

    public static void setupPrefixEditor() {
        editingPlayersArrayList = new ArrayList<>();
    }

    public static boolean isEditing(Player player) {
        for (EditingPlayer ep : editingPlayersArrayList) {
            if (ep.getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    public static void addPlayer(Player player, String guipath, BeanPrefix plugin) {
        EditingPlayer p = new EditingPlayer(player, guipath);
        editingPlayersArrayList.add(new EditingPlayer(player, guipath));
        p.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, p, 20L, 20L));
    }

    public static void removePlayer(Player player) {

        for (EditingPlayer ep : editingPlayersArrayList) {
            if (ep.getPlayer() == player) {
                Bukkit.getScheduler().cancelTask(ep.getID());
                editingPlayersArrayList.remove(ep);
            }
        }
    }

    public static EditingPlayer getEditingPlayer(Player player) {
        for (EditingPlayer ep: editingPlayersArrayList) {
            if (ep.getPlayer() == player) {
                return ep;
            }
        }
        return null;
    }
}
