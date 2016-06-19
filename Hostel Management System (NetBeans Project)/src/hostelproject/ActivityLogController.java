/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelproject;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Pranav
 */
public class ActivityLogController implements Initializable
{
    @FXML
    private AnchorPane scrollAnchorPane;
    Statement statement;
    char user = MainProgramSceneController.userLabel.getText().charAt(0);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            statement = MainSceneController.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from activity where admin='" + user + "'");
            resultSet.next();
            Text text[] = new Text[resultSet.getInt(1) * 2];
            resultSet = statement.executeQuery("select * from activity where admin='" + user + "' order by time desc");
            for (int i = 0; resultSet.next(); i++)
            {
                text[i] = new Text();
                DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy, hh:mm a");
                text[i].setText(dateFormat.format(resultSet.getTimestamp("time")));
                scrollAnchorPane.getChildren().add(text[i]);
                text[i].setLayoutX(25);
                text[i].setLayoutY(25 + i * 30);
                i++;
                text[i] = new Text();
                text[i].setText(resultSet.getString("string"));
                text[i].setFont(new Font(18));
                scrollAnchorPane.getChildren().add(text[i]);
                text[i].setLayoutX(25);
                text[i].setLayoutY(45 + (i - 1) * 30);
                scrollAnchorPane.setMinHeight(35 + i * 30);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
        }

    }
}
