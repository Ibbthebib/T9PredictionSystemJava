
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.GridLayout;

public class KeypadController extends JPanel {

	private JButton key1;
	private JButton key2;
	private JButton key3;
	private JButton key4;
	private JButton key5;
	private JButton key6;
	private JButton key7;
	private JButton key8;
	private JButton key9;
	private JButton key0;
	private JButton keyStar;
	private JButton keyBlank;

	public KeypadController(DictionaryModel m) {
		super();

		setLayout(new GridLayout(4, 3));

		key1 = new JButton("<html><center> 1 <p>  </center></html>");
		key2 = new JButton("<html><center> 2 <p> abc </center></html>");
		key3 = new JButton("<html><center> 3 <p> def </center></html>");
		key4 = new JButton("<html><center> 4 <p> ghi </center></html>");
		key5 = new JButton("<html><center> 5 <p> jkl </center></html>");
		key6 = new JButton("<html><center> 6 <p> mno </center></html>");
		key7 = new JButton("<html><center> 7 <p> pqrs </center></html>");
		key8 = new JButton("<html><center> 8 <p> tuv </center></html>");
		key9 = new JButton("<html><center> 9 <p> vwxy </center></html>");
		keyBlank = new JButton("<html> C </html>");
		key0 = new JButton("<html><center> 0 <p> _ </center></html>");
		keyStar = new JButton(" * ");

		key2.addActionListener(new KeypadListener(m, '2'));
		key3.addActionListener(new KeypadListener(m, '3'));
		key4.addActionListener(new KeypadListener(m, '4'));
		key5.addActionListener(new KeypadListener(m, '5'));
		key6.addActionListener(new KeypadListener(m, '6'));
		key7.addActionListener(new KeypadListener(m, '7'));
		key8.addActionListener(new KeypadListener(m, '8'));
		key9.addActionListener(new KeypadListener(m, '9'));
		keyStar.addActionListener(new KeypadListener(m, '*'));
		key0.addActionListener(new KeypadListener(m, '0'));
		keyBlank.addActionListener(new KeypadListener(m, 'C'));

		add(key1);
		add(key2);
		add(key3);
		add(key4);
		add(key5);
		add(key6);
		add(key7);
		add(key8);
		add(key9);
		add(keyStar);
		add(key0);
		add(keyBlank);
	}

}
