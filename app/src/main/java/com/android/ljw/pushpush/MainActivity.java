package com.android.ljw.pushpush;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    float stageWidth, stageHeight;

    int gridCount;
    float unit;
    Stage stage;

    FrameLayout container;
    Player player;
    GameMap gameMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGame();
        initStage();
        initPlayer();
        initButton();

        stage.initBoxCount(gameMap.map);
    }

    private long pressTIme;
    // 뒤로가기 누를때 실행되는 함수. 2초이내 더블클릭 시 종료.
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (pressTIme == 0) {
            Toast.makeText(this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            pressTIme = System.currentTimeMillis();
        } else {
            long seconds = System.currentTimeMillis() - pressTIme;

            if (seconds > 2000) {
                pressTIme = 0;
            } else
                finish();
        }
    }

    // 꺼질때 실행되는 함수. static을 초기화하기 위해 사용했다.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        GameMap.LEVEL = 1;
    }

    private void initGame() {
        gameMap = new GameMap();
        //화면 전체사이즈
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //게임판 사이즈
        gridCount = gameMap.map.length;
        stageWidth = metrics.widthPixels;
        //한칸 사이즈
        unit = stageWidth / gridCount;
    }

    private void initStage() {
        stage = new Stage(this);
        stage.levelView = findViewById(R.id.levelView);
        stage.levelView.setText("<Level " + GameMap.LEVEL + ">");
        container = findViewById(R.id.container);


        stage.setConfig(gridCount, unit);
        container.addView(stage);

        stage.setMap(gameMap.map);
    }

    private void initPlayer() {
        player = new Player();
        stage.addPlayer(player);
    }

    public void initButton() {
        findViewById(R.id.btnUp).setOnClickListener(buttonListener);
        findViewById(R.id.btnDown).setOnClickListener(buttonListener);
        findViewById(R.id.btnLeft).setOnClickListener(buttonListener);
        findViewById(R.id.btnRight).setOnClickListener(buttonListener);
        findViewById(R.id.btnRefresh).setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnUp:
                    stage.move(Stage.UP);
                    break;
                case R.id.btnDown:
                    stage.move(Stage.DOWN);
                    break;
                case R.id.btnLeft:
                    stage.move(Stage.LEFT);
                    break;
                case R.id.btnRight:
                    stage.move(Stage.RIGHT);
                    break;
                case R.id.btnRefresh:
                    stage.refresh();
                    stage.invalidate();
                    break;
            }
        }
    };


}
