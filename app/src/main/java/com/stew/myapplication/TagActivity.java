package com.stew.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by stew on 17/1/16.
 * mail: stewforani@gmail.com
 */
public class TagActivity extends Activity {
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        TextView tag = (TextView) findViewById(R.id.tvTag);

        String str = "tag : " + getIntent().getIntExtra("tag", 0);

        tag.setText(str);

    }

    public static Intent getInstance(Context context, int tag) {
        Intent intent = new Intent(context, TagActivity.class);
        intent.putExtra("tag", tag);
        return intent;
    }
}
