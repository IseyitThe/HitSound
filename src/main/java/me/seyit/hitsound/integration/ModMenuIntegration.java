package me.seyit.hitsound.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import me.seyit.hitsound.config.ConfigHandler;
import me.seyit.hitsound.util.FileManager;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import net.minecraft.text.Text;

import java.util.List;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("HitSound Config"));

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startTextDescription(Text.literal("HitSound Configuration")).build());

            DropdownMenuBuilder<String> dropdownMenu = entryBuilder
                    .startDropdownMenu(Text.literal("Select Hit Sound"), DropdownMenuBuilder.TopCellElementBuilder.of(ConfigHandler.configData.selectedSound, this::getSoundFile))
                    .setDefaultValue("default.ogg")
                    .setSelections(getSoundFiles())
                    .setSuggestionMode(false)
                    .setSaveConsumer(newValue -> ConfigHandler.configData.selectedSound = newValue);

            general.addEntry(dropdownMenu.build());

            IntSliderBuilder volumeSlider = entryBuilder.startIntSlider(Text.literal("Volume"), (int) (ConfigHandler.configData.volume * 100), 0, 100)
                    .setDefaultValue(100)
                    .setSaveConsumer(newValue -> ConfigHandler.configData.volume = newValue / 100.0f);

            general.addEntry(volumeSlider.build());

            builder.setSavingRunnable(ConfigHandler::saveConfig);

            return builder.build();
        };
    }

    private String getSoundFile(String fileName) {
        List<String> soundFiles = getSoundFiles();
        for (String soundFile : soundFiles) {
            if (soundFile.equals(fileName)) {
                return soundFile;
            }
        }
        return "default.ogg";
    }

    private List<String> getSoundFiles() {
        return FileManager.getSoundFiles();
    }
}
