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
				alert.setTitle("���̵� �Է�");
				alert.setHeaderText("���̵� �Է��ϼ���.");
				alert.setContentText("���̵� �ٽ� �Է��ϼ���.");
				alert.showAndWait();
				userid.clear();
				userid.requestFocus();
				return;
			}else {
				so = new Socket("127.0.0.1",7777);
				pwr = new PrintWriter(so.getOutputStream());
				br = new BufferedReader(new InputStreamReader(so.getInputStream()));
				
				pwr.println("id��"+userid.getText());
				pwr.flush();
				
				String result = br.readLine().trim();
				if(result.equals("s")) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("���̵� ��� ����");
					alert.setHeaderText("������ ���̵��Դϴ�.");
					alert.setContentText("��� ������ ���̵��Դϴ�. \n�Ʒ� ������ �Է����ּ���.");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("���̵� �ߺ�");
					alert.setHeaderText("�̹� �����ϴ� ���̵��Դϴ�.");
					alert.setContentText("�̹� �����ϴ� ���̵��Դϴ�. \n�ٽ� �Է����ּ���.");
					alert.showAndWait();
					userid.clear();
				}
			}
			}catch(IOException e) {
			}
	}
	
	@FXML private void join() throws Exception {
			if(so==null) {
				msg.setText("�ߺ� �˻縦 �ϼ���");
				}
			if(!(userpw.getText().equals(reduppw.getText()))) {msg.setText("�н����尡 ��ġ�����ʽ��ϴ�."); userpw.clear(); reduppw.clear();}
			if(!(userid.getText().isEmpty() || userpw.getText().isEmpty() || username.getText().isEmpty() || petname.getText().isEmpty())) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("ȸ������ ����");
				alert.setHeaderText("ȸ�����Կ� �����Ͽ����ϴ�.");
				alert.setContentText("ȸ������ ������ּż� �����մϴ�.\n�ٽ� �α��� ���ּ���.");
				alert.showAndWait();
			}else msg.setText("������ �Է��ϼ���");
			
			pwr.println("ȸ�����ԡ�"+userid.getText()+","+userpw.getText()+","+username.getText()+","+petname.getText());
			pwr.flush();
			try {
				Thread.sleep(500);
				so.close();
			} catch (Exception e) {}
			
			Stage join = (Stage)joinbtn.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = loader.load();
			join.setScene(new Scene(root));
			join.setTitle("�α���");
			join.show();
	}
	
	@FXML private void back() {
		try {
			Stage join = (Stage)backbtn.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = loader.load();
			join.setScene(new Scene(root));
			join.setTitle("�α���");
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
