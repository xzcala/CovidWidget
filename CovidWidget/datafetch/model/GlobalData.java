package CovidWidget.datafetch.model;

//class that saves global data fetched
public class GlobalData {
	//fields for the global data
	private long recovered;
	private long cases;
	private long deaths;
	
	//getters and setters for all fields
	public long getRecovered() {
		return recovered;
	}
	public void setRecovered(long recovered) {
		this.recovered = recovered;
	}
	public long getCases() {
		return cases;
	}
	public void setCases(long cases) {
		this.cases = cases;
	}
	public long getDeaths() {
		return deaths;
	}
	public void setDeaths(long deaths) {
		this.deaths = deaths;
	}
	
	@Override
	public String toString() {
		return "GlobalData [recovered=" + recovered + ", cases=" + cases + ", deaths=" + deaths + "]";
	}
}
