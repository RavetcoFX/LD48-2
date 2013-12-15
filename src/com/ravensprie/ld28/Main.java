package com.ravensprie.ld28;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.ravensprie.ld28.entity.EntityEarth;
/**
 * 
 * Ludum Dare Submission for the theme "You only get one"
 * 
 * All rights reserved Ravenspire
 * 
 * @author Ravenspire
 * 
 * 
 */

public class Main extends Canvas implements Runnable, KeyListener, MouseMotionListener, MouseListener
{
	private static final long serialVersionUID = 602801997L;
	
	public static final String TITLE = "Only One Earth";
	public static final String VERSION = "0.4.3";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	public static final int FPS = 30;
	
	private JFrame frame;
	private EntityEarth earth;
	public Color graphite = new Color(40, 40, 40);
	public Color panel = new Color(140, 140, 140);
	private Random rand = new Random();
	private Font textFont = new Font("Arial", Font.BOLD, 12);
	private Thread thread;
	
	public ImageIcon imgLogo = new ImageIcon(this.getClass().getResource("/res/ravenspire.png"));
	public ImageIcon imgTitle = new ImageIcon(this.getClass().getResource("/res/title.png"));
	public ImageIcon imgGloss = new ImageIcon(this.getClass().getResource("/res/gloss.png"));
	public ImageIcon imgEarth = new ImageIcon(this.getClass().getResource("/res/earth.png"));
	public ImageIcon imgAboutBut1 = new ImageIcon(this.getClass().getResource("/res/but/button1.png"));
	public ImageIcon imgAboutBut2 = new ImageIcon(this.getClass().getResource("/res/but/button2.png"));
	public ImageIcon imgAboutBut1l = new ImageIcon(this.getClass().getResource("/res/but/button1_light.png"));
	public ImageIcon imgAboutBut2l = new ImageIcon(this.getClass().getResource("/res/but/button2_light.png"));


	public Image sprtLogo = imgLogo.getImage();
	public Image sprtTitle = imgTitle.getImage();
	public Image sprtGloss = imgGloss.getImage();
	public Image sprtEarth = imgEarth.getImage();
	public Image sprtBut1 = imgAboutBut1.getImage();
	public Image sprtBut2 = imgAboutBut2.getImage();
	
	private static boolean running = false;
	private boolean inMenu = true;
	private boolean inAbout = false;
	private boolean inGame = false;
	private boolean isDebug = false;
	private boolean hasStarted = false;
	private boolean isBut1Pressed = false;
	private boolean isBut2Pressed = false;

	private long lastTimer;
	
	public int oilLevel = 100;
	public int population = 100;
	public int freshWater = 100;
	public int pollution = 100;
	public int tempurture = 50;
	public int numStars = 500;
	public int starXSeed[] = new int[1000];
	public int starYSeed[] = new int[1000];
	
	public Rectangle rectBut1;
	public Rectangle rectBut2;
	
	public Main()
	{
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));	
		frame = new JFrame(TITLE + " v" + VERSION);
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		addKeyListener(this);
		addMouseListener(this);
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() 
	{
        long lastTime = System.nanoTime();
        double skipTicks = 1000000000 / FPS;
        lastTimer = System.currentTimeMillis();
        double i1 = 0;
        
        while (running == true) 
        {
            long now = System.nanoTime();
            i1 += (now - lastTime) / skipTicks;
            lastTime = now;
            boolean shouldRender = true;

            while (i1 >= 1) {
                tick();
                i1 -= 1;
                shouldRender = true;
                //System.out.println("Tick");
            }
            
            try { Thread.sleep(2); } 
            catch (InterruptedException e) {
            }

            if (shouldRender == true) {
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;                
            }
        
        }
	}
	
	public void tick()
	{
		if(inGame){
			if(!hasStarted){
				init();
			}
			earth.tick();
		}
	}
	
	public void init()
	{
		earth = new EntityEarth();
		hasStarted = true;
		
		for(int i=0;i<=numStars;i++){
			starXSeed[i] = rand.nextInt(800);
		}
		for(int i=0;i<=numStars;i++){
			starYSeed[i] = rand.nextInt(500);
		}
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
 		
		if(bs == null){
			createBufferStrategy(3); //triple buffered to ensure no tearing
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Menu Render
		if(inMenu){
			g.setColor(graphite);
			g.fillRect(0, 0, 800, 880);//draws the canvas
			
			g.drawImage(sprtTitle, 260, 10, this);
			g.drawImage(sprtEarth, 315, 220, this);
			g.drawImage(sprtLogo, 525, 420, this);
			g.drawImage(sprtBut1, 600, 230, this);
			g.drawImage(sprtBut2, 30, 230, this);
			
			rectBut1 = new Rectangle(600, 230, sprtBut1.getWidth(null), sprtBut1.getHeight(null));
			rectBut2 = new Rectangle(30, 230, sprtBut2.getWidth(null), sprtBut2.getHeight(null));
		}
		
		//Game Render
		if(inGame){
			
			g.setColor(graphite);
			g.fillRect(0, 0, WIDTH, HEIGHT);//draws the canvas
			
			if(hasStarted){
				
				//Draws Stars
				int sizex = 1;
				int sizey = 1;
				for(int i = 0; i<=numStars; i++){
					g.setColor(Color.WHITE);
					g.drawOval(starXSeed[i], starYSeed[i], sizex, sizey);
					if(rand.nextInt(50) == 0){ 	
						g.drawOval(starXSeed[i], starYSeed[i], rand.nextInt(2), rand.nextInt(2));
					}
				}
				//Placeholder moon
				g.setColor(Color.darkGray);
				g.fillOval(-390, 400, WIDTH * 2, WIDTH * 2);
				
				//Placeholder sun
				g.setColor(Color.yellow);
				g.fillOval(20, 20, 50, 50);
				
				//Panel
				g.setColor(panel);
				g.fillRect(0, 450, WIDTH, 50);
				
				//Amount Bars
				g.setFont(textFont);
				g.setColor(Color.BLACK);
				pollution = 100;
				g.drawString("Population: ", 0, 467);
				g.setColor(Color.WHITE);
				g.drawRect(82, 454, 101, 20); //Population bar outline
				g.setColor(new Color(143, 84, 63));
				g.fillRect(83, 455, population, 19); //Population bar fill
				g.setColor(Color.black);
				g.drawString("" + population, 120, 468); //Population num
				
				g.setColor(Color.BLACK);	
				g.drawString("Resources: ", 0, 493);
				g.setColor(Color.WHITE);
				g.drawRect(82, 478, 101, 20); //oil bar outline
				g.setColor(Color.BLACK);
				g.fillRect(83, 479, oilLevel, 19); //oil bar fill
				g.setColor(Color.WHITE);
				g.drawString("" + oilLevel, 120, 493); //oil num

				
				g.setColor(Color.BLACK);
				g.drawString("Pollution: ", 200, 467);
				g.setColor(Color.WHITE);
				g.drawRect(299, 454, 101, 20); //pollution bar outline
				g.setColor(new Color(109, 135, 0));
				g.fillRect(300, 455, pollution, 19); //pollution bar fill
				g.setColor(Color.BLACK);
				g.drawString("" + pollution, 336, 468); //pollution num

				
				g.setColor(Color.BLACK);
				g.drawString("Fresh Water: ", 200, 493);
				g.setColor(Color.WHITE);
				g.drawRect(299, 478, 101, 20); //water bar outline
				g.setColor(new Color(0, 102, 255));
				g.fillRect(300, 479, freshWater, 19); //water bar fill
				g.setColor(Color.WHITE);
				g.drawString("" + freshWater, 336, 493); //water num

				
				g.setColor(Color.BLACK);
				g.drawString("Tempurture: ", 420, 467);
				g.setColor(Color.WHITE);
				g.drawRect(515, 454, 101, 20); //Temperature bar outline
				g.setColor(new Color(213, 55, 55));
				g.fillRect(516, 455, tempurture*2, 19); //Temperature bar fill
				g.setColor(Color.BLACK);
				g.drawString("" + tempurture, 556, 468); //pollution num

				g.drawImage(sprtGloss, 0, 448, this); //Panel Gloss
				
				g.drawImage(earth.getSprt(), earth.getX(), earth.getY(), this); //Earth sprite
				
				if(isDebug){
					g.setColor(Color.WHITE);
					Rectangle earthRect = earth.getBounds();
					g.draw(earthRect);
				}
			}
		}
		
		g.dispose();
		bs.show();
	}
	
	public void refreshStars()
	{
		for(int i=0;i<=numStars;i++){
			starXSeed[i] = rand.nextInt(800);
		}
		for(int i=0;i<=numStars;i++){
			starYSeed[i] = rand.nextInt(500);
		}
	}
	
	public void startGame()
	{
		if(!inGame){
			inMenu = false;
			inGame = true;
			System.out.println("Game Entered");
		}
	}
	
	public void resetGame()
	{
		oilLevel = 100;
		population = 100;
		freshWater = 100;
		pollution = 100;
		tempurture = 50;
		numStars = 500;
		init();
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) { //Please use buttons later to enter the game, not a cheaty button press!!!
			startGame();
		}
		
		if (keyCode == KeyEvent.VK_ESCAPE){
			if(!inMenu){
				System.out.println("Game Exited");
				if(inGame){
					inMenu = true;
					inGame = false;
				}
				if(inAbout){
					
				}
				resetGame();
			}
		}
		
		if (keyCode == KeyEvent.VK_F3){
			isDebug = !isDebug;
		}
		
		if (keyCode == KeyEvent.VK_F7){
			refreshStars();
		}
		
	}

	public void mousePressed(MouseEvent e) 
	{
		int mx = e.getX(), my = e.getY();
		//start button
		if(mx > rectBut1.x && mx < rectBut1.x + rectBut1.width){
			isBut1Pressed = true;
			sprtBut1 = imgAboutBut1l.getImage();
		}
		//About button
		if(mx > rectBut2.x && mx < rectBut2.x + rectBut2.width){
			isBut2Pressed = true;
			sprtBut2 = imgAboutBut2l.getImage();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX(), my = e.getY();
		//Start button
		sprtBut1 = imgAboutBut1.getImage();
		if(mx > rectBut1.x && mx < rectBut1.x + rectBut1.width){
			if(isBut1Pressed){
				isBut1Pressed = false;
				startGame();
			}
		}
		//about button
		sprtBut2 = imgAboutBut2.getImage();

	}
	
	public void mouseMoved(MouseEvent e) 
	{
		
		
	}
	
	public void keyReleased(KeyEvent e){	
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		
	}



}