package controller;
	
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.TaskToDo;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application implements ITaskSaved {
	private Stage MainWindow;
	private TableView<TaskToDo> table;
	private ObservableList<TaskToDo> tableList;
	private List<TaskToDo> backupList=new LinkedList<TaskToDo>();
	TaskToDo passedTask;
	MainController mainController;
	String filePath="";
	public AddTaskControler addControler;
	private Button fileSelectButton;
	@Override
	public void start(Stage primaryStage) {
		try {
			MainWindow=primaryStage;
			VBox root = new VBox(20);
			root.setPadding(new Insets(5,10,10,5));
			Scene scene = new Scene(root,660,450);
			MainWindow.setTitle("TO DO LIST");
			
			VBox fileBox = CreateFileBox();
			root.getChildren().add(fileBox);
			scene.getStylesheets().add("controller/application.css");
			
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
			

			filterGroup.selectedToggleProperty().addListener(
					(ov,oldToggle,newToggle)->{
						try {
							refresh();
							if(btnFilterAll.isSelected()){
//								System.out.println("AllFilter");
								FilterTable(0);
								refresh();
							}else if(btnFilterOverdue.isSelected()){
//								System.out.println("OverdueFilter");
								FilterTable(1);
								refresh();
							}else if(btnFilterToday.isSelected()){
//								System.out.println("TodayFilter");
								this.FilterTable(2);
								refresh();
							}else if(btnFilterThisWeek.isSelected()){
//								System.out.println("ThisWeekFilter");
								this.FilterTable(3);
								refresh();
							}else if(btnFilterNotCompleted.isSelected()){
//								System.out.println("NotCompletedFilter");
								this.FilterTable(4);
								refresh();
							}
						}catch(ParseException e)
						{
							
						}
		
					});
			
			HBox filterBox=new HBox(20);
			filterBox.getChildren().addAll(btnFilterAll,btnFilterOverdue,btnFilterToday,btnFilterThisWeek,btnFilterNotCompleted);
			filterBox.setAlignment(Pos.CENTER);
			filterBox.setStyle("");
			filterBox.getStyleClass().add("filterBox");
			
			root.getChildren().add(filterBox);
			
			
			table=ConfigureTableView(tableList);		
			
			Button btnAdd=new Button("Add");
			btnAdd.setMinWidth(150);
			btnAdd.setMinHeight(50);
			
            Main tempStore = this;
			
			table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			    	System.out.println();
			        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			            if(mouseEvent.getClickCount() == 2){
			               // System.out.println(table.getSelectionModel().getSelectedItem());	                
			                refresh();
			                //creation of new window
			                try{
					            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddEditView.fxml"));
					            Parent root1 = (Parent) fxmlLoader.load();
					            Stage stage = new Stage();
					            addControler = fxmlLoader.<AddTaskControler>getController();
					            addControler.setTask(table.getSelectionModel().getSelectedItem());
					            addControler.setTaskListner(tempStore);
					            stage.initModality(Modality.APPLICATION_MODAL);
					            stage.initStyle(StageStyle.UNDECORATED);
					            stage.setTitle("Add/Eddit");
					            stage.setScene(new Scene(root1));  
					            stage.show();
					          }
							 catch (IOException e) {
								//e.printStackTrace();
							 }              
			            }
			        }
			    }
			});
		
			VBox tableBox = new VBox(20);
			tableBox.getChildren().add(table);
			
			VBox buttonBox = new VBox(20);
			buttonBox.getChildren().add(btnAdd);
			buttonBox.setAlignment(Pos.CENTER);
						
			btnAdd.setOnAction(
					event->{
						try{
				            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddEditView.fxml"));
				            Parent root1 = (Parent) fxmlLoader.load();
				            Stage stage = new Stage();
				            addControler = fxmlLoader.<AddTaskControler>getController();
				            addControler.setTask(new TaskToDo());
				            addControler.setTaskListner(tempStore);
				            stage.initModality(Modality.APPLICATION_MODAL);
				            stage.initStyle(StageStyle.UNDECORATED);
				            stage.setTitle("Add/Eddit");
				            stage.setScene(new Scene(root1));  
				            stage.show();
				          }
						 catch (IOException e) {
							//e.printStackTrace();
						 }});

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
	
	public ObservableList<TaskToDo> LoadData(String path)
	{
		ObservableList<TaskToDo> tasks = FXCollections.observableArrayList();
	    String line = "";
	    String cvsSplitBy = ",";
	    
	    if(!path.endsWith(".txt"))
	    {
	    	System.out.println("InvalidFile");
	    	return tasks;    
	    }

	        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

	            while ((line = br.readLine()) != null) {
	                String[] task = line.split(cvsSplitBy);
	                tasks.add(new TaskToDo(task[0],task[1], Integer.parseInt(task[2]), task[3], Boolean.parseBoolean(task[4])));
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("InvalidFile");
	        }
		return tasks;
	}
	
	@SuppressWarnings("unchecked")
	public TableView<TaskToDo> ConfigureTableView(ObservableList<TaskToDo> tableList)
	{
		TableView<TaskToDo> table=new TableView<>();
		
		TableColumn<TaskToDo,CheckBox> CheckColumn = new TableColumn<>("\u2611");
		CheckColumn.setMinWidth(35);
		CheckColumn.setMaxWidth(40);
		CheckColumn.setCellValueFactory(new PropertyValueFactory<>("isChecked"));
		CheckColumn.editableProperty();
		
		TableColumn<TaskToDo,String> DueDateColumn = new TableColumn<>("DueDate");
		DueDateColumn.setMinWidth(60);
		DueDateColumn.setStyle("-fx-alignment: CENTER");
		DueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		
		TableColumn<TaskToDo,String> TitleColumn = new TableColumn<>("Title");
		TitleColumn.setMinWidth(120);
		TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		TableColumn<TaskToDo,Integer> CompletionColumn = new TableColumn<>("Completion %");
		CompletionColumn.setMinWidth(70);
		CompletionColumn.setStyle("-fx-alignment: CENTER");
		CompletionColumn.setCellValueFactory(new PropertyValueFactory<>("completion"));

		
		TableColumn<TaskToDo,String> DescriptionColumn = new TableColumn<>("Description");
		DescriptionColumn.setMinWidth(150);
		DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		
		table.getColumns().addAll(CheckColumn,DueDateColumn,TitleColumn,CompletionColumn,DescriptionColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setMaxHeight(600);
		table.getStyleClass().add("table");
		
		
		return table;
	}
	void refresh()
	{
		table.setItems(tableList);
		table.refresh();
	}
	
	public VBox CreateFileBox()
	{		
		VBox fileBox = new VBox();
		fileSelectButton = new Button();
		fileSelectButton.setPrefWidth(660);
		fileBox.getChildren().add(fileSelectButton);
		fileBox.setAlignment(Pos.CENTER);
		fileSelectButton.setOnAction(
				event->{
					FileChooser fileChooser = new FileChooser();
					File file = fileChooser.showOpenDialog(MainWindow);
					if(file!=null)
					{
						//filename path
//						System.out.println(file.getAbsolutePath());
						fileSelectButton.setText(file.getAbsolutePath());
						filePath=file.getAbsolutePath();
						if(tableList!=null)
						{
							tableList.addAll(LoadData(filePath));
						}
						else
							tableList=(LoadData(filePath));
						backupList.addAll((LoadData(filePath)));
						refresh();
					}
				});
		return fileBox;
	}
	
	public void FilterTable(int filterType) throws ParseException
	{
		//0-all,1-overdue,2-today,3-this week 4-not completed
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		if(filterType==0)
		{
			if(backupList.size()>0)
			{
				tableList.clear();
				tableList.addAll(backupList);	
			}
		}else if(filterType==1)
		{
			List<TaskToDo> temp = new LinkedList<TaskToDo>();
			if(backupList.size()>0)
			{
				backupList.forEach(s->{
					if(s.getDueDate().compareTo(currentDate)>0)
					{
//						System.out.println(s.getDueDate());
					}else
						temp.add(s);
				});
				tableList.clear();
				tableList.addAll(temp);
			}
		}else if(filterType==2)
		{
			List<TaskToDo> temp = new LinkedList<TaskToDo>();
			if(backupList.size()>0)
			{
				backupList.forEach(s->{
					if(s.getDueDate().compareTo(currentDate)==0)
					{
						temp.add(s);
					}
				});
				tableList.clear();
				tableList.addAll(temp);
			}
		}else if(filterType==3)
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date curDate = df.parse(currentDate);
			cal.setTime(curDate);
			int curWeek = cal.get(Calendar.WEEK_OF_YEAR);

			List<TaskToDo> temp = new LinkedList<TaskToDo>();
			if(backupList.size()>0)
			{
				backupList.forEach(s->{
					Date date = null;
					try {
						date = df.parse(s.getDueDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cal.setTime(date);
					int week = cal.get(Calendar.WEEK_OF_YEAR);
					if(curWeek==week)
					{
						temp.add(s);
					}
				});
				tableList.clear();
				tableList.addAll(temp);
			}
		}else if(filterType==4)
		{
			List<TaskToDo> temp = new LinkedList<TaskToDo>();
			if(backupList.size()>0)
			{
				backupList.forEach(s->{
					if(s.getIsChecked().isSelected()==false)
					{
						temp.add(s);
					}
				});
				tableList.clear();
				tableList.addAll(temp);
			}
		}
	}
	
	
	public void SaveData(String path)
	{		
	    
	    if(!path.endsWith(".txt"))
	    {
	    	System.out.println("InvalidFile");
	    	return;    
	    }
	    if(tableList==null || tableList.size()<=0)
	    {
	    	return;  
	    }

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

	        	tableList.forEach(s->{
	        		String string = String.format("%s,%s,%d,%s,%s", s.getDueDate(), s.getTitle(),s.getCompletion(),s.getDescription(),s.getIsChecked().isSelected());
	        		//"task[0],task[1], Integer.parseInt(task[2]), task[3], Boolean.parseString(task[4]
	        		try {
						writer.write(string);
						writer.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		        			
	        	});
	        	writer.close();
	        	
	        	
//	            while ((line = br.readLine()) != null) {
//	                String[] task = line.split(cvsSplitBy);
//	                tasks.add(new TaskToDo(task[0],task[1], Integer.parseInt(task[2]), task[3], Boolean.parseBoolean(task[4])));
//	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("InvalidFile");
	        }
	}
	
	public void SaveTaskToDoList()
	{
		if(filePath=="")
		{
			FileChooser saveDirChoose = new FileChooser();
			saveDirChoose.getExtensionFilters().add(
					new ExtensionFilter("Text Files", "*.txt")
					);
			File newFileToSave =saveDirChoose.showSaveDialog(MainWindow);
			filePath=newFileToSave.getAbsolutePath();
			fileSelectButton.setText(filePath);
			try {
				newFileToSave.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to create file");
				return;
			}
			SaveData(filePath);
		}else
		{
			SaveData(filePath);		
		}
		
		
	}
	
	
	 @Override
	    public void TaskSaved(Boolean add) {
	        if(addControler.getTaskToDo()!=null)
	        {
//	        	System.out.println(addControler.getTaskToDo());
	        if(add)
	        {
	        	if(tableList==null)
	        		tableList = FXCollections.observableArrayList();
	        	if(addControler.getTaskToDo()!=null)
	        	{
	        		tableList.add(addControler.getTaskToDo());
	        		backupList.add(addControler.getTaskToDo());
	        	}	        	
	        }
	        SaveTaskToDoList();
            refresh();
	        }
	    }
	
}
