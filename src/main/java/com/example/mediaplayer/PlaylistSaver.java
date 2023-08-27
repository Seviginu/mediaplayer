package com.example.mediaplayer;

import com.example.mediaplayer.music.Playlist;

import java.io.*;

public class PlaylistSaver {
    private PlaylistSaver(){

    }

    public static void save(Playlist playlist, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(playlist);
    }

    public static Playlist load(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream oos = new ObjectInputStream(fis);
        return (Playlist) oos.readObject();
    }
}
