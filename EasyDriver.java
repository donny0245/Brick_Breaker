import javax.swing.JFrame;

public class EasyDriver extends JFrame{
//main driver that makes JFrame and runs program
	
	public EasyDriver() {
		add(new EasyBoard());
		setTitle("Brick Breaker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);	
	}
	
	public static void main(String[] args) {
		EasyDriver easyDriver = new EasyDriver();
	}
}
