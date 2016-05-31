import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class DeveloperBtn extends JButton{
	BufferedImage bi;
	BufferedImage bi2;
	//private BufferedImage read_bi;
	public boolean press;
	public DeveloperBtn(){
		initial();
		press = false;
	}
	public BufferedImage getImage_true(){
		return this.bi2;
	}
	public BufferedImage getImage_false(){
		return this.bi;
	}
	public void initial(){
		File f1 = new File("Ima/Developer.png");
		try {
			bi = ImageIO.read(f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File f2 = new File("Ima/Developer_1.png");
		try {
			bi2 = ImageIO.read(f2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void set_press(boolean b){
		this.press = b;
	}
}
