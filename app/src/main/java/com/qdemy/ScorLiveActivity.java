package com.qdemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScorLiveActivity extends AppCompatActivity {

    private ImageView inapoi;
    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scor_live);

        initializare();
    }

    private void initializare() {

        inapoi = findViewById(R.id.back_image_scorLive);
        timer = findViewById(R.id.timer_text_scorLive);

        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
