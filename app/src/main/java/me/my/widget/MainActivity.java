package me.my.widget;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CircleProgressBar progressBar1;
    CircleProgressBar progressBar2;
    CircleProgressBar progressBar3;
    CircleProgressBar progressBar4;
    CircleProgressBar progressBar5;
    CircleProgressBar progressBar6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar1 = findViewById(R.id.progress1);
        progressBar2 = findViewById(R.id.progress2);
        progressBar3 = findViewById(R.id.progress3);
        progressBar4 = findViewById(R.id.progress4);
        progressBar5 = findViewById(R.id.progress5);
        progressBar6 = findViewById(R.id.progress6);

        progressBar1.setRingColor(new int[]{Color.YELLOW, Color.RED});
        progressBar1.setProgress(100);

        progressBar2.setRingColor(new int[]{Color.YELLOW, Color.RED});
        progressBar2.setProgress(66);

        progressBar3.setRingColor(Color.CYAN);
        progressBar3.setProgress(77);

        progressBar4.setRingColor(new int[]{Color.YELLOW, Color.RED});
        progressBar4.setGradientType(CircleProgressBar.Gradient.SWEEP);
        progressBar4.setProgress(88);

        progressBar5.setRingColor(new int[]{Color.YELLOW, Color.RED});
        progressBar5.setGradientType(CircleProgressBar.Gradient.SWEEP);
        progressBar5.setPercentStyle("分");
        progressBar5.setProgress(88);

        progressBar6.setRingColor(new int[]{Color.YELLOW, Color.RED});
        progressBar6.setGradientType(CircleProgressBar.Gradient.SWEEP);
        progressBar6.setTextColor(Color.GREEN);
        progressBar6.setTextPercentColor(Color.RED);
        progressBar6.setProgress(88);
    }

}
