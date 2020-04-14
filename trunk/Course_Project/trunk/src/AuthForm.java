import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AuthForm extends JFrame {
	JPanel 
	authPanel;
	JLabel
	userNameLabel, passLabel;
	JTextField
	userNameField, passField;
	JButton
	authButton;
	
	public AuthForm() {
		super("����� �����������");
		this.setBounds(0,0,300,220);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		userNameLabel = new JLabel("������� ��� �����");
		passLabel = new JLabel("������� ��� ������");
		
		userNameField = new JTextField("User");
		userNameField.setSize(200, 40);
		userNameField.setEditable(true);
		
		passField = new JTextField("**********");
		userNameField.setSize(200, 40);
		userNameField.setEditable(true);
		
		authButton = new JButton("�����");
		
		authPanel = new JPanel();
		authPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); 
		
		c.anchor = GridBagConstraints.CENTER; 
		c.gridheight = 1;
		c.gridwidth  = 1; 
		c.gridx = 0; 
		c.gridy = GridBagConstraints.RELATIVE; 
		c.insets = new Insets(10, 20, 0, 0);
		
		authPanel.add(userNameLabel, c);
		authPanel.add(userNameField, c);
		
		c.insets = new Insets(20, 20, 0, 0);
		authPanel.add(passLabel, c);
		
		c.insets = new Insets(10, 20, 0, 0);
		authPanel.add(passField, c);
		
		c.insets = new Insets(20, 20, 0, 0);
		authPanel.add(authButton, c);
		
		this.add(authPanel);
		
	}
	
	class ButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			}
		}
}
