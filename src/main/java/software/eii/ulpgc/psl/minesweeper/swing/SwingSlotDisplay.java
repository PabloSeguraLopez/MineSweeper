package software.eii.ulpgc.psl.minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlotDisplay extends JButton {
    private final int col;
    private final int row;
    private static final Color[] values_color = {Color.CYAN, Color.GREEN, Color.RED, Color.BLUE, Color.PINK, Color.ORANGE};
    private List<SlotObserver> observers = new ArrayList<>();

    public SlotDisplay(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.setBackground(Color.GRAY);
        this.addActionListener(e -> pulsed());
    }

    public void addObserver(SlotObserver observer){
        observers.add(observer);
    }

    public void pulsed() {
        observers.forEach(o->o.changed(this.row, this.col));
    }

    public void discoverNumber(int number) {
        setBackground(Color.WHITE);
        if (number != -1 && number != 0)
            setForeground(values_color[number - 1]);
        if (number == 0) {
            setText("");
        } else if (number != -1) {
                setText(String.valueOf(number));
        } else {
            try {
                Image img = ImageIO.read(new File("src/main/resources/bomba.png"))
                        .getScaledInstance(20,20,Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(img));
            } catch (IOException e) {
            }
        }
    }


    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }


    public void switchFlag() {
        if (this.getIcon() == null) {
            try {
                Image img = ImageIO.read(new File("src/main/resources/flag.png"))
                        .getScaledInstance(20,20,Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(img));
            } catch (IOException e) {
            }
        } else {
            this.setIcon(null);
        }
    }
}