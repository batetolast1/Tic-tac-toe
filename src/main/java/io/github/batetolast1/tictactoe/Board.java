package io.github.batetolast1.tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Board {
    private final Scanner scanner = new Scanner(System.in);
    private final StringBuilder stringBuilder = new StringBuilder();

    private final int boardSize;
    private final int numOfFields;
    private final char[][] board;
    private BoardState boardState;
    int numOfMoves;

    char currentPlayer;
    boolean isNextMoveValid;
    int xCoordinate;
    int yCoordinate;

    public Board(int boardSize, char firstPlayer) {
        this.boardSize = boardSize;
        this.numOfFields = boardSize * boardSize;
        this.board = new char[boardSize][boardSize];
        this.currentPlayer = firstPlayer;
        this.boardState = BoardState.NOT_FINISHED;

        fillBoardWithEmptyFields();
        printBoard();
    }

    // printing board

    private void fillBoardWithEmptyFields() {
        for (char[] line : board) {
            Arrays.fill(line, ' ');
        }
    }

    private void printBoard() {
        stringBuilder.setLength(0);
        appendBoardBorder();
        for (int i = 0; i < boardSize; i++) {
            stringBuilder.append("| ");
            for (int j = 0; j < boardSize; j++) {
                stringBuilder.append(board[i][j]).append(" ");
            }
            stringBuilder.append("|").append(System.lineSeparator());
        }
        appendBoardBorder();
        System.out.println(stringBuilder);
    }

    private void appendBoardBorder() {
        stringBuilder.append("-".repeat(2 * boardSize + 3)).append(System.lineSeparator());
    }

    // gameplay flow

    public void playGame() {
        while (isGameNotFinished()) {
            getNextMoveCoordinates();
            makeMove();
            determineBoardState();
            changePlayer();
            prepareForNextInput();
            printBoard();
        }
        printResult();
    }

    private boolean isGameNotFinished() {
        return boardState == BoardState.NOT_FINISHED;
    }

    // process input

    private void getNextMoveCoordinates() {
        while (!isNextMoveValid) {
            System.out.print("Enter the coordinates: ");
            parseInput();
            verifyInput();
        }
    }

    private void parseInput() {
        String[] input = scanner.nextLine().trim().split(" ");
        try {
            if (input.length == 2) {
                xCoordinate = Integer.parseInt(input[0]);
                yCoordinate = Integer.parseInt(input[1]);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("You should enter numbers!");
        }
    }

    private void verifyInput() {
        if (!isInputInRange()) {
            System.out.println("Coordinates should be from 1 to 3!");
        } else if (!isCellEmpty()) {
            System.out.println("This cell is occupied! Choose another one!");
        } else {
            isNextMoveValid = true;
        }
    }

    private boolean isInputInRange() {
        return xCoordinate >= 1 && xCoordinate <= boardSize && yCoordinate >= 1 && yCoordinate <= boardSize;
    }

    private boolean isCellEmpty() {
        convertInputToArrayCoordinates();
        return board[yCoordinate][xCoordinate] == ' ';
    }

    private void convertInputToArrayCoordinates() {
        xCoordinate--;
        yCoordinate = boardSize - yCoordinate;
    }

    // making a move

    private void makeMove() {
        board[yCoordinate][xCoordinate] = currentPlayer;
        numOfMoves++;
    }

    // determining game result
    private void determineBoardState() {
        if (isGameWon()) {
            chooseWinner();
        } else if (isDraw()) {
            boardState = BoardState.DRAW;
        }
    }

    private boolean isGameWon() {
        return checkRow() || checkColumn() || checkDiagonal() || checkAntiDiagonal();
    }

    private void chooseWinner() {
        if (currentPlayer == 'X') {
            boardState = BoardState.X_WINS;
        } else {
            boardState = BoardState.O_WINS;
        }
    }

    private boolean isDraw() {
        return numOfMoves == numOfFields;
    }

    // checking for wins
    private boolean checkRow() {
        for (int i = 0; i < boardSize; i++) {
            if (board[i][xCoordinate] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn() {
        for (int i = 0; i < boardSize; i++) {
            if (board[yCoordinate][i] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonal() {
        for (int i = 0; i < boardSize; i++) {
            if (board[i][i] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAntiDiagonal() {
        for (int i = 0; i < boardSize; i++) {
            if (board[i][boardSize - i - 1] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    // preparing for next move

    private void changePlayer() {
        currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
    }

    private void prepareForNextInput() {
        isNextMoveValid = false;
    }

    // printing result
    private void printResult() {
        System.out.println(boardState.stateDescription);
    }
}
