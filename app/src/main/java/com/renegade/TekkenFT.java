package com.renegade;

public class TekkenFT {
    int nCr(int n, int r){
        if (r > n / 2)
            r = n - r;

        int answer = 1;
        for (int i = 1; i <= r; i++) {
            answer *= (n - r + i);
            answer /= i;
        }

        return answer;
    }

    float binomialProbabilityAcumulat(int n, int k, float p){
        float r=0;
        for (int i=k;i<=n;i++){
            r=r+(nCr(n, i) * (float)Math.pow(p, i) * (float)Math.pow(1 - p, n - i));
        }
        return r;
    }

    public double start(int puntuacionW, int puntuacionL, int firstTo) {
        double winner = puntuacionW;
        double loser = puntuacionL;
        double ft = firstTo;

        double K = 100 * (ft / 10);

        double x = ft - 1;
        double r = ft;
        double p = 1 / (1 + Math.pow(10, ((loser - winner) / 1613)));
        float p2 = (float) p;
        int ft2 = ((int) ft * 2) - 1;
        double winP = binomialProbabilityAcumulat(ft2, (int) ft, p2);
        System.out.println(winP);
        double DifP = K * (1 - winP);

        return DifP;
    }
}
