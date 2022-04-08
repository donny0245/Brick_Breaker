import javax.swing.JFrame;

public class HardDriver extends JFrame{
	
	public HardDriver() {
		add(new HardBoard());
		setTitle("Brick Breaker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);	
	}
	
	public static void main(String[] args) {
		HardDriver hardDriver = new HardDriver();
	}
}

