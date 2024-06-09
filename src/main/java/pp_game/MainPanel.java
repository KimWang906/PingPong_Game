package pp_game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPanel extends JPanel {
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = (int)(SCREEN_WIDTH * 0.5555);
    static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

    JButton register_btn;
    JButton login_btn;
    JButton exit_btn;

    MainPanel(JFrame frame) {
        this.setPreferredSize(SCREEN_SIZE);
        this.setBackground(Color.BLACK);

        JLabel title = new JLabel("BSSM Ping Pong Game");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 100, 500, 100);

        register_btn = new JButton("Register");
        button_cfg(register_btn);
        register_btn.setBounds(400, 250, 200, 40);

        login_btn = new JButton("Login");
        button_cfg(login_btn);
        login_btn.setBounds(400, 300, 200, 40);

        exit_btn = new JButton("Exit");
        button_cfg(exit_btn);
        exit_btn.setBounds(400, 350, 200, 40);

        frame.add(title);
        frame.add(register_btn);
        frame.add(login_btn);
        frame.add(exit_btn);
    }

    void button_cfg(JButton btn) {
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 24));
    }

    void register() {

    }

}
