import com.google.gson.Gson;
import resources.Coin;
import resources.Resource;
import resources.Shield;

import java.util.ArrayList;

public class GsonTest {
    public static void main(String[] args){
        Gson gson = new Gson();
        ArrayList<Resource> r = new ArrayList<>();
        r.add(new Coin());
        r.add(new Shield());
        DevelopmentCard card = new DevelopmentCard(Colour.BLUE, 1, 1, r, r, r);
        System.out.println(gson.toJson(card));

    }
}
