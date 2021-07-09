package CovidWidget.datafetch;
import java.util.concurrent.CompletableFuture;

import CovidWidget.datafetch.model.CountryData;
import CovidWidget.datafetch.model.CovidDataModel;
import CovidWidget.datafetch.model.GlobalData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//class that uses covidapi interface with retrofit methods to get data
public class DataProviderService {
	//method to get data
	public CovidDataModel getData(String countryName) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://coronavirus-19-api.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		//make new interface into retrofit
		CovidApi covidApi = retrofit.create(CovidApi.class);
		
		//asynchronous callback
		CompletableFuture<GlobalData> callback1 = new CompletableFuture<>();
		
		//create method for getting data
		covidApi.getGlobalData()
			.enqueue(new Callback<GlobalData>() {
				@Override
				public void onResponse(Call<GlobalData> call, Response<GlobalData> response) {
					System.out.println(response.body());
					callback1.complete(response.body());
				}
				
				@Override
				public void onFailure(Call<GlobalData> call, Throwable t) {
					callback1.completeExceptionally(t);
				}
			});
		
		CompletableFuture<CountryData> callback2 = new CompletableFuture<>();
		
		covidApi.getCountryData(countryName)
			.enqueue(new Callback<CountryData>() {
				@Override
				public void onResponse(Call<CountryData> call, Response<CountryData> response) {
					System.out.println(response.body());	
					callback2.complete(response.body());
				}
			
				@Override
				public void onFailure(Call<CountryData> call, Throwable t) {
					callback2.completeExceptionally(t);
				}
			});
		
		GlobalData globalData = callback1.join();
		CountryData countryData = callback2.join();
		
		return new CovidDataModel(globalData, countryData);
	}
}
