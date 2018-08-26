package Play;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Gameplay extends JPanel implements KeyListener,ActionListener
{
	
	private int [] snakexlength = new int[750];  //array for snake pos(total length) frm x-axis.
	private int [] snakeylength = new int[750];
	
	private boolean left=false;  //detecting which dir snake is moving
	private boolean right=false;  //if left is true,dir of mth in lft dir
	private boolean up=false; 
	private boolean down=false; 
	
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;   //dir of mouth of snake
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	
	private int lengthofsnake =3;  //initial length of snake
		
	private Timer timer;      //manage speed of snake
	private int delay=150;// of snake
	
	private ImageIcon snakeimage;  //var for snake body 
	
	private int[] enemyxpos = {25,50,75,100,125,150,175,200,250,275,300,325,350,375,400,425,450,500,525,550,575,
			                    600,625,650,675,700,725,750,775,800,825,850};                       //default pos fr fud pickup
	private int[] enemyypos= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,
			                    600,625};
	private ImageIcon enemyimage;
	private Random random=new Random();
	private int xpos=random.nextInt(34);  //total x pos
	private int ypos=random.nextInt(23);   //total y pos
	
	private int score=0;
	private int moves=0; //initial position
	private ImageIcon titleImag;
	
	public Gameplay()  //constructor
	{
		addKeyListener(this);  //this->object
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer=new Timer(delay,this);
		timer.start();
		
	}
	public void paint(Graphics g)
	{
		if(moves==0)      //just game is abt to begins 
		{
			snakexlength[2]=50;
			snakexlength[1]=75;   //distance of body part(index 0) frm x-axis
			snakexlength[0]=100;  //distance of mouth frm x-axis
			
			snakeylength[2]=100;
			snakeylength[1]=100;
			snakeylength[0]=100;   //distance of mouth frm y-axis
		 }
		//draw title image border
		g.setColor(Color.PINK);
		g.drawRect(16,10,865,55);
		
		//draw title image
		ImageIcon titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this,g,25,11);  //x-axis & y-axis
		
		//draw border for gameplay area
		g.setColor(Color.YELLOW);
		g.drawRect(16,74,865,581);
		
		//draw background for gameplay area
		g.setColor(Color.GREEN);
		g.fillRect(17,75,864,580);
		
		//draw score
		g.setColor(Color.GREEN);
		g.setFont(new Font("arial",Font.PLAIN,14));
		g.drawString("Scores: "+score,780,30);
		
		
		rightmouth=new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]); //position of mouth of snake at index 0
		
		//detect direction of mouth
		for(int a=0;a<lengthofsnake;a++)  
		{
			if(a==0 && right)
			{
				rightmouth=new ImageIcon("rightmouth.png");
			rightmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]);
			}
			
			if(a==0 && left)
			{
				leftmouth=new ImageIcon("leftmouth.png");
			leftmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]);
			}
			
			if(a==0 && down)
			{
				downmouth=new ImageIcon("downmouth.png");
			downmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]);
			}
			
			if(a==0 && up)
			{
				upmouth=new ImageIcon("upmouth.png");
			upmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]);
			}
			
			if(a!=0) //body of snake
			{
				snakeimage=new ImageIcon("snakeimage.png");
			 snakeimage.paintIcon(this,g,snakexlength[a],snakeylength[a]);
			}
				
		 }
		
		enemyimage=new ImageIcon("enemy.png");  //draw food
		if(enemyxpos[xpos]==snakexlength[0] && enemyypos[ypos]==snakeylength[0]) //check food colide wth head[0]
		{   score++;
			lengthofsnake++;
			xpos=random.nextInt(34); //regenerate food in nxt pos
			ypos=random.nextInt(23);
			
		}
		enemyimage.paintIcon(this, g,enemyxpos[xpos], enemyypos[ypos]);
		
		for(int b=1;b<lengthofsnake;b++)
		{
			if(snakexlength[b]==snakexlength[0]&&snakeylength[b]==snakeylength[0]) //condition for collision
			{
				right=false;
			left=false;
			up=false;
			down=false;
			g.setColor(Color.RED);
			g.setFont(new Font("arial",Font.BOLD,50));
			g.drawString("GAME OVER !", 300, 400);
			
			g.setFont(new Font("arial",Font.ITALIC,20));
			g.drawString("press Enter to Start", 400, 500);
			g.setColor(Color.ORANGE);
			
			}
			
		}
		g.dispose();
		
	  }
	public void actionPerformed(ActionEvent e)
	{
		timer.start();
		
//movement in right dir		
		if(right)       //check whch var is true
		{
			for(int r=lengthofsnake-1;r>=0;r--)
			{
				snakeylength[r+1]=snakeylength[r];  //shift pos y of head to nxt index
				
			}
			
			//set pos of snake x length
			for(int r=lengthofsnake;r>=0;r--)
			{
				if(r==0)
				{
				snakexlength[r]=snakexlength[r]+25;
				}
				else
				{
					snakexlength[r]=snakexlength[r-1];  //shift x pos 
				}
				if(snakexlength[r]>850)  //if head touch right boundry
				{
					snakexlength[r]=25;  //shift head to left again
				}				
			}
			repaint();
		}
		
		if(left)
		{
			for(int r=lengthofsnake-1;r>=0;r--)
			{
				snakeylength[r+1]=snakeylength[r];
				
			}
			for(int r=lengthofsnake;r>=0;r--)
			{
				if(r==0)
				{
				snakexlength[r]=snakexlength[r]-25;
				}
				else
				{
					snakexlength[r]=snakexlength[r-1];
				}
				if(snakexlength[r]<25)
				{
					snakexlength[r]=850;
				}				
			 }
			repaint();
		
        	}
		
		if(up)
		{
			for(int r=lengthofsnake-1;r>=0;r--)
			{
				snakexlength[r+1]=snakexlength[r];
				
			}
			for(int r=lengthofsnake;r>=0;r--)
			{
				if(r==0)
				{
				snakeylength[r]=snakeylength[r]-25;
				}
				else
				{
					snakeylength[r]=snakeylength[r-1];
				}
				if(snakeylength[r]<75)
				{
					snakeylength[r]=625;
				}				
			 }
			repaint();
		
        	}
		
		if(down)
		{
			for(int r=lengthofsnake-1;r>=0;r--)
			{
				snakexlength[r+1]=snakexlength[r];
				
			}
			for(int r=lengthofsnake;r>=0;r--)
			{
				if(r==0)
				{
				snakeylength[r]=snakeylength[r]+25;
				}
				else
				{
					snakeylength[r]=snakeylength[r-1];
				}
				if(snakeylength[r]>625)
				{
					snakeylength[r]=75;
				}				
			 }
			repaint();
		
        	}
      }
		
		
	
	public void keyPressed(KeyEvent e)
	{
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) //right key is pressed
		{
			 moves++;
			 right=true; 
			 
			 /*if mouth in right dir,snake not move in
              left dir if left key is pressed.*/
			 if(!left)       //if left is not true
			 {
				 right=true;    //make right true
				 
			 }                 //if mouth in right dir,snake not move in
			                   // left dir if left key is pressed.
			 else
			 {
				 right=false;
				 left=true;
			 }
			 up=false; 
			 down=false;
			
		 }
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
		{
			 moves++;
			 left=true; 
			 if(!right)
			 {
				 left=true;
			 }                
			                   
			 else
			 {
				 left=false;
				 right=true;
			 }
			 up=false; 
			 down=false;
			
		 }
		
	  	
	if(e.getKeyCode() == KeyEvent.VK_UP) 
	{
		 moves++;
		 up=true; 
		 if(!down)
		 {
			 up=true;
		 }                
		                   
		 else
		 {
			 up=false;
			 down=true;
		 }
		 left=false; 
		 right=false;
		
	  }
	
	if(e.getKeyCode() == KeyEvent.VK_DOWN) 
	{
		 moves++;
		 down=true; 
		 if(!up)
		 {
			 down=true;
		 }                
		                   
		 else
		 {
			 down=false;
			 up=true;
		 }
		 left=false; 
		 right=false;
		
	 }
	    if(e.getKeyCode()== KeyEvent.VK_ENTER)
	   {
	         moves=0;
	         score=0;
	         lengthofsnake=3;
	         repaint();
	    }
	    if(e.getKeyCode() == KeyEvent.VK_SPACE)
	    {
	    	left=false;
	    	right=false;
	    	up=false;
	    	down=false;
	    	
	    }
   }
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
