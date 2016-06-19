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
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class AdjustmentController implements Initializable
{
    @FXML
    private ComboBox<String> blockComboBox;
    @FXML
    private RadioButton SingleRadioButton;
    @FXML
    private RadioButton DoubleRadioButton;
    @FXML
    private ComboBox<String> AvailableRooms;
    @FXML
    private Font x1;
    @FXML
    private Button submitbutton;
    @FXML
    private Button cancelButton;
    static ObservableList<String> blocksObservableList = FXCollections.observableArrayList("A Block", "B Block", "C Block");
    static ObservableList<String> availableroomObservableList = FXCollections.observableArrayList();
    static ResultSet availrooms;
    ToggleGroup singledoubleToggleGroup;
    @FXML
    private Text ErrorLabel;
    static Statement statement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            statement = MainSceneController.connection.createStatement();
        } catch (SQLException ex)
        {
        }
        blockComboBox.setItems(blocksObservableList);
        AvailableRooms.setDisable(true);
        AvailableRooms.setItems(availableroomObservableList);
        SingleRadioButton.setDisable(true);
        DoubleRadioButton.setDisable(true);
        blockComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                if (SingleRadioButton.isSelected())
                {
                    try
                    {
                        availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=1 and doubleroom=0 and adjusted=0");
                        availableroomObservableList.clear();
                        while (availrooms.next())
                        {
                            availableroomObservableList.add(availrooms.getString("roomno"));
                        }
                    } catch (SQLException ex)
                    {
                    }
                    AvailableRooms.setDisable(false);
                }
                if (DoubleRadioButton.isSelected())
                {
                    try
                    {
                        availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=1 and doubleroom=1 and adjusted=0");
                        availableroomObservableList.clear();
                        while (availrooms.next())
                        {
                            String roomno = availrooms.getString("roomno");
                            roomno = roomno.substring(0, roomno.length() - 1);
                            if (!availableroomObservableList.contains(roomno))
                            {
                                availableroomObservableList.add(roomno);
                            }
                        }
                    } catch (SQLException ex)
                    {
                    }
                    AvailableRooms.setDisable(false);
                }
                SingleRadioButton.setDisable(false);
                DoubleRadioButton.setDisable(false);

            }
        });

        SingleRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                try
                {
                    availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=1 and doubleroom=0 and adjusted=0");
                    availableroomObservableList.clear();
                    while (availrooms.next())
                    {
                        availableroomObservableList.add(availrooms.getString("roomno"));
                    }
                } catch (SQLException ex)
                {
                }
                AvailableRooms.setDisable(false);
            }
        });

        DoubleRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                try
                {
                    availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=1  and doubleroom=1 and adjusted=0");
                    availableroomObservableList.clear();
                    while (availrooms.next())
                    {
                        String roomno = availrooms.getString("roomno");
                        roomno = roomno.substring(0, roomno.length() - 1);
                        if (!availableroomObservableList.contains(roomno))
                        {
                            availableroomObservableList.add(roomno);
                        }
                    }
                } catch (SQLException ex)
                {
                }
                AvailableRooms.setDisable(false);
            }
        });

        singledoubleToggleGroup = new ToggleGroup();
        SingleRadioButton.setToggleGroup(singledoubleToggleGroup);
        DoubleRadioButton.setToggleGroup(singledoubleToggleGroup);

        ErrorLabel.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                double rightwidht = MainProgramSceneController.RightSideofSplitPane.getWidth();
                ErrorLabel.setLayoutX((rightwidht / 2) - (ErrorLabel.getText().length() * 5));
                FadeTransition errorFadeTransition = new FadeTransition(Duration.millis(5000), ErrorLabel);
                errorFadeTransition.setFromValue(1);
                errorFadeTransition.setToValue(0);
                errorFadeTransition.play();
            }
        });


    }

    @FXML
    private void AllotToNewButtonAction(ActionEvent event)
    {

        ErrorLabel.setText("");
        if (blockComboBox.getSelectionModel().isEmpty())
        {
            blockComboBox.requestFocus();
            ErrorLabel.setText("Select Block First");
            return;
        }
        if (!SingleRadioButton.isSelected() && !DoubleRadioButton.isSelected() || AvailableRooms.getSelectionModel().isEmpty())
        {
            ErrorLabel.setText("Select Room to make adjustment in");
            return;
        }
        try
        {
            statement.execute("insert into rooms(block,roomno,doubleroom,filled) values('" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "','" + AvailableRooms.getSelectionModel().getSelectedItem() + "E" + "','" + adjustint() + "', 0)");
            statement.execute("update rooms set adjusted=1 where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and roomno like '" + AvailableRooms.getSelectionModel().getSelectedItem() + "%" + "' ");
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }

        try
        {
            String roomno = AvailableRooms.getSelectionModel().getSelectedItem();
            availableroomObservableList.clear();
            int acc = 0;
            if (SingleRadioButton.isSelected())
            {
                acc = 1;
            }

            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("AllotNewRoom.fxml"))));
            AllotNewRoomController.Adjust(blockComboBox.getSelectionModel().getSelectedItem(), roomno + "E", acc);

        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void CancelButtonAction(ActionEvent event)
    {
        try
        {
            availableroomObservableList.clear();
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("RightSideMain.fxml"))));
        } catch (IOException ex)
        {
        }
    }

    char block(String blockname)
    {
        if ("A Block".equals(blockname))
        {
            return 'A';
        }
        if ("B Block".equals(blockname))
        {
            return 'B';
        }
        return 'C';
    }

    int adjustint()
    {
        if (DoubleRadioButton.isSelected())
        {
            return 3;
        } else
        {
            return 2;
        }
    }
}
