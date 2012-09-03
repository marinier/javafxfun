package myapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.jsoar.kernel.SoarException;
import org.jsoar.kernel.io.beans.SoarBeanOutputContext;
import org.jsoar.kernel.io.beans.SoarBeanOutputHandler;
import org.jsoar.kernel.io.beans.SoarBeanOutputManager;
import org.jsoar.runtime.ThreadedAgent;
import org.jsoar.util.commands.SoarCommands;

public class MyApplication extends Application {
	
	private static final Object soarSource = MyApplication.class.getResource("load.soar");
	
	private ThreadedAgent agent;
	
	private MyModel model = new MyModel();
	

	@Override
	public void start(Stage primaryStage) throws IOException, SoarException, InterruptedException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.load(getClass().getResourceAsStream("MyApplication.fxml"));
		
		MyController controller = (MyController) loader.getController();
		controller.setModel(this.model);
		primaryStage.setTitle("My JavaFx Application");
		Scene scene = new Scene(controller.getView(), 400,275);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(MyApplication.class.getResource("MyApplication.css").toExternalForm());
		primaryStage.show();
		
		this.SetupSoar();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void SetupSoar() throws SoarException, InterruptedException {
		agent = ThreadedAgent.create();
		final SoarBeanOutputManager manager = new SoarBeanOutputManager(agent.getEvents());
		final SoarBeanOutputHandler<MyCountBean> handler = new SoarBeanOutputHandler<MyCountBean>() {
			
			@Override
			public void handleOutputCommand(SoarBeanOutputContext context, final MyCountBean bean)
			{
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						model.count.set(bean.value);
					}
				});
			}
		};
		
		manager.registerHandler("count", handler, MyCountBean.class);
		
		SoarCommands.source(agent.getInterpreter(), soarSource);
		agent.openDebuggerAndWait();
	}
}
