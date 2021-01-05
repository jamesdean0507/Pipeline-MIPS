package mips;

/**
 * RegFile.java
 * Creates a memory class for the Register File
 * extends AbstractMemoryContent
 */
public class RegFile extends AbstractMemoryContent {
	private int read0;
	private int read1;
	private int write;

	/**
	 * RegFile()
	 * Class Constructor
	 */
	public RegFile() {
		super(32);
	}

	/**
	 * setRegisters()
	 * @param read0
	 * @param read1
	 * @param write
	 */
	public void setRegisters(int read0, int read1, int write) {
		this.read0= read0;
		this.read1 = read1;
		this.write = write;
	}

	/**
	 * readData0()
	 * @return data read0
	 */
	public int readData0() {
		return getDataAt(read0);
	}

	/**
	 * readData1()
	 * @return data read1
	 */
	public int readData1() {
		return getDataAt(read1);
	}

	/**
	 * writeToReg()
	 * @param RegWrite
	 * @param data
	 */
	public void writeToReg(boolean RegWrite, int data) {
		if(RegWrite) {
			set(write, data);
		}
	}


	/**
	 * getDataAt()
	 * @param index
	 * @return data at index
	 */
	@Override
	protected int getDataAt(int index) {
		if(index == 0) {
			return 0; // $zero register
		}
		return super.getDataAt(index);
	}

	/**
	 * set()
	 * @param index
	 * @param value
	 */
	@Override
	protected void set(int index, int value) {
		if(index == 0) {
			return;
		}
		super.set(index, value);
	}

	/**
	 * nameRegisters()
	 * @param index
	 * @return String[] regNames
	 */
	public static String nameRegisters(int index) {
		String[] regNames = {
				"$zero", 
				"$at",
				"$v0",
				"$v1",
				"$a0",
				"$a1",
				"$a2",
				"$a3",
				"$t0",
				"$t1",
				"$t2",
				"$t3",
				"$t4",
				"$t5",
				"$t6",
				"$t7",
				"$s0",
				"$s1",
				"$s2",
				"$s3",
				"$s4",
				"$s5",
				"$s6",
				"$s7",
				"$t8",
				"$t9",
				"$k0",
				"$k1",
				"$gp",
				"$sp",
				"$fp",
				"$ra"
		};
		return regNames[index];
	}
}
