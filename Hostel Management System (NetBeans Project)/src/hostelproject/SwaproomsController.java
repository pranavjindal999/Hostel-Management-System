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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
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
public class SwaproomsController implements Initializable
{
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
    private AnchorPane seconddetails;
    @FXML
    private Text secondNameLabel;
    @FXML
    private Text secondRollnoLabel;
    @FXML
    private Text ErrorLabel;
    static ObservableList<String> filledroomObservableList = FXCollections.observableArrayList();
    static ObservableList<String> filledroomObservableListSec = FXCollections.observableArrayList();
    static ObservableList<String> blocksObservableList = FXCollections.observableArrayList("A Block", "B Block", "C Block");
    @FXML
    private ComboBox<String> firstblock;
    @FXML
    private RadioButton firstSingleRadio;
    @FXML
    private RadioButton firstDoubleRadio;
    @FXML
    private ComboBox<String> firstfilledCombo;
    @FXML
    private ComboBox<String> secondBlock;
    @FXML
    private RadioButton secondSingleRadio;
    @FXML
    private ComboBox<String> secondfilledCombo;
    @FXML
    private RadioButton secondDoubleRadio;
    ToggleGroup firstToggleGroup;
    ToggleGroup secondToggleGroup;
    ResultSet availrooms;
    @FXML
    private Button swapButton;
    @FXML
    private Button cancelButton;
    ResultSet first;
    ResultSet second;
    Statement secondStatement;
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

        firstblock.setItems(blocksObservableList);
        secondBlock.setItems(blocksObservableList);
        try
        {
            secondStatement = MainSceneController.connection.createStatement();
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
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

        secondBlock.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {

                secondDoubleRadio.setDisable(false);
                secondSingleRadio.setDisable(false);
                secondDoubleRadio.setSelected(false);
                secondSingleRadio.setSelected(false);
                filledroomObservableListSec.clear();
                seconddetails.setVisible(false);
                secondfilledCombo.setDisable(true);
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

        secondSingleRadio.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                try
                {
                    availrooms = secondStatement.executeQuery("select roomno from rooms where block='" + block(secondBlock.getSelectionModel().getSelectedItem()) + "' and filled=1 and (doubleroom=0 or doubleroom=2)");
                    filledroomObservableListSec.clear();
                    while (availrooms.next())
                    {
                        filledroomObservableListSec.add(availrooms.getString("roomno"));
                    }
                } catch (SQLException ex)
                {
                }
                secondfilledCombo.setDisable(false);
            }
        });

        secondDoubleRadio.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                try
                {
                    availrooms = secondStatement.executeQuery("select roomno from rooms where block='" + block(secondBlock.getSelectionModel().getSelectedItem()) + "' and filled=1 and (doubleroom=1 or doubleroom=3)");
                    filledroomObservableListSec.clear();
                    while (availrooms.next())
                    {
                        filledroomObservableListSec.add(availrooms.getString("roomno"));
                    }
                } catch (SQLException ex)
                {
                }
                secondfilledCombo.setDisable(false);
            }
        });

        firstToggleGroup = new ToggleGroup();
        firstDoubleRadio.setToggleGroup(firstToggleGroup);
        firstSingleRadio.setToggleGroup(firstToggleGroup);

        secondToggleGroup = new ToggleGroup();
        secondSingleRadio.setToggleGroup(secondToggleGroup);
        secondDoubleRadio.setToggleGroup(secondToggleGroup);

        firstDoubleRadio.setDisable(true);
        firstSingleRadio.setDisable(true);
        secondDoubleRadio.setDisable(true);
        secondSingleRadio.setDisable(true);

        firstfilledCombo.setItems(filledroomObservableList);
        secondfilledCombo.setItems(filledroomObservableListSec);
        firstfilledCombo.setDisable(true);
        secondfilledCombo.setDisable(true);

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

        secondfilledCombo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                try
                {
                    second = secondStatement.executeQuery("select * from rooms where block='" + block(secondBlock.getSelectionModel().getSelectedItem()) + "' and roomno='" + secondfilledCombo.getSelectionModel().getSelectedItem() + "'");
                    second.next();
                    secondNameLabel.setText(second.getString("name"));
                    secondRollnoLabel.setText("" + second.getInt("rollno"));
                    seconddetails.setVisible(true);
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
    }

    @FXML
    private void swapButtonAction(ActionEvent event)
    {
        ErrorLabel.setText("");
        if (firstfilledCombo.getSelectionModel().isEmpty() || secondfilledCombo.getSelectionModel().isEmpty())
        {
            ErrorLabel.setText("Select Rooms to Swap");
            return;
        }

        if (firstblock.getSelectionModel().getSelectedIndex() == secondBlock.getSelectionModel().getSelectedIndex() && firstfilledCombo.getSelectionModel().getSelectedIndex() == secondfilledCombo.getSelectionModel().getSelectedIndex())
        {
            ErrorLabel.setText("Swapping same rooms is illogical");
            return;
        }

        try
        {
            Statement temp = MainSceneController.connection.createStatement();
            temp.execute("update rooms set feeno='" + first.getInt("feeno") + "',name='" + first.getString("name") + "',father='" + first.getString("father") + "',mother='" + first.getString("mother") + "', rollno='" + first.getInt("rollno") + "',dob='" + first.getDate("dob") + "',mobile='" + first.getString("mobile") + "',address='" + first.getString("address") + "',city='" + first.getString("city") + "',state='" + first.getString("state") + "',vehicle='" + first.getInt("vehicle") + "',vehicleno='" + first.getString("vehicleno") + "',due='" + first.getInt("due") + "' where roomno='" + secondfilledCombo.getSelectionModel().getSelectedItem() + "' and block='" + block(secondBlock.getSelectionModel().getSelectedItem()) + "'");
            temp.execute("update rooms set feeno='" + second.getInt("feeno") + "',name='" + second.getString("name") + "',father='" + second.getString("father") + "',mother='" + second.getString("mother") + "', rollno='" + second.getInt("rollno") + "',dob='" + second.getDate("dob") + "',mobile='" + second.getString("mobile") + "',address='" + second.getString("address") + "',city='" + second.getString("city") + "',state='" + second.getString("state") + "',vehicle='" + second.getInt("vehicle") + "',vehicleno='" + second.getString("vehicleno") + "' , due='" + second.getInt("due") + "' where roomno='" + firstfilledCombo.getSelectionModel().getSelectedItem() + "' and block='" + block(firstblock.getSelectionModel().getSelectedItem()) + "'");
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }

        ErrorLabel.setFill(Color.GREEN);
        ErrorLabel.setText("Swapping Successful !!");
        MainProgramSceneController.updateSearchTable();
        try
        {
            statement.execute("insert into activity(admin, string) values('h', '"+"Room No. "+firstfilledCombo.getSelectionModel().getSelectedItem()+", Block "+block(firstblock.getSelectionModel().getSelectedItem())+" swapped with "+"Room No. "+secondfilledCombo.getSelectionModel().getSelectedItem()+", Block "+block(secondBlock.getSelectionModel().getSelectedItem())+"')");
        } catch (SQLException ex)
        {
        }


        swapButton.setDisable(true);
        cancelButton.setDisable(true);

        Node currentNode = MainProgramSceneController.RightSideofSplitPane.getChildren().get(0);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), currentNode);
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
}
