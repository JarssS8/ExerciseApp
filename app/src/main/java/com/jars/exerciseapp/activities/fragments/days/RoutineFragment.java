package com.jars.exerciseapp.activities.fragments.days;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jars.exerciseapp.R;
import com.jars.exerciseapp.database.SQLiteManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutineFragment extends Fragment implements View.OnClickListener {

    //Todo añadir configuracion para poder cambiar el tiempo de espera

    private View root, viewWait;
    private TextView txtRoundCount, txtCountDownWait, txtCircuitCount;
    private int actualCircuit = 1, actualPhoto = 0;
    private ImageView imageButtonStart, imageButtonNextPhoto;
    private static ArrayList<Integer> photos, auxPhotos;
    private CountDownTimer countDownTimerCircuit, countDownTimerBreak;
    private static String currentWeekDay;


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
        if(actualCircuit %2==0){
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
        long totalTimeRound = 7 * 60 * 1000 + 1000; //7 minutos 7 * 60 * 1000 + 1
        countDownTimerCircuit = new CountDownTimer(totalTimeRound, 1000) {
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
                if(actualCircuit<4) {
                    txtRoundCount.setText("BREAK");
                    if (countDownTimerBreak != null)
                        countDownTimerBreak.cancel();
                    waitTime("CIRCUIT");
                    actualCircuit++;
                    imageButtonNextPhoto.setVisibility(View.INVISIBLE);
                    txtCircuitCount.setText("Circuito : " + actualCircuit + "/4");
                    actualPhoto = 0;
                    setImagesToAux();
                } else {
                    if (countDownTimerBreak != null)
                        countDownTimerBreak.cancel();
                    txtRoundCount.setText("FINISH");
                    MediaPlayer.create(getContext(),R.raw.finishedday).start();
                }
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
                } else if (txtRoundCount.getText().toString().equalsIgnoreCase("FINISH")){
                    if(currentWeekDay!=null) {
                        SQLiteManager manager = new SQLiteManager(getContext());
                        manager.insertNewSave(Integer.parseInt(currentWeekDay.substring(0, 1)), Integer.parseInt(currentWeekDay.substring(1)));
                        manager.close();
                    }
                    Navigation.findNavController(v).navigate(R.id.action_nav_routine_to_nav_home);
                }
                break;
            case R.id.imageButtonExercise:
                    actualPhoto++;
                    if (actualPhoto == auxPhotos.size()) {
                        actualPhoto=0;
                        //Todo waitTime("NORMAL");
                    }
                    imageButtonNextPhoto.setImageResource(auxPhotos.get(actualPhoto));
                break;
        }
    }

    private void waitTime(String breakType) {
        switch (breakType){
            case "CIRCUIT":
                imageButtonNextPhoto.setClickable(false);
                viewWait.setVisibility(View.VISIBLE);
                txtCountDownWait.setVisibility(View.VISIBLE);
                long totalTimeRound = 60 * 1000 + 1000; //60 segundos
                countDownTimerBreak = new CountDownTimer(totalTimeRound, 1000) {
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
                        imageButtonNextPhoto.setVisibility(View.VISIBLE);
                        imageButtonNextPhoto.setImageResource(auxPhotos.get(actualPhoto));
                        startRoundCount();
                    }
                }.start();
                break;
            case "NORMAL":
                //Todo tiempo total < timepo descanso
                imageButtonNextPhoto.setClickable(false);
                viewWait.setVisibility(View.VISIBLE);
                txtCountDownWait.setVisibility(View.VISIBLE);
                totalTimeRound = 3 * 1000 + 1000; //30 segundos
                countDownTimerBreak = new CountDownTimer(totalTimeRound, 1000) {
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
                break;
        }

    }

    public static void setCurrentWeekDay(String currentWeekDay) {
        RoutineFragment.currentWeekDay = currentWeekDay;
    }

    public static void setPhotos(ArrayList<Integer> photos) {
        RoutineFragment.photos = photos;
    }


}
