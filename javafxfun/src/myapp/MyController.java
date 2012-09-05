package myapp;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;


public class MyController {
	
	// These are automatically bound to elements defined in the FXML file (via reflection)
	// The @FXML annotation is only required if these are private
	@FXML private Parent root;
	@FXML private Label counterValue;

	/*
	 * bind the model to the view label so the view automatically updates when the model changes 
	 * basically, this automatically registers for the event that the model fires
	 */
	public void setModel(MyModel model)
	{
		this.counterValue.textProperty().bind(model.count.asString());
	}
	
	/*
	 * returns a scene object
	 * we use this to set up the window in Application
	 */
	public Parent getView()
	{
		return root;
	}
	
}
