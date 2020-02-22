package com.jars.exerciseapp.activities.fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.database.SQLiteManager;
import com.jars.exerciseapp.recyclers.RecyclerAdapterWeekly;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private View root;
    private Spinner spinnerWeeks, spinnerDays;
    private Button btChargeRoutine, btSync;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        spinnerDays = root.findViewById(R.id.spinnerDays);
        spinnerWeeks = root.findViewById(R.id.spinnerWeeks);
        btChargeRoutine = root.findViewById(R.id.btChargeRoutine);
        btSync = root.findViewById(R.id.btSynchronize);
        btChargeRoutine.setOnClickListener(this);
        btSync.setOnClickListener(this);
        chargeWeeksAndDays();

        return root;
    }

    private void chargeWeeksAndDays() {
        //WEEKS
        List<String> weeks = Arrays.asList(getResources().getStringArray(R.array.weeks_arrays));
        ArrayAdapter<String> weeksAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, weeks) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        weeksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeeks.setAdapter(weeksAdapter);

        //DAYS
        List<String> days = Arrays.asList(getResources().getStringArray(R.array.days_array));
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, days) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(daysAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSynchronize:
                SQLiteManager manager = new SQLiteManager(getContext());
                String lastRoutine = manager.findLastCircuit();
                if(lastRoutine==null)
                    Toast.makeText(getContext(), "You don't finish any session yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btChargeRoutine:
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_day);
                break;

        }

    }
}
