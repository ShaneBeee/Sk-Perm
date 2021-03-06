package tk.shanebee.skperm.utils.api;

import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;
import me.TechsCode.UltraPermissions.storage.objects.Permission;
import me.TechsCode.UltraPermissions.storage.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class UltraAPI implements API {

    /**
     * Load API
     */
    private UltraPermissionsAPI api = UltraPermissions.getAPI();

    public void addPerm(OfflinePlayer player, String permission) { // TODO come back to this
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().has(permission)) return;
        user.newPermission(permission).create();
    }

    public void addPerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().worlds(true,world.getName()).has(permission)) {
            return;
        }
        user.newPermission(permission).setWorld(world.getName()).create();
    }

    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().worlds(true, world.getName()).has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission).setWorld(world.getName()).setExpiration(time.getTime()).create();
    }

    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission).setExpiration(time.getTime()).create();
    }

    public void addPerm(String group, String permission) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().has(permission)) return;
        groupT.newPermission(permission).create();
    }

    public void addPerm(String group, String permission, World world) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().worlds(true, world.getName()).has(permission)) return;
        groupT.newPermission(permission).setWorld(world.getName()).create();
    }

    public void addPerm(String group, String permission, World world, int seconds) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().worlds(true, world.getName()).has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        groupT.newPermission(permission).setWorld(world.getName()).setExpiration(time.getTime()).create();
    }

    public void addPerm(String group, String permission, int seconds) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        groupT.newPermission(permission).setExpiration(time.getTime()).create();
    }

    public void removePerm(OfflinePlayer player, String permission) {
        User user = api.getUsers().uuid(player.getUniqueId());
        for (Permission perm : user.getPermissions().get()) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }

    public void removePerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId());
        for (Permission perm : user.getPermissions().worlds(true, world.getName()).get()) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }

    public void removePerm(String group, String permission) {
        Group groupT = api.getGroups().name(group);
        for (Permission perm : groupT.getPermissions().get()) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }

    public void removePerm(String group, String permission, World world) {
        Group groupT = api.getGroups().name(group);
        for (Permission perm : groupT.getPermissions().worlds(true, world.getName()).get()) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }

    public String[] getPerm(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId());
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : user.getPermissions().get()) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }

    public String[] getPerm(OfflinePlayer player, World world) {
        User user = api.getUsers().uuid(player.getUniqueId());
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : user.getPermissions().worlds(true, world.getName()).get()) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }

    public String[] getPerm(String group) {
        Group groupT = api.getGroups().name(group);
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : groupT.getPermissions().get()) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }

    public String[] getPerm(String group, World world) {
        Group groupT = api.getGroups().name(group);
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : groupT.getPermissions().worlds(true, world.getName()).get()) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }

    public void createGroup(String group) {
        if (api.getGroups().name(group) == null) {
            api.newGroup(group).create();
        }
    }

    public void createGroup(String group, String[] parents) {
        if (api.getGroups().name(group) == null) {
            api.newGroup(group).create();
            Group groupT = api.getGroups().name(group);
            for (String par : parents) {
                Group parent = api.getGroups().name(par);
                groupT.addGroup(parent);
            }
            groupT.save();
        }
    }

    public void removeGroup(String group) {
        api.getGroups().name(group).remove();
    }

    public void addPlayerToGroup(OfflinePlayer player, String group) {
        User user = api.getUsers().uuid(player.getUniqueId());
        Group groupT = api.getGroups().name(group);
        if (groupT == null) return;
        user.addGroup(groupT);
        user.save();
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        // TODO NOT SUPPORTED
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        // TODO NOT SUPPORTED
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId());
        Group groupT = api.getGroups().name(group);
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.addGroup(groupT, time.getTime());
        user.save();
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        User user = api.getUsers().uuid(player.getUniqueId());
        Group groupT = api.getGroups().name(group);
        if (groupT == null) return;
        user.removeGroup(groupT);
        user.save();
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        // TODO NOT SUPPORTED
    }

    public OfflinePlayer[] getPlayersInGroup(String group) {
        User user;
        ArrayList<OfflinePlayer> list = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            user = api.getUsers().uuid(player.getUniqueId());
            if (user == null) continue;
            if (user.getGroups().name(group) != null) {
                list.add(player);
            }
        }
        return list.toArray(new OfflinePlayer[0]);
    }

    public void setGroupWeight(String group, int weight) {
        Group groupT = api.getGroups().name(group);
        groupT.setPriority(weight);
        groupT.save();
    }

    public void setGroupRank(String group, int rank) {
        // NOT SUPPORTED
    }

    public int getGroupWeight(String group) {
        Group groupT = api.getGroups().name(group);
        return groupT.getPriority();
    }

    public int getGroupRank(String group) {
        return 0;
        // NOT SUPPORTED
    }

    public void setGroupPrefix(String group, String prefix) {
        Group groupT = api.getGroups().name(group);
        groupT.setPrefix(prefix);
        groupT.save();
    }

    public void setGroupPrefix(String group, String prefix, World world) {
        Group groupT = api.getGroups().name(group);
        groupT.setPrefix(prefix);
        groupT.save();
        // WORLD NOT SUPPORTED
    }

    public String getGroupPrefix(String group) {
        return api.getGroups().name(group).getPrefix();
    }

    public String getGroupPrefix(String group, World world) {
        return api.getGroups().name(group).getPrefix();
        // WORLDS NOT SUPPORTED
    }

    public void setGroupSuffix(String group, String suffix) {
        Group groupT = api.getGroups().name(group);
        groupT.setSuffix(suffix);
        groupT.save();
    }

    public void setGroupSuffix(String group, String suffix, World world) {
        Group groupT = api.getGroups().name(group);
        groupT.setSuffix(suffix);
        groupT.save();
        // WORLDS NOT SUPPORTED
    }

    public String getGroupSuffix(String group) {
        return api.getGroups().name(group).getSuffix();
    }

    public String getGroupSuffix(String group, World world) {
        return api.getGroups().name(group).getSuffix();
        // WORLDS NOT SUPPORTED
    }

    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        User user = api.getUsers().uuid(player.getUniqueId());
        user.setPrefix(prefix);
        user.save();
    }

    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        User user = api.getUsers().uuid(player.getUniqueId());
        user.setPrefix(prefix);
        user.save();
    }

    public String getPlayerPrefix(OfflinePlayer player) {
        return api.getUsers().uuid(player.getUniqueId()).getPrefix();
    }

    public String getPlayerPrefix(OfflinePlayer player, World world) {
        return api.getUsers().uuid(player.getUniqueId()).getPrefix();
        // Does not support worlds
    }

    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        User user = api.getUsers().uuid(player.getUniqueId());
        user.setSuffix(suffix);
        user.save();
    }

    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        User user = api.getUsers().uuid(player.getUniqueId());
        user.setSuffix(suffix);
        user.save();
        // Does not support worlds
    }

    public String getPlayerSuffix(OfflinePlayer player) {
        return api.getUsers().uuid(player.getUniqueId()).getSuffix();
    }

    public String getPlayerSuffix(OfflinePlayer player, World world) {
        return api.getUsers().uuid(player.getUniqueId()).getSuffix();
        // Does not support worlds
    }

}
