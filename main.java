import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage Stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("tabel.fxml"));

        Stage.setTitle("Diagram Batang dan Table");

        Stage.setScene(new Scene(root, 640, 600));

        Stage.show();
    }

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
        launch(args);
    }
}
