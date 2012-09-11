package myapp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Simple model defines a couple observables (they fire events whenever they change)
 * @author bob.marinier
 *
 */
public class MyModel
{
    public final IntegerProperty count = new SimpleIntegerProperty();
    public final BooleanProperty isRunning = new SimpleBooleanProperty();
}
