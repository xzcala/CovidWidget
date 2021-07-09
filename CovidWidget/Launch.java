package CovidWidget;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launch extends Application {

	//mouse variables
	private double xOffset;
	private double yOffset;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		//removes menu bar at top
		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		//load fxml file
		Parent root = FXMLLoader.load(getClass().getResource("/CovidWidget/gui/widget.fxml"));
		
		//set scene
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//align to top right
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX(visualBounds.getMaxX() - 20 - scene.getWidth());
		primaryStage.setY(visualBounds.getMinY() + 20);
		
		//drag and drop
		scene.setOnMousePressed(event -> {
			xOffset = primaryStage.getX() - event.getScreenX();
			yOffset = primaryStage.getY() - event.getScreenY();
		});
		scene.setOnMouseDragged(event -> {
			primaryStage.setX(event.getScreenX() + xOffset);
			primaryStage.setY(event.getScreenY() + yOffset);
		});
	}
}
