package server;

import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {
	@FXML Text msg, count;
	@FXML Button stop, quit;
	Model model;
	
	public Controller() {
		model = new Model();
		new Thread(model).start();
		model.con = this;
	}
	
	@FXML void stop() {
		try {
			model.ss.close();
			model.plist.clear();
			model.plist.add(new PrintWriter(System.out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML void quit() {
		msg.setText("프로그램을 종료합니다");
		stop();
		System.exit(0);
	}
	void renewMsg(int cnt) {
		msg.setText("현재 접속자 : " + cnt);
	}
	void renewMsg() {
		msg.setText("현재 접속자 : " + model.plist.size());
	}
}
