package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sac {
    private List<Boolean> contenu; // Vecteur binaire des objets (true = inclus, false = non inclus)
    private List<Integer> coutsActuels; // Coûts courants pour chaque contrainte
    private int fitness;

    // Constructeur
    public Sac(Random random) {
        int nombreDeContraintes = Env.getBudgets().size();
        int nombreDObjets = Env.getObjets().size();

        this.contenu = new ArrayList<>(nombreDObjets);
        this.coutsActuels = new ArrayList<>(nombreDeContraintes);

        // Initialiser les coûts actuels à zéro
        for (int j = 0; j < nombreDeContraintes; j++) {
            coutsActuels.add(0);
        }

        //Random random = new Random();
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
    public Sac() {
        int nombreDeContraintes = Env.getBudgets().size();
        int nombreDObjets = Env.getObjets().size();

        this.contenu = new ArrayList<>(nombreDObjets);
        this.coutsActuels = new ArrayList<>(nombreDeContraintes);

        // Initialiser les coûts actuels à zéro et le contenu à false
        for (int i = 0; i < nombreDObjets; i++) {
            this.contenu.add(false); // Aucun objet inclus
        }
        for (int j = 0; j < nombreDeContraintes; j++) {
            this.coutsActuels.add(0); // Coûts initiaux à zéro
        }

        this.fitness = 0; // Fitness initiale à zéro
    }

//    public Sac() {
//        Random random = new Random();
//        int nombreDeContraintes = Env.getBudgets().size();
//        int nombreDObjets = Env.getObjets().size();
//
//        this.contenu = new ArrayList<>(nombreDObjets);
//        this.coutsActuels = new ArrayList<>(nombreDeContraintes);
//
//        // Initialiser les coûts actuels à zéro
//        for (int j = 0; j < nombreDeContraintes; j++) {
//            coutsActuels.add(0);
//        }
//
//        //Random random = new Random();
//        // Initialiser le contenu du sac de manière aléatoire mais en respectant les budgets
//        for (int i = 0; i < nombreDObjets; i++) {
//            boolean addItem = random.nextBoolean(); // Décide aléatoirement si l'objet doit être envisagé
//            if (addItem) {
//                List<Integer> coutsObjet = Env.getObjets().get(i).getCouts();
//                if (peutAjouter(coutsObjet)) {
//                    contenu.add(true);
//                    // Mettre à jour les coûts actuels
//                    for (int j = 0; j < nombreDeContraintes; j++) {
//                        coutsActuels.set(j, coutsActuels.get(j) + coutsObjet.get(j));
//                    }
//                } else {
//                    contenu.add(false);
//                }
//            } else {
//                contenu.add(false);
//            }
//        }
//    }


    // Cette méthode parcourt tous les objets et met à jour les coûts actuels basés sur les objets inclus
    public void updateCoutsActuels() {
        // Réinitialiser les coûts actuels à zéro avant la mise à jour
        for (int j = 0; j < coutsActuels.size(); j++) {
            coutsActuels.set(j, 0);
        }

        // Parcourir chaque objet et, si inclus, ajouter ses coûts aux coûts actuels
        for (int i = 0; i < contenu.size(); i++) {
            if (contenu.get(i)) {
                List<Integer> coutsObjet = Env.getObjets().get(i).getCouts();
                for (int j = 0; j < coutsActuels.size(); j++) {
                    coutsActuels.set(j, coutsActuels.get(j) + coutsObjet.get(j));
                }
            }
        }
    }

    public void updateFitness() {
        fitness = 0;
        for (int i = 0; i < contenu.size(); i++) {
            if (contenu.get(i)) {
                fitness += Env.getObjets().get(i).getUtilite();
            }
        }
    }

    public void muter(double probMutation, Random random) {
        for (int i = 0; i < contenu.size(); i++) {
            if (random.nextDouble() < probMutation) {
                boolean currentState = contenu.get(i);
                contenu.set(i, !currentState);

                // Mettre à jour les coûts actuels en fonction de l'état de l'objet
                if (currentState) {
                    // L'objet était inclus, maintenant retiré, donc soustraire les coûts
                    for (int j = 0; j < coutsActuels.size(); j++) {
                        coutsActuels.set(j, coutsActuels.get(j) - Env.getObjets().get(i).getCouts().get(j));
                    }
                } else {
                    // L'objet était exclu, maintenant inclus, donc ajouter les coûts
                    for (int j = 0; j < coutsActuels.size(); j++) {
                        coutsActuels.set(j, coutsActuels.get(j) + Env.getObjets().get(i).getCouts().get(j));
                    }
                }
            }
        }
        reparer(); // Réparer le sac après mutation pour garantir l'admissibilité
        updateFitness(); // Mise à jour de la fitness après réparation
        updateCoutsActuels();
    }



    private void reparer() {
        // Retirer des objets si nécessaire
        for (int i = contenu.size() - 1; i >= 0; i--) {
            if (contenu.get(i)) {
                for (int j = 0; j < coutsActuels.size(); j++) {
                    if (coutsActuels.get(j) > Env.getBudgets().get(j)) {
                        contenu.set(i, false);
                        coutsActuels.set(j, coutsActuels.get(j) - Env.getObjets().get(i).getCouts().get(j));
                    }
                }
            }
        }

        // Ajouter des objets si possible
        for (int i = 0; i < contenu.size(); i++) {
            if (!contenu.get(i)) {
                boolean canAdd = true;
                for (int j = 0; j < coutsActuels.size(); j++) {
                    if (coutsActuels.get(j) + Env.getObjets().get(i).getCouts().get(j) > Env.getBudgets().get(j)) {
                        canAdd = false;
                        break;
                    }
                }
                if (canAdd) {
                    contenu.set(i, true);
                    for (int j = 0; j < coutsActuels.size(); j++) {
                        coutsActuels.set(j, coutsActuels.get(j) + Env.getObjets().get(i).getCouts().get(j));
                    }
                }
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

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }



    @Override
    public String toString() {
        return STR."Sac{\ncontenu=\{contenu}, \ncoutsActuels=\{coutsActuels}\{'}'}";
    }

    public Sac copy() {
        Sac newSac = new Sac();
        newSac.contenu = new ArrayList<>(this.contenu);
        newSac.coutsActuels = new ArrayList<>(this.coutsActuels);
        newSac.fitness = this.fitness;
        return newSac;
    }
}
