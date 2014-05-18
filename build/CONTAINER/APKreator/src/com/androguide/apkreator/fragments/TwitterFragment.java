package com.androguide.apkreator.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androguide.apkreator.R;
import com.androguide.apkreator.helpers.twitter.Tweet;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TwitterFragment extends Fragment {

    private ActionBarActivity fa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LinearLayout ll = (LinearLayout) inflater.inflate(
                R.layout.twitter_fragment, container, false);
        fa = (ActionBarActivity) super.getActivity();

        new Thread(new Runnable() {

            @Override
            public void run() {
                ArrayList<Tweet> tweetsArray = loadTweets();
                for (int i = 0; i < tweetsArray.size(); i++) {
                    Log.e("Tweet Author", tweetsArray.get(i).getAuthor());
                    Log.e("Tweet Content", tweetsArray.get(i).getContent());
                }
            }
        }).start();

        return ll;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private ArrayList<Tweet> loadTweets() {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        try {
            Log.e("Loading Tweets...", "...");
            HttpClient hc = new DefaultHttpClient();
            HttpGet get = new HttpGet(
                    "https://api.twitter.com/1.1/search/tweets.json?q=%40android");
            HttpResponse rp = hc.execute(get);

            if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(rp.getEntity());
                JSONObject root = new JSONObject(result);
                JSONArray sessions = root.getJSONArray("results");
                for (int i = 0; i < sessions.length(); i++) {
                    JSONObject session = sessions.getJSONObject(i);

                    Tweet tweet = new Tweet();
                    tweet.setContent(session.getString("text"));
                    tweet.setAuthor(session.getString("from_user"));
                    tweets.add(tweet);
                    Log.e("text", session.getString("text"));
                    Log.e("user", session.getString("from_user"));
                }
            }

        } catch (Exception e) {
            Log.e("TwitterFeedActivity", "Error loading JSON", e);
        }
        return tweets;
    }

}
