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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androguide.apkreator.R;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.LOG;

import java.util.concurrent.ExecutorService;

@SuppressLint("SetJavaScriptEnabled")
public class PhoneGapFragment extends Fragment implements CordovaInterface {

    private LinearLayout ll;
    private ActionBarActivity fa;
    public static CordovaWebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa = (ActionBarActivity) super.getActivity();
        ll = (LinearLayout) inflater.inflate(R.layout.phonegap, container,
                false);
        webView = (CordovaWebView) (ll != null ? ll.findViewById(R.id.cordova_web_view) : null);
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDatabaseEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
        }
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
    public Object onMessage(String id, Object data) {
        LOG.d("PhoneGapFragment", "onMessage(" + id + "," + data + ")");
        if ("exit".equals(id))
            fa.finish();
        return null;
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
    }

    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public ExecutorService getThreadPool() {
        return null;
    }

    public static void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public PhoneGapFragment getPhoneGapFragment() {
        return this;
    }
}