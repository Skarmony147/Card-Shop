/**
 * Main program for final project by Sebastian and Charlie
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class MainProgram {
	public static void wait(int tillWhen){
		try {
			Thread.sleep(tillWhen);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Restore interrupted status
		}
	}
    public static void main(String[] args) {
        JFrame logWin = new JFrame("Login Window");
        logWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logWin.setSize(250, 140);
        logWin.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 7));

        JLabel userLabel = new JLabel("Username");
        JTextField userTextField = new JTextField(10);
        JLabel passLabel = new JLabel("Password");
        JTextField passTextField = new JTextField(10);
        JButton loginButton = new JButton("Login");

        panel.add(userLabel);
        panel.add(userTextField);
        panel.add(passLabel);
        panel.add(passTextField);
        panel.add(loginButton);

        logWin.add(panel, BorderLayout.CENTER);
        logWin.setLocationRelativeTo(null);
        logWin.setVisible(true);

        LoginWindow log = new LoginWindow();
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = passTextField.getText();
                if (log.authenticate(username, password)) {
					// Waits for one second, had to do some reasearch for this
                    loginButton.setText("Success");
					new javax.swing.Timer(10, evt -> {
						logWin.setState(Frame.ICONIFIED);
						loginButton.setText("Login");
					}).setRepeats(false);
                } else {
                    loginButton.setText("Invalid");
					new javax.swing.Timer(10, evt -> {
						loginButton.setText("Login");
					}).setRepeats(false);
                }
            }
        });
    }
}
