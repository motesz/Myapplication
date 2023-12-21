package com.montesdiether.myapplication.data;

public class musicwishlist {
    public int musicID;
    public String musicName;
    public String musicAuthor;

    musicwishlist(int musicID, String musicName, String musicAuthor){
        this.musicID = musicID;
        this.musicName = musicName;
        this.musicAuthor = musicAuthor;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "musicID=" + musicID +
                ", musicName='" + musicName + '\'' +
                ", musicAuthor='" + musicAuthor + '\'' +
                '}';
    }
}
