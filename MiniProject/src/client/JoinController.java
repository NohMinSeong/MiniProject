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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JoinController {
	@FXML Button joinbtn, redupbtn, backbtn;
	@FXML TextField userid, username, petname;
	@FXML PasswordField userpw, reduppw;
	@FXML Text msg;
	Main main;
	MainController model;
	Socket so;
	PrintWriter pwr;
	BufferedReader br;
	
	@FXML void redupcheck() {
		try {
			if(userid.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("아이디 입력");
				alert.setHeaderText("아이디를 입력하세요.");
				alert.setContentText("아이디를 다시 입력하세요.");
				alert.showAndWait();
				userid.clear();
				userid.requestFocus();
				return;
			}else {
				so = new Socket("127.0.0.1",7777);
				pwr = new PrintWriter(so.getOutputStream());
				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				
				pwr.println("id★"+userid.getText());
				pwr.flush();
				
				String result = br.readLine().trim();
				if(result.equals("s")) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("아이디 사용 가능");
					alert.setHeaderText("가능한 아이디입니다.");
					alert.setContentText("사용 가능한 아이디입니다. \n아래 정보를 입력해주세요.");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("아이디 중복");
					alert.setHeaderText("이미 존재하는 아이디입니다.");
					alert.setContentText("이미 존재하는 아이디입니다. \n다시 입력해주세요.");
					alert.showAndWait();
					userid.clear();
				}
			}
			}catch(IOException e) {
			}
	}
	
	@FXML private void join() throws Exception {
			if(so==null) {
				msg.setText("중복 검사를 하세요");
				}
			if(!(userpw.getText().equals(reduppw.getText()))) {msg.setText("패스워드가 일치하지않습니다."); userpw.clear(); reduppw.clear();}
			if(!(userid.getText().isEmpty() || userpw.getText().isEmpty() || username.getText().isEmpty() || petname.getText().isEmpty())) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("회원가입 성공");
				alert.setHeaderText("회원가입에 성공하였습니다.");
				alert.setContentText("회원으로 등록해주셔서 감사합니다.\n다시 로그인 해주세요.");
				alert.showAndWait();
			}else msg.setText("정보를 입력하세요");
			
			pwr.println("회원가입★"+userid.getText()+","+userpw.getText()+","+username.getText()+","+petname.getText());
			pwr.flush();
			try {
				Thread.sleep(500);
				so.close();
			} catch (Exception e) {}
			
			Stage join = (Stage)joinbtn.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = loader.load();
			join.setScene(new Scene(root));
			join.setTitle("로그인");
			join.show();
	}
	
	@FXML private void back() {
		try {
			Stage join = (Stage)backbtn.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = loader.load();
			join.setScene(new Scene(root));
			join.setTitle("로그인");
			join.show();
			so.close();
		} catch (Exception e) {
		}
	}
	
	
	
	@FXML void key(KeyEvent e) throws Exception {
		if(e.getCode() == KeyCode.ENTER) {
			join();
		}
	}
}
