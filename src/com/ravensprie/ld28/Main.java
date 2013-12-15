package com.ravensprie.ld28;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.ravensprie.ld28.entity.EntityEarth;
/**
 * 
 *
 * My Ludum Dare Submission for the "You only get one"
 * 
 * All rights reserved Ravenspire
 * 
 * @author Ravenspire
 * 
 * Can anyone see this????
 * 
 */

public class Main extends Canvas implements Runnable, KeyListener
{
	private static final long serialVersionUID = 602801997L;
	
	public static final String TITLE = "Only One Earth";
	public static final String VERSION = "0.2";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	
	private JFrame frame;
	
	private long lastTimer;
	
	private EntityEarth earth;
	public Color graphite = new Color(40, 40, 40);
	
	public ImageIcon imgMenu = new ImageIcon(this.getClass().getResource("/res/menu.png"));
	public ImageIcon imgAboutBut1 = new ImageIcon(this.getClass().getResource("/res/but/button1.png"));
	public ImageIcon imgAboutBut2 = new ImageIcon(this.getClass().getResource("/res/but/button2.png"));


	public Image sprtMenu = imgMenu.getImage();
	public Image sprtBut1 = imgAboutBut1.getImage();
	public Image sprtBut2 = imgAboutBut2.getImage();

	
	private static boolean running = false;
	private boolean inMenu = true;
	private boolean inGame = false;
	private boolean isDebug = true;
	private boolean hasStarted = false;
	
	private int but1X = 596, but1Y = 237, but2X = 31, but2Y = 229;
	
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
	}
	
	public synchronized void start()
	{
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop()
	{
		running = false;
	}
	
	public void init()
	{
		earth = new EntityEarth();
		hasStarted = true;
	}

	public void run() 
	{
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;
        lastTimer = System.currentTimeMillis();
        double i1 = 0;
        
        while (running == true) 
        {
            long now = System.nanoTime();
            i1 += (now - lastTime) / nsPerTick;
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
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
 		
		if(bs == null){
			createBufferStrategy(3); //triple buffered to ensure no tearing
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(inMenu){
			g.setColor(graphite);
			g.fillRect(0, 0, 800, 880);//draws the canvas
			
			g.drawImage(sprtMenu, 0, 0, this);
			g.drawImage(sprtBut1, but1X, but1Y, this);
			g.drawImage(sprtBut2, but2X, but2Y, this);
		}
		
		if(inGame){
			g.setColor(graphite);
			g.fillRect(0, 0, WIDTH, HEIGHT);//draws the canvas
			
			if(hasStarted){
				g.setColor(Color.WHITE);
				g.fillRect(0, 450, WIDTH, 50);
				
				g.drawImage(earth.getSprt(), earth.getX(), earth.getY(), this);
				
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
	
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) { //Please use buttons later to enter the game, not a cheaty button press!!!
			if(!inGame){
				inMenu = false;
				inGame = true;
				System.out.println("Game Entered");
			}
		}
		
		if (keyCode == KeyEvent.VK_F3){
			isDebug = !isDebug;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}

}