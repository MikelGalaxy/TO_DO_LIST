package controller;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.TaskToDo;


public class AddTaskControler {

	private MainController main;
	private TaskToDo currentTask;
	@FXML
	private DatePicker dDueDate;
	@FXML
	private Button btnClose;	
	
	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;
	@FXML
	private TextField txtTitle;
	@FXML
	private Slider sCompletion;

	public void initialize(MainController mainController) {
		main = mainController;
		System.out.println("OK");
	}
	
	public void SaveChanges()
	{
		System.out.println("YOLO");
	}
	public void CloseWindow()
	{
		 Stage stage = (Stage) btnClose.getScene().getWindow();
		    stage.close();
	}
	public void CancelWindow()
	{
		 Stage stage = (Stage) btnCancel.getScene().getWindow();
		    stage.close();
	}
	
	
	
	
	
}
