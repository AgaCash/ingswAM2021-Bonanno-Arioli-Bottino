package view;

import java.io.PrintWriter;

public class VirtualView implements View{
    private PrintWriter out;
    private String username;

    public VirtualView(PrintWriter out, String username) {
        this.out = out;
        this.username = username;
    }
}
