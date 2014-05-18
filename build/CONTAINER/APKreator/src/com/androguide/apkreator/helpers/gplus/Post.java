package com.androguide.apkreator.helpers.gplus;

import java.io.Serializable;

/**
 * This is an Object representation of a user's YouTube video
 */
public class Post implements Serializable {

    /**
     * randomly generated session UID
     */
    private static final long serialVersionUID = 429416120011327727L;

    /**
     * those variables represent various characteristics of a YouTube video such as its title, duration, etc..
     */
    private String username, avatarUrl, resharedTitle, resharedDesc, resharedText, annotation, imageUrl, postUrl, resharedFrom, originalTitle;
    private Boolean hasImage, isReshared;
    private int plusOnes;

    public Post(String username, String avatarUrl, String annotation, String resharedTitle, String resharedDesc, String resharedText, String imageUrl, String postUrl, String resharedFrom,
                String originalTitle, Boolean hasImage, Boolean isReshared, int plusOnes) {
        super();
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.resharedTitle = resharedTitle;
        this.resharedDesc = resharedDesc;
        this.resharedText = resharedText;
        this.annotation = annotation;
        this.imageUrl = imageUrl;
        this.postUrl = postUrl;
        this.resharedFrom = resharedFrom;
        this.originalTitle = originalTitle;
        this.hasImage = hasImage;
        this.isReshared = isReshared;
        this.plusOnes = plusOnes;
    }

    /**
     * @return the display name of the user
     */
    public String getUsername() {
        return username;
    }

    public String getResharedTitle() {
        return resharedTitle;
    }

    public String getResharedDesc() {
        return resharedDesc;
    }

    public String getResharedText() {
        return resharedText;
    }

    /**
     * @return the post's text body
     */
    public String getAnnotation() {
        return annotation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public String getResharedFrom() {
        return resharedFrom;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @return the image url of the post
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public Boolean getIsReshared() {
        return isReshared;
    }

    public int getPlusOnes() {
        return plusOnes;
    }

//    /**
//     * @return the date at which the post was shared (e.g: 2007-06-05T22:07:03.000Z)
//     */
//    public String getDate() {
//        return date;
//    }
}