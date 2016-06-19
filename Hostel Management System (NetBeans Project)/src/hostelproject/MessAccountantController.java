package hostelproject;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class MessAccountantController implements Initializable
{
    @FXML
    private TableView<searchresult> SearchResultTable;
    @FXML
    private TableColumn<searchresult, String> roomnoColumn;
    @FXML
    private TableColumn<searchresult, String> nameColumn;
    @FXML
    private TableColumn<searchresult, Integer> dueColumn;
    @FXML
    private TableColumn<searchresult, Integer> AddAmountColumn;
    @FXML
    private TableColumn<searchresult, Integer> subtractAmountColumn;
    @FXML
    private TextField searchbox;
    static ObservableList<searchresult> searchresultObservableList = FXCollections.observableArrayList();
    static char user;
    static Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            statement = MainSceneController.connection.createStatement();
        } catch (SQLException ex)
        {
        }

        user = ChangepasswordController.selectedUser;
        searchbox.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                searchresultObservableList.clear();
                try
                {
                    ResultSet resultSet = statement.executeQuery("select roomno,name,due from rooms where  filled=1 and roomno like '" + "%" + searchbox.getText() + "%" + "'and block='" + user + " '");
                    while (resultSet.next())
                    {
                        searchresult tempresult = new searchresult();
                        tempresult.setName(resultSet.getString("name"));
                        tempresult.setRoomno(resultSet.getString("roomno"));
                        tempresult.setDue(resultSet.getInt("due"));
                        tempresult.setAdd(0);
                        tempresult.setSubtract(0);
                        searchresultObservableList.add(tempresult);
                    }
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
        updateSearchTable();

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<searchresult, String> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getName());
            }
        });

        roomnoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<searchresult, String> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getRoomno());
            }
        });

        dueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, Integer>, ObservableValue<Integer>>()
        {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<searchresult, Integer> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getDue());
            }
        });

        AddAmountColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, Integer>, ObservableValue<Integer>>()
        {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<searchresult, Integer> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getAdd());
            }
        });

        subtractAmountColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<searchresult, Integer>, ObservableValue<Integer>>()
        {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<searchresult, Integer> p)
            {
                return new ReadOnlyObjectWrapper<>(p.getValue().getSubtract());
            }
        });

        SearchResultTable.setItems(searchresultObservableList);

        AddAmountColumn.setCellFactory(new Callback<TableColumn<searchresult, Integer>, TableCell<searchresult, Integer>>()
        {
            @Override
            public TableCell<searchresult, Integer> call(TableColumn<searchresult, Integer> p)
            {
                return new EditingCell();
            }
        });

        subtractAmountColumn.setCellFactory(new Callback<TableColumn<searchresult, Integer>, TableCell<searchresult, Integer>>()
        {
            @Override
            public TableCell<searchresult, Integer> call(TableColumn<searchresult, Integer> p)
            {
                return new EditingCell();
            }
        });

        subtractAmountColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<searchresult, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<searchresult, Integer> t)
            {
                try
                {
                    int newdue = t.getRowValue().getDue() - t.getNewValue();
                    if(t.getNewValue()!=0)
                    statement.execute("insert into activity(admin, string) values('" + user + "', '" + "Rs. " + t.getNewValue() + " subtracted from Room No. " + t.getRowValue().getRoomno() + "')");
                    t.getRowValue().setAdd(0);
                    t.getRowValue().setDue(newdue);
                    statement.execute("update rooms set due='" + newdue + "' where roomno='" + t.getRowValue().getRoomno() + "' and block='" + user + " '");
                    SearchResultTable.getSelectionModel().getSelectedItem().setDue(newdue);
                    SearchResultTable.getSelectionModel().getSelectedItem().setAdd(0);
                    subtractAmountColumn.setVisible(false);
                    subtractAmountColumn.setVisible(true);
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
        AddAmountColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<searchresult, Integer>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<searchresult, Integer> t)
            {
                try
                {
                    int newdue = t.getRowValue().getDue() + t.getNewValue();
                    if(t.getNewValue()!=0)
                    statement.execute("insert into activity(admin, string) values('" + user + "', '" + "Rs. " + t.getNewValue() + " added to Room No. " + t.getRowValue().getRoomno() + "')");
                    t.getRowValue().setAdd(0);
                    t.getRowValue().setDue(newdue);
                    statement.execute("update rooms set due='" + newdue + "' where roomno='" + t.getRowValue().getRoomno() + "' and block='" + user + " '");
                    SearchResultTable.getSelectionModel().getSelectedItem().setDue(newdue);
                    SearchResultTable.getSelectionModel().getSelectedItem().setAdd(0);
                    AddAmountColumn.setVisible(false);
                    AddAmountColumn.setVisible(true);
                } catch (SQLException ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
    }

    void updateSearchTable()
    {
        searchbox.setText("R" + searchbox.getText());
        searchbox.setText(searchbox.getText().substring(1));
    }
}

class EditingCell extends TextFieldTableCell<searchresult, Integer>
{
    private TextField textField;

    public EditingCell()
    {
    }

    @Override
    public void startEdit()
    {
        super.startEdit();
        if (textField == null)
        {
            createTextField();
        }
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                textField.requestFocus();
                textField.selectAll();
            }
        });
    }

    @Override
    public void cancelEdit()
    {
        super.cancelEdit();
        setText("" + getItem());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    private void createTextField()
    {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent t)
            {
                if (t.getCode() == KeyCode.ENTER)
                {
                    commitEdit(Integer.parseInt(textField.getText()));
                } else if (t.getCode() == KeyCode.ESCAPE)
                {
                    cancelEdit();
                }
            }
        });
    }

    private String getString()
    {
        return getItem() == null ? "" : getItem().toString();
    }
}