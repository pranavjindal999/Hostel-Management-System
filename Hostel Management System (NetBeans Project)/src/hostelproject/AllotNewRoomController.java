/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sun.util.calendar.CalendarUtils;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class AllotNewRoomController implements Initializable
{
    @FXML
    private Font x1;
    @FXML
    private TextField NameTextFeild;
    @FXML
    private TextField FatherNameTextFeild;
    @FXML
    private TextField MotherNameTextFeild;
    @FXML
    private TextField RollNumberTextFeild;
    @FXML
    private ComboBox<Integer> DateComboBox;
    @FXML
    private ComboBox<String> MonthCombobox;
    @FXML
    private ComboBox<Integer> YearCombobox;
    @FXML
    private TextField VehicleNumberTextFeild;
    @FXML
    private Text ErrorLabel;
    @FXML
    private CheckBox VehicleCheckBox;
    ObservableList<Integer> datesObservableList = FXCollections.observableArrayList();
    ObservableList<String> monthsObservableList = FXCollections.observableArrayList();
    ObservableList<Integer> yearObservableList = FXCollections.observableArrayList();
    ObservableList<String> statesObservableList = FXCollections.observableArrayList();
    ObservableList<String> blocksObservableList = FXCollections.observableArrayList("A Block", "B Block", "C Block");
    static ObservableList<String> availableroomObservableList = FXCollections.observableArrayList();
    static ResultSet availrooms;
    @FXML
    private ComboBox<String> StateComboBox;
    @FXML
    static private ComboBox<String> blockComboBox;
    @FXML
    static private RadioButton SingleRadioButton;
    @FXML
    static private RadioButton DoubleRadioButton;
    @FXML
    static private ComboBox<String> AvailableRooms;
    @FXML
    private TextField feenoTextFeild;
    @FXML
    private Font x3;
    @FXML
    private TextField mobileTextFeild;
    @FXML
    private TextField addressTextFeild;
    @FXML
    private TextField CityTextFeild;
    ToggleGroup singledoubleToggleGroup;
    @FXML
    private Button submitbutton;
    @FXML
    private Button cancelButton;
    @FXML
    static Text headerLabel;
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
                        availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=0 and doubleroom=0");
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
                        availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=0 and doubleroom=1");
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
                    availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=0 and doubleroom=0");
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
                    availrooms = statement.executeQuery("select roomno from rooms where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and filled=0 and doubleroom=1");
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

        for (int i = 1; i <= 31; i++)
        {
            datesObservableList.add(i);
        }
        monthsObservableList.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 1980; i <= 2020; i++)
        {
            yearObservableList.add(i);
        }
        statesObservableList.addAll("Andaman and Nicobar", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal");

        blockComboBox.setItems(blocksObservableList);
        DateComboBox.setItems(datesObservableList);
        MonthCombobox.setItems(monthsObservableList);
        YearCombobox.setItems(yearObservableList);
        StateComboBox.setItems(statesObservableList);
    }

    @FXML
    private void VehicleCheckBoxAction(ActionEvent event)
    {
        if (VehicleCheckBox.isSelected())
        {
            VehicleNumberTextFeild.setDisable(false);
        } else
        {
            VehicleNumberTextFeild.setDisable(true);
            VehicleNumberTextFeild.setText("");
        }
    }

    @FXML
    private void CancelButtonAction(ActionEvent event)
    {
        try
        {
            availableroomObservableList.clear();
            if (AvailableRooms.mouseTransparentProperty().get() && AvailableRooms.getSelectionModel().getSelectedItem().contains("E"))
            {
                statement.execute("delete from rooms where roomno='" + AvailableRooms.getSelectionModel().getSelectedItem() + "' and block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "'");
                statement.execute("update rooms set adjusted=0 where block='" + block(blockComboBox.getSelectionModel().getSelectedItem()) + "' and roomno like '" + AvailableRooms.getSelectionModel().getSelectedItem().replace("E", "") + "%" + "'");
            }
            MainProgramSceneController.RightSideofSplitPane.getChildren().set(0, ((Parent) FXMLLoader.load(getClass().getResource("RightSideMain.fxml"))));
        } catch (IOException ex)
        {
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }

    }

    @FXML
    private void submitButtonAction(ActionEvent event)
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
            ErrorLabel.setText("Select Room to Allot");
            return;
        }
        if ("".equals(feenoTextFeild.getText()))
        {
            feenoTextFeild.requestFocus();
            ErrorLabel.setText("Enter Fee Receipt Number");
            return;
        }
        if ("".equals(NameTextFeild.getText()))
        {
            NameTextFeild.requestFocus();
            ErrorLabel.setText("Enter Name");
            return;
        }
        if ("".equals(FatherNameTextFeild.getText()))
        {
            FatherNameTextFeild.requestFocus();
            ErrorLabel.setText("Enter Father's Name");
            return;
        }
        if ("".equals(MotherNameTextFeild.getText()))
        {
            MotherNameTextFeild.requestFocus();
            ErrorLabel.setText("Enter Mother's Name");
            return;
        }
        if ("".equals(RollNumberTextFeild.getText()))
        {
            RollNumberTextFeild.requestFocus();
            ErrorLabel.setText("Enter Roll Numer");
            return;
        }
        if (DateComboBox.getSelectionModel().isEmpty() || MonthCombobox.getSelectionModel().isEmpty() || YearCombobox.getSelectionModel().isEmpty())
        {
            ErrorLabel.setText("Select Date of Birth");
            return;
        }
        if ("".equals(mobileTextFeild.getText()))
        {
            mobileTextFeild.requestFocus();
            ErrorLabel.setText("Enter Mobile Number");
            return;
        }
        if (mobileTextFeild.getText().length() != 10)
        {
            ErrorLabel.setText("Invalid Mobile Number");
            mobileTextFeild.requestFocus();
            return;
        }
        if ("".equals(addressTextFeild.getText()))
        {
            addressTextFeild.requestFocus();
            ErrorLabel.setText("Enter Permanent Address");
        }
        if ("".equals(CityTextFeild.getText()))
        {
            CityTextFeild.requestFocus();
            ErrorLabel.setText("Enter City of Residence");
            return;
        }
        if (StateComboBox.getSelectionModel().isEmpty())
        {
            ErrorLabel.setText("Select State of Residence");
            StateComboBox.requestFocus();
            return;
        }
        if (VehicleCheckBox.isSelected() && "".equals(VehicleNumberTextFeild.getText()))
        {
            ErrorLabel.setText("Enter Vehicle Number");
            return;
        }

        int date = DateComboBox.getSelectionModel().getSelectedItem();
        int month = MonthCombobox.getSelectionModel().getSelectedIndex();
        int year = YearCombobox.getSelectionModel().getSelectedItem() - 1900;
        if (month == 1 && date > 29)
        {
            ErrorLabel.setText("Invalid Date");
            return;
        }
        if (month == 1 && date == 29 && !CalendarUtils.isGregorianLeapYear(year))
        {
            ErrorLabel.setText("Invalid Date, Not a Leap Year");
            return;
        }
        if ((month == 3 || month == 5 || month == 8 || month == 10) && date == 31)
        {
            ErrorLabel.setText("Invalid Date");
            return;
        }
        Date dob = new Date(year, month, date);

        try
        {
            int rollno = Integer.parseInt(RollNumberTextFeild.getText());
            if (rollnoExixts(rollno))
            {
                RollNumberTextFeild.requestFocus();
                return;
            }

        } catch (NumberFormatException e)
        {
            ErrorLabel.setText("Invalid Roll Number.");
            RollNumberTextFeild.requestFocus();
            return;
        }
        try
        {
            int feeno = Integer.parseInt(feenoTextFeild.getText());
            if (feenoExists(feeno))
            {
                feenoTextFeild.requestFocus();
                return;
            }

        } catch (NumberFormatException e)
        {
            ErrorLabel.setText("Invalid Fee Receipt Number");
            feenoTextFeild.requestFocus();
            return;
        }

        int vehiclebool;
        if (VehicleCheckBox.isSelected())
        {
            vehiclebool = 1;
        } else
        {
            vehiclebool = 0;
        }

        if (vehiclenoExists(VehicleNumberTextFeild.getText()))
        {
            VehicleNumberTextFeild.requestFocus();
            return;
        }
        char block = block(blockComboBox.getSelectionModel().getSelectedItem());

        try
        {
            statement.execute("update rooms set filled='1',feeno='" + feenoTextFeild.getText() + "',name='" + NameTextFeild.getText() + "',father='" + FatherNameTextFeild.getText() + "',mother='" + MotherNameTextFeild.getText() + "', rollno='" + RollNumberTextFeild.getText() + "',dob='" + dob + "',mobile='" + mobileTextFeild.getText() + "',address='" + addressTextFeild.getText() + "',city='" + CityTextFeild.getText() + "',state='" + StateComboBox.getSelectionModel().getSelectedItem() + "',vehicle='" + vehiclebool + "',vehicleno='" + VehicleNumberTextFeild.getText() + "' where roomno='" + AvailableRooms.getSelectionModel().getSelectedItem() + "' and block='" + block + "'");
            ErrorLabel.setFill(Color.GREEN);
            ErrorLabel.setText("Room Allotement Successful !!");
            MainProgramSceneController.updateSearchTable();
            statement.execute("insert into activity(admin, string) values('h', '" + "Room No. " + AvailableRooms.getSelectionModel().getSelectedItem() + ", Block " + block + " Alloted to " + NameTextFeild.getText() + "')");

            submitbutton.setDisable(true);
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
                    }

                }
            });

        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            ErrorLabel.setText("Invalid Input somewhere.");
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

    boolean rollnoExixts(int rollno)
    {
        try
        {
            ResultSet tem = statement.executeQuery("select rollno,roomno,block from rooms where filled='1'");
            while (tem.next())
            {
                if (tem.getInt("rollno") == rollno)
                {
                    ErrorLabel.setText("Roll Number Already Registered for Room: " + tem.getString("roomno") + " " + tem.getString("block") + " Block");
                    return true;
                }
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
        return false;
    }

    boolean feenoExists(int feeno)
    {
        try
        {
            ResultSet tem = statement.executeQuery("select feeno,roomno,block from rooms where filled='1'");
            while (tem.next())
            {
                if (tem.getInt("feeno") == feeno)
                {
                    ErrorLabel.setText("Fee Receipt Number Already Registered for Room: " + tem.getString("roomno") + " " + tem.getString("block") + " Block");
                    return true;
                }
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
        return false;
    }

    boolean vehiclenoExists(String vehicleno)
    {
        if (vehicleno.equals(""))
        {
            return false;
        }
        try
        {
            ResultSet tem = statement.executeQuery("select vehicleno,roomno,block from rooms where filled='1'");
            while (tem.next())
            {
                if (tem.getString("vehicleno").equals(vehicleno))
                {
                    ErrorLabel.setText("Vehicle Number Already Registered for Room: " + tem.getString("roomno") + " " + tem.getString("block") + " Block");
                    return true;
                }
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }
        return false;
    }

    static void Adjust(String block, String roomno, int accomodation)
    {
        blockComboBox.getSelectionModel().select(block);
        blockComboBox.setMouseTransparent(true);
        blockComboBox.setFocusTraversable(false);

        if (accomodation == 1)
        {
            SingleRadioButton.setSelected(true);
        } else
        {
            DoubleRadioButton.setSelected(true);
        }

        SingleRadioButton.setMouseTransparent(true);
        SingleRadioButton.setFocusTraversable(false);
        DoubleRadioButton.setMouseTransparent(true);
        DoubleRadioButton.setFocusTraversable(false);

        ObservableList<String> temp = FXCollections.observableArrayList();

        temp.add(roomno);
        AvailableRooms.setItems(temp);
        AvailableRooms.getSelectionModel().selectFirst();
        AvailableRooms.setMouseTransparent(true);
        AvailableRooms.setFocusTraversable(false);
        headerLabel.setText("Enter Details for New Adjustment");
    }
}
