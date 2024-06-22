package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Objet {
    private int utilite;
    private List<Integer> couts;

    // Constructeur de l'objet avec des valeurs aléatoires pour l'utilité et les coûts
    public Objet(int nombreDeCouts, int maxUtilite, int maxCout) {
        Random random = new Random();

        // Générer une utilité aléatoire entre 1 et maxUtilite
        this.utilite = random.nextInt(maxUtilite) + 1;

        // Initialiser la liste des coûts et remplir avec des valeurs aléatoires
        this.couts = new ArrayList<>();
        for (int i = 0; i < nombreDeCouts; i++) {
            couts.add(random.nextInt(maxCout) + 1);
        }
    }

    public Objet(int utilite, List<Integer> couts) {
        //Random random = new Random();

        // Générer une utilité aléatoire entre 1 et maxUtilite
        //this.utilite = random.nextInt(maxUtilite) + 1;
        this.utilite = utilite;
        this.couts = new ArrayList<>(couts);
    }

    // Getters et Setters
    public int getUtilite() {
        return utilite;
    }

    public void setUtilite(int utilite) {
        this.utilite = utilite;
    }

    public List<Integer> getCouts() {
        return couts;
    }

    public void setCouts(List<Integer> couts) {
        this.couts = couts;
    }

    public int getCoutI(int i) {
        return this.couts.get(i);
    }

    @Override
    public String toString() {
        return STR."\noi{utilite=\{utilite}, couts=\{couts}\{'}'}";
    }
}
