package myapp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/*
 * Simple model defines an observable integer (it fires an event whenever it changes)
 */
public class MyModel {
	public IntegerProperty count = new SimpleIntegerProperty();
}
