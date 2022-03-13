package com.drakkens.gamecenter.Classes.Games.G2048;

public enum MovementDirection {
    DOWN,
    LEFT,
    RIGHT,
    UNDEFINED,
    UP;

    enum GeneralAxis {
    HORIZONTAL,
    VERTICAL
    }

    public GeneralAxis getGeneralAxis() {
        switch (this) {
            case DOWN:
            case UP:
                return GeneralAxis.VERTICAL;
            case RIGHT:
            case LEFT:
                return GeneralAxis.HORIZONTAL;
        }
        return null;
    }


    public int[] toInt() {
        switch (this) {

            case DOWN:
                return new int[]{-1, 0};
            case LEFT:
                return new int[]{0, 1};
            case RIGHT:
                return new int[]{0, -1};
            case UNDEFINED:
                break;
            case UP:
                return new int[]{1, 0};
        }

        return new int[0];
    }

    public int[] getStartPosition(TableCell[][] table) {
        switch (this) {
            case DOWN:
                return new int[]{table.length - 1, 0};
            case LEFT:
            case UP:
                return new int[]{0, 0};
            case RIGHT:
                return new int[]{0, table.length - 1};

        }
        return new int[0];
    }

    public MovementDirection opposite() {
        switch (this) {
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
        }

        return this;
    }


}
