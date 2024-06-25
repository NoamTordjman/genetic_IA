package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Population {
    private List<Sac> sacs;
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

    public Population() {
        this.sacs = new ArrayList<>();
    }

    public Population(int nombreDeSacs) {
        this.sacs = new ArrayList<>();

        // Créer des sacs initiaux tous "vides"
        for (int k = 0; k < nombreDeSacs; k++) {
            Sac sac = new Sac(); // Utiliser le nouveau constructeur
            sacs.add(sac);
        }
    }

    public void add(Sac sac) {
        sacs.add(sac);
    }

    public Sac get(int index) {
        return sacs.get(index);
    }

    public int size() {
        return sacs.size();
    }

    public Iterator<Sac> iterator() {
        return sacs.iterator();
    }

    public void replace(int index, Sac newSac) {
        sacs.set(index, newSac);
    }

    public List<Sac> getTopFitnessSacs(int count) {
        return sacs.stream()
                .sorted(Comparator.comparingInt(Sac::getFitness).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }


    public Sac getHighestFitnessSac() {
        if (sacs == null || sacs.isEmpty()) {
            return null; // Retourne null si la population est vide
        }

        Sac bestSac = sacs.get(0);          // Initialise le meilleur sac au premier sac de la liste
        for (Sac sac : sacs) {
            if (sac.getFitness() > bestSac.getFitness()) {
                bestSac = sac;              // Met à jour le meilleur sac si un sac avec une meilleure fitness est trouvé
            }
        }
        return bestSac;                     // Retourne le sac ayant la meilleure fitness
    }

    public void performMutation(double mutationRate, Random random) {
        for (Sac sac : sacs) {
            if (random.nextDouble() < mutationRate) {
                sac.muter(mutationRate, random);
            }
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
