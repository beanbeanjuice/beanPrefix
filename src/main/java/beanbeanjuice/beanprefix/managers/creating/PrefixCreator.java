package beanbeanjuice.beanprefix.managers.creating;

import beanbeanjuice.beanprefix.BeanPrefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PrefixCreator {

    private static ArrayList<CreatingPlayer> creatingPlayersArrayList;

    public PrefixCreator() {
        setupPrefixCreator();
    }

    public static void setupPrefixCreator() {
        creatingPlayersArrayList = new ArrayList<>();
    }

    public static boolean isCreating(Player player) {
        for (CreatingPlayer cp : creatingPlayersArrayList) {
            if (cp.getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    public static void addPlayer(Player player, String guiPath, BeanPrefix plugin) {
        CreatingPlayer p = new CreatingPlayer(player, guiPath);
        creatingPlayersArrayList.add(new CreatingPlayer(player, guiPath));
        p.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, p, 20L, 20L));
    }

    public static void removePlayer(Player player) {

        for (CreatingPlayer cp : creatingPlayersArrayList) {
            if (cp.getPlayer() == player) {
                Bukkit.getScheduler().cancelTask(cp.getID());
                creatingPlayersArrayList.remove(cp);
            }
        }
    }

    public static CreatingPlayer getCreatingPlayer(Player player) {
        for (CreatingPlayer cp: creatingPlayersArrayList) {
            if (cp.getPlayer() == player) {
                return cp;
            }
        }
        return null;
    }

}
