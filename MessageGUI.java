
import javax.swing.JFrame;

public class MessageGUI {

	public static void main(String[] args) throws Exception {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

		DictionaryModel predictive = new DictionaryModel("/usr/share/dict/words");

		MessageComponent comp = new MessageComponent(predictive);
		MessageComponent comp1 = new MessageComponent(predictive);

		JFrame gui = new JFrame();
		JFrame gui2 = new JFrame();

		gui.setSize(200, 300);
		gui.setLocation(200, 200);
		gui.setTitle("Predictive Text");

		gui2.setSize(200, 300);
		gui2.setLocation(500, 200);
		gui2.setTitle("Predictive Text");

		gui.add(comp);
		gui2.add(comp1);

		gui.setVisible(true);
		gui2.setVisible(true);

		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
