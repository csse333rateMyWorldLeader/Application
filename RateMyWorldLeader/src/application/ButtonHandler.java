package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {
	  String type;
	  public ButtonHandler(String t)
	   {
		   type = t;
	   }
      public void actionPerformed(ActionEvent e) {
    	  switch(type)
    	  {
    	  case "close":
    	  System.exit(0);
    	  default:
    	  System.exit(0);
    	  }
    	  
      }
   }

