package cards;
//this is a provisional class
public class GenericLeaderCard extends LeaderCard{

	public GenericLeaderCard(boolean en){
		whichResource=null;
		isEnabled= en;
	}
	
	public void method(){
		System.out.println("I'm a generic leader card");
	}
}