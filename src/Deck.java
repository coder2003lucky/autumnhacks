import java.util.ArrayList;

public class Deck {

	private ArrayList<String> frontSide;
	private ArrayList<String> backSide;
	private ArrayList<Integer> numMatchCorrect;
	private ArrayList<Integer> numMatchWrong;
	private ArrayList<Integer> numTextCorrect;
	private ArrayList<Integer> numTextWrong;
	
	public Deck() {
		frontSide = new ArrayList<String>();
		backSide = new ArrayList<String>();
		numMatchCorrect = new ArrayList<Integer>();
		numMatchWrong = new ArrayList<Integer>();
		numTextCorrect = new ArrayList<Integer>();
		numTextWrong = new ArrayList<Integer>();
	}
	
	public void addCard(String front, String back) {
		frontSide.add(front);
		backSide.add(back);
		numMatchCorrect.add(0);
		numMatchWrong.add(0);
		numTextCorrect.add(0);
		numTextWrong.add(0);
		
	}
	
	public void addCard(String front, String back, int correct, int wrong) {
		frontSide.add(front);
		backSide.add(back);
		numMatchCorrect.add(0);
		numMatchWrong.add(0);
		numTextCorrect.add(0);
		numTextWrong.add(0);
	}
	
	public String getFront(int i) {
		return frontSide.get(i);
	}

	public String getBack(int i) {
		return backSide.get(i);
	}

	public int getMatchCorrect(int i) {
		return numMatchCorrect.get(i);
	}

	public int getMatchWrong(int i) {
		return numMatchWrong.get(i);
	}
	
	public int getTextCorrect(int i) {
		return numTextCorrect.get(i);
	}

	public int getTextWrong(int i) {
		return numTextWrong.get(i);
	}
	
	public boolean isEmpty() {
		return frontSide.isEmpty();
	}
	
	public void printDeck() {
		for(int i = 0; i<frontSide.size(); i++) {
			System.out.println(frontSide.get(i)+"\t"+backSide.get(i));
		}
	}
	
	public int getSize() {
		return frontSide.size();
	}
	
	public Deck giveNextFive(int startingFrom) {
		Deck d = new Deck();
		for(int i = startingFrom; i<startingFrom+5 && i<getSize(); i++) {
			d.addCard(this.getFront(i), this.getBack(i), this.getMatchCorrect(i), this.getMatchWrong(i));
		}
		return d;
	}
	
	public int getIndexOf(String front) {
		return frontSide.indexOf(front);
	}
	
	public void addMatchCorrect(int index, int amount) {
		numMatchCorrect.set(index, numMatchCorrect.get(index)+amount);
	}
	
	public void addTextCorrect(int index, int amount) {
		numTextCorrect.set(index, numTextCorrect.get(index)+amount);
	}
	
	public void addMatchWrong(int index, int amount) {
		numMatchWrong.set(index, numMatchWrong.get(index)+amount);
	}
	
	public void addTextWrong(int index, int amount) {
		numTextWrong.set(index, numTextWrong.get(index)+amount);
	}
	
	public void removeCard(int index) {
		frontSide.remove(index);
		backSide.remove(index);
		numMatchCorrect.remove(index);
		numMatchWrong.remove(index);
	}
	
}
