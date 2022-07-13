package me.TEXAPlayer.AutoAvatar;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Timer
{
    Main main = Main.GetInstance();
    Methods methods;
    List<ProxiedPlayer> playersInAllowedServers = new ArrayList<>();
    
    public Timer(Methods m)
    {
        methods = m;
    }

    public Runnable RepetitionCycle()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                playersInAllowedServers = methods.GetPlayersInServers();
                ProxiedPlayer chosenPlayer = methods.GetRandomPlayer(playersInAllowedServers);
                if (playersInAllowedServers.size() >= main.config.getInt("player-amount") && chosenPlayer != null)
                    EnoughtPlayers(chosenPlayer);
                else
                    NotEnoughtPlayers();
            }
        };
    }

    private void EnoughtPlayers(ProxiedPlayer chosenPlayer)
    {
        List<String> data = new ArrayList<>();
        data.add("newAvatar");
        data.add(chosenPlayer.getName());
        data.add("noInfo");
        main.server.getLogger().info("sent: newAvatar + " + chosenPlayer.getName() + " + noInfo");
        main.messaging.SendMessage(chosenPlayer, "autoavatar:newavatar", data);
    }

    private void NotEnoughtPlayers()
    {
        List<ProxiedPlayer> players = new ArrayList<>(main.server.getPlayers());
        if (players.isEmpty())
            return;
        List<String> data = new ArrayList<>();
        data.add("null");
        data.add("null");
        data.add("null");
        main.server.getLogger().info("sent: null");
        main.messaging.SendMessage(players.get(0), "autoavatar:newavatar", data);
    }
}
