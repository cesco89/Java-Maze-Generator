import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main (String... args){
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Generator Algorithm");
            MazePanel mazePanel = new MazePanel();
            mazePanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(mazePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
