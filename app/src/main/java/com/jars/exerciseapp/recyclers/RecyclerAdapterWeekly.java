package com.jars.exerciseapp.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jars.exerciseapp.R;

import java.util.ArrayList;

public class RecyclerAdapterWeekly extends RecyclerView.Adapter<RecyclerAdapterWeekly.MyHolder> {

    private String[] days = new String[]{"Monday", "Wednesday", "Friday"};

    @NonNull
    @Override
    public RecyclerAdapterWeekly.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_days, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterWeekly.MyHolder holder, int position) {
        holder.txtNameDay.setText(days[position]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView txtNameDay;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtNameDay = itemView.findViewById(R.id.txtDayCell);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_day);
                }
            });
        }
    }
}
