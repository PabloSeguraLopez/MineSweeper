package software.eii.ulpgc.psl.buscaminas;

public interface SlotObserver {
    void changed(int row, int col, boolean hasBeenFlagged);
}
