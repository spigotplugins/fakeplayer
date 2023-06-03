package io.github.portlek.fakeplayer.utils;

import io.github.portlek.fakeplayer.FakePlayerPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Random;

public class FakePlayerRandomNameGenerator {
    private static final char[] chars1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final char[] chars2 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] chars3 = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final char[] chars4 = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static String generate() {
        FileConfiguration configuration = FakePlayerPlugin.INSTANCE.getConfig();
        int length = configuration.getInt("FakePlayer.random-name.length", 5);
        StringBuilder randomSb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean num = configuration.getBoolean("FakePlayers.random-name.contains-number");
            boolean uppercase = configuration.getBoolean("FakePlayers.random-name.contains-uppercase");
            char[] chars = num ? uppercase ? chars1 : chars3 : uppercase ? chars2 : chars4;
            int index = random.nextInt(chars.length);
            randomSb.append(chars[index]);
        }
        return randomSb.toString();
    }
}
