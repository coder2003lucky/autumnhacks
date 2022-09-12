import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.xml.sax.InputSource;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable{

	@FXML
	private Button fButton, fLearnButton, fFlashcardsButton;
	
	@FXML
	private Button flashcard, flashcardMenuBackButton;
	
	@FXML
	private ScrollPane fSPane;
	
	@FXML
	private Label errorLabel;
	


	
	private AnimationTimer learnTimer;
	
	private static Deck deck, wholeDeck;
	
	// For card mode
	private static int currentCard = 0;
	private Button cardModeNextButton;
	private boolean cardFacingUp = true;

	private String backColor = "-fx-border-color: #FC3808; -fx-background-color: #FAF3ED;";
	private String frontColor = "-fx-border-color: black; -fx-background-color: lightgray;";
	
	// For learning mode
	private static int currentQCard = 0;
	@FXML
	private Label learnQuestion;
	@FXML
	private Button learnAnswer0, learnAnswer1, learnAnswer2, learnAnswer3;
	private int learnCurrentAnswer;
	private String answerDefaultColor = "-fx-border-color: #242423; -fx-background-color: #EBEBE4;";
	@FXML
	private Button learnModeNextButton;
	@FXML
	private TextField learnModeTextField;
	@FXML
	private AnchorPane learnModePane;
	private String wrongColor;
	private static int learnedCardCount = 0;
	private static boolean isLearnMatchMode = true;
	@FXML
	private Label learnAnswer;
	@FXML
	private Button learnThisCorrectButton;
	
	private static int learnCurrentDeckStart = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources){
		learnTimer = new AnimationTimer() {

			public void handle(long now) {
				long old = 0;
				if(now-old > (Math.pow(10, 9))) {
					old = now;
					//System.out.println(deck);
				}
			}
		};
		if(flashcard!=null) {
			flashcard.setText(deck.getFront(currentCard));
			flashcard.setStyle(frontColor);
		}
		if(learnQuestion!=null) {
			
			if(isLearnMatchMode) {
				loadLearnQuestion();
			}else {
				learnLoadTextModeQuestion();
			}
		}
		
	}
	
	@FXML
	public void openFlashcardMenu() throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("flashcardMenu.fxml"));
		
		Stage primaryStage = (Stage) fButton.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
		
		
	}
	
	@FXML
	public void loadFlashcards() throws Exception{
		Stage primaryStage = (Stage) fLearnButton.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(primaryStage);
		
		String content = "";
		
		deck = new Deck();
		
		FileInputStream in = new FileInputStream(file);
		BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8")); 
		String line = "";
		Text txt = new Text();
		String[] t;
		
		while(line!=null) {
			line = rd.readLine();
			if(line==null) {
				break;
			}
			//System.out.println(line);
			content += line + "\n";
			
			t = line.split("\t");
			//System.out.println(t[0] +" " + t[1]);
			deck.addCard(t[0], t[1]);
		}
		
		learnCurrentDeckStart = 0;
		wholeDeck = deck;
		deck = wholeDeck.giveNextFive(0);
		//deck.printDeck();
		
		//deck.printDeck();
		
		txt.setText(content);
		fSPane.setContent(txt);
		
		
		rd.close();
	}
	
	@FXML
	public void fClickFlashcard() {
		//System.out.println(cardFacingUp);
		if(cardFacingUp) {
			cardFacingUp = false;
			//System.out.println(deck.getBack(currentCard));
			flashcard.setText(deck.getBack(currentCard));
			flashcard.setStyle(backColor);
		}else {
			cardFacingUp = true;
			flashcard.setText(deck.getFront(currentCard));
			
			flashcard.setStyle(frontColor);
		}
	}
	
	@FXML
	public void cardModeShowNext() {
		currentCard++;
		if(currentCard>=deck.getSize()) {
			currentCard = 0;
		}
		cardFacingUp = true;
		flashcard.setText(deck.getFront(currentCard));
		
		flashcard.setStyle(frontColor);
	}
	
	@FXML
	public void openCardModeMenu() throws Exception {
		//System.out.println(1);
		if(deck==null || deck.isEmpty()) {
			 Parent root;
		        try {
		            root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
		            Stage stage = new Stage();
		            stage.setTitle("ERROR");
		            stage.setScene(new Scene(root));
		            stage.show();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		        return;
		}
		Parent root = FXMLLoader.load(getClass().getResource("cardModeMenu.fxml"));
			
		Stage primaryStage = (Stage) fFlashcardsButton.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
		
		//System.out.println(flashcard);
		
		cardFacingUp = true;
		//deck.printDeck();
		
	}
	
	@FXML
	public void leaveCardModeMenu() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("flashcardMenu.fxml"));
		
		Stage primaryStage = (Stage) flashcard.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
		
	}
	
	@FXML
	public void leaveLearnModeMenu() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("flashcardMenu.fxml"));
		
		Stage primaryStage = (Stage) learnQuestion.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
		
	}
	
	@FXML
	public void openLearnModeMenu1() throws Exception {
		if(deck==null || deck.isEmpty()) {
			 Parent root;
		        try {
		            root = FXMLLoader.load(getClass().getClassLoader().getResource("error.fxml"));
		            Stage stage = new Stage();
		            stage.setTitle("ERROR");
		            stage.setScene(new Scene(root));
		            stage.show();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		        return;
		}
		Parent root = FXMLLoader.load(getClass().getResource("learnModeMenu1.fxml"));
		
		Stage primaryStage = (Stage) fFlashcardsButton.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
		
	}
	
	@FXML
	public void learnAnswer0Clicked() {
		learnAnswerClicked(0);
	}
	
	@FXML
	public void learnAnswer1Clicked() {
		learnAnswerClicked(1);
	}
	
	@FXML
	public void learnAnswer2Clicked() {
		//System.out.println(1);
		learnAnswerClicked(2);
	}
	
	@FXML
	public void learnAnswer3Clicked() {
		learnAnswerClicked(3);
	}
	
	public void learnAnswerClicked(int ans) {
		highlightAnswers();
		if(ans == learnCurrentAnswer) { // If answer was correct in match mode
			System.out.println(deck.getSize());
			
			learnModePane.getChildren().add(learnModeNextButton);
			
		}else {
			learnModePane.getChildren().remove(learnModeTextField);
		}
		
	}
	
	public void learnSwitchToTextMode() {
		learnModePane.getChildren().remove(learnAnswer0);
		learnModePane.getChildren().remove(learnAnswer1);
		learnModePane.getChildren().remove(learnAnswer2);
		learnModePane.getChildren().remove(learnAnswer3);
		learnModePane.getChildren().remove(learnModeNextButton);
	
		learnLoadTextModeQuestion();
	
		isLearnMatchMode = !isLearnMatchMode;
	}
	
	@FXML
	public void learnTextFieldAction() {
		if(isLearnMatchMode) {
			String a = null;
			do {
				a = learnModeTextField.getText();
				if(a.equals(deck.getBack(currentQCard))) {
					learnModeNextClicked();
					learnModePane.getChildren().remove(learnModeTextField);
					learnModeTextField.setStyle("-fx-border-color: black; -fx-background-color: white;");
				}else {
					learnModeTextField.setStyle(wrongColor);
				}
			}while(a.equals(deck.getBack(currentQCard)));
			learnModeTextField.clear();
		}else {
			String a = null;
			learnModePane.getChildren().remove(learnAnswer);
			do {
				a = learnModeTextField.getText();
				if(a.equals(deck.getBack(currentQCard))) {
					learnTextModeCorrect();
					learnModeTextField.setStyle("-fx-border-color: black; -fx-background-color: white;");
				}else {

					learnModePane.getChildren().remove(learnAnswer);
					learnModePane.getChildren().add(learnAnswer);
					learnModePane.getChildren().remove(learnThisCorrectButton);
					learnModePane.getChildren().add(learnThisCorrectButton);
					learnAnswer.setText(deck.getBack(currentQCard));
					learnModeTextField.setStyle(wrongColor);
				}
			}while(a.equals(deck.getBack(currentQCard)));
			learnModeTextField.clear();
		}
	}
	
	@FXML
	// Only available to be clicked if answer was correct
	public void learnModeNextClicked() {
		//System.out.println(1);

		answeredCorrectly();
		
		if(deck.isEmpty()) {
			deck = newSubDeck(isLearnMatchMode);
			learnSwitchToTextMode();
			return;
		}
		
		loadLearnQuestion();
		learnModePane.getChildren().remove(learnModeNextButton);
	}
	
	@FXML
	public void learnIsCorrectButtonAction() {
		learnTextModeCorrect();
		learnModeTextField.setStyle("-fx-border-color: black; -fx-background-color: white;");
		learnModePane.getChildren().remove(learnThisCorrectButton);
	}
	
	// Is called if text mode answer was correct
	public void learnTextModeCorrect() {
		answeredCorrectly();
		if(currentQCard>=deck.getSize()) {
			currentQCard = 0;
		}
		
		if(deck.isEmpty()) {
			deck = newSubDeck(isLearnMatchMode);
			learnSwitchToMatchMode();
			return;
		}else {
			learnQuestion.setText(deck.getFront(currentQCard));
		}
		learnModePane.getChildren().remove(learnModeNextButton);
		learnModePane.getChildren().remove(learnAnswer);
		
	}
	
	public void learnSwitchToMatchMode() {
		learnModePane.getChildren().add(learnAnswer0);
		learnModePane.getChildren().add(learnAnswer1);
		learnModePane.getChildren().add(learnAnswer2);
		learnModePane.getChildren().add(learnAnswer3);
		
		isLearnMatchMode = !isLearnMatchMode;
		
		loadLearnQuestion();
	}
	
	// Helper
	public static List<Integer> pickNRandom(int n, int exclude) {
	    ArrayList<Integer>  arr = new ArrayList<Integer>();
	    for(int i = 0; i<wholeDeck.getSize(); i++) {
	    	if(i!=exclude) {
	    		arr.add(i);
	    	}
	    }
	    Collections.shuffle(arr);
	    //System.out.println(arr.toString());
	    return n > arr.size() ? arr.subList(0, arr.size()) : arr.subList(0, n);
	}
	
	// Doesn't increment question number
	public void loadLearnQuestion() {
		learnModePane.getChildren().remove(learnModeTextField);
		learnModePane.getChildren().remove(learnModeNextButton);
		learnModePane.getChildren().remove(learnAnswer);
		learnModePane.getChildren().remove(learnThisCorrectButton);
		
		learnQuestion.setText(deck.getFront(currentQCard));
		List<Integer> answers = pickNRandom(3, wholeDeck.getIndexOf(deck.getFront(currentQCard)));
		
		int r = (int) (Math.random()*4);
		learnCurrentAnswer = r;
		
		if(r==0) {
			learnAnswer0.setText(deck.getBack(currentQCard));
			learnAnswer1.setText(wholeDeck.getBack(answers.get(0)));
			learnAnswer2.setText(wholeDeck.getBack(answers.get(1)));
			learnAnswer3.setText(wholeDeck.getBack(answers.get(2)));
		}else if(r==1) {
			learnAnswer0.setText(wholeDeck.getBack(answers.get(0)));
			learnAnswer1.setText(deck.getBack(currentQCard));
			learnAnswer2.setText(wholeDeck.getBack(answers.get(1)));
			learnAnswer3.setText(wholeDeck.getBack(answers.get(2)));
		}else if(r==2) {
			learnAnswer0.setText(wholeDeck.getBack(answers.get(0)));
			learnAnswer1.setText(wholeDeck.getBack(answers.get(1)));
			learnAnswer2.setText(deck.getBack(currentQCard));
			learnAnswer3.setText(wholeDeck.getBack(answers.get(2)));
		}else{
			learnAnswer0.setText(wholeDeck.getBack(answers.get(0)));
			learnAnswer1.setText(wholeDeck.getBack(answers.get(1)));
			learnAnswer2.setText(wholeDeck.getBack(answers.get(2)));
			learnAnswer3.setText(deck.getBack(currentQCard));
		}
		eraseAnswerHighlights();
	}
	
	public void highlightAnswers() {
		wrongColor = "-fx-border-color: #FC3808; -fx-background-color: #FAF3ED;";
		String correctColor = "-fx-border-color: #059100; -fx-background-color: #DFF5DA;";
		if(learnCurrentAnswer==0) {
			learnAnswer0.setStyle(correctColor);
			learnAnswer1.setStyle(wrongColor);
			learnAnswer2.setStyle(wrongColor);
			learnAnswer3.setStyle(wrongColor);
		}else if(learnCurrentAnswer==1) {
			learnAnswer0.setStyle(wrongColor);
			learnAnswer1.setStyle(correctColor);
			learnAnswer2.setStyle(wrongColor);
			learnAnswer3.setStyle(wrongColor);
		}else if(learnCurrentAnswer==2) {
			learnAnswer0.setStyle(wrongColor);
			learnAnswer1.setStyle(wrongColor);
			learnAnswer2.setStyle(correctColor);
			learnAnswer3.setStyle(wrongColor);
		}else{
			learnAnswer0.setStyle(wrongColor);
			learnAnswer1.setStyle(wrongColor);
			learnAnswer2.setStyle(wrongColor);
			learnAnswer3.setStyle(correctColor);
		}
		
	}
	
	public void eraseAnswerHighlights() {
		learnAnswer0.setStyle(answerDefaultColor);
		learnAnswer1.setStyle(answerDefaultColor);
		learnAnswer2.setStyle(answerDefaultColor);
		learnAnswer3.setStyle(answerDefaultColor);
	}
	
	public void learnLoadTextModeQuestion() {
		learnModePane.getChildren().remove(learnModeTextField);
		learnModePane.getChildren().remove(learnModeNextButton);
		learnModePane.getChildren().remove(learnAnswer);
		learnModePane.getChildren().remove(learnThisCorrectButton);
		
		learnModePane.getChildren().add(learnModeTextField);
		learnModePane.getChildren().remove(learnAnswer0);
		learnModePane.getChildren().remove(learnAnswer1);
		learnModePane.getChildren().remove(learnAnswer2);
		learnModePane.getChildren().remove(learnAnswer3);
		learnModePane.getChildren().remove(learnModeNextButton);
		learnQuestion.setText(deck.getFront(currentQCard));
	}
	
	public void answeredCorrectly() {
		int i = wholeDeck.getIndexOf(deck.getFront(currentQCard));
		if(isLearnMatchMode) {
			wholeDeck.addMatchCorrect(wholeDeck.getIndexOf(deck.getFront(currentQCard)), 1);
			
			
			if(wholeDeck.getMatchCorrect(i)>=1) { // CHANGE
				deck.removeCard(currentQCard);
				currentQCard--;
			}
			
			currentQCard++;
			learnedCardCount++;
		}else {
			wholeDeck.addTextCorrect(wholeDeck.getIndexOf(deck.getFront(currentQCard)), 1);
			
			
			if(wholeDeck.getTextCorrect(i)>=2) { // CHANGE
				deck.removeCard(currentQCard);
				currentQCard--;
			}
			
			currentQCard++;
			learnedCardCount++;
		}
	}
	
	public Deck newSubDeck(boolean isMatchMode) {
		if(deck.getSize()<5) {
			
		}
		Deck d = new Deck();
		if(!isMatchMode) {
			learnCurrentDeckStart +=5;
		}
		d = wholeDeck.giveNextFive(learnCurrentDeckStart);
		d.printDeck();
		return d;
	}

}
