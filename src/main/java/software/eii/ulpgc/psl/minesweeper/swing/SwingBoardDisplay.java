package software.eii.ulpgc.psl.minesweeper;

import javax.swing.*;
import java.awt.*;


public class BoardDisplay extends JPanel{
    private SlotDisplay[][] boxes;
    private int rows = 0;
    private int cols = 0;


    public BoardDisplay(int rows, int cols) {
        resetBoardDisplay(rows, cols);
        this.rows = rows;
        this.cols = cols;
    }
    public void resetBoardDisplay(int rows, int cols){
        setLayout(new GridLayout(rows, cols));
        if (this.rows != 0) removeSlots();
        this.rows = rows;
        this.cols = cols;
        boxes = new SlotDisplay[rows][cols];
        addSlots();
        revalidate();
    }

    private void removeSlots() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                remove(boxes[i][j]);
            }
        }
    }

    private void addSlots() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                SlotDisplay newSlot = new SlotDisplay(i, j);
                boxes[i][j] = newSlot;
                this.add(newSlot);
            }
        }
    }

    public SlotDisplay[][] getBoxes() {return boxes; }

    public void lost(){
        JOptionPane.showMessageDialog(
                this, "Explotaste", "Perdiste", JOptionPane.ERROR_MESSAGE);
    }
    public void won() {
        JOptionPane.showMessageDialog(
                this, "Sobreviviste", "Ganaste", JOptionPane.INFORMATION_MESSAGE);
    }
}
