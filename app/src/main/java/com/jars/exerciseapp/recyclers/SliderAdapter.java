package com.jars.exerciseapp.recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.beans.Circuit;
import com.jars.exerciseapp.beans.Tutorial;

public class SliderAdapter extends PagerAdapter {
    private Context mContext;
    private Tutorial [] tutorials;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        mContext=context;
        setPages();
    }

    private void setPages() {
        tutorials= new Tutorial[3];
        tutorials[0]= new Tutorial(R.drawable.pre_week_1_friday_1, "Tutorial 1", "Hola esto es el tutorial 1");
        tutorials[1]= new Tutorial(R.drawable.pre_week_1_friday_2, "Tutorial 2", "Hola esto es el tutorial 2");
        tutorials[2]= new Tutorial(R.drawable.pre_week_1_friday_3, "Tutorial 3", "Hola esto es el tutorial 3");
    }


    @Override
    public int getCount() {
        return tutorials.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.page_content_tutorial, container ,false);

        ImageView imageSlider = view.findViewById(R.id.imageSiler);
        TextView txtHeader = view.findViewById(R.id.txtHeadSlider);
        TextView txtDescription = view.findViewById(R.id.txtDescriptionSlider);

        imageSlider.setImageResource(tutorials[position].getImage());
        txtHeader.setText(tutorials[position].getHeader());
        txtDescription.setText(tutorials[position].getDescription());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
