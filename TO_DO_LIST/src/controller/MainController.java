package controller;

import model.TaskToDo;

public class MainController {

	
	public AddTaskControler addController;
	public TaskToDo currentTask;
	
	
	public void initialize() {
		addController.initialize(this);
	}
	public void changeTask(TaskToDo task)
	{
		currentTask=task;
	}
	
}
