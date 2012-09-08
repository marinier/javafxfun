package myapp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/*
 * Simple model defines an observable integer (it fires an event whenever it changes)
 */
public class MyModel
{
    public final IntegerProperty count = new SimpleIntegerProperty();
    public final BooleanProperty isRunning = new SimpleBooleanProperty();
}
