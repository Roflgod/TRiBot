package scripts.roflgod.settingsexplorer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * 
 * @author Roflgod
 *
 * @param <E>
 */
@SuppressWarnings("serial")
public class SortableListModel<E extends Comparable<E>> extends AbstractListModel<E> {
	
	protected List<E> items = new ArrayList<>();

	@Override
	public int getSize() {
		return items.size();
	}

	@Override
	public E getElementAt(int index) {
		return items.get(index);
	}
	
	public E getFirst() {
		if (!items.isEmpty()) {
			return items.get(0);
		}
		return null;
	}

	public E getLast() {
		if (!items.isEmpty()) {
			return items.get(items.size() - 1);
		}
		return null;
	}

	public void add(E item) {
		int index = items.size();
		items.add(item);
		fireIntervalAdded(this, index, index);
	}
	
	public void clear() {
		if (!items.isEmpty()) {
			int end = getSize() - 1;
			items = new ArrayList<>();
			if (end >= 0) {
				fireIntervalRemoved(this, 0, end);
			}
		}
	}
	
	public void sort() {
		sort(null);
	}
	
	public void sort(Comparator<E> comparator) {
		if (comparator == null) {
			Collections.sort(items);
		} else {
			Collections.sort(items, comparator);
		}
		fireContentsChanged(this, 0, getSize() - 1);
	}
	
}