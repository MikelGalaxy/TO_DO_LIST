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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.TaskToDo;


public class AddTaskControler {

//	private MainController main;
	private Boolean isAdding=false;
	private ITaskSaved listner;
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
	private TextArea areaDescription;
	@FXML
	private Slider sCompletion;
	
	public TaskToDo getTaskToDo()
	{
		return currentTask;
	}
	

	public void setTask(TaskToDo task)
	{
		sCompletion.setMax(100);
		sCompletion.setMin(0);
		
		if(task!=null)
		{
			currentTask=task;
			if(currentTask.getTitle()!=null)
				txtTitle.setText(currentTask.getTitle());
			if(currentTask.getDescription()!="")
			{
				areaDescription.setText(currentTask.getDescription());
				isAdding=false;
			}else
				isAdding=true;
				
			
			sCompletion.setValue(currentTask.getCompletion());
			
		}
	}
	public void setTaskListner(Main listner)
	{
		this.listner=listner;
	}
	
	public void SaveChanges()
	{
		if(txtTitle.getText()!=null)
			currentTask.setTitle(txtTitle.getText());
		if(areaDescription.getText()!=null)
			currentTask.setDescription(areaDescription.getText());
			
		currentTask.setCompletion((int) sCompletion.getValue());
		
		if(listner!=null)
			listner.TaskSaved(isAdding);
		CloseWindow();
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
