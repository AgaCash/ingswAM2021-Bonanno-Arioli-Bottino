package GUI;

import java.util.ArrayList;

public class Drawer {
    Gui gui;
    public Drawer(Gui gui){
        this.gui = gui;
    }
    public void drawMarket (){};
    public void drawPlayerBoard (){};
    public void drawLeader (){};
    public void drawDevBoard (){};
    public void drawFaith(int pos){
        String basePath = "file:src/main/resources";
        double gap = 29.0;
        if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+(pos*gap),75.0,50,50);
        else if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+(2*gap),75-((pos-2)*gap),50,50);
        else if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+((pos-2)*gap),75.0-(2*gap),50,50);
        else if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+(7*gap),75-((11%pos)*gap),50,50);
        else if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+((pos-4)*gap),75.0,50,50);
        else if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+(12*gap),75-((pos-16)*gap),50,50);
        else if(pos>=1 && pos<=2)
            gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/redCross.png"),15.0+((pos-16)*gap),75-(2*gap),50,50);
    }
}
