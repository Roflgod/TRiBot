package scripts.roflgod.settingsexplorer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.tribot.api2007.Game;

/**
 * Wrapper class for settings.
 * 
 * @author Roflgod
 */
public class Setting {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"h:m:s a");

	private final int index;

	private AtomicInteger lastValue;
	private long lastChange = -1;

	public Setting(int index) {
		this.index = index;
		this.lastValue = new AtomicInteger(Game.getSetting(index));
	}

	/**
	 * Checks for a change in the value of this setting.
	 * 
	 * @return The {@link SettingLog} if there has been a change; otherwise,
	 *         null.
	 */
	public SettingLog check() {
		int lastValueInt = lastValue.get();
		int newValue = Game.getSetting(index);
		if (lastValueInt != newValue) {
			lastChange = System.currentTimeMillis();
			SettingLog log = new SettingLog(index, lastValueInt, newValue);
			lastValue.set(newValue);
			return log;
		}
		return null;
	}

	public int getIndex() {
		return index;
	}

	public int getValue() {
		return lastValue.get();
	}

	public long getLastChange() {
		return lastChange;
	}

	/**
	 * @return The last time the setting change in a formatted string. If the
	 *         setting has not changed yet, "never" is returned.
	 */
	public String getLastChangeString() {
		if (lastChange == -1) {
			return "never";
		}
		return getFormattedTime();
	}

	private String getFormattedTime() {
		return FORMAT.format(new Date(lastChange));
	}

}