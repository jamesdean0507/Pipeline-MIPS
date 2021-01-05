package mips;
/**
 * ProgramCounter.java
 */
public class ProgramCounter {
	private int value;

	/**
	 * ProgramCounter()
	 * Class Constructor
	 */
	public ProgramCounter() {
		value = 0;
	}

	/**
	 * setPC()
	 * @param value
	 * PC Setter method
	 */
	public void setPC(int value) {
		assert value%4 == 0;
		this.value = value;
	}

	/**
	 * getPC()
	 * @return
	 * PC getter method
	 */
	public int getPC() {
		return value;
	}

	/**
	 * resetPC()
	 */
	public void resetPC() {
		value = 0;
	}
}
