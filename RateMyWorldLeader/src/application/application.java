package application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class application {

	private static class MainMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("This is the menu", 20, 30);
		}
	}

	private static class SearchMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Hello World!", 20, 30);
		}
	}

	private static class RateMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Hello World!", 20, 30);
		}
	}

	private static class UserMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("This is the user menu for " + username +".", 20, 30);
		}
	}

	private static class LeaderMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Hello World!", 20, 30);
		}
	}

	private static class LoginMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Enter your Username and Password", 20, 30);
		}
	}

	private static class ButtonHandler implements ActionListener {
		String type;

		public ButtonHandler(String t) {
			type = t;
		}

		public void actionPerformed(ActionEvent e) {
			switch (type) {
			case "close":
				System.exit(0);
				break;
			case "toLogin":
				System.out.println("login");
				window.setContentPane(loginMenu);
				window.setSize(300, 200);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "toMenu":
				System.out.println("menu");
				window.setContentPane(mainMenu);
				window.setSize(300, 200);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "login":
				//TODO: Add stuff to actually check inputed
				//		password and username and THEN go to
				//		user page
				if(userField.getText().equals("FiniteZ")
						&& passField.getText().equals("moom1232"))
				{
					username = userField.getText();
					System.out.println("You did it!");
					window.setContentPane(userMenu);
					window.setSize(300, 200);
					window.setLocation(100, 100);
					window.setVisible(true);
				}
				else
				{
					System.out.println("You failed.");
				}
				
				break;
			}

		}
	}

	static JPanel mainMenu = new JPanel();
	static JPanel loginMenu = new JPanel();
	static JPanel userMenu = new JPanel();
	static JFrame window = new JFrame("GUI Test");
	static JTextField userField = new JTextField();
	static String username = "";
	static JTextField passField = new JPasswordField();

	public static void main(String[] args) {

		JPanel menuPanel = new MainMenu();
		JPanel loginPanel = new LoginMenu();
		JPanel userPanel = new UserMenu();
		
		JButton loginButton = new JButton("Login");
		ButtonHandler loginListener = new ButtonHandler("toLogin");
		loginButton.addActionListener(loginListener);
		JButton loginButton2 = new JButton("Login");
		ButtonHandler loginListener2 = new ButtonHandler("login");
		loginButton2.addActionListener(loginListener2);
		
		JButton closeButton = new JButton("Close");
		ButtonHandler closeListener = new ButtonHandler("close");
		closeButton.addActionListener(closeListener);

		JButton menuButton = new JButton("Menu");
		ButtonHandler menuListener = new ButtonHandler("toMenu");
		menuButton.addActionListener(menuListener);
		
		JButton logoutButton = new JButton("Logout");
		ButtonHandler logoutListener = new ButtonHandler("toMenu");
		logoutButton.addActionListener(logoutListener);
		
		JButton searchButton = new JButton("Search");
		ButtonHandler searchListener = new ButtonHandler("toSearch");
		searchButton.addActionListener(searchListener);
		
		JButton optionsButton = new JButton("Options");
		ButtonHandler optionsListener = new ButtonHandler("toOptions");
		optionsButton.addActionListener(optionsListener);
		
		//Create Menu Panel
		mainMenu = new JPanel();
		mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.PAGE_AXIS));
		mainMenu.add(menuPanel);
		mainMenu.add(loginButton);
		mainMenu.add(closeButton);
		
		//Create Login Panel
		loginMenu = new JPanel();
		loginMenu.setLayout(new BoxLayout(loginMenu, BoxLayout.Y_AXIS));
		loginMenu.add(menuButton);
		loginMenu.add(loginPanel);
		loginMenu.add(Box.createVerticalGlue());
		userField.setMaximumSize(new Dimension(300,15));
		passField.setMaximumSize(new Dimension(300,15));
		loginMenu.add(userField);
		loginMenu.add(passField);
		loginMenu.add(loginButton2);

		//Create User Menu
		userMenu = new JPanel();
		userMenu.setLayout(new BoxLayout(userMenu, BoxLayout.Y_AXIS));
		userMenu.add(userPanel);
		userMenu.add(Box.createVerticalGlue());
		userMenu.add(searchButton);
		userMenu.add(optionsButton);
		userMenu.add(logoutButton);
		
		//Generate Window
		window = new JFrame("Rate My World Leader");
		window.setContentPane(mainMenu);
		window.setSize(300, 200);
		window.setLocation(100, 100);
		window.setVisible(true);

	}
}
