/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class ChangepasswordController implements Initializable
{
    @FXML
    private Font x1;
    @FXML
    private PasswordField oldpasswordTextFeild;
    @FXML
    private PasswordField newpasswordTextFeild;
    @FXML
    private PasswordField confirmnewTextFeild;
    @FXML
    private Font x2;
    @FXML
    private Text errorLabel;
    @FXML
    private Button confirmButton;
    static char selectedUser;
    static Statement statement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {  
        try
        {
            statement=MainSceneController.connection.createStatement();
        } catch (SQLException ex)
        {
        }
        errorLabel.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                double confirmbuttonMP=confirmButton.getLayoutX()+confirmButton.getWidth()/2;
                errorLabel.setLayoutX(confirmbuttonMP-errorLabel.getText().length()*3.75);
                FadeTransition errorFadeTransition = new FadeTransition(Duration.millis(2000), errorLabel);
                errorFadeTransition.setFromValue(1);
                errorFadeTransition.setToValue(0);
                errorFadeTransition.play();             
            }
        });

        errorLabel.opacityProperty().addListener(new ChangeListener<Number> () {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                if(("Password Successfully Changed".equals(errorLabel.getText())) && (errorLabel.opacityProperty().doubleValue()==0))
                {
                   MainProgramSceneController.passwordStage.close(); 
                   MainProgramSceneController.mainTopAnchorPane.effectProperty().setValue(null);
                }
            }
        });
    }

    @FXML
    private void confirmButtonAction(ActionEvent event)
    {
        errorLabel.setText("");
        if ("".equals(oldpasswordTextFeild.getText()))
        {
            errorLabel.setText("Enter Old Password");
            oldpasswordTextFeild.requestFocus();
            return;
        }

        ResultSet passResultSet;
        String pass = "";
        try
        {
            passResultSet = statement.executeQuery("select pass from admin where user='" + selectedUser + "'");
            passResultSet.next();
            pass = passResultSet.getString("pass");
        } catch (SQLException ex)
        {
        }
        if (!pass.equals(oldpasswordTextFeild.getText()))
        {
            errorLabel.setText("Old Password do not match");
            oldpasswordTextFeild.requestFocus();
            return;
        }

        if ("".equals(newpasswordTextFeild.getText()))
        {
            errorLabel.setText("Enter New Password");
            newpasswordTextFeild.requestFocus();
            return;
        }
        if ("".equals(confirmnewTextFeild.getText()) || !newpasswordTextFeild.getText().equals(confirmnewTextFeild.getText()))
        {
            errorLabel.setText("Password do not match.");
            confirmnewTextFeild.requestFocus();
            return;
        }
        if (newpasswordTextFeild.getText().length() > 20)
        {
            errorLabel.setText("New Password Exceeds Maximum Length");
            newpasswordTextFeild.requestFocus();
            return;
        }
        if (newpasswordTextFeild.getText().length() < 5)
        {
            errorLabel.setText("New Password less than Minimum Length");
            newpasswordTextFeild.requestFocus();
            return;
        }
        try
        {
            statement.execute("update admin set pass='"+newpasswordTextFeild.getText()+"' where user='"+selectedUser+"'");
            errorLabel.setFill(Color.GREEN);
            errorLabel.setText("Password Successfully Changed");
            statement.execute("insert into activity(admin, string) values('h', 'Password Changed')");
        } catch (SQLException ex)
        {
        }
    }
    
    
}
