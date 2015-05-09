package application;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
			g.drawString("Hello World!", 20, 30);
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
			g.drawString("This is the login page", 20, 30);
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
				window.setSize(500, 200);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "toMenu":
				System.out.println("menu");
				window.setContentPane(mainMenu);
				window.setSize(500, 200);
				window.setLocation(100, 100);
				window.setVisible(true);
				break;
			case "login":
				//TODO: Add stuff to actually check inputed
				//		password and username and THEN go to
				//		user page
				System.out.println("You did it!");
				
				break;
			}

		}
	}

	static JPanel mainMenu = new JPanel();
	static JPanel loginMenu = new JPanel();
	static JFrame window = new JFrame("GUI Test");

	public static void main(String[] args) {

		JPanel menuPanel = new MainMenu();
		JPanel loginPanel = new LoginMenu();
		
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
		
		mainMenu = new JPanel();
		mainMenu.setLayout(new BorderLayout());
		mainMenu.add(menuPanel, BorderLayout.CENTER);
		mainMenu.add(loginButton, BorderLayout.EAST);
		mainMenu.add(closeButton, BorderLayout.SOUTH);
		
		loginMenu = new JPanel();
		loginMenu.setLayout(new BorderLayout());
		loginMenu.add(loginPanel, BorderLayout.CENTER);
		loginMenu.add(menuButton, BorderLayout.WEST);
		loginMenu.add(loginButton2, BorderLayout.SOUTH);

		window = new JFrame("GUI Test");
		window.setContentPane(mainMenu);
		window.setSize(500, 200);
		window.setLocation(100, 100);
		window.setVisible(true);

	}
}
