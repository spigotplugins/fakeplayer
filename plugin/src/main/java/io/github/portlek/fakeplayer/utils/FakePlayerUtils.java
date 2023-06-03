package io.github.portlek.fakeplayer.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.portlek.fakeplayer.FakePlayerPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class FakePlayerUtils {
    public static void checkVersion(CommandSender sender){
        String version = FakePlayerPlugin.INSTANCE.getDescription().getVersion();
        try {
            URL uri = new URL("https://api.github.com/repos/spigotplugins/fakeplayer/releases/latest");
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setConnectTimeout(2000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
                reader.close();
                JsonObject jsonObject = JsonParser.parseString(responseBody.toString()).getAsJsonObject();
                String latest = jsonObject.get("name").getAsString();
                if (latest != null && !latest.isBlank()){
                    FakePlayerPlugin.messageHandler.sendMessage(sender, "Version.New", latest);
                }else if (Objects.equals(latest, version)){
                    FakePlayerPlugin.messageHandler.sendMessage(sender,"Version.Latest");
                }else {
                    FakePlayerPlugin.messageHandler.sendMessage(sender,"Version.CheckFailed");
                }
            }else {
                FakePlayerPlugin.messageHandler.sendMessage(sender,"Version.CheckFailed");
            }
        }catch (Exception e){
            FakePlayerPlugin.messageHandler.sendMessage(sender,"Version.CheckFailed");
        }
    }

    public static String generateRandomName() {
        FileConfiguration configuration = FakePlayerPlugin.INSTANCE.getConfig();
        int length = configuration.getInt("FakePlayer.random-name.length", 5);
        StringBuilder randomSb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean num = configuration.getBoolean("FakePlayers.random-name.contains-number");
            boolean uppercase = configuration.getBoolean("FakePlayers.random-name.contains-uppercase");
            String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            if (!num) {
                characters = characters.replaceAll("[0-9]", "");
            }
            if (!uppercase) {
                characters = characters.replaceAll("[A-Z]", "");
            }
            char[] chars = characters.toCharArray();
            int index = random.nextInt(chars.length);
            randomSb.append(chars[index]);
        }
        return randomSb.toString();
    }
}
