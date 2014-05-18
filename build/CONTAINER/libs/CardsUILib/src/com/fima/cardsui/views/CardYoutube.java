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

package com.fima.cardsui.views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fima.cardsui.R;
import com.fima.cardsui.objects.Card;
import com.squareup.picasso.Picasso;

public class CardYoutube extends Card {

    protected static final String DEV_KEY = "AIzaSyCaIJoWM1Ft-8_9NMXTcno2jtNxLl64XHk";

    public CardYoutube(String title, String desc, String titleColor, String url, int duration, int rating, int likes, int comments) {
        super(title, desc, titleColor, url, duration, rating, likes, comments);
    }

    @Override
    public View getCardContent(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_youtube, null);
        int minutes = duration / 60;
        int seconds = duration % 60;
        String length = String.format("%d:%02d", minutes, seconds);
        assert v != null;
        Picasso.with(context).load(url).placeholder(R.drawable.no_thumbnail).into((ImageView) v.findViewById(R.id.image));
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color.parseColor(titleColor));
        ((TextView) v.findViewById(R.id.description)).setText(desc);
        ((TextView) v.findViewById(R.id.rating)).setText(rating + "");
        ((TextView) v.findViewById(R.id.likes)).setText(likes + "");
        ((TextView) v.findViewById(R.id.comments)).setText(comments + "");
        ((TextView) v.findViewById(R.id.duration)).setText(length);

        return v;
    }

    public int getCardContentId() {
        return R.layout.card_youtube;
    }

    @Override
    public boolean convert(View convertCardView) {
        return true;
    }
}
