package nameerror.skillbuilder.Configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * <i>Created on 07.12.2019.</i>
 * Custom configuration class.
 * Based by
 * @author Titov Kirill <thezit445@yandex.ru>
 * @version 2.1.0
 */

public class PluginConfiguration extends YamlConfiguration {
    private final String dirPath;
    private final String fileName;
    private File file;

    public PluginConfiguration(String dirPath, String fileName) {
        this.dirPath = dirPath;
        this.fileName = fileName;

        String path = dirPath + "\\" + fileName + ".yml";
        file = new File(path);

        if (file.exists()) {
            try {
                this.load(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public PluginConfiguration(File file) {
        this.dirPath = file.getParentFile().getPath();
        this.fileName = file.getName();

        if (file.exists()) {
            try {
                this.load(file);
                this.file = file;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                this.file = file;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getConfig() { return this; }

    public void save() {
        try {
            this.save(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean reload() {
        try {
            this.load(file);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDirPath() {
        return dirPath;
    }

    public String getFileName() {
        return fileName;
    }

}