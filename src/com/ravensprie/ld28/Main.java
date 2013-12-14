package com.ravensprie.ld28;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

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

public class Main extends Canvas implements Runnable
{
	private static final long serialVersionUID = 602801997L;
	
	public static final String TITLE = "LD28";
	public static final String VERSION = "0.01";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 450;
	
	private JFrame frame;
	
	private long lastTimer;
	
	private EntityEarth earth;
	
	private static boolean running = false;
	private boolean inGame = true;
	
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
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
	}
	
	public synchronized void start()
	{
		running = true;
		init();
		new Thread(this).start();
	}
	
	public synchronized void stop()
	{
		running = false;
	}
	
	public void init()
	{
		earth = new EntityEarth();
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
		
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int mx = (int) b.getX();
		int my = (int) b.getY();
 		
		if(bs == null){
			createBufferStrategy(3); //triple buffered to ensure no tearing
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(inGame){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 800, 880);//draws the canvas
			
			g.drawImage(earth.getSprt(), earth.getX(), earth.getY(), this);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}

}
