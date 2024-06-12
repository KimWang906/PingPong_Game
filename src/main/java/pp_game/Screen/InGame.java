package pp_game.Screen;

import pp_game.Game.GamePanel;

import javax.swing.*;
import java.awt.*;

public class InGame extends JFrame {
    GamePanel panel;

    public InGame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Ping Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocation(new Point());
        this.setLocationRelativeTo(null);
    }
}
