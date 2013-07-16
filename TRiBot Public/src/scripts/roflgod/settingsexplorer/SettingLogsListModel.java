package scripts.roflgod.settingsexplorer;


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