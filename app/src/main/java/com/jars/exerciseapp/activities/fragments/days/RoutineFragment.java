package com.jars.exerciseapp.activities.fragments.days;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.jars.exerciseapp.beans.Circuit;
import com.jars.exerciseapp.database.SQLiteManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutineFragment extends Fragment implements View.OnClickListener {

    //Todo añadir configuracion para poder cambiar el tiempo de espera

    private View root, viewWait;
    private TextView txtRoundCount, txtCountDownWait, txtCountDownWaitNormal, txtCircuitCount;
    private int actualCircuit = 1, actualPhoto = 0, timeBetweenCircuit;
    private ImageView imageButtonStart, imageButtonNextPhoto;
    private ArrayList<Integer> photos, auxPhotos;
    private static Circuit circuit;
    private CountDownTimer countDownTimerCircuit, countDownTimerBreak;
    private static String currentWeekDay;
    private boolean sound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_routine, container, false);

        txtRoundCount = root.findViewById(R.id.txtCountTime);
        imageButtonStart = root.findViewById(R.id.imageButtonStart);
        imageButtonNextPhoto = root.findViewById(R.id.imageButtonExercise);
        viewWait = root.findViewById(R.id.viewWait);
        txtCountDownWait = root.findViewById(R.id.txtCountDownWait);
        txtCountDownWaitNormal = root.findViewById(R.id.txtCountDownWaitNormal);
        txtCircuitCount = root.findViewById(R.id.txtCircuitCount);
        photos = circuit.getImagesInt();
        currentWeekDay = circuit.getWeekId() + circuit.getDayId() + "";

        imageButtonNextPhoto.setVisibility(View.INVISIBLE);

        imageButtonNextPhoto.setOnClickListener(this);
        imageButtonStart.setOnClickListener(this);

        setImagesToAux();
        getPreferences();


        return root;
    }

    private void getPreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        sound = preferences.getBoolean("sound", true);
        int progressSeekBar = preferences.getInt("progress", 1);
        switch (progressSeekBar) {
            case 0:
                timeBetweenCircuit = 30;
                break;
            case 1:
                timeBetweenCircuit = 60;
                break;
            case 2:
                timeBetweenCircuit = 90;
                break;
            case 3:
                timeBetweenCircuit = 120;
                break;
        }

    }

    private void setImagesToAux() {
        auxPhotos = new ArrayList<>();
        if (actualCircuit % 2 == 0) {
            for (int i = 0; i < photos.size(); i++) {
                if (i >= photos.size() / 2) {
                    auxPhotos.add(photos.get(i));
                }
            }
        } else {
            for (int i = 0; i < photos.size(); i++) {
                if (i < photos.size() / 2) {
                    auxPhotos.add(photos.get(i));
                }
            }
        }
    }

    private void startRoundCount() {
        long totalTimeRound = 7* 60 * 1000 + 1000; //7 minutos 7 * 60 * 1000 + 1
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
                if(sound) {
                    if (seconds <= 5 && mins == 0 && seconds > 0) {
                        MediaPlayer.create(getContext(), R.raw.last5seconds).start();
                    } else if (seconds == 0 && mins == 0) {
                        MediaPlayer.create(getContext(), R.raw.finalbell).start();
                    }
                }
            }

            @Override
            public void onFinish() {
                if (actualCircuit < 4) {
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
                    MediaPlayer.create(getContext(), R.raw.finishedday).start();
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
                } else if (txtRoundCount.getText().toString().equalsIgnoreCase("FINISH")) {
                    if (currentWeekDay != null) {
                        SQLiteManager manager = new SQLiteManager(getContext());
                        manager.insertNewSave(circuit.getWeekId(), circuit.getDayId());
                        manager.close();
                    }
                    Navigation.findNavController(v).navigate(R.id.action_nav_routine_to_nav_home);
                }
                break;
            case R.id.imageButtonExercise:
                if (circuit.getWeekId() == 1 && circuit.getDayId() == 2 || circuit.getWeekId() == 2 && circuit.getDayId() == 2) {
                    if (actualPhoto == 2) {
                        waitTime("NORMAL30");
                    }
                } else if (circuit.getWeekId() == 10 && circuit.getDayId() == 1 || circuit.getWeekId() == 12 && circuit.getDayId() == 1) {
                    if (actualPhoto == 0) {
                        waitTime("NORMAL60");
                    }
                }
                actualPhoto++;
                if (actualPhoto == auxPhotos.size()) {
                    actualPhoto = 0;
                }

                imageButtonNextPhoto.setImageResource(auxPhotos.get(actualPhoto));
                break;
        }
    }

    private void waitTime(String breakType) {
        switch (breakType) {
            case "CIRCUIT":
                txtCountDownWaitNormal.setVisibility(View.INVISIBLE);
                imageButtonNextPhoto.setClickable(false);
                viewWait.setVisibility(View.VISIBLE);
                txtCountDownWait.setVisibility(View.VISIBLE);
                long totalTimeRound = timeBetweenCircuit * 1000 + 1000; //60 segundos
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
                        if (sound) {
                            if (seconds <= 5 && mins == 0 && seconds > 0) {
                                MediaPlayer.create(getContext(), R.raw.last5seconds).start();
                            } else if (seconds == 0 && mins == 0) {
                                MediaPlayer.create(getContext(), R.raw.finalbell).start();
                            }
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
            case "NORMAL30":
                imageButtonNextPhoto.setClickable(false);
                txtCountDownWaitNormal.setVisibility(View.VISIBLE);
                totalTimeRound = 35 * 1000 + 1000; //35 segundos
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

                        txtCountDownWaitNormal.setText(minutesShow + " : " + secondsShow);
                        if (sound) {
                            if (seconds <= 5 && mins == 0 && seconds > 0) {
                                MediaPlayer.create(getContext(), R.raw.last5seconds).start();
                            } else if (seconds == 0 && mins == 0) {
                                MediaPlayer.create(getContext(), R.raw.finalbell).start();
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        txtCountDownWaitNormal.setVisibility(View.INVISIBLE);
                        imageButtonNextPhoto.setClickable(true);
                    }
                }.start();
                break;
            case "NORMAL60":
                imageButtonNextPhoto.setClickable(false);
                txtCountDownWaitNormal.setVisibility(View.VISIBLE);
                totalTimeRound = 65 * 1000 + 1000; //60 segundos
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

                        txtCountDownWaitNormal.setText(minutesShow + " : " + secondsShow);
                        if (sound) {
                            if (seconds <= 5 && mins == 0 && seconds > 0) {
                                MediaPlayer.create(getContext(), R.raw.last5seconds).start();
                            } else if (seconds == 0 && mins == 0) {
                                MediaPlayer.create(getContext(), R.raw.finalbell).start();
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        txtCountDownWaitNormal.setVisibility(View.INVISIBLE);
                        imageButtonNextPhoto.setClickable(true);
                    }
                }.start();
                break;
        }

    }

    public static void setCurrentWeekDay(String currentWeekDay) {
        RoutineFragment.currentWeekDay = currentWeekDay;
    }

    public static void setCircuit(Circuit circuit) {
        RoutineFragment.circuit = circuit;
    }


}
