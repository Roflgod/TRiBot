package scripts.roflgod.settingsexplorer;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.text.PlainDocument;

import net.miginfocom.swing.MigLayout;

/**
 * @author Roflgod
 */
@SuppressWarnings("serial")
public class SettingsGUI extends JFrame {

	private final JScrollPane scrollPaneSettings = new JScrollPane();

	private final SettingsTableModel tableModelSettings = new SettingsTableModel();
	private final JTable tableSettings = new JTable(tableModelSettings);

	private final SettingInfoTableModel tableModelInfo = new SettingInfoTableModel();
	private final JTable tableInfo = new JTable(tableModelInfo);

	private final SettingLogsListModel listModelLogs = new SettingLogsListModel();
	private final JList<SettingLog> listLogs = new JList<SettingLog>(
			listModelLogs);
	private final JScrollPane scrollPaneLogs = new JScrollPane();
	private final DefaultTextTextField txtFind = new DefaultTextTextField(
			"Find", "Invalid input");
	private final JButton btnPause = new JButton("Pause");
	private final JButton btnClear = new JButton("Clear");

	public SettingsGUI(final SettingsExplorer script) {
		setTitle("Roflgod's Settings Explorer");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				script.stopScript();
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 542);

		getContentPane().setLayout(
				new MigLayout("inset 0", "[280px:280px:280px,grow][1px,grow]",
						"[][grow][][]"));

		getContentPane().add(scrollPaneSettings,
				"cell 0 0 1 2,alignx left,growy");
		{
			scrollPaneSettings.setViewportView(tableSettings);
			tableSettings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSettings.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							tableModelInfo.setSelected(tableSettings
									.getSelectedRow());
						}
					});

			final TableColumn indexCol = tableSettings.getColumn("Index");
			final int indexColWidth = 40;
			indexCol.setResizable(false);
			indexCol.setMinWidth(indexColWidth);
			indexCol.setMaxWidth(indexColWidth);
			indexCol.setPreferredWidth(indexColWidth);

			final TableColumn valueCol = tableSettings.getColumn("Value");
			valueCol.setResizable(false);
		}

		getContentPane().add(tableInfo, "cell 1 0,grow");
		{
			tableInfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			final TableColumn labelCol = tableInfo.getColumnModel()
					.getColumn(0);
			final int labelColWidth = 100;
			labelCol.setResizable(false);
			labelCol.setMinWidth(labelColWidth);
			labelCol.setMaxWidth(labelColWidth);
			labelCol.setPreferredWidth(labelColWidth);
		}

		getContentPane().add(scrollPaneLogs, "cell 1 1 1 2,grow");
		{
			scrollPaneLogs.setViewportView(listLogs);
		}

		getContentPane().add(txtFind, "cell 0 2,growx");
		{
			PlainDocument doc = (PlainDocument) txtFind.getDocument();
			doc.setDocumentFilter(new CustomDocumentFilter() {
				@Override
				public boolean accept(String text) {
					if (text.isEmpty() || text.equals(txtFind.getDefaultText())
							|| text.equals(txtFind.getInvalidText())) {
						return true;
					}
					try {
						Integer.parseInt(text);
						return true;
					} catch (NumberFormatException e) {
						return false;
					}
				}
			});
			txtFind.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (txtFind.getText().equals(txtFind.getInvalidText())) {
						txtFind.setForeground(Color.black);
						txtFind.setText("");
					}
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							&& !txtFind.getText().isEmpty()) {
						int index = Integer.parseInt(txtFind.getText());
						if (index < tableModelSettings.getRowCount()) {
							Rectangle rect = new Rectangle(tableSettings
									.getCellRect(index, 0, true));
							tableSettings.scrollRectToVisible(rect);
							tableSettings.getSelectionModel()
									.setSelectionInterval(index, index);
						} else {
							txtFind.setInvalid();
						}
					}
				}
			});
		}

		getContentPane().add(btnPause, "cell 0 3,grow");
		{
			btnPause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean paused = script.togglePause();
					btnPause.setText(paused ? "Start" : "Pause");
				}
			});
		}

		getContentPane().add(btnClear, "cell 1 3,grow");
		{
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					listModelLogs.clear();
				}
			});
		}

		setVisible(true);
	}

	public void addSettingLog(SettingLog log) {
		tableModelSettings.settingChanged(log);
		tableModelInfo.settingChanged(log);
		listModelLogs.settingChanged(log);
	}

}