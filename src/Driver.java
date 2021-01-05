import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import mips.InstructionHandler;
import mips.Pipeline;
import mips.RegFile;

/**
 * Driver.java
 * Ties the GUI with the pipeline simulator
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Driver {
	private GUI gui;
	private Pipeline processor;
	private List<InstructionHandler> instructions;

	private DefaultListModel instructionFile;
	private DefaultListModel registerFile;
	private DefaultListModel memoryFile;

	private volatile boolean running = false;
	private boolean hexadecimal = false;

	/**
	 * Driver()
	 * Class Constructor
	 */
	public Driver() {
		gui = new GUI();
		gui.setGUIListener(activeListener);

		processor = new Pipeline();

		instructionFile = new DefaultListModel();
		gui.setInstructionList(instructionFile);

		registerFile = new DefaultListModel();
		gui.setRegisterList(registerFile);

		memoryFile = new DefaultListModel();
		gui.setMemoryList(memoryFile);
	}

	/**
	 * update()
	 * Update GUI with current Pipeline status
	 */
	private void update() {
		int pc = processor.getPcValue();
		gui.setPc(pc);

		int instructionIndex = pc/4;
		if(instructionIndex >= instructionFile.getSize()) {
			gui.clearInstructionSelection();
		} else {
			gui.selectInstruction(instructionIndex);
		}

		registerFile.clear();
		memoryFile.clear();

		int[] registerData = processor.getRegisters();
		List<Integer> changedRegisters = processor.getChangedRegisters();
		for(int index : changedRegisters) {
			String regString = String.format(
					"%s: %s", RegFile.nameRegisters(index), string_value(registerData[index]));
			registerFile.addElement(regString);
		}

		int[] memoryData = processor.getMemory();
		List<Integer> changedMemory = processor.getChangedMemory();
		for(int index : changedMemory) {
			String regString = String.format(
					"%s: %s", string_value((short)index), string_value(memoryData[index]));
			memoryFile.addElement(regString);
		}
	}

	/**
	 * string_value()
	 * @param b
	 * @return String (Hex or decimal)
	 */
	private String string_value(int b) {
		if(hexadecimal) {
			return String.format("0x%x", b & 0xffffffffL);
		} else {
			return String.format("%d", b & 0xffffffffL);
		}
	}

	/**
	 * updateLater()
	 * delayed update to GUI thread
	 */
	private void updateLater() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				update();
			}
		});
	}

	/**
	 * run()
	 * Runs simulation thread until quit
	 */
	private void run() {
		if(running) {
			return;
		}
		new Thread(){
			@Override
			public void run() {
				if(running) {
					return;
				}
				running = true;
				while(running && !processor.isDone()) {
					step();
				}
				updateLater();
			};
		}.start();
	}

	/**
	 * stop()
	 */
	private void stop() {
		running = false;
	}

	/**
	 * step()
	 * used to iterate through Pipeline line by line
	 */
	private synchronized void step() {
		processor.iteratePipeline();
	}

	/**
	 * reset()
	 * Stop current thread and clears memory
	 */
	private void reset() {
		stop();
		step(); //Block until running stops
		processor.clearMemoryContents();
	}

	/**
	 * renderInsrtuctions()
	 */
	private void renderInstructions() {
		instructionFile.clear();
		for(InstructionHandler i : instructions) {
			instructionFile.addElement(i.representation(hexadecimal));
		}
	}

	/**
	 * load()
	 * Initiates loading from Input
	 * @param filename (Instructions Path)
	 */
	private void load(String filename) {
		String line;
		BufferedReader reader = null;
		instructions = new ArrayList<InstructionHandler>();

		instructionFile.clear();

		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int i = 0;
			while((line = reader.readLine()) != null){
				i++;
				if(line.length() == 0) {
					continue;
				}
				try {
					InstructionHandler instruction = new InstructionHandler(line);
					instructions.add(instruction);
				} catch (Exception e) {
					System.out.printf("Invalid instruction '%s' on line %d\n", line, i);
				}
			}
		} catch (IOException e) {
			System.out.printf("File reading error: %s \n", e.getMessage());
		}
		processor.setInstructionSet(instructions);
		renderInstructions();
		update();
	}

	/**
	 * activeListener
	 * Listens for each button to be pressed
	 */
	private GUI.GUIListener activeListener = new GUI.GUIListener() {

		@Override
		public void onStop() {
			stop();
			update();
		}

		@Override
		public void onStep() {
			step();
			update();
		}

		@Override
		public void onRun() {
			run();
		}

		@Override
		public void onReset() {
			reset();
			update();
		}

		@Override
		public void onLoad(String filename) {
			load(filename);
		}

		@Override
		public void onHex() {
			hexadecimal = true;
			renderInstructions();
			update();
		}

		@Override
		public void onDec() {
			hexadecimal = false;
			renderInstructions();
			update();
		}
	};

	/**
	 * main()
	 * @param args
	 * Will run the program
	 */
	public static void main(String[] args) {
		new Driver();
	}
}
