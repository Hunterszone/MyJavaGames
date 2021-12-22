package loading;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import util.Constants;

public class LoadingAnimation extends JFrame {
	
	private static final long serialVersionUID = 7713252743423298707L;

	public void init() {
		Icon loading = new ImageIcon("images/loadingBar.gif");
	    JLabel label = new JLabel(loading);
	    setUndecorated(true);
	    setResizable(false);
	    getContentPane().add(label);
	    pack();
		this.setLocation(Constants.DIMENSION.width / 2 - this.getSize().width / 2, Constants.DIMENSION.height / 2 - this.getSize().height / 2);
	    setVisible(true);
	}

}
