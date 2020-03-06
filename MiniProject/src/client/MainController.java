package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MainController {
	Socket so;
	BufferedReader br;
	PrintWriter pw;
	Member mb;
	boolean first, second, hos, shop;
	WebEngine engine;
	DLOGModel dm;
	
	@FXML WebView web;
	@FXML Text msg;
	@FXML Button logout;
	@FXML MenuItem sns, chat, hospital, petshop;
	@FXML Pane pane;
	@FXML AnchorPane snspane, chatpane, hospane, shoppane;
	
	@FXML private void sns(ActionEvent e) throws IOException{
		first = e.isConsumed();
		if(first == false) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
			snspane = loader.load();
			DLOGController dc = loader.getController();
			dc.dm = this.dm;
			dc.mb = this.mb;
			dc.so = this.so;
			
			pane.getChildren().clear();
			pane.getChildren().add(snspane);
			first = true;
			
			TableView<DLOGMember> tv = (TableView<DLOGMember>)snspane.getChildren().get(1);
			tv.setItems(dm.ol);
		}
	}
	
	@FXML private void chat(ActionEvent e) throws IOException {
		second = e.isConsumed();
		if(second == false) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
			chatpane = loader.load();
			pane.getChildren().clear();
			pane.getChildren().add(chatpane);
			second = true;
			
			chatController cc = loader.getController();
			cc.member = mb;
			cc.start();
			System.out.println("채팅 접속");
			
			cc.chat.setCellValueFactory(new PropertyValueFactory<ChatText, HBox>("HBox"));
			cc.table.setItems(cc.chatlist);
		}
	}
	
	@FXML private void hospital(ActionEvent e) throws IOException {
		
		hos = e.isConsumed();
		if(hos == false) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("hospital.fxml"));
			web = loader.load();
			engine = web.getEngine();
			pane.getChildren().clear();
			pane.getChildren().add(web);
			engine.load("https://m.map.naver.com/search2/search.nhn?query=%EB%8F%99%EB%AC%BC%EB%B3%91%EC%9B%90&sm=hty&style=v4&centerCoord=35.1648:129.1483");
			hos = true;
		}
	}
	
	@FXML private void petshop(ActionEvent e) throws IOException {
		shop = e.isConsumed();
		if(shop == false) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("petshop.fxml"));
			web = loader.load();
			engine = web.getEngine();
			pane.getChildren().clear();
			pane.getChildren().add(web);
			engine.load("https://m.map.naver.com/search2/search.nhn?query=%ED%8E%AB%EC%9A%A9%ED%92%88%EC%A0%90&sm=hty&style=v4");

			shop = true;
		}
	}
	
	public void logout() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("로그아웃");
			alert.setHeaderText("로그아웃 하시겠습니까?");
			alert.showAndWait();
			so.close();
			
			Stage join = (Stage)logout.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = loader.load();
			join.setScene(new Scene(root));
			join.setTitle("로그인");
			join.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


