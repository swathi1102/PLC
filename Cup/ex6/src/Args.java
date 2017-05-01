package src;

public class Args {

    private Aexp Fi, Se;

    Args(Aexp x, Aexp y) {
        Fi = x;
        Se = y;
    }

    public Aexp getfi() {
        return Fi;
    }

    public Aexp getse() {
        return Se;
    }

}
