package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	@FXML TextField idfield;
	@FXML PasswordField pwfield;
	@FXML Button loginbtn, joinbtn;
	@FXML Text msg;
	@FXML AnchorPane snspane;
	
	Stage stage;
	PrintWriter pwr;
	Member member;
	MainController model;
	String id, pw;
	Socket so;
	Main main;
	BufferedReader br;
	DLOGModel dm;

	@FXML void logincheck() {
		try {
			id = idfield.getText();
			pw = pwfield.getText();	
			
			so = new Socket("127.0.0.1",7777);
			pwr = new PrintWriter(so.getOutputStream());
			br = new BufferedReader(new InputStreamReader(so.getInputStream()));
			
			pwr.println("로그인★" +id +"," + pw);
			pwr.flush();
			
			String result = br.readLine().trim();
			String[] re = result.split("★");
			if(re[0].equals("logins")) {
				String re2 = re[1];
				String[] mem = re2.split(",");
				member = new Member(mem[0], mem[1], mem[2], mem[3]);
				
				dm = new DLOGModel();
				dm.so = this.so;
				new Thread(dm).start();
				
				// 메인화면
				Stage join = (Stage)loginbtn.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
				Parent root = loader.load();
				join.setScene(new Scene(root));
				MainController mc = loader.getController();
				mc.so = this.so;
				mc.br = this.br;
				mc.pw = this.pwr;
				mc.dm = dm;
				mc.mb = this.member;
	
				// 메인화면의 초기화 화면
				FXMLLoader loader2 = new FXMLLoader(getClass().getResource("board.fxml"));
				snspane = loader2.load();
				DLOGController dc = loader2.getController();
				dc.dm = dm;
				dc.mb = member;
				dc.so = this.so;
				
				TableView<DLOGMember> tv = (TableView<DLOGMember>)snspane.getChildren().get(1);
				tv.setItems(dm.ol);
				mc.pane.getChildren().add(snspane);
				
				mc.msg.setText(member.getPetname() + " 님, 안녕하시개 !");
				join.setTitle("메인 페이지");
				join.show();
				
			
		} else if(result.equals("loginf")){
			msg.setText("아이디 또는 비밀번호를 확인하세요");
			so.close();
		}else {
			
		}
		}catch(IOException e) {
			System.out.println("로그인 체크 오류");
		}
	}
	
	@FXML void join() throws Exception {
		Stage join = (Stage)joinbtn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("join.fxml"));
		Parent root = loader.load();
		join.setScene(new Scene(root));
		join.setTitle("회원가입");
		join.show();
		JoinController jc = loader.getController();
	}
	
	@FXML void key(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER) {
			logincheck();
		}
		else if(e.getCode() == KeyCode.ESCAPE) {
			TextField tf = (TextField)e.getSource();
			tf.clear();
		}
	} 
}
