package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class chatController extends Thread{
	@FXML TextField textfield;
	@FXML Button sendbtn;
	@FXML TableView<ChatText> table;
	@FXML TableColumn<ChatText, HBox> chat;
	@FXML AnchorPane chatpane;
	
	ObservableList<ChatText> chatlist = FXCollections.observableArrayList();
	Socket so;
	PrintWriter pw;
	BufferedReader br;
	Member member;
	
	@FXML void key(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER) send();
	}
	@FXML private void send() {
		pw.println(member.getPetname()+" : " + textfield.getText());
		pw.flush();
		textfield.clear();
	}
	
	@Override
	public void run() {
		try {
			so = new Socket("127.0.0.1", 7979);
			pw = new PrintWriter(so.getOutputStream());
			br = new BufferedReader(new InputStreamReader(so.getInputStream()));
			
			pw.println("[" + member.getPetname() +"] 님이 입장하였습니다.");
			pw.flush();
			
			while(true) {
				HBox box = new HBox();
				String text = br.readLine().trim();
				System.out.println("클라가 받은 버퍼 : " + text);
				if(text.contains(":")) {
					String[] name = text.split(" :");
					Text tx2 = new Text(text);
					box.getChildren().add(tx2);
					chatlist.add(new ChatText(box));
					if(name[0].equals(member.getPetname())) {
						box.setStyle("-fx-background-color: #f89b00;");
					}
				} 
				
				else if(text.contains("입장")) {
					HBox box1 = new HBox();
					Text tx = new Text(text);
					box1.setAlignment(Pos.CENTER);
					tx.setFill(Color.BLUE);
					box1.getChildren().add(tx);
					chatlist.add(new ChatText(box1));
				}
				
				
				
			}
		} catch (Exception e) {
			System.out.println("소켓 연결 오류");
			e.printStackTrace();
		}
	}
	
	
}
