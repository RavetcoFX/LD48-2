package com.ravensprie.ld28.entity;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.ravensprie.ld28.Main;

public class EntityEarth 
{
	
	private Main main;
	public ImageIcon imgOne = new ImageIcon(this.getClass().getResource("/res/earth.png"));
	public Image sprt;
	private static final int START_X = 330, START_Y = 200;
	
	public boolean isAlive;
	public static int x, y, width, height;
	public int health = 100;
	private int tickCount;
	private boolean goUp = true;
	private int cnt = 0;
	
	
	public EntityEarth()
	{
		new Thread().start();
		sprt = imgOne.getImage();
		isAlive = true;
		x = START_X;
		y = START_Y;
		width = sprt.getWidth(null);
		height = sprt.getHeight(null);
	}
	
	public void tick()
	{
		if(isAlive){
			if(goUp){ cnt++; y--; }
			else{ cnt--; y++; }
			if(cnt==40){ goUp=false; }
			if(cnt==0){ goUp=true; }
		}
	}
	
	public Image getSprt()
	{
		return sprt;
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
