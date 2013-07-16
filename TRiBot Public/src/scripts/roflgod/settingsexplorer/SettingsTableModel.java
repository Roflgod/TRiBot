package scripts.roflgod.settingsexplorer;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Roflgod
 */
@SuppressWarnings("serial")
public class SettingsTableModel extends AbstractTableModel implements
		SettingListener {

	private static final String[] COLUMN_NAMES = { "Index", "Value" };

	@Override
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	@Override
	public int getRowCount() {
		List<Setting> settings = SettingsExplorer.getSettings();
		if (settings != null) {
			return settings.size();
		}
		return 2000;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (col == 0) {
			return row;
		}
		List<Setting> settings = SettingsExplorer.getSettings();
		if (settings != null) {
			Setting setting = settings.get(row);
			if (setting != null) {
				final StringBuilder s = new StringBuilder();
				s.append(setting.getValue());
				if (setting.getLastChange() != -1) {
					s.append(" {");
					s.append(setting.getLastChangeString());
					s.append('}');
				}
				return s.toString();
			}
		}
		return -1;
	}

	@Override
	public void settingChanged(SettingLog log) {
		fireTableCellUpdated(1, log.getIndex());
	}

}