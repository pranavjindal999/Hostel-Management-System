/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class HelpController implements Initializable
{
    @FXML
    private Font x1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void closeHelpAction(ActionEvent event)
    {
         try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("RightSideMain.fxml"))));
        } catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }
}
