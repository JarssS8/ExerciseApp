package com.jars.exerciseapp.activities.fragments.days;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jars.exerciseapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView txtSecondsSeekBar;
    private Switch switchSeconds;
    private SeekBar seekBar;
    private View root;
    private Button btSavePreferences;

    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_options, container, false);

        txtSecondsSeekBar = root.findViewById(R.id.txtSecondsSeekBar);
        switchSeconds = root.findViewById(R.id.switchSound);
        seekBar = root.findViewById(R.id.seekBarSeconds);
        btSavePreferences=root.findViewById(R.id.btSavePreferences);

        setPreferences();
        setTextSeekBar();

        btSavePreferences.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        return root;
    }

    private void setPreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        boolean sound = preferences.getBoolean("sound",true);
        int progressSeekBar = preferences.getInt("progress",1);

        switchSeconds.setChecked(sound);
        seekBar.setProgress(progressSeekBar);
    }
    private void setTextSeekBar() {
        int position = seekBar.getProgress();
        switch (position){
            case 0:
                txtSecondsSeekBar.setText("30 seconds");
                break;
            case 1:
                txtSecondsSeekBar.setText("60 seconds");
                break;
            case 2:
                txtSecondsSeekBar.setText("90 seconds");
                break;
            case 3:
                txtSecondsSeekBar.setText("120 seconds");
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setTextSeekBar();
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sound",switchSeconds.isChecked());
        editor.putInt("progress",seekBar.getProgress());
        editor.apply();
        Toast.makeText(getContext(), "Preferences saved", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(v).navigate(R.id.action_nav_options_to_nav_home);
    }
}
