import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DeveloperPage extends JPanel implements MouseListener, Runnable {
	private GameStage gs;
	private boolean running = true;
	private int page = 1;
	BufferedImage bi;
	BufferedImage bi2;
	BufferedImage bi3;
	BufferedImage bi4;
	BufferedImage bi5;
	BufferedImage bi6;
	private NextBtn Next;
	private BackBtn Back;

	public DeveloperPage(GameStage f) {
		this.gs = f;
		setLayout(null);
		initButton();
		initialIma();
		addMouseListener(this);
	}

	private void initialIma() {
		// TODO Auto-generated method stub
		File f1 = new File("Ima/de_4.png");
		try {
			bi = ImageIO.read(f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f2 = new File("Ima/de_2.png");
		try {
			bi2 = ImageIO.read(f2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f3 = new File("Ima/de_3.png");
		try {
			bi3 = ImageIO.read(f3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f4 = new File("Ima/de_1.png");
		try {
			bi4 = ImageIO.read(f4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f5 = new File("Ima/de_5.png");
		try {
			bi5 = ImageIO.read(f5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f6 = new File("Ima/de_6.png");
		try {
			bi6 = ImageIO.read(f6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initButton() {
		// TODO Auto-generated method stub
		Next = new NextBtn();
		Back = new BackBtn();

		add(Next);
		add(Back);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(page == 1) g.drawImage(bi, 0, 0, 450, 700, null);
		else if(page == 2) g.drawImage(bi2, 0, 0, 450, 700, null);
		else if(page == 3) g.drawImage(bi3, 0, 0, 450, 700, null);
		else if(page == 4) g.drawImage(bi4, 0, 0, 450, 700, null);
		else if(page == 5) g.drawImage(bi5, 0, 0, 450, 700, null);
		else g.drawImage(bi6, 0, 0, 450, 700, null);
		
		if (Next.press == true) {
			this.repaint();
			g.drawImage(Next.getImage_true(), 300, 600, 120, 45, null);
		} else {
			this.repaint();
			g.drawImage(Next.getImage_false(), 300, 600, 120, 45, null);
		}
		if (Back.press == true) {
			this.repaint();
			g.drawImage(Back.getImage_true(), 30, 600, 120, 45, null);
		} else {
			this.repaint();
			g.drawImage(Back.getImage_false(), 30, 600, 120, 45, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// Next
		if (e.getX() > 300 && e.getX() < 420 && e.getY() > 600 && e.getY() < 645) {
			Next.set_press(true);
			Back.set_press(false);
			System.out.println("Next");
			page = page + 1;
			System.out.println(page);
		}
		// Back
		else if (e.getX() > 30 && e.getX() < 150 && e.getY() > 600 && e.getY() < 645) {
			Back.set_press(true);
			Next.set_press(false);
			System.out.println("Back");
			gs.setstage(0);
			try {
				gs.initial();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Next.set_press(false);
			Back.set_press(false);
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

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
