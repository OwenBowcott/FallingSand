
/**
 * Write a description of class SandLab here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int LAVA = 4;
  public static final int OBSIDIAN = 5;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[6];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[LAVA] = "Lava";
    names[OBSIDIAN] = "Obsidian";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
      grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
    for(int row = 0; row < grid.length; row++)//might need to switch 80 and 120, if there is an error
    {
        for(int col = 0; col < grid[row].length; col++)//check 0
        {
            if(grid[row][col] == EMPTY)
            {
                display.setColor(row, col, Color.BLACK);
            }
            else if(grid[row][col] == METAL)
            {
                display.setColor(row, col, Color.GRAY);
            }
            else if(grid[row][col] == SAND)
            {
                display.setColor(row, col, Color.YELLOW);
            }
            else if(grid[row][col] == WATER)
            {
                display.setColor(row, col, Color.BLUE);
            }
            else if(grid[row][col] == LAVA)
            {
                display.setColor(row, col, Color.RED);
            }
            else if(grid[row][col] == OBSIDIAN)
            {
                display.setColor(row, col, Color.magenta);
            }
        }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
      int row = (int)(Math.random() * (grid.length - 1));
      int col = (int)(Math.random() * (grid[0].length));
      if(grid[row][col] == SAND && grid[row+1][col] == EMPTY)
      {
          grid[row][col] = EMPTY;
          grid[row+1][col] = SAND;// -1
      }
      if(grid[row][col] == SAND && grid[row+1][col] == WATER)
      {
          grid[row][col] = WATER;
          grid[row+1][col] = SAND;
      }//combine both sands
      if(grid[row][col] == WATER && grid[row+1][col] == WATER)
      {
          grid[row][col] = WATER;
          grid[row+1][col] = WATER;// -1
      }
      if(grid[row][col] == WATER)
      {
          int num = (int)(Math.random() * (4));
          if ((col + 1 <= grid[0].length-1) && (grid[row][col+1] == EMPTY) && (num == 1))//error at row
          {
             grid[row][col] = EMPTY;
             grid[row][col+1] = WATER;
          }
          else if((col - 1 >= 0) &&(grid[row][col-1] == EMPTY) && (num == 2)) 
          {
             grid[row][col] = EMPTY;
             grid[row][col-1] = WATER;
          }
          else if ((row + 1 <= grid.length-1) && (grid[row+1][col] == EMPTY) && (num == 3)) 
          {
             grid[row][col] = EMPTY;
             grid[row+1][col] = WATER;
          }
      }
      if(grid[row][col] == LAVA && grid[row+1][col] == LAVA)
      {
          grid[row][col] = LAVA;
          grid[row+1][col] = LAVA;// -1
      }
      if(grid[row][col] == LAVA && grid[row+1][col] == WATER)
      {
          grid[row][col] = EMPTY;
          grid[row+1][col] = OBSIDIAN;// -1
      }
      if(grid[row][col] == WATER && grid[row+1][col] == LAVA)
      {
          grid[row][col] = EMPTY;
          grid[row+1][col] = OBSIDIAN;// -1
      }
      if(grid[row][col] == LAVA)
      {
          int num = (int)(Math.random() * (4));
          if ((col + 1 <= grid[0].length-1) && (grid[row][col+1] == EMPTY) && (num == 1))//error at row
          {
             grid[row][col] = EMPTY;
             grid[row][col+1] = LAVA;
          }
          else if((col - 1 >= 0) &&(grid[row][col-1] == EMPTY) && (num == 2)) 
          {
             grid[row][col] = EMPTY;
             grid[row][col-1] = LAVA;
          }
          else if ((row + 1 <= grid.length-1) && (grid[row+1][col] == EMPTY) && (num == 3)) 
          {
             grid[row][col] = EMPTY;
             grid[row+1][col] = LAVA;
          }
      }
   }

  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
