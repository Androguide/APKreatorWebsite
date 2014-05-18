/**   Copyright (C) 2013  Louis Teboul (a.k.a Androguide)
 *
 *    admin@pimpmyrom.org  || louisteboul@gmail.com
 *    http://pimpmyrom.org || http://androguide.fr
 *    71 quai Clémenceau, 69300 Caluire-et-Cuire, FRANCE.
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License along
 *      with this program; if not, write to the Free Software Foundation, Inc.,
 *      51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 **/

package com.androguide.apkreator.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androguide.apkreator.R;
import com.androguide.apkreator.helpers.Helpers;
import com.androguide.apkreator.helpers.youtube.GetYouTubeUserVideosTask;
import com.androguide.apkreator.helpers.youtube.Library;
import com.androguide.apkreator.helpers.youtube.PlayVideo;
import com.androguide.apkreator.helpers.youtube.Video;
import com.androguide.apkreator.pluggable.parsers.ParserInterface;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardLoadMore;
import com.fima.cardsui.views.CardUI;
import com.fima.cardsui.views.CardYoutube;

import java.util.ArrayList;


/**
 * Every tab in the application contains an instance of this unique Fragment
 * Object. I send & retrieve the desired position of the tab which each
 * PluginFragment instance should belong to via the Activity Bundle. This
 * 0-based index also determines which XML plugin file the PluginFragment
 * instance will load (tab0.xml, tab1.xml, tab2.xml etc...)
 *
 * @see com.androguide.apkreator.MainActivity
 */
public class YouTubeFragment extends Fragment implements ParserInterface {

    public static LinearLayout ll;
    private static ActionBarActivity fa;
    private static CardUI mCardUI;
    private static SharedPreferences mPrefs;
    private static String mYoutubeUser;
    private static int mLastIndex = 0, mTotalItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        fa = (ActionBarActivity) super.getActivity();
        fa.setSupportProgressBarIndeterminateVisibility(true);

        ll = (LinearLayout) inflater.inflate(
                com.androguide.apkreator.R.layout.cardsui, container, false);

        mPrefs = fa.getSharedPreferences("CONFIG", 0);
        mYoutubeUser = mPrefs.getString("YOUTUBE_USER", "");

        mCardUI = (CardUI) ll
                .findViewById(com.androguide.apkreator.R.id.cardsui);
        mCardUI.addStack(new CardStack(""), true);

        new Thread(new GetYouTubeUserVideosTask(responseHandler,
                mYoutubeUser, 1, 50)).start();
        mLastIndex = 50;

        mPrefs.edit().putInt("LAST_INDEX", mLastIndex).commit();

        return ll;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.youtube_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.subscribe)
            Helpers.openUrl(fa, "http://youtube.com/user/" + mYoutubeUser);
        return super.onOptionsItemSelected(item);
    }

    public static Handler responseHandler = new Handler() {
        public void handleMessage(Message msg) {
            Library lib;
            final int lastPos = mCardUI.getLastCardStackPosition();
            try {
                // noinspection ConstantConditions
                lib = (Library) msg.getData().get(
                        GetYouTubeUserVideosTask.LIBRARY);
                mTotalItems = msg.getData().getInt("TOTAL_ITEMS");
                Log.e("TOTAL_ITEMS", "TOTAL ITEMS: " + mTotalItems);

                ArrayList<Video> list = (ArrayList<Video>) lib.getVideos();
                for (Video aList : list) {

                    final String videoId = aList.getUrl()
                            .replace("https://m.youtube.com/details?v=", "");
                    final String videoTitle = aList.getTitle();
                    final String videoDesc = aList.getDescription();
                    final String videoDate = aList.getUploaded();
                    final int videoLength = aList.getDuration();
                    final int videoRating = aList.getRating();
                    final int videoLikes = aList.getLikes();
                    final int videoComments = aList.getComments();

                    final CardYoutube card = new CardYoutube(
                            videoTitle,
                            videoDesc,
                            mPrefs.getString("APP_COLOR", "#96AA39"),
                            aList.getThumbUrl(),
                            aList.getDuration(),
                            videoRating,
                            videoLikes,
                            videoComments
                    );

                    mCardUI.addAsyncCard(card, false, fa);

                    card.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(fa, PlayVideo.class);
                            mPrefs.edit()
                                    .putString("VIDEO_TO_PLAY", videoId)
                                    .putString("VIDEO_TITLE", videoTitle)
                                    .putString("VIDEO_DESCRIPTION", videoDesc)
                                    .putInt("VIDEO_LENGTH", videoLength)
                                    .putInt("VIDEO_RATING", videoRating)
                                    .putInt("VIDEO_LIKES", videoLikes)
                                    .putInt("VIDEO_COMMENTS", videoComments)
                                    .commit();

                            fa.startActivity(intent);
                        }
                    });

                }

                mCardUI.refresh();

                final CardLoadMore loadMore = new CardLoadMore("Load More...", "", fa);
                loadMore.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        fa.setSupportProgressBarIndeterminateVisibility(true);

                        if (mLastIndex + 50 <= mTotalItems) {
                            mCardUI.getLastStack().remove(0);
                            new Thread(new GetYouTubeUserVideosTask(responseHandler,
                                    mPrefs.getString("YOUTUBE_USER", ""), mLastIndex + 1, 50)).start();
                            mLastIndex += 50;

                        } else {
                            mCardUI.getLastStack().remove(0);
                            int toAdd = mTotalItems - mLastIndex;
                            new Thread(new GetYouTubeUserVideosTask(responseHandler,
                                    mPrefs.getString("YOUTUBE_USER", ""), mLastIndex + 1, toAdd)).start();
                            mLastIndex += toAdd;
                        }
                    }
                });
                mCardUI.addCard(loadMore, true);

            } catch (Exception e) {
                Log.e("Exception", e.getMessage() + "");
            }
            fa.setSupportProgressBarIndeterminateVisibility(false);
        }
    };

}
