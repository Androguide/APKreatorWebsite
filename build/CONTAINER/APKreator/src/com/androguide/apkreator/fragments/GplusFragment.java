package com.androguide.apkreator.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androguide.apkreator.R;
import com.androguide.apkreator.helpers.Helpers;
import com.androguide.apkreator.helpers.gplus.GooglePlusStreamTask;
import com.androguide.apkreator.helpers.gplus.Post;
import com.androguide.apkreator.helpers.gplus.Stream;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardGplus;
import com.fima.cardsui.views.CardUI;

import java.util.ArrayList;

public class GplusFragment extends Fragment {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final ArrayList<Card> mCards = new ArrayList<Card>();
    private static ActionBarActivity fa;
    private static CardUI mCardUI;
    private static String mGplusPageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout ll = (LinearLayout) inflater.inflate(
                R.layout.gplus_fragment, container, false);
        fa = (ActionBarActivity) super.getActivity();
        fa.setSupportProgressBarIndeterminateVisibility(true);
        SharedPreferences mPrefs = fa.getSharedPreferences("CONFIG", 0);
        mCardUI = (CardUI) (ll != null ? ll.findViewById(R.id.cards_ui) : null);
        mCardUI.addStack(new CardStack(""), true);
        mGplusPageUrl = mPrefs.getString("GOOGLE+", "");
        String devKey = mPrefs.getString("DEVELOPER_KEY", "null");
        String gplusUsername = mGplusPageUrl.replace("http://plus.google.com/u/0/", "");
        gplusUsername = gplusUsername.replace("/posts", "");

        new Thread(new GooglePlusStreamTask(
                responseHandler,
                gplusUsername,
                devKey,
                0
        )).start();

        return ll;
    }

    private void threadCallback() {
        Card[] array = new Card[mCards.size()];
        for (int i = 0; i < mCards.size(); i++)
            array[i] = mCards.get(i);

        mCardUI.addSeparateCards(array);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.youtube_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.subscribe)
            Helpers.openUrl(fa, mGplusPageUrl);
        return super.onOptionsItemSelected(item);
    }

    public static Handler responseHandler = new Handler() {
        public void handleMessage(Message msg) {
            Stream lib;
            try {
                // noinspection ConstantConditions
                lib = (Stream) msg.getData().get(
                        GooglePlusStreamTask.STREAM);

                ArrayList<Post> list = (ArrayList<Post>) lib.getPosts();
                for (final Post aList : list) {
                    final CardGplus card = new CardGplus(
                            aList.getUsername(),
                            aList.getAnnotation(),
                            aList.getAvatarUrl(),
                            aList.getImageUrl(),
                            aList.getResharedTitle(),
                            aList.getResharedDesc(),
                            aList.getResharedText(),
                            aList.getResharedFrom(),
                            aList.getOriginalTitle(),
                            aList.getPlusOnes(),
                            aList.getIsReshared()
                    );

                    mCardUI.addAsyncCard(card, false, fa);

                    final String postUrl = aList.getPostUrl();
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent postIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
                            fa.startActivity(postIntent);
                        }
                    });
                }

                mCardUI.refresh();
                fa.setSupportProgressBarIndeterminateVisibility(false);

            } catch (Exception e) {
                Log.e("Exception", e.getMessage() + "");
            }
        }
    };

}
