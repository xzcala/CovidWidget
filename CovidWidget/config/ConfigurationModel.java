package CovidWidget.config;

//model for configuration file
public class ConfigurationModel {
	private int refreshIntervalSeconds;
	private String countryName;
	private String countryCode;
	
	
	//default values
	public ConfigurationModel() {
		refreshIntervalSeconds = 600;
		countryName = "USA";
		countryCode = "US";
	}
	public int getRefreshIntervalSeconds() {
		return refreshIntervalSeconds;
	}
	public void setRefreshIntervalSeconds(int refreshIntervalSeconds) {
		this.refreshIntervalSeconds = refreshIntervalSeconds;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
