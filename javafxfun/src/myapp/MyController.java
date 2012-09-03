package myapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;


public class MyController {
	private  MyModel model;
	
	@FXML private Parent root;
	@FXML private Label label;

	
	public void setModel(MyModel model) {
		this.model = model;
		this.label.textProperty().bind(model.count.asString());
//		model.count.addListener(
//				new ChangeListener<Object>() {
//					public void changed(ObservableValue<?> observable,
//							Object oldValue, Object newValue) {
//						label.setText(newValue.toString());
//					}
//				});
	}
	
	public Parent getView()
	{
		return root;
	}
	
}
