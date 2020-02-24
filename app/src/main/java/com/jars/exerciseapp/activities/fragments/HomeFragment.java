package com.jars.exerciseapp.activities.fragments;


import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.beans.Circuit;
import com.jars.exerciseapp.database.SQLiteManager;

import java.util.ArrayList;
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

        setHasOptionsMenu(true);
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
        ArrayAdapter<String> weeksAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_adapter_text, weeks) {
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
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_adapter_text, days) {
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
                ContentValues lastRoutine = manager.findLastCircuit();
                if (lastRoutine == null) {
                    spinnerWeeks.setSelection(1);
                    spinnerDays.setSelection(1);
                }
                else{
                    spinnerWeeks.setSelection((Integer)lastRoutine.get("week"));
                    spinnerDays.setSelection((Integer)lastRoutine.get("day"));
                }
                break;
            case R.id.btChargeRoutine:
                if(spinnerWeeks.getSelectedItemPosition()!=0 && spinnerDays.getSelectedItemPosition()!=0) {
                    Circuit circuit = dataSession();
                    Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_day);
                    DayPreviousInfoFragment.setCircuit(circuit);
                }else
                    Toast.makeText(getContext(), "Select one week and one day first", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private Circuit dataSession() {
        Circuit circuit = new Circuit();
        circuit.setName(spinnerWeeks.getSelectedItem().toString() + " : " + spinnerDays.getSelectedItem().toString());
        circuit.setWeekId(spinnerWeeks.getSelectedItemPosition());
        circuit.setDayId(spinnerDays.getSelectedItemPosition());
        circuit.setImagesInt(addImages());
        circuit.setRequiredMaterial(addRequiredMaterial());
        circuit.setOptionalMaterial(addOptionalMaterial());
        return circuit;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options, menu);
    }

    /**
     * Select items from my menu
     *
     * @param item the item that user is interacting with
     * @return true if the item was selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options:
                Navigation.findNavController(getView()).navigate(R.id.action_nav_home_to_nav_options);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> addRequiredMaterial() {
        ArrayList<String> required = new ArrayList<>();
        switch (spinnerWeeks.getSelectedItemPosition()) {
            case 1:
            case 2:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Comba");
                        required.add("Banco o cajón para subirse encima");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        break;
                    case 3:
                        required.add("Banco para hacer triceps");
                        required.add("Banco o cajón para subirse encima");
                        break;
                }
                break;
            case 3:
            case 4:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Banco o cajón para subirse encima");
                        required.add("Comba");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        break;
                    case 3:
                        required.add("Comba");
                        break;
                }
                break;
            case 5:
            case 7:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Banco o cajón para subirse encima");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        break;
                    case 3:
                        required.add("Banco o cajón para subirse encima");
                        break;
                }
                break;
            case 6:
            case 8:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Comba");
                        required.add("Banco o cajón para subirse encima");
                        required.add("Step o cajón bajo");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        break;
                    case 3:
                        required.add("Comba");
                        break;
                }
                break;
            case 9:
            case 11:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Comba");
                        required.add("Banco o cajón para subirse encima");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        break;
                    case 3:
                        required.add("Comba");
                        required.add("Banco para hacer triceps");
                        break;
                }
                break;
            case 10:
            case 12:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Banco para hacer triceps");
                        break;
                    case 2:
                        required.add("Comba");
                        required.add("Banco para hacer triceps");
                        break;
                    case 3:
                        required.add("Banco o cajón para subirse encima");
                        required.add("Step o cajón bajo");
                        break;
                }
                break;
            case 13:
            case 15:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Banco o cajón para subirse encima");
                        required.add("Step o cajón bajo");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        required.add("Bosu");
                        break;
                    case 3:
                        required.add("Banco para hacer abdominales y saltarlo por encima");
                        break;
                }
                break;
            case 14:
            case 16:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        required.add("Banco o cajón para subirse encima");
                        required.add("Step o cajón bajo");
                        break;
                    case 2:
                        required.add("Banco para hacer triceps");
                        required.add("Bosu");
                        break;
                    case 3:
                        required.add("Banco para hacer abdominales y saltarlo por encima");
                        required.add("Banco o cajón para subirse encima");
                        break;
                }
                break;
        }
        return required;
    }

    private ArrayList<String> addOptionalMaterial() {
        ArrayList<String> optional = new ArrayList<>();
        switch (spinnerWeeks.getSelectedItemPosition()) {
            case 1:
            case 2:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        break;
                }
                break;
            case 3:
            case 4:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        break;
                }
                break;
            case 5:
            case 7:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                }
                break;
            case 6:
            case 8:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        break;
                }
                break;
            case 9:
            case 11:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        optional.add("Pesa (3-6 Kg)");
                        optional.add("Pesa (10-15 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                }
                break;
            case 10:
            case 12:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                }
                break;
            case 13:
            case 15:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        optional.add("Pesa (3-6 Kg)");

                        break;
                    case 3:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                }
                break;
            case 14:
            case 16:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        optional.add("Esterilla");
                        optional.add("Balon medicinal (6-12 Kg)");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 2:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                    case 3:
                        optional.add("Esterilla");
                        optional.add("Pesa (3-6 Kg)");
                        break;
                }
                break;
        }
        return optional;
    }

    private ArrayList<Integer> addImages() {
        ArrayList<Integer> imagesIds = new ArrayList<>();
        switch (spinnerWeeks.getSelectedItemPosition()) {
            case 1:
            case 2:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.pre_week_1_monday_1);
                        imagesIds.add(R.drawable.pre_week_1_monday_2);
                        imagesIds.add(R.drawable.pre_week_1_monday_3);
                        imagesIds.add(R.drawable.pre_week_1_monday_4);
                        imagesIds.add(R.drawable.pre_week_1_monday_5);
                        imagesIds.add(R.drawable.pre_week_1_monday_6);
                        imagesIds.add(R.drawable.pre_week_1_monday_7);
                        imagesIds.add(R.drawable.pre_week_1_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.pre_week_1_wednesday_1);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_2);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_3);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_4);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_5);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_6);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_7);
                        imagesIds.add(R.drawable.pre_week_1_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.pre_week_1_friday_1);
                        imagesIds.add(R.drawable.pre_week_1_friday_2);
                        imagesIds.add(R.drawable.pre_week_1_friday_3);
                        imagesIds.add(R.drawable.pre_week_1_friday_4);
                        imagesIds.add(R.drawable.pre_week_1_friday_5);
                        imagesIds.add(R.drawable.pre_week_1_friday_6);
                        imagesIds.add(R.drawable.pre_week_1_friday_7);
                        imagesIds.add(R.drawable.pre_week_1_friday_8);
                        break;
                }
                break;
            case 3:
            case 4:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.pre_week_3_monday_1);
                        imagesIds.add(R.drawable.pre_week_3_monday_2);
                        imagesIds.add(R.drawable.pre_week_3_monday_3);
                        imagesIds.add(R.drawable.pre_week_3_monday_4);
                        imagesIds.add(R.drawable.pre_week_3_monday_5);
                        imagesIds.add(R.drawable.pre_week_3_monday_6);
                        imagesIds.add(R.drawable.pre_week_3_monday_7);
                        imagesIds.add(R.drawable.pre_week_3_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.pre_week_3_wednesday_1);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_2);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_3);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_4);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_5);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_6);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_7);
                        imagesIds.add(R.drawable.pre_week_3_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.pre_week_3_friday_1);
                        imagesIds.add(R.drawable.pre_week_3_friday_2);
                        imagesIds.add(R.drawable.pre_week_3_friday_3);
                        imagesIds.add(R.drawable.pre_week_3_friday_4);
                        imagesIds.add(R.drawable.pre_week_3_friday_5);
                        imagesIds.add(R.drawable.pre_week_3_friday_6);
                        imagesIds.add(R.drawable.pre_week_3_friday_7);
                        imagesIds.add(R.drawable.pre_week_3_friday_8);
                        break;
                }
                break;
            case 5:
            case 7:
                    switch (spinnerDays.getSelectedItemPosition()) {
                        case 1:
                            imagesIds.add(R.drawable.week_1_monday_1);
                            imagesIds.add(R.drawable.week_1_monday_2);
                            imagesIds.add(R.drawable.week_1_monday_3);
                            imagesIds.add(R.drawable.week_1_monday_4);
                            imagesIds.add(R.drawable.week_1_monday_5);
                            imagesIds.add(R.drawable.week_1_monday_6);
                            imagesIds.add(R.drawable.week_1_monday_7);
                            imagesIds.add(R.drawable.week_1_monday_8);
                            break;
                        case 2:
                            imagesIds.add(R.drawable.week_1_wedneday_1);
                            imagesIds.add(R.drawable.week_1_wedneday_2);
                            imagesIds.add(R.drawable.week_1_wedneday_3);
                            imagesIds.add(R.drawable.week_1_wedneday_4);
                            imagesIds.add(R.drawable.week_1_wedneday_5);
                            imagesIds.add(R.drawable.week_1_wedneday_6);
                            imagesIds.add(R.drawable.week_1_wedneday_7);
                            imagesIds.add(R.drawable.week_1_wedneday_8);
                            break;
                        case 3:
                            imagesIds.add(R.drawable.week_1_friday_1);
                            imagesIds.add(R.drawable.week_1_friday_2);
                            imagesIds.add(R.drawable.week_1_friday_3);
                            imagesIds.add(R.drawable.week_1_friday_4);
                            imagesIds.add(R.drawable.week_1_friday_5);
                            imagesIds.add(R.drawable.week_1_friday_6);
                            imagesIds.add(R.drawable.week_1_friday_7);
                            imagesIds.add(R.drawable.week_1_friday_8);
                            break;
                }
                break;
            case 6:
            case 8:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.week_2_monday_1);
                        imagesIds.add(R.drawable.week_2_monday_2);
                        imagesIds.add(R.drawable.week_2_monday_3);
                        imagesIds.add(R.drawable.week_2_monday_4);
                        imagesIds.add(R.drawable.week_2_monday_5);
                        imagesIds.add(R.drawable.week_2_monday_6);
                        imagesIds.add(R.drawable.week_2_monday_7);
                        imagesIds.add(R.drawable.week_2_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.week_2_wednesday_1);
                        imagesIds.add(R.drawable.week_2_wednesday_2);
                        imagesIds.add(R.drawable.week_2_wednesday_3);
                        imagesIds.add(R.drawable.week_2_wednesday_4);
                        imagesIds.add(R.drawable.week_2_wednesday_5);
                        imagesIds.add(R.drawable.week_2_wednesday_6);
                        imagesIds.add(R.drawable.week_2_wednesday_7);
                        imagesIds.add(R.drawable.week_2_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.week_2_friday_1);
                        imagesIds.add(R.drawable.week_2_friday_2);
                        imagesIds.add(R.drawable.week_2_friday_3);
                        imagesIds.add(R.drawable.week_2_friday_4);
                        imagesIds.add(R.drawable.week_2_friday_5);
                        imagesIds.add(R.drawable.week_2_friday_6);
                        imagesIds.add(R.drawable.week_2_friday_7);
                        imagesIds.add(R.drawable.week_2_friday_8);
                        break;
                }
                break;
            case 9:
            case 11:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.week_5_monday_1);
                        imagesIds.add(R.drawable.week_5_monday_2);
                        imagesIds.add(R.drawable.week_5_monday_3);
                        imagesIds.add(R.drawable.week_5_monday_4);
                        imagesIds.add(R.drawable.week_5_monday_5);
                        imagesIds.add(R.drawable.week_5_monday_6);
                        imagesIds.add(R.drawable.week_5_monday_7);
                        imagesIds.add(R.drawable.week_5_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.week_5_wednesday_1);
                        imagesIds.add(R.drawable.week_5_wednesday_2);
                        imagesIds.add(R.drawable.week_5_wednesday_3);
                        imagesIds.add(R.drawable.week_5_wednesday_4);
                        imagesIds.add(R.drawable.week_5_wednesday_5);
                        imagesIds.add(R.drawable.week_5_wednesday_6);
                        imagesIds.add(R.drawable.week_5_wednesday_7);
                        imagesIds.add(R.drawable.week_5_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.week_5_friday_1);
                        imagesIds.add(R.drawable.week_5_friday_2);
                        imagesIds.add(R.drawable.week_5_friday_3);
                        imagesIds.add(R.drawable.week_5_friday_4);
                        imagesIds.add(R.drawable.week_5_friday_5);
                        imagesIds.add(R.drawable.week_5_friday_6);
                        imagesIds.add(R.drawable.week_5_friday_7);
                        imagesIds.add(R.drawable.week_5_friday_8);
                        break;
                }
                break;
            case 10:
            case 12:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.week_6_monday_1);
                        imagesIds.add(R.drawable.week_6_monday_2);
                        imagesIds.add(R.drawable.week_6_monday_3);
                        imagesIds.add(R.drawable.week_6_monday_4);
                        imagesIds.add(R.drawable.week_6_monday_5);
                        imagesIds.add(R.drawable.week_6_monday_6);
                        imagesIds.add(R.drawable.week_6_monday_7);
                        imagesIds.add(R.drawable.week_6_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.week_6_wednesday_1);
                        imagesIds.add(R.drawable.week_6_wednesday_2);
                        imagesIds.add(R.drawable.week_6_wednesday_3);
                        imagesIds.add(R.drawable.week_6_wednesday_4);
                        imagesIds.add(R.drawable.week_6_wednesday_5);
                        imagesIds.add(R.drawable.week_6_wednesday_6);
                        imagesIds.add(R.drawable.week_6_wednesday_7);
                        imagesIds.add(R.drawable.week_6_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.week_6_friday_1);
                        imagesIds.add(R.drawable.week_6_friday_2);
                        imagesIds.add(R.drawable.week_6_friday_3);
                        imagesIds.add(R.drawable.week_6_friday_4);
                        imagesIds.add(R.drawable.week_6_friday_5);
                        imagesIds.add(R.drawable.week_6_friday_6);
                        imagesIds.add(R.drawable.week_6_friday_7);
                        imagesIds.add(R.drawable.week_6_friday_8);
                        break;
                }
                break;
            case 13:
            case 15:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.week_9_monday_1);
                        imagesIds.add(R.drawable.week_9_monday_2);
                        imagesIds.add(R.drawable.week_9_monday_3);
                        imagesIds.add(R.drawable.week_9_monday_4);
                        imagesIds.add(R.drawable.week_9_monday_5);
                        imagesIds.add(R.drawable.week_9_monday_6);
                        imagesIds.add(R.drawable.week_9_monday_7);
                        imagesIds.add(R.drawable.week_9_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.week_9_wednesday_1);
                        imagesIds.add(R.drawable.week_9_wednesday_2);
                        imagesIds.add(R.drawable.week_9_wednesday_3);
                        imagesIds.add(R.drawable.week_9_wednesday_4);
                        imagesIds.add(R.drawable.week_9_wednesday_5);
                        imagesIds.add(R.drawable.week_9_wednesday_6);
                        imagesIds.add(R.drawable.week_9_wednesday_7);
                        imagesIds.add(R.drawable.week_9_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.week_9_friday_1);
                        imagesIds.add(R.drawable.week_9_friday_2);
                        imagesIds.add(R.drawable.week_9_friday_3);
                        imagesIds.add(R.drawable.week_9_friday_4);
                        imagesIds.add(R.drawable.week_9_friday_5);
                        imagesIds.add(R.drawable.week_9_friday_6);
                        imagesIds.add(R.drawable.week_9_friday_7);
                        imagesIds.add(R.drawable.week_9_friday_8);
                        break;
                }
                break;
            case 14:
            case 16:
                switch (spinnerDays.getSelectedItemPosition()) {
                    case 1:
                        imagesIds.add(R.drawable.week_10_monday_1);
                        imagesIds.add(R.drawable.week_10_monday_2);
                        imagesIds.add(R.drawable.week_10_monday_3);
                        imagesIds.add(R.drawable.week_10_monday_4);
                        imagesIds.add(R.drawable.week_10_monday_5);
                        imagesIds.add(R.drawable.week_10_monday_6);
                        imagesIds.add(R.drawable.week_10_monday_7);
                        imagesIds.add(R.drawable.week_10_monday_8);
                        break;
                    case 2:
                        imagesIds.add(R.drawable.week_10_wednesday_1);
                        imagesIds.add(R.drawable.week_10_wednesday_2);
                        imagesIds.add(R.drawable.week_10_wednesday_3);
                        imagesIds.add(R.drawable.week_10_wednesday_4);
                        imagesIds.add(R.drawable.week_10_wednesday_5);
                        imagesIds.add(R.drawable.week_10_wednesday_6);
                        imagesIds.add(R.drawable.week_10_wednesday_7);
                        imagesIds.add(R.drawable.week_10_wednesday_8);
                        break;
                    case 3:
                        imagesIds.add(R.drawable.week_10_friday_1);
                        imagesIds.add(R.drawable.week_10_friday_2);
                        imagesIds.add(R.drawable.week_10_friday_3);
                        imagesIds.add(R.drawable.week_10_friday_4);
                        imagesIds.add(R.drawable.week_10_friday_5);
                        imagesIds.add(R.drawable.week_10_friday_6);
                        imagesIds.add(R.drawable.week_10_friday_7);
                        imagesIds.add(R.drawable.week_10_friday_8);
                        break;
                }
                break;
        }
        return imagesIds;
    }
}
