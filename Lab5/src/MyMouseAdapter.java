import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter 
{
	private Random generator = new Random();
	private int randomNumber = -1;

	public void mousePressed(MouseEvent e) 
	{
		switch (e.getButton()) 
		{
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) 
			{
				c = c.getParent();
				if (c == null) 
				{
					return;
				}
			}

			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();

			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);

			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		switch (e.getButton()) 
		{
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) 
			{
				c = c.getParent();
				if (c == null) 
				{
					return;
				}
			}

			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) 
			{
				//Had pressed outside
				//Do nothing
				System.out.println("You pressed the line");
			} 
			else 
			{
				if ((gridX == -1) || (gridY == -1)) 
				{
					//Is releasing outside
					//Do nothing
					System.out.println("You released the mouse outside the cells");
				} 
				else 
				{
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) 
					{
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
						System.out.println("Released button on a different cell");
					} 
					else 
					{
						//Released the mouse button on the same cell where it was pressed
						//Question 5
						if ((gridX == 0) && (gridY == 0)) 
						{
							Color newColor = null;

							int newRandomNumber = generator.nextInt(5);
							while(this.randomNumber == newRandomNumber)
							{
								newRandomNumber = generator.nextInt(5);
							}
							this.randomNumber = newRandomNumber;

							for(int i = 4; i <= 6; i++)
							{
								for(int j = 4; j <= 6; j++)
								{
									switch(this.randomNumber)
									{
									case 0:
										newColor = Color.YELLOW;
										break;
									case 1:
										newColor = Color.MAGENTA;
										break;
									case 2:
										newColor = Color.BLACK;
										break;
									case 3:
										newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									case 4:
										newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
										break;
									}

									gridX = j;
									gridY = i;

									myPanel.colorArray[gridX][gridY] = newColor;
									myPanel.repaint();
								}
							}

							System.out.println("The diagonal should paint");
						} 
						else if (gridY == 10) 
						{
							//Question 6
							System.out.println("Middle 3x3 cells should paint");
						} 
						//Question 3
						else if ((gridX == 0) && (gridY != 0) && (gridY != 10))
						{
							Color newColor = null;

							int newRandomNumber = generator.nextInt(5);
							while(this.randomNumber == newRandomNumber)
							{
								newRandomNumber = generator.nextInt(5);
							}
							this.randomNumber = newRandomNumber;

							for(int i = 1; i < myPanel.mouseDownGridX + 10; i++) //ADD THIS TO REST mouse stuff
							{
								switch(this.randomNumber)
								{
								case 0:
									newColor = Color.YELLOW;
									break;
								case 1:
									newColor = Color.MAGENTA;
									break;
								case 2:
									newColor = Color.BLACK;
									break;
								case 3:
									newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 4:
									newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								}

								gridX = i;

								myPanel.colorArray[gridX][myPanel.mouseDownGridY] = newColor;
								myPanel.repaint();
							}

							System.out.println("The row should be painted");
						}
						//Question 4
						else if ((gridX != 0) && (gridY == 0)) //FIXXX
						{
							Color newColor = null;

							int newRandomNumber = generator.nextInt(5);
							while(this.randomNumber == newRandomNumber)
							{
								newRandomNumber = generator.nextInt(5);
							}
							this.randomNumber = newRandomNumber;

							for(int i = 1; i <= 9; i++)
							{
								switch(this.randomNumber)
								{
								case 0:
									newColor = Color.YELLOW;
									break;
								case 1:
									newColor = Color.MAGENTA;
									break;
								case 2:
									newColor = Color.BLACK;
									break;
								case 3:
									newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								case 4:
									newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
									break;
								}

								gridY = i;

								myPanel.colorArray[myPanel.mouseDownGridX][gridY] = newColor;
								myPanel.repaint();
							}

							System.out.println("The column should be painted");
						}
						else 
						{
							//On the grid other than on the left column and on the top row:
							Color newColor = null;

							int newRandomNumber = generator.nextInt(5);
							while(this.randomNumber == newRandomNumber)
							{
								newRandomNumber = generator.nextInt(5);
							}
							this.randomNumber = newRandomNumber;

							switch(this.randomNumber)
							{
							case 0:
								newColor = Color.YELLOW;
								break;
							case 1:
								newColor = Color.MAGENTA;
								break;
							case 2:
								newColor = Color.BLACK;
								break;
							case 3:
								newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
								break;
							case 4:
								newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
								break;
							}

							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							myPanel.repaint();
						}
					}
				}
			}
			myPanel.repaint();

			break;
		//Question 7
		case 3:		//Right mouse button
			System.out.println("All non gray cells should changed to 3 of new colors");
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}