package com.ravensprie.ld28.entity;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.ravensprie.ld28.Main;

public class EntityEarth 
{
	
	private Main main;
	public ImageIcon imgA = new ImageIcon(this.getClass().getResource("/res/air.png"));
	public ImageIcon imgW = new ImageIcon(this.getClass().getResource("/res/water.png"));
	public ImageIcon imgL = new ImageIcon(this.getClass().getResource("/res/land.png"));
	public ImageIcon imgG = new ImageIcon(this.getClass().getResource("/res/earthG.png"));

	public Image sprt1;
	public Image sprt2;
	public Image sprt3;
	public Image sprt4;

	private static final int START_X = 330, START_Y = 140;
	
	public boolean isAlive;
	public static int x, y, width, height;
	public int health = 100;
	private int tickCount;
	private boolean goUp = true;
	private int cnt = 0;
	private int bobHeight = 20;
	
	
	public EntityEarth()
	{
		new Thread().start();
		sprt1 = imgA.getImage();
		sprt2 = imgW.getImage();
		sprt3 = imgL.getImage();
		sprt4 = imgG.getImage();
		isAlive = true;
		x = START_X;
		y = START_Y;
		width = sprt1.getWidth(null);
		height = sprt1.getHeight(null);
	}
	
	public void tick()
	{
		if(isAlive){
			if(goUp){ cnt++; y--; }
			else{ cnt--; y++; }
			if(cnt==bobHeight){ goUp=false; }
			if(cnt==0){ goUp=true; }
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x + 34, y + 32, width - 64, height - 64);
	}

}
