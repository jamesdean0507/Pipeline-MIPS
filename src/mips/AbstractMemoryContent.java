package mips;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractMemoryContent.java
 * Used to define datasets used in memory
 * Outlines Memory Content implementation
 */
public abstract class AbstractMemoryContent {
	private int[] data;
	private List<Integer> changedField;

	/**
	 * AbstractMemoryContent()
	 * @param size
	 * Class Constructor
	 */
	public AbstractMemoryContent(int size) {
		data = new int[size];
		resetContent();
	}

	/**
	 * resetContent()
	 * sets data array = 0 through iteration
	 */
	public void resetContent() {
		changedField = new ArrayList<Integer>();
		for(int i = 0; i < data.length; i++) {
			data[i] = 0;
		}
	}

	/**
	 * getDataAt()
	 * @param index
	 * @return int
	 * getter method for data tag at specific index
	 */
	protected int getDataAt(int index) {
		return data[index];
	}

	/**
	 * set()
	 * @param index
	 * @param value
	 * Calls touch method
	 */
	protected void set(int index, int value) {
		touch(index);
		data[index] = value;
	}

	/**
	 * touch()
	 * @param index
	 * Replaces current value at index in dictionary with 0
	 */
	private void touch(int index) {
		if(changedField.contains(index)) {
			changedField.remove(changedField.indexOf(index));
		}
		changedField.add(0, index);
	}

	/**
	 * getRawData()
	 * @return data
	 */
	public int[] getRawData() {
		return data.clone();
	}

	public List<Integer> getChangedIndices() {
		return new ArrayList<Integer>(changedField);
	}

}
