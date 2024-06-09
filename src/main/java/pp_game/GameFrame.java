package pp_game;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    MainPanel main;

    GameFrame() {
        main = new MainPanel(this);
        this.add(main);

        this.setTitle("PP Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocation(new Point());
    }
}
