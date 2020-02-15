package com.jars.exerciseapp.activities.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.recyclers.RecyclerAdapterWeekly;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private RecyclerAdapterWeekly mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        setRecyclerData();

        return root;
    }

    private void setRecyclerData() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = root.findViewById(R.id.recyclerDays);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapterWeekly();
        recyclerView.setAdapter(mAdapter);
    }

}
