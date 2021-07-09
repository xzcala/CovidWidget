package CovidWidget.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//service for creating config file
public class ConfigurationService {
	private final File SETTINGS_FILE = new File("settings.json");
	private Gson gson = new GsonBuilder().create();
	
	//create file if first time else read from file
	public ConfigurationModel getConfiguration() throws Exception{
		if(!SETTINGS_FILE.exists()) {
			createSettingsFile();
		}
		return getConfigurationFromSettingsFile();
	}
	
	public void createSettingsFile() throws IOException {
		ConfigurationModel configModel = new ConfigurationModel();
		try (Writer writer = new FileWriter(SETTINGS_FILE, false)) {
			gson.toJson(configModel, writer);
		}
	}
	
	public ConfigurationModel getConfigurationFromSettingsFile() throws IOException {
		try (Reader reader = new FileReader(SETTINGS_FILE)) {
			return gson.fromJson(reader, ConfigurationModel.class);
		}
	}
}
