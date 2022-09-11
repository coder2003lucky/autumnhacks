package autumnhacks;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{


	
	private Group root;
	private Scene homeScene;
	
	private Group fRoot;
	private Scene flashScene;
	private Button fButton, learnButton;
	private StackPane fBPane;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Educational App");
		stage.setWidth(800);
		stage.setHeight(600);
		
		// Homescreen
		root = new Group();
		homeScene = new Scene(root);
		
		stage.setScene(homeScene);
		
		fButton = new Button();
		fButton.setPrefWidth(100);
		fButton.setPrefHeight(20);
		fButton.setLayoutX(stage.getWidth()*0.5-fButton.getPrefWidth()*0.5);
		fButton.setLayoutY(stage.getHeight()*0.7-fButton.getPrefHeight()*0.5);
		fButton.setText("Flashcards");
		fButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.setScene(flashScene);
			}
			
		});
		root.getChildren().add(fButton);
		
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("Menu 1");
		menuBar.getMenus().add(menu1);
		root.getChildren().add(menuBar);
		
		// Flashcards scene
		BackgroundFill background_fill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
		Background bg = new Background(background_fill);
		
		fRoot = new Group();
		flashScene = new Scene(fRoot);
		
		fBPane = new StackPane();
		fBPane.setPrefWidth(stage.getWidth()*0.8);
		fBPane.setPrefHeight(stage.getHeight()*0.2);
		fBPane.setLayoutX(stage.getWidth()*0.5-fBPane.getPrefWidth()*0.5);
		fBPane.setLayoutY(stage.getHeight()-fBPane.getPrefHeight()*1.5);
		fBPane.setBackground(bg);
		fRoot.getChildren().add(fBPane);
		
		learnButton = new Button();
		learnButton.setText("Learn Mode");
		fBPane.getChildren().add(learnButton);
		
		MenuBar fMenuBar = new MenuBar();
		Menu fMenu1 = new Menu("Import");
		MenuItem fMenu1A = new MenuItem("From File");
		MenuItem fMenu1B = new MenuItem("From Clipboard");
		fMenu1B.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		fMenu1.getItems().addAll(fMenu1A, fMenu1B);
		fMenuBar.getMenus().addAll(fMenu1);
		
		fRoot.getChildren().add(fMenuBar);
		
		
		
		stage.show();
	}
	
	/*
	private class ButtonHandler implements EventHandler{

		public void handle(Event event) {
			if(event.getSource().equals(fButton)) {
				stage.setScene()
			}
			
		}
		
		
	}
	*/

}
