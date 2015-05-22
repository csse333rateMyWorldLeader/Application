package application;

//Currently at 12 hours of actual work on this

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			if (isAdmin)
				g.drawString("ADMIN", 20, 50);
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

	private static class OtherUserMenu extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("This is the User menu for " + otherUsername, 20, 30);
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
				window.setSize(400, 300);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "toOptions":
				window.setContentPane(optionsMenu);
				window.setSize(400, 300);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "deleteUser":
				try {
					PreparedStatement csDltChk = con
							.prepareStatement("EXEC loginsp ?, ?");
					csDltChk.setString(1, username);
					csDltChk.setString(2, optionsPassField.getText());
					ResultSet rsChk = csDltChk.executeQuery();
					if (!rsChk.next()) {
						throw new Exception();
					}
					PreparedStatement csDlt = con
							.prepareStatement("EXEC delete_account ?, ?");
					csDlt.setString(1, username);
					csDlt.setString(2, username);
					csDlt.execute();
					System.out.println("menu");
					window.setContentPane(mainMenu);
					window.setSize(300, 200);
					window.setLocation(100, 100);
					window.setVisible(true);
				} catch (Exception e33) {
					changePassField.setText("Incorect Password");
					optionsPassField.setText("");
					e33.printStackTrace();
				}
				break;
			case "changePass":
				try {
					PreparedStatement csPassChng = con
							.prepareStatement("EXEC changepassword ?, ?, ?");
					csPassChng.setString(1, username);
					csPassChng.setString(2, optionsPassField.getText());
					csPassChng.setString(3, changePassField.getText());
					csPassChng.executeQuery();
					window.setContentPane(userMenu);
					window.setSize(400, 300);
					window.setLocation(100, 100);
					window.setVisible(true);
				} catch (Exception eQD) {
					changePassField.setText("Incorect Password");
					optionsPassField.setText("");
					eQD.printStackTrace();
				}
				break;
			case "removeAdminOtherUser":
				try {
					PreparedStatement csNoGodsNoAdminsOnlyUsers = con
							.prepareStatement("EXEC removeAdminPrivlage ?");
					csNoGodsNoAdminsOnlyUsers.setString(1, otherUsername);
					csNoGodsNoAdminsOnlyUsers.execute();
					otherUserWindow.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			case "adminOtherUser":
				try {
					PreparedStatement csBestowEnlightenment = con
							.prepareStatement("EXEC adminaccount ?, ?");
					csBestowEnlightenment.setString(1, username);
					csBestowEnlightenment.setString(2, otherUsername);
					csBestowEnlightenment.execute();
					otherUserWindow.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			case "deleteOtherUser":
				try {
					PreparedStatement csBurnInAFlamingPitOfDeath = con
							.prepareStatement("EXEC delete_account ?, ?");
					csBurnInAFlamingPitOfDeath.setString(1, username);
					csBurnInAFlamingPitOfDeath.setString(1, otherUsername);
					csBurnInAFlamingPitOfDeath.execute();
					otherUserWindow.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
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
					PreparedStatement cs = con
							.prepareStatement("EXEC loginsp ?, ?");
					cs.setString(1, userField.getText());
					cs.setString(2, passField.getText());
					ResultSet res = cs.executeQuery();
					username = userField.getText();
					if (res.next() && res.getString(1).equals(username)) {
						username = userField.getText();
						isAdmin = res.getBoolean(2);
						userField.setText("");
						passField.setText("");
						System.out.println("You did it!");
						System.out.println("Admin = " + isAdmin);
						generateWindows();
						window.setContentPane(userMenu);
						window.setSize(400, 300);
						window.setLocation(100, 100);
						window.setVisible(true);
						;
					} else {
						throw new Error();
					}
				} catch (Exception e1) {
					System.out.println("You failed.");
					userField.setText("Invalid user/password");
					passField.setText("");
					e1.printStackTrace();
				}
				break;
			case "createUser":
				System.out.println("Woo?");
				try {
					PreparedStatement cs;
					if (!newPassField.getText().equals(
							newPassConfirmField.getText())) {
						throw new Exception();
					}
					cs = con.prepareStatement("EXEC CreateLogin ?, ?, ?");
					cs.setString(1, newUserField.getText());
					cs.setString(2, newPassConfirmField.getText());
					cs.setString(3, emailField.getText());
					cs.execute();
					System.out.println("menu");
					window.setContentPane(mainMenu);
					window.setSize(300, 200);
					window.setLocation(100, 100);
					window.setVisible(true);
				} catch (Exception e3) {
					newUserField.setText("Invalid information");
					newPassField.setText("");
					newPassConfirmField.setText("");
					emailField.setText("");
					e3.printStackTrace();
				}
				break;
			case "search":
				PreparedStatement cs;
				if (searchType.equals("Leader")) {
					try {
						cs = con.prepareStatement("SELECT * FROM LEADER l INNER JOIN AVERAGELEADERRATING r"
								+ " ON l.fname = r.fname AND l.midint = r.midint AND l.lname = r.lname"
								+ " WHERE l.fname = ? AND l.midint = ? AND l.lname = ?");
						cs.setString(1, searchFieldF.getText());
						cs.setString(2, searchFieldM.getText());
						cs.setString(3, searchFieldL.getText());
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
						leaderMenu.setLayout(new BoxLayout(leaderMenu,
								BoxLayout.Y_AXIS));
						leaderMenu.add(leaderPanel);
						cs = con.prepareStatement("SELECT username FROM RATING "
								+ "WHERE username = ? AND leader_id = ?");
						cs.setString(1, username);
						cs.setInt(2, leaderID);
						ResultSet res2 = cs.executeQuery();
						if (!res2.next()) {
							JPanel option = new JPanel(new GridLayout(1, 0));
							ButtonGroup group = new ButtonGroup();
							for (int x = 0; x <= 10; x++) {
								JRadioButton tempButton = new JRadioButton(""
										+ x);
								ButtonHandler tempListener = new ButtonHandler(
										"rate" + x);
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
							ButtonHandler rateListener = new ButtonHandler(
									"rate");
							rateButton.addActionListener(rateListener);
							leaderMenu.add(rateButton);
						}
						JButton exitButton = new JButton("Close");
						ButtonHandler exitListener = new ButtonHandler(
								"exitWindowLeader");
						exitButton.addActionListener(exitListener);
						leaderMenu.add(exitButton);
						leaderWindow = new JFrame(currLeader);
						cs = con.prepareStatement("SELECT username, rating, txt FROM UserRatings "
								+ "WHERE leader_id = ?");
						cs.setInt(1, leaderID);
						ResultSet res3 = cs.executeQuery();
						String[] columnNames = { "Username", "Rating", "Text" };
						ArrayList<String[]> dataArrayList = new ArrayList<String[]>();
						while (res3.next()) {
							String[] temp = new String[3];
							temp[0] = res3.getString(1);
							temp[1] = res3.getString(2);
							temp[2] = res3.getString(3);
							dataArrayList.add(temp);
						}
						String[][] data = new String[dataArrayList.size()][3];
						for (int x = 0; x < dataArrayList.size(); x++) {
							data[x] = dataArrayList.get(x);
						}
						JTable table = new JTable(data, columnNames);
						JScrollPane scrollPane = new JScrollPane(table);
						table.setPreferredScrollableViewportSize(new Dimension(
								300, 100));
						leaderMenu.add(scrollPane);
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
						cs = con.prepareStatement("SELECT * FROM USER_ACCOUNT WHERE username = ?");
						cs.setString(1, searchFieldF.getText());
						ResultSet resUser = cs.executeQuery();
						if (!resUser.next()) {
							throw new Exception();
						}
						cs = con.prepareStatement("SELECT username, lname, rating FROM UserRatings "
								+ "WHERE username = ?");
						cs.setString(1, searchFieldF.getText());
						cs.executeQuery();
						otherUsername = searchFieldF.getText();
						System.out.println(otherUsername);
						otherUserMenu = new JPanel();
						otherUserMenu.setLayout(new BoxLayout(otherUserMenu,
								BoxLayout.Y_AXIS));
						JPanel otherUserPanel = new OtherUserMenu();
						otherUserMenu.add(otherUserPanel);
						JButton exitButton6 = new JButton("Close");
						ButtonHandler exitListener6 = new ButtonHandler(
								"exitWindowUser");
						exitButton6.addActionListener(exitListener6);
						otherUserMenu.add(exitButton6);
						if (isAdmin) {
							JPanel adminOptions = new JPanel();
							adminOptions.setLayout(new GridLayout(1, 0));
							JButton deleteUserButton = new JButton(
									"Delete User");
							ButtonHandler deleteUserListener = new ButtonHandler(
									"deleteOtherUser");
							deleteUserButton
									.addActionListener(deleteUserListener);
							adminOptions.add(deleteUserButton);
							cs = con.prepareStatement("SELECT administrator FROM USER_ACCOUNT "
									+ "WHERE username = ?");
							cs.setString(1, otherUsername);
							ResultSet res78 = cs.executeQuery();
							if (res78.next() && res78.getBoolean(1)) {
								JButton removeAdminUserButton = new JButton(
										"Un-Admin-ify User");
								ButtonHandler removeAdminUserListener = new ButtonHandler(
										"removeAdminOtherUser");
								removeAdminUserButton
										.addActionListener(removeAdminUserListener);
								adminOptions.add(removeAdminUserButton);
							} else {
								JButton adminUserButton = new JButton(
										"Admin-ify User");
								ButtonHandler adminUserListener = new ButtonHandler(
										"adminOtherUser");
								adminUserButton
										.addActionListener(adminUserListener);
								adminOptions.add(adminUserButton);
							}
							otherUserMenu.add(adminOptions);
						}
						otherUserWindow = new JFrame(otherUsername);
						cs = con.prepareStatement("SELECT fname, midint, lname, rating, txt FROM UserRatings "
								+ "WHERE username = ?");
						cs.setString(1, otherUsername);
						ResultSet res3 = cs.executeQuery();
						String[] columnNames = { "First Name",
								"Middle Initial", "Last Name", "Rating", "Text" };
						ArrayList<String[]> dataArrayList = new ArrayList<String[]>();
						while (res3.next()) {
							String[] temp = new String[5];
							temp[0] = res3.getString(1);
							temp[1] = res3.getString(2);
							temp[2] = res3.getString(3);
							temp[3] = res3.getString(4);
							temp[4] = res3.getString(5);
							dataArrayList.add(temp);
						}
						String[][] data = new String[dataArrayList.size()][3];
						for (int x = 0; x < dataArrayList.size(); x++) {
							data[x] = dataArrayList.get(x);
						}
						JTable table = new JTable(data, columnNames);
						JScrollPane scrollPane = new JScrollPane(table);
						table.setPreferredScrollableViewportSize(new Dimension(
								300, 100));
						otherUserMenu.add(scrollPane);
						otherUserWindow.setContentPane(otherUserMenu);
						otherUserWindow.setSize(500, 400);
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
				PreparedStatement cs2;
				try {
					cs2 = con.prepareStatement("EXEC addRating ?, ?, ?, ?");
					cs2.setInt(1, leaderID);
					cs2.setString(2, username);
					cs2.setString(3, numRating);
					cs2.setString(4, rating.getText());
					cs2.execute();
					leaderWindow.setVisible(false);
					leaderMenu = new JPanel();
					JPanel leaderPanel = new LeaderMenu();
					leaderMenu.setLayout(new BoxLayout(leaderMenu,
							BoxLayout.Y_AXIS));
					leaderMenu.add(leaderPanel);
					JButton exitButton = new JButton("Close");
					ButtonHandler exitListener = new ButtonHandler(
							"exitWindowLeader");
					exitButton.addActionListener(exitListener);
					leaderMenu.add(exitButton);
					leaderWindow = new JFrame(currLeader);
					cs = con.prepareStatement("SELECT username, rating, txt FROM UserRatings "
							+ "WHERE leader_id = ?");
					cs.setInt(1, leaderID);
					ResultSet res3 = cs.executeQuery();
					String[] columnNames = { "Username", "Rating", "Text" };
					ArrayList<String[]> dataArrayList = new ArrayList<String[]>();
					while (res3.next()) {
						String[] temp = new String[3];
						temp[0] = res3.getString(1);
						temp[1] = res3.getString(2);
						temp[2] = res3.getString(3);
						dataArrayList.add(temp);
					}
					String[][] data = new String[dataArrayList.size()][3];
					for (int x = 0; x < dataArrayList.size(); x++) {
						data[x] = dataArrayList.get(x);
					}
					JTable table = new JTable(data, columnNames);
					JScrollPane scrollPane = new JScrollPane(table);
					table.setPreferredScrollableViewportSize(new Dimension(300,
							100));
					leaderMenu.add(scrollPane);
					leaderWindow.setContentPane(leaderMenu);
					leaderWindow.setSize(500, 400);
					leaderWindow.setLocation(110, 110);
					leaderWindow.setVisible(true);
				} catch (SQLException e1) {
					rating.setText("An error occured");
					e1.printStackTrace();
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
	static JPanel createUserMenu = new JPanel();
	static JPanel leaderMenu = new JPanel();
	static JPanel otherUserMenu = new JPanel();
	static JPanel adminUserMenu = new JPanel();
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
	static JTextField changePassField = new JTextField();
	static String currLeader = "";
	static int leaderID;
	static JTextField searchFieldF = new JTextField();
	static JTextField searchFieldM = new JTextField();
	static JTextField searchFieldL = new JTextField();
	static String searchType = "Leader";
	static String numRating = "0";
	static private Boolean isAdmin = false;

	public static void main(String[] args) {

		// Connect to the database, any failure to do so results in the closing
		// of the application
		connectToDatabase();

		// Create all the panels upon startup
		JPanel menuPanel = new MainMenu();
		JPanel loginPanel = new LoginMenu();
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

	private static void generateWindows() {
		JPanel userPanel = new UserMenu();
		JPanel searchPanel = new SearchMenu();
		JPanel optionsPanel = new OptionsMenu();

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
		optionsMenu = new JPanel();
		optionsMenu.setLayout(new BoxLayout(optionsMenu, BoxLayout.Y_AXIS));
		optionsMenu.add(optionsPanel);
		optionsMenu.add(optionsPassField);
		optionsPassField.setMaximumSize(new Dimension(300, 15));
		optionsMenu.add(changePassField);
		changePassField.setMaximumSize(new Dimension(300, 15));
		changePassField.setText("New Password");
		optionsMenu.add(Box.createVerticalGlue());
		JButton deleteButton = new JButton("Delete User");
		ButtonHandler deleteListener = new ButtonHandler("deleteUser");
		deleteButton.addActionListener(deleteListener);
		optionsMenu.add(deleteButton);
		JButton changeButton = new JButton("Change Password");
		ButtonHandler changeListener = new ButtonHandler("changePass");
		changeButton.addActionListener(changeListener);
		optionsMenu.add(changeButton);
		JButton backButton3 = new JButton("Back");
		ButtonHandler backListener3 = new ButtonHandler("toUser");
		backButton3.addActionListener(backListener3);
		optionsMenu.add(backButton3);
	}
}
