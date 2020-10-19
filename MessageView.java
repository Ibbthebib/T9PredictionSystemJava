import java.util.Observer;
import java.util.Observable;

import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

public class MessageView extends JPanel implements Observer {

	private DictionaryModel model;
	private JTextArea message;

	public MessageView(DictionaryModel m) {
		model = m;
		m.addObserver(this);
		message = new JTextArea(5, 20);
		message.setEditable(false);
		message.setLineWrap(true);
		message.setText("predictive text: enter text with 8 keys.");

		JScrollPane scrollPane = new JScrollPane(message);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}

	public void update(Observable obs, Object obj) {
		StringBuffer words = new StringBuffer();
		for (String word : model.getWords())
			words.append(word + " ");
		message.setText(words.toString());
	}
}
