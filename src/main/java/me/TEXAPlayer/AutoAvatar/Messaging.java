package me.TEXAPlayer.AutoAvatar;

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

    public void SendMessage(ProxiedPlayer player, String channel)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(player.getDisplayName());
        player.getServer().getInfo().sendData(channel, out.toByteArray());
    }

}
