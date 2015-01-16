/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlecityput;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;


/**
 *
 * @author szymon
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML private TextField testTextField;
    @FXML private AnchorPane battlefield;
    @FXML private Rectangle field;
    @FXML private Text levelcounter;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        battlefield.setVisible(false);
        field.setVisible(false);
    }

    
    @FXML
    private void handleMainMenuKeyPressedAction(KeyEvent event) {
        System.out.print("Key ");
        System.out.print(event.getCode());
        System.out.println(" pressed!");
        if(event.getCode() == KeyCode.ENTER)
        {
            battlefield.setVisible(true);
            testTextField.setonke
            testTextField.setOnKeyPressed(handleBattleFieldBeforeKeyPressedAction);
        }
    }
    @FXML
    private void handleBattleFieldBeforeKeyPressedAction(KeyEvent event) {
        System.out.print("Key ");
        System.out.print(event.getCode());
        System.out.println(" pressed!");
        if(event.getCode() == KeyCode.UP)
            levelcounter.setText(Integer.toString(Integer.parseInt(levelcounter.getText()) + 1));
            
    }

    

    
}
