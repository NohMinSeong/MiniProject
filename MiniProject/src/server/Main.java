package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	static Controller con;
	static Model model;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("server.fxml"));
		// loader ��ü ����鼭 controller ��ü�� ��������� controller ������ ����Ǹ鼭 model ����
		Parent root = loader.load();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Server");
		primaryStage.show();
			
		con = loader.getController();
	}
	public static void main(String[] args) {
		launch(args);
	}

}