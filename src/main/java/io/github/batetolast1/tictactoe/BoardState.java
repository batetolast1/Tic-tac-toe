package io.github.batetolast1.tictactoe;

public enum BoardState {
    NOT_FINISHED("Game not finished") {},
    DRAW("Draw") {},
    X_WINS("X wins") {},
    O_WINS("O wins") {};

    String stateDescription;

    BoardState(String stateDescription) {
        this.stateDescription = stateDescription;
    }
}
