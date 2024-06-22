package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Sac> sacs;
    //private int nbContraintes = Env.getBudgets().size();
    //private int nbObj = Env.getObjets().size();

    public Population(int nombreDeSacs) {
        this.sacs = new ArrayList<>();

        // Créer des sacs initiaux
        for (int k = 0; k < nombreDeSacs; k++) {
            //Sac sac = new Sac(Env.getObjets().size(), Env.getBudgets().size());
            Sac sac = new Sac();
            sacs.add(sac);
            //reparer(sac); // S'assurer que le sac est valide dès le début
        }
    }
    // Méthode de réparation pour un sac

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < sacs.size(); i++) {
            s.append(sacs.get(i)).append('\n');

        }
        return s.toString();
    }
}
