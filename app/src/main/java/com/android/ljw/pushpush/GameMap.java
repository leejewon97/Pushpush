package com.android.ljw.pushpush;

import java.util.Random;

public class GameMap {
    public GameMap() {
        randMap();
    }

    int map[][] = new int[10][10];

    Random random = new Random();

    private void randMap() {
        int x = 0;
        int y = 0;
        // 박스 생성
        while (true) {
            if (x == 0 || y == 0 || x == 9 || y == 9) {
                x = random.nextInt(9);
                y = random.nextInt(9);
            } else {
                map[y][x] = 1;
                break;
            }
        }
        // 장애물 생성
        while (true) {
            if (map[y][x] == 1
                    || map[y - 1][x - 1] == 1 || map[y - 1][x] == 1 || map[y - 1][x + 1] == 1
                    || map[y + 1][x - 1] == 1 || map[y + 1][x] == 1 || map[y + 1][x + 1] == 1
                    || map[y][x - 1] == 1 || map[y][x + 1] == 1) {
                x = random.nextInt(6) + 2;
                y = random.nextInt(6) + 2;
            } else {
                map[y][x] = 2;
                break;
            }
        }
        // 골 생성
        while (true) {
            if (map[y][x] == 1 || map[y][x] == 2) {
                x = random.nextInt(9);
                y = random.nextInt(9);
            } else {
                map[y][x] = 9;
                break;
            }
        }
    }
}
