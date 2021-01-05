package mips;

/**
 * Control.java
 * Used to take the input and invoke the mips instruction
 * and determine control values in the pipeline
 */
public class Control {
	 //Each control line distinct Boolean variable.

	private boolean RegDist;
	private boolean Branch;
	private boolean MemRead;
	private boolean MemtoReg;
	private boolean ALUOp1;
	private boolean ALUOp0;
	private boolean MemWrite;
	private boolean ALUsrc;
	private boolean RegWrite;

	/**
	 * Control()
	 * @param instruction
	 * Class Constructor
	 * Determines control values for each instruction type
	 */
	public Control(InstructionHandler instruction) {
		short opcode = instruction.getOpcode();

		if(instruction.is_r_type()) {
			RegDist = true;
			RegWrite = true;
			ALUOp1 = true;
		}

		else if(opcode == InstructionHandler.OPCODE_LW) {
			MemRead = true;
			MemtoReg = true;
			RegWrite = true;
			ALUsrc = true;
		}

		else if(opcode == InstructionHandler.OPCODE_SW) {
			MemWrite = true;
			ALUsrc = true;
		}

		else if(opcode == InstructionHandler.OPCODE_BEQ || opcode == InstructionHandler.OPCODE_J) {
			Branch = true;
			ALUOp0 = true;
		}
	}

	/**
	 * isRegDist()
	 * @return bool
	 */
	public boolean isRegDist() {
		return RegDist;
	}

	/**
	 * isBranch()
	 * @return bool
	 */
	public boolean isBranch() {
		return Branch;
	}

	/**
	 * isMemRead()
	 * @return bool
	 */
	public boolean isMemRead() {
		return MemRead;
	}

	/**
	 * isMemtoReg()
	 * @return bool
	 */
	public boolean isMemtoReg() {
		return MemtoReg;
	}

	/**
	 * isALUOp1()
	 * @return bool
	 */
	public boolean isALUOp1() {
		return ALUOp1;
	}

	/**
	 * isALUOp0()
	 * @return bool
	 */
	public boolean isALUOp0() {
		return ALUOp0;
	}

	/**
	 * isMemWrite()
	 * @return bool
	 */
	public boolean isMemWrite() {
		return MemWrite;
	}

	/**
	 * isALUsrc()
	 * @return bool
	 */
	public boolean isALUsrc() {
		return ALUsrc;
	}

	/**
	 * isRegWrite()
	 * @return bool
	 */
	public boolean isRegWrite() {
		return RegWrite;
	}


}
