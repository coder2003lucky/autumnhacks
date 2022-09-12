import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    
        stage.setTitle("QILG");
        stage.setResizable(false);
        stage.setScene(scene);
        
        stage.getIcons().add(new Image(Main_Class.class.getResourceAsStream("Screen Shot 2022-09-11 at 10.21.29 PM.png")));
        stage.show();
	}

	

}
