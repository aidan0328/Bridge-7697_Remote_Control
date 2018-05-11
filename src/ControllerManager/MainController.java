package ControllerManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
public class MainController implements Initializable {

	@FXML
	private Button BtnApply;
	@FXML
	private Label board1Label, wifi_Info;
	@FXML
	private ListView<String> boardList;

	@FXML
	private TextField TextKeyCodeInput1, TextKeyCodeInput2, TextKeyCodeInput3, TextKeyCodeInput4, TextKeyCodeInput5,TextKeyCodeInput6, TextKeyCodeInput7, TextKeyCodeInput8, sysInfoText;
	private ArrayList<TextField> inputTextList;

	public static final int BUTTON_SIZE = 8;
	private KeyCode[] tempKeyCodeArray = new KeyCode[BUTTON_SIZE];
	ConnectionManager server;
	BoardManager boardManager;
	Thread serverThread;
	String currentBoardName = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		socketServeInit();
		boardManager = BoardManager.getBoardManager();
		boardList.setItems(boardManager.getObservableList());

		boardList.setOnMouseClicked(e -> {
			String boardID = boardList.getSelectionModel().getSelectedItem();
			showBoardInfo(boardID);
		});

		inputTextList = new ArrayList<>();
		inputTextList.add(TextKeyCodeInput1);
		inputTextList.add(TextKeyCodeInput2);
		inputTextList.add(TextKeyCodeInput3);
		inputTextList.add(TextKeyCodeInput4);
		inputTextList.add(TextKeyCodeInput5);
		inputTextList.add(TextKeyCodeInput6);
		inputTextList.add(TextKeyCodeInput7);
		inputTextList.add(TextKeyCodeInput8);
	}

	@FXML
	private void OnClickListener_ADD(ActionEvent event) {
		// boards.add(UUID.randomUUID().toString());
		currentBoardName = UUID.randomUUID().toString().substring(0, 5);
		boardManager.add(currentBoardName);
	}

	@FXML
	private void OnClickListener_Apply(ActionEvent event) {
		ArrayList<KeyCode> keylist = new ArrayList<>();

		for (KeyCode code : tempKeyCodeArray) {
			keylist.add(code);
		}

		boardManager.setKeyList(boardList.getSelectionModel().getSelectedItem(), keylist);
	}

	@FXML
	private void textfieldTyped(KeyEvent keyEvent) {
		TextField textField = (TextField) keyEvent.getSource();
		textField.clear();
		textField.setText(keyEvent.getCode().getName());
		tempKeyCodeArray[inputTextList.indexOf(textField)] =keyEvent.getCode();
		
	}

	Runnable updateSysInfo = null;
	String sysMsg = "";

	private void socketServeInit() {
		server = new ConnectionManager(8765, this);
		serverThread = new Thread(server);
		serverThread.setDaemon(true);
		serverThread.start();
		updateSysInfo = () -> sysInfoText.setText(sysMsg);
	}

	public void showBoardInfo(String boardID) {

		Platform.runLater(() -> {
			board1Label.setText(boardID);
			for (int i = 0; i < BUTTON_SIZE; i++) {
				tempKeyCodeArray[i] = BoardManager.getKeyCode(boardID, i);
				inputTextList.get(i).setText(tempKeyCodeArray[i].getName());
			}

		});
	}

	public void showWifiInfo(String msg) {
		Platform.runLater(() -> wifi_Info.setText(msg));
	}

	public void showSysInfo(String msg) {
		sysMsg = msg;
		Platform.runLater(updateSysInfo);
	}
}
