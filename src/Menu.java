import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu extends JPanel implements MouseListener, Runnable {
	private static final long serialVersionUID = 1L;
	public int save = 0; // 之前有沒有存檔記錄
	public String str[][];
	private Scanner scanner;
	private GameStage gs;
	private boolean running = true;
	BufferedImage bi;
	private StartBtn Start;
	private ContinueBtn Continue;
	private DeveloperBtn Developer;

	public Menu(GameStage f) throws IOException {
		this.gs = f;
		setLayout(null);
		initButton();
		initialIma();
		Initialtxt();
		initialAudio();
		addMouseListener(this);
	}

	private void initialAudio() throws MalformedURLException {
		// TODO Auto-generated method stub
		URL cb;
		AudioClip aau;
		File f = new File("Ima/final_open.wav"); 
		cb = f.toURI().toURL(); 
		aau = Applet.newAudioClip(cb); 
		aau.play();
		System.out.println("Music");
		

	}

	private void initialIma() {
		File f1 = new File("Ima/menu.png");
		try {
			bi = ImageIO.read(f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void Initialtxt() throws IOException {
		str = new String[8][2];
		try {
			File file = new File("Ima/save.txt");
			scanner = new Scanner(file);
			int num = 0;

			while (scanner.hasNext()) {
				str[num] = scanner.next().split(":"); // 字串分割 存入陣列
				num++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanner.close();
		if (Integer.parseInt(str[0][1]) == 0) {
			save = 0;
		} else {
			save = 1;
		}
	}

	private void initButton() {
		Start = new StartBtn();
		Continue = new ContinueBtn();
		Developer = new DeveloperBtn();

		add(Start);
		add(Continue);
		add(Developer);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bi, 0, 0, 450, 700, null);
		if (Start.press == true) {
			this.repaint();
			g.drawImage(Start.getImage_true(), 135, 300, 180, 65, null);
		} else {
			this.repaint();
			g.drawImage(Start.getImage_false(), 135, 300, 180, 65, null);
		}
		if (Continue.press == true) {
			this.repaint();
			g.drawImage(Continue.getImage_true(), 135, 390, 180, 65, null);
		} else {
			this.repaint();
			g.drawImage(Continue.getImage_false(), 135, 390, 180, 65, null);
		}
		if (Developer.press == true) {
			this.repaint();
			g.drawImage(Developer.getImage_true(), 135, 480, 180, 65, null);
		} else {
			this.repaint();
			g.drawImage(Developer.getImage_false(), 135, 480, 180, 65, null);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// Start
		if (e.getX() > 135 && e.getX() < 315 && e.getY() > 300 && e.getY() < 365) {
			Start.set_press(true);
			Continue.set_press(false);
			Developer.set_press(false);
			System.out.println("Start");
			if (save == 1) {
				int dialogButton = 0;
				dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure to restart?", "Confirm", dialogButton);
				if (dialogButton == JOptionPane.YES_OPTION) {
					// txt值清零
					for (int i = 0; i < 7; i++) {
						str[i][1] = Integer.toString(0);
						// System.out.println(str[i][1]);
					}
				}
			}
		}
		// Continue
		else if (e.getX() > 135 && e.getX() < 315 && e.getY() > 390 && e.getY() < 455) {
			Continue.set_press(true);
			Start.set_press(false);
			Developer.set_press(false);
			System.out.println("Continue");
			if (save == 0) {
				int dialogButton = -1;
				dialogButton = JOptionPane.showConfirmDialog(null, "Sorry, you don't have any record.", "Confirm",
						dialogButton);
				if (dialogButton == JOptionPane.CLOSED_OPTION) {
				}
			}
		}
		// Developer
		else if (e.getX() > 135 && e.getX() < 315 && e.getY() > 480 && e.getY() < 545) {
			Developer.set_press(true);
			Start.set_press(false);
			Continue.set_press(false);
			System.out.println("Developer");
			gs.setstage(2);
			try {
				gs.initial();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Start.set_press(false);
			Continue.set_press(false);
			Developer.set_press(false);
			System.out.println("nothing");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// Start
		/*
		 * if(e.getX() > 135 && e.getX() < 315 && e.getY() > 350 && e.getY() <
		 * 415){ System.out.println("Start_In"); Start.set_press(true); }
		 * //Continue else if(e.getX() > 135 && e.getX() < 315 && e.getY() > 420
		 * && e.getY() < 485){ System.out.println("Continue_In");
		 * Continue.set_press(true); } //Developer else if(e.getX() > 135 &&
		 * e.getX() < 315 && e.getY() > 490 && e.getY() < 555){
		 * System.out.println("Developer_In"); Developer.set_press(true); }
		 * else{ System.out.println("nothing_In"); }
		 */
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		// Start
		/*
		 * if(!(e.getX() > 135 && e.getX() < 315 && e.getY() > 350 && e.getY() <
		 * 415)){ //System.out.println("Out"); Start.set_press(false); }
		 * //Continue else if(!(e.getX() > 135 && e.getX() < 315 && e.getY() >
		 * 420 && e.getY() < 485)){ Continue.set_press(false); } //Developer
		 * else if(!(e.getX() > 135 && e.getX() < 315 && e.getY() > 490 &&
		 * e.getY() < 555)){ Developer.set_press(false); } else{ }
		 */
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			this.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void stopThread() 
    {
        this.running = false;
    }
}
