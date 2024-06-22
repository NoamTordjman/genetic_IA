package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Env {
    private static List<Objet> objets;
    private static List<Integer> budgets;
    private int fitness;

    // Initialiser les objets et les budgets
    public static void initialiser(int nombreDObjets, int nombreDeContraintes, int maxUtilite, int maxCout, long seed) {
        objets = new ArrayList<>();
        budgets = new ArrayList<>();
        Random random = new Random(seed);

        // Définir les budgets
        int budgetBase = 8 * 3;
        for (int j = 0; j < nombreDeContraintes; j++) {
            int budget = random.nextInt(budgetBase / 2) + budgetBase; // Budgets sont entre budgetBase et 1.5*budgetBase
            budgets.add(budget);
        }

        for (int i = 0; i < nombreDObjets; i++) {
            List<Integer> couts = new ArrayList<>();
            for (int j = 0; j < nombreDeContraintes; j++) {
                int maxCoutContrainte = budgets.get(j) / 3; // Coût maximal par objet est un dixième du budget pour cette contrainte
                couts.add(random.nextInt(maxCoutContrainte) + 1);
            }
            int utilite = random.nextInt(maxCout) + 1;
            objets.add(new Objet(utilite, couts));
        }
    }

    public static List<Objet> getObjets() {
        return objets;
    }

    public static List<Integer> getBudgets() {
        return budgets;
    }
}
