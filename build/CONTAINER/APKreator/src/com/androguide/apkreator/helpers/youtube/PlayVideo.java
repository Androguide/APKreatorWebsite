package com.androguide.apkreator.helpers.youtube;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.androguide.apkreator.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideo extends YouTubeBaseActivity {

    protected static final String DEV_KEY = "AIzaSyCaIJoWM1Ft-8_9NMXTcno2jtNxLl64XHk";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video);

        //noinspection ConstantConditions
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences prefs = getSharedPreferences("CONFIG", 0);

        int mRating = prefs.getInt("VIDEO_RATING", 5);
        int mLikes = prefs.getInt("VIDEO_LIKES", 0);
        int mComments = prefs.getInt("VIDEO_COMMENTS", 0);

        String mTitle = prefs.getString("VIDEO_TITLE", "");
        String mDescription = prefs.getString("VIDEO_DESCRIPTION", "");
        String mDuration = String.format("%d:%02d", (prefs.getInt("VIDEO_LENGTH", 0) / 60), (prefs.getInt("VIDEO_LENGTH", 0) % 60));

        getActionBar().setTitle(mTitle);

        ((TextView) findViewById(R.id.title)).setText(mTitle);
        ((TextView) findViewById(R.id.desc)).setText(mDescription);
        ((TextView) findViewById(R.id.duration)).setText(mDuration);
        ((TextView) findViewById(R.id.rating)).setText(mRating + "");
        ((TextView) findViewById(R.id.likes)).setText(mLikes + "");
        ((TextView) findViewById(R.id.comments)).setText(mComments + "");

        YouTubePlayerView player = (YouTubePlayerView) findViewById(R.id.player);
        player.initialize(DEV_KEY, new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(Provider arg0,
                                                YouTubePlayer player, boolean arg2) {
                try {
                    player.loadVideo(prefs.getString("VIDEO_TO_PLAY", ""));
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Could not load requested video, please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
