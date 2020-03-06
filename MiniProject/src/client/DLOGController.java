package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DLOGController implements Initializable{
	@FXML TableView<DLOGMember> table;
	@FXML TableColumn<DLOGMember, VBox> boxCol;
	@FXML Button writebtn;
	@FXML AnchorPane updatepane, boardpane, writepane;
	@FXML ContextMenu conmenu;
	@FXML MenuItem update, delete;
	
	boolean flag = false;
	
	String getImage, getText, like;
	DLOGModel dm;
	Member mb;
	DLOGMember dmem;
	Socket so;
	
	int index, likecount;
	boolean sw = false;
	
    @FXML private void write() {
    	try {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("write.fxml"));
    		writepane = loader.load();
    		boardpane.getChildren().clear();
			boardpane.getChildren().add(writepane);
    		WriteController wcon = loader.getController();
    		wcon.dc = this;
    		wcon.dm = this.dm;
    		wcon.mb = this.mb;
    		wcon.so = this.so;

		} catch (Exception e) {
			System.out.println("예외 발생");
		}
    }

	@FXML private void update() {
		try {
			index = table.getSelectionModel().getSelectedIndex();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("update.fxml"));
			updatepane = loader.load();
			boardpane.getChildren().clear();
			boardpane.getChildren().add(updatepane);
			UpdateController uc = loader.getController();
			uc.dc = this;
			uc.dm = this.dm;
			uc.mb = this.mb;
			uc.so = this.so;
			uc.index = this.index;
			uc.likecount = this.likecount;
			
		} catch (Exception e) {}
		
	}
	@FXML private void delete() {
		int index = table.getSelectionModel().getSelectedIndex();
		dm.fal.remove(index);
		dm.ol.remove(index);
		
		Alert al = new Alert(AlertType.CONFIRMATION);
		al.setHeaderText("삭제 완료");
		al.setContentText("삭제되었습니다");
		al.showAndWait();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.setOnMouseClicked(e->{
			if(e.getClickCount()==2 && sw==false) {
				index = table.getSelectionModel().getSelectedIndex();
				this.likecount = dm.ol.get(index).getLike();
				likecount += 1;
				sw = true;
				
				dm.myupdate(dm.ol.get(index).getNickname(), dm.ol.get(index).getImageString(), 
						likecount, dm.ol.get(index).getText(), index);
				
			}else if(e.getClickCount()==2 && sw==true) {
				index = table.getSelectionModel().getSelectedIndex();
				this.likecount = dm.ol.get(index).getLike();
				likecount -= 1;
				sw = false;
				
				dm.myupdate(dm.ol.get(index).getNickname(), dm.ol.get(index).getImageString(), 
						likecount, dm.ol.get(index).getText(), index);
			}
		});
		
		boxCol.setCellValueFactory(new PropertyValueFactory<DLOGMember, VBox>("box"));
		  

	}
}
