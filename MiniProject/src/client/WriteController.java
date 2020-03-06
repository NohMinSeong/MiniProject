package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class WriteController implements Initializable{
	@FXML TextField image, text;
	@FXML Button uploadbtn, canclebtn;
	@FXML AnchorPane snspane, writepane;

	String getImage, getText, nickname;
	int like = 0;
	
	DLOGController dc;
	DLOGModel dm;
	DLOGMember dmem;
	Member mb;
	Socket so;
	PrintWriter pwr;
	BufferedReader br;
	
	
	@FXML private void upload() throws FileNotFoundException{
		System.out.println("(upload)socket : " + so);

		getImage = image.getText();
		getText = text.getText();
		nickname = "[ " + mb.getPetname() +" ] 이의 일상";
		
		try {
			pwr = new PrintWriter(so.getOutputStream());
			pwr.println("sns★" + nickname + "," + getImage +"," +like +"," +getText);
			pwr.flush();
			
		} catch (Exception e) {
			System.out.println("업로드 예외 발생");
			e.printStackTrace();
		}
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
			snspane = loader.load();
			writepane.getChildren().clear();
			writepane.getChildren().add(snspane);
			DLOGController dc = loader.getController();
			dc.dm = this.dm; 
			dc.mb = this.mb;
			dc.so = this.so;
			this.br = dm.br; 
			
			String result = br.readLine().trim();
			
			String[] re = result.split("★");
			if(re[0].equals("sns")) {
				String[] initfal = re[1].split(",");
				int count = Integer.parseInt(initfal[2]);
				dm.ol.clear();
				dm.myadd(initfal[0], initfal[1], count, initfal[3]);
			}
			
			dc.table.setItems(dm.ol);
			
		} catch (Exception e) {
			System.out.println("장면전환");
			e.printStackTrace();
		}
		
	}
	
	@FXML private void cancle() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
			snspane = loader.load();
			writepane.getChildren().clear();
			writepane.getChildren().add(snspane);
			DLOGController dc = loader.getController();
			dc.dm = this.dm; 
			dc.mb = this.mb;
			dc.so = this.so;
		} catch (Exception e) {
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
