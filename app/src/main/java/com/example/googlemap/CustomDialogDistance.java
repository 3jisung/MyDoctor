package com.example.googlemap;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-08-07.
 */

public class CustomDialogDistance {

    private Context context;
    private SeekBar seekbar;
    private TextView newDistance;
    int number;
    MainActivity parent;

    public CustomDialogDistance(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(MainActivity main) {
        parent = main;

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dia = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dia.setContentView(R.layout.custom_distance);

        // 커스텀 다이얼로그를 노출한다.
        dia.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        seekbar = dia.findViewById(R.id.seekbar);
        newDistance = dia.findViewById(R.id.newDistance);

        seekbar.setMax(49);
        seekbar.setProgress(main.distanceOption - 1);
        newDistance.setText((main.distanceOption) + "km");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Seekbar의 움직임이 멈춘다면 실행될 사항
                number = seekbar.getProgress() + 1;
                newDistance.setText(Integer.toString(number) + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Seekbar의 움직임이 시작될 때 실행될 사항
                number = seekbar.getProgress() + 1;
                newDistance.setText(Integer.toString(number) + "km");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Seekbar의 상태(정도)가 변경되었을 때 실행될 사항
                number = seekbar.getProgress() + 1;
                newDistance.setText(Integer.toString(number) + "km");
                parent.updateDistance(number);
            }
        });
    }
}