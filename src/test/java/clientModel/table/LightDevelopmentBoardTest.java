package clientModel.table;

import model.table.DevelopmentBoard;
import org.junit.jupiter.api.Test;

class LightDevelopmentBoardTest {

    @Test
    void testToString() {
        DevelopmentBoard b = new DevelopmentBoard();
        LightDevelopmentBoard board = b.convert();

        System.out.println(board.toString());

    }
}