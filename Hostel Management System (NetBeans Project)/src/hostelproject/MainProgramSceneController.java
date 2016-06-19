package hostelproject;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;


public class MainProgramSceneController implements Initializable
{
    @FXML
    static AnchorPane RightSideofSplitPane;
    @FXML
    private AnchorPane LeftSideofSplitPane;
    @FXML
    static TextField searchbox;
    @FXML
    private TableColumn<searchresult, Character> blockcolum;
    @FXML
    private TableColumn<searchresult, String> roomnocolum;
    @FXML
    private TableColumn<searchresult, String> namecolumn;
    @FXML
    private TableColumn<searchresult, Integer> rollnocolumn;
    @FXML
    private TableView<searchresult> searchTable;
    static ObservableList<searchresult> searchresultObservableList = FXCollections.observableArrayList();
    @FXML
    static SplitPane SplitPaneMain;
    @FXML
    private Button shrinkDetailsButton;
    @FXML
    static AnchorPane mainAnchorPane;
    static Stage passwordStage;
    @FXML
    private Font x1;
    @FXML
    private Text resultnoLabel;
    @FXML
    static Text userLabel;
    @FXML
    static AnchorPane mainTopAnchorPane;
    static Statement statement;
    @FXML
    private Button activityLogButton;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            statement=MainSceneController.connection.createStatement();
        } catch (SQLException ex)
        {
        }
        searchbox.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                searchresultObservableList.clear();
                try
                {
                    ResultSet resultSet = statement.executeQuery("select block,roomno,name,rollno from rooms where  filled=1 and (roomno like '" + "%" + searchbox.getText() + "%" + "' or rollno like '" + "%" + searchbox.getText() + "%" + "' or    state like '" + "%" + searchbox.getText() + "%" + "' or city like '" + "%" + searchbox.getText() + "%" + "' or vehicleno like '" + "%" + searchbox.getText() + "%" + "' or name like '" + "%" + searchbox.getText() + "%" + "')");
                    if("pending".equals(searchbox.getText().toLowerCase()))
                    {
                        resultSet=statement.executeQuery("select * from rooms where not due=0");
                    }
                    if("adjust".equals(searchbox.getText().toLowerCase()))
                    {
                        resultSet=statement.executeQuery("select * from rooms where doubleroom=2 or doubleroom=3");
                    }
                    while (resultSet.next())
                    {
                        searchresult tempresult = new searchresult();
                        tempresult.setBlock(resultSet.getString("block").charAt(0));
                        tempresult.setName(resultSet.getString("name"));
                        tempresult.setRollno(resultSet.getInt("rollno"));
                        tempresult.setRoomno(resultSet.getString("roomno"));
                        searchresultObservableList.add(tempresult);
                    }
                } catch (SQLException ex)
                {
                }
                resultnoLabel.setText("" + searchresultObservableList.size());
            }
        });


        searchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<searchresult>()
        {
            @Override
            public void changed(ObservableValue<? extends searchresult> ov, searchresult t, searchresult t1)
            {
                if ("Cancel".equals(StudentDetailController.editCancelButton.getText()))
                {
                    StudentDetailController.editCancelButtonAction(null);
                }
                if (searchTable.getSelectionModel().isEmpty())
                {
                    return;
                }
                searchresult selectedItem = searchTable.getSelectionModel().getSelectedItem();
                StudentDetailController.dataacceptter(selectedItem.getBlock(), selectedItem.getRoomno());
                final Animation animation = new Transition()
                {
                    
                    {
                        setCycleDuration(Duration.millis(800));
                    }

                    @Override
                    protected void interpolate(double frac)
                    {
                        SplitPaneMain.setDividerPosition(0, SplitPaneMain.getDividerPositions()[0] + frac);
                    }
                };
                animation.play();
            }
        });

        searchTable.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                if (!searchTable.isFocused())
                {
                    searchTable.getSelectionModel().clearSelection(searchTable.getSelectionModel().getSelectedIndex());
                    searchTable.getSelectionModel().clearSelection();
                }
            }
        });

        updateSearchTable();

        blockcolum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, Character>, ObservableValue<Character>>()
        {
            @Override
            public ObservableValue<Character> call(TableColumn.CellDataFeatures<searchresult, Character> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getBlock());
            }
        });
        namecolumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<searchresult, String> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getName());
            }
        });
        rollnocolumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, Integer>, ObservableValue<Integer>>()
        {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<searchresult, Integer> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getRollno());
            }
        });
        roomnocolum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<searchresult, String> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getRoomno());
            }
        });
        searchTable.setItems(searchresultObservableList);
    }

    @FXML
    private void logoutAction(ActionEvent event)
    {
        try
        {
            HostelProject.scene.setRoot((Parent) FXMLLoader.load(getClass().getResource("MainScene.fxml")));
        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void shirnkDetailsAction(ActionEvent event)
    {
        if ("Cancel".equals(StudentDetailController.editCancelButton.getText()))
        {
            StudentDetailController.editCancelButtonAction(event);
        }

        final Animation animation = new Transition()
        {
            
            {
                setCycleDuration(Duration.millis(800));
            }

            @Override
            protected void interpolate(double frac)
            {
                SplitPaneMain.setDividerPosition(0, SplitPaneMain.getDividerPositions()[0]-frac);
            }
        };
        animation.play();
    }

    public static void updateSearchTable()
    {
        searchbox.setText("R" + searchbox.getText());
        searchbox.setText(searchbox.getText().substring(1));
    }

    @FXML
    private void changePasswordAction(ActionEvent event)
    {
        mainTopAnchorPane.setEffect(new BoxBlur());
        passwordStage = new Stage();
        passwordStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent t)
            {
                mainTopAnchorPane.effectProperty().setValue(null);
            }
        });
        passwordStage.setTitle("Change Password");
        passwordStage.initModality(Modality.APPLICATION_MODAL);
        passwordStage.initStyle(StageStyle.UTILITY);
        passwordStage.setResizable(false);
        try
        {
            Parent passwordParent = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
            passwordStage.setScene(new Scene(passwordParent));
            passwordStage.show();
        } catch (IOException ex)
        {
        }
    }

    @FXML
    private void activityLogButtonAction(ActionEvent event)
    {
        mainTopAnchorPane.setEffect(new BoxBlur());
        passwordStage = new Stage();
        passwordStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent t)
            {
                mainTopAnchorPane.effectProperty().setValue(null);
            }
        });
        passwordStage.setTitle("Activity Log");
        passwordStage.initModality(Modality.APPLICATION_MODAL);
        passwordStage.initStyle(StageStyle.UTILITY);
        passwordStage.setResizable(false);
        try
        {
            Parent passwordParent = FXMLLoader.load(getClass().getResource("activityLog.fxml"));
            passwordStage.setScene(new Scene(passwordParent));
            passwordStage.show();
        } catch (IOException ex)
        {
        }
    }
}







class searchresult
{
    char block;
    int rollno;
    String name;
    String roomno;
    int due, subtract, add;

    public void setAdd(int add)
    {
        this.add = add;
    }

    public void setSubtract(int subtract)
    {
        this.subtract = subtract;
    }

    public void setDue(int due)
    {
        this.due = due;
    }

    public int getAdd()
    {
        return add;
    }

    public int getSubtract()
    {
        return subtract;
    }

    public int getDue()
    {
        return due;
    }

    public void setBlock(char block)
    {
        this.block = block;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRollno(int rollno)
    {
        this.rollno = rollno;
    }

    public void setRoomno(String roomno)
    {
        this.roomno = roomno;
    }

    public char getBlock()
    {
        return block;
    }

    public String getName()
    {
        return name;
    }

    public int getRollno()
    {
        return rollno;
    }

    public String getRoomno()
    {
        return roomno;
    }
}