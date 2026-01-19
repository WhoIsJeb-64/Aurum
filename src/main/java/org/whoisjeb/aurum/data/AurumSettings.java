package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.whoisjeb.aurum.Aurum;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class AurumSettings extends AurumConfig {
    private Aurum plugin;
    private File settingsFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumSettings(Aurum plugin, File settingsFile) {
        super(new File(plugin.getDataFolder(), "config.yml"));
        this.plugin = plugin;
        this.settingsFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public void load() {
        try {
            Files.createDirectories(settingsFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!settingsFile.exists()) {
            initializeDefaultConfig();
        }
        super.load();
    }

    private void initializeDefaultConfig() {
        String resourcePath = "/" + settingsFile.getName();
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.severe("[Aurum] Failed to find configuration file!");
                return;
            }
            //Does the actual copying of the resource to the real config file
            Files.copy(inputStream, settingsFile.toPath());
        } catch (IOException e) {
            log.severe("[Aurum] Failed to initialize default configuration!");
            throw new RuntimeException(e);
        }
    }
}
