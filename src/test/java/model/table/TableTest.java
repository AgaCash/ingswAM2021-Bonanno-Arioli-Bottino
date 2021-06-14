package model.table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void sendQuartet() {

        Table table = new Table();

        System.out.println(table.sendQuartet()+"_____________________\n");
        System.out.println(table.sendQuartet()+"_____________________\n");
        System.out.println(table.sendQuartet()+"_____________________\n");
        System.out.println(table.sendQuartet()+"_____________________\n");

    }
}