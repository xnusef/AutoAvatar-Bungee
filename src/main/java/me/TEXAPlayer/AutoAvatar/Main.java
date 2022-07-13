package me.TEXAPlayer.AutoAvatar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class Main extends Plugin
{

    private static Main instance;
    private File configFile;
    public Configuration config;

    public Timer timer;
    public Methods methods;
    public Messaging messaging;
    public ProxyServer server;

    @Override
    public void onEnable() 
    {
        SetInstances();
        ManageFiles();
        EnableCycle();
    }

    public static Main GetInstance() { return instance; }

    private void SetInstances()
    {
        Main.instance = this;
        server = getProxy();
        methods = new Methods();
        timer = new Timer(methods);
        messaging = new Messaging(server);
    }

    private void ManageFiles()
    {
        try {ManageConfig();}
        catch (IOException e) {e.printStackTrace();}
        LoadConfig();
    }

    public void ManageConfig() throws IOException 
    {
        if (!getDataFolder().exists())
           getDataFolder().mkdir();
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) 
        {
            FileOutputStream outputStream = new FileOutputStream(configFile); 
            InputStream in = getResourceAsStream("config.yml");
            in.transferTo(outputStream);
        }
    }
    
    private void LoadConfig()
    {
        try 
        {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    // private void SaveConfig()
    // {
    //     try 
    //     {
    //         ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
    //     } catch (IOException e) 
    //     {
    //         e.printStackTrace();
    //     }
    // }

    private void EnableCycle()
    {
        if (config.getBoolean("enable-cycle"))
            server.getScheduler().schedule(
                this,
                timer.RepetitionCycle(),
                config.getLong("delay"),
                config.getLong("interval"),
                TimeUnit.SECONDS);
    }
}