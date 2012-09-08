package myapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.jsoar.kernel.SoarException;
import org.jsoar.kernel.events.StartEvent;
import org.jsoar.kernel.events.StopEvent;
import org.jsoar.kernel.io.beans.SoarBeanOutputContext;
import org.jsoar.kernel.io.beans.SoarBeanOutputHandler;
import org.jsoar.kernel.io.beans.SoarBeanOutputManager;
import org.jsoar.runtime.ThreadedAgent;
import org.jsoar.util.commands.SoarCommands;
import org.jsoar.util.events.SoarEvent;
import org.jsoar.util.events.SoarEventListener;

public class MyApplication extends Application {
	
	private ThreadedAgent agent = ThreadedAgent.create();
	
	// the model, which will be connected to a controller and Soar
	// this doesn't really need to be here; could just create it in start() and pass it around
	private MyModel model = new MyModel();

	/* 
	 * This gets called automatically when an FXML application is launched
	 * This is where the setup happens
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, SoarException, InterruptedException {
		
		// load FXML file
		FXMLLoader loader = new FXMLLoader();
		loader.load(getClass().getResourceAsStream("MyApplication.fxml"));
		
		// the FXML file specifies a controller class, which is automatically instantiated
		// get the controller instance from the FXML file
		MyController controller = (MyController) loader.getController();
		
		// perform controller setup stuff
		controller.initialize(this.model, this.agent);
		
		// setup the JavaFX window
		primaryStage.setTitle("My JavaFx Application");
		Scene scene = new Scene(controller.getView(), 400,275); // get the view from the controller, which is bound to the FXML
		primaryStage.setScene(scene);
		// apply a CSS style sheet
		scene.getStylesheets().add(MyApplication.class.getResource("MyApplication.css").toExternalForm());
		primaryStage.show();
		
		// create the Soar agent, load productions, register output, etc.
		this.SetupSoar();
	}

	/*
	 * application entry
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/*
	 * creates the agent, loads productions, registered an output handler, and launches a debugger
	 */
	private void SetupSoar() throws SoarException, InterruptedException {
		
		// register output handler
		final SoarBeanOutputManager manager = new SoarBeanOutputManager(agent.getEvents());
		final SoarBeanOutputHandler<MyCountBean> handler = new SoarBeanOutputHandler<MyCountBean>() {
			
			// when we get a count output command, update the model in the JavaFX thread
			@Override
			public void handleOutputCommand(SoarBeanOutputContext context, final MyCountBean bean)
			{
				// this is the equivalent to Swing's invokeLater
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						model.count.set(bean.value);
					}
				});
			}
		};
		
		manager.registerHandler("count", handler, MyCountBean.class);
		
		agent.getEvents().addListener(StartEvent.class,
				new SoarEventListener() {

					@Override
					public void onEvent(SoarEvent event) {
						// this is the equivalent to Swing's invokeLater
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								model.isRunning.set(true);
							}
						});
					}
				});
		
		agent.getEvents().addListener(StopEvent.class,
				new SoarEventListener() {

					@Override
					public void onEvent(SoarEvent event) {
						// this is the equivalent to Swing's invokeLater
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								model.isRunning.set(false);
							}
						});
					}
				});
		
		// load productions
		final Object soarSource = MyApplication.class.getResource("load.soar");
		SoarCommands.source(agent.getInterpreter(), soarSource);
		
		agent.openDebugger();
	}
}
