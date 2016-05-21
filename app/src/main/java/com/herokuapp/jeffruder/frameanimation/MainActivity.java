package com.herokuapp.jeffruder.frameanimation;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.heartScroller) ImageView mHeartScrollerImageView;
    @Bind(R.id.heartSelector) ImageView mHeartSelectorImageView;
    @BindDrawable(R.drawable.heart0) Drawable mHeart0;
    @BindDrawable(R.drawable.heart1) Drawable mHeart1;
    @BindDrawable(R.drawable.heart2) Drawable mHeart2;
    @BindDrawable(R.drawable.heart3) Drawable mHeart3;
    @BindDrawable(R.drawable.heart4) Drawable mHeart4;

    private GestureDetector mGestureDetector;
    private ArrayList<Drawable> mHearts;

    private float mTotalDistanceY = 0;
    private int mCurrentHeartIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeDetector();
        mHearts = new ArrayList<>(Arrays.asList(mHeart0, mHeart1, mHeart2, mHeart3, mHeart4));

        mHeartScrollerImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    //the scroll is over, so reset the total distance counter
                    mTotalDistanceY = 0;
                }
                return mGestureDetector.onTouchEvent(event);
            }
        });

        mHeartSelectorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeartSelectorImageView.setActivated(!mHeartSelectorImageView.isActivated());
            }
        });
    }

    private void initializeDetector() {
        mGestureDetector = new GestureDetector(this, new SimpleOnScrollListener());
    }

    private class SimpleOnScrollListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mTotalDistanceY += distanceY;
            if (mTotalDistanceY > 35) {
                mCurrentHeartIndex++;
                if (mCurrentHeartIndex > mHearts.size() - 1) {
                    mCurrentHeartIndex = mHearts.size() - 1;
                }
                mHeartScrollerImageView.setImageDrawable(mHearts.get(mCurrentHeartIndex));
                mTotalDistanceY = 0;
            } else if (mTotalDistanceY < -35) {
                mCurrentHeartIndex--;
                if (mCurrentHeartIndex < 0) {
                    mCurrentHeartIndex = 0;
                }
                mHeartScrollerImageView.setImageDrawable(mHearts.get(mCurrentHeartIndex));
                mTotalDistanceY = 0;
            }
            return false;
        }
    }
}
