package myapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.jsoar.runtime.ThreadedAgent;

public class MyController
{

    // These are automatically bound to elements defined in the FXML file (via
    // reflection)
    // The @FXML annotation is only required if these are not public
    @FXML
    protected Parent root;

    @FXML
    protected Label counterValue;

    @FXML
    protected Button runButton;

    @FXML
    protected Button stopButton;

    protected ThreadedAgent agent;

    /*
     * bind the model to the view label so the view automatically updates when
     * the model changes basically, this automatically registers for the event
     * that the model fires also store the agent so we can send commands to it
     */

    /**
     * @param model
     *            : MyModel instance to bind to
     * @param agent
     *            : Agent instance to control
     * 
     *            bind the model to the view label so the view automatically
     *            updates when the model changes basically, this automatically
     *            registers for the event that the model fires also store the
     *            agent so we can send commands to it
     */
    public void initialize(MyModel model, ThreadedAgent agent)
    {
        this.agent = agent;
        this.counterValue.textProperty().bind(model.count.asString());
        this.runButton.disableProperty().bind(model.isRunning);
        this.stopButton.disableProperty().bind(model.isRunning.not());
    }

    /*
     * returns a scene object we use this to set up the window in Application
     */
    public Parent getView()
    {
        return root;
    }

    @FXML
    protected void handleRunButtonAction(ActionEvent event)
    {
        this.agent.runForever();
    }

    @FXML
    protected void handleStopButtonAction(ActionEvent event)
    {
        this.agent.stop();
    }

}
