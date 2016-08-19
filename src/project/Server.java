     /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package project;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class Server extends JFrame{
    
    class pair{
        int first, second;
        public pair(int i, int j){
            first = i;
            second = j;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
        
    };
    ArrayList<ArrayList<pair>> AdjList = new ArrayList<ArrayList<pair>> ();
    
    public void createAdjList(){
        
        ArrayList <pair> zero = new ArrayList<pair> ();
        zero.add(new pair(-1,-1));
        AdjList.add(zero);
        
        ArrayList <pair> one = new ArrayList<pair> ();
        one.add(new pair(2,3));
        one.add(new pair(4,9));
        AdjList.add(one);
       
        ArrayList <pair> two = new ArrayList<pair> ();
        two.add(new pair(1,-1));
        two.add(new pair(5,9));
        two.add(new pair(3,-1));
        AdjList.add(two);

        ArrayList <pair> three = new ArrayList<pair> ();
        three.add(new pair(2,1));
        three.add(new pair(6,9));
        AdjList.add(three);
            
        ArrayList <pair> four = new ArrayList<pair> ();
        four.add(new pair(1,-1));
        four.add(new pair(5,6));
        four.add(new pair(9,15));
        AdjList.add(four);
        
        ArrayList <pair> five = new ArrayList<pair> ();
        five.add(new pair(2,-1));
        five.add(new pair(4,-1));
        five.add(new pair(6,-1));
        five.add(new pair(9,14));
        AdjList.add(five);

        ArrayList <pair> six = new ArrayList<pair> ();
        six.add(new pair(3,-1));
        six.add(new pair(9,13));
	six.add(new pair(5,4));
        AdjList.add(six);

        ArrayList <pair> seven = new ArrayList<pair> ();
        seven.add(new pair(8,9));
        seven.add(new pair(13,19));
        seven.add(new pair(12,17));
        AdjList.add(seven);
        
        ArrayList <pair> eight = new ArrayList<pair> ();
        eight.add(new pair(7,-1));
        eight.add(new pair(13,18));
        eight.add(new pair(9,10));
        AdjList.add(eight);

        ArrayList <pair> nine = new ArrayList<pair> ();
        nine.add(new pair(4,1));
        nine.add(new pair(5,2));
        nine.add(new pair(6,3));
        nine.add(new pair(8,7));
        nine.add(new pair(13,17));
        nine.add(new pair(14,19));
        nine.add(new pair(15,21));
        nine.add(new pair(10,11));
        AdjList.add(nine);

        ArrayList <pair> ten = new ArrayList<pair> ();
        ten.add(new pair(9,8));
        ten.add(new pair(15,20));
        ten.add(new pair(11,-1));
        AdjList.add(ten);

        ArrayList <pair> eleven = new ArrayList<pair> ();
        eleven.add(new pair(10,9));
        eleven.add(new pair(15,19));
        eleven.add(new pair(16,21));
        AdjList.add(eleven);

        ArrayList <pair> twelve = new ArrayList<pair> ();
        twelve.add(new pair(7,-1));
        twelve.add(new pair(13,14));
        twelve.add(new pair(17,22));
        AdjList.add(twelve);

        ArrayList <pair> thirteen = new ArrayList<pair> ();        
        thirteen.add(new pair(7,-1));
        thirteen.add(new pair(8,-1));
        thirteen.add(new pair(9,6));
        thirteen.add(new pair(14,15));
        thirteen.add(new pair(19,25));
        thirteen.add(new pair(18,23));
        thirteen.add(new pair(17,-1));
        thirteen.add(new pair(12,-1));
        AdjList.add(thirteen);
        
        ArrayList <pair> fourteen = new ArrayList<pair> ();
        fourteen.add(new pair(9,5));
        fourteen.add(new pair(15,16));
        fourteen.add(new pair(19,24));
        fourteen.add(new pair(13,12));
        AdjList.add(fourteen);
        
        ArrayList <pair> fifteen = new ArrayList<pair> ();
        fifteen.add(new pair(9,4));
        fifteen.add(new pair(10,-1));
        fifteen.add(new pair(11,-1));
        fifteen.add(new pair(16,-1));
        fifteen.add(new pair(21,-1));
        fifteen.add(new pair(20,25));
        fifteen.add(new pair(19,23));
        fifteen.add(new pair(14,13));
        AdjList.add(fifteen);
        
        ArrayList <pair> sixteen = new ArrayList<pair> ();
        sixteen.add(new pair(11,-1));
        sixteen.add(new pair(15,14));
        sixteen.add(new pair(21,26));
        AdjList.add(sixteen);
        
        ArrayList <pair> seventeen = new ArrayList<pair> ();
        seventeen.add(new pair(12,7));
        seventeen.add(new pair(13,9));
        seventeen.add(new pair(18,19));
        seventeen.add(new pair(23,29));
        seventeen.add(new pair(22,27));
        AdjList.add(seventeen);
        
        ArrayList <pair> eighteen = new ArrayList<pair> ();
        eighteen.add(new pair(13,8));
        eighteen.add(new pair(19,20));
        eighteen.add(new pair(23,28));
        eighteen.add(new pair(17,-1));
        AdjList.add(eighteen);
        
        ArrayList <pair> nineteen = new ArrayList<pair> ();
        nineteen.add(new pair(13,7));
        nineteen.add(new pair(14,9));
        nineteen.add(new pair(15,11));
        nineteen.add(new pair(20,21));
        nineteen.add(new pair(25,31));
        nineteen.add(new pair(24,29));
        nineteen.add(new pair(23,27));
        nineteen.add(new pair(18,17));
        AdjList.add(nineteen);
        
        ArrayList <pair> twenty = new ArrayList<pair> ();
        twenty.add(new pair(15,10));
        twenty.add(new pair(21,-1));
        twenty.add(new pair(25,30));
        twenty.add(new pair(19,18));
        AdjList.add(twenty);
        
        ArrayList <pair> twentyone = new ArrayList<pair> ();
        twentyone.add(new pair(16,11));
        twentyone.add(new pair(15,9));
        twentyone.add(new pair(20,19));
        twentyone.add(new pair(25,29));
        twentyone.add(new pair(26,31));
        AdjList.add(twentyone);
        
        ArrayList <pair> twentytwo = new ArrayList<pair> ();
        twentytwo.add(new pair(17,12));
        twentytwo.add(new pair(23,24));
        twentytwo.add(new pair(27,-1));
        AdjList.add(twentytwo);
        
        ArrayList <pair> twentythree = new ArrayList<pair> ();
        twentythree.add(new pair(17,-1));
        twentythree.add(new pair(18,13));
        twentythree.add(new pair(19,15));
        twentythree.add(new pair(24,25));
        twentythree.add(new pair(29,34));
        twentythree.add(new pair(28,-1));
        twentythree.add(new pair(27,-1));
        twentythree.add(new pair(22,-1));
        AdjList.add(twentythree);
        
        ArrayList <pair> twentyfour = new ArrayList<pair> ();
        twentyfour.add(new pair(19,14));
        twentyfour.add(new pair(25,26));
        twentyfour.add(new pair(29,33));
        twentyfour.add(new pair(23,22));
        AdjList.add(twentyfour);
        
        ArrayList <pair> twentyfive = new ArrayList<pair> ();
        twentyfive.add(new pair(19,13));
        twentyfive.add(new pair(20,15));
        twentyfive.add(new pair(21,-1));
        twentyfive.add(new pair(26,-1));
        twentyfive.add(new pair(31,-1));
        twentyfive.add(new pair(30,-1));
        twentyfive.add(new pair(29,32));
        twentyfive.add(new pair(24,23));
        AdjList.add(twentyfive);
        
        ArrayList <pair> twentysix = new ArrayList<pair> ();
        twentysix.add(new pair(21,16));
        twentysix.add(new pair(25,24));
        twentysix.add(new pair(31,-1));
        AdjList.add(twentysix);
        
        ArrayList <pair> twentyseven = new ArrayList<pair> ();
        twentyseven.add(new pair(22,17));
        twentyseven.add(new pair(23,19));
        twentyseven.add(new pair(28,29));
        AdjList.add(twentyseven);
        
        ArrayList <pair> twentyeight = new ArrayList<pair> ();
        twentyeight.add(new pair(23,18));
        twentyeight.add(new pair(29,30));
        twentyeight.add(new pair(27,-1));
        AdjList.add(twentyeight);
        
        ArrayList <pair> twentynine = new ArrayList<pair> ();
        twentynine.add(new pair(28,27));
        twentynine.add(new pair(23,17));
        twentynine.add(new pair(24,19));
        twentynine.add(new pair(25,21));
        twentynine.add(new pair(30,31));
        twentynine.add(new pair(32,35));
        twentynine.add(new pair(33,36));
        twentynine.add(new pair(34,37));
        AdjList.add(twentynine);
        
        ArrayList <pair> thirty = new ArrayList<pair> ();
        thirty.add(new pair(29,28));
        thirty.add(new pair(25,20));
        thirty.add(new pair(31,-1));
        AdjList.add(thirty);
        
        ArrayList <pair> thirtyone = new ArrayList<pair> ();
        thirtyone.add(new pair(30,29));
        thirtyone.add(new pair(25,19));
        thirtyone.add(new pair(26,21));
        AdjList.add(thirtyone);
        
        ArrayList <pair> thirtytwo = new ArrayList<pair> ();
        thirtytwo.add(new pair(29,25));
        thirtytwo.add(new pair(33,34));
        thirtytwo.add(new pair(35,-1));
        AdjList.add(thirtytwo);
        
        ArrayList <pair> thirtythree = new ArrayList<pair> ();
        thirtythree.add(new pair(32,-1));
        thirtythree.add(new pair(29,24));
        thirtythree.add(new pair(34,-1));
        thirtythree.add(new pair(36,-1));
        AdjList.add(thirtythree);
        
        ArrayList <pair> thirtyfour = new ArrayList<pair> ();
        thirtyfour.add(new pair(29,23));
        thirtyfour.add(new pair(33,32));
        thirtyfour.add(new pair(37,-1));
        AdjList.add(thirtyfour);
        
        ArrayList <pair> thirtyfive = new ArrayList<pair> ();
        thirtyfive.add(new pair(32,29));
        thirtyfive.add(new pair(36,37));
        AdjList.add(thirtyfive);
        
        ArrayList <pair> thirtysix = new ArrayList<pair> ();
        thirtysix.add(new pair(35,-1));
        thirtysix.add(new pair(33,29));
        thirtysix.add(new pair(37,-1));
        AdjList.add(thirtysix);
        
        ArrayList <pair> thirtyseven = new ArrayList<pair> ();
        thirtyseven.add(new pair(36,35));
        thirtyseven.add(new pair(34,29));
        AdjList.add(thirtyseven);
    }
    
    private int totalGameNum=0;
    
    private JTextArea outputArea;
    private Player[] players;
    private ServerSocket server;
   
    
    private ExecutorService runGame;
   
    public int onlinePlayers[]=new int[500];
    public int onlinePlayerNum=0;
    
    public Server(){
        
        super("SHOLOGUTI SERVER");
        DrawServerWindow();
        createAdjList();
        runGame=Executors.newFixedThreadPool(10);
        
        players=new Player[100];
       
        //set up serversocket
        try{
            server=new ServerSocket(11111,100);
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    
    public void DrawServerWindow(){
        outputArea=new JTextArea();
        
        outputArea.setEditable(false);
        add(outputArea,BorderLayout.CENTER);
        outputArea.setText("Server Awaiting Connection\n");
        
        setSize(400,400);
        setVisible(true);
    }
    public void execute(){
        for(int i=0;i<players.length;i++){
            try{
                players[i]=new Player(server.accept(),i);
                runGame.execute(players[i]);
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        
    }//end of method execute
    
    public void displayMessage(final String Message){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        outputArea.append(Message);
                    }
                }
        
        );
        
    }
    
    public int findPlayer(String name)
    {
        for(int i=0;i<onlinePlayerNum;i++){
            if (players[onlinePlayers[i]].getPlayerName().equals(name))
                return onlinePlayers[i];
            
        }
        return -1;
    }
    
    private class Player implements Runnable{
        private String thisPlayerName;
        private int thisPlayerId;
        private String ImageOfPlayer;
        
        private Socket connection;
        private Scanner input;
        private Formatter output;
        
        int opponentId=-1;
        int gameNum;
        private int eatThisGuti;
        
        int board[]=new int[40];
        
        final String[] gutiImage={"resources\\18.png",
                                  "resources\\12.png"};

        public int getOpponentId() {
            return opponentId;
        }
        

        public String getPlayerName() {
            return thisPlayerName;
        }
        public Formatter getOutput() {
            return output;
        }
        
        public Player(Socket socket , int id){
            thisPlayerId=id;
            connection=socket;
            
            ImageOfPlayer="sss";

            //obtain stream from server via socket
            try{
                input=new Scanner(connection.getInputStream());
                output=new Formatter(connection.getOutputStream());       
            }
            catch (IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        void reset(){
            opponentId=-1;
            gameNum=-1;
            eatThisGuti=-1;
        }
        //inform this player that other player has moved form location1 to location2

        public void OtherPlayerMoved(int location1,int location2,int eat){
            
            board[mod(location2-38)]=board[mod(location1-38)];
            board[mod(location1-38)]=-1;
            
            if (eat!=-1) board[mod(eat-38)]=-1;
            
            output.format("Opponent Moved.\n");
            output.format("%d\n",location1);
            output.format("%d\n",location2);
            output.format("%d\n",eat);
         
            output.flush();
        }
        
        void boardInitialize(){
            for(int i=1;i<=37;i++) board[i]=-1;
            for(int i=1;i<=16;i++) board[i]=opponentId;
            for(int i=22;i<=37;i++) board[i]=thisPlayerId;
        }
        
        void thisPlayerOnline()
        {
            for(int i=0;i<onlinePlayerNum;i++){
                if (onlinePlayers[i]!=thisPlayerId){
                    Formatter temp=players[onlinePlayers[i]].getOutput();
                    
                    
                    output.format("Add To Chat\n");
                    output.format(String.format("%s\n", players[onlinePlayers[i]].getPlayerName()));
                    output.flush();
                    
                    temp.format("Add To Chat\n");
                    temp.format(String.format("%s\n",this.getPlayerName()));
                    temp.flush();
                }
            }
        }
        
        void thisPlyerOffline(){
            for(int i=0;i<onlinePlayerNum;i++){
                if (onlinePlayers[i]!=thisPlayerId){
                    Formatter temp=players[onlinePlayers[i]].getOutput();
                    
                    temp.format("offline\n");
                    temp.format(String.format("%s\n",this.getPlayerName()));
                    temp.flush();
                }
            }
        }
        
        void letKnow(int id,int game,String color,String name){
            opponentId=id;
            gameNum=game;
            
            output.format("Accepted\n");
            output.format("%d\n",gameNum);
            output.format("%d\n",opponentId);
            output.format("%s\n",name); // opponent name
            output.format("%s\n",color); //this player color
            output.flush();
            boardInitialize();
            //startGame();
        }
        
        public void run() {
            onlinePlayers[onlinePlayerNum++]=thisPlayerId;
            thisPlayerName=input.nextLine();
            
            thisPlayerOnline();
            
            while(true){
                if (input.hasNext()){
                    processMessage(input.nextLine());
                }
            }
        }
        
        
        public void processMessage(String msg){
            if (msg.equals("I Wanna Play")){
                String name=input.nextLine(); // opponents name
                int color=input.nextInt();    // opponents color
                int playerId=findPlayer(name); // opponents id
                
                if (playerId!=-1 && players[playerId].getOpponentId() !=-1){
                    output.format("opponent is busy\n");
                    output.format("%s\n",name);
                    output.flush();
                }
                
                else{
                    Formatter temp=players[playerId].getOutput();
                    temp.format("Wanna Play?\n");
                    temp.format("%s\n",thisPlayerName);
                    temp.format("%d\n",thisPlayerId);
                    temp.format("%d\n",color);
                    temp.format("%d\n",totalGameNum+1);
                    temp.flush();
                }
            }
            
            else if (msg.equals("Yes")){ // this player agree to play
                
                String color=input.nextLine();
                int id=input.nextInt();
                
                opponentId=id;
                gameNum=++totalGameNum;
                // let other player know that this player accepts his request
                
                players[id].letKnow(thisPlayerId,gameNum,color,thisPlayerName); 
                boardInitialize();
                
               // startGame();
            }
             
            else if (msg.equals("closed")){
                if (opponentId!=-1){
                    players[opponentId].opponentId=-1;
                    players[opponentId].eatThisGuti=-1;
                    players[opponentId].gameNum=-1;
                    
                    Formatter temp=players[opponentId].getOutput();
                    temp.format("opponent left\n");
                    temp.flush();
                }
                thisPlyerOffline();
                for(int i=0;i<onlinePlayerNum;i++){
                    if (onlinePlayers[i]==thisPlayerId){
                        for(int j=i+1;j<onlinePlayerNum;j++)
                            onlinePlayers[j-1]=onlinePlayers[j];
                        break;
                    }
                }
                onlinePlayerNum--;
                
                try {
                    connection.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else if (msg.equals("My Message")){
                String message=input.nextLine();

                Formatter temp=players[opponentId].getOutput();
                temp.format("Message\n");
                temp.format(message+"\n");
                temp.flush();

            }
            else if (msg.equals("quit")){
                Formatter temp=players[opponentId].getOutput();
                players[opponentId].opponentId=-1;
                players[opponentId].eatThisGuti=-1;
                players[opponentId].gameNum=-1;
                
                temp.format("opponent quit\n");
                temp.flush();
                
                opponentId=-1;
                gameNum=-1;
                eatThisGuti=-1;
            }
            else if (msg.equals("A Chal"))
            {
                int location1=input.nextInt();
                int location2=input.nextInt();
                int prevState=input.nextInt();

                if (validateAndMove(location1,location2,prevState)){
                    checkGameState();
                    
                    output.format("Valid Move.\n");
                    output.format("%d\n",eatThisGuti);
                    output.flush();
                    
                }
                else if (prevState==-1){
                    output.format("Invalid Move.Try Again.\n");
                    output.flush();
                }
                else {

                    Formatter temp=players[opponentId].getOutput();

                    output.format("Invalid Move.\n");
                    output.flush();

                    temp.format("Your Turn\n");
                    temp.flush();
                }
                //state=GameOver();
            }
            
            else if (msg.equals("Your Turn")){
                Formatter temp=players[opponentId].getOutput();
                temp.format(msg+"\n");
                temp.flush();
            }
            else if (msg.equals("Draw Offer"))
            {
                Formatter temp=players[opponentId].getOutput();
                temp.format(msg+"\n");
                temp.flush();
            }
            else if (msg.equals("Draw Accepted"))
            {
                Formatter temp=players[opponentId].getOutput();
                temp.format(msg+"\n");
                temp.flush();
                
                players[opponentId].reset();
                reset();
            }
            else if (msg.equals("Draw Canceled"))
            {
                Formatter temp=players[opponentId].getOutput();
                temp.format(msg+"\n");
                temp.flush();
            }
        }
        
        public void startGame(){
            boardInitialize();
            int state=GameOver();
            
            while(state==0){
           
                int location1=0,location2=0;
                
                if (input.hasNext()){
                    String msg=input.nextLine();
                    
                    if (msg.equals("My Message")){
                        String message=input.nextLine();
                        
                        Formatter temp=players[opponentId].getOutput();
                        temp.format("Message\n");
                        temp.format(message+"\n");
                        temp.flush();
                        
                        state=GameOver();
                        continue;
                    }
                    
                    else if (msg.equals("A Chal"))
                    {
                        location1=input.nextInt();
                        location2=input.nextInt();
                        int prevState=input.nextInt();
                        
                        if (validateAndMove(location1,location2,prevState)){
                           
                            output.format("Valid Move.\n");
                            output.format("%d\n",eatThisGuti);
                            output.flush();
                        }
                        else if (prevState==-1){
                            output.format("Invalid Move.Try Again.\n");
                            output.flush();
                        }
                        else {
                            
                            Formatter temp=players[opponentId].getOutput();
                            
                            output.format("Invalid Move.\n");
                            output.flush();
                            
                            temp.format("Your Turn\n");
                            temp.flush();
                        }
                        state=GameOver();
                    }
                    else if (msg.equals("Draw Offer"))
                    {
                        int otherPlayer=(thisPlayerId+1)%2;
                        Formatter temp=players[otherPlayer].getOutput();
                        temp.format(msg+"\n");
                        temp.flush();
                    }
                    else if (msg.equals("Draw Accepted"))
                    {
                        int otherPlayer=(thisPlayerId+1)%2;
                        Formatter temp=players[otherPlayer].getOutput();
                        temp.format(msg+"\n");
                        temp.flush();
                    }
                    else if (msg.equals("Draw Canceled"))
                    {
                        int otherPlayer=(thisPlayerId+1)%2;
                        Formatter temp=players[otherPlayer].getOutput();
                        temp.format(msg+"\n");
                        temp.flush();
                    }
                    else if (msg.equals("Your Turn")){
                        int otherPlayer=(thisPlayerId+1)%2;
                        Formatter temp=players[otherPlayer].getOutput();
                        
                        temp.format(msg+"\n");
                        temp.flush();
                    }
                }
            }
            
        }
        
        
        public boolean validateAndMove(int x,int y,int gutiEaten){  //gutiEaten means previous chal

            eatThisGuti=-1;
            //System.out.printf("%d %d\n",x,y);
            if (x!=-1 && y!=-1 && !isOccupied(y)){
               for(int i=0;i<AdjList.get(x).size();i++){
                   pair p=AdjList.get(x).get(i);
                   if (p.getFirst()==y && gutiEaten==-1) {

                       board[y]=board[x];
                       board[x]=-1;

                       players[opponentId].OtherPlayerMoved(x, y,eatThisGuti);
                       
                       return true;
                   }

                   else if (p.getFirst()>=1 && board[p.getFirst()]==opponentId && y==p.getSecond()) 
                   {
                       eatThisGuti=p.getFirst();
                       players[opponentId].OtherPlayerMoved(x, y,eatThisGuti);
                       board[y]=board[x];
                       board[x]=-1;
                       board[eatThisGuti]=-1;
                       return true;
                   }
                }
            }
            return false;
        } // end of validateAndMove method
    
        
        void checkGameState()
        {
            int state=GameOver();
            if (state==2){
                Formatter temp=players[opponentId].getOutput();
                temp.format("You Win\n");
                temp.flush();
                
                
                output.format("You Loose\n");
                output.flush();
                players[opponentId].reset();
                reset();
                
            }
            else if (state==1){
                Formatter temp=players[opponentId].getOutput();
                
                output.format("You Win\n");
                output.flush();
                
                temp.format("You Loose\n");
                temp.flush();
                
                players[opponentId].reset();
                reset();
                
            }
            
        }
        
        public int GameOver(){
            int counter1=0,counter2=0;
            for(int i=1;i<=37;i++){
                if (board[i]==thisPlayerId) counter1++;
                else if (board[i]==opponentId) counter2++;
            }
            if (counter1==0) return 2;
            else if (counter2==0) return 1;
            return 0;
        }
        
        public boolean isOccupied(int location){
            if (board[location]==-1) return false;
            return true;
        }
       
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }

        return true;
    }
    
    int mod(int a){
        return (a<0)?-a:a;
    }
    
    public static void main(String args[]){
        Server apps=new Server();
        apps.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        apps.execute();
    }
}
