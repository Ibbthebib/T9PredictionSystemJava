
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

import predictive.Dictionary;

import java.util.Collections;

public class DictionaryModel extends Observable {

	// This class represents the model for the telephone keypad GUI.

	// The dictionary object
	private Dictionary dict;
	// The current signature being typed by the user
	private String currentSig;
	// The matches for the current signature
	private List<String> currentMatches;
	// The overall text message being composed
	private List<String> message;
	// The current index into the currentMatches
	private int index;

	public DictionaryModel() throws java.io.IOException {
		this("words");
	}

	public DictionaryModel(String dictionaryFile) throws java.io.IOException {
		super();
		dict = new TreeDictionary(dictionaryFile);

		message = new ArrayList<String>();

		// start a new word entry
		startNewWordEntry();
	}

	// Return the words in the current message typed in, including the
	// current word.

	public List<String> getWords() {
		ArrayList<String> words = new ArrayList<String>(message);
		words.add(getCurrentWord());
		return words;
	}

	// Process a key press

	public void press(char key) {
		if (key == '0') {
			// previous word was entered ok
			message.add(getCurrentWord());
			// start a new word entry
			startNewWordEntry();
		} else if (key == '*') {
			// change the current word in the currentMatches
			incrementWordIndex();
		} else if (key == 'C') {
			// remove the last character from the current signature
			if (currentSig.length() > 0) {
				deleteCharacter();
			}
		} else {
			// add a new character to the current signature
			addCharacter(key);
		}

		setChanged();
		notifyObservers();
	}

	// start a new word entry
	private void startNewWordEntry() {
		currentMatches = new ArrayList<String>();
		currentMatches.add("");
		currentSig = "";
		index = 0;
	}

	// change the current word in the currentMatches
	private void incrementWordIndex() {
		index++;
		if (index == currentMatches.size())
			index = 0;
	}

	// perform a dictionary look-up
	private void addCharacter(char key) {
		String prefix = currentMatches.get(index);
		ArrayList<String> matches = new ArrayList<String>(dict.signatureToWords(currentSig + key));
		if (matches.size() > 0) {
			currentSig = currentSig + key;
			currentMatches = matches;
			index = -Collections.binarySearch(currentMatches, prefix) - 1;
			// the prefix will not be an element of matches
			// binarySearch returns -(insertionpoint)-1
			// so, we calculate the insertionpoint.
			// if the insertionpoint is beyond the array, we cycle around.
			if (index >= currentMatches.size())
				index = 0;
			assert 0 <= index && index < currentMatches.size() : index;
		}
	}

	private void deleteCharacter() {
		String prefix = currentMatches.get(index);
		prefix = prefix.substring(0, currentSig.length() - 1);
		currentSig = currentSig.substring(0, currentSig.length() - 1);
		if (currentSig.length() > 0) {
			currentMatches = new ArrayList<String>(dict.signatureToWords(currentSig));
			index = Collections.binarySearch(currentMatches, prefix);

		} else {
			currentMatches = new ArrayList<String>();
			currentMatches.add("");
			index = 0;
		}
		assert 0 <= index && index < currentMatches.size() : index;
	}

	private String getCurrentWord() {
		// if there is a match return it else return the empty string
		if (currentMatches.size() > 0)
			return currentMatches.get(index);
		else
			return "";
	}

}
