package TicTacToe;

import java.util.Arrays;
import java.util.Random;

public class Logic {
    static int SIZE = 5;
    static int DOTS_TO_WIN = 4;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;

    static Random random = new Random();

    static boolean isFinishedGame;
    static String dialogFinished;
    static int[] winLine = {0, 0, 0, 0};

    private static void go() {
        isFinishedGame = true;

        if (checkWin( DOT_X )) {
            dialogFinished = "Вы победили! Поздравляем!";
            return;
        }
        if (isFull()) {
            dialogFinished = "Ничья";
            Arrays.fill( winLine, 0 );
            return;
        }

        aiTurn();

        if (checkWin( DOT_O )) {
            dialogFinished = "Компьютер победил.";
            return;
        }
        if (isFull()) {
            dialogFinished = "Ничья";
            return;
        }

        isFinishedGame = false;
    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < map.length; i++) {
            Arrays.fill( map[i], DOT_EMPTY );
        }
    }

    public static void humanTurn(int x, int y) {
        if (!isCellValid( y, x )) {
            return;
        }

        map[y][x] = DOT_X;
        go();
    }

    public static boolean isCellValid(int y, int x) {
        if (y < 0 || x < 0 || y >= SIZE || x >= SIZE) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    public static void aiTurn() {
        int x = -1;
        int y = -1;
        boolean win = false;

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                if (isCellValid( i, j )) {
                    map[i][j] = DOT_X;
                    if (checkWin( DOT_X )) {
                        x = j;
                        y = i;
                        win = true;
                    }
                    map[i][j] = DOT_EMPTY;

                }
            }
        }
        if (!win) {

            do {
                x = random.nextInt( SIZE );
                y = random.nextInt( SIZE );
            } while (!isCellValid( y, x ));
        }

        map[y][x] = DOT_O;
    }

    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWin(char dot) {

        for (int column = 0; column < SIZE - DOTS_TO_WIN + 1; column++) {
            for (int row = 0; row < SIZE - DOTS_TO_WIN + 1; row++) {
                if (checkD( dot, column, row ) || checkCR( dot, column, row )) {
                    return true;
                }
            }
        }
        return false;

    }

    public static boolean checkD(char dot, int c, int r) {

        boolean leftD = true;
        boolean rightD = true;
        for (int i = 0; i < DOTS_TO_WIN; i++) {

            leftD &= (map[i + c][i + r] == dot);
            rightD &= (map[DOTS_TO_WIN - i - 1 + c][i + r] == dot);

        }
        if (leftD) {
            winLine[1] = c;
            winLine[0] = r;
            winLine[3] = DOTS_TO_WIN - 1 + c;
            winLine[2] = DOTS_TO_WIN - 1 + r;
        }
        if (rightD) {
            winLine[1] = c;
            winLine[0] = DOTS_TO_WIN - 1 + r;
            winLine[3] = DOTS_TO_WIN - 1 + c;
            winLine[2] = r;
        }

        return leftD || rightD;
    }

    public static boolean checkCR(char dot, int c, int r) {

        boolean column;
        boolean row;
        for (int i = c; i < DOTS_TO_WIN + c; i++) {
            column = true;
            row = true;

            for (int j = r; j < DOTS_TO_WIN + r; j++) {

                row &= (map[i][j] == dot);
                column &= (map[j][i] == dot);
            }

            if (column || row) {

                if (column) {
                    winLine[0] = i;
                    winLine[1] = r;
                    winLine[2] = i;
                    winLine[3] = DOTS_TO_WIN - 1 + r;

                }
                if (row) {
                    winLine[0] = DOTS_TO_WIN - 1 + r;
                    winLine[1] = i;
                    winLine[2] = r;
                    winLine[3] = i;

                }

                return true;
            }
        }

        return false;
    }
}