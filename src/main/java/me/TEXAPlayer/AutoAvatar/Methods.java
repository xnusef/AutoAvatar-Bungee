package me.TEXAPlayer.AutoAvatar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Methods 
{
    Main main = Main.GetInstance();

    public List<ProxiedPlayer> GetPlayersInServers()
    {
        List<ProxiedPlayer> playersInAllowedServers = new ArrayList<ProxiedPlayer>();
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
            for (String serverName : main.config.getStringList("allowed-servers"))
                if (player.getServer().getInfo().getName().equals(serverName))
                {
                    playersInAllowedServers.add(player);
                    break;
                }
        return playersInAllowedServers;
    }

    public ProxiedPlayer GetRandomPlayer(List<ProxiedPlayer> allowedPlayers)
    {
        List<ProxiedPlayer> weightedPlayers = WeightedList(allowedPlayers);
        if (weightedPlayers.isEmpty())
            return null;
        int index = (int) Math.floor(Math.random() * weightedPlayers.size()); 
        return weightedPlayers.get(index);
    }

    private List<ProxiedPlayer> WeightedList(List<ProxiedPlayer> allowedPlayers)
    {
        List<ProxiedPlayer> playersToRandomize = new ArrayList<ProxiedPlayer>();
        for (ProxiedPlayer player : allowedPlayers)
        {
            if (player.hasPermission("avatar.choosable") && HasPermissionStartingWith(player, "avatar.weight."))
            {
                String number = GetPermissionStartingWith(player, "avatar.weight.").replace("avatar.weight.", "");
                for (int i = 1 ; i <= Integer.parseInt(number); i++)
                    playersToRandomize.add(player);
            }
        }
        return playersToRandomize;
    }

    private boolean HasPermissionStartingWith(ProxiedPlayer player, String match)
    {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(player);
        CachedPermissionData permissionData = user.getCachedData().getPermissionData();
        List<String> list = GetListFromMap(permissionData.getPermissionMap());

        for(String permission : list) // no entra al for
            if(permission.startsWith(match))
                return true;
        return false;
    }

    private String GetPermissionStartingWith(ProxiedPlayer player, String match)
    {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(player);
        CachedPermissionData permissionData = user.getCachedData().getPermissionData();
        List<String> list = GetListFromMap(permissionData.getPermissionMap());

        for(String permission : list)
            if(permission.startsWith(match))
                return permission;
        return null;
    }

    private List<String> GetListFromMap(Map<String, Boolean> map)
    {
        List<String> stringArray = new ArrayList<String>();
        for (String key : map.keySet())
            if (map.get(key) == true)
                stringArray.add(key);
        return stringArray;
    }
}
