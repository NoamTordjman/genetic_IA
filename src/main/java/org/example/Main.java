package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int nombreDObjets = 20;              //Nombre d'objets en tout dans la simulation
        int nombreDeContraintes = 10;       //Nombres de contraitnes par objet
        int maxUtilite = 100;               //Utilité max par objet, aleatorie entre 0 et 100 (Comme si on avait un pourcentage d'utilité)
        int maxCout = 8;                    //Avec 8 les budgets max par contrainte sera entre 80 et 120
        long seed = 1234L;                  //Seed pour avoir le meme environnement dans tous les tests
        double elitismeRate = 0.2;
        Random random = new Random(seed);

        Env.initialiser(nombreDObjets, nombreDeContraintes, maxUtilite, maxCout, seed);

        int nombreDeSacs = 200;
        Population population = new Population(nombreDeSacs, seed);

        int numberOfGenerations = 200;      // Nombre de générations pour l'évolution
        double mutationRate = 0.1;         // Taux de mutation

        System.out.println("--------------------------------------------------- ENV ------------------------------------------------------------------------------------------------------");
        System.out.println("BUDGETS ------- : \n" + Env.getBudgets());
        System.out.println("OBJETS  ------- : " + Env.getObjets());

        System.out.println("-------------------------------------------- Initial Population ------------------------------------------------------------------------------------------------------");
        //System.out.println("POP : \n" + population);

        // Boucle d'évolution
        for (int gen = 0; gen < numberOfGenerations; gen++) {
            //System.out.println("Generation " + gen);
            population.evolve(1, mutationRate); // Évoluer pour une génération avec mutation
        }

        // Afficher la fitness du meilleur sac à la fin
        Sac bestSac = population.getSacs().stream()
                .max((s1, s2) -> Integer.compare(s1.getFitness(), s2.getFitness()))
                .orElse(null);

        if (bestSac != null) {
            System.out.println("Best Sac Fitness: " + bestSac.getFitness());
            System.out.println("Best Sac : " + bestSac.toString());
        }

        //System.out.println("--------------------------------------------------- POP ------------------------------------------------------------------------------------------------------");
        //System.out.println("POP : \n" + population);

        //Sac sac = new Sac();
        //System.out.println("Mutation : " + sac);
        //sac.muter(0.5d, random);
        //System.out.println("!Mutation : " + sac);
        //System.out.println(sac.getFitness());
    }
}