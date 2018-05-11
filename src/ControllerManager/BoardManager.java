package ControllerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Entity.Board;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;

public class BoardManager  {

	private static ObservableList<String> boardIDList;
	private static BoardManager self = new BoardManager();
	private static Map<String, Board> boardMap;
	private final static KeyCode[][] KEYSET_OF_PLAYERs= new KeyCode[][]{
		{KeyCode.DIGIT1,KeyCode.DIGIT2,KeyCode.DIGIT3,KeyCode.DIGIT4,KeyCode.DIGIT5,KeyCode.DIGIT6,KeyCode.DIGIT7,KeyCode.DIGIT8},
		{KeyCode.A,KeyCode.B,KeyCode.C,KeyCode.D,KeyCode.E,KeyCode.F,KeyCode.G,KeyCode.H},	//player1 default setting
		{KeyCode.I,KeyCode.J,KeyCode.K,KeyCode.L,KeyCode.M,KeyCode.N,KeyCode.O,KeyCode.P},  //player2 default setting
		{KeyCode.Q,KeyCode.R,KeyCode.S,KeyCode.T,KeyCode.U,KeyCode.V,KeyCode.W,KeyCode.X}
	}; 
	
	// json
	private BoardManager() {
		boardMap = new HashMap<>();
		boardIDList = FXCollections.observableList(new ArrayList<>());
	}

	public static BoardManager getBoardManager() {
		return self;
	}

	

	public static KeyCode getKeyCode(String boardID, int btnNo) {

		if (boardMap.containsKey(boardID)) {
			return boardMap.get(boardID).getKeyCode(btnNo);
		} else {

			return null;
		}
	}
	public static int getKeyNo(String boardID, int btnNo) {

		if (boardMap.containsKey(boardID) ) {
			return boardMap.get(boardID).getKeyCode(btnNo).getCode();
		} else {

			return -1;
		}
	}

	public static String getKeyEvent(String boardID, int btnNo) {
		if (boardMap.containsKey(boardID)) {
			
			return boardMap.get(boardID).getKeyValue(btnNo);
		} else {
			return "none";
		}
	}

	public boolean contain(String clientID) {
		return boardMap.containsKey(clientID);
	}

	public void add(String clientID) {
		Board board = new Board(clientID);
		if(boardIDList.size()<KEYSET_OF_PLAYERs.length) {
			for(KeyCode code:KEYSET_OF_PLAYERs[boardIDList.size()]) {
				board.addKeyEvent(code);
			}
		}else {
			for(KeyCode code:KEYSET_OF_PLAYERs[0]) {
				board.addKeyEvent(code);
			}
		}
		Platform.runLater( ()->boardIDList.add(clientID));
		
		boardMap.put(clientID, board);
	}

	public static String getBoardID(int i) {

		return boardIDList.get(0);
	}
	public void setKeyList(String currentBoardName, ArrayList<KeyCode> keylist) {
		boardMap.get(currentBoardName).setKeyList(keylist);
		
	}
	public ObservableList<String> getObservableList (){
		return boardIDList;
	}
	

}
