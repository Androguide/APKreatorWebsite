package com.androguide.apkreator.helpers.gplus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.androguide.apkreator.helpers.youtube.LogYoutube;
import com.androguide.apkreator.helpers.youtube.StreamUtils;
import com.androguide.apkreator.pluggable.objects.Config;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GooglePlusStreamTask implements Runnable {
    // The Google API Developer Key
    // TODO: remove my key before making sources public
    //    private String DEV_KEY = "AIzaSyCaIJoWM1Ft-8_9NMXTcno2jtNxLl64XHk";
    // A reference to retrieve the data when this task finishes
    public static final String STREAM = "Stream";
    // A handler that will be notified when the task is finished
    private final Handler replyTo;
    // The user we are querying on Google+ for posts
    private final String username;
    private String mDevKey;
    private int startIndex;

    /**
     * @param replyTo  - the handler you want to receive the response when this task
     *                   has finished
     * @param username - the username of who on Google+ you are browsing
     */
    public GooglePlusStreamTask(Handler replyTo, String username, int startIndex) {
        this.replyTo = replyTo;
        this.username = username;
        this.startIndex = startIndex;
    }

    public GooglePlusStreamTask(Handler replyTo, String username, String devKey, int startIndex) {
        this.replyTo = replyTo;
        this.username = username;
        this.startIndex = startIndex;
        this.mDevKey = devKey;
    }

    @Override
    public void run() {
        try {

            HttpClient client = new DefaultHttpClient();
            Log.e("URL", "URL: " + "https://www.googleapis.com/plus/v1/people/" + username + "/activities/public?maxResults=50&key=" + mDevKey);
            HttpUriRequest request = new HttpGet(
                    "https://www.googleapis.com/plus/v1/people/" + username + "/activities/public?maxResults=50&key=" + mDevKey
            );
            HttpResponse response = client.execute(request);
            String jsonString = StreamUtils.convertToString(response
                    .getEntity().getContent());
            Log.e("JSON G+", jsonString);
            JSONObject json = new JSONObject(jsonString);

            JSONArray jsonArray = json.getJSONArray("items");
            Log.e("Server Response", jsonArray.toString() + "");

            List<Post> posts = new ArrayList<Post>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject actor = object.getJSONObject("actor");
                JSONObject image = actor.getJSONObject("image");
                String originalTitle = "";
                Boolean hasImage = false;

//                if (object.has("title")) originalTitle = object.getString("title");

                String resharedTitle = "",
                        resharedDescription = "",
                        resharedText = "",
                        resharedUrl = "",
                        resharedImage = "";


                if (object.getJSONObject("object").has("attachments")) {
                    JSONArray attachments = object.getJSONObject("object").getJSONArray("attachments");
                    for (int j = 0; j < attachments.length(); j++) {
                        JSONObject content = attachments.getJSONObject(j);
                        if (content.has("objectType")) {

                            if (content.getString("objectType").equals("article")) {
                                resharedTitle = content.getString("displayName");
                                if (content.has("image")) {
                                    hasImage = true;
                                    JSONObject imageAttachment = content.getJSONObject("image");
                                    resharedImage = imageAttachment.getString("url");
//                                    Log.e("GPLUS", "has image : " + resharedImage);
                                }

                            } else if (content.getString("objectType").equals("photo")) {
                                JSONObject imageAttachment = content.getJSONObject("image");
                                resharedImage = imageAttachment.getString("url");


                            } else if (content.getString("objectType").equals("video")) {
                                resharedTitle = content.getString("displayName");
                                JSONObject imageAttachment = content.getJSONObject("image");
                                resharedImage = imageAttachment.getString("url");
                            }
                        }
                    }
                }

                String annotation = "";
                if (object.has("annotation")) {
                    annotation = object.getString("annotation");
                }

                if (object.getJSONObject("object").has("content")) {
                    resharedText = object.getJSONObject("object").getString("content");
                }

                String userName = actor.getString("displayName");
                String avatarUrl = image.getString("url");
                Boolean isReshared = object.getString("verb").equals("share");
                int plusOnes = object.getJSONObject("object").getJSONObject("plusoners").getInt("totalItems");

                String resharedFrom = "";
                if (object.getJSONObject("object").has("actor"))
                    resharedFrom = object.getJSONObject("object").getJSONObject("actor").getString("displayName");

                String postUrl = object.getJSONObject("object").getString("url");


                posts.add(new Post(userName, avatarUrl, annotation, resharedTitle, resharedDescription, resharedText, resharedImage, postUrl, resharedFrom, originalTitle, hasImage, isReshared, plusOnes));

            }

            Stream lib = new Stream(username, posts);
            Bundle data = new Bundle();
            data.putSerializable(STREAM, lib);

            Message msg = Message.obtain();

            if (msg != null) {
                msg.setData(data);
                replyTo.sendMessage(msg);
            }

        } catch (ClientProtocolException e) {
            LogYoutube.e("Error", e);
        } catch (IOException e) {
            LogYoutube.e("Error", e);
        } catch (JSONException e) {
            LogYoutube.e("Error", e);
        }
    }
}