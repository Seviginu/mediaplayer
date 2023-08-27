package com.example.mediaplayer;

import com.example.mediaplayer.music.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Slider progressSlider;
    @FXML
    private BorderPane wavePane;
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<Track> table;
    @FXML
    private TableColumn<Track, String> trackName;
    private Media media;
    private MediaPlayer player;
    private final Playlist playlist = new Playlist();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        trackName.setCellValueFactory(new PropertyValueFactory<>("name"));


        table.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                    if(table.getSelectionModel().getSelectedItem().getPath().getAbsolutePath().equals(media.getSource())) player.stop();
                    table.getItems().remove(table.getSelectionModel().getSelectedItem());

                }
            }
        });

    }

    public void onAdd(ActionEvent e){
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MEDIA", "*.mp3", "*.wav"));
        File trackFile = chooser.showOpenDialog(((Node)e.getSource()).getScene().getWindow());
        playlist.add(new Track(trackFile));
        table.getItems().add(new Track((trackFile)));
    }

    private void setNextTrack(){
        table.getSelectionModel().selectNext();
        setTrack();
    }

    private void setTrack(){
        File file = table.getSelectionModel().getSelectedItem().getPath();
        media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);
        player.setOnEndOfMedia(this::setNextTrack);
        player.setOnReady(() -> {
            progressSlider.setMax(media.getDuration().toSeconds());
            System.out.println("Duration: "+media.getDuration().toMinutes());
            player.play();
        });
        player.currentTimeProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                progressSlider.setValue(t1.toSeconds());
            }
        });

        progressSlider.setOnMouseDragged(mouseEvent -> {
            player.seek(Duration.seconds(progressSlider.getValue()));
        });
        progressSlider.setOnMousePressed(mouseEvent -> {
            player.setMute(true);
            player.seek(Duration.seconds(progressSlider.getValue()));

        });
        progressSlider.setOnMouseReleased(mouseEvent -> {
            player.setMute(false);
        });

    }

    public void onPlay(ActionEvent event)   {
        try {
            Duration total = media.getDuration();
            System.out.println(total.toSeconds());
            System.out.println(total);
            System.out.println(total.toMillis());
            System.out.println(player.getCurrentTime());
            System.out.println(player.getCurrentTime().toSeconds());

        }
        catch (Exception exception) {}

        if(player == null && table.getSelectionModel().getSelectedItem() == null){
            table.getSelectionModel().selectFirst();
        }
        if(player != null && player.getStatus() == MediaPlayer.Status.PAUSED) player.play();
        else if (player == null || player.getStatus() == MediaPlayer.Status.STOPPED ) {
            setTrack();
        }

    }

    public void onPause(ActionEvent event) {
        player.pause();
    }
    public void onStop(ActionEvent event) {
        player.stop();
    }

    public void onPlaylists(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("playlists-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        PlaylistsView controller = loader.getController();
        newWindow.show();
        controller.setTableView(table);
        controller.setCurrentPlaylist(playlist);
    }

    public void onEqualizer(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("equalizer-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        EqualizerView controller = loader.getController();
        controller.setEqualizer(player.getAudioEqualizer());
        newWindow.show();

    }
}