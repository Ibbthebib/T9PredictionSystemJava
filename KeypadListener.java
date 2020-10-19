
import java.awt.event.*;

public class KeypadListener implements ActionListener {

	private DictionaryModel model;
	private char key;

	public KeypadListener(DictionaryModel m, char k) {
		model = m;
		key = k;
	}

	public void actionPerformed(ActionEvent e) {
		model.press(key);
	}
}
