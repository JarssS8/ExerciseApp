package com.jars.exerciseapp.activities.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.beans.Circuit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayPreviousInfoFragment extends Fragment implements View.OnClickListener {

    private View root;
    private Button btStartRoutine;
    private static Circuit circuit;
    private TextView txtDayName, txtDescription;
    private ImageView next, back, image;
    private int actualPosition=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_day_previous_info, container, false);

        txtDayName = root.findViewById(R.id.txtDayName);
        txtDescription = root.findViewById(R.id.txtDescriptionPrevious);
        btStartRoutine = root.findViewById(R.id.btStartRoutine);
        next = root.findViewById(R.id.btNextPreview);
        back = root.findViewById(R.id.btBackPreview);
        image = root.findViewById(R.id.imageViewPreview);

        next.setOnClickListener(this);
        back.setOnClickListener(this);

        txtDayName.setText(circuit.getName());
        btStartRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_day_to_nav_routine);
                RoutineFragment.setCircuit(circuit);
            }
        });

        image.setImageResource(circuit.getImagesInt().get(actualPosition));
        setDescription();
        return root;
    }

    private void setDescription() {
        txtDescription.setText("OBLIGATORIOS:\n");
        for(int i=0;i<circuit.getRequiredMaterial().size();i++){
            txtDescription.setText(txtDescription.getText()+new String((i+1)+". "+circuit.getRequiredMaterial().get(i))+"\n");
        }
        txtDescription.setText(txtDescription.getText()+"\nOPCIONALES:\n");
        for(int i=0;i<circuit.getOptionalMaterial().size();i++){
            txtDescription.setText(txtDescription.getText()+new String((i+1)+". "+circuit.getOptionalMaterial().get(i))+"\n");
        }
    }

    public static void setCircuit(Circuit circuit) {
        DayPreviousInfoFragment.circuit = circuit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btNextPreview:
                actualPosition++;
                if (actualPosition == circuit.getImagesInt().size()) {
                    actualPosition=0;
                }
                image.setImageResource(circuit.getImagesInt().get(actualPosition));
                break;
            case R.id.btBackPreview:
                actualPosition--;
                if (actualPosition == -1) {
                    actualPosition=circuit.getImagesInt().size()-1;
                }
                image.setImageResource(circuit.getImagesInt().get(actualPosition));
                break;
        }
    }
}
