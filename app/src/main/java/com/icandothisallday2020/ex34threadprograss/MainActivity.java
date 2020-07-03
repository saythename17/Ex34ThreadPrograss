package com.icandothisallday2020.ex34threadprograss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    int gauge=0;//막대바 프로그레스의 진행률 값
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        //wheel type progress dialog
        dialog=new ProgressDialog(this);
        dialog.setTitle("Wheel Dialog");
        dialog.setMessage("Downloading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        //outside 를 touch 해도 Dialog 가 꺼지지 않도록
        dialog.setCancelable(false);
        //뒤로가기를 눌러도 꺼지지 않도록
        dialog.show();
        //3초뒤 Dialog 종료 시키기
        //Handler (사장조카 낙하산 바보) 이용-사장(Main)은 잠들면 X
        handler.sendEmptyMessageDelayed(0,3000);
    }
    Handler handler= new Handler(){
        @Override //3000ms 후에 Handler 의 handleMassage()발동
        public void handleMessage(@NonNull Message msg) {
            dialog.dismiss();
        }
    };

    public void click2(View view) {
        //bar style progress dialog
        dialog=new ProgressDialog(this);
        dialog.setTitle("Bar Dialog");
        dialog.setMessage("Downloading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //dialog.setMax(10); 0/10
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setProgress(gauge); //게이지바 차오름 설정
        //별도 스레드가 게이지를 증가 시키도록
        new Thread(){
            @Override
            public void run() {
                while(gauge<100){
                    gauge++;
                    dialog.setProgress(gauge);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }//while...
                dialog.dismiss();                gauge=0;
            }
        }.start();


    }
}
