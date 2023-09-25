package com.example.mediaplayer;

import com.example.mediaplayer.music.Playlist;
import com.example.mediaplayer.music.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlaylistsView implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<Playlist> playlistBox;
    private Playlist currentPlaylist;
    private final File directory = new File("playlists");
    private TableView<Track> tableView;

    public void setCurrentPlaylist(Playlist list){
        currentPlaylist = list;
    }

    public void setTableView(TableView<Track> tableView){
        this.tableView = tableView;
    }

    public void onSave(ActionEvent event) throws IOException {
        String name = nameField.getText();
        File file = new File(directory.getAbsoluteFile() + File.separator + name + ".playlist");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        PlaylistSaver.save(currentPlaylist, file);
        currentPlaylist.setName(name);
        playlistBox.getItems().add(currentPlaylist);
    }

    public void onSet(ActionEvent e){
        tableView.setItems(FXCollections.observableList(playlistBox.getSelectionModel().getSelectedItem().getList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlistBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Playlist playlist) {
                return playlist == null ? null : playlist.getName();
            }

            @Override
            public Playlist fromString(String s) {
                return null;
            }
        });
        ObservableList<Playlist> playlists = FXCollections.observableArrayList();
        for(File file : Objects.requireNonNull(directory.listFiles())){
            try {
                playlists.add(PlaylistSaver.load(file));
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        playlistBox.setItems(playlists);
    }
}
