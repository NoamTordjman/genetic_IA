package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    private Population population;
    private int populationSize;
    private final double probMutaiton = 0.1;
    private Random random;

    public GeneticAlgorithm(int populationSize, long seed) {
        this.population = new Population();
        this.populationSize = populationSize;
        this.random = new Random(seed);
        initializePopulation();
    }

    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Sac sac = new Sac(random);
            population.add(sac);
        }
    }

    public ArrayList<Couple> selection() {
        ArrayList<Couple> couples = new ArrayList<>();
        RandomSelector selector = new RandomSelector();

        // Remplir le RandomSelector avec les fitness des individus
        for (Sac sac : population.getSacs()) {
            sac.updateFitness();
            selector.add(sac.getFitness()); // Convertir la fitness en int si nécessaire
            //System.out.println("Fitness added: " + sac.getFitness());
        }

        // Sélectionner des paires d'individus
        for (int i = 0; i < populationSize / 2; i++) {
            int index1 = selector.randomChoice();
            int index2 = selector.randomChoice();

            // Assurer que les deux parents ne sont pas les mêmes
            while (index1 == index2) {
                index2 = selector.randomChoice();
            }

            Sac parent1 = population.get(index1);
            Sac parent2 = population.get(index2);
            couples.add(new Couple(parent1, parent2));
        }
        return couples;
    }

    public Population crossover(List<Couple> couples) {
        Population newPopulation = new Population();
        for (Couple couple : couples) {
            Sac parent1 = couple.getFather();
            Sac parent2 = couple.getFather();
            Sac child1 = parent1.copy(); // Créer un enfant à partir de la copie du parent 1
            Sac child2 = parent2.copy(); // Créer un enfant à partir de la copie du parent 2

            // Mélanger les caractéristiques
            for (int i = 0; i < child1.getContenu().size(); i++) {
                if (random.nextBoolean()) {
                    child1.getContenu().set(i, parent2.getContenu().get(i)); // Choisir trait du parent 2
                }
                if (random.nextBoolean()) {
                    child2.getContenu().set(i, parent1.getContenu().get(i)); // Choisir trait du parent 1
                }
            }
            newPopulation.add(child1);
            newPopulation.add(child2);
        }
        return newPopulation;
    }


    public Sac solve(double mutationRate, double elitismRate, int maxGenerations) {
        int generation = 0;
        while (generation < maxGenerations) {
            ArrayList<Couple> parents = this.selection();           // Obtient les couples pour la reproduction
            Population newPopulation = this.crossover(parents);     // Crée une nouvelle population par crossover

            // Mutation
            for (int i = 0; i < newPopulation.size(); i++) {
                if (random.nextDouble() < mutationRate) {
                    newPopulation.get(i).muter(probMutaiton, random);
                }
            }

            // Élitisme: Conserve une partie des meilleurs individus de la génération précédente
            applyElitism(newPopulation, elitismRate);

            // Remplace l'ancienne population par la nouvelle
            this.population = newPopulation;
            generation++;
        }

        return getBestSac();
    }

    //Fonction qui va changer une partie de la population
    private void applyElitism(Population newPopulation, double elitismRate) {
        int elitismNumber = (int) (elitismRate * this.population.size());
        List<Sac> eliteSacs = this.population.getTopFitnessSacs(elitismNumber); // Méthode fictive pour récupérer les meilleurs sacs
        for (int i = 0; i < elitismNumber; i++) {
            newPopulation.replace(i, eliteSacs.get(i)); // Remplace les moins bons de la nouvelle pop. par les meilleurs de l'ancienne
        }
    }

    private Sac getBestSac() {
        // Retourne le sac avec la meilleure fitness
        return this.population.getHighestFitnessSac(); // Méthode fictive pour obtenir le meilleur Sac
    }
}
