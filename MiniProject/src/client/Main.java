package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	static LoginController lc;
	static MainController md;

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("·Î±×ÀÎ");
		primaryStage.show();
		
		lc = loader.getController();
		
	}	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void stop() {
		try {
			if(lc.pwr != null) lc.pwr.close();
			if(lc.so != null) lc.so.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}