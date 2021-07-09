package CovidWidget.datafetch.model;

//data model for fetched data
public class CovidDataModel {
	private GlobalData globalData;
	private CountryData countryData;

	public CovidDataModel(GlobalData globalData, CountryData countryData) {
		//super();
		this.globalData = globalData;
		this.countryData = countryData;
	}

	public GlobalData getGlobalData() {
		return globalData;
	}
	public void setGlobalData(GlobalData globalData) {
		this.globalData = globalData;
	}
	public CountryData getCountryData() {
		return countryData;
	}
	public void setCountryData(CountryData countryData) {
		this.countryData = countryData;
	}
	
	@Override
	public String toString() {
		return "CovidDataModel [globalData=" + globalData + ", countryData=" + countryData + "]";
	}
}
