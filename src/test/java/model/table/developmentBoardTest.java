package model.table;

import exceptions.EmptyDeckException;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class developmentBoardTest {
    DevelopmentBoard d;
    @Test
    void createDevBoardTest(){
        d = new DevelopmentBoard();
        assertNotNull(d);
    }

    @Test
    void getDeckTest(){
        d = new DevelopmentBoard();
        try {
            d.getDeck(-1);
        }catch(InputMismatchException e){
            assertTrue(true);
        }
        assertNotNull(d.getDeck(1));
        assertNotNull(d.getDeck(2));
        assertNotNull(d.getDeck(5));
        assertNotNull(d.getDeck(11));
        try {
            d.getDeck(12);
        }catch(InputMismatchException e){
            assertTrue(true);
        }
        try {
            assertNotNull(Objects.requireNonNull(d.getDeck(11)).popCard());
        } catch (EmptyDeckException e) {
            e.printStackTrace();
        }

    }


}
