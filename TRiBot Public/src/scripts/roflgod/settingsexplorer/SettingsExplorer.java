package scripts.roflgod.settingsexplorer;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.tribot.api2007.Game;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;

/**
 * @author Roflgod
 */
@ScriptManifest(version = 1, name = "Settings Explorer", authors = "Roflgod", category = "Tools")
public class SettingsExplorer extends Script implements Ending {
	private static List<Setting> settingsList;

	public static List<Setting> getSettings() {
		return settingsList;
	}

	private static SettingsGUI gui;

	private AtomicBoolean paused = new AtomicBoolean();

	public boolean togglePause() {
		paused.set(!paused.get());
		return paused.get();
	}

	@Override
	public void onEnd() {
		if (gui != null) {
			gui.dispose();
		}
		settingsList = null;
	}

	@Override
	public void run() {
		// POPULATING THE LIST OF SETTINGS
		settingsList = new ArrayList<>();
		for (int i = 0; i < Game.getSettingsArray().length; i++) {
			getSettings().add(new Setting(i));
		}

		// STARTING THE GUI
		try {
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					gui = new SettingsGUI(SettingsExplorer.this);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}

		// UPDATING THE SETTINGS
		boolean running = true;
		while (running) {
			if (!paused.get()) {
				for (Setting setting : getSettings()) {
					SettingLog log = setting.check();
					if (log != null) {
						gui.addSettingLog(log);
						System.out
								.println("[Settings Explorer] Setting changed: "
										+ setting.getIndex());
					}
				}
			}

			sleep(10);
		}
	}

}