package com.jars.exerciseapp.activities.fragments.days;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jars.exerciseapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MondayFragment extends Fragment {

    private View root;
    private Button btStartRoutine;
    private ArrayList<Integer> photos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_monday, container, false);

        //Todo
        cargarFotos();

        btStartRoutine = root.findViewById(R.id.btStartRoutine);
        btStartRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_day_to_nav_routine);
                RoutineFragment.setPhotos(photos);
            }
        });
        return root;
    }

    private void cargarFotos() {
        photos = new ArrayList<Integer>();
        photos.add(R.drawable.week1_monday_1);
        photos.add(R.drawable.week1_monday_2);
        photos.add(R.drawable.week1_monday_3);
        photos.add(R.drawable.week1_monday_4);
        photos.add(R.drawable.week1_monday_5);
        photos.add(R.drawable.week1_monday_6);
        photos.add(R.drawable.week1_monday_7);
        photos.add(R.drawable.week1_monday_8);
    }

}
