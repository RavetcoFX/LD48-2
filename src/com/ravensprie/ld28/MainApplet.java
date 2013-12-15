package com.ravensprie.ld28;

import java.applet.Applet;
import java.awt.BorderLayout;

public class MainApplet extends Applet
{
	private static final long serialVersionUID = 1L;
	
	private Main main = new Main();
	
	public void init() 
	{
		setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
	}
	
	public void start() {
		main.start();
	}

	public void stop() {
		main.stop();
	}

}
