package software.eii.ulpgc.psl.minesweeper;

import software.eii.ulpgc.psl.minesweeper.swing.MainFrame;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(0);
        Map<String, Command> commands = new HashMap<>();
        BoardDisplay boardDisplay = new BoardDisplay(board.rows(),board.cols());
        BoardPresenter presenter = new BoardPresenter(board, boardDisplay);
        createButtonCommands(commands, presenter);
        MainFrame mainFrame = new MainFrame(boardDisplay, commands);
        mainFrame.setVisible(true);
    }
    private static void createButtonCommands(Map<String, Command> commands, BoardPresenter boardPresenter) {
        commands.put("Restart", boardPresenter::restart);
        commands.put("Flag", boardPresenter::toggleFlag);
        commands.put("-EASY", boardPresenter::changeDifficultyEasy);
        commands.put("-INTERMEDIATE", boardPresenter::changeDifficultyMedium);
        commands.put("-HARD", boardPresenter::changeDifficultyHard);
    }
}
