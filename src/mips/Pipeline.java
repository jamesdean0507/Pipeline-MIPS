package mips;
import java.util.ArrayList;
import java.util.List;


/**
 * This class weaves together all the modules of the mips processor
 * with muxes, provides a way to step through a set of instructions, 
 * and allows the state of the register and memory to be retrieved.
 */
public class Pipeline {

	//private ProgramCounter pc;

	private RegFile register;
	private MainMemory memory;
	private ALU alu;
	private ProgramCounter pc;
	private InstructionMemoryFile instructions;


	/**
	 * Creates a new processor with a zeroes register and memory
	 */
	public Pipeline() {
		pc = new ProgramCounter();
		instructions = new InstructionMemoryFile();
		register = new RegFile();
		alu = new ALU();
		memory = new MainMemory();
	}

	/**
	 * Feeds instructions into the processor
	 * Also resets the processor
	 * @param instructions
	 */
	public void setInstructionSet(List<InstructionHandler> instructions) {
		this.instructions.loadIM(new ArrayList<InstructionHandler>(instructions));
		clearMemoryContents();
	}

	/**
	 * clearMemoryContents()
	 * Resets all registers, memory, and pc to 0
	 */
	public void clearMemoryContents() {
		pc.resetPC();
		register.resetContent();
		memory.resetContent();
	}

	/**
	 * iteratePipeline()
	 * Instruction by instruction this method iterates through the pipeline process
	 */
	public void iteratePipeline() {
		InstructionHandler i;
		int outALU = 0;
		boolean alu0 = false;
		int outData = 0;
		int writeBack;
		int rs = 0;
		int rt = 0;
		int freshPC = pc.getPC();
		int branchPC;

		if(isDone()) {
			return;
		}

		// Instruction Fetch
		i = instructions.findInstruction(pc);
		Control control = new Control(i);

		// Instruction Decode
		int writeReg = mux(i.getRt(), i.getRd(), control.isRegDist());
		register.setRegisters(i.getRs(), i.getRt(), writeReg);
		rs = register.readData0();
		rt = register.readData1();

		// Execution Stage
		alu.setLogicOperation(
				ALU.getControl(control.isALUOp1(), control.isALUOp0(), i.getFunct()),
				mux(rt, i.getImm(), control.isALUsrc()),
				rs);
		outALU = alu.getRd();
		alu0 = alu.isZero();

		// Memory Stage
		outData = memory.writeRead(outALU, rt, control.isMemRead(), control.isMemWrite());

		// Write Back
		writeBack = mux(outALU, outData, control.isMemtoReg());
		register.writeToReg(control.isRegWrite(), writeBack);


		freshPC += 4;
		branchPC = freshPC + (i.getImm() << 2);
		freshPC = mux(freshPC, branchPC, control.isBranch() && alu0);
		pc.setPC(freshPC);
	}

	/**
	 * mux()
	 * @param value1
	 * @param value2
	 * @param getSecond
	 * @return
	 * Imitates a MUX
	 */
	private int mux(int value1, int value2, boolean getSecond) {
		if(getSecond) {
			return value2;
		}
		return value1;
	}

	/**
	 * isDone()
	 * @return
	 * Returns true if the simulation is done
	 */
	public boolean isDone() {
		return pc.getPC() >= instructions.length() || instructions.findInstruction(pc).isExit();
	}

	/**
	 * getPcValue()
	 * @return Program Counter
	 */
	public int getPcValue() {
		return pc.getPC();
	}

	/**
	 * getRegisters()
	 * @return
	 */
	public int[] getRegisters() {
		return register.getRawData();
	}

	/**
	 * getMemory()
	 * @return
	 */
	public int[] getMemory() {
		return memory.getRawData();
	}

	/**
	 * getChangedRegisters()
	 * @return
	 */
	public List<Integer> getChangedRegisters() {
		return register.getChangedIndices();
	}

	/**
	 * getChangedMemory()
	 * @return
	 */
	public List<Integer> getChangedMemory() {
		return memory.getChangedIndices();
	}


}
