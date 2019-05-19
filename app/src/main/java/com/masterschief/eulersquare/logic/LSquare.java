package com.masterschief.eulersquare.logic;

import java.util.ArrayList;
import java.util.Random;

public class LSquare {
    private int n;
    private Pair[][] eulerSquare;

    public LSquare(int n) {
        this.n = n;
        eulerSquare = new Pair[n][n];

        int k1 = 0, k2 = 0;
        switch (n) {
            case 5:
                k1 = 4;
                k2 = 2;
                break;
            case 4:
                k1 = 3;
                k2 = 1;
                break;
            case 3:
                k1 = 2;
                k2 = 1;
                break;
        }

        fillLSquare(k1,k2);
        printPairs();
    }

    private void mix() {
        if (Math.random() < 0.5) transp();
        for (int i = 0; i < 20; i++) {
            if (Math.random() < 0.5) transp();
            int r1 = (int) (Math.random() * n);
            int r2 = (int) (Math.random() * n);
            int r3 = (int) (Math.random() * n);
            int r4 = (int) (Math.random() * n);

            System.out.println("r1, r2 = " + r1 + ", " + r2 + "\nr3, r4 = " + r3 + ", " + r4);
            permI(r1,r2);
            permJ(r3 , r4);
        }
        if (Math.random() < 0.5) transp();
    }

    private void permI(int f, int s) {
        Pair[] tmp = new Pair[n];

        for (int j = 0; j < n; j++) {
            tmp[j] = eulerSquare[f][j];
        }

        //from second to first
        for (int j = 0; j < n; j++) {
            eulerSquare[f][j] = eulerSquare[s][j];
        }

        //from first (copy in tmp) to second
        for (int j = 0; j < n; j++) {
            eulerSquare[s][j] = tmp[j];
        }
    }

    private void permJ(int f, int s) {
        Pair[] tmp = new Pair[n];

        for (int i = 0; i < n; i++) {
            tmp[i] = eulerSquare[i][f];
        }

        //from second to first
        for (int i = 0; i < n; i++) {
            eulerSquare[i][f] = eulerSquare[i][s];
        }

        //from first (copy in tmp) to second
        for (int i = 0; i < n; i++) {
            eulerSquare[i][s] = tmp[i];
        }
    }

    private void transp() {
        Pair[][] newEuler = new Pair[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newEuler[j][i] = eulerSquare[i][j];
            }
        }

        eulerSquare = newEuler;
    }

    private void fillLSquare(int kFirst, int kSecond) {
        int tmpFirst;
        int tmpSecond;

        for (int j = 0; j < n; j++) {
            eulerSquare[0][j] = new Pair(j+1, j+1);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpFirst = (j - kFirst < 0) ? eulerSquare[i-1][n + (j-kFirst)].first : eulerSquare[i-1][j - kFirst].first;
                tmpSecond = (j - kSecond < 0) ? eulerSquare[i-1][n + (j-kSecond)].second : eulerSquare[i-1][j - kSecond].second;

                eulerSquare[i][j] = new Pair(tmpFirst, tmpSecond);
            }
        }
    }

    public boolean isOrt() {
        ArrayList<Pair> list = new ArrayList(n*n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                list.add(eulerSquare[i][j]);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Pair comp = list.get(i);
            list.remove(i);
            if (list.contains(comp))
                return false;
        }

        return true;
    }

    private void printPairs() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("(" + eulerSquare[i][j].first + ", " + eulerSquare[i][j].second + ")" + "  ");
            }
            System.out.println();
        }
    }

    /*
    private void print2DArr(int[][] arr) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void fillSquare(int[][] arr, int k) {
        for (int j = 0; j < n; j++) {
            arr[0][j] = (j+1);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = (j - k < 0) ? arr[i-1][n + (j-k)] : arr[i-1][j - k];
            }
        }
    }

    private void fillEulareSquare() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eulerSquare[i][j] = new Pair(mainSquare[i][j], secondSquare[i][j]);
            }
        }
    }
    */
}
