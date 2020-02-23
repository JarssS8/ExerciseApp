package com.jars.exerciseapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.recyclers.SliderAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager sliderViewPager;
    private LinearLayout linearLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dotsIndicartor;
    private Button backButton, nextButton;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        sliderViewPager = findViewById(R.id.sliderPager);
        linearLayout = findViewById(R.id.dotsLayout);
        backButton = findViewById(R.id.btBackSlider);
        nextButton = findViewById(R.id.btNextSlider);

        backButton.setEnabled(false);
        backButton.setVisibility(View.INVISIBLE);
        nextButton.setText("NEXT");

        sliderAdapter = new SliderAdapter(this);
        sliderViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        sliderViewPager.addOnPageChangeListener(pageChangeListener);
    }

    private void addDotsIndicator(int position) {
        dotsIndicartor = new TextView[sliderAdapter.getCount()];
        linearLayout.removeAllViews();
        for (int i = 0; i < dotsIndicartor.length; i++) {
            dotsIndicartor[i] = new TextView(this);
            dotsIndicartor[i].setText(Html.fromHtml("&#8226;"));
            dotsIndicartor[i].setTextSize(35);
            dotsIndicartor[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            linearLayout.addView(dotsIndicartor[i]);
        }
        if (dotsIndicartor.length > 0) {
            dotsIndicartor[position].setTextColor(getResources().getColor(R.color.white));
        }


    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            currentPosition = position;

            if (position == 0) {
                backButton.setEnabled(false);
                backButton.setVisibility(View.INVISIBLE);

                nextButton.setText("NEXT");
            } else if (position == 1) {
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                backButton.setText("BACK");

                nextButton.setVisibility(View.VISIBLE);
                nextButton.setEnabled(true);
                nextButton.setText("NEXT");
            } else {
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setEnabled(true);
                nextButton.setText("FINISH");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
