package de.seifi.mongo_editor;

import de.seifi.mongo_editor.controllers.ControllerBase;
import de.seifi.mongo_editor.controllers.MainController;
import de.seifi.mongo_editor.controllers.SplashController;
import de.seifi.mongo_editor.utils.ConnectionReader;
import de.seifi.mongo_editor.utils.MongoDbConnection;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.Pair;
import org.springframework.boot.context.event.SpringApplicationEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MongoDbEditorFxApp extends Application implements Runnable {

    private static final int SPLASH_WIDTH = 770;

    private static final int SPLASH_HEIGHT = 300;

    private static Scene mainScene;

    private static Stage splashStage;

    private GridPane splashPane;

    public static Stage mainStage;

    private static String[] startArgs = null;

    private static MainController mainController;

    private static ControllerBase currentController;

    private static SplashController splashController;

    private static ConnectionReader connectionReader = new ConnectionReader();



    public static void main(String[] args) throws IOException {

        String appId = "RechnungManagerAppId";
        boolean alreadyRunning;

        connectionReader.read();
        //connectionReader.addConnection(new MongoDbConnection("default", "mq-mongo01.mediqon.local", 27017, "mongoadmin", "yPDKljYv6kA1MvStSBbw"), true);

        MongoDbEditorFxApp.startArgs = args;
        MongoDbEditorFxApp.launch(args);

    }

    public static void newSpringInitialEvent(SpringApplicationEvent event) {
        splashController.newSpringInitialEvent(event);
    }

    @Override
    public void run() {
        MongoDbEditorSpringApp.main(startArgs);

        try {
            showMainStage();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private void showMainStage() throws IOException {
        mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("FX Experience");
        mainStage.setIconified(true);

        mainScene = new Scene(loadFXML("main"));
        mainScene.getStylesheets().add(getMainStyle());

        mainStage.setScene(mainScene);

        mainScene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

        mainStage.show();

        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1), splashPane);
        fadeSplash.setFromValue(1.0);
        fadeSplash.setToValue(0.0);

        fadeSplash.setOnFinished(ev -> {
                mainStage.setIconified(false);
                splashStage.hide();
        });
        fadeSplash.play();

    }

    private void closeWindowEvent(WindowEvent event) {
        if(isCurrentControllerDirty()){
            //UiUtils.showError("Niche gespeicherte Änderungen ...", currentController.getDirtyMessage());

            event.consume();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        showSplash(primaryStage);

        Platform.runLater(this);

    }

    private void showSplash(Stage initStage) throws IOException {

        splashStage = initStage;

        Pair<Parent, FXMLLoader> pair = loadFXMLLoader("splash");

        splashPane =  (GridPane)pair.getKey();
        FXMLLoader fxmlLoader = pair.getValue();
        splashController = fxmlLoader.<SplashController>getController();

        Scene splashScene = new Scene(splashPane, SPLASH_WIDTH, SPLASH_HEIGHT);
        splashScene.getStylesheets().add(getMainStyle());
        initStage.initStyle(StageStyle.UNDECORATED);
        initStage.setOpacity(1.0);
        initStage.setScene(splashScene);
        initStage.show();

        initStage.toFront();
    }

    private static Pair<Parent, FXMLLoader> loadFXMLLoader(String fxml) throws IOException {
        URL fxmlResource = MongoDbEditorFxApp.class.getResource("fxml/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlResource);
        return new Pair<>(fxmlLoader.load(), fxmlLoader) ;
    }

    public static String getMainStyle() {
        return MongoDbEditorFxApp.class.getResource("styles/styles.css").toExternalForm();
    }

    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        URL fxmlResource = MongoDbEditorFxApp.class.getResource("fxml/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlResource);
        return fxmlLoader.load();
    }

    public static MainController getMainController() {
        return MongoDbEditorFxApp.mainController;
    }

    public static void setMainController(MainController mainController) {
        MongoDbEditorFxApp.mainController = mainController;
    }

    public static ControllerBase getCurrentController() {
        return currentController;
    }

    public static boolean isCurrentControllerDirty() {
        return currentController != null? currentController.isDirty() : false;
    }

    public static void setCurrentController(ControllerBase currentController) {
        MongoDbEditorFxApp.currentController = currentController;
    }

    public static Map<String, MongoDbConnection> getConnections() {
        return connectionReader.getConnections();
    }

}
