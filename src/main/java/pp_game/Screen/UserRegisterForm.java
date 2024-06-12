/*
 * Created by JFormDesigner on Wed Jun 12 11:14:51 KST 2024
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
public class UserRegisterForm extends JFrame {
    private final Logger logger = Logger.getLogger(UserRegisterForm.class.getName());

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JTextField username;
    public JPasswordField password;
    public JLabel statusMsg;
    public JTextField studentId;
    private JLabel usernameLabel;
    private JLabel loginTitle;
    private JLabel passwordLabel;
    private JButton register;
    private JButton back;
    private JLabel studentIdLabel;
    private ExecuteRegister executeRegister;
    private CloseUserRegisterForm runCloseUserRegisterForm;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public UserRegisterForm() {
        initComponents();
        this.setVisible(true);
    }

    public JTextField getUsername() {
        return username;
    }

    public JTextField getStudentId() {
        return studentId;
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
        register = new JButton();
        back = new JButton();
        statusMsg = new JLabel();
        studentIdLabel = new JLabel();
        studentId = new JTextField();
        executeRegister = new ExecuteRegister();
        runCloseUserRegisterForm = new CloseUserRegisterForm();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- username ----
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(username);
        username.setBounds(210, 150, 180, 34);

        //---- usernameLabel ----
        usernameLabel.setText("username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(usernameLabel);
        usernameLabel.setBounds(new Rectangle(new Point(120, 155), usernameLabel.getPreferredSize()));

        //---- loginTitle ----
        loginTitle.setText("PP Game Register");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 30));
        contentPane.add(loginTitle);
        loginTitle.setBounds(new Rectangle(new Point(130, 20), loginTitle.getPreferredSize()));

        //---- passwordLabel ----
        passwordLabel.setText("password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(passwordLabel);
        passwordLabel.setBounds(120, 195, 89, 24);

        //---- password ----
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(password);
        password.setBounds(210, 190, 180, 34);

        //---- register ----
        register.setFont(new Font("Arial", Font.PLAIN, 16));
        register.setAction(executeRegister);
        contentPane.add(register);
        register.setBounds(135, 270, 110, 34);

        //---- back ----
        back.setFont(new Font("Arial", Font.PLAIN, 16));
        back.setAction(runCloseUserRegisterForm);
        contentPane.add(back);
        back.setBounds(265, 270, 110, 34);

        //---- statusMsg ----
        statusMsg.setText("Status Message");
        statusMsg.setFont(new Font("Arial", Font.BOLD, 14));
        contentPane.add(statusMsg);
        statusMsg.setBounds(120, 70, 310, statusMsg.getPreferredSize().height);

        //---- studentIdLabel ----
        studentIdLabel.setText("student id");
        studentIdLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(studentIdLabel);
        studentIdLabel.setBounds(120, 115, 89, 24);

        //---- studentId ----
        studentId.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(studentId);
        studentId.setBounds(210, 110, 180, 34);

        contentPane.setPreferredSize(new Dimension(500, 360));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    private class ExecuteRegister extends AbstractAction {
        private ExecuteRegister() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Register");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            AuthClient auth = new AuthClient(Main.channel);
            String studentId = UserRegisterForm.this.getStudentId().getText();
            String username = UserRegisterForm.this.getUsername().getText();
            String password = UserRegisterForm.this.getPasswordString();

            auth.register(username, studentId, password).thenAccept(token -> {
                UserRegisterForm.this.statusMsg.setVisible(false);
                logger.info("Register successful, token: " + token);
                Main.userClient.set_token(token);
                Main.userClient.set_user(Main.userClient.getUserData().join());
                UserRegisterForm.this.dispose();
                // SHOW ROOMS
                new Lobby();
            }).exceptionally(ex -> {
                UserRegisterForm.this.statusMsg.setText(
                        "Register failed, Error: " + Status.fromThrowable(ex).getCode()
                );
                return null;
            });
        }
    }

    private class CloseUserRegisterForm extends AbstractAction {
        private CloseUserRegisterForm() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Back");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            UserRegisterForm.this.dispose();
            new MainApp();
        }
    }
}
