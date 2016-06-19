/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Pranav
 */
public class MainSceneController implements Initializable
{
    @FXML
    private PasswordField mainScenePassword;
    @FXML
    private Font x1;
    String pass;
    @FXML
    private AnchorPane accessGrantedLabel;
    Parent temParent = null;
    @FXML
    private ProgressIndicator BusyIndicator;
    @FXML
    private RadioButton localhostRadio;
    @FXML
    private RadioButton networkhostRadio;
    ToggleGroup serverToggleGroup;
    @FXML
    private TextField IP1Label;
    @FXML
    private TextField IP2Label;
    @FXML
    private TextField IP3Label;
    @FXML
    private TextField IP4Label;
    static Connection connection;
    String IP;
    @FXML
    private Button makeConnectionButton;
    @FXML
    private ProgressIndicator doneProgressButton;
    @FXML
    private Text unsuccessfulLabel;
    static Statement statement = null;
    @FXML
    private Font x3;
    @FXML
    static ComboBox<String> UserComboBox;
    static ObservableList<String> UserObservableList = FXCollections.observableArrayList();
    static char selectedUser;
    @FXML
    private Font x2;
    @FXML
    private Text textmarq;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        UserObservableList.clear();
        UserObservableList.addAll("Hostel Accountant", "A Block Accountant", "B Block Accountant", "C Block Accountant");
        UserComboBox.setItems(UserObservableList);
        DriverManager.setLoginTimeout(3);
        serverToggleGroup = new ToggleGroup();
        localhostRadio.setToggleGroup(serverToggleGroup);
        networkhostRadio.setToggleGroup(serverToggleGroup);
        connection = null;
        localhostRadio.setSelected(false);
        networkhostRadio.setSelected(false);

        UserComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1)
            {
                try
                {
                    selectedUser = UserComboBox.getSelectionModel().getSelectedItem().charAt(0);
                } catch (NullPointerException e)
                {
                }
            }
        });
        UserComboBox.getSelectionModel().select(0);

        localhostRadio.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                doneProgressButton.setOpacity(0);
                unsuccessfulLabel.setOpacity(0);
                connection = null;
                mainScenePassword.setDisable(true);
                makeConnectionButton.setDisable(true);
                IP1Label.setDisable(true);
                IP2Label.setDisable(true);
                IP3Label.setDisable(true);
                IP4Label.setDisable(true);

                IP = "127.0.0.1";
                try
                {
                    connection = null;
                    connection = DriverManager.getConnection("JDBC:mysql://" + IP + ":3306/hosteldb", "root", "qwqw");
                    statement = connection.createStatement();
                    HostelProject.statement = statement;
                    mainScenePassword.setDisable(false);
                    doneProgressButton.setOpacity(1);
                    unsuccessfulLabel.setOpacity(0);
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                    unsuccessfulLabel.setOpacity(1);
                    doneProgressButton.setOpacity(0);
                }
            }
        });

        networkhostRadio.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                connection = null;
                doneProgressButton.setOpacity(0);
                unsuccessfulLabel.setOpacity(0);
                IP1Label.setDisable(false);
                IP2Label.setDisable(false);
                IP3Label.setDisable(false);
                IP4Label.setDisable(false);
                mainScenePassword.setDisable(true);
                makeConnectionButton.setDisable(false);
            }
        });

        mainScenePassword.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                ResultSet passResultSet;
                FadeTransition errorFadeTransition = new FadeTransition(Duration.millis(500), BusyIndicator);
                errorFadeTransition.setFromValue(1);
                errorFadeTransition.setToValue(0);
                errorFadeTransition.play();
                errorFadeTransition.setOnFinished(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent t)
                    {
                        BusyIndicator.setOpacity(0);
                    }
                });
                try
                {
                    passResultSet = HostelProject.statement.executeQuery("select pass from admin where user='" + selectedUser + "'");
                    passResultSet.next();
                    pass = passResultSet.getString("pass");
                } catch (SQLException ex)
                {
                }

                if (pass.equals(mainScenePassword.getText()))
                {
                    BusyIndicator.setVisible(false);
                    try
                    {
                        if (selectedUser == 'H')
                        {
                            temParent = (Parent) FXMLLoader.load(getClass().getResource("MainProgramScene.fxml"));
                            ChangepasswordController.selectedUser = 'h';
                        }
                        if (selectedUser == 'A')
                        {
                            temParent = (Parent) FXMLLoader.load(getClass().getResource("MainProgramScene.fxml"));
                            ChangepasswordController.selectedUser = 'a';
                        }
                        if (selectedUser == 'B')
                        {
                            temParent = (Parent) FXMLLoader.load(getClass().getResource("MainProgramScene.fxml"));
                            ChangepasswordController.selectedUser = 'b';
                        }
                        if (selectedUser == 'C')
                        {
                            temParent = (Parent) FXMLLoader.load(getClass().getResource("MainProgramScene.fxml"));
                            ChangepasswordController.selectedUser = 'c';
                        }
                    } catch (IOException ex)
                    {
                    }

                    MainProgramSceneController.userLabel.setText(UserComboBox.getSelectionModel().getSelectedItem());
                    accessGrantedLabel.setVisible(true);
                    mainScenePassword.setText("");
                    final Animation temp = new Transition()
                    {
                        
                        {
                            setCycleDuration(Duration.millis(300));
                        }

                        @Override
                        protected void interpolate(double d)
                        {
                        }
                    };

                    temp.play();

                    temp.setOnFinished(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent t)
                        {
                            HostelProject.scene.setRoot(temParent);
                            if (selectedUser == 'A' || selectedUser == 'B' || selectedUser == 'C')
                            {
                                try
                                {
                                    MainProgramSceneController.mainAnchorPane.getChildren().set(0, (Parent) FXMLLoader.load(getClass().getResource("MessAccountant.fxml")));
                                } catch (IOException ex)
                                {
                                    System.out.println(ex.toString());
                                }
                            }

                        }
                    });

                }
            }
        });
    }

    @FXML
    private void makeConnectionAction(ActionEvent event)
    {
        IP = IP1Label.getText() + "." + IP2Label.getText() + "." + IP3Label.getText() + "." + IP4Label.getText();
        try
        {
            connection = null;
            connection = DriverManager.getConnection("JDBC:mysql://" + IP + ":3306/hosteldb", "root", "qwqw");
            doneProgressButton.setOpacity(1);
            unsuccessfulLabel.setOpacity(0);
            statement = connection.createStatement();
            HostelProject.statement = statement;
            mainScenePassword.setDisable(false);
        } catch (SQLException ex)
        {
            unsuccessfulLabel.setOpacity(1);
            doneProgressButton.setOpacity(0);
        }
    }
}
