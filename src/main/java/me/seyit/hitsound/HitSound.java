package me.seyit.hitsound;

import me.seyit.hitsound.util.FileManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import me.seyit.hitsound.config.ConfigHandler;

public class HitSound implements ModInitializer {
    public static final String MOD_ID = "hitsound";
    public static final Identifier HIT_SOUND_ID = new Identifier(MOD_ID, "default");
    public static SoundEvent HIT_SOUND_EVENT = SoundEvent.of(HIT_SOUND_ID);
    public static SoundManager soundManager;

    @Override
    public void onInitialize() {
        Registry.register(Registries.SOUND_EVENT, HIT_SOUND_ID, HIT_SOUND_EVENT);
        System.out.println("HitSound has been initialized!");

        FileManager.initialize();
        ConfigHandler.loadConfig();

        soundManager = new SoundManager();
        soundManager.registerSounds();
    }
}
