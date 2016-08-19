/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.sound.sampled.*;

/**
 *
 * @author programmer
 */
public class ClientFinal extends javax.swing.JPanel implements Runnable {

    DefaultListModel model = new DefaultListModel();
    JList onlinePlayerList = new JList(model);

    private Socket connection;
    private Scanner input;
    private Formatter output;
    private boolean myTurn;

    int opponentId = -1;
    int gameNum = -1;

    private String shologutiHost;
    private String myImage;
    private int pastLocation;
    private int presentLocation;
    private int opponentLocation1=-1;
    private int opponentLocation2=-1;
    
    
    private JLabel labelForTurn;
    private boolean firstInput, secondInput;
    private boolean highlight = false;
    private boolean otherPlayerConnected = false;

    private String myName;
    private String opponentName;
    private boolean opponentHighlight=false;

    private JLabel Player1;
    private JLabel Player2;
    private JLabel Player1Have;
    private JLabel Player2Have;

    String BoardImage = "resources\\BG.png";
    String RedImage = "resources\\18.png";
    String GreenImage = "resources\\12.png";
    String backGround = "resources\\background.JPG";
    String moveFile="resources\\button-16.wav";
    String startFile="resources\\button-2.wav";

    int gutiX[] = {0, 127, 276, 420, 186, 277, 366, 69, 169, 277, 390, 494, 68, 171, 279, 387, 495, 66,
        170, 278, 389, 493, 66, 168, 278, 391, 494, 67, 170, 276, 388, 495, 187, 277, 359, 125, 276, 420};

    int gutiY[] = {0, 66, 65, 65, 111, 109, 109, 179, 179, 178, 177, 178, 262, 262, 260, 261, 261, 346,
        345, 346, 345, 345, 432, 430, 431, 430, 432, 521, 517, 518, 518, 519, 586, 586, 586, 635, 635, 634};

    Image Red, Green, MainBoard, BackGround;
    Image y;
    Image o;
    
    File moveMusic;
    File startMusic;
    
    int counter = 0;

    int board[] = new int[38];
    private int thisPlayer;
    private int eatThisGuti = -1;

    public ClientFinal(String host, String name) {
        shologutiHost = host;
        myName = name;
        System.out.println(name);

        initComponents();
        startClient();

        showChatList();

        Player1 = new javax.swing.JLabel();
        Player2 = new javax.swing.JLabel();
        Player1Have = new javax.swing.JLabel();
        Player2Have = new javax.swing.JLabel();
        labelForTurn = new javax.swing.JLabel();

        try {
            Red = ImageIO.read(new File(RedImage));
            Green = ImageIO.read(new File(GreenImage));
            MainBoard = ImageIO.read(new File(BoardImage));
            BackGround = ImageIO.read(new File(backGround));

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        repaint();
    }

    void initialize() {
        
        moveMusic=new File(moveFile);
        startMusic=new File(startFile);
        
        firstInput = false;
        secondInput = false;

        for (int i = 0; i < 38; i++) {
            board[i] = 0;
        }

        for (int i = 1; i <= 16; i++) {
            if (thisPlayer == 1) {
                board[i] = 2;
            } else {
                board[i] = 1;
            }
        }
        for (int i = 22; i <= 37; i++) {
            board[i] = thisPlayer;
        }

    }

    private void showChatList() {

        onlinePlayerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        onlinePlayerList.setBounds(1070, 43, 150, 280);
        add(onlinePlayerList);

        onlinePlayerList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                //want to play with this player
                if (onlinePlayerList.getSelectedIndex()!=-1){
                    
                
                    if (opponentId == -1) {

                        String str = (String) onlinePlayerList.getSelectedValue();

                        int color = chooseColor();

                        output.format("I Wanna Play\n");
                        output.format("%s\n", str); //opponent

                        if (color == 1) {
                            output.format("1\n"); //1 for green
                        } else {
                            output.format("0\n"); //0 for red
                        }
                        output.flush();
                    } 
                    else {
                        showMessage("Sorry! You Can't play two games at a time\n");
                    }
                }
                else showMessage("Sorry! No player Online\n. please wait for players\n");
            }
        });

    }

    void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    int chooseColor() {
        int select = JOptionPane.showConfirmDialog(this, "Select Yes for green No for Red", "Color Chooser", JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            return 1;
        }
        return 0;
    }

    public void DrawBoard(Graphics g) {

        g.drawImage(MainBoard, 20, 10, 830, 680, null);
        for (int i = 1; i < 38; i++) {
            if (board[i] == 1) {
                g.drawImage(Red, gutiX[i] - 12, gutiY[i] - 12, 24, 24, null);
            } else if (board[i] == 2) {
                g.drawImage(Green, gutiX[i] - 12, gutiY[i] - 12, 24, 24, null);
            }
        }

        Player1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        Player1.setText(String.format("%s", opponentName));
        Player1.setBounds(580, 50, 190, 40);

        Player2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        Player2.setText(String.format("%s", myName));
        Player2.setBounds(580, 340, 190, 40);

        int counter1 = 0, counter2 = 0;
        for (int i = 1; i <= 37; i++) {
            if (board[i] == 1) {
                counter1++;
            } else if (board[i] == 2) {
                counter2++;
            }
        }
        String str1 = String.format("%d", counter1);
        String str2 = String.format("%d", counter2);

        Player1Have.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        Player1Have.setText(str1);
        Player1Have.setBounds(630, 100, 100, 44);

        Player2Have.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18
        Player2Have.setText(str2);
        Player2Have.setBounds(630, 370, 62, 90);

        add(Player1);
        add(Player2);
        add(Player1Have);
        add(Player2Have);
        setLabelForTurn();
        //if (myTurn) 
        //g.drawImage(y,570,560,167,38,null);
        //else 

        //  g.drawImage(o,570,560,230,33,null);
    }

    //@Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (opponentId != -1) {

            DrawBoard(g);

            if (highlight) {
                
                if (pastLocation >= 1 && pastLocation <= 37) {
                    g.draw3DRect(gutiX[pastLocation] - 24, gutiY[pastLocation] - 24, 48, 48, true);
                }
            }
            
            if (opponentHighlight)
            {
                if (opponentLocation1>=1 && opponentLocation1<=37){
                    g.drawOval(gutiX[opponentLocation1] - 24, gutiY[opponentLocation1] - 24, 48, 48);
                }
                
                if (opponentLocation2>=1 && opponentLocation2<=37){
                    g.drawOval(gutiX[opponentLocation2] - 24, gutiY[opponentLocation2] - 24, 48, 48);
                }
            }
        } else {
            g.drawImage(BackGround, 20, 10, 830, 680, null);
            Player1.setText("");
            Player2.setText("");
            Player1Have.setText("");
            Player2Have.setText("");
            labelForTurn.setText("");
        }
    }

    public void MoveGuti(int location1, int location2, int eatThisGuti) {
        System.out.printf("%dxx%d\n", board[location1], board[location2]);
        board[location2] = board[location1];
        board[location1] = 0;
        if (eatThisGuti != -1) {
            board[eatThisGuti] = 0;
        }
        
        highlight = false;
        
        
        repaint();
    }

    public void startClient() {
        try {
            connection = new Socket(InetAddress.getByName(shologutiHost), 11111);
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this);
    }
    
    public void playSound(File file)
    {
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch (UnsupportedAudioFileException e) {
          e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            e.printStackTrace();
         }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        drawButton = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel2 = new javax.swing.JLabel();
        quit = new javax.swing.JButton();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(null);

        textField.setEditable(false);
        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldActionPerformed(evt);
            }
        });
        add(textField);
        textField.setBounds(850, 330, 190, 50);

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane2.setViewportView(textArea);

        add(jScrollPane2);
        jScrollPane2.setBounds(846, 40, 190, 280);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CHAT BOX");
        add(jLabel1);
        jLabel1.setBounds(900, 20, 160, 14);

        drawButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        drawButton.setText("OFFER DRAW");
        drawButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawButtonMouseClicked(evt);
            }
        });
        add(drawButton);
        drawButton.setBounds(890, 400, 120, 40);
        add(jScrollBar1);
        jScrollBar1.setBounds(1210, 40, 17, 280);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("ONLINE PLAYERS");
        add(jLabel2);
        jLabel2.setBounds(1080, 20, 140, 20);

        quit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        quit.setText("QUIT GAME");
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitActionPerformed(evt);
            }
        });
        add(quit);
        quit.setBounds(890, 453, 120, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        if (opponentId != -1) {

            int ClickedNode = -1;
            System.out.printf("%d %d\n", evt.getX(), evt.getY());
            
            for (int i = 1; i < 38; i++) {
                int topLeftX = gutiX[i] - 12;
                int topLeftY = gutiY[i] - 12;
                int X = evt.getX();
                int Y = evt.getY();

                if ((X >= topLeftX && X <= topLeftX + 24) && (Y >= topLeftY && Y <= topLeftY + 24)) {
                    ClickedNode = i;
                    break;
                }
            }

            if (myTurn) {
                if (!firstInput && ClickedNode != -1 && board[ClickedNode] == thisPlayer) {
                    pastLocation = ClickedNode;
                    firstInput = true;
                    //if (pastLocation>=1 && pastLocation<=16){
                    highlight = true;
                    
                    repaint();
                    //}
                } else {
                    if (ClickedNode == -1) {
                        firstInput = false;
                        highlight = false;
                        repaint();
                    } else {

                        presentLocation = ClickedNode;

                        firstInput = false;

                        sendToServer(pastLocation, presentLocation);
                    }
                    //System.out.printf("past: %d present: %d\n",pastLocation,presentLocation);
                }

            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldActionPerformed
        // TODO add your handling code here:

        String msg = evt.getActionCommand();
        
        textArea.append(myName + " >> " + msg + "\n");
        textField.setText("");

        output.format("My Message\n");
        output.format(msg + "\n");
        output.flush();

    }//GEN-LAST:event_textFieldActionPerformed

    private void drawButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawButtonMouseClicked
        // TODO add your handling code here:
        if (opponentId != -1) {
            output.format("Draw Offer\n");
            output.flush();
        } else {
            showMessage("No Game Loaded!!");
        }
    }//GEN-LAST:event_drawButtonMouseClicked

    private void quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitActionPerformed
        // TODO add your handling code here:

        if (opponentId != -1) {
            output.format("quit\n");
            output.flush();

            opponentId = -1;
            gameNum = -1;
            myTurn = false;
            eatThisGuti = -1;
            textField.setEditable(false);
            repaint();
        } else {
            showMessage("No Game Loaded!!");
        }
    }//GEN-LAST:event_quitActionPerformed

    public void sendToServer(int location1, int location2) {
        if (myTurn) {
            output.format("A Chal\n");
            output.format("%d\n", location1);
            output.format("%d\n", location2);
            System.out.printf("%d send it %d %d\n", pastLocation, presentLocation, eatThisGuti);
            output.format("%d\n", eatThisGuti);
            output.flush();
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginWindow login = new LoginWindow();
                login.setVisible(true);
                login.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton drawButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton quit;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables

    public void setLabelForTurn() {
        // labelForTurn = new javax.swing.JLabel();

        labelForTurn.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelForTurn.setText("");
        if (myTurn) {
            labelForTurn.setText("YOUR TURN");
        } else {
            labelForTurn.setFont(new java.awt.Font("Tahoma", 2, 20));
            labelForTurn.setText("OPPONENTS TURN");
        }

        labelForTurn.setBounds(570 - 20, 530 + 10, 230, 70);
        add(labelForTurn);
    }

    @Override
    public void run() {
        output.format(String.format("%s\n", myName));
        output.flush();

        while (true) {
            
            if (input.hasNext()) {
                processMessage(input.nextLine());
            }
        }
    }

    public void processMessage(String message) {
        if (message.equals("Add To Chat")) {
            String name = input.nextLine();
            System.out.println(name + "  add it");
            model.addElement(name);
        } else if (message.equals("offline")) {
            String str = input.nextLine();
            model.removeElement(str);
        }
        else if (message.equals("opponent quit")) {
            JOptionPane.showMessageDialog(this, String.format("%s quit the game.You Win!", opponentName));

            opponentId = -1;
            gameNum = -1;
            myTurn = false;
            eatThisGuti = -1;
            textField.setEditable(false);
            repaint();
        } else if (message.equals("opponent left")) {
            JOptionPane.showMessageDialog(this, message + "\n You Win!");

            opponentId = -1;
            gameNum = -1;
            myTurn = false;
            eatThisGuti = -1;
            textField.setEditable(false);
            repaint();
        } else if (message.equals("opponent is busy")) {
            String opName = input.nextLine();
            JOptionPane.showMessageDialog(this, String.format("Sorry!! %s is playing another game", opName));
        } else if (message.equals("Wanna Play?") && opponentId==-1) {
            String name = input.nextLine(); //opponents name
            int id = input.nextInt();  //opponents id
            int c = input.nextInt();
            int game = input.nextInt();

            String color;  //opponents color

            if (c == 1) {
                color = "Green";
            } else {
                color = "Red";
            }

            String msg = String.format("%s wants to play with you tooking %s color", name, color);
            int select = JOptionPane.showConfirmDialog(this, msg, null, JOptionPane.YES_NO_OPTION);

            if (select == JOptionPane.YES_OPTION) {
                opponentId = id;
                opponentName = name;
                gameNum = game;

                if (color.equals("Green")) {
                    myImage = RedImage;
                    thisPlayer = 1;
                    myTurn = false;
                } else {
                    myImage = GreenImage;
                    thisPlayer = 2;
                    myTurn = true;
                }

                output.format("Yes\n"); // this player agree to play
                output.format("%s\n", color);
                output.format("%d\n", id);

                output.flush();
                initialize();
                textField.setEditable(true);
                repaint();
            }
        } else if (message.equals("Accepted")) {
            int game = input.nextInt();
            int id = input.nextInt();
            input.nextLine();

            opponentName = input.nextLine();
            String color = input.nextLine(); // this player image

            gameNum = game;
            opponentId = id;

            if (color.equals("Red")) {
                myTurn = false;
                myImage = RedImage;
                thisPlayer = 1;
            } else {
                myTurn = true;
                myImage = GreenImage;
                thisPlayer = 2;
            }

            initialize();
            textField.setEditable(true);
            playSound(startMusic);
            
            repaint();
        } 
        
        else if (message.equals("Valid Move.")) {
            System.out.println(message);
            eatThisGuti = input.nextInt();
            opponentHighlight=false;
            
            MoveGuti(pastLocation, presentLocation, eatThisGuti);
            if (eatThisGuti != -1) {
                int result = JOptionPane.showConfirmDialog(this, "Eat another guti??", "SELECT TURN", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                } else {
                    myTurn = false;
                    eatThisGuti = -1;
                    
                    output.format("Your Turn\n");
                    output.flush();
                    repaint();
                }
            } else {
                myTurn = false;
                repaint();
            }
        } else if (message.equals("Invalid Move.Try Again.")) {
            System.out.println(message);
            myTurn = true;
            highlight = false;
            
            repaint();
        } else if (message.equals("Invalid Move.")) {
            System.out.println(message);
            
            myTurn = false;
            highlight = false;
            eatThisGuti = -1;
            
            showMessage("Invalid Move . You've lost your turn!");
            repaint();
        } else if (message.equals("Your Turn")) {
            myTurn = true;
            highlight = false;
            eatThisGuti=-1;
            
            repaint();
        } else if (message.equals("Opponent Moved.")) {
            int location1 = input.nextInt();
            int location2 = input.nextInt();
            int gutiEaten = input.nextInt();
            
            opponentHighlight=true;
            opponentLocation1=mod(location1-38);
            opponentLocation2=mod(location2-38);
            
            if (gutiEaten == -1) {
                myTurn = true;
            } else {
                gutiEaten = mod(gutiEaten - 38);
            }

            MoveGuti(mod(location1 - 38), mod(location2 - 38), gutiEaten);
            playSound(moveMusic);
            
            //System.out.println(message+".Your turn");
        } else if (message.equals("You Win")) {
            JOptionPane.showMessageDialog(this, "Game Over!!\n You WIN!!\n");

            opponentId = -1;
            gameNum = -1;
            myTurn = false;
            eatThisGuti = -1;
            textField.setEditable(false);
            repaint();
        } else if (message.equals("You Loose")) {
            JOptionPane.showMessageDialog(this, "Game Over!!\n You LOOSE!!\n");

            opponentId = -1;
            gameNum = -1;
            myTurn = false;
            eatThisGuti = -1;
            textField.setEditable(false);
            repaint();
        } else if (message.equals("Message")) {
            String msg = input.nextLine();

            textArea.append(opponentName + " >> " + msg + "\n");
        } else if (message.equals("Draw Offer")) {
            int select = JOptionPane.showConfirmDialog(this, String.format("%s Offers Draw.Accept it??", opponentName), "Draw Offer", JOptionPane.YES_NO_OPTION);

            if (select == JOptionPane.YES_OPTION) {
                output.format("Draw Accepted\n");
                output.flush();

                opponentId = -1;
                gameNum = -1;
                myTurn = false;
                eatThisGuti = -1;
                textField.setEditable(false);
                repaint();
            } else {
                output.format("Draw Canceled\n");
                output.flush();
            }
        } else if (message.equals("Draw Accepted")) {
            JOptionPane.showMessageDialog(this, "Draw Offer Accepted!");

            opponentId = -1;
            gameNum = -1;
            myTurn = false;
            eatThisGuti = -1;
            textField.setEditable(false);
            repaint();
        } else if (message.equals("Draw Canceled")) {
            JOptionPane.showMessageDialog(this, String.format("Sorry !! %s rejects Draw offer!", opponentName));
        }
    }

    int mod(int a) {
        return (a < 0) ? -a : a;
    }

    Formatter getOutput() {
        return output;
    }
}
