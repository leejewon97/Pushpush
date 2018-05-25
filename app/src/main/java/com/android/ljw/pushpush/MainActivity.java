package com.android.ljw.pushpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

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
    }

    private void initPlayer() {
        player = new Player();
        stage.addPlayer(player);
    }

    private void initStage() {
        container = findViewById(R.id.container);
        stage = new Stage(this);
        stage.setConfig(gridCount, unit);
        container.addView(stage);

        stage.setCurrentMap(gameMap.map);
    }

    private void initGame() {
        gameMap = new GameMap();
        //화면 전체사이즈
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //게임판 사이즈
        gridCount = gameMap.map.length;
        stageWidth = metrics.widthPixels;
        stageHeight = stageWidth;
        //한칸 사이즈
        unit = stageWidth / gridCount;
    }

    public void initButton() {
        findViewById(R.id.btnUp).setOnClickListener(buttonListener);
        findViewById(R.id.btnDown).setOnClickListener(buttonListener);
        findViewById(R.id.btnLeft).setOnClickListener(buttonListener);
        findViewById(R.id.btnRight).setOnClickListener(buttonListener);
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
            }
        }
    };


}
