/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import static hostelproject.ChangepasswordController.statement;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class EmptyRoomsController implements Initializable
{
    @FXML
    private ComboBox<String> firstblock;
    @FXML
    private RadioButton firstSingleRadio;
    @FXML
    private RadioButton firstDoubleRadio;
    @FXML
    private ComboBox<String> firstfilledCombo;
    @FXML
    private Font x1;
    @FXML
    private Font x2;
    @FXML
    private AnchorPane firstdetails;
    @FXML
    private Text firstnameLabel;
    @FXML
    private Text firstrollnoLabel;
    @FXML
    private Button swapButton;
    @FXML
    private Text ErrorLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane particlarRoomAnchor;
    @FXML
    private AnchorPane blockEmptyAnchor;
    @FXML
    private RadioButton ParticularRoomRadio;
    @FXML
    private RadioButton EntireBlockRadio;
    @FXML
    private RadioButton entirehostelRadio;
    @FXML
    private ComboBox<String> EntireBlockCombo;
    boolean adjustDeletion;
    static ObservableList<String> filledroomObservableList = FXCollections.observableArrayList();
    ToggleGroup firstToggleGroup;
    ToggleGroup allToggleGroup;
    ResultSet first;
    ResultSet availrooms;
    static ObservableList<String> blocksObservableList = FXCollections.observableArrayList("A Block", "B Block", "C Block");
    Statement statement;

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
        particlarRoomAnchor.setDisable(true);
        blockEmptyAnchor.setDisable(true);

        allToggleGroup = new ToggleGroup();
        ParticularRoomRadio.setToggleGroup(allToggleGroup);
        EntireBlockRadio.setToggleGroup(allToggleGroup);
        entirehostelRadio.setToggleGroup(allToggleGroup);

        allToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)
            {
                if (ParticularRoomRadio.isSelected())
                {
                    blockEmptyAnchor.setDisable(true);
                    particlarRoomAnchor.setDisable(false);
                }

                if (EntireBlockRadio.isSelected())
                {
                    particlarRoomAnchor.setDisable(true);
                    blockEmptyAnchor.setDisable(false);
                    firstblock.getSelectionModel().clearSelection();
                }
                if (entirehostelRadio.isSelected())
                {
                    particlarRoomAnchor.setDisable(true);
                    blockEmptyAnchor.setDisable(true);
                    firstblock.getSelectionModel().clearSelection();
                }
            }
        });

        firstblock.setItems(blocksObservableList);
        EntireBlockCombo.setItems(blocksObservableList);

        firstblock.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                firstDoubleRadio.setDisable(false);
                firstSingleRadio.setDisable(false);
                firstDoubleRadio.setSelected(false);
                firstSingleRadio.setSelected(false);
                filledroomObservableList.clear();
                firstdetails.setVisible(false);
                firstfilledCombo.setDisable(true);
            }
        });


        firstSingleRadio.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                try
                {
                    availrooms = statement.executeQuery("select roomno from rooms where block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "' and filled=1 and (doubleroom=0 or doubleroom=2)");
                    filledroomObservableList.clear();
                    while (availrooms.next())
                    {
                        filledroomObservableList.add(availrooms.getString("roomno"));
                    }
                } catch (SQLException ex)
                {
                }
                firstfilledCombo.setDisable(false);
            }
        });

        firstDoubleRadio.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                try
                {
                    availrooms = statement.executeQuery("select roomno from rooms where block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "' and filled=1 and (doubleroom=1 or doubleroom=3)");
                    filledroomObservableList.clear();
                    while (availrooms.next())
                    {
                        filledroomObservableList.add(availrooms.getString("roomno"));
                    }
                } catch (SQLException ex)
                {
                }
                firstfilledCombo.setDisable(false);
            }
        });
        firstToggleGroup = new ToggleGroup();
        firstDoubleRadio.setToggleGroup(firstToggleGroup);
        firstSingleRadio.setToggleGroup(firstToggleGroup);
        firstDoubleRadio.setDisable(true);
        firstSingleRadio.setDisable(true);
        firstfilledCombo.setItems(filledroomObservableList);
        firstfilledCombo.setDisable(true);
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

        firstfilledCombo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                try
                {
                    first = statement.executeQuery("select * from rooms where block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "' and roomno='" + firstfilledCombo.getSelectionModel().getSelectedItem() + "'");
                    first.next();
                    firstnameLabel.setText(first.getString("name"));
                    firstrollnoLabel.setText("" + first.getInt("rollno"));
                    firstdetails.setVisible(true);
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });


    }

    @FXML
    private void CancelButtonAction(ActionEvent event)
    {
        try
        {
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("RightSideMain.fxml"))));
        } catch (IOException ex)
        {
            System.out.println(ex.toString());
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

    @FXML
    private void EmptyButtonAction(ActionEvent event)
    {
        try
        {
            first = statement.executeQuery("select * from rooms where block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "' and roomno='" + firstfilledCombo.getSelectionModel().getSelectedItem() + "'");
            first.next();
        } catch (SQLException ex)
        {
        }

        ErrorLabel.setText("");
        if (!ParticularRoomRadio.isSelected() && !EntireBlockRadio.isSelected() && !entirehostelRadio.isSelected())
        {
            ErrorLabel.setText("Make Some Selection First");
            return;
        }
        if (firstfilledCombo.getSelectionModel().isEmpty() && ParticularRoomRadio.isSelected())
        {
            ErrorLabel.setText("Select Room to Empty");
            return;
        }
        if (EntireBlockRadio.isSelected() && EntireBlockCombo.getSelectionModel().isEmpty())
        {
            ErrorLabel.setText("Select Block to Empty");
            return;
        }

        if (ParticularRoomRadio.isSelected())
        {
            try
            {
                if (first.getInt("due") != 0)
                {
                    ErrorLabel.setText("Mess Bill Pending !!");
                    return;
                }
                if (first.getString("roomno").contains("E"))
                {
                    statement.execute("delete from rooms where roomno='" + firstfilledCombo.getSelectionModel().getSelectedItem() + "' and block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "'");
                    statement.execute("update rooms set adjusted=0 where block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "' and roomno like '" + firstfilledCombo.getSelectionModel().getSelectedItem().replace("E", "") + "%" + "'");
                } else
                {
                    statement.execute("update rooms set filled=0 where roomno='" + firstfilledCombo.getSelectionModel().getSelectedItem() + "' and block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "'");
                }
                statement.execute("insert into activity(admin, string) values('h', '"+"Room No. "+firstfilledCombo.getSelectionModel().getSelectedItem()+", Block "+block(firstblock.getSelectionModel().getSelectedItem())+" Emptied"+"')");
            } catch (SQLException ex)
            {
                System.out.println(ex.toString());
            }
        }

        if (EntireBlockRadio.isSelected())
        {
            try
            {
                if (duebill(block(EntireBlockCombo.getSelectionModel().getSelectedItem())))
                {
                    ErrorLabel.setText("Emptying Unsuccessful !! Mess Bill Pending for few Rooms");
                    return;
                }
                statement.execute("delete from rooms where (adjusted=2 or adjusted=3) and block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "'");
                statement.execute("update rooms set filled=0, adjusted=0 where block='" + block(EntireBlockCombo.getSelectionModel().getSelectedItem()) + "'");
                statement.execute("insert into activity(admin, string) values('h', '"+"Block "+block(firstblock.getSelectionModel().getSelectedItem())+" Emptied"+"')");
            } catch (SQLException ex)
            {
                System.out.println(ex.toString());
            }
        }

        if (entirehostelRadio.isSelected())
        {
            try
            {
                if (duebill('a') || duebill('b') || duebill('c'))
                {
                    ErrorLabel.setText("Emptying Unsuccessful !! Mess Bill Pending for few Rooms");
                    return;
                }

                statement.execute("delete from rooms where adjusted=2 or adjusted=3");
                statement.execute("update rooms set filled=0, adjusted=0");
                statement.execute("insert into activity(admin, string) values('h', 'Hostel Emptied')");

            } catch (SQLException ex)
            {
                System.out.println(ex.toString());
            }
        }

        ErrorLabel.setFill(Color.GREEN);
        ErrorLabel.setText("Emptying Successful !!");
        MainProgramSceneController.updateSearchTable();

        swapButton.setDisable(true);
        cancelButton.setDisable(true);

        Node currentNode = MainProgramSceneController.RightSideofSplitPane.getChildren().get(0);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1300), currentNode);
        fadeTransition.play();
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t)
            {
                try
                {
                    MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("RightSideMain.fxml"))));
                } catch (IOException ex)
                {
                    ex.toString();
                }

            }
        });
    }

    boolean duebill(char block)
    {
        try
        {
            ResultSet temp = statement.executeQuery("select count(*) from rooms where block='" + block + "' and not due=0");
            temp.next();
            if (temp.getInt(1) != 0)
            {
                return true;
            }
        } catch (SQLException ex)
        {
        }
        return false;
    }
}
