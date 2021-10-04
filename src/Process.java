import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.Timer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics2D;
import java.awt.Graphics; 
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Desktop;  
import java.awt.event.KeyListener;

public class Process extends JPanel implements ActionListener,MouseListener,KeyListener{
    private Area area;
    private ArrayList playerArray = new ArrayList();
    private Player playerCurrentTurn;
    private Boss boss;
    private boolean isFirstTime = true;
    private int counting = 1;
    private int currentTurn = 0;
    private int winner;
    private int nextPosition;
    private boolean isend = false; 
    private playmusic pm;
    

    private int[][] positionOnGui = {{670, 600},{610, 530},{570, 480},{530, 450},
                                    {490, 420},{430, 390},{390, 350},{350, 310},
                                    {300, 270},{360, 220 },{400, 180 },{440, 140 },
                                    {490, 120},{540, 90},{590, 60},{630, 20},
                                    {690, -10},{740, 20},{780,  60},{830, 90},
                                    {880, 120},{930, 140},{970, 180},{1020, 220},
                                    {1090, 270},{1020, 320},{980, 360},{940, 390},
                                    {890, 420},{840, 450},{790, 480},{750, 530}}; //31
    //GUI ZONE
    final int PANEL_WIDTH =  1550;
	final int PANEL_HEIGHT = 800;
	private Image player1,player2,player3,player4;
	private Image backgroundImage;
	private Timer timer;
	private int xVelocity = 20;
	private int yVelocity = 20;
	private int x = 670;
	private int y = 600;
    private int p2x,p2y,p1x,p1y,p3x,p3y,p4x,p4y;  
	private JButton button2 = new JButton();
	private ImageIcon oneimg,twoimg,threeimg,fourimg,fiveimg,siximg,pressrandombuttonimg,randombuttonimg,bossGui,areaimg, bossDie,stunflameimg,stuneffectimg;
	private ImageIcon baroneimg , bartwoimg , barthreeimg , barfourimg,upgradebuttonimg,atkbossbuttonimg,slashimg,greenturnimg,redturnimg,blueturnimg,yellowturnimg;
	private JLabel 	buttonlabel , numberofromrandomlabel,barone ,bartwo,barthree,barfour , bosslJLabel,areaGui,swordbutton,bossbutton,winnerend,turncountbar,tutbutton,turndigitcount,turn10count,slash1,slash2,slash3;
	private JLayeredPane layeredPane3 = new JLayeredPane();
    private JLayeredPane layeredPane4;
    private JLabel background2, Flag, trophey, winJLabel, color, score ,winnerJLabel,score1,score10,score100,score1k,score10k,score100k, hpLabel0, atkLabel0, weaponLabel0, scoreLabel0, hpLabel1, atkLabel1, weaponLabel1, scoreLabel1, hpLabel2, atkLabel2, weaponLabel2, scoreLabel2, hpLabel3, atkLabel3, weaponLabel3, scoreLabel3, hpBossLabel, atkBossLabel,battleA,battleL;
    private ImageIcon BG, flagimg, tropheyimg, winimggreen,winimgblue,winimgred,winimgyellow, red, green, yellow, scoreimg,winnerimgyellow,winnerimgblue,winnerimgred,winnerimggreen,tutbuttonimg ,turncountbarimg;
    private JLabel atkdown,atkup1, atkup2, atkup3, atkup4,Hpup,Hpdown1,Hpdown2,Hpdown3,Hpdown4,colorturn,playerslash,tonado;
    private ImageIcon scoreNo0,scoreNo1,scoreNo2,scoreNo3,scoreNo4,scoreNo5,scoreNo6,scoreNo7,scoreNo8,scoreNo9,atkdownimg,Hpupimg,Hpdownimg,atkupimg,playerslashimg,tonadoimg,battleAimg1,battleLimg1,battleAimg2,battleLimg2;;
    private JLabel player1die,player2die,player3die,player4die,stunflame,stuneffect,tornado,bossatkup;
    

    public void countingIncrement() {
        this.counting++;
        
        if(this.counting % 2 == 0) {
            this.boss.aoeAtk(this.playerArray);
            pm.playmusic("./src/sound/BOSSATK.wav");
            Hpdown1.setVisible(true);
            Hpdown2.setVisible(true);
            if (playerArray.size()==3) {
                Hpdown3.setVisible(true);
            }else if (playerArray.size()==4) {
                Hpdown3.setVisible(true);
                Hpdown4.setVisible(true);
            }
            bossatkup.setVisible(true);
            slash1.setVisible(true);
            slash2.setVisible(true);
            slash3.setVisible(true);
            checkDie();
        }
    }

    public void setPlayersToArray(ArrayList arr) {
        for(int i=0; i<arr.size(); i++) {
            this.playerArray.add(arr.get(i));
        }
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public String getEvenOrOdd() {//Roughly waiting for gui
        Random rand = new Random();
        int evenOrOdd = rand.nextInt(2); //random 0-1
        if (evenOrOdd == 1){
            return "Odd";
        }else{
            return "Even";
        }
    }

    public void roleTheDice()  {
        this.playerCurrentTurn = (Player) this.playerArray.get(currentTurn);
    }
    
    public void checkDie(){
        for (int i=0; i<this.playerArray.size(); i++){
            Player p = (Player)this.playerArray.get(i);
            if (p.getHP() <= 0){
                pm.playmusic("./src/sound/PLAYERDEAD.wav");
                if(i==0){
                    player1die.setVisible(true);
                }
                else if (i==1){
                    player2die.setVisible(true);
                }
                else if (i==2){
                    player3die.setVisible(true);
                }
                else if (i==3){
                    player4die.setVisible(true);
                }
                p.setHP(100);
                p.setWeaponAtk(0);
                p.decreaseScore(p.getScore());
                this.area.resetPlayerPosition(i);
            }
        }
    }

    public void attackBoss() {
        Player player = (Player) this.playerArray.get(currentTurn);
        player.attackBoss(boss, player);
    }

    public void checkSpecialBlock(int playerPosition){
        if (area.getSpecialBlock().contains(playerPosition)){
            buttonlabel.setVisible(false);
            swordbutton.setVisible(true);
            bossbutton.setVisible(true);
        }
    }

    public void checkTrapBlock(int playerPosition){
        if (area.getTrapBlock().contains(playerPosition)){
            area.activeTrapBlock(this.playerCurrentTurn);
            pm.playmusic("./src/sound/trap.wav");
            if (currentTurn==0) {
                Hpdown1.setVisible(true);
                
            }
            if (currentTurn==1) {
                Hpdown2.setVisible(true);
                
            }
            if (currentTurn==2) {
                Hpdown3.setVisible(true);
                
            }
            if (currentTurn==3) {
                Hpdown4.setVisible(true);
                
            }
        }
    }

    public void checkMoveBlock(int playerPosition){
        if (area.getMoveBlock().contains(playerPosition)){
            area.activeMoveBlock(currentTurn);
            pm.playmusic("./src/sound/warp.wav");
        }
    }

    public void checkStunBlock(int playerPosition){
        if (playerPosition == 8){
            pm.playmusic("./src/sound/stun.wav");
            stunflame.setVisible(true);
            stuneffect.setVisible(true);
            area.activeStunBlock(this.playerCurrentTurn);
        }
    }

    public void checkMagicDoorBlock(int playerPosition){
        if (playerPosition == 16){
            pm.playmusic("./src/sound/tornado.wav");
            tornado.setVisible(true);
            area.activeMagicDoorBlock(currentTurn);
            tonado.setVisible(true);
        }
    }

    public void checkHealBlock(int playerPosition){
        if (playerPosition == 24){
            pm.playmusic("./src/sound/HOLY.wav");
            area.activeHealBlock(this.playerCurrentTurn);
            Hpup.setVisible(true);
        }
    }
    
    public void findWinner() {
        int max = 0;
        for(int i=0; i<this.playerArray.size(); i++) {
            Player p = (Player) this.playerArray.get(i);
            double score = p.score;
            if(score > max) {
                max=(int)score;
                this.winner = i;
                
            }
            
        }
        renderEndPage(max);
    }

    
    public void renderEndPage(int max) {
        int score = max;
        String s = Integer.toString(score);
        int sLength = s.length();
        String newNum = "0".repeat(5 - sLength) + s;
        for(int i=0; i<5; i++) {
            char curNum = newNum.charAt(i);
            if(curNum == '0') {
                if(i==0){
                    score1.setIcon(scoreNo0);
                }
                else if(i==1){
                    score10.setIcon(scoreNo0);
                }
                else if(i==2){
                    score100.setIcon(scoreNo0);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo0);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo0);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo0);
                }
            }else if(curNum == '1') {
                if(i==0){
                    score1.setIcon(scoreNo1);
                }
                else if(i==1){
                    score10.setIcon(scoreNo1);
                }
                else if(i==2){
                    score100.setIcon(scoreNo1);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo1);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo1);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo1);
                }
            }else if(curNum == '2') {
                if(i==0){
                    score1.setIcon(scoreNo2);
                }
                else if(i==1){
                    score10.setIcon(scoreNo2);
                }
                else if(i==2){
                    score100.setIcon(scoreNo2);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo2);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo2);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo2);
                }
            }else if(curNum == '3') {
                if(i==0){
                    score1.setIcon(scoreNo3);
                }
                else if(i==1){
                    score10.setIcon(scoreNo3);
                }
                else if(i==2){
                    score100.setIcon(scoreNo3);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo3);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo3);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo3);
                }
            }else if(curNum == '4') {
                if(i==0){
                    score1.setIcon(scoreNo4);
                }
                else if(i==1){
                    score10.setIcon(scoreNo4);
                }
                else if(i==2){
                    score100.setIcon(scoreNo4);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo4);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo4);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo4);
                }
            }else if(curNum == '5') {
                if(i==0){
                    score1.setIcon(scoreNo5);
                }
                else if(i==1){
                    score10.setIcon(scoreNo5);
                }
                else if(i==2){
                    score100.setIcon(scoreNo5);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo5);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo5);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo5);
                }
            }else if(curNum == '6') {
                if(i==0){
                    score1.setIcon(scoreNo6);
                }
                else if(i==1){
                    score10.setIcon(scoreNo6);
                }
                else if(i==2){
                    score100.setIcon(scoreNo6);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo6);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo6);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo6);
                }
            }else if(curNum == '7') {
                if(i==0){
                    score1.setIcon(scoreNo7);
                }
                else if(i==1){
                    score10.setIcon(scoreNo7);
                }
                else if(i==2){
                    score100.setIcon(scoreNo7);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo7);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo7);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo7);
                }
            }else if(curNum == '8') {
                if(i==0){
                    score1.setIcon(scoreNo8);
                }
                else if(i==1){
                    score10.setIcon(scoreNo8);
                }
                else if(i==2){
                    score100.setIcon(scoreNo8);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo8);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo8);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo8);
                }
            }else if(curNum == '9') {
                if(i==0){
                    score1.setIcon(scoreNo9);
                }
                else if(i==1){
                    score10.setIcon(scoreNo9);
                }
                else if(i==2){
                    score100.setIcon(scoreNo9);
                }
                else if(i==3){
                    score1k.setIcon(scoreNo9);
                }
                else if(i==4){
                    score10k.setIcon(scoreNo9);
                }
                else if(i==5){
                    score100k.setIcon(scoreNo9);
                }
            }
        }
    }
   
	
	Process(ArrayList arr){
        pm = new playmusic();
        
        this.addKeyListener(this);
        this.area = new Area(arr.size());
        this.playerArray = arr;
		this.setPreferredSize(new DimensionUIResource(PANEL_WIDTH,PANEL_HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		player1 = new ImageIcon("./src/images/player1.png").getImage();	
		player2 = new ImageIcon("./src/images/player2.png").getImage();	
		player3 = new ImageIcon("./src/images/player3.png").getImage();	
		player4 = new ImageIcon("./src/images/player4.png").getImage();	
		bossGui = new ImageIcon("./src/images/boss.gif");
        bossDie = new ImageIcon("./src/images/die.png");	
		backgroundImage = new ImageIcon("./src/images/BGtest.png").getImage();
		areaimg = new ImageIcon("./src/images/area.png");
		ImageIcon playerbuttIcon = new ImageIcon("./src/images/play.png"); 
		oneimg = new ImageIcon("./src/images/1B.png");
        twoimg = new ImageIcon("./src/images/2.png");
        threeimg = new ImageIcon("./src/images/3B.png");
        fourimg = new ImageIcon("./src/images/4B.png");
        fiveimg = new ImageIcon("./src/images/5B.png");
        siximg = new ImageIcon("./src/images/6B.png");
		button2.setIcon(playerbuttIcon);
		button2.addMouseListener(this);
		randombuttonimg = new ImageIcon("./src/images/RANDOMBUUTON.png");
        pressrandombuttonimg = new ImageIcon("./src/images/pressRANDOMBOTTON.png");
		baroneimg = new ImageIcon("./src/images/barone.png");
        bartwoimg = new ImageIcon("./src/images/bartwo.png");
        barthreeimg = new ImageIcon("./src/images/barthree.png");
		barfourimg = new ImageIcon("./src/images/barfour.png");

        atkbossbuttonimg = new ImageIcon("./src/images/atkbossbutton.png");
		upgradebuttonimg = new ImageIcon("./src/images/upgradeweapon.png");
        turncountbarimg = new ImageIcon("./src/images/turncountbar.png");
        tutbuttonimg = new ImageIcon("./src/images/tutbuuton.png");

        slashimg =new ImageIcon("./src/images/shadowspear.gif");
        atkdownimg = new ImageIcon("./src/images/atkdown.png");
        atkupimg = new ImageIcon("./src/images/atkup.png");
        Hpdownimg = new ImageIcon("./src/images/HPdown.png");
        Hpupimg = new ImageIcon("./src/images/HPUP.png");

        greenturnimg = new ImageIcon("./src/images/greenturn.png");
        redturnimg = new ImageIcon("./src/images/redturn.png");
        blueturnimg = new ImageIcon("./src/images/blueturn.png");
        yellowturnimg = new ImageIcon("./src/images/yellowturn.png");

        tonadoimg = new ImageIcon("./src/images/tonado.gif");
        playerslashimg =new ImageIcon("./src/images/slash.gif");

        battleAimg1 = new ImageIcon("./src/images/battleA.png");
		battleAimg2 = new ImageIcon("./src/images/battleA2.png");
		battleLimg1 = new ImageIcon("./src/images/battleB.png");
		battleLimg2 = new ImageIcon("./src/images/battleB2.png");

        stunflameimg = new ImageIcon("./src/images/flame.gif");
        stuneffectimg = new ImageIcon("./src/images/Stun_icon.png");

        turncountbar = new JLabel();
		turncountbar.setIcon(turncountbarimg);

		tutbutton = new JLabel();
        tutbutton.addMouseListener(this);
		tutbutton.setIcon(tutbuttonimg);

		barone = new JLabel();
        barone.setIcon(baroneimg);
        
        bartwo = new JLabel();
        bartwo.setIcon(bartwoimg);

		barthree = new JLabel();
		barthree.setIcon(barthreeimg);
        
		barfour = new JLabel();
        barfour.setIcon(barfourimg);

		bosslJLabel = new JLabel();
		bosslJLabel.setIcon(bossGui);
		
		buttonlabel = new JLabel();
        buttonlabel.addMouseListener(this);
        buttonlabel.setIcon(randombuttonimg);
        
		areaGui = new JLabel();
        areaGui.setIcon(areaimg);
        
		numberofromrandomlabel = new JLabel();
        
        hpLabel0 = new JLabel();
        atkLabel0 = new JLabel();
        weaponLabel0 = new JLabel();
        scoreLabel0 = new JLabel();
        hpLabel1 = new JLabel();
        atkLabel1 = new JLabel();
        weaponLabel1 = new JLabel();
        scoreLabel1 = new JLabel();
        hpLabel2 = new JLabel();
        atkLabel2 = new JLabel();
        weaponLabel2 = new JLabel();
        scoreLabel2 = new JLabel();
        hpLabel3 = new JLabel();
        atkLabel3 = new JLabel();
        weaponLabel3 = new JLabel();
        scoreLabel3 = new JLabel();

        hpBossLabel = new JLabel();
        atkBossLabel = new JLabel();

        swordbutton = new JLabel();
		swordbutton.setIcon(upgradebuttonimg);
		swordbutton.setVisible(false);
		swordbutton.addMouseListener(this);

		bossbutton = new JLabel();
		bossbutton.setIcon(atkbossbuttonimg);;
		bossbutton.setVisible(false);
		bossbutton.addMouseListener(this);

        BG = new ImageIcon("./src/images/endBG2.png");
		background2 = new JLabel();
		background2.setIcon(BG);
			
		background2.setLayout(null);
		background2.setVisible(false);

        //FLAG
		flagimg = new ImageIcon("./src/images/FLAG.png");
		Flag = new JLabel();
		Flag.setIcon(flagimg);
		Flag.setLayout(null);
		Flag.setVisible(false);
	
		//trophey
		tropheyimg = new ImageIcon("./src/images/trophey.png");
		trophey = new JLabel();
		trophey.setIcon(tropheyimg);
		trophey.setVisible(false);
		trophey.setLayout(null);
	
		//win
		winimgyellow = new ImageIcon("./src/images/winneryellowbar.png");
		winimgred = new ImageIcon("./src/images/winnerredbar.png");
		winimggreen = new ImageIcon("./src/images/winnergreenbar.png");
        winimgblue = new ImageIcon("./src/images/winnerbluebar.png");

		winJLabel = new JLabel();
		winJLabel.setVisible(false);
			
		//winner
		winnerend = new JLabel();
		winnerimggreen = new ImageIcon("./src/images/winnergreen.png");
		winnerimgred = new ImageIcon("./src/images/winnerred.png");
		winnerimgblue = new ImageIcon("./src/images/winnerblue.png");
		winnerimgyellow = new ImageIcon("./src/images/winneryellow.png");

		winnerend.setLayout(null);
		winnerend.setVisible(false);
	
        //score
        scoreimg = new ImageIcon("./src/images/score.png");
        score = new JLabel();
        score.setIcon(scoreimg);
        
        score.setLayout(null);
        score.setVisible(false);

        scoreNo0 = new ImageIcon("./src/images/0mini.png");
        scoreNo1 = new ImageIcon("./src/images/1mini.png");
        scoreNo2 = new ImageIcon("./src/images/2mini.png");
        scoreNo3 = new ImageIcon("./src/images/3mini.png");
        scoreNo4 = new ImageIcon("./src/images/4mini.png");
        scoreNo5 = new ImageIcon("./src/images/5mini.png");
        scoreNo6 = new ImageIcon("./src/images/6mini.png");
        scoreNo7 = new ImageIcon("./src/images/7mini.png");
        scoreNo8 = new ImageIcon("./src/images/8mini.png");
        scoreNo9= new ImageIcon("./src/images/9mini.png");

        score1 = new JLabel();
        score1.setLayout(null);
        score1.setVisible(false);
        
        
        score10 = new JLabel();
        score10.setLayout(null);
        score10.setVisible(false);

        score100 = new JLabel();
        score100.setLayout(null);
        score100.setVisible(false);

        score1k = new JLabel();
        score1k.setLayout(null);
        score1k.setVisible(false);

        score10k = new JLabel();
        score10k.setLayout(null);
        score10k.setVisible(false);

        turn10count = new JLabel();
        turn10count.setIcon(scoreNo0);

        turndigitcount = new JLabel();
        turndigitcount.setIcon(scoreNo1);

        slash1 = new JLabel();
        slash1.setIcon(slashimg);
        slash1.setVisible(false);

        slash2 = new JLabel();
        slash2.setIcon(slashimg);
        slash2.setVisible(false);

        slash3 = new JLabel();
        slash3.setIcon(slashimg);
        slash3.setVisible(false);

        Hpup = new JLabel();
        Hpup.setIcon(Hpupimg);
        Hpup.setVisible(false);
        
        atkup1 = new JLabel();
        atkup1.setIcon(atkupimg);
        atkup1.setVisible(false);

        atkup2 = new JLabel();
        atkup2.setIcon(atkupimg);
        atkup2.setVisible(false);

        atkup3 = new JLabel();
        atkup3.setIcon(atkupimg);
        atkup3.setVisible(false);

        atkup4 = new JLabel();
        atkup4.setIcon(atkupimg);
        atkup4.setVisible(false);

        Hpdown1 = new JLabel();
        Hpdown1.setIcon(Hpdownimg);
        Hpdown1.setVisible(false);

        Hpdown2 = new JLabel();
        Hpdown2.setIcon(Hpdownimg);
        Hpdown2.setVisible(false);

        Hpdown3 = new JLabel();
        Hpdown3.setIcon(Hpdownimg);
        Hpdown3.setVisible(false);

        Hpdown4 = new JLabel();
        Hpdown4.setIcon(Hpdownimg);
        Hpdown4.setVisible(false);

        colorturn = new JLabel();
        colorturn.setIcon(redturnimg);

        tonado = new JLabel();
        tonado.setIcon(tonadoimg);
        tonado.setVisible(false);

        playerslash = new JLabel();
        playerslash.setIcon(playerslashimg);
        playerslash.setVisible(false);

        battleA = new JLabel();
		battleA.setIcon(battleAimg1);
		battleA.setVisible(false);

		battleL = new JLabel();
		battleL.setIcon(battleLimg1);
        battleL.setVisible(false);

        player1die = new JLabel();
        player1die.setIcon(bossDie);
        player1die.setVisible(false);
        
        player2die = new JLabel();
        player2die.setIcon(bossDie);
        player2die.setVisible(false);
        
        player3die = new JLabel();
        player3die.setIcon(bossDie);
        player3die.setVisible(false);

        player4die = new JLabel();
        player4die.setIcon(bossDie);
        player4die.setVisible(false);

        stuneffect = new JLabel();
        stuneffect.setIcon(stuneffectimg);
		stuneffect.setVisible(false);

        stunflame= new JLabel();
        stunflame.setIcon(stunflameimg);
        stunflame.setVisible(false);

        tornado = new JLabel();
        tornado.setIcon(tonadoimg);
        tornado.setVisible(false);

        bossatkup = new JLabel();
        bossatkup.setIcon(atkupimg);
        bossatkup.setVisible(false);
	}

	public void paint(Graphics g) { // remind HPDOWN1-4
		buttonlabel.setBounds(70, 320, 150, 150);
		numberofromrandomlabel.setBounds(1310,  320, 150, 150);
		barone.setBounds(0, 0, 500, 200);
		bartwo.setBounds(940, 600, 600, 200);
		barthree.setBounds(0, 655, 600, 200);
		barfour.setBounds(940, -55, 600, 200);
		bosslJLabel.setBounds(670, 260, 150, 150);
        playerslash.setBounds(690, 260, 150, 150);
		areaGui.setBounds(250, 0, 1000, 1000);
        swordbutton.setBounds(550, 310, 150, 150);
		bossbutton.setBounds(790, 310, 150, 150);
        turncountbar.setBounds(580, 765, 350, 50);
		tutbutton.setBounds(1450, 150, 70, 70);
        turn10count.setBounds(690, 785, 30, 30);
		turndigitcount.setBounds(716, 785, 30, 30);
        slash1.setBounds(450, 200, 129, 273);
        slash2.setBounds(930, 200, 129, 273);
        slash3.setBounds(680, 30, 129, 273);
        Hpup.setBounds(1130, 250, 50, 50);
        atkup1.setBounds(520, 380, 50, 50);
        atkup2.setBounds(510, 100, 50, 50);
        atkup3.setBounds(915, 80, 50, 50);
        atkup4.setBounds(915, 380, 50, 50);
        colorturn.setBounds(95, 480, 100, 50);
        tonado.setBounds(690, 0, 200, 100);
        Hpdown1.setBounds(200, -35, 100, 100);
        Hpdown2.setBounds(1270, 620, 100, 100);
        Hpdown4.setBounds(1270, -35, 100, 100);
        Hpdown3.setBounds(200, 620, 100, 100);
        battleA.setBounds(320, 150, 500, 500);
		battleL.setBounds(660, 150, 500, 500);
        player1die.setBounds(120, 0, 150, 150);
        player2die.setBounds(1270, 650, 150, 150);
        player3die.setBounds(120, 650, 150, 150);
        player4die.setBounds(1270, 0, 150, 150);
        stunflame.setBounds(250, 200, 200, 200);
        stuneffect.setBounds(350, 200, 150, 150);
        tornado.setBounds(x, y, 200, 100);
        bossatkup.setBounds(725, 130, 150, 150);

        background2.setBounds(0, 0, 1900,1000 );
		Flag.setBounds(80, 0, 600, 500);
		trophey.setBounds(336, 300, 150, 150);
		winnerend.setBounds(900, 90, 500, 500);
		winJLabel.setBounds(130, 450, 500, 150);
		score.setBounds(180, 550, 450, 150);
		score10k.setBounds(560, 607, 30, 30);
		score1k.setBounds(525, 607, 30, 30);
		score100.setBounds(490, 607, 30, 30);
		score10.setBounds(455, 607, 30, 30);
		score1.setBounds(420, 607, 30, 30);

        hpLabel0.setBounds(180, -35, 50, 100);
        hpLabel0.setText(String.valueOf(((Player) this.playerArray.get(0)).getHP()));
        atkLabel0.setBounds(180, -5, 50, 100);
        atkLabel0.setText(String.valueOf(((Player) this.playerArray.get(0)).getBaseATK()));
        weaponLabel0.setBounds(180, 25, 50, 100);
        weaponLabel0.setText(String.valueOf(((Player) this.playerArray.get(0)).getWeaponATK()));
        scoreLabel0.setBounds(180, 55, 50, 100);
        scoreLabel0.setText(String.valueOf(((Player) this.playerArray.get(0)).getScore()));

        hpLabel1.setBounds(1330, 620, 50, 100);
        hpLabel1.setText(String.valueOf(((Player) this.playerArray.get(1)).getHP()));
        atkLabel1.setBounds(1330, 650, 50, 100);
        atkLabel1.setText(String.valueOf(((Player) this.playerArray.get(1)).getBaseATK()));
        weaponLabel1.setBounds(1330, 680, 50, 100);
        weaponLabel1.setText(String.valueOf(((Player) this.playerArray.get(1)).getWeaponATK()));
        scoreLabel1.setBounds(1330, 709, 50, 100);
        scoreLabel1.setText(String.valueOf(((Player) this.playerArray.get(1)).getScore()));
        
        this.add(bossatkup);
        this.add(tornado);
        this.add(stuneffect);
        this.add( stunflame);
        this.add(player1die);
        this.add(player2die);
        this.add(player3die);
        this.add(player4die);
        this.add(playerslash);
        this.add(battleA);
		this.add(battleL);
        this.add(Hpdown1);
        this.add(Hpdown2);
        this.add(Hpdown3);
        this.add(Hpdown4);
        this.add(tonado);
        this.add(atkup1);
        this.add(atkup2);
        this.add(atkup3);
        this.add(atkup4);
        this.add(Hpup);
        this.add(slash1);
        this.add(slash2);
        this.add(slash3);
        this.add(turndigitcount);
		this.add(turn10count);
        this.add(tutbutton);
		this.add(turncountbar);
        this.add(swordbutton);
        this.add(bossbutton);
        this.add(hpLabel0);
        this.add(atkLabel0);
        this.add(weaponLabel0);
        this.add(scoreLabel0);
        this.add(hpLabel1);
        this.add(atkLabel1);
        this.add(weaponLabel1);
        this.add(scoreLabel1);
        this.add(Flag);
        this.add(winnerend);
        this.add(trophey);
        this.add(winJLabel);
        this.add(score1);
        this.add(score10);
        this.add(score100);
        this.add(score1k);
        this.add(score10k);
        this.add(score);
        this.add(background2);

        if(boss.checkBoss()) {
            bosslJLabel.setIcon(bossDie);
            hpBossLabel.setVisible(false);
            atkBossLabel.setVisible(false);
        }

        atkBossLabel.setBounds(740, 200, 50, 100);
        atkBossLabel.setForeground(Color.red);
        atkBossLabel.setText(String.valueOf(((Boss) this.boss).getAtkBoss()));
        atkBossLabel.setHorizontalTextPosition(JLabel.CENTER);

        hpBossLabel.setBounds((((Boss) this.boss).getHpBossAtTheMoment() < 100) ? 730: 725, 180, 50, 100);
        hpBossLabel.setForeground(Color.red);
        hpBossLabel.setText(String.valueOf(((Boss) this.boss).getHpBossAtTheMoment()) + "/100");
        hpBossLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.add(hpBossLabel);
        this.add(atkBossLabel);

        this.add(bosslJLabel);
		this.add(areaGui);
        if(this.playerArray.size() == 2) {
            this.add(barone);
            this.add(bartwo);
        }else if(this.playerArray.size() == 3) {
            hpLabel2.setBounds(180, 620, 50, 100);
            hpLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getHP()));
            atkLabel2.setBounds(180, 650, 50, 100);
            atkLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getBaseATK()));
            weaponLabel2.setBounds(180, 680, 50, 100);
            weaponLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getWeaponATK()));
            scoreLabel2.setBounds(180, 709, 50, 100);
            scoreLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getScore()));

            this.add(hpLabel2);
            this.add(atkLabel2);
            this.add(weaponLabel2);
            this.add(scoreLabel2);
            this.add(barone);
            this.add(bartwo);
            this.add(barthree);
        }else if(this.playerArray.size() == 4) {
            hpLabel2.setBounds(180, 620, 50, 100);
            hpLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getHP()));
            atkLabel2.setBounds(180, 650, 50, 100);
            atkLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getBaseATK()));
            weaponLabel2.setBounds(180, 680, 50, 100);
            weaponLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getWeaponATK()));
            scoreLabel2.setBounds(180, 709, 50, 100);
            scoreLabel2.setText(String.valueOf(((Player) this.playerArray.get(2)).getScore()));

            hpLabel3.setBounds(1330, -35, 50, 100);
            hpLabel3.setText(String.valueOf(((Player) this.playerArray.get(3)).getHP()));
            atkLabel3.setBounds(1330, -5, 50, 100);
            atkLabel3.setText(String.valueOf(((Player) this.playerArray.get(3)).getBaseATK()));
            weaponLabel3.setBounds(1330, 25, 50, 100);
            weaponLabel3.setText(String.valueOf(((Player) this.playerArray.get(3)).getWeaponATK()));
            scoreLabel3.setBounds(1330, 55, 50, 100);
            scoreLabel3.setText(String.valueOf(((Player) this.playerArray.get(3)).getScore()));

            this.add(hpLabel2);
            this.add(atkLabel2);
            this.add(weaponLabel2);
            this.add(scoreLabel2);
            this.add(hpLabel3);
            this.add(atkLabel3);
            this.add(weaponLabel3);
            this.add(scoreLabel3);
            this.add(barone);
            this.add(bartwo);
            this.add(barthree);
            this.add(barfour);
        }
		this.add(buttonlabel);
		this.add(numberofromrandomlabel);
		
		super.paint(g); // paint background
		
		Graphics2D g2D = (Graphics2D) g;
        
		if(this.playerArray.size() == 2&& !isend) {
            if(this.isFirstTime) {
                g2D.drawImage(player2, 700, 600, null);
                g2D.drawImage(player1, 690, 600, null);
                this.isFirstTime = false;
            }else {
                if(this.currentTurn == 0) {
                    int p1x = this.positionOnGui[this.area.playerPosition[0]][0];
                    int p1y = this.positionOnGui[this.area.playerPosition[0]][1];
                    g2D.drawImage(player2, x - 10, y, null); 
                    g2D.drawImage(player1, p1x + 10, p1y, null);
                    colorturn.setIcon(redturnimg);
                }else if(this.currentTurn == 1) {
                    int p2x = this.positionOnGui[this.area.playerPosition[1]][0];
                    int p2y = this.positionOnGui[this.area.playerPosition[1]][1];
                    g2D.drawImage(player2, p2x - 10, p2y, null);
                    g2D.drawImage(player1, x + 10, y, null);
                    colorturn.setIcon(blueturnimg);
                }
            }
        }else if(this.playerArray.size() == 3&& !isend) {
            if(this.isFirstTime) {
                g2D.drawImage(player3, 690, 590, null);
                g2D.drawImage(player2, 700, 600, null);
                g2D.drawImage(player1, 690, 600, null);
                this.isFirstTime = false;
            }else {
                if(this.currentTurn == 0) {
                     p1x = this.positionOnGui[this.area.playerPosition[0]][0];
                     p1y = this.positionOnGui[this.area.playerPosition[0]][1];
                     p2x = this.positionOnGui[this.area.playerPosition[1]][0];
                     p2y = this.positionOnGui[this.area.playerPosition[1]][1];
                    g2D.drawImage(player3, x, y - 10, null);
                    g2D.drawImage(player2, p2x + 10, p2y, null);
                    g2D.drawImage(player1, p1x, p1y, null);
                }else if(this.currentTurn == 1) {
                     p2x = this.positionOnGui[this.area.playerPosition[1]][0];
                     p2y = this.positionOnGui[this.area.playerPosition[1]][1];
                     p3x = this.positionOnGui[this.area.playerPosition[2]][0];
                     p3y = this.positionOnGui[this.area.playerPosition[2]][1];
                    g2D.drawImage(player3, p3x, p3y - 10, null);
                    g2D.drawImage(player2, p2x + 10, p2y, null);
                    g2D.drawImage(player1, x, y, null);
                }else if(this.currentTurn == 2) {
                     p1x = this.positionOnGui[this.area.playerPosition[0]][0];
                     p1y = this.positionOnGui[this.area.playerPosition[0]][1];
                     p3x = this.positionOnGui[this.area.playerPosition[2]][0];
                     p3y = this.positionOnGui[this.area.playerPosition[2]][1];
                    g2D.drawImage(player3, p3x, p3y - 10, null);
                    g2D.drawImage(player2, x + 10, y, null);
                    g2D.drawImage(player1, p1x, p1y, null);
                }
            }
        }else if(this.playerArray.size() == 4&& !isend) {
            if(this.isFirstTime) {
                g2D.drawImage(player4, 720, 550, null);
                g2D.drawImage(player3, 670, 550, null);
                g2D.drawImage(player2, 720, 600, null);
                g2D.drawImage(player1, 670, 600, null);
                this.isFirstTime = false;
            }else {
                if(this.currentTurn == 0) {
                    p1x = this.positionOnGui[this.area.playerPosition[0]][0];
                    p1y = this.positionOnGui[this.area.playerPosition[0]][1];
                    p2x = this.positionOnGui[this.area.playerPosition[1]][0];
                    p2y = this.positionOnGui[this.area.playerPosition[1]][1];
                    p3x = this.positionOnGui[this.area.playerPosition[2]][0];
                    p3y = this.positionOnGui[this.area.playerPosition[2]][1];
                    g2D.drawImage(player4, x, y, null);
                    g2D.drawImage(player3, p3x, p3y - 10, null);
                    g2D.drawImage(player2, p2x + 10, p2y, null);
                    g2D.drawImage(player1, p1x, p1y, null);
                }else if(this.currentTurn == 1) {
                    p2x = this.positionOnGui[this.area.playerPosition[1]][0];
                    p2y = this.positionOnGui[this.area.playerPosition[1]][1];
                    p3x = this.positionOnGui[this.area.playerPosition[2]][0];
                    p3y = this.positionOnGui[this.area.playerPosition[2]][1];
                    p4x = this.positionOnGui[this.area.playerPosition[3]][0];
                    p4y = this.positionOnGui[this.area.playerPosition[3]][1];
                    g2D.drawImage(player4, p4x, p4y, null);
                    g2D.drawImage(player3, p3x, p3y - 10, null);
                    g2D.drawImage(player2, p2x + 10, p2y, null);
                    g2D.drawImage(player1, x, y, null);
                }else if(this.currentTurn == 2) {
                    p1x = this.positionOnGui[this.area.playerPosition[0]][0];
                    p1y = this.positionOnGui[this.area.playerPosition[0]][1];
                    p3x = this.positionOnGui[this.area.playerPosition[2]][0];
                    p3y = this.positionOnGui[this.area.playerPosition[2]][1];
                    p4x = this.positionOnGui[this.area.playerPosition[3]][0];
                    p4y = this.positionOnGui[this.area.playerPosition[3]][1];
                    g2D.drawImage(player4, p4x, p4y, null);
                    g2D.drawImage(player3, p3x, p3y - 10, null);
                    g2D.drawImage(player2, x + 10, y, null);
                    g2D.drawImage(player1, p1x, p1y, null);
                }else if(this.currentTurn == 3) {
                    p1x = this.positionOnGui[this.area.playerPosition[0]][0];
                    p1y = this.positionOnGui[this.area.playerPosition[0]][1];
                    p2x = this.positionOnGui[this.area.playerPosition[1]][0];
                    p2y = this.positionOnGui[this.area.playerPosition[1]][1];
                    p4x = this.positionOnGui[this.area.playerPosition[3]][0];
                    p4y = this.positionOnGui[this.area.playerPosition[3]][1];
                    g2D.drawImage(player4, p4x, p4y, null);
                    g2D.drawImage(player3, x, y - 10, null);
                    g2D.drawImage(player2, p2x + 10, p2y, null);
                    g2D.drawImage(player1, p1x, p1y, null);
                }
            }   
        }
        else{
            g2D.drawImage(player4, 10000,10000, null);
            g2D.drawImage(player3,10000, 10000 , null);
            g2D.drawImage(player2, 10000, 10000, null);
            g2D.drawImage(player1, 10000, 10000, null);
        }
	}

	@Override
	public void actionPerformed(ActionEvent a) {
	}
    
	@Override
	public void mouseClicked(MouseEvent e) {
       
        // TODO Auto-generated method
      
        if(this.counting >= 21) {
            findWinner();
            pm.stopmusic();
            pm.playmusic("./src/sound/rickroll.wav");
           
            if(winner==0){
                winnerend.setIcon(winnerimgred);
                winJLabel.setIcon(winimgred);
            }
            else if(winner==1){
                winnerend.setIcon(winnerimgblue);
                winJLabel.setIcon(winimgblue);
            }
            else if(winner==2){
                winnerend.setIcon(winnerimgyellow );
                winJLabel.setIcon(winimgyellow);
            }
            else if(winner==3){
                winnerend.setIcon(winnerimggreen);
                winJLabel.setIcon(winimggreen);
            }
           
            isend = true;
            
            buttonlabel.setVisible(false);
			numberofromrandomlabel.setVisible(false);
		    barone.setVisible(false);
		    bartwo.setVisible(false);
		    barthree.setVisible(false);
		    barfour.setVisible(false);
		    bosslJLabel.setVisible(false);
		    areaGui.setVisible(false);
		    swordbutton.setVisible(false);
		    bossbutton.setVisible(false);
		    turncountbar.setVisible(false);
		    tutbutton.setVisible(false);
			hpLabel0.setVisible(false);
            atkLabel0.setVisible(false); weaponLabel0.setVisible(false); scoreLabel0.setVisible(false); hpLabel1.setVisible(false); atkLabel1.setVisible(false); weaponLabel1.setVisible(false); scoreLabel1.setVisible(false); hpLabel2.setVisible(false); atkLabel2.setVisible(false); weaponLabel2.setVisible(false); scoreLabel2.setVisible(false); hpLabel3.setVisible(false); atkLabel3.setVisible(false); 
            weaponLabel3.setVisible(false); 
            scoreLabel3.setVisible(false);
            turn10count.setVisible(false);
            turndigitcount.setVisible(false);
            Hpdown1.setVisible(false);
            Hpdown2.setVisible(false);
            Hpdown3.setVisible(false);
            Hpdown4.setVisible(false);
            

            background2.setVisible(true);
            Flag.setVisible(true);
            trophey.setVisible(true);
            winJLabel.setVisible(true);
            winnerend.setVisible(true);
            score.setVisible(true);
            score10k.setVisible(true);
            score1k.setVisible(true);
            score100.setVisible(true);
            score10.setVisible(true);
            score1.setVisible(true);
            repaint();
        }else {
            if (e.getSource()==bossbutton) {
                bossbutton.setVisible(false);
                swordbutton.setVisible(false);
                buttonlabel.setVisible(true);
                playerslash.setVisible(true);
                pm.playmusic("./src/sound/ATKBOSS.wav");
                if(nextPosition==4){
                    atkup1.setVisible(true);
                }
                else if(nextPosition==12){
                    atkup2.setVisible(true);
                }
                else if(nextPosition==20){
                    atkup3.setVisible(true);
                }
                else if(nextPosition==28){
                    atkup4.setVisible(true);
                }
                this.area.activeSpecialBlock(this.playerCurrentTurn, boss, "B");
            }else if (e.getSource()==swordbutton) {
                bossbutton.setVisible(false);
                swordbutton.setVisible(false);
                buttonlabel.setVisible(true);
                if(nextPosition==4){
                    atkup1.setVisible(true);
                }
                else if(nextPosition==12){
                    atkup2.setVisible(true);
                }
                else if(nextPosition==20){
                    atkup3.setVisible(true);
                }
                else if(nextPosition==28){
                    atkup4.setVisible(true);
                }
                this.area.activeSpecialBlock(this.playerCurrentTurn, boss, "W");
            }
            else if (e.getSource()==tutbutton){
                try{
                    File file = new File("./src/docs/Tutorial.pdf"); 
                    Desktop desktop = Desktop.getDesktop();  
                    desktop.open(file);
                }catch(Exception error){  
                    error.printStackTrace();  
                }  
            }
            else if (e.getSource()==buttonlabel){
                pm.playmusic("./src/sound/mouse_click.wav");
                bossatkup.setVisible(false);
                tornado.setVisible(false);
                player1die.setVisible(false);
                player2die.setVisible(false);
                player3die.setVisible(false);
                player4die.setVisible(false);
                playerslash.setVisible(false);
                slash1.setVisible(false);
                slash2.setVisible(false);
                slash3.setVisible(false);
                atkup1.setVisible(false);
                atkup2.setVisible(false);
                atkup3.setVisible(false);
                atkup4.setVisible(false);
                Hpup.setVisible(false);
                tonado.setVisible(false);
                Hpdown1.setVisible(false);
                Hpdown2.setVisible(false);
                Hpdown3.setVisible(false);
                Hpdown4.setVisible(false);
                if (counting%10==0) {
                    turndigitcount.setIcon(scoreNo0);
                }
                else if (counting%10==1) {
                    turndigitcount.setIcon(scoreNo1);
                }
                else if (counting%10==2) {
                    turndigitcount.setIcon(scoreNo2);
                }
                else if (counting%10==3) {
                    turndigitcount.setIcon(scoreNo3);
                }
                else if (counting%10==4) {
                    turndigitcount.setIcon(scoreNo4);
                }
                else if (counting%10==5) {
                    turndigitcount.setIcon(scoreNo5);
                }
                else if (counting%10==6) {
                    turndigitcount.setIcon(scoreNo6);
                }
                else if (counting%10==7) {
                    turndigitcount.setIcon(scoreNo7);
                }
                else if (counting%10==8) {
                    turndigitcount.setIcon(scoreNo8);
                }
                else if (counting%10==9) {
                    turndigitcount.setIcon(scoreNo9);
                }
                else if (counting/10==0) {
                    turn10count.setIcon(scoreNo0);
                }

                double numberturn =counting/10;
                if (Math.floor(numberturn)==1) {
                    turn10count.setIcon(scoreNo1);
                }
                else if (Math.floor(numberturn)==2) {
                    turn10count.setIcon(scoreNo2);
                }

                Dice dice = new Dice();
                dice.roleTheDice();
                int number = dice.getRandom();
                if (number==1){
                    numberofromrandomlabel.setIcon(oneimg);
                }
                else if (number==2){
                    numberofromrandomlabel.setIcon(twoimg);
                }
                else if (number==3){
                    numberofromrandomlabel.setIcon(threeimg);
                }
                else if (number==4){
                    numberofromrandomlabel.setIcon(fourimg);
                }
                else if (number==5){
                    numberofromrandomlabel.setIcon(fiveimg);
                }
                else if (number==6){
                    numberofromrandomlabel.setIcon(siximg);
                }

                this.playerCurrentTurn = (Player) this.playerArray.get(this.currentTurn);
                int beforePosition = this.area.getPlayerPosition(this.currentTurn);
                if (playerCurrentTurn.isStunStatus()){
                    playerCurrentTurn.setStunStatus(false);
                    this.currentTurn++;
                    stunflame.setVisible(false);
                    stuneffect.setVisible(false);
                    if(this.currentTurn > this.playerArray.size() - 1) {
                        this.currentTurn = 0;
                    }
                }

                int currentPlayerPosition = this.area.playerPosition[this.currentTurn];
                nextPosition = currentPlayerPosition + number;
                
                if(nextPosition > 31) {
                    int x = 0;
                    x += (nextPosition - 31);
                    nextPosition = x;
                }

                x = this.positionOnGui[nextPosition][0];
                y = this.positionOnGui[nextPosition][1];
                this.area.playerPosition[this.currentTurn] = nextPosition;
                repaint();

                this.playerCurrentTurn = (Player) this.playerArray.get(currentTurn);
                checkSpecialBlock(nextPosition);
                checkTrapBlock(nextPosition);
                checkMoveBlock(nextPosition);
                checkStunBlock(nextPosition);
                checkMagicDoorBlock(nextPosition);
                checkHealBlock(nextPosition);

                nextPosition = this.area.getPlayerPosition(this.currentTurn);
                x = this.positionOnGui[nextPosition][0];
                y = this.positionOnGui[nextPosition][1];
                this.area.playerPosition[this.currentTurn] = nextPosition;
                repaint();

                checkDie();

                nextPosition = this.area.getPlayerPosition(this.currentTurn);
                x = this.positionOnGui[nextPosition][0];
                y = this.positionOnGui[nextPosition][1];
                this.area.playerPosition[this.currentTurn] = nextPosition;
                repaint();

                for(int i=0; i<this.playerArray.size() ;i++) {
                    if(this.area.playerPosition[i] > beforePosition && this.area.playerPosition[i] < nextPosition && i != this.currentTurn) { //Attack everyone who on the way.
                        int ATK = this.playerCurrentTurn.getATK() / 2;
                        ((Player) this.playerArray.get(i)).decreaseHpPlayerByPlayer(ATK);
                        
                        if (i==0) {
                            Hpdown1.setVisible(true);
                        }
                        else if (i==1) {
                            Hpdown2.setVisible(true);
                        }
                        else if (i==2) {
                            Hpdown3.setVisible(true);
                        }
                        else if (i==3) {
                            Hpdown4.setVisible(true);
                        }
                        this.playerCurrentTurn.setScore(ATK);
                    }
                    if(this.area.playerPosition[i] == nextPosition && i != this.currentTurn) { //Attack everyone who in a same block.
                        int attackerATK = this.playerCurrentTurn.getATK();
                        battleL.setVisible(true);
				        battleA.setVisible(true);
                        buttonlabel.setVisible(false);
                        ((Player) this.playerArray.get(i)).decreaseHpPlayerByPlayer(attackerATK);

                        if (i==0) {
                            Hpdown1.setVisible(true);
                        }
                        else if (i==1) {
                            Hpdown2.setVisible(true);
                        }
                        else if (i==2) {
                            Hpdown3.setVisible(true);
                        }
                        else if (i==3) {
                            Hpdown4.setVisible(true);                           
                        }
                        this.playerCurrentTurn.setScore(attackerATK);
                    }
                }
                if(this.currentTurn == this.playerArray.size() - 1) {
                    this.currentTurn = 0;
                    countingIncrement();
                }else {
                    this.currentTurn++;
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource()==buttonlabel){
		buttonlabel.setIcon(pressrandombuttonimg);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource()==buttonlabel){
        buttonlabel.setIcon(randombuttonimg);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    int xux=0;
	int uxux =0;
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        switch(e.getKeyChar()) {
			
			case 'a': battleA.setIcon(battleAimg2);
            pm.playmusic("./src/sound/fighting.wav");
			
			break;
			
			 
			case 'l': battleL.setIcon(battleLimg2);
			pm.playmusic("./src/sound/fighting.wav");
			
			break;
		}

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
         
        switch(e.getKeyChar()) {
			case 'a': battleA.setIcon(battleAimg1);
			xux++;
			if (xux>=35){
				battleA.setVisible(false);
				battleL.setVisible(false);
				xux=0;
				uxux =0;
                buttonlabel.setVisible(true);
				this.playerCurrentTurn.increaseHpPlayer(20);
			}
			break;
			case 'l': battleL.setIcon(battleLimg1);
			uxux++;
			if (uxux>=35){
				battleL.setVisible(false);
				battleA.setVisible(false);
				uxux=0;
				xux=0;
                buttonlabel.setVisible(true);
			}
			break;
		}
    }
}
//pm.playmusic("./src/sound/ATKPLAYER.wav");