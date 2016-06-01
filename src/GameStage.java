import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;

public class GameStage extends JFrame{
	//stage=0 選單 stage=1 start/continue stage=2 developer
	public int stage = 0;
	private boolean De_In = false; 
	private Menu menu;
	private DeveloperPage de;
	private Thread thread;
	private Thread thread2;
	public GameStage() throws IOException{
		
		setLayout(null);
		setSize(450,700);
		setLocation(100,100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initial();
		
	}
	public void initial() throws IOException{
		System.out.println("GS");
		if(stage == 0){
			if(this.De_In == true){
				de.stopThread();
				//thread2.interrupt();
				remove(de);
				setDe(false);
			}
			else{}
			menu = new Menu(this);
			menu.setBounds(0, 0, 450, 750);
			add(menu);
			setVisible(true);
			thread = new Thread(menu);
			thread.start();
		}
		else if(stage == 1){
			
		}
		else if(stage == 2){
			menu.stopThread();
			//thread.interrupt();
			remove(menu);
			setDe(true);
			de = new DeveloperPage(this);
			de.setBounds(0, 0 , 450, 750);
			add(de);
			setVisible(true);
			Thread thread2 = new Thread(de);
			thread2.start();
		}
	}
	public void setstage(int n){
		this.stage = n;
	}
	public void setDe(boolean b){
		this.De_In = b;
	}
}
