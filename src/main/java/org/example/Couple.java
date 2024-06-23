package org.example;

/**
 * This class represents a couple of PentominosBoard.
 * The two elements of the couple represents parents
 * of individuals in a genetic algorithm.
 * @author Documented by Hugo Gilbert, a large part of the code
 * was written by David Eck and Julien Lesca
 *
 */
public class Couple {
    public Sac mother, father;

    public Couple(Sac m, Sac f) {
        this.mother = m.copy();
        this.father = f.copy();
    }

    public Sac getMother() {
        return this.mother;
    }
    public Sac getFather() {
        return this.father;
    }

}