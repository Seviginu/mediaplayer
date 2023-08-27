package com.example.mediaplayer.music;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {
    private final ArrayList<Track> songList;
    private String name;


    public Playlist(){
        songList = new ArrayList<>();
    }


    public Playlist(ArrayList<Track> songList){
        this.songList = songList;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    private boolean validate(String fileName){
        return true;
    }

    public void add(Track track){
        songList.add(track);
    }

    public Track getLast(){
        return songList.get(songList.size()-1);
    }

    public List<Track> getList(){
        return new ArrayList<>(songList);
    }
}
