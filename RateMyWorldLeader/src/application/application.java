package application;

//Currently at 7.5 hours of actual work on this

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
			g.drawString("This is the search menu.", 20, 30);
		}
	}

	private static class RateMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Hello World!", 20, 30);
		}
	}

	private static class CreateUserMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Please enter a username, email, password.", 20, 30);
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
			g.drawString("This is the leader menu for " + currLeader, 20, 30);
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

	// Because I am such a fantastic coder, and Java's swing is so silly
	// here is a single action listener to handle every button ever.
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
			case "toNewUser":
				System.out.println("newUser");
				window.setContentPane(createUserMenu);
				newUserField.setText("Username");
				emailField.setText("Email");
				newPassField.setText("Password");
				newPassConfirmField.setText("Confirm Password");
				window.setSize(400, 300);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "toSearch":
				System.out.println("searchMenu");
				window.setContentPane(searchMenu);
				window.setSize(500, 400);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "toUser":
				window.setContentPane(userMenu);
				window.setSize(300, 200);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "toOptions":
				window.setContentPane(optionsMenu);
				window.setSize(300, 200);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "radio1":
				searchType = "Leader";
				searchFieldM.setVisible(true);
				searchFieldL.setVisible(true);
				break;
			case "radio2":
				searchType = "User";
				searchFieldM.setVisible(false);
				searchFieldL.setVisible(false);
				break;
			case "rate0":
				numRating = "0";
				break;
			case "rate1":
				numRating = "1";
				break;
			case "rate2":
				numRating = "2";
				break;
			case "rate3":
				numRating = "3";
				break;
			case "rate4":
				numRating = "4";
				break;
			case "rate5":
				numRating = "5";
				break;
			case "rate6":
				numRating = "6";
				break;
			case "rate7":
				numRating = "7";
				break;
			case "rate8":
				numRating = "8";
				break;
			case "rate9":
				numRating = "9";
				break;
			case "rate10":
				numRating = "10";
				break;
			case "exitWindowLeader":
				leaderWindow.setVisible(false);
				break;
			case "exitWindowUser":
				otherUserWindow.setVisible(false);
				break;
			case "login":
				try {
					CallableStatement cs = con.prepareCall("EXEC loginsp '"
							+ userField.getText() + "', '"
							+ passField.getText() + "'");
					ResultSet res = cs.executeQuery();
					if (res.next() && res.getString(1).equals("1")) {
						username = userField.getText();
						userField.setText("");
						passField.setText("");
						System.out.println("You did it!");
						window.setContentPane(userMenu);
						window.setSize(300, 200);
						window.setLocation(100, 100);
						window.setVisible(true);
					} else {
						throw new Error();
					}
				} catch (Exception e1) {
					System.out.println("You failed.");
					userField.setText("Invalid user/password");
					passField.setText("");
				}
				break;
			case "createUser":
				System.out.println("Woo?");
				// TODO add user to database
				break;
			case "search":
				CallableStatement cs;
				if (searchType.equals("Leader")) {
					try {
						cs = con.prepareCall("SELECT * FROM LEADER l INNER JOIN AVERAGELEADERRATING r"
								+ " ON l.fname = r.fname AND l.midint = r.midint AND l.lname = r.lname"
								+ " WHERE l.fname ='"
								+ searchFieldF.getText()
								+ "' AND"
								+ " l.midint ='"
								+ searchFieldM.getText()
								+ "' AND"
								+ " l.lname ='" + searchFieldL.getText() + "'");
						ResultSet res = cs.executeQuery();
						currLeader = searchFieldF.getText() + " "
								+ searchFieldM.getText() + " "
								+ searchFieldL.getText();
						if (!res.next()) {
							throw new Exception();
						}
						leaderID = res.getInt("leader_id");
						System.out.println(leaderID + " : " + currLeader);
						leaderMenu = new JPanel();
						JPanel leaderPanel = new LeaderMenu();
						leaderMenu.setLayout(new BoxLayout(leaderMenu, BoxLayout.Y_AXIS));
						leaderMenu.add(leaderPanel);
						cs = con.prepareCall("SELECT username FROM RATING "
								+ "WHERE username = '" + searchFieldF.getText() + "' "
										+ "AND leader_id = " + leaderID);
						ResultSet res2 = cs.executeQuery();
						if(!res2.next()) {
							JPanel option = new JPanel(new GridLayout(1, 0));
							ButtonGroup group = new ButtonGroup();
							for(int x = 0; x <= 10; x++) {
								JRadioButton tempButton = new JRadioButton("" + x);
								ButtonHandler tempListener = new ButtonHandler("rate" + x);
								tempButton.addActionListener(tempListener);
								group.add(tempButton);
								option.add(tempButton);
							}
							leaderMenu.add(option);
							leaderMenu.add(rating);
							rating.setMaximumSize(new Dimension(300, 200));
							rating.setVisible(true);
							leaderMenu.add(Box.createVerticalGlue());
							JButton rateButton = new JButton("Rate");
							ButtonHandler rateListener = new ButtonHandler("rate");
							rateButton.addActionListener(rateListener);
							leaderMenu.add(rateButton);
						}
						JButton exitButton = new JButton("Close");
						ButtonHandler exitListener = new ButtonHandler("exitWindowLeader");
						exitButton.addActionListener(exitListener);
						leaderMenu.add(exitButton);
						leaderWindow = new JFrame(currLeader);
						//TODO Implement the JTable
						cs = con.prepareCall("SELECT username, rating, txt FROM UserRatings "
								+ "WHERE ");
						//TODO Implement the JTable
						leaderWindow.setContentPane(leaderMenu);
						leaderWindow.setSize(500, 400);
						leaderWindow.setLocation(110, 110);
						leaderWindow.setVisible(true);
					} catch (Exception e1) {
						searchFieldF.setText("Non-Existant Leader");
						searchFieldM.setText("");
						searchFieldL.setText("");
					}
				} else if (searchType.equals("User")) {
					try {
						cs = con.prepareCall("SELECT username, lname, rating FROM UserRatings "
								+ "WHERE username = '" + searchFieldF.getText() + "'");
						ResultSet res = cs.executeQuery();
						if (!res.next()) {
							throw new Exception();
						}
						System.out.println();
						otherUsername = searchFieldF.getText();
						System.out.println(otherUsername);
						otherUserMenu = new JPanel();
						otherUserMenu.setLayout(new BoxLayout(otherUserMenu, BoxLayout.Y_AXIS));
						JPanel leaderPanel = new LeaderMenu();
						otherUserMenu.add(leaderPanel);
						JButton exitButton = new JButton("Close");
						ButtonHandler exitListener = new ButtonHandler("exitWindowUser");
						exitButton.addActionListener(exitListener);
						otherUserMenu.add(exitButton);
						otherUserWindow = new JFrame(otherUsername);
						//TODO Implement the JTable
						otherUserWindow.setContentPane(otherUserMenu);
						otherUserWindow.setSize(300, 200);
						otherUserWindow.setLocation(110, 110);
						otherUserWindow.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
						searchFieldF.setText("Non-existant User");
						searchFieldM.setText("");
						searchFieldL.setText("");
					}
				}
				break;
			case "rate":
				//TODO make rating add to database
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
	static JPanel createUserMenu = new JPanel();
	static JPanel leaderMenu = new JPanel();
	static JPanel otherUserMenu = new JPanel();
	static JFrame window;
	static JFrame leaderWindow;
	static JFrame otherUserWindow;
	static JTextField userField = new JTextField();
	static String username = "";
	static String otherUsername = "";
	static JTextField passField = new JPasswordField();
	static JTextField newUserField = new JTextField();
	static JTextField emailField = new JTextField();
	static JTextField newPassField = new JTextField();
	static JTextField newPassConfirmField = new JTextField();
	static JTextArea rating = new JTextArea();
	static JTextField optionsPassField = new JPasswordField();
	static String currLeader = "";
	static int leaderID;
	static JTextField searchFieldF = new JTextField();
	static JTextField searchFieldM = new JTextField();
	static JTextField searchFieldL = new JTextField();
	static String searchType = "Leader";
	static String numRating = "0";

	public static void main(String[] args) {

		// Connect to the database, any failure to do so results in the closing
		// of the application
		connectToDatabase();

		// Create all the panels upon startup
		JPanel menuPanel = new MainMenu();
		JPanel loginPanel = new LoginMenu();
		JPanel userPanel = new UserMenu();
		JPanel searchPanel = new SearchMenu();
		JPanel optionsPanel = new OptionsMenu();
		JPanel createUserPanel = new CreateUserMenu();

		// Create Menu Panel
		mainMenu = new JPanel();
		mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.PAGE_AXIS));
		mainMenu.add(menuPanel);
		JButton loginButton = new JButton("Login");
		ButtonHandler loginListener = new ButtonHandler("toLogin");
		loginButton.addActionListener(loginListener);
		mainMenu.add(loginButton);
		JButton newUserButton = new JButton("New User");
		ButtonHandler newUserListener = new ButtonHandler("toNewUser");
		newUserButton.addActionListener(newUserListener);
		mainMenu.add(newUserButton);
		JButton closeButton = new JButton("Close");
		ButtonHandler closeListener = new ButtonHandler("close");
		closeButton.addActionListener(closeListener);
		mainMenu.add(closeButton);

		// Create Login Panel
		loginMenu = new JPanel();
		loginMenu.setLayout(new BoxLayout(loginMenu, BoxLayout.Y_AXIS));
		JButton menuButton = new JButton("Back");
		ButtonHandler menuListener = new ButtonHandler("toMenu");
		menuButton.addActionListener(menuListener);
		loginMenu.add(menuButton);
		loginMenu.add(loginPanel);
		loginMenu.add(Box.createVerticalGlue());
		userField.setMaximumSize(new Dimension(300, 15));
		passField.setMaximumSize(new Dimension(300, 15));
		loginMenu.add(userField);
		loginMenu.add(passField);
		JButton loginButton2 = new JButton("Login");
		ButtonHandler loginListener2 = new ButtonHandler("login");
		loginButton2.addActionListener(loginListener2);
		loginMenu.add(loginButton2);

		// Create User Menu
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

		// Create createUser Menu
		createUserMenu = new JPanel();
		createUserMenu
				.setLayout(new BoxLayout(createUserMenu, BoxLayout.Y_AXIS));
		createUserMenu.add(createUserPanel);
		createUserMenu.add(newUserField);
		createUserMenu.add(emailField);
		createUserMenu.add(newPassField);
		createUserMenu.add(newPassConfirmField);
		newUserField.setMaximumSize(new Dimension(300, 15));
		emailField.setMaximumSize(new Dimension(300, 15));
		newPassField.setMaximumSize(new Dimension(300, 15));
		newPassConfirmField.setMaximumSize(new Dimension(300, 15));
		createUserMenu.add(Box.createVerticalGlue());
		JButton createUserButton = new JButton("Create User");
		ButtonHandler createUserListener = new ButtonHandler("createUser");
		createUserButton.addActionListener(createUserListener);
		createUserMenu.add(createUserButton);
		JButton backButton = new JButton("Back");
		ButtonHandler backListener = new ButtonHandler("toMenu");
		backButton.addActionListener(backListener);
		createUserMenu.add(backButton);

		// Create Search Menu
		searchMenu = new JPanel();
		searchMenu.setLayout(new BoxLayout(searchMenu, BoxLayout.Y_AXIS));
		searchMenu.add(searchPanel);
		searchMenu.add(searchFieldF);
		searchFieldF.setMaximumSize(new Dimension(3000, 15));
		searchMenu.add(searchFieldM);
		searchFieldM.setMaximumSize(new Dimension(3000, 15));
		searchMenu.add(searchFieldL);
		searchFieldL.setMaximumSize(new Dimension(3000, 15));
		JPanel option = new JPanel(new GridLayout(1, 0));
		JRadioButton leaderButton = new JRadioButton("Leader");
		ButtonHandler radioListener = new ButtonHandler("radio1");
		leaderButton.addActionListener(radioListener);
		JRadioButton UserButton = new JRadioButton("User");
		ButtonHandler radioListener2 = new ButtonHandler("radio2");
		UserButton.addActionListener(radioListener2);
		ButtonGroup group = new ButtonGroup();
		group.add(leaderButton);
		group.add(UserButton);
		option.add(leaderButton);
		option.add(UserButton);
		searchMenu.add(option);
		JButton searchButton2 = new JButton("Search");
		ButtonHandler searchListener2 = new ButtonHandler("search");
		searchButton2.addActionListener(searchListener2);
		searchMenu.add(searchButton2);
		searchMenu.add(Box.createVerticalGlue());
		JButton backButton2 = new JButton("Back");
		ButtonHandler backListener2 = new ButtonHandler("toUser");
		backButton2.addActionListener(backListener2);
		searchMenu.add(backButton2);

		// Create Options Menu
		// TODO give the user ability to change password or delete account
		optionsMenu = new JPanel();
		optionsMenu.setLayout(new BoxLayout(optionsMenu, BoxLayout.Y_AXIS));
		optionsMenu.add(optionsPanel);
		optionsMenu.add(optionsPassField);
		optionsPassField.setMaximumSize(new Dimension(300, 15));
		optionsMenu.add(Box.createVerticalGlue());
		JButton deleteButton = new JButton("Delete User");
		ButtonHandler deleteListener = new ButtonHandler("deleteUser");
		deleteButton.addActionListener(deleteListener);
		optionsMenu.add(deleteButton);
		JButton backButton3 = new JButton("Back");
		ButtonHandler backListener3 = new ButtonHandler("toUser");
		backButton3.addActionListener(backListener3);
		optionsMenu.add(backButton3);

		// Create adminUser Menu
		// TODO same as user, but has extra options

		// Create adminOptions Menu
		// TODO has extra options for admin

		// Create adminOtherUser Menu
		// TODO extra options for admin

		// Generate Window
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
