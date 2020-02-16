package com.jars.exerciseapp.activities.fragments.days;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jars.exerciseapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutineFragment extends Fragment implements View.OnClickListener {

    private View root, viewWait;
    private TextView txtRoundCount, txtCountDownWait, txtCircuitCount;
    private int roundCount = 1, actualPhoto = 0;
    private ImageView imageButtonStart, imageButtonNextPhoto;
    private static ArrayList<Integer> photos, auxPhotos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_routine, container, false);

        txtRoundCount = root.findViewById(R.id.txtCountTime);
        imageButtonStart = root.findViewById(R.id.imageButtonStart);
        imageButtonNextPhoto = root.findViewById(R.id.imageButtonExercise);
        viewWait = root.findViewById(R.id.viewWait);
        txtCountDownWait = root.findViewById(R.id.txtCountDownWait);
        txtCircuitCount = root.findViewById(R.id.txtCircuitCount);

        imageButtonNextPhoto.setVisibility(View.INVISIBLE);

        imageButtonNextPhoto.setOnClickListener(this);
        imageButtonStart.setOnClickListener(this);

        setImagesToAux();


        return root;
    }

    private void setImagesToAux() {
        auxPhotos = new ArrayList<>();
        if(roundCount%2==0){
            for(int i=0;i<photos.size();i++){
                if(i>=photos.size()/2){
                    auxPhotos.add(photos.get(i));
                }
            }
        }else{
            for(int i=0;i<photos.size();i++){
                if(i<photos.size()/2){
                    auxPhotos.add(photos.get(i));
                }
            }
        }
    }

    private void startRoundCount() {
        long totalTimeRound = 10 * 1000 + 1000; //7 minutos 7 * 60 * 1000 + 1
        CountDownTimer countDownTimer = new CountDownTimer(totalTimeRound, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int mins, seconds, totalTimeInSeconds;
                String secondsShow, minutesShow;
                totalTimeInSeconds = (int) millisUntilFinished / 1000;
                mins = (int) (totalTimeInSeconds / 60);
                seconds = totalTimeInSeconds % 60;
                minutesShow = String.format("%02d", mins);
                secondsShow = String.format("%02d", seconds);

                txtRoundCount.setText(minutesShow + " : " + secondsShow);
                if (seconds <= 5 && mins == 0 && seconds > 0) {
                    MediaPlayer.create(getContext(), R.raw.last5seconds).start();
                } else if (seconds == 0 && mins == 0) {
                    MediaPlayer.create(getContext(), R.raw.finalbell).start();
                }
            }

            @Override
            public void onFinish() {
                txtRoundCount.setText("start");
                roundCount++;
                imageButtonNextPhoto.setVisibility(View.INVISIBLE);
                txtCircuitCount.setText("Circuito : "+roundCount+"/4");
                actualPhoto = 0;
                setImagesToAux();
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonStart:
                if (txtRoundCount.getText().toString().equalsIgnoreCase("START")) {
                    imageButtonNextPhoto.setVisibility(View.VISIBLE);
                    imageButtonNextPhoto.setImageResource(auxPhotos.get(0));
                    startRoundCount();
                }
                break;
            case R.id.imageButtonExercise:
                    actualPhoto++;
                    if (actualPhoto == auxPhotos.size()) {
                        actualPhoto=0;
                        waitTime();
                    }
                    imageButtonNextPhoto.setImageResource(auxPhotos.get(actualPhoto));
                break;
        }
    }

    private void waitTime() {
        //Todo tiempo total < timepo descanso
        imageButtonNextPhoto.setClickable(false);
        viewWait.setVisibility(View.VISIBLE);
        txtCountDownWait.setVisibility(View.VISIBLE);
        long totalTimeRound = 3 * 1000 + 1000; //30 segundos
        CountDownTimer countDownTimer = new CountDownTimer(totalTimeRound, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int mins, seconds, totalTimeInSeconds;
                String secondsShow, minutesShow;
                totalTimeInSeconds = (int) millisUntilFinished / 1000;
                mins = (int) (totalTimeInSeconds / 60);
                seconds = totalTimeInSeconds % 60;
                minutesShow = String.format("%02d", mins);
                secondsShow = String.format("%02d", seconds);

                txtCountDownWait.setText(minutesShow + " : " + secondsShow);
                if (seconds <= 5 && mins == 0 && seconds > 0) {
                    MediaPlayer.create(getContext(), R.raw.last5seconds).start();
                } else if (seconds == 0 && mins == 0) {
                    MediaPlayer.create(getContext(), R.raw.finalbell).start();
                }
            }

            @Override
            public void onFinish() {
                txtCountDownWait.setVisibility(View.INVISIBLE);
                viewWait.setVisibility(View.INVISIBLE);
                imageButtonNextPhoto.setClickable(true);
            }
        }.start();
    }


    public static void setPhotos(ArrayList<Integer> photos) {
        RoutineFragment.photos = photos;
    }


}
