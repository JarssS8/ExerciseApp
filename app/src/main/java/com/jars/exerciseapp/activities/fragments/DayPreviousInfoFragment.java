package com.jars.exerciseapp.activities.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.beans.Circuit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayPreviousInfoFragment extends Fragment {

    private View root;
    private Button btStartRoutine;
    private static Circuit circuit;
    private TextView txtDayName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_day_previous_info, container, false);

        txtDayName = root.findViewById(R.id.txtDayName);
        btStartRoutine = root.findViewById(R.id.btStartRoutine);

        txtDayName.setText(circuit.getName());
        btStartRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_day_to_nav_routine);
                RoutineFragment.setCircuit(circuit);
            }
        });
        return root;
    }

    public static void setCircuit(Circuit circuit) {
        DayPreviousInfoFragment.circuit = circuit;
    }
}
