package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //int nombreDObjets = 40;              //Nombre d'objets en tout dans la simulation
        //int nombreDeContraintes = 20;       //Nombres de contraitnes par objet
        //int maxUtilite = 100;               //Utilité max par objet, aleatorie entre 0 et 100 (Comme si on avait un pourcentage d'utilité)
        //int maxCout = 8;                    //Avec 8 les budgets max par contrainte sera entre 80 et 120
        //long seed = 1234L;                  //Seed pour avoir le meme environnement dans tous les tests
        //double elitismeRate = 0.2;
        //Random random = new Random(seed);

        int nombreDObjets = 50;             // Définir le nombre d'objets
        int nombreDeContraintes = 20;       // Définir le nombre de contraintes
        int maxUtilite = 1000;              // L'utilité maximale pour les objets
        int maxCout = 50;                   // Le coût maximal pour les objets
        long seed = 1234L;                  // Seed pour la reproductibilité
        int populationSize = 100;           // Taille de la population
        double mutationRate = 0.2;          // Taux de mutation
        double elitismRate = 0.9;           // Taux d'élitisme
        int maxGenerations = 400;           // Nombre maximum de générations

        Env.initialiser(nombreDObjets, nombreDeContraintes, maxUtilite, maxCout, seed);

        System.out.println("--------------------------------------------------- ENV ------------------------------------------------------------------------------------------------------");
        System.out.println("OBJETS  ------- : " + Env.getObjets());
        System.out.println("BUDGETS ------- : \n" + Env.getBudgets());
        System.out.println("--------------------------------------------------- ENV ------------------------------------------------------------------------------------------------------");


        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize, seed);


        List<Integer> maxFitnessPerGeneration = new ArrayList<>();

        // Simulation de l'évolution
        for (int i = 0; i < maxGenerations; i++) {
            Sac bestSac = geneticAlgorithm.solve(mutationRate, elitismRate, 1); // Solve pour une seule génération à la fois
            maxFitnessPerGeneration.add(bestSac.getFitness());
        }

        // Création du dataset pour le graphique
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < maxFitnessPerGeneration.size(); i++) {
            dataset.addValue(maxFitnessPerGeneration.get(i), "Fitness", Integer.toString(i));
        }

        // Création du graphique
        JFreeChart chart = ChartFactory.createLineChart(
                "Évolution de l'utilité par génération",
                "Génération", "Utilité",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Affichage du graphique dans une fenêtre
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Sac bestSac = geneticAlgorithm.solve(mutationRate, elitismRate, maxGenerations);

        //if (bestSac != null) {
        //    System.out.println("Meilleur Sac trouvé avec fitness: " + bestSac.getFitness());
        //    System.out.println(bestSac);
        //} else {
        //    System.out.println("Aucun sac satisfaisant trouvé.");
        //}

        //List<Integer> result = new ArrayList<>();

        //for (int i = 0; i < Env.getBudgets().size(); i++) {
        //    result.add(Env.getBudgets().get(i) - bestSac.getCoutsActuels().get(i));
        //}
        //System.out.println("Soustraction : " + result);






        //System.out.println("-------------------------------------------- Initial Population ------------------------------------------------------------------------------------------------------");
        //System.out.println("POP : \n" + population);


        //System.out.println("--------------------------------------------------- POP ------------------------------------------------------------------------------------------------------");
        //System.out.println("POP : \n" + population);

        //Sac sac = new Sac();
        //System.out.println("Mutation : " + sac);
        //sac.muter(0.5d, random);
        //System.out.println("!Mutation : " + sac);
        //System.out.println(sac.getFitness());
    }
}