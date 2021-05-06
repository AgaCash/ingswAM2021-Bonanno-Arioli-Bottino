package controller;

import model.resources.Resource;

import java.util.ArrayList;

public class Controller {
    private int id;

    public Controller(int id){
        System.out.println("CONTORLLER CREATO");
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Resource> market(boolean line, int num){
        //fai il mercato
        return new ArrayList<Resource>();
    }
}
