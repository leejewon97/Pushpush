package com.android.ljw.pushpush;

import java.util.Random;

public class GameMap {
    public GameMap() {
        randMap();
    }

    public static int LEVEL = 1;

    int map[][] = new int[10][10];

    Random random = new Random();

    private void randMap() {
        int x = 0;
        int y = 0;

        // 처음 무조건 랜덤함수를 거치기 위해
        boolean first = true;

        for (int i = 0; i < LEVEL; i++) {
            // 골 생성
            while (true) {
                if (first || overlapCheck(x, y)) {
                    x = random.nextInt(9);
                    y = random.nextInt(9);
                    first = false;
                } else {
                    map[y][x] = 9;
                    break;
                }
            }
            // 박스 생성
            while (true) {
                if (overlapCheck(x, y)) {
                    x = random.nextInt(8) + 1;
                    y = random.nextInt(8) + 1;
                } else {
                    map[y][x] = 1;
                    break;
                }
            }
            // 장애물 생성
            while (true) {
                if (overlapCheck(x, y) || aroundBoxCheck(x, y) || blockGoalCheck(x, y)) {
                    x = random.nextInt(6) + 2;
                    y = random.nextInt(6) + 2;
                } else {
                    map[y][x] = 2;
                    break;
                }
            }
        }
    }

    private boolean blockGoalCheck(int x, int y) {
        return false;
    }

    private boolean overlapCheck(int x, int y) {
        if (map[y][x] == 1 || map[y][x] == 2 || map[y][x] == 9)
            return true;
        else
            return false;
    }

    private boolean aroundBoxCheck(int x, int y) {
        if (map[y][x - 1] == 1 || map[y][x + 1] == 1
                || map[y - 1][x - 1] == 1 || map[y - 1][x] == 1 || map[y - 1][x + 1] == 1
                || map[y + 1][x - 1] == 1 || map[y + 1][x] == 1 || map[y + 1][x + 1] == 1)
            return true;
        else
            return false;
    }
}
