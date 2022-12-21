package listener;

public enum Commands {
    PRESS_KEY(1),
    RELEASED_KEY(-1),
    PRESS_MOUSE(2),
    RELEASED_MOUSE(-2),
    MOUVE_MOUSE(3);
    final int abbrev;
    Commands(int abbrev) {
        this.abbrev = abbrev;
    }
    public int getAbbrev() {
        return this.abbrev;
    }
}
