import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main_Class extends Application{


	
	private Group root;
	private Scene homeScene;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("homeMenu.fxml"));
		//Group root = new Group();
		
        Scene scene = new Scene(root, 800, 600);
    
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
	}

	

}
