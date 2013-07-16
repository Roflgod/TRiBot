package scripts.roflgod.settingsexplorer;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * A text field that starts with default text and reverts to default text if
 * empty.
 * 
 * @author Roflgod
 */
@SuppressWarnings("serial")
public class DefaultTextTextField extends JTextField implements FocusListener {

	private String defaultText;
	private String invalidText;

	public DefaultTextTextField(String defaultText, String invalidText) {
		super(defaultText);
		setForeground(Color.GRAY);
		this.defaultText = defaultText;
		this.invalidText = invalidText;
		addFocusListener(this);
	}

	public DefaultTextTextField(String defaultText) {
		this(defaultText, null);
	}

	public String getDefaultText() {
		return defaultText;
	}
	
	public String getInvalidText() {
		return invalidText;
	}

	public void setInvalid() {
		if (invalidText != null) {
			setForeground(Color.red);
			setText(invalidText);
			setCaretPosition(0);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (getText().equals(defaultText) || getText().equals(invalidText)) {
			setForeground(Color.BLACK);
			setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (getText().isEmpty()) {
			setForeground(Color.GRAY);
			setText(defaultText);
		}
	}

}