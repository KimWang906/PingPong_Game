/*
 * Created by JFormDesigner on Wed Jun 12 10:03:14 KST 2024
 */

package pp_game.Screen;

import io.grpc.Status;
import pp_game.Auth.AuthClient;
import pp_game.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

/**
 * @author hyunbin
 */
public class UserLoginForm extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JTextField username;
    public JPasswordField password;
    public JLabel statusMsg;
    private JLabel usernameLabel;
    private JLabel loginTitle;
    private JLabel passwordLabel;
    private JButton login;
    private JButton back;
    private ExecuteLogin execLogin;
    private CloseLogin runCloseLogin;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public UserLoginForm() {
        initComponents();
        this.setVisible(true);
        this.statusMsg.setVisible(false);
        this.setBackground(Color.BLACK);
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    public JTextField getUsername() {
        return username;
    }

    public String getPasswordString() {
        return new String(password.getPassword());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        username = new JTextField();
        usernameLabel = new JLabel();
        loginTitle = new JLabel();
        passwordLabel = new JLabel();
        password = new JPasswordField();
        login = new JButton();
        back = new JButton();
        statusMsg = new JLabel();
        execLogin = new ExecuteLogin();
        runCloseLogin = new CloseLogin();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- username ----
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(username);
        username.setBounds(210, 110, 180, 34);

        //---- usernameLabel ----
        usernameLabel.setText("username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(usernameLabel);
        usernameLabel.setBounds(new Rectangle(new Point(120, 115), usernameLabel.getPreferredSize()));

        //---- loginTitle ----
        loginTitle.setText("PP Game Login");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 30));
        contentPane.add(loginTitle);
        loginTitle.setBounds(new Rectangle(new Point(145, 20), loginTitle.getPreferredSize()));

        //---- passwordLabel ----
        passwordLabel.setText("password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(passwordLabel);
        passwordLabel.setBounds(120, 155, 89, 24);

        //---- password ----
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(password);
        password.setBounds(210, 150, 180, 34);

        //---- login ----
        login.setFont(new Font("Arial", Font.PLAIN, 16));
        login.setAction(execLogin);
        contentPane.add(login);
        login.setBounds(165, 210, 78, 34);

        //---- back ----
        back.setFont(new Font("Arial", Font.PLAIN, 16));
        back.setAction(runCloseLogin);
        contentPane.add(back);
        back.setBounds(265, 210, 78, 34);

        //---- statusMsg ----
        statusMsg.setText("Status Message");
        statusMsg.setFont(new Font("Arial", Font.BOLD, 14));
        contentPane.add(statusMsg);
        statusMsg.setBounds(120, 70, 310, statusMsg.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(500, 300));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    private class ExecuteLogin extends AbstractAction {
        private final Logger logger = Logger.getLogger(ExecuteLogin.class.getName());

        private ExecuteLogin() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Login");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            AuthClient request = new AuthClient(Main.channel);

            String username = UserLoginForm.this.getUsername().getText();
            String password = UserLoginForm.this.getPasswordString();
            request.login(
                    username,
                    password
            ).thenAccept(token -> {
                UserLoginForm.this.statusMsg.setVisible(false);
                logger.info("Login successful, token: " + token);
                Main.userClient.set_token(token);
                Main.userClient.set_user(Main.userClient.getUserData().join());
                UserLoginForm.this.dispose();
                // SHOW ROOMS
                new Lobby();
            }).exceptionally(ex -> {
                UserLoginForm.this.statusMsg.setVisible(true);
                UserLoginForm.this.statusMsg.setText("Login failed, Error: " + Status.fromThrowable(ex).getCode());

                logger.info("Debug Mode");
                logger.info("Username: " + username);
                logger.info("Password: " + password);
                return null;
            });
        }
    }

    private class CloseLogin extends AbstractAction {
        private CloseLogin() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Back");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            UserLoginForm.this.dispose();
            new MainApp();
        }
    }
}
