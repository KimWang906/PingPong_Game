/*
 * Created by JFormDesigner on Wed Jun 12 09:27:19 KST 2024
 */

package pp_game.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.lang.System.exit;

/**
 * @author hyunbin
 */
public class MainApp extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY // GEN-BEGIN:variables @formatter:off
    private JLabel MainTitle;
    private JButton goLogin;
    private JButton goRegister;
    private JButton exitGame;
    private RunExit runExit;
    private GoRegister userRegisterForm;
    private GoLogin userLoginForm;
    // JFormDesigner - End of variables declaration // GEN-END:variables  @formatter:on

    public MainApp() {
        initComponents();
        this.setVisible(true);
        this.setBackground(Color.BLACK);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY // GEN-BEGIN:initComponents @formatter:off
        MainTitle = new JLabel();
        goLogin = new JButton();
        goRegister = new JButton();
        exitGame = new JButton();
        runExit = new RunExit();
        userRegisterForm = new GoRegister();
        userLoginForm = new GoLogin();

        //======== this ========
        setTitle("PP Game");
        setForeground(SystemColor.windowText);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- MainTitle ----
        MainTitle.setText("BSSM Ping Pong Game");
        MainTitle.setFont(new Font("Arial", Font.BOLD, 30));
        contentPane.add(MainTitle);
        MainTitle.setBounds(new Rectangle(new Point(240, 75), MainTitle.getPreferredSize()));

        //---- goLogin ----
        goLogin.setFont(new Font("Arial", Font.PLAIN, 16));
        goLogin.setAction(userLoginForm);
        contentPane.add(goLogin);
        goLogin.setBounds(305, 135, 210, 34);

        //---- goRegister ----
        goRegister.setFont(new Font("Arial", Font.PLAIN, 16));
        goRegister.setAction(userRegisterForm);
        contentPane.add(goRegister);
        goRegister.setBounds(305, 175, 210, 34);

        //---- exitGame ----
        exitGame.setFont(new Font("Arial", Font.PLAIN, 16));
        exitGame.setAction(runExit);
        contentPane.add(exitGame);
        exitGame.setBounds(305, 235, 210, 34);

        contentPane.setPreferredSize(new Dimension(800, 480));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization // GEN-END:initComponents  @formatter:on
    }

    private static class RunExit extends AbstractAction {
        private RunExit() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Exit Game");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            exit(0);
        }
    }

    private class GoRegister extends AbstractAction {
        private GoRegister() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Register");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            MainApp.this.dispose();
            new UserRegisterForm();
        }
    }

    private class GoLogin extends AbstractAction {
        private GoLogin() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Login");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            MainApp.this.dispose();
            new UserLoginForm();
        }
    }
}
