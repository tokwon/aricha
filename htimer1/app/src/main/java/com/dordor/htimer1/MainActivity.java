package com.dordor.htimer1;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int MILLISINFUTURE = 11*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    static int count = 0;
    private int dcount = 360;
    private int dstep = 0;
    private TextView countTxt ;
    private CountDownTimer countDownTimer;

    private int btn4count = 360;

    Button btn1,btn2,btn3,btn4;
    // LinearLayout backimg;
    TextView count_txt;
    DonutView donutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//회전금지.
        Toast.makeText(getApplicationContext(), "타이머를 오래누르면 취소됩니다.", Toast.LENGTH_SHORT).show();
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

//        backimg = (LinearLayout) findViewById(R.id.backimg);
//        backimg.setBackgroundResource(R.drawable.dream31);
//btn5 = (Button) findViewById(R.id.btn5);

        donutView=(DonutView)findViewById(R.id.donut);

/*-
        String[] dbcolum = new String[]{};
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select _id, idnum, ssec from tb_data order by _id desc ",null);
        //limit 5

        tdata = new ArrayList<>();
        arrayList = new ArrayList<>();
        if (cursor!=null) {

            while (cursor.moveToNext()) {
                arrayList.add(cursor.getInt(0));
                tdata.add(cursor.getString(1));
                Log.d("db load --- ", "---");
            }
            db.close();
        } else { //초기값 입력
            db.execSQL("insert into tb_data (idnum, ssec) values (1, 300)");
            db.close();
            Log.d("db inserted --- ", "---");
        }

-*/
        donutView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    donutView.setValue(0);
                    countDownTimer.cancel();
                    donutView.setValue(0);
                    btn1.setClickable(true);
                    btn2.setClickable(true);
                    btn3.setClickable(true);
                    btn4.setClickable(true);
                    Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_SHORT).show();
                    return false;
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "not Started", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   countDownTimer.onFinish();
                count= 30;
                MILLISINFUTURE =30 * 1000;
                dstep = 360 / count;
                dcount = 360;

                countDownTimer();
                countDownTimer.start();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count= 60;
                MILLISINFUTURE = 60 * 1000;
                dstep = 360 / count;
                dcount = 360;
                countDownTimer();

                countDownTimer.start();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count= 90;
                MILLISINFUTURE = 90 * 1000;
                dstep = 360 / count;
                dcount = 360;
                countDownTimer();

                countDownTimer.start();

            }
        });


        btn4.setText(String.valueOf(btn4count));
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count= btn4count;
                MILLISINFUTURE = 120 * 1000;
                dstep = 360 / count;
                dcount = 360;
                countDownTimer();
                countDownTimer.start();

            }
        });


/*
        btn4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText edittext = new EditText(MainActivity.this);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                alertDialog.setTitle("휴식시간을 초단위로 입력하세요")
                        .setView(edittext)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              btn4count = Integer.parseInt(edittext.getText().toString());
                                btn4.setText(String.valueOf(btn4count));
                            }
                        });
                        alertDialog.show();

                       return true;
            } });
*/
    }


    public void countDownTimer(){

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                //countTxt.setText(String.valueOf(count));
                count --;
                dcount = dcount - dstep;
                donutView.setValue(dcount);
                btn1.setClickable(false);
                btn2.setClickable(false);
                btn3.setClickable(false);
                btn4.setClickable(false);
            }
            public void onFinish() {
                count =0;
                donutView.setValue(0);
                //countTxt.setText(String.valueOf("Finish ."));
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),notification);
                ringtone.play();
                btn1.setClickable(true);
                btn2.setClickable(true);
                btn3.setClickable(true);
                btn4.setClickable(true);

            }
        };
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
            donutView.setValue(360);
        } catch (Exception e) {}
        countDownTimer=null;
    }



}


