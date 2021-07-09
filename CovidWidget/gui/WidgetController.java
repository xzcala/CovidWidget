package CovidWidget.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import CovidWidget.config.ConfigurationModel;
import CovidWidget.config.ConfigurationService;
import CovidWidget.datafetch.DataProviderService;
import CovidWidget.datafetch.model.CountryData;
import CovidWidget.datafetch.model.CovidDataModel;
import CovidWidget.datafetch.model.GlobalData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Window;

public class WidgetController implements Initializable {
	
	//scheduler to run tasks at specified time
	private ScheduledExecutorService executorService;
	//configuration of app
	private ConfigurationModel configModel;
	
	//elements
	@FXML
	public AnchorPane rootPane;
	@FXML
	public Text countryCodeText;
	@FXML
	public Text globalDataText;
	@FXML
	public Text countryDataText;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//load config
		try {
			configModel = new ConfigurationService().getConfiguration();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeScheduler();
		initializeContextMenu();
		countryCodeText.setText(configModel.getCountryCode());
	}

	//schedule to load data every at 0 secs, then every 600 secs based on config file
	private void initializeScheduler() {
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(this::loadData, 0, configModel.getRefreshIntervalSeconds(), TimeUnit.SECONDS);
	}

	//load the data from dataproviderservice
	private void loadData() {
		DataProviderService dataProviderService = new DataProviderService();
		CovidDataModel covidDataModel = dataProviderService.getData(configModel.getCountryName());
	
		//use a separate thread
		Platform.runLater(() -> {
			inflateData(covidDataModel);
		});
	}
	
	private void inflateData(CovidDataModel covidDataModel) {
		GlobalData globalData = covidDataModel.getGlobalData();
		globalDataText.setText(getFormattedData(globalData.getCases(),globalData.getRecovered(),globalData.getDeaths()));
		CountryData countryData = covidDataModel.getCountryData();
		countryDataText.setText(getFormattedDataCountry(countryData.getCases(),countryData.getRecovered(),countryData.getDeaths()));
		
		readjustStage(globalDataText.getScene().getWindow());
	}
	
	//format the data
	private String getFormattedData(long totalCases, long recoveredCases, long totalDeaths) {
		return String.format("Cases: %-10d | Recovered: %-10d | Deaths: %-8d", totalCases, recoveredCases, totalDeaths);
	}
	private String getFormattedDataCountry(long totalCases, long recoveredCases, long totalDeaths) {
		return String.format("Cases: %-10d  | Recovered: %-10d  | Deaths: %-8d", totalCases, recoveredCases, totalDeaths);
	}
	
	//readjust the screen size
	private void readjustStage(Window stage) {
		stage.sizeToScene();
		
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(visualBounds.getMaxX() - 20 - globalDataText.getScene().getWidth());
		stage.setY(visualBounds.getMinY() + 20);
	}
	
	//button for refreshing and exiting program
	private void initializeContextMenu() {
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setOnAction(event -> {
			System.exit(0);
		});
		
		MenuItem refreshItem = new MenuItem("Refresh");
		refreshItem.setOnAction(event -> {
			executorService.schedule(this::loadData, 0, TimeUnit.SECONDS);
		});
		
		final ContextMenu contextMenu = new ContextMenu(exitItem, refreshItem);
		rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
			if(event.isSecondaryButtonDown()) {
				contextMenu.show(rootPane, event.getScreenX(), event.getScreenY());
			} else {
				if(contextMenu.isShowing()) {
					contextMenu.hide();
				}
			}
		});
	}
}
