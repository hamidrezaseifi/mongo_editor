package de.seifi.mongo_editor.controllers;

import com.mongodb.*;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import de.seifi.mongo_editor.MongoDbEditorFxApp;
import de.seifi.mongo_editor.utils.MongoDbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private VBox rootBox;

    @FXML private SplitPane splMain;

    @FXML private MenuBar menuBar;

    @FXML private TabPane tbMain;

    @FXML private ListView<MongoDbConnection> lstConnections;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBox.setVgrow(splMain, Priority.ALWAYS);

        menuBar.setFocusTraversable(true);
        menuBar.prefWidthProperty().bind(rootBox.widthProperty());

        Map<String, MongoDbConnection> connections =  MongoDbEditorFxApp.getConnections();
        for(String key: connections.keySet()){
            lstConnections.getItems().add(connections.get(key));
        }

    }

    @FXML
    private void handleAboutAction()
    {
        //https://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials/authentication/
        //http://mongodb.github.io/mongo-java-driver/3.2/driver/getting-started/quick-tour/
        //java mongodb
        MongoCredential credential = MongoCredential.createCredential("mongoadmin", "p21mapping", "yPDKljYv6kA1MvStSBbw".toCharArray());
        //MongoClient mongoClient = new MongoClient("host1", 27017, Arrays.asList(credential));

        MongoClientOptions options = MongoClientOptions.builder().build();
        //MongoClientURI uri = new MongoClientURI("mongodb://user1:pwd1@host1/?authSource=db1&authMechanism=SCRAM-SHA-1");
        MongoClientURI uri = new MongoClientURI("mongodb://mongoadmin:yPDKljYv6kA1MvStSBbw@mq-mongo01.mediqon.local:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false");
        //MongoClient mongoClient = new MongoClient(new ServerAddress("mq-mongo01.mediqon.local", 27017), credential, options);
        MongoClient mongoClient = new MongoClient(uri);

        MongoDatabase database = mongoClient.getDatabase("p21mapping");

        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }

        MongoIterable<String> cols =  database.listCollectionNames();
        MongoCursor<String> iterator = cols.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    @FXML
    private void handleConnectionSettings() {

    }

}
