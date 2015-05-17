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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
			g.drawString("This is the search menu", 20, 30);
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
			g.drawString("This is the user menu for " + username, 20, 30);
		}
	}

	private static class LeaderMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("This is the leader menu for David", 20, 30);
		}
	}

	private static class LoginMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Enter your Username and Password", 20, 30);
		}
	}
	
	private static class OptionsMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("This is the options menu", 20, 30);
		}
	}

	//Because I am such a fantastic coder, and Java's swing is so silly
	//here is a single action
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
				try {
					CallableStatement cs = con.prepareCall("EXEC loginsp '"
							+ userField.getText()+"', '"
							+ passField.getText()+"'");
					ResultSet res = cs.executeQuery();
				
				if(res.next() && res.getString(1).equals("1"))
				{
					username = userField.getText();
					userField.setText("");
					passField.setText("");
					System.out.println("You did it!");
					window.setContentPane(userMenu);
					window.setSize(300, 200);
					window.setLocation(100, 100);
					window.setVisible(true);
				}
				else
				{
					throw new Error();
				}
				} catch (Exception e1) {
					System.out.println("You failed.");
					userField.setText("Invalid user/password");
					passField.setText("");
				}
				break;
			}

		}
	}
	
	static Connection con;
	static Statement statement;

	static JPanel mainMenu = new JPanel();
	static JPanel loginMenu = new JPanel();
	static JPanel userMenu = new JPanel();
	static JPanel searchMenu = new JPanel();
	static JPanel optionsMenu = new JPanel();
	static JFrame window = new JFrame("GUI Test");
	static JTextField userField = new JTextField();
	static String username = "";
	static JTextField passField = new JPasswordField();

	public static void main(String[] args) {
		
		//Connect to the database, any failure to do so results in the closing of the application
		connectToDatabase();
		
		//Create all the panels upon startup, all button related items are indented to increase readability
		JPanel menuPanel = new MainMenu();
		JPanel loginPanel = new LoginMenu();
		JPanel userPanel = new UserMenu();
		JPanel searchPanel = new SearchMenu();
		JPanel optionsPanel = new OptionsMenu();
		
		//Create Menu Panel
		mainMenu = new JPanel();
		mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.PAGE_AXIS));
		mainMenu.add(menuPanel);
			JButton loginButton = new JButton("Login");
			ButtonHandler loginListener = new ButtonHandler("toLogin");
			loginButton.addActionListener(loginListener);
		mainMenu.add(loginButton);
			JButton closeButton = new JButton("Close");
			ButtonHandler closeListener = new ButtonHandler("close");
			closeButton.addActionListener(closeListener);
		mainMenu.add(closeButton);
		
		//Create Login Panel
		loginMenu = new JPanel();
		loginMenu.setLayout(new BoxLayout(loginMenu, BoxLayout.Y_AXIS));
			JButton menuButton = new JButton("Menu");
			ButtonHandler menuListener = new ButtonHandler("toMenu");
			menuButton.addActionListener(menuListener);
		loginMenu.add(menuButton);
		loginMenu.add(loginPanel);
		loginMenu.add(Box.createVerticalGlue());
		userField.setMaximumSize(new Dimension(300,15));
		passField.setMaximumSize(new Dimension(300,15));
		loginMenu.add(userField);
		loginMenu.add(passField);
			JButton loginButton2 = new JButton("Login");
			ButtonHandler loginListener2 = new ButtonHandler("login");
			loginButton2.addActionListener(loginListener2);
		loginMenu.add(loginButton2);

		//Create User Menu
		userMenu = new JPanel();
		userMenu.setLayout(new BoxLayout(userMenu, BoxLayout.Y_AXIS));
		userMenu.add(userPanel);
		userMenu.add(Box.createVerticalGlue());
			JButton searchButton = new JButton("Search");
			ButtonHandler searchListener = new ButtonHandler("toSearch");
			searchButton.addActionListener(searchListener);
		userMenu.add(searchButton);
			JButton optionsButton = new JButton("Options");
			ButtonHandler optionsListener = new ButtonHandler("toOptions");
			optionsButton.addActionListener(optionsListener);
		userMenu.add(optionsButton);
			JButton logoutButton = new JButton("Logout");
			ButtonHandler logoutListener = new ButtonHandler("toMenu");
			logoutButton.addActionListener(logoutListener);
		userMenu.add(logoutButton);
		
		//Create createUser Menu
		//TODO allows new user to be created
		
		//Create Search Menu
		//TODO allow user to search other users, leaders, states, countries
		
		//Create Options Menu
		//TODO give the user ability to change password or delete account
		
		//Create Leader Menu
		//TODO display overall rating as well as a list of current ratings
		
		//Create Rating Menu
		//TODO allow entry of text to rate a leader
		
		//Create otherUser Menu
		//TODO should allow current user to view another users account
		
		//Create adminUser Menu
		//TODO same as user, but has extra options
		
		//Create adminOptions Menu
		//TODO has extra options for admin
		
		//Create adminOtherUser Menu
		//TODO extra options for admin
		
		//Generate Window
		window = new JFrame("Rate My World Leader");
		window.setContentPane(mainMenu);
		window.setSize(300, 200);
		window.setLocation(100, 100);
		window.setVisible(true);

	}

	private static void connectToDatabase() {
		String host = "jdbc:sqlserver://titan.csse.rose-hulman.edu;databaseName=RateYourWorldLeader";
		Properties connectionProps = new Properties();
	    connectionProps.put("user", "hamiltjc");
	    connectionProps.put("password", "moom1232");
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(host, connectionProps);
			statement = con.createStatement();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
	}
}
