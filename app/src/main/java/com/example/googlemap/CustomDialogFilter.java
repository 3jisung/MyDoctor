package com.example.googlemap;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CustomDialogFilter implements View.OnClickListener{
    private Context context;
    private CheckBox[] checkType = new CheckBox[17];
    MainActivity parent;

    public CustomDialogFilter(Context context) {
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
        dia.setContentView(R.layout.custom_filter);

        // 커스텀 다이얼로그를 노출한다.
        dia.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        TextView title = dia.findViewById(R.id.title); title.setOnClickListener(this);
        CheckBox typeA = dia.findViewById(R.id.typeA); checkType[0] = typeA; typeA.setOnClickListener(this);
        CheckBox typeB = dia.findViewById(R.id.typeB); checkType[1] = typeB; typeB.setOnClickListener(this);
        CheckBox typeC = dia.findViewById(R.id.typeC); checkType[2] = typeC; typeC.setOnClickListener(this);
        CheckBox typeD = dia.findViewById(R.id.typeD); checkType[3] = typeD; typeD.setOnClickListener(this);
        CheckBox typeE = dia.findViewById(R.id.typeE); checkType[4] = typeE; typeE.setOnClickListener(this);
        CheckBox typeG = dia.findViewById(R.id.typeG); checkType[5] = typeG; typeG.setOnClickListener(this);
        CheckBox typeH = dia.findViewById(R.id.typeH); checkType[6] = typeH; typeH.setOnClickListener(this);
        CheckBox typeI = dia.findViewById(R.id.typeI); checkType[7] = typeI; typeI.setOnClickListener(this);
        CheckBox typeM = dia.findViewById(R.id.typeM); checkType[8] = typeM; typeM.setOnClickListener(this);
        CheckBox typeN = dia.findViewById(R.id.typeN); checkType[9] = typeN; typeN.setOnClickListener(this);
        CheckBox typeR = dia.findViewById(R.id.typeR); checkType[10] = typeR; typeR.setOnClickListener(this);
        CheckBox typeT = dia.findViewById(R.id.typeT); checkType[11] = typeT; typeT.setOnClickListener(this);
        CheckBox typeU = dia.findViewById(R.id.typeU); checkType[12] = typeU; typeU.setOnClickListener(this);
        CheckBox typeV = dia.findViewById(R.id.typeV); checkType[13] = typeV; typeV.setOnClickListener(this);
        CheckBox typeW = dia.findViewById(R.id.typeW); checkType[14] = typeW; typeW.setOnClickListener(this);
        CheckBox typeY = dia.findViewById(R.id.typeY); checkType[15] = typeY; typeY.setOnClickListener(this);
        CheckBox typeZ = dia.findViewById(R.id.typeZ); checkType[16] = typeZ; typeZ.setOnClickListener(this);

        for(int i = 0; i < main.type.length; i++)
        {
            if(main.type[i] == true)
                checkType[i].setChecked(true);
            else
                checkType[i].setChecked(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.typeA:
                parent.updateFilter(0, checkType[0].isChecked());
                break;
            case R.id.typeB:
                parent.updateFilter(1, checkType[1].isChecked());
                break;
            case R.id.typeC:
                parent.updateFilter(2, checkType[2].isChecked());
                break;
            case R.id.typeD:
                parent.updateFilter(3, checkType[3].isChecked());
                break;
            case R.id.typeE:
                parent.updateFilter(4, checkType[4].isChecked());
                break;
            case R.id.typeG:
                parent.updateFilter(5, checkType[5].isChecked());
                break;
            case R.id.typeH:
                parent.updateFilter(6, checkType[6].isChecked());
                break;
            case R.id.typeI:
                parent.updateFilter(7, checkType[7].isChecked());
                break;
            case R.id.typeM:
                parent.updateFilter(8, checkType[8].isChecked());
                break;
            case R.id.typeN:
                parent.updateFilter(9, checkType[9].isChecked());
                break;
            case R.id.typeR:
                parent.updateFilter(10, checkType[10].isChecked());
                break;
            case R.id.typeT:
                parent.updateFilter(11, checkType[11].isChecked());
                break;
            case R.id.typeU:
                parent.updateFilter(12, checkType[12].isChecked());
                break;
            case R.id.typeV:
                parent.updateFilter(13, checkType[13].isChecked());
                break;
            case R.id.typeW:
                parent.updateFilter(14, checkType[14].isChecked());
                break;
            case R.id.typeY:
                parent.updateFilter(15, checkType[15].isChecked());
                break;
            case R.id.typeZ:
                parent.updateFilter(16, checkType[16].isChecked());
                break;
        }
    }
}
