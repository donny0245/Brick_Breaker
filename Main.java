import java.util.Scanner;


public class Main{
	
	public static void main(String[] args) {
		Scanner scnr  = new Scanner(System.in);
		System.out.print("Enter a difficulty: Easy, Medium or Hard");
		String difficulty; 
		difficulty = scnr.next();
		
		while (!difficulty.equals("Easy") && !difficulty.equals("Medium") && !difficulty.equals("Hard")) {
			System.out.println("Invalid game difficulty");
			System.out.println("Pick a game difficulty: Easy, Medium, or Hard");
			difficulty = scnr.next();
		}
		
		if (difficulty.equals("Easy")) {
			EasyDriver easyDriver = new EasyDriver();
		}
		if (difficulty.equals("Medium")) {
			MediumDriver mediumDriver = new MediumDriver();
		}
		if (difficulty.equals("Hard")) {
			HardDriver hardDriver = new HardDriver();
		}	
		
	}
}
