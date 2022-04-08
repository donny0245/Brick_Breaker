import javax.swing.JFrame;

public class MediumDriver extends JFrame {

	public MediumDriver() {
		add(new MediumBoard());
		setTitle("Brick Breaker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);	
	}
	
	public static void main(String[] args) {
		MediumDriver mediumDriver = new MediumDriver();
	}
}

