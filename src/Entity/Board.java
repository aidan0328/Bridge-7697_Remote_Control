package Entity;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class Board {

	public String name;
	private ArrayList<KeyCode> keyList;
	private int score;
	public Board() {
		this.keyList = new ArrayList<>();
		this.score=0;
		this.name = "";
	}
	public Board(String name) {

		this.keyList = new ArrayList<>();
		this.score=0;
		this.name = name;
	}
	
	public KeyCode getKeyCode(int index) {
		
		return (index<keyList.size())?keyList.get(index):KeyCode.UNDEFINED;
	}
	
	public void addKeyEvent(KeyCode keycode) {
		keyList.add(keycode);
	}
	public void setKeyList(ArrayList<KeyCode> list) {
		this.keyList = list;
		
	}
	public String getKeyValue(int btnNo) {
		
		return keyList.get(btnNo).toString();
	}
}
