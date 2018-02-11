import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Cell {

    public int i;
    public int j;
    public int x;
    public int y;
    public boolean[] walls = {true, true, true, true}; //top, right, bottom, left
    public boolean visited = false;
    private Color visitedColor = new Color(50, 0, 250, 50);


    public Cell(int _i, int _j) {
        this.i = _i;
        this.j = _j;
    }

    public void show(int size, Graphics2D g) {
        this.x = i * size;
        this.y = j * size;


        if (walls[0]) {
            g.drawLine(x, y, x + size, y);
        }
        if (walls[1]) {
            g.drawLine(x + size, y, x + size, y + size);
        }
        if (walls[2]) {
            g.drawLine(x + size, y + size, x, y + size);
        }
        if (walls[3]) {
            g.drawLine(x, y + size, x, y);
        }

        if (this.visited) {
            Rectangle r = new Rectangle(x, y, size, size);
            //g.drawRect(x,y,size,size);
            g.setColor(visitedColor);
            g.fillRect(x, y, size, size);
            g.setColor(Color.BLACK);
        }
    }

    public Cell checkNeighbors(ArrayList<Cell> grid, int cols, int rows) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        Cell top = index(i, j - 1, cols, rows) != -1 ? grid.get(index(i, j - 1, cols, rows)) : null;
        Cell right = index(i + 1, j, cols, rows) != -1 ? grid.get(index(i + 1, j, cols, rows)) : null;
        Cell bottom = index(i, j + 1, cols, rows) != -1 ? grid.get(index(i, j + 1, cols, rows)) : null;
        Cell left = index(i - 1, j, cols, rows) != -1 ? grid.get(index(i - 1, j, cols, rows)) : null;

        if (top != null && !top.visited) {
            neighbors.add(top);
        }
        if (right != null && !right.visited) {
            neighbors.add(right);
        }
        if (bottom != null && !bottom.visited) {
            neighbors.add(bottom);
        }
        if (left != null && !left.visited) {
            neighbors.add(left);
        }

        if (neighbors.size() > 0) {
           return neighbors.get(new Random().nextInt(neighbors.size()));
        }
        return null;


    }

    private int index(int i, int j, int cols, int rows) {
        if (i < 0 || j < 0 || i > cols - 1 || j > rows - 1) {
            return -1;
        }
        return i + j * cols;
    }

    public void setVisitedColor(Color color) {
        this.visitedColor = color;
    }

    public void highlight(int size, Graphics2D g) {
        Rectangle r = new Rectangle(x, y, size, size);
        //g.drawRect(x,y,size,size);
        g.setColor(new Color(139, 249, 147, 200));
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
    }
}
