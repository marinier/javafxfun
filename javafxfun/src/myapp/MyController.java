package myapp;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;


public class MyController {
	
	@FXML private Parent root;
	@FXML private Label counterValue;

	
	public void setModel(MyModel model) {
		this.counterValue.textProperty().bind(model.count.asString());
	}
	
	public Parent getView()
	{
		return root;
	}
	
}
