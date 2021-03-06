package com.per.notezzw.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.per.notezzw.R;

/**
 * Created by 22762 on 2016/1/3.
 */
public class BaseActivity extends Activity {
    protected ImageButton mReturn;
    protected TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParentView();
        setParentListener();
    }

    protected void initParentView() {
        mReturn = (ImageButton) findViewById(R.id.baseactivity_ib_return);
        mTitle = (TextView) findViewById(R.id.baseactivity_tv_title);
    }

    protected void setParentListener() {
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
