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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class ViewPayBillController implements Initializable
{
    @FXML
    private Font x1;
    @FXML
    private Color x2;
    @FXML
    private Text blockLabel;
    @FXML
    private Text roomnoLabel;
    @FXML
    private Text nameLabel;
    @FXML
    private Text dueLabel;
    @FXML
    private TextField amountpaidTextFeild;
    @FXML
    private Font x3;
    ResultSet temp;
    @FXML
    private Text errorLabel;
    @FXML
    private Button payButton;
    @FXML
    private TextField latefeeTextbox;
    @FXML
    private TextField percentTextBox;
    Statement statement;

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
                double confirmbuttonMP = payButton.getLayoutX() + payButton.getWidth() / 2;
                errorLabel.setLayoutX(confirmbuttonMP - errorLabel.getText().length() * 3.75);
                FadeTransition errorFadeTransition = new FadeTransition(Duration.millis(1300), errorLabel);
                errorFadeTransition.setFromValue(1);
                errorFadeTransition.setToValue(0);
                errorFadeTransition.play();
            }
        });

        errorLabel.opacityProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                if (("Successfully Paid".equals(errorLabel.getText())) && (errorLabel.opacityProperty().doubleValue() == 0))
                {
                    StudentDetailController.billStage.close();
                    MainProgramSceneController.mainTopAnchorPane.effectProperty().setValue(null);
                }
            }
        });
        blockLabel.setText(StudentDetailController.blockLabel.getText());
        roomnoLabel.setText(StudentDetailController.roomnoLabel.getText());
        nameLabel.setText(StudentDetailController.nameLabel.getText());
        refreshdueLabel();
    }

    @FXML
    private void PayButtonAction(ActionEvent event)
    {
        int newdue;
        errorLabel.setText("");
        if ("".equals(amountpaidTextFeild.getText()))
        {
            errorLabel.setText("Enter Paid Amount");
            amountpaidTextFeild.requestFocus();
            return;
        }
        try
        {
            newdue = Integer.parseInt(amountpaidTextFeild.getText());
            newdue = Integer.parseInt(dueLabel.getText()) - newdue;
        } catch (NumberFormatException e)
        {
            errorLabel.setText("Enter Valid Amount");
            amountpaidTextFeild.requestFocus();
            return;
        }
        try
        {
            statement.execute("update rooms set due='" + newdue + "' where block='" + blockLabel.getText().charAt(0) + "' and roomno='" + roomnoLabel.getText() + "'");
            errorLabel.setFill(Color.GREEN);
            errorLabel.setText("Successfully Paid");
            statement.execute("insert into activity(admin, string) values('h', '"+"Bill amount "+amountpaidTextFeild.getText()+" paid for Room No. "+roomnoLabel.getText()+", Block "+blockLabel.getText()+"')");
            amountpaidTextFeild.setText("");
            refreshdueLabel();
            if ("pending".equals(MainProgramSceneController.searchbox.getText().toLowerCase()))
            {
                MainProgramSceneController.updateSearchTable();
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }

    void refreshdueLabel()
    {
        try
        {
            temp = statement.executeQuery("select due from rooms where block='" + blockLabel.getText().charAt(0) + "' and roomno='" + roomnoLabel.getText() + "'");
            temp.next();
            dueLabel.setText("" + temp.getInt(1));
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }
}
