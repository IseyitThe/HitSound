package me.seyit.hitsound.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.launch.FabricLauncher;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ConfigHandler {

    public static ConfigData configData;
    private static final File CONFIG_FILE = new File("config/hitsound_config.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                configData = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            configData = new ConfigData();
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(configData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSoundFiles() {
        File dir = new File(FabricLoader.getInstance().getGameDir() + "hitsound");
        if (!dir.exists()) {
            dir.mkdirs();

            try {
                File defaultSound = new File(dir, "default.ogg");
                if (!defaultSound.exists()) {
                    defaultSound.createNewFile();

                    try (InputStream in = ConfigHandler.class.getResourceAsStream("/assets/hitsound/sounds/default.ogg");
                         FileOutputStream out = new FileOutputStream(defaultSound)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Arrays.asList(dir.list((dir1, name) -> name.endsWith(".ogg")));
    }

    public static class ConfigData {
        public String selectedSound = "default.ogg";
        public float volume = 1.0F;
    }
}
