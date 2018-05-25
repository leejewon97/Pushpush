package com.android.ljw.pushpush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Stage extends View {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    int currentMap[][];
    Paint gridPaint = new Paint();
    Paint boxPaint = new Paint();
    Paint goalPaint = new Paint();
    Paint rockPaint = new Paint();
    Paint completePaint = new Paint();

    int gridCount;
    float unit;
    Player player;

    public Stage(Context context) {
        super(context);
        gridPaint.setColor(Color.GRAY); // 사각형의 색
        gridPaint.setStyle(Paint.Style.STROKE); // 사각형의 스타일
        gridPaint.setStrokeWidth(1); // 선 두께

        goalPaint.setColor(Color.CYAN);
        goalPaint.setStyle(Paint.Style.STROKE);
        goalPaint.setStrokeWidth(10);

        boxPaint.setColor(Color.CYAN);

        rockPaint.setColor(Color.BLACK);

        completePaint.setColor(Color.GRAY);

    }

    public void setConfig(int gridCount, float unit) {
        this.gridCount = gridCount;
        this.unit = unit;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMap(canvas);
        drawPlayer(canvas);
    }

    public void addPlayer(Player player) {
        this.player = player;
    }

    private void drawPlayer(Canvas canvas) {
        if (player != null)
            canvas.drawCircle(
                    player.x * unit + unit / 2,  // x좌표
                    player.y * unit + unit / 2,  // y좌표
                    unit / 2,           // 크기
                    player.paint);
    }

    Paint tempPaint;

    private void drawMap(Canvas canvas) {
        for (int y = 0; y < currentMap.length; y++) { // 2차원배열의 length는 행의 개수다.
            for (int x = 0; x < currentMap[0].length; x++) {
                if (currentMap[y][x] == 0) {
                    tempPaint = gridPaint;
                } else if (currentMap[y][x] == 1) {
                    tempPaint = boxPaint;
                } else if (currentMap[y][x] == 2) {
                    tempPaint = rockPaint;
                } else if (currentMap[y][x] == 9) {
                    tempPaint = goalPaint;
                } else if (currentMap[y][x] == 10) {
                    tempPaint = completePaint;
                }
                canvas.drawRect(
                        x * unit,
                        y * unit,
                        x * unit + unit,
                        y * unit + unit,
                        tempPaint);
            }
        }

    }

    public void move(int direction) {
        switch (direction) {
            case UP:
                if (notCollisionCheck(UP))
                    player.up();
                break;
            case DOWN:
                if (notCollisionCheck(DOWN))
                    player.down();
                break;
            case LEFT:
                if (notCollisionCheck(LEFT))
                    player.left();
                break;
            case RIGHT:
                if (notCollisionCheck(RIGHT))
                    player.right();
                break;
        }
        invalidate();
//        completionProcess();
    }

    public boolean notCollisionCheck(int direction) {
        switch (direction) {
            case UP:
                // 맵을 벗어나면 이동X
                if ((player.y - 1) < 0)
                    return false;
                // 다음 진행할 곳의 장애물 검사
                // 박스를 밀려고 하면(골에 넣었든, 안 넣었든)
                if (currentMap[player.y - 1][player.x] == 1 || currentMap[player.y - 1][player.x] == 10) {
                    // 물건이 붙어있으면 이동X
                    if (player.y - 2 < 0
                            || currentMap[player.y - 2][player.x] == 1 || currentMap[player.y - 2][player.x] == 2 || currentMap[player.y - 2][player.x] == 10) {
                        return false;
                    }
                    // 다음이 골일때
                    if (currentMap[player.y - 2][player.x] == 9) {
                        // 벌써 넣어 놓은 박스라면
                        if (currentMap[player.y - 1][player.x] == 10) {
                            currentMap[player.y - 1][player.x] = 9;
                        } else {
                            currentMap[player.y - 1][player.x] = 0;
                        }
                        currentMap[player.y - 2][player.x] = 10;
                    } // 다음이 골이 아니고 벌써 넣어 놓은 박스라면
                    else if (currentMap[player.y - 1][player.x] == 10) {
                        currentMap[player.y - 1][player.x] = 9;
                        currentMap[player.y - 2][player.x] = 1;
                    } // 다 아니고 그냥 일반적인 경우면
                    else {
                        currentMap[player.y - 1][player.x] = 0;
                        currentMap[player.y - 2][player.x] = 1;
                    }
                } // 장애물을 밀려고 하면
                else if (currentMap[player.y - 1][player.x] == 2)
                    return false;
                break;
            case DOWN:
                if ((player.y + 1) >= gridCount)
                    return false;
                // 다음 진행할 곳의 장애물 검사
                if (currentMap[player.y + 1][player.x] == 1 || currentMap[player.y + 1][player.x] == 10) {
                    if (player.y + 2 >= gridCount
                            || currentMap[player.y + 2][player.x] == 1 || currentMap[player.y + 2][player.x] == 2 || currentMap[player.y + 2][player.x] == 10) {
                        return false;
                    }
                    if (currentMap[player.y + 2][player.x] == 9) {
                        if (currentMap[player.y + 1][player.x] == 10) {
                            currentMap[player.y + 1][player.x] = 9;
                        } else {
                            currentMap[player.y + 1][player.x] = 0;
                        }
                        currentMap[player.y + 2][player.x] = 10;
                    } else if (currentMap[player.y + 1][player.x] == 10) {
                        currentMap[player.y + 1][player.x] = 9;
                        currentMap[player.y + 2][player.x] = 1;
                    } else {
                        currentMap[player.y + 1][player.x] = 0;
                        currentMap[player.y + 2][player.x] = 1;
                    }
                } else if (currentMap[player.y + 1][player.x] == 2)
                    return false;
                break;
            case LEFT:
                if ((player.x - 1) < 0)
                    return false;
                // 다음 진행할 곳의 장애물 검사
                if (currentMap[player.y][player.x - 1] == 1 || currentMap[player.y][player.x - 1] == 10) {
                    if (player.x - 2 < 0
                            || currentMap[player.y][player.x - 2] == 1 || currentMap[player.y][player.x - 2] == 2 || currentMap[player.y][player.x - 2] == 10) {
                        return false;
                    }
                    if (currentMap[player.y][player.x - 2] == 9) {
                        if (currentMap[player.y][player.x - 1] == 10) {
                            currentMap[player.y][player.x - 1] = 9;
                        } else {
                            currentMap[player.y][player.x - 1] = 0;
                        }
                        currentMap[player.y][player.x - 2] = 10;
                    } else if (currentMap[player.y][player.x - 1] == 10) {
                        currentMap[player.y][player.x - 1] = 9;
                        currentMap[player.y][player.x - 2] = 1;
                    } else {
                        currentMap[player.y][player.x - 1] = 0;
                        currentMap[player.y][player.x - 2] = 1;
                    }
                } else if (currentMap[player.y][player.x - 1] == 2)
                    return false;
                break;
            case RIGHT:
                if ((player.x + 1) >= gridCount)
                    return false;
                // 다음 진행할 곳의 장애물 검사
                if (currentMap[player.y][player.x + 1] == 1 || currentMap[player.y][player.x + 1] == 10) {
                    if (player.x + 2 >= gridCount
                            || currentMap[player.y][player.x + 2] == 1 || currentMap[player.y][player.x + 2] == 2 || currentMap[player.y][player.x + 2] == 10) {
                        return false;
                    }
                    if (currentMap[player.y][player.x + 2] == 9) {
                        if (currentMap[player.y][player.x + 1] == 10) {
                            currentMap[player.y][player.x + 1] = 9;
                        } else {
                            currentMap[player.y][player.x + 1] = 0;
                        }
                        currentMap[player.y][player.x + 2] = 10;
                    } else if (currentMap[player.y][player.x + 1] == 10) {
                        currentMap[player.y][player.x + 1] = 9;
                        currentMap[player.y][player.x + 2] = 1;
                    } else {
                        currentMap[player.y][player.x + 1] = 0;
                        currentMap[player.y][player.x + 2] = 1;
                    }
                } else if (currentMap[player.y][player.x + 1] == 2)
                    return false;
                break;
        }
        return true;
    }

//    private void completionProcess() {
//        for (int x = 0; x < gridCount; x++) {
//            for (int y = 0; y < gridCount; y++) {
//                if (currentMap[y][x] == 9) {
//
//                }
//            }
//        }
//    }

    public void setCurrentMap(int[][] map) {
        currentMap = map;
    }
}
