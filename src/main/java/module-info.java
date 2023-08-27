module com.example.mediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires jave.core;

    opens com.example.mediaplayer to javafx.fxml;
    opens com.example.mediaplayer.music to javafx.base;

    exports com.example.mediaplayer;
    exports com.example.mediaplayer.music;
}