package scripts.roflgod.settingsexplorer;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Roflgod
 */
@SuppressWarnings("serial")
public class SettingInfoTableModel extends AbstractTableModel implements
		SettingListener {

	private static final String BINARY_PREFIX = "0b";
	private static final String HEX_PREFIX = "0x";
	private static final String[] ROW_NAMES = { "Index", "Value", "Binary",
			"Hex", "Last changed" };

	private int selectedSetting = -1;

	@Override
	public int getRowCount() {
		return ROW_NAMES.length;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// if no setting is selected
		if (selectedSetting == -1) {
			return "";
		} else if (col == 0) {
			return ROW_NAMES[row];
		} else {
			List<Setting> settings = SettingsExplorer.getSettings();
			if (settings != null) {
				Setting setting = settings.get(selectedSetting);
				int value = setting.getValue();
				switch (row) {
				case 0:
					return selectedSetting;
				case 1:
					return value;
				case 2:
					if (value == 0) {
						return 0;
					}
					return BINARY_PREFIX + Integer.toBinaryString(value);
				case 3:
					if (value == 0) {
						return 0;
					}
					return HEX_PREFIX + Integer.toHexString(value);
				case 4:
					return setting.getLastChangeString();
				}
			}
		}
		return null;
	}

	@Override
	public void settingChanged(SettingLog log) {
		fireTableDataChanged();
	}

	public void setSelected(int index) {
		selectedSetting = index;
		fireTableDataChanged();
	}

}
