package com.jars.exerciseapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.recyclers.SliderAdapter;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

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

        if(getPreferences()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }


        sliderViewPager = findViewById(R.id.sliderPager);
        linearLayout = findViewById(R.id.dotsLayout);
        backButton = findViewById(R.id.btBackSlider);
        nextButton = findViewById(R.id.btNextSlider);

        backButton.setEnabled(false);
        backButton.setVisibility(View.INVISIBLE);
        nextButton.setText("NEXT");
        backButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

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
            dotsIndicartor[i].setTextColor(getResources().getColor(R.color.colorPrimary));

            linearLayout.addView(dotsIndicartor[i]);
        }
        if (dotsIndicartor.length > 0) {
            dotsIndicartor[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNextSlider:
                if (currentPosition >= 0 && currentPosition <=1) {
                    sliderViewPager.setCurrentItem(currentPosition+1);
                } else {
                    setPreferences();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }

                break;
            case R.id.btBackSlider:
                if (currentPosition >= 1 && currentPosition <=2) {
                    sliderViewPager.setCurrentItem(currentPosition-1);
                }
                break;
        }
    }

    private void setPreferences() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("tutorial",true);
        editor.apply();
    }

    private boolean getPreferences() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("tutorial",false);
    }
}
