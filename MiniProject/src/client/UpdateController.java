package client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateController {
	@FXML TextField image, text;
	@FXML Button updatebtn, canclebtn;
	@FXML AnchorPane snspane, updatepane;
	
	DLOGModel dm;
	DLOGMember dmem;
	Member mb;
	String getImage, getText, nickname;
	DLOGController dc;
	PrintWriter pwr;
	BufferedReader br;
	Socket so;
	int index, likecount;
	
	@FXML void update() {
		
		getImage = image.getText();
		getText = text.getText();
		nickname = "[ " + mb.getPetname() +" ] 이의 일상" ;

		try {
			pwr = new PrintWriter(so.getOutputStream());
			dc.dm.myupdate(nickname, getImage, likecount, getText, dc.index);
		} catch (Exception e) {
		}
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
			snspane = loader.load();
			updatepane.getChildren().clear();
			updatepane.getChildren().add(snspane);
			DLOGController dc = loader.getController();
			dc.dm = this.dm; 
			dc.mb = this.mb;
			dc.table.setItems(dm.ol);
		} catch (Exception e) {
		}
	}
	@FXML void cancle() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
			snspane = loader.load();
			updatepane.getChildren().clear();
			updatepane.getChildren().add(snspane);
			DLOGController dc = loader.getController();
			dc.dm = this.dm;
			dc.mb = this.mb;
		} catch (Exception e) {
		}
	}
}
