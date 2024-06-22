package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Sac> sacs;
    //private int nbContraintes = Env.getBudgets().size();
    //private int nbObj = Env.getObjets().size();

    public Population(int nombreDeSacs, long seed) {
        this.sacs = new ArrayList<>();
        Random random = new Random(seed);

        // Créer des sacs initiaux
        for (int k = 0; k < nombreDeSacs; k++) {
            //Sac sac = new Sac(Env.getObjets().size(), Env.getBudgets().size());
            Sac sac = new Sac(random);
            sacs.add(sac);
        }
    }


    public List<Sac> selection() {
        List<Sac> selected = new ArrayList<>();
        double totalFitness = sacs.stream().mapToDouble(Sac::getFitness).sum();
        Random random = new Random();
        int targetSize = sacs.size() - (sacs.size() % 2);  // Assurer un nombre pair

        while (selected.size() < targetSize) {
            double rand = random.nextDouble() * totalFitness;
            double runningSum = 0;
            for (Sac sac : sacs) {
                runningSum += sac.getFitness();
                if (runningSum >= rand && !selected.contains(sac)) {
                    selected.add(sac);
                    break;
                }
            }
        }
        return selected;
    }

    public List<Sac> crossover(List<Sac> selectedSacs) {
        List<Sac> newPopulation = new ArrayList<>();
        Random random = new Random();

        // Assurer un nombre pair pour le crossover
        if (selectedSacs.size() % 2 != 0) {
            selectedSacs.add(selectedSacs.get(random.nextInt(selectedSacs.size())));
        }

        for (int i = 0; i < selectedSacs.size(); i += 2) {
            Sac parent1 = selectedSacs.get(i);
            Sac parent2 = selectedSacs.get(i + 1);
            // Générer deux enfants
            Sac child1 = new Sac(random);
            Sac child2 = new Sac(random);
            for (int j = 0; j < parent1.getContenu().size(); j++) {
                boolean geneFromParent1 = random.nextBoolean();
                child1.getContenu().set(j, geneFromParent1 ? parent1.getContenu().get(j) : parent2.getContenu().get(j));
                child2.getContenu().set(j, !geneFromParent1 ? parent1.getContenu().get(j) : parent2.getContenu().get(j));
            }
            newPopulation.add(child1);
            newPopulation.add(child2);
        }
        return newPopulation;
    }


    public void evolve(int numberOfGenerations, double mutationRate) {
        Random random = new Random();
        for (int gen = 0; gen < numberOfGenerations; gen++) {
            List<Sac> selected = selection();                   // Sélection basée sur la fitness
            List<Sac> offspring = crossover(selected);          // Génération de descendants via crossover

            // Appliquer la mutation définie dans chaque Sac
            for (Sac sac : offspring) {
                sac.muter(mutationRate, random);
            }

            sacs = offspring; // Remplacer l'ancienne population par la nouvelle
        }
    }


    public List<Sac> getSacs() {
        return sacs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        for (Sac sac : sacs) {
            builder.append(sac).append("\n");
        }
        return builder.toString();
    }
}
