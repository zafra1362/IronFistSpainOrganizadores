package com.renegade;

public class TekkenFT {


    public double calcularPuntos(int puntuacionLocal, int puntuacionVisitante, int ft) {

        double k = 100 * ((double) ft / 10);

        double p = 1 / (1 + Math.pow(10, (((double) puntuacionVisitante - (double) puntuacionLocal) / 400)));

        int difP = (int) (k * (1 - p));

        return difP;
    }
}
