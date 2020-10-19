
import javax.swing.JPanel;

import java.awt.BorderLayout;

public class MessageComponent extends JPanel {

	public MessageComponent(DictionaryModel model) throws java.io.IOException {
		super();

		MessageView messageDisplay = new MessageView(model);

		KeypadController controls = new KeypadController(model);

		setLayout(new BorderLayout());
		add(messageDisplay, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);
	}

}
