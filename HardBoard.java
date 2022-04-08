import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.*;
import javax.swing.JPanel;
import java.io.*;


public class HardBoard extends JPanel implements Runnable, MouseListener
{
	Random randX = new Random();
	//booleans
	boolean ingame = true;
	boolean[] showBrick = new boolean[64];
	boolean padRight = false;
	boolean padLeft = false;
	
	
	private Dimension a;
	//integers for board 
	int boardWidth=600;
	int boardHeight=500;
	//integers for ball
	int ballX = randX.nextInt(400);
	int ballY = 200;
	int xVelo = 12;
	int yVelo = 13;
	int ballWidth = 15;
	//integers for paddle
	int paddleX = boardWidth - 300;
	int paddleY = boardHeight - 100;
	int paddleWidth = 50;
	int paddleHeight = 10;
	//lives and score
	int lives = 3;
	int score = 0;
	//powerdown "-" sign
	int divX = 200;
	int divY = 250;
	int divH = 5;
	int divW = 30;
	//powerup (oval)
	int powerX = 500;
	int powerY = 225;
	int powerRad = 30;
	int powerH = 50;
	

	private Thread animator;

	Brick[] bricks = new Brick[64];
	
 
//board
 
    public HardBoard()
    {
    	addKeyListener(new TAdapter());
        addMouseListener(this);
        setFocusable(true);
        a = new Dimension(boardWidth, boardHeight);
        setBackground(Color.black);
      
        if (animator == null || !ingame) {
        	animator = new Thread(this);
            animator.start();
            }
                    
  
        setDoubleBuffered(true);
        
        int brickX = 40;
        int brickY = 20;
        int count = 1;
        
        for(int i = 0; i < bricks.length; i++) {
        	bricks[i] = new Brick(brickX, brickY, 50, 10);
        	showBrick[i] = true;
        	brickX += 65;
        	if (count % 8 == 0) {
        		brickX = 40;
        		brickY += 20;
        	}
        	count++;
        }
    }
    //draws board, message, paddle, ball and bricks
    public void paint(Graphics g) {
    	super.paint(g);
    	g.setColor(Color.white);
    	g.fillRect(0, 0, a.width, a.height);


        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.black);
        g.setFont(small);
        g.drawString("Score: " + score, 525, a.height-60);
        g.drawString("Lives: " + lives, 10, a.height-60);
        g.drawString("Hard", 275, a.height-60);
        g.drawString("Oval is good", 275, a.height-45);
        g.drawString(" -- is bad  ", 275, a.height - 30);
        
        if (lives == 0) {
        	 g.drawString("Game Over! Score: " + score, 225, 250);
        }
        if (score == 640) {
        	g.drawString("You Win!!! Score: " + score, 225, 250);
        }
//       if (score == 320) {
//    	   paddleWidth += 50;
//    	   
//       }
//        while (score >= 320) {
//        	g.fillOval(powerX, powerY, powerW, powerH);
//        }
       
        for (int i = 0; i < bricks.length; i++) {
        	if (showBrick[i] == true) {
        		g.fillRect(bricks[i].x, bricks[i].y, bricks[i].w, bricks[i].h);
        	}
        }
        
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
        g.fillOval(ballX, ballY, ballWidth, ballWidth);
        g.fillRect(divX, divY, divW, divH);
        g.fillOval(powerX, powerY, powerRad, powerH);

        if (ingame) {
        	ballBounce();
        	if(padRight) {
        		paddleX += 25;
        		}
        	if(padLeft) {
        		paddleX -= 25;
        		}
        	if (paddleX > 550) {
        		paddleX = 550;
        	}
        	if (paddleX < 0) {
        		paddleX = 0;
        	}
        	}
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        }
    
    //checks collisions and changes direction of ball
    public void ballBounce() {
    	if(ballX > boardWidth - 20 || ballX < 0) {
    		xVelo = xVelo * -1;
    	}
    	if (ballY < 0) {
    		yVelo = yVelo * -1;
    	}
    	if (ballY > boardHeight) {
    		ballX = randX.nextInt(400);
    		ballY = 200;
    		lives -= 1;
    	}
    	if(ballX+ballWidth > divX && ballX < divX + divW && ballY + ballWidth > divY && ballY < divY + divH) {
    		divX -= 1000;
    		divY += 1000;
    		paddleWidth -= 20;
    		ballWidth -= 10;
    	}
    	if(ballX+ballWidth > powerX && ballX < powerX + divW && powerY + ballWidth > powerY && ballY < powerY + powerH) {
    		powerX -= 1000;
    		powerY += 1000;
    		paddleWidth += 20;
    		ballWidth += 10;
    	}
//    	if(ballX+ballWidth > powerX && ballX < powerX + powerW && ballY + ballWidth > powerY && ballY < powerY + powerH) {
//    		lives += 1;
//    		powerX = 550;
//    		powerY = 1000;
//    	}
    	if(ballX+ballWidth > paddleX && ballX < paddleX + paddleWidth && ballY + ballWidth > paddleY && ballY < paddleY + paddleHeight) {
    		yVelo = yVelo * -1;
    	}
    	for(int i = 0; i < bricks.length; i++) {
    		if(showBrick[i] == true && ballX + ballWidth > bricks[i].x && ballX < bricks[i].x + bricks[i].w && ballY + ballWidth > bricks[i].y && ballY < bricks[i].y + bricks[i].h) {
    			yVelo = yVelo * -1;
    			showBrick[i] = false;
    			score += 10;
    		}
    	}
    	ballX += xVelo;
    	ballY += yVelo;
    	
    	if (lives == 0 || score == 640) {
    		xVelo = 0;
    		yVelo = 0;
    	}
    	

    }
    //key events
    private class TAdapter extends KeyAdapter {
    	public void keyReleased(KeyEvent e) {
    		int key = e.getKeyCode();
    		padRight=false;
    		padLeft=false;
    		}
    	public void keyPressed(KeyEvent e) {
    		int key = e.getKeyCode();
    		if(key==39){
    			padRight = true;
    		}
    		if(key==37) {
    			padLeft = true;
    		}
    		
    	}
    	
    }
    //mouse event
    public void mousePressed(MouseEvent e) {
    	int x = e.getX();
    	int y = e.getY();
    	ingame = true;
    	}
    public void run() {
    	long beforeTime, timeDiff, sleep;

    	beforeTime = System.currentTimeMillis();
    	int delay = 50;
    	long time = System.currentTimeMillis();
    	while (true) {
    		repaint();
    		try {
    			time += delay;
    			Thread.sleep(Math.max(0,time - 
    					System.currentTimeMillis()));
      } catch (InterruptedException e) {
    	  System.out.println(e);
      }//end catch
    }//end while loop

    



}

//not used but have to be in program

@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}//end of run

}//end of class

         