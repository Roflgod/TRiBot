package scripts.roflgod.settingsexplorer;

import scripts.roflgod.ui.SortableListModel;

/**
 * @author Roflgod
 */
@SuppressWarnings("serial")
public class SettingLogsListModel extends SortableListModel<SettingLog>
		implements SettingListener {

	@Override
	public SettingLog getElementAt(int index) {
		return items.get(items.size() - 1 - index);
	}

	@Override
	public void settingChanged(SettingLog log) {
		add(log);
	}

}