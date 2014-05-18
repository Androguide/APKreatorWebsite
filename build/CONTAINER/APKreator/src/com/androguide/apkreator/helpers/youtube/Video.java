package com.androguide.apkreator.helpers.youtube;

import java.io.Serializable;

/**
 * This is an Object representation of a user's YouTube video
 */
public class Video implements Serializable {

    /**
     * randomly generated session UID
     */
    private static final long serialVersionUID = 429416120011327727L;

    /**
     * those variables represent various characteristics of a YouTube video such as its title, duration, etc..
     */
    private String title, description, url, thumbUrl, uploaded;
    private int duration, rating, likes, comments;

    /** The actual constructor for the Video Object
     * @param title: the title of the video
     * @param description: the description of the video
     * @param url: the (mobile) url of the video
     * @param thumbUrl: the url of the video's thumbnail
     * @param uploaded: the date at which the video was uploaded
     * @param duration: the duration of the video
     * @param rating: the rating of the video
     * @param likes: the amount of "likes" gathered by the video
     * @param comments: the amount of comments on the video
     */
    public Video(String title, String description, String url, String thumbUrl, String uploaded, int duration, int rating, int likes, int comments) {
        super();
        this.title = title;
        this.description = description;
        this.uploaded = uploaded;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.duration = duration;
        this.rating = rating;
        this.likes = likes;
        this.comments = comments;
    }

    /**
     * @return the title of the video
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description of the video
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the url to this video on youtube
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the thumbUrl of a still image representation of this video
     */
    public String getThumbUrl() {
        return thumbUrl;
    }

    /**
     * @return the date at which the video was uploaded (e.g: 2007-06-05T22:07:03.000Z)
     */
    public String getUploaded() {
        return uploaded;
    }

    /**
     * @return the duration of the video (in seconds)
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return the rating of the video (a floating-point number between 0 and 5)
     */
    public int getRating() {
        return rating;
    }

    /**
     * @return the amount of "likes" gathered by the video
     */
    public int getLikes() {
        return likes;
    }

    /**
     * @return the amount of comments on the video
     */
    public int getComments() {
        return comments;
    }
}