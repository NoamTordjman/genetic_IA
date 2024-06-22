package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sac {
    private List<Boolean> contenu; // Vecteur binaire des objets (true = inclus, false = non inclus)
    private List<Integer> coutsActuels; // Coûts courants pour chaque contrainte

    // Constructeur
    public Sac() {
        int nombreDeContraintes = Env.getBudgets().size();
        int nombreDObjets = Env.getObjets().size();

        this.contenu = new ArrayList<>(nombreDObjets);
        this.coutsActuels = new ArrayList<>(nombreDeContraintes);

        // Initialiser les coûts actuels à zéro
        for (int j = 0; j < nombreDeContraintes; j++) {
            coutsActuels.add(0);
        }

        Random random = new Random();
        // Initialiser le contenu du sac de manière aléatoire mais en respectant les budgets
        for (int i = 0; i < nombreDObjets; i++) {
            boolean addItem = random.nextBoolean(); // Décide aléatoirement si l'objet doit être envisagé
            if (addItem) {
                List<Integer> coutsObjet = Env.getObjets().get(i).getCouts();
                if (peutAjouter(coutsObjet)) {
                    contenu.add(true);
                    // Mettre à jour les coûts actuels
                    for (int j = 0; j < nombreDeContraintes; j++) {
                        coutsActuels.set(j, coutsActuels.get(j) + coutsObjet.get(j));
                    }
                } else {
                    contenu.add(false);
                }
            } else {
                contenu.add(false);
            }
        }
    }

    // Vérifie si l'ajout de l'objet respecte les contraintes de budget
    private boolean peutAjouter(List<Integer> coutsObjet) {
        for (int j = 0; j < coutsActuels.size(); j++) {
            if (coutsActuels.get(j) + coutsObjet.get(j) > Env.getBudgets().get(j)) {
                return false;
            }
        }
        return true;
    }

    // Méthode pour vérifier si le sac respecte les contraintes de budget
    public boolean respecteLesContraintes() {
        for (int j = 0; j < coutsActuels.size(); j++) {
            if (coutsActuels.get(j) > Env.getBudgets().get(j)) {
                return false;
            }
        }
        return true;
    }

    public List<Boolean> getContenu() {
        return contenu;
    }

    @Override
    public String toString() {
        return STR."Sac{contenu=\{contenu}, coutsActuels=\{coutsActuels}\{'}'}";
    }
}
