package software.eii.ulpgc.psl.minesweeper;

public class BoardPresenter implements SlotObserver {
    private final BoardDisplay boardDisplay;
    private Board board;
    private int state = 0; // Stand-By = 0, Playing = 1, Win/Lose = 2
    private int flag = 0; // Discover slot = 0, Place flag = 1
    private int discovered = 0;


    public BoardPresenter(Board board, BoardDisplay boardDisplay) {
        this.board = board;
        this.boardDisplay = boardDisplay;
        SlotDisplay[][] boxes = boardDisplay.getBoxes();
        addObserversToSlots(boxes);
    }

    private void addObserversToSlots(SlotDisplay[][] boxes) {
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                boxes[i][j].addObserver(this);
            }
        }
    }

    public void changeDifficultyEasy() {
        reset(0);
    }

    public void changeDifficultyMedium() {
        reset(1);
    }

    public void changeDifficultyHard() {
        reset(2);
    }
    public void restart() {
        reset(board.getDifficulty());
    }

    public void reset(int difficulty){
        board = new Board(difficulty);
        boardDisplay.resetBoardDisplay(board.rows(), board.cols());
        addObserversToSlots(boardDisplay.getBoxes());
        state = 0;
        discovered = 0;
    }


    public void toggleFlag() {
        this.flag = this.flag == 1 ? 0 : 1;
    }

    @Override
    public void changed(int row, int col) {
        int cellState = board.boardState()[row][col];
        int cellValue = board.boardValues()[row][col];
        SlotDisplay slot = boardDisplay.getBoxes()[row][col];
        if (cellContinuesInTheSameState(cellState)) {
            return;
        }
        if (this.flag == 1) {
            switchFlag(row, col, slot);
            return;
        }
        if (this.state == 0){
            startRound(row, col);
        }
        discoverCell(row, col, cellValue, slot);
    }

    private void discoverCell(int row, int col, int cellValue, SlotDisplay slot) {
        slot.discoverNumber(cellValue);
        board.setState(row, col, 1);
        if (cellValue == 0) discoverNeighbors(row, col);
        if (cellValue == -1){
            boardDisplay.lost();
            this.state = 2;
        } else {
            discovered++;
        }
        if (discovered == (board.rows()* board.cols()) - board.nBombs() ){
            boardDisplay.won();
            this.state = 2;
        }
    }

    private void switchFlag(int row, int col, SlotDisplay slot) {
        slot.switchFlag();
        board.switchFlag(row, col);
    }

    private void startRound(int row, int col) {
        board.fill(row, col);
        this.state = 1;
    }

    private boolean cellContinuesInTheSameState(int cellState) {
        return (cellState == Board.OPEN) || (this.flag == 0 && cellState == Board.FLAG) || (this.state == 2);
    }


    public void discoverNeighbors(int row, int col){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row + i < board.rows() && row + i >= 0 && col + j < board.cols() && col + j >= 0)
                    if (board.boardState()[row+i][col+j] == 0)
                        boardDisplay.getBoxes()[row+i][col+j].pulsed();
            }
        }
    }
}