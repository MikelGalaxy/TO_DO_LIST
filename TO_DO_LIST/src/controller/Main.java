package controller;
	
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.TaskToDo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
	@FXML
	private Stage MainWindow;
	private TableView<TaskToDo> table;
	@Override
	public void start(Stage primaryStage) {
		try {
			MainWindow=primaryStage;
			VBox root = new VBox(20);
			root.setPadding(new Insets(5,10,10,5));
			Scene scene = new Scene(root,660,450);
			
			VBox fileBox = new VBox();
			TextField fileField = new TextField(); 
			fileBox.getChildren().add(fileField);
			fileField.setDisable(true);
			//fileField.s
			
			root.getChildren().add(fileBox);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//FileChooser fileChooser = new FileChooser();
			//File file = fileChooser.showOpenDialog(MainWindow);
			
			RadioButton btnFilterAll=new RadioButton("All");
			RadioButton btnFilterOverdue=new RadioButton("Overdue");
			RadioButton btnFilterToday=new RadioButton("Today");
			RadioButton btnFilterThisWeek=new RadioButton("This week");
			RadioButton btnFilterNotCompleted=new RadioButton("Not completed");
			
			
			ToggleGroup filterGroup = new ToggleGroup();
			btnFilterAll.setToggleGroup(filterGroup);
			btnFilterOverdue.setToggleGroup(filterGroup);
			btnFilterToday.setToggleGroup(filterGroup);
			btnFilterThisWeek.setToggleGroup(filterGroup);
			btnFilterNotCompleted.setToggleGroup(filterGroup);
			
			filterGroup.selectToggle(btnFilterAll);
			
			/*
			myToggleGroup.selectedToggleProperty().addListener(
					(ov,odlToggle,newToggle)->{
					System.out.println("Radio button toggled");
					if(myButton.isSelected()){
						System.out.println("NEW button");
					}else{
						System.out.println("BUTTON BUTTON");				
					}
					}		
					);
			*/
			
			HBox filterBox=new HBox(20);
			filterBox.getChildren().addAll(btnFilterAll,btnFilterOverdue,btnFilterToday,btnFilterThisWeek,btnFilterNotCompleted);
			filterBox.setAlignment(Pos.CENTER);
			root.getChildren().add(filterBox);
			
			table=ConfigureTableView();
			
			
			Button btnAdd=new Button("Add");
			btnAdd.setMinWidth(150);
			btnAdd.setMinHeight(50);

			
			VBox tableBox = new VBox(20);
			tableBox.getChildren().add(table);
			
			VBox buttonBox = new VBox(20);
			buttonBox.getChildren().add(btnAdd);
			buttonBox.setAlignment(Pos.CENTER);
			
			

			
			
			root.getChildren().add(tableBox);
			root.getChildren().add(buttonBox);
			
			
			MainWindow.setScene(scene);
			MainWindow.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<TaskToDo> LoadData()
	{
		ObservableList<TaskToDo> tasks = FXCollections.observableArrayList();
		tasks.add(new TaskToDo("2018-03-29", "END", 25, "OK easy", false));
		tasks.add(new TaskToDo("2018-03-32", "FF", 15, "OK easy", false));
		tasks.add(new TaskToDo("2018-03-29", "END", 25, "OK easy", true));
		tasks.add(new TaskToDo("2018-03-32", "FF", 15, "OK easy", false));
		
		return tasks;
	}
	
	@SuppressWarnings("unchecked")
	public TableView<TaskToDo> ConfigureTableView()
	{
		TableView<TaskToDo> table=new TableView<>();
		
		TableColumn<TaskToDo,CheckBox> CheckColumn = new TableColumn<>("[]");
		CheckColumn.setMinWidth(40);
		CheckColumn.setCellValueFactory(new PropertyValueFactory<>("isChecked"));

		
		TableColumn<TaskToDo,String> DueDateColumn = new TableColumn<>("DueDate");
		DueDateColumn.setMinWidth(100);
		DueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		
		TableColumn<TaskToDo,String> TitleColumn = new TableColumn<>("Title");
		TitleColumn.setMinWidth(100);
		TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		TableColumn<TaskToDo,Integer> CompletionColumn = new TableColumn<>("Completion %");
		CompletionColumn.setMinWidth(60);
		CompletionColumn.setCellValueFactory(new PropertyValueFactory<>("completion"));

		
		TableColumn<TaskToDo,String> DescriptionColumn = new TableColumn<>("Description");
		DescriptionColumn.setMinWidth(120);
		DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		
		table.setItems(LoadData());
		table.getColumns().addAll(CheckColumn,DueDateColumn,TitleColumn,CompletionColumn,DescriptionColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setMaxHeight(600);
		
		return table;
	}
	
	
}
