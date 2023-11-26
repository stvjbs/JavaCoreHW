package ru.geekbrains.core.lesson2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Program {
    private static int turnsCounter = 0; // переменная для подсчета общего числа ходов, для выявления ничьи
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final int WIN_COUNT = 4;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static final LinkedList<int[]> listOfHumansTurns = new LinkedList<>(); // Коллекция, созданная для
                                                                         // оптимизации сложности программы
                                                                         // (позволяет проверять статус игры
                                                                         // без прохождения по всем ячейкам игрового поля
    private static final LinkedList<int[]> listOfCompTurns = new LinkedList<>(); // см. выше
    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkGameState())
                    break;
                aiTurn();
                printField();
                if (checkGameState())
                    break;
            }
            listOfHumansTurns.clear();
            listOfCompTurns.clear();
            turnsCounter = 0;
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }
    /**
     * Инициализация игрового поля
     */
    static void initialize() {
        fieldSizeY = 5;
        fieldSizeX = 5;
        field = new char[fieldSizeY][fieldSizeX];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }
    /**
     * Печать текущего состояния игрового поля
     */
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");
        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    /**
     * Ход игрока (человека)
     */
    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3)\nчерез пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
        listOfHumansTurns.add(new int[]{x, y});
        turnsCounter++;
    }
    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn() {
        int x;
        int y;
        ArrayList<Integer> arrayList = niceMove(); // Сохраненные координаты точки, препятствующие победе человека.
        if (!arrayList.isEmpty()) {
            x = arrayList.get(0);
            y = arrayList.get(1);
            field[x][y] = DOT_AI;
            listOfCompTurns.add(new int[]{x, y}); // Сохранение ходов ИИ.
            turnsCounter++;
            return;
        } else do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
        listOfCompTurns.add(new int[]{x, y}); // Сохранение ходов ИИ.
        turnsCounter++;
    }
    /**
     * Проверка, является ли ячейка игрового поля пустой
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }
    /**
     * Проверка доступности ячейки игрового поля
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }
    /**
     * Метод проверки состояния игры
     */
    static boolean checkGameState() {
        if (checkWin(listOfHumansTurns, DOT_HUMAN)) {
            System.out.println("Вы победили!");
            return true;
        }
        if (checkWin(listOfCompTurns, DOT_AI)) {
            System.out.println("Компьютер победил!");
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }
    /**
     * Проверка на ничью
     */
    static boolean checkDraw() {
        return turnsCounter == fieldSizeX * fieldSizeY;
    }
    /**
     * Проверка победы игрока
     */
    static boolean checkWin(LinkedList<int[]> list, char dot) {
        int x;
        int y;
        for (int[] dotCoordinates : list) {
            x = dotCoordinates[0];
            y = dotCoordinates[1];
            if (checkAll(x, y, dot, WIN_COUNT)) return true;
        }
        return false;
    }
    static boolean checkAll(int x, int y, char dot, int winCount) {
        return checkHorizontal(x, y, dot, winCount) || checkDiagonal1(x, y, dot, winCount) ||
                checkDiagonal2(x, y, dot, winCount) || checkVertical(x, y, dot, winCount);
    }
    static boolean checkHorizontal(int x, int y, char dot, int winCount) {
        int dotsStreak = 1;
        try {
            while (field[x][y + 1] == dot) {
                y++;
                dotsStreak++;
                if (dotsStreak == winCount) return true;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }
    static boolean checkDiagonal1(int x, int y, char dot, int winCount) {
        int dotsStreak = 1;
        try {
            while (field[x + 1][y + 1] == dot) {
                x++;
                y++;
                dotsStreak++;
                if (dotsStreak == winCount) return true;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }
    static boolean checkDiagonal2(int x, int y, char dot, int winCount) {
        int dotsStreak = 1;
        try {
            while (field[x + 1][y - 1] == dot) {
                x++;
                y--;
                dotsStreak++;
                if (dotsStreak == winCount) return true;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }
    static boolean checkVertical(int x, int y, char dot, int winCount) {
        int dotsStreak = 1;
        try {
            while (field[x + 1][y] == dot) {
                x++;
                dotsStreak++;
                if (dotsStreak == winCount) return true;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return false;
    }
    private static ArrayList<Integer> niceMove() { // метод "ИИ"
        ArrayList<Integer> niceMoveResult = new ArrayList<>();
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (checkHorizontal(x, y, DOT_HUMAN, WIN_COUNT - 1)) {
                    niceMoveResult.add(x);
                    niceMoveResult.add(y + WIN_COUNT - 1);
                }
                if (checkDiagonal1(x, y, DOT_HUMAN, WIN_COUNT - 1)) {
                    niceMoveResult.add(x + WIN_COUNT - 1);
                    niceMoveResult.add(y + WIN_COUNT - 1);
                }
                if (checkDiagonal2(x, y, DOT_HUMAN, WIN_COUNT - 1)) {
                    niceMoveResult.add(x + WIN_COUNT - 1);
                    niceMoveResult.add(y - WIN_COUNT + 1);
                }
                if (checkVertical(x, y, DOT_HUMAN, WIN_COUNT - 1)) {
                    niceMoveResult.add(x + WIN_COUNT - 1);
                    niceMoveResult.add(y);
                }
                try {
                    if (!niceMoveResult.isEmpty() && field[niceMoveResult.get(0)][niceMoveResult.get(1)] == DOT_EMPTY)
                        return niceMoveResult;

                    else niceMoveResult.clear();
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return new ArrayList<>();
    }
}
