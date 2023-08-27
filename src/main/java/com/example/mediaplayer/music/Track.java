package com.example.mediaplayer.music;

import java.io.File;
import java.io.Serializable;

public class Track implements Serializable {
    private String name;

    public File getPath() {
        return path;
    }

    private final File path;

    public Track(String fileName) {
        path = new File(fileName);
        this.name = fileName;
    }

    public Track(File file) {
        this.path = file;
        this.name = file.getName();
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
