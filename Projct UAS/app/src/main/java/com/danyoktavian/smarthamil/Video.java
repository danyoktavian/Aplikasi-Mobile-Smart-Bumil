package com.danyoktavian.smarthamil;

public class Video {
    private String videoId;
    private String description;

    // Konstruktor
    public Video(String videoId, String description) {
        this.videoId = videoId;
        this.description = description;
    }

    // Getter
    public String getVideoId() {
        return videoId;
    }

    public String getDescription() {
        return description;
    }

    // Setter
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
