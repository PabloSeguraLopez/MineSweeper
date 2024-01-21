package software.eii.ulpgc.psl.minesweeper;

public class Board {
    public static final int FLAG = -1;
    public static final int CLOSED = 0;
    public static final int OPEN = 1;
    private int[][] states = {{0}};
    private int[][] boardValues;
    private static final int[] level = {9,12,18};
    private static final int[] bombOptions = {10, 30, 70};
    private int nBombs;
    private int difficulty;

    public Board(int difficulty) {
        this.boardValues = new int[level[difficulty]][level[difficulty]];
        nBombs = bombOptions[difficulty];
        this.difficulty = difficulty;
        fillStates();
    }

    public void fill(int firstPickRow, int firstPickCol){
        this.boardValues = BoardFiller.fill(
                boardValues.length,
                boardValues[0].length,
                firstPickRow,
                firstPickCol,
                nBombs);
    }

    private void fillStates() {
        this.states = new int[boardValues.length][boardValues[0].length];
        for (int i = 0; i < boardValues.length; i++) {
            for (int j = 0; j < boardValues[0].length; j++) {
                states[i][j] = 0;
            }
        }
    }

    public int[][] boardValues(){
        return boardValues;
    }

    public int cols() {
        return boardValues[0].length;
    }

    public int rows() {
        return boardValues.length;
    }

    public int nBombs() {
        return nBombs;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int[][] boardState(){
        return states;
    }

    public void setState(int row, int col, int state) {states[row][col] = state;}

    public void switchFlag(int row, int col) {
        states[row][col] = states[row][col] == -1 ? 0 : -1;
    }
}
