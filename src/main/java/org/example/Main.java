package org.example;

public class Main {
    public static void main(String[] args) {
        int nombreDObjets = 5;             //Nombre d'objets en tout dans la simulation
        int nombreDeContraintes = 20;       //Nombres de contraitnes par objet
        int maxUtilite = 100;               //Utilité max par objet, aleatorie entre 0 et 100 (Comme si on avait un pourcentage d'utilité)
        int maxCout = 8;                    //Avec 8 les budgets max par contrainte sera entre 80 et 120
        long seed = 1234L;                  //Seed pour avoir le meme environnement dans tous les tests
        Env.initialiser(nombreDObjets, nombreDeContraintes, maxUtilite, maxCout, seed);

        int nombreDeSacs = 5;
        Population population = new Population(nombreDeSacs);


        System.out.println("--------------------------------------------------- ENV ------------------------------------------------------------------------------------------------------");
        System.out.println("BUDGETS ------- : \n" + Env.getBudgets());
        System.out.println("OBJETS  ------- : " + Env.getObjets());
        System.out.println("--------------------------------------------------- POP ------------------------------------------------------------------------------------------------------");
        System.out.println("POP : \n" + population.toString());
    }
}