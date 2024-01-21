package software.eii.ulpgc.psl.minesweeper;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;

public class BoardFiller {
    private static int[] offsets = {-1,0,1};

    public static int[][] fill(int nRows, int nCols, int firstPickRow, int firstPickCol, int nBombs){
        IntVar[] bombsArray = new IntVar[nRows * nCols];
        IntVar[][] resultArray = new IntVar[nRows][nCols];
        Model model = new Model("Board Filler");
        placeBombs(nRows, nCols, firstPickRow, firstPickCol, bombsArray, model, nBombs);
        countBombsAround(nRows, nCols, bombsArray, resultArray, model);
        Solver solver = model.getSolver();
        return resolveConditions(solver,nRows,nCols,bombsArray,resultArray);
    }

    private static int[][] resolveConditions(Solver solver, int nRows, int nCols, IntVar[] bombsArray, IntVar[][] resultArray) {
        if (solver.solve()){
            int[][] result = new int[nRows][nCols];
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    result[i][j] = resultArray[i][j].getValue();
                }
            }
            return result;
        } else {
            System.out.println("hola");
            return new int[nRows][nCols];
        }
    }

    private static void countBombsAround(int nRows, int nCols, IntVar[] bombsArray, IntVar[][] resultArray, Model model) {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                resultArray[i][j] = model.intVar("" + i + " - " + j, -1, 6);
                model.ifThen(
                        model.arithm(bombsArray[i * nRows + j],"=",1),
                        model.arithm(resultArray[i][j],"=",-1)
                );
                IntVar[] neighbors = neighbors(i,j,bombsArray, nRows, nCols);
                model.ifThen(
                        model.arithm(bombsArray[i * nRows + j],"=",0),
                        model.sum(neighbors,"=",resultArray[i][j])
                );
                model.arithm(resultArray[i][j] , "<", neighbors.length).post();
            }
        }
    }

    private static IntVar[] neighbors(int row, int col, IntVar[] bombsArray, int nRows, int nCols) {
        ArrayList<IntVar> neighbors = new ArrayList<>();
        for (int offsetRow : offsets) {
            for (int offsetCol : offsets) {
                if (offsetCol == 0 && offsetRow == 0) continue;
                if (row + offsetRow < nRows
                        && row + offsetRow >= 0
                        && col + offsetCol < nCols
                        && col + offsetCol >= 0 ){
                    neighbors.add(bombsArray[(row+offsetRow) * nRows + col+offsetCol]);
                }
            }
        }
        return neighbors.toArray(new IntVar[neighbors.size()]);
    }

    private static void placeBombs(int rows, int cols, int firstPickRow, int firstPickCol, IntVar[] bombsArray, Model model, int nBombs) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bombsArray[i * rows + j] = isFirstPickOrAround(i,j,firstPickRow,firstPickCol) ?
                        model.intVar("" + i + " - " + j, 0) :
                        model.intVar("" + i + " - " + j, 0,1);
            }
        }
        model.sum(bombsArray,"=", nBombs).post();
    }

    private static boolean isFirstPickOrAround(int row, int col, int firstPickRow, int firstPickCol) {
        for (int offsetRow : offsets) {
            for (int offsetCol : offsets) {
                if (row == firstPickRow + offsetRow && col == firstPickCol + offsetCol) return true;
            }
        }
        return false;
    }


}
