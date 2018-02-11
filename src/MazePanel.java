import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MazePanel extends JPanel implements ActionListener{

    private int gridDim = 10; //grid aka cell size
    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height = Toolkit.getDefaultToolkit().getScreenSize().height-100; // minus 100 to avoid windows appbar overlapping on screen, i'm lazy AF
    private int rows = (int)Math.floor(height/gridDim);
    private int cols = (int)Math.floor(width/gridDim);
    private int numgenerators = 20; //the number of maze generators
    private ArrayList<Cell> grid; //RLY!? this is the grid!
    private ArrayList<ArrayList<Cell>> stack; //stack for every generator
    private Cell[] currents; //this array saves the current position of every generator while it moves to the next element in the grid
    private ArrayList<Color> randColors; // 'cause i'm lazy and it's funny
    Timer timer=new Timer(1, this); //repaint timer

    //constructor
    public MazePanel() {
        grid = new ArrayList<>();
        stack = new ArrayList<>();
        randColors = new ArrayList<>();
        for(int i = 0; i< numgenerators; i++) {
            stack.add(new ArrayList<>());
        }

        currents = new Cell[numgenerators];
        setup();
        timer.start();
    }

    //setup all the things!
    public void setup(){
        for(int j = 0; j<rows; j++) {
            for(int i = 0; i<cols; i++) {
                grid.add(new Cell(i,j));
            }
        }

        for(int x = 0; x<numgenerators; x++){
            currents[x] = grid.get(pickIndex());
            randColors.add(generateCcolor());
        }
    }

    //here comes the fun stuff
    void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //generate the main grid
        for(int i = 0; i<grid.size(); i++) {
            grid.get(i).show(gridDim,g2d);
        }

        //Loop through every current element to move it to the next one
        for(int k = 0; k<currents.length; k++ ) {
            currents[k].setVisitedColor(randColors.get(k)); //setting the color for every generator's maze
            currents[k].visited = true; //mark the current cell as visited
            currents[k].highlight(gridDim, g2d); //highlight the current cell (green)
            Cell next = currents[k].checkNeighbors(grid, cols, rows); //check if there is a neighbor unvisited
            if(next != null) {
                next.visited = true;
                stack.get(k).add(currents[k]); //add the current to the stack
                removeWalls(currents[k], next); //remove walls from current and next
                currents[k] = next; //set the next as the current

            }else if(stack.get(k).size()>0){
                currents[k] = stack.get(k).get(stack.get(k).size()-1); //we dont' have unvisited cell, pop back the last one from the stack and move to it
                Collections.reverse(stack.get(k));
                stack.get(k).remove(stack.get(k).size()-1); //delete it, so we can move to the previous visited one
            }
        }
        //this.repaint();
    }

    //necessary stuff
    public void paint(Graphics g) {
        super.paint(g);
        drawGrid(g);
    }

    //given current and next cell, check and remove walls from each one
    private void removeWalls(Cell a, Cell b) {
        int x = a.i-b.i;
        if(x == 1) {
            a.walls[3] = false;
            b.walls[1] = false;
        }else if(x == -1) {
            a.walls[1] = false;
            b.walls[3] = false;
        }

        int y = a.j-b.j;

        if(y == 1) {
            a.walls[0] = false;
            b.walls[2] = false;
        }else if(y == -1) {
            a.walls[2] = false;
            b.walls[0] = false;
        }
    }

    //generate random color (LAZINESS)
    private Color generateCcolor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        return new Color(r,g,b, 127);
    }

    //Pick a random index as a starting point
    private int pickIndex() {

        //return (((grid.size() - numgenerators + cols)/2) + (int)(Math.random() * ((((grid.size() + numgenerators + cols)/2)-((grid.size() - numgenerators + cols)/2)) + 1)));

        return new Random().nextInt(grid.size());
    }

    //needed for the timer
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==timer){
            repaint();
        }
    }
}