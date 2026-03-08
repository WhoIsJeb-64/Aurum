package org.whoisjeb.aurum.data;

import org.bukkit.Bukkit;
import org.whoisjeb.aurum.Aurum;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class AurumMessages extends AurumData {
    private final Aurum plugin;
    private final File langFile;
    private static final Logger log = Bukkit.getServer().getLogger();

    public AurumMessages(Aurum plugin, File langFile) {
        super(langFile);
        this.plugin = plugin;
        this.langFile = langFile;
    }

    public void load() {
        try {
            Files.createDirectories(langFile.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!langFile.exists()) {
            initializeNewLangFile();
        }
        super.load();
    }

    private void initializeNewLangFile() {
        String resourcePath = "/messages.yml";
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                log.severe("[Aurum] Failed to find messages.yml!");
                return;
            }
            //Does the actual copying of the resource to the real log file
            Files.copy(inputStream, langFile.toPath());
        } catch (IOException e) {
            log.severe("[Aurum] Failed to initialize new messages.yml!");
            throw new RuntimeException(e);
        }
    }
}
