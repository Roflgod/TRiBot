package scripts.roflgod.settingsexplorer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used for logging setting changes.
 * 
 * @author Roflgod
 */
public class SettingLog implements Comparable<SettingLog> {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"h:m:s a");

	private final long time;
	private final String formattedTime;
	private final int index, prevVal, newVal;

	public SettingLog(int index, int prevVal, int newVal) {
		this.time = System.currentTimeMillis();
		this.formattedTime = FORMAT.format(new Date(time));

		this.index = index;
		this.prevVal = prevVal;
		this.newVal = newVal;
	}

	public long getTime() {
		return time;
	}

	public int getIndex() {
		return index;
	}

	public int getPrev() {
		return prevVal;
	}

	public int getNew() {
		return newVal;
	}

	@Override
	public int compareTo(SettingLog s) {
		return Long.compare(time, s.time);
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		s.append('{');
		s.append(formattedTime);
		s.append("} [");
		s.append(index);
		s.append("] ");
		s.append(prevVal);
		s.append(" => ");
		s.append(newVal);
		return s.toString();
	}

}