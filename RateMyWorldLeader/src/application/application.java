package application;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class application {

	private static class MainMenu extends JPanel {
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawString( "Hello World!", 20, 30 );
	      }
	   }
	private static class SeachMenu extends JPanel {
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawString( "Hello World!", 20, 30 );
	      }
	   }
	private static class RateMenu extends JPanel {
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawString( "Hello World!", 20, 30 );
	      }
	   }
	private static class UserMenu extends JPanel {
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawString( "Hello World!", 20, 30 );
	      }
	   }
	private static class LeaderMenu extends JPanel {
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawString( "Hello World!", 20, 30 );
	      }
	   }
	   
	   public static void main(String[] args) {
	      
	      JPanel displayPanel = new MainMenu();
	      JButton okButton = new JButton("OK");
	      ButtonHandler listener = new ButtonHandler("close");
	      okButton.addActionListener(listener);

	      JPanel content = new JPanel();
	      content.setLayout(new BorderLayout());
	      content.add(displayPanel, BorderLayout.CENTER);
	      content.add(okButton, BorderLayout.SOUTH);

	      JFrame window = new JFrame("GUI Test");
	      window.setContentPane(content);
	      window.setSize(500,200);
	      window.setLocation(100,100);
	      window.setVisible(true);

	   }
	}

