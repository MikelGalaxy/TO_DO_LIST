package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class TaskToDo {

	private CheckBox isChecked;
	private final SimpleStringProperty dueDate;
	private String title;
	private int completion;
	private String Description;

	
	@Override
	public String toString() {
		return "TaskToDo [isChecked=" + isChecked + ", dueDate=" + dueDate + ", title=" + title + ", completion="
				+ completion + ", Description=" + Description + "]";
	}


	public TaskToDo()
	{
		super();
		setIsChecked(new CheckBox());
		dueDate=new SimpleStringProperty();
		title="";
		completion=0;
		Description="";

	}
	
	
	public TaskToDo(String dueDate, String title, int completion, String description, boolean checkBoxValue) {
		super();
		this.setIsChecked(new CheckBox());
		isChecked.setSelected(checkBoxValue);
		this.dueDate = new SimpleStringProperty(dueDate);
		this.title = title;
		this.completion = completion;
		Description = description;

	}
	
	
	
	public String getDueDate() {
		return dueDate.get();
	}
	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCompletion() {
		return completion;
	}
	public void setCompletion(int completion) {
		this.completion = completion;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}


	public CheckBox getIsChecked() {
		return isChecked;
	}


	public void setIsChecked(CheckBox isChecked) {
		this.isChecked = isChecked;
	}

	
	
	
	
	
}
