package com.masterschief.eulersquare.logic;

import android.graphics.Paint;

import com.masterschief.eulersquare.controller.GameController;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class LSquare {
    private Logger log = Logger.getLogger(LSquare.class.getName());

    private int n;
    private Pair[][] eulerSquare;

    public Pair[][] getEulerSquare() {
        return eulerSquare;
    }

    public Pair[][] getMixEulerSquare() {
        mix();
        return eulerSquare;
    }

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
                k1 = 0;
                k2 = 0;
                break;
            case 3:
                k1 = 2;
                k2 = 1;
                break;
        }

        fillLSquare(k1,k2);
        mix();
        //printPairs();
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

            printPairs();
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

        if (n == 4) {
            eulerSquare[1][0] = new Pair(2,3);
            eulerSquare[1][1] = new Pair(1,4);
            eulerSquare[1][2] = new Pair(4,1);
            eulerSquare[1][3] = new Pair(3,2);
            eulerSquare[2][0] = new Pair(3,4);
            eulerSquare[2][1] = new Pair(4,3);
            eulerSquare[2][2] = new Pair(1,2);
            eulerSquare[2][3] = new Pair(2,1);
            eulerSquare[3][0] = new Pair(4,2);
            eulerSquare[3][1] = new Pair(3,1);
            eulerSquare[3][2] = new Pair(2,4);
            eulerSquare[3][3] = new Pair(1,3);
        } else {
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tmpFirst = (j - kFirst < 0) ? eulerSquare[i - 1][n + (j - kFirst)].first : eulerSquare[i - 1][j - kFirst].first;
                    tmpSecond = (j - kSecond < 0) ? eulerSquare[i - 1][n + (j - kSecond)].second : eulerSquare[i - 1][j - kSecond].second;

                    eulerSquare[i][j] = new Pair(tmpFirst, tmpSecond);
                }
            }
        }

        printPairs();
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
        String str = "";
        log.info("LSquare Print pairs\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str += "(" + eulerSquare[i][j].first + ", " + eulerSquare[i][j].second + ")" + "  ";
            }
            log.info(str);
            str = "";
            System.out.println();
        }

        log.info("pisdec is " + !checkWin());
    }

    public boolean checkWin() {
        int sum = (int) ((1 + n) / 2.0 * n);
        int j_sumF = 0;
        int i_sumF = 0;
        int j_sumS = 0;
        int i_sumS = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                i_sumF += eulerSquare[i][j].first;
                j_sumF += eulerSquare[i][j].first;
                i_sumS += eulerSquare[i][j].second;
                j_sumS += eulerSquare[i][j].second;
            }
            if (i_sumF != sum || j_sumF != sum|| i_sumS != sum|| j_sumS != sum) {
                return false;
            }
            i_sumF = j_sumF = i_sumS = j_sumS = 0;
        }

        ArrayList<Pair> list = new ArrayList(n * n);

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
}
