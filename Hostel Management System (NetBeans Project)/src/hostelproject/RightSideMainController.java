/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class RightSideMainController implements Initializable
{
    @FXML
    private Font x1;
    @FXML
    private Font x2;
    @FXML
    private Text AEmptyDouble;
    @FXML
    private Text AEmptySingle;
    @FXML
    private Text BEmptydouble;
    @FXML
    private Text CEmptydouble;
    @FXML
    private Text totalEmptyDouble;
    @FXML
    private Text totalEmptySingle;
    private Rectangle rectangleallot;
    @FXML
    private Text AAdjustment;
    @FXML
    private Text BAdjustment;
    @FXML
    private Text CAdjustment;
    @FXML
    private Text TotalAdjustment;
        static Text amountLabel;
    @FXML
    private Color x3;

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
        figureUpdate();
    }

    @FXML
    private void AllotNewRoomAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("AllotNewRoom.fxml"))));
        } catch (IOException ex)
        {
        }
    }

    void figureUpdate()
    {
        try
        {
            ResultSet temp;

            temp = statement.executeQuery("select count(*) from rooms where block='a' and filled=0 and doubleroom=1");
            temp.next();
            AEmptyDouble.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where block='a' and filled=0 and doubleroom=0");
            temp.next();
            AEmptySingle.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where block='b' and filled=0 and doubleroom=1");
            temp.next();
            BEmptydouble.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where block='c' and filled=0 and doubleroom=1");
            temp.next();
            CEmptydouble.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where filled=0 and doubleroom=1");
            temp.next();
            totalEmptyDouble.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where filled=0 and doubleroom=0");
            temp.next();
            totalEmptySingle.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where block='a' and (doubleroom=2 or doubleroom=3)");
            temp.next();
            AAdjustment.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where block='b' and (doubleroom=2 or doubleroom=3)");
            temp.next();
            BAdjustment.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where block='c' and (doubleroom=2 or doubleroom=3)");
            temp.next();
            CAdjustment.setText("" + temp.getInt(1));

            temp = statement.executeQuery("select count(*) from rooms where (doubleroom=2 or doubleroom=3)");
            temp.next();
            TotalAdjustment.setText("" + temp.getInt(1));
            
            
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void EmptyRoomAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("emptyRooms.fxml"))));
        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void SwapRoomsAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("swaprooms.fxml"))));
        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void ShiftToEmptyAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("shiftToEmptyRoom.fxml"))));
        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void MakeAdjustmentAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("adjustment.fxml"))));
        } catch (IOException ex)
        {
        }

    }

    @FXML
    private void HelpButtonAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("Help.fxml"))));
        } catch (IOException ex)
        {
        }
    }
}
