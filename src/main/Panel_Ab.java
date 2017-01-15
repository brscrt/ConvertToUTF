package main;

import java.awt.EventQueue;

import javax.swing.JPanel;

public abstract class Panel_Ab extends JPanel implements Panel_IO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void invoke() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				display();
			}
		});
	}
	
	@Override
	public abstract void display();

	
}
