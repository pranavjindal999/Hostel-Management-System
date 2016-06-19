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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import sun.util.calendar.CalendarUtils;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class StudentDetailController implements Initializable
{
    @FXML
    private Font x1;
    @FXML
    static TextField feenoTextFeild;
    @FXML
    static TextField nameTextFeild;
    @FXML
    static TextField fatherTextFeild;
    @FXML
    static TextField motherTextFeild;
    @FXML
    static TextField rollnoTextFeild;
    @FXML
    static ComboBox<Integer> dateComboBox;
    @FXML
    static ComboBox<String> monthCombox;
    @FXML
    static ComboBox<Integer> yearComboBox;
    @FXML
    static TextField mobileTextFeild;
    @FXML
    static TextField addressTextFeild;
    @FXML
    static TextField cityTextFeild;
    @FXML
    static ComboBox<String> stateCombobox;
    @FXML
    static CheckBox VehicleCheckBox;
    @FXML
    static Font x2;
    @FXML
    static TextField vehiclenoTextFeild;
    @FXML
    static Text blockLabel;
    @FXML
    static Text roomnoLabel;
    @FXML
    static Button updateButton;
    @FXML
    static Button editCancelButton;
    @FXML
    private Text ErrorLabel;
    static ObservableList<Integer> datesObservableList = FXCollections.observableArrayList();
    static ObservableList<String> monthsObservableList = FXCollections.observableArrayList();
    static ObservableList<Integer> yearObservableList = FXCollections.observableArrayList();
    static ObservableList<String> statesObservableList = FXCollections.observableArrayList();
    @FXML
    static Text DateLabel;
    @FXML
    static Text StateLabel;
    @FXML
    static Text dobLabel;
    static String acceptedRoomNo;
    static char acceptedBlock;
    @FXML
    static HBox dobHBox;
    @FXML
    static Text feenoLabel;
    @FXML
    static Text nameLabel;
    @FXML
    static Text fatherLabel;
    @FXML
    static Text motherLabel;
    @FXML
    static Text rollnoLabel;
    @FXML
    static Text mobileLabel;
    @FXML
    static Text addressLabel;
    @FXML
    static Text CityLabel;
    @FXML
    static Text vehicleNoLabel;
    static Timestamp originalTimestamp;
        static Stage billStage;
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
        ErrorLabel.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                double mid = (updateButton.getLayoutX());
                ErrorLabel.setLayoutX((mid + 315) - ErrorLabel.getText().length() * 5);
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

        dateComboBox.setItems(datesObservableList);
        monthCombox.setItems(monthsObservableList);
        yearComboBox.setItems(yearObservableList);
        stateCombobox.setItems(statesObservableList);
    }

    @FXML
    private void VehicleCheckBoxAction(ActionEvent event)
    {
        if (VehicleCheckBox.isSelected())
        {
            vehiclenoTextFeild.setEditable(true);
        } else
        {
            vehiclenoTextFeild.setEditable(false);
            vehiclenoTextFeild.setText("");
        }
    }

    @FXML
    private void updateButtonAction(ActionEvent event)
    {

        ErrorLabel.setText("");
        ErrorLabel.setFill(Color.RED);

        if ("".equals(feenoTextFeild.getText()))
        {
            feenoTextFeild.requestFocus();
            ErrorLabel.setText("Enter Fee Receipt Number");
            return;
        }
        if ("".equals(nameTextFeild.getText()))
        {
            nameTextFeild.requestFocus();
            ErrorLabel.setText("Enter Name");
            return;
        }
        if ("".equals(fatherTextFeild.getText()))
        {
            fatherTextFeild.requestFocus();
            ErrorLabel.setText("Enter Father's Name");
            return;
        }
        if ("".equals(motherTextFeild.getText()))
        {
            motherTextFeild.requestFocus();
            ErrorLabel.setText("Enter Mother's Name");
            return;
        }
        if ("".equals(rollnoTextFeild.getText()))
        {
            rollnoTextFeild.requestFocus();
            ErrorLabel.setText("Enter Roll Numer");
            return;
        }
        if (dateComboBox.getSelectionModel().isEmpty() || monthCombox.getSelectionModel().isEmpty() || yearComboBox.getSelectionModel().isEmpty())
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
        if ("".equals(cityTextFeild.getText()))
        {
            cityTextFeild.requestFocus();
            ErrorLabel.setText("Enter City of Residence");
            return;
        }
        if (stateCombobox.getSelectionModel().isEmpty())
        {
            ErrorLabel.setText("Select State of Residence");
            stateCombobox.requestFocus();
            return;
        }
        if (VehicleCheckBox.isSelected() && "".equals(vehiclenoTextFeild.getText()))
        {
            ErrorLabel.setText("Enter Vehicle Number");
            return;
        }

        int date = dateComboBox.getSelectionModel().getSelectedItem();
        int month = monthCombox.getSelectionModel().getSelectedIndex();
        int year = yearComboBox.getSelectionModel().getSelectedItem() - 1900;
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
            int rollno = Integer.parseInt(rollnoTextFeild.getText());
            if (rollnoExixts(rollno))
            {
                rollnoTextFeild.requestFocus();
                return;
            }

        } catch (NumberFormatException e)
        {
            ErrorLabel.setText("Invalid Roll Number.");
            rollnoTextFeild.requestFocus();
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

        if (vehiclenoExists(vehiclenoTextFeild.getText()))
        {
            vehiclenoTextFeild.requestFocus();
            return;
        }


        try
        {
            statement.execute("update rooms set feeno='" + feenoTextFeild.getText() + "',name='" + nameTextFeild.getText() + "',father='" + fatherTextFeild.getText() + "',mother='" + motherTextFeild.getText() + "', rollno='" + rollnoTextFeild.getText() + "',dob='" + dob + "',mobile='" + mobileTextFeild.getText() + "',address='" + addressTextFeild.getText() + "',city='" + cityTextFeild.getText() + "',state='" + stateCombobox.getSelectionModel().getSelectedItem() + "',vehicle='" + vehiclebool + "',vehicleno='" + vehiclenoTextFeild.getText() + "',datemodified='" + originalTimestamp + "' where roomno='" + roomnoLabel.getText() + "' and block='" + blockLabel.getText() + "'");
            MainProgramSceneController.updateSearchTable();
            ErrorLabel.setFill(Color.GREEN);
            ErrorLabel.setText("Updation Successful !!");
            statement.execute("insert into activity(admin, string) values('h', '"+"Details of Room No. "+roomnoLabel.getText()+", Block "+blockLabel.getText().charAt(0)+" Updated"+"')");

            editCancelButtonAction(event);
            dataacceptter(acceptedBlock, acceptedRoomNo);
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
                    }

                }
            });

        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            ErrorLabel.setText("Invalid Input somewhere.");
        }

    }

    @FXML
    static void editCancelButtonAction(ActionEvent event)
    {
        if ("Edit".equals(editCancelButton.getText()))
        {
            editCancelButton.setText("Cancel");
            updateButton.setDisable(false);

            feenoTextFeild.setVisible(true);
            feenoLabel.setVisible(false);
            nameTextFeild.setVisible(true);
            nameLabel.setVisible(false);
            fatherTextFeild.setVisible(true);
            fatherLabel.setVisible(false);
            motherTextFeild.setVisible(true);
            motherLabel.setVisible(false);
            mobileTextFeild.setVisible(true);
            mobileLabel.setVisible(false);
            rollnoTextFeild.setVisible(true);
            rollnoLabel.setVisible(false);
            dobHBox.setVisible(true);
            dobLabel.setVisible(false);
            mobileTextFeild.setVisible(true);
            mobileLabel.setVisible(false);
            addressTextFeild.setVisible(true);
            addressLabel.setVisible(false);
            cityTextFeild.setVisible(true);
            CityLabel.setVisible(false);
            stateCombobox.setVisible(true);
            StateLabel.setVisible(false);
            VehicleCheckBox.setMouseTransparent(false);
            VehicleCheckBox.setFocusTraversable(true);
            if(VehicleCheckBox.isSelected())
            {
                vehiclenoTextFeild.setEditable(true);
            }
            vehiclenoTextFeild.setVisible(true);
            vehicleNoLabel.setVisible(false);
        } else
        {
            dataacceptter(acceptedBlock, acceptedRoomNo);
            editCancelButton.setText("Edit");
            updateButton.setDisable(true);
            feenoTextFeild.setVisible(false);
            feenoLabel.setVisible(true);
            nameTextFeild.setVisible(false);
            nameLabel.setVisible(true);
            fatherTextFeild.setVisible(false);
            fatherLabel.setVisible(true);
            motherTextFeild.setVisible(false);
            motherLabel.setVisible(true);
            mobileTextFeild.setVisible(false);
            mobileLabel.setVisible(true);
            rollnoTextFeild.setVisible(false);
            rollnoLabel.setVisible(true);
            dobHBox.setVisible(false);
            dobLabel.setVisible(true);
            mobileTextFeild.setVisible(false);
            mobileLabel.setVisible(true);
            addressTextFeild.setVisible(false);
            addressLabel.setVisible(true);
            cityTextFeild.setVisible(false);
            CityLabel.setVisible(true);
            stateCombobox.setVisible(false);
            StateLabel.setVisible(true);
            VehicleCheckBox.setMouseTransparent(true);
            VehicleCheckBox.setFocusTraversable(false);
            vehiclenoTextFeild.setVisible(false);
            vehiclenoTextFeild.setEditable(false);
            vehicleNoLabel.setVisible(true);

        }
    }

    static void dataacceptter(char block, String roomnoString)
    {
        acceptedBlock = block;
        acceptedRoomNo = roomnoString;
        ResultSet executeQuery;
        try
        {
            executeQuery = statement.executeQuery("select * from rooms where roomno='" + acceptedRoomNo + "' and block='" + acceptedBlock + "' ");
            executeQuery.next();
            blockLabel.setText(executeQuery.getString("block"));
            roomnoLabel.setText(executeQuery.getString("roomno"));

            Timestamp temp = executeQuery.getTimestamp("datemodified");
            originalTimestamp = temp;
            DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy, hh:mm a");
            DateLabel.setText(dateFormat.format(temp));

            feenoTextFeild.setText("" + executeQuery.getInt("feeno"));
            feenoLabel.setText(feenoTextFeild.getText());
            nameTextFeild.setText(executeQuery.getString("name"));

            nameLabel.setText(nameTextFeild.getText());
            fatherTextFeild.setText(executeQuery.getString("father"));
            fatherLabel.setText(fatherTextFeild.getText());
            motherTextFeild.setText(executeQuery.getString("mother"));
            motherLabel.setText(motherTextFeild.getText());
            rollnoTextFeild.setText("" + executeQuery.getInt("rollno"));
            rollnoLabel.setText(rollnoTextFeild.getText());

            dateComboBox.getSelectionModel().select(executeQuery.getDate("dob").getDate() - 1);
            monthCombox.getSelectionModel().select(executeQuery.getDate("dob").getMonth());
            yearComboBox.getSelectionModel().select(executeQuery.getDate("dob").getYear() - 80);

            dateFormat = new SimpleDateFormat("d MMMM yyyy");
            dobLabel.setText(dateFormat.format(executeQuery.getDate("dob")));

            mobileTextFeild.setText(executeQuery.getString("mobile"));
            mobileLabel.setText(mobileTextFeild.getText());

            addressTextFeild.setText(executeQuery.getString("address"));
            addressLabel.setText(addressTextFeild.getText());

            cityTextFeild.setText(executeQuery.getString("city"));
            CityLabel.setText(cityTextFeild.getText());

            StateLabel.setText(executeQuery.getString("state"));
            stateCombobox.getSelectionModel().select(executeQuery.getString("state"));

            VehicleCheckBox.setSelected(executeQuery.getInt("vehicle") == 1);
            vehiclenoTextFeild.setText(executeQuery.getString("vehicleno"));
            vehicleNoLabel.setText(vehiclenoTextFeild.getText());

        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }

    }

    boolean rollnoExixts(int rollno)
    {
        try
        {
            ResultSet tem = statement.executeQuery("select rollno,roomno,block from rooms where filled='1'");
            while (tem.next())
            {
                if (tem.getInt("rollno") == rollno && !(tem.getString("roomno").equals(roomnoLabel.getText()) && tem.getString("block").equals(blockLabel.getText())))
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
                if (tem.getInt("feeno") == feeno && !(tem.getString("roomno").equals(roomnoLabel.getText()) && tem.getString("block").equals(blockLabel.getText())))
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
                if (tem.getString("vehicleno").equals(vehicleno) && !(tem.getString("roomno").equals(roomnoLabel.getText()) && tem.getString("block").equals(blockLabel.getText())))
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

    @FXML
    private void viewPayBillAction(ActionEvent event)
    {
        MainProgramSceneController.mainTopAnchorPane.setEffect(new BoxBlur());
        billStage = new Stage();
        billStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent t)
            {
                MainProgramSceneController.mainTopAnchorPane.effectProperty().setValue(null);
            }
        });
        billStage.setTitle("View And Pay Due Bills");
        billStage.initModality(Modality.APPLICATION_MODAL);
        billStage.initStyle(StageStyle.UTILITY);
        billStage.setResizable(false);
        try
        {
            Parent passwordParent = FXMLLoader.load(getClass().getResource("viewPayBill.fxml"));
            billStage.setScene(new Scene(passwordParent));
            billStage.show();
        } catch (IOException ex)
        {
        }
    }
}
