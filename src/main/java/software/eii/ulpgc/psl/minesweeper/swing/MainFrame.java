package software.eii.ulpgc.psl.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainFrame extends JFrame{
    private BoardDisplay boardDisplay;
    private Map<String, Command> commands;
    private JComboBox<String> difficultySelector;

    public MainFrame(BoardDisplay boardDisplay, Map<String, Command> commands) throws HeadlessException {
        this.setTitle("MineSweeper");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.commands = commands;
        addBoard(boardDisplay);
        addHeader();
    }

    private void addHeader() {
        JPanel header = new JPanel();
        commands.keySet().stream().filter(k-> !k.startsWith("-")).forEach(k->createButton(k, header));
        addDifficultySelector(header);
        add(header, BorderLayout.NORTH);
    }

    private void createButton(String key, JPanel header) {
        JButton button = new JButton(key);
        button.addActionListener(e->commands.get(key).execute());
        header.add(button);
    }

    private void addDifficultySelector(JPanel header) {
        this.difficultySelector = new JComboBox<>();
        difficultySelector.addItem("EASY");
        difficultySelector.addItem("INTERMEDIATE");
        difficultySelector.addItem("HARD");
        difficultySelector.addItemListener(e -> {
            commands.get("-" + difficultySelector.getItemAt(difficultySelector.getSelectedIndex())).execute();
        });
        header.add(difficultySelector);
    }

    public void addBoard(BoardDisplay boardDisplay){
        this.boardDisplay = boardDisplay;
        this.add(boardDisplay, BorderLayout.CENTER);
    }

}
