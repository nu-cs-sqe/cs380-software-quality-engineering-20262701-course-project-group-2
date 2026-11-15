package domain;

public class Location {
    private final int column;
    private final int row;

    public Location(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Location)) {
            return false;
        }

        Location otherLocation = (Location) other;
        return column == otherLocation.column && row == otherLocation.row;
    }

    @Override
    public int hashCode() {
        return 31 * column + row;
    }

}
