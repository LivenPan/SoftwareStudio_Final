import java.io.IOException;

import javax.swing.JFrame;

public class GameStage extends JFrame{
	public GameStage() throws IOException{
		setLayout(null);
		setSize(450,700);
		setLocation(100,100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Menu menu = new Menu();
		menu.setBounds(0, 0, 450, 750);
		add(menu);
		setVisible(true);
		Thread thread = new Thread(menu);
		thread.start();
	}
}
