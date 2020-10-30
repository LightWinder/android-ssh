package com.example.wxj.broadcast;

import android.os.AsyncTask;

/**
 * Created by wxj on 2020/10/27.
 */

public class PersonTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        Shell.shell();
        return null;
    }
}

