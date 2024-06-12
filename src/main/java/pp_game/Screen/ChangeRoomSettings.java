/*
 * Created by JFormDesigner on Wed Jun 12 21:29:47 KST 2024
 */

package pp_game.Screen;

import io.grpc.services.room.RoomSettings;
import pp_game.Main;
import pp_game.Room.RoomClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author hyunbin
 */
public class ChangeRoomSettings extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label5;
    private JLabel label1;
    private JLabel label3;
    private JLabel roomSettingsLabel;
    private JTextField changeMaxScoreField;
    private JTextField changeInitBallSpeedField;
    private JTextField changePaddleSpeedField;
    private JButton apply;
    private JButton back;
    private DoBackRoomLobby doBackRoomLobby;
    private DoApplySettings doApplySettings;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public ChangeRoomSettings() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label5 = new JLabel();
        label1 = new JLabel();
        label3 = new JLabel();
        roomSettingsLabel = new JLabel();
        changeMaxScoreField = new JTextField();
        changeInitBallSpeedField = new JTextField();
        changePaddleSpeedField = new JTextField();
        apply = new JButton();
        back = new JButton();
        doBackRoomLobby = new DoBackRoomLobby();
        doApplySettings = new DoApplySettings();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label5 ----
        label5.setText("Paddle Speed:");
        label5.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(label5);
        label5.setBounds(105, 145, 100, 18);

        //---- label1 ----
        label1.setText("Max Score:");
        label1.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(label1);
        label1.setBounds(125, 85, 75, 18);

        //---- label3 ----
        label3.setText("Init Ball Speed:");
        label3.setFont(new Font("Arial", Font.PLAIN, 15));
        contentPane.add(label3);
        label3.setBounds(105, 115, 100, 18);

        //---- roomSettingsLabel ----
        roomSettingsLabel.setText("Room Settings");
        roomSettingsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPane.add(roomSettingsLabel);
        roomSettingsLabel.setBounds(new Rectangle(new Point(160, 35), roomSettingsLabel.getPreferredSize()));

        //---- changeMaxScoreField ----
        changeMaxScoreField.setFont(new Font("Arial", Font.PLAIN, 13));
        contentPane.add(changeMaxScoreField);
        changeMaxScoreField.setBounds(205, 85, 150, changeMaxScoreField.getPreferredSize().height);

        //---- changeInitBallSpeedField ----
        changeInitBallSpeedField.setFont(new Font("Arial", Font.PLAIN, 13));
        contentPane.add(changeInitBallSpeedField);
        changeInitBallSpeedField.setBounds(205, 115, 150, 22);

        //---- changePaddleSpeedField ----
        changePaddleSpeedField.setFont(new Font("Arial", Font.PLAIN, 13));
        contentPane.add(changePaddleSpeedField);
        changePaddleSpeedField.setBounds(205, 145, 150, 22);

        //---- apply ----
        apply.setFont(new Font("Arial", Font.PLAIN, 16));
        apply.setAction(doApplySettings);
        contentPane.add(apply);
        apply.setBounds(260, 190, 75, 25);

        //---- back ----
        back.setFont(new Font("Arial", Font.PLAIN, 16));
        back.setAction(doBackRoomLobby);
        contentPane.add(back);
        back.setBounds(165, 190, 75, 25);

        contentPane.setPreferredSize(new Dimension(500, 290));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    private class DoBackRoomLobby extends AbstractAction {
        private DoBackRoomLobby() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Back");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            ChangeRoomSettings.this.dispose();
        }
    }

    private class DoApplySettings extends AbstractAction {
        private DoApplySettings() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            putValue(NAME, "Apply");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            RoomClient roomManager = new RoomClient(Main.channel);
            RoomSettings roomSettings = RoomSettings
                    .newBuilder()
                    .setMaxScore(
                            Integer.parseInt(changeMaxScoreField.getText()))
                    .setBallSpped(
                            Integer.parseInt(changeInitBallSpeedField.getText()))
                    .setPaddleSpeed(
                            Integer.parseInt(changePaddleSpeedField.getText()))
                    .build();
            roomManager.changeRoomSettings(RoomLobby.roomId, roomSettings);
            ChangeRoomSettings.this.dispose();
        }
    }
}
