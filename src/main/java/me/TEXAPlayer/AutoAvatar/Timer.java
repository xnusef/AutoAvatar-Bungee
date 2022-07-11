package me.TEXAPlayer.AutoAvatar;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Timer
{
    Main main = Main.GetInstance();
    Methods methods;
    List<ProxiedPlayer> playersInAllowedServers = new ArrayList<>();
    //DateTimeFormatter formatter;
    
    public Timer(/*Main plugin, */Methods m)
    {
        methods = m;
        //formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
    }

    public Runnable RepetitionCycle()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                playersInAllowedServers = methods.GetPlayersInServers();
                if (playersInAllowedServers.size() >= main.config.getInt("player-amount"))
                {
                    ProxiedPlayer chosenPlayer = methods.GetRandomPlayer(playersInAllowedServers);
                    if (chosenPlayer != null)
                        main.messaging.SendMessage(chosenPlayer, "autoavatar:newavatar");
                        //main.server.getLogger().info(chosenPlayer.getName());
                }
            }
        };
    }
}
