package mips;

import java.util.List;
import java.util.ListIterator;

/**
 * InstructionMemoryFile.java
 * Stores instructions in a list after 
 * being parsed in InstructionHandler
 */
public class InstructionMemoryFile {
	private InstructionHandler[] instructions = {};

	/**
	 * loadIM()
	 * @param instructions
	 */
	public void loadIM(List<InstructionHandler> instructions) {
		this.instructions = new InstructionHandler[instructions.size()];
		ListIterator<InstructionHandler> iterator = instructions.listIterator();
		for(int i = 0; i < instructions.size(); i++) {
			this.instructions[i] = iterator.next();
		}
	}

	/**
	 * findInstruction()
	 * @param pc
	 * @return
	 */
	public InstructionHandler findInstruction(ProgramCounter pc) {
		return instructions[pc.getPC() / 4];
	}

	/**
	 * length()
	 * @return Instruction Length
	 */
	public int length() {
		return instructions.length * 4;
	}

}
