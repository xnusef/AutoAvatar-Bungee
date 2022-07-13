package me.TEXAPlayer.AutoAvatar;

import java.util.List;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Messaging
{
    Main main = Main.GetInstance();

    public Messaging(ProxyServer proxy)
    {
        proxy.registerChannel( "autoavatar:newavatar" );
    }

    public void SendMessage(ProxiedPlayer player, String channel, List<String> data)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String info : data)
            out.writeUTF(info);
        player.getServer().getInfo().sendData(channel, out.toByteArray());
    }

}
