package mips;

/**
 * MainMemory.java
 * Creates a memory class for Main Memory
 * extends AbstractMemoryContent
 */
public class MainMemory extends AbstractMemoryContent {
	/**
	 * MainMemory()
	 * Class Constructor
	 */
	public MainMemory() {
		super(1000);
	}

	/**
	 * writeRead()
	 * @param addr
	 * @param write_data
	 * @param memRead
	 * @param memWrite
	 * @return
	 * Read/Write to Memory
	 */
	public int writeRead(int addr, int write_data, boolean memRead,
			boolean memWrite) {
		if(memWrite) {
			set(addr, write_data);
		}

		if(memRead) {
			return getDataAt(addr);
		}

		return 0;
	}
}
