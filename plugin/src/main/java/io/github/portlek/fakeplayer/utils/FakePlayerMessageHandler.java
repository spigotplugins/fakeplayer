package io.github.portlek.fakeplayer.utils;

import io.github.portlek.fakeplayer.FakePlayerPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakePlayerMessageHandler {
    private final YamlConfiguration message;

    public FakePlayerMessageHandler(final FakePlayerPlugin plugin){
        String language = plugin.getConfig().getString("FakePlayer.language","en-us");
        String fileName = "lang/"+language.toLowerCase()+".yml";
        File file = new File(plugin.getDataFolder(),fileName);
        if (!file.exists()) {
            InputStream is = plugin.getResource(fileName);
            if (is != null) {
                plugin.saveResource(fileName,false);
            }else {
                file = new File(plugin.getDataFolder(),"lang/en-us.yml");
            }
        }
        message = YamlConfiguration.loadConfiguration(file);
    }

    public String get(String node){
        return colorize(message.getString(node,"&4Get message '"+node+"' failed, maybe it's not exists."));
    }

    public void sendMessage(CommandSender sender, String node, Object... args) {
        sender.sendMessage(String.format(get(node), args));
    }

    public void sendMessages(CommandSender commandSender, String node){
        List<String> messages = message.getStringList(node);
        for (String message : messages){
            commandSender.sendMessage(colorize(message));
        }
    }

    public String colorize(String string) {
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        for (Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)) {
            String str = string.substring(matcher.start(), matcher.end());
            String color = str.replace("&","");
            string = string.replace(str, net.md_5.bungee.api.ChatColor.of(color)+"");
        }
        string = org.bukkit.ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }


}
