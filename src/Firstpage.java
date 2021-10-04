import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Firstpage extends JFrame implements ActionListener,MouseListener {
    private JButton startbutton = new JButton();
    private JFrame frame = new JFrame();
    private JLayeredPane layeredPane = new JLayeredPane();
    private JLabel LOGO,backgournd2 ;
    //page2
    private JButton button2 = new JButton();
    private JButton button3 = new JButton();
    private JButton button4 = new JButton();
    //page 4
    private JLayeredPane layeredPane2;
    private JLabel background3, Flag, trophey, winJLabel, color, score ,winner,score1,score10,score100,score1k,score10k,score100k;
    private ImageIcon BG, flagimg, tropheyimg, winimg, red, green, yellow, scoreimg,winnerimg;
    private int numberPlayer ;
    private Process process;
    final int PANEL_WIDTH =  1550;
	final int PANEL_HEIGHT = 800;
    private playmusic pm;
    JLayeredPane layeredPane4 = new JLayeredPane();

    public Firstpage() {
        pm = new playmusic();
        
        
        initComponents();
    }

    public void setPlayers(int N) {
        ArrayList playerArray = new ArrayList();
        Boss boss = new Boss();
        for(int i=0; i<N ; i++) {
            Player p = new Player(i);
            playerArray.add(p);
        }
        this.process = new Process(playerArray);
        this.process.setBoss(boss);
    }

    private void initComponents(){
        //ALLImage
        pm.playmusic("./src/sound/BGSTART.wav");
        ImageIcon startbuttIcon = new ImageIcon("./src/images/play.png");
        ImageIcon backgroundimg = new ImageIcon("./src/images/BG1.gif");
        ImageIcon logoimg = new ImageIcon("./src/images/logo1.png");

        
        
        //ALLLabel
        backgournd2 = new JLabel();
        backgournd2.setIcon(backgroundimg);
        backgournd2.setSize(1920, 900);
        backgournd2.setLayout(null);

        LOGO = new JLabel();
        LOGO.setIcon(logoimg);
        LOGO.setBounds(480, 0, 600, 600);
        LOGO.setLayout(null);
        
        //ALLbutton
        startbutton.setBounds(680, 500, 0, 0);
        startbutton.setSize(200, 100);
        startbutton.setIcon(startbuttIcon); 
        startbutton.setFocusable(false);
        startbutton.addMouseListener(this);
        
        //layerPane
        layeredPane.add(LOGO);
        layeredPane.add(startbutton);
        layeredPane.add(backgournd2);
        
        //frame
        frame.setTitle("THE HUNTED");
        frame.add(layeredPane);
        frame.setSize(1550, 850);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(logoimg.getImage()); //change icon of frame
        this.pack();
		this.setLocationRelativeTo(null);
    }

    private void initComponents2(){
        
        ImageIcon backgroundimg = new ImageIcon("./src/images/BG1.gif");
        ImageIcon player2buttIcon = new ImageIcon("./src/images/2playerbutton.png");
        ImageIcon player3buttIcon = new ImageIcon("./src/images/3playerbutton.png");
        ImageIcon player4buttIcon = new ImageIcon("./src/images/4playerbutton.png");
        
        JLabel backgournd2 = new JLabel();
        backgournd2.setIcon(backgroundimg);
        backgournd2.setSize(1920, 900);
        backgournd2.setLayout(null);

        //ALLbutton
        button2.setBounds(620, 300, 0, 0); // 2 player
        button2.setSize(300, 100);
        button2.setIcon(player2buttIcon); 
        button2.setFocusable(false);
        button2.addMouseListener(this);

        button3.setBounds(620, 450, 0, 0); // 3 player
        button3.setSize(300, 100);
        button3.setIcon(player3buttIcon);
        button3.setFocusable(false);
        button3.addMouseListener(this);

        button4.setBounds(620, 600, 0, 0); //4 player
        button4.setSize(300, 100);
        button4.setIcon(player4buttIcon); 
        button4.setFocusable(false);
        button4.addMouseListener(this);

        //layerpane
        layeredPane2 = new JLayeredPane();
        layeredPane2.add(button2); 
        layeredPane2.add(button3);
        layeredPane2.add(button4);
        layeredPane2.add(backgournd2);

        frame.add(layeredPane2);
    }

   

    private void initComponents3(){
        
        frame.add(this.process);
        process.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==startbutton){
            layeredPane.setVisible(false);
            pm.playmusic("./src/sound/mouse_click.wav");
            
            initComponents2();
        }
        if (e.getSource()==button2) {
            layeredPane2.setVisible(false);
            pm.playmusic("./src/sound/mouse_click.wav");
            pm.playmusic("./src/sound/two.wav");
            setPlayers(2);
           
            initComponents3();
        }
        if (e.getSource()==button3) {
            layeredPane2.setVisible(false);
            pm.playmusic("./src/sound/mouse_click.wav");
            pm.playmusic("./src/sound/three.wav");
            setPlayers(3);
            initComponents3();
        }
        if (e.getSource()==button4) {
            layeredPane2.setVisible(false);
            pm.playmusic("./src/sound/mouse_click.wav");
            pm.playmusic("./src/sound/four.wav");
            
            setPlayers(4);
            initComponents3();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub 
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    
    public int getNumberPlayer() {
        return numberPlayer;
    }
}