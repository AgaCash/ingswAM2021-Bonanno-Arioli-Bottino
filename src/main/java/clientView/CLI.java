package clientView;

/*public class CLI implements View{
    private Scanner in;
    private PrintStream out;
    LightController controller;

    public CLI(){
        this.in = new Scanner(System.in);
        this.out = System.out;
        this.controller = new LightController();
    }

    public void askServerInfo(){
        out.println("IP: ");
        String ip = in.nextLine();
        out.println("Port: ");
        int port = in.nextInt();
        //controller.connectToServer(ip, port);
        askUsername();
    }

    public void askUsername(){
        out.println("Username: ");
        String username = in.nextLine();
        //controller.setUsername(username);
        askMenu();
    }

    public void askMenu() {
        boolean stop = false;
        while (!stop) {
            out.println("Scegli cosa vuoi fare \n1- Singleplayer \n2- Join Multiplayer Lobby" +
                    " \n3- Create Multiplayer Lobby");
            int choice = in.nextInt();
            switch (choice) {
                case 1: {
                    handleSinglePlayer();
                    stop = true;
                    break;
                }
                case 2: {
                    handleMultiJoin();
                    stop = true;
                    break;
                }
                case 3: {
                    handleMultiCreate();
                    stop = true;
                    break;
                }
                default: {
                    out.println("CaggFa? invalid choice ");
                }
            }
        }
    }

    public void handleSinglePlayer(){

    }

    public void handleMultiJoin(){

    }

    public void handleMultiCreate(){
        //controller.createLobby();
                    /* public void createLobby(){
                    *           manda un msg
                    *           riceve msg
                    *           view.showError
                    *              view.waitingLobby
    }

    public void notifyJoin(){

    }
}*/
