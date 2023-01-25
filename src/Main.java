import java.awt.Color;
import javax.swing.JFrame;

public class Main {

	/**
	 * on run, create a new window and add a Gameplay object in it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// creating new window
		JFrame obj = new JFrame();
		// creating a new gameplay object
		Gameplay stage = new Gameplay();
		
		// setting properties of the window
		obj.setBounds(0, 0, 1200, 770);
		obj.setBackground(Color.black);
		obj.setResizable(true);
		obj.setTitle("PacMan - Computer Science IA");
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// adding the gameplay object to the window
		obj.add(stage);
		obj.setVisible(true);
	}

}