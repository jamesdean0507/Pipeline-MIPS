package mips;

/**
 * ALU.java
 * Class implements arithmetic logical operations of the pipeline
 */
public class ALU {
	public static final short AND = 0;
	public static final short OR = 1;
	public static final short ADD = 2;
	public static final short SLL = 3;
	public static final short SRL = 4;
	public static final short MUL = 5;
	public static final short SUBTRACT = 6;
	public static final short NOR = 12;
	
	private int rd;
	private boolean zero;

	/**
	 * getRd()
	 * Getter method for rd
	 * @return the out value
	 */
	public int getRd() {
		return rd;
	}

	/**
	 * isZero()
	 * Bool getter method
	 * @return zero
	 */
	public boolean isZero() {
		return zero;
	}

	/**
	 * setLogicOperation(short, int, int)
	 * defines cases for each arithmetic logical operation needed
	 * @param operation
	 * @param rt
	 * @param rs
	 */
	public void setLogicOperation(short operation, int rt, int rs) {
		zero = false;
		rd = 0;
		
		switch(operation) {
		case ADD:
			rd =  rs + rt;
			break;
		case SUBTRACT:
			rd = rs - rt;
			if(rd == 0) {
				zero = true;
			}
			break;
		case AND:
			rd = rs & rt;
			break;
		case OR:
			rd = rs | rt;
			break;
		case SLL:
			rd = rs << rt;
			break;
		case SRL:
			rd = rs >> rt;
			break;
		case MUL:
			rd = rs * rt;
			break;
		case NOR:
			rd = ~(rs | rt);
			break;
		}
	}

/**
* getControl()
* takes op code and determines ALU operation
*/
	public static short getControl(boolean opALU1, boolean opALU0, short fcn){
		if(!opALU1 && !opALU0) {
			return ALU.ADD;
		}
		if(opALU0) {
			return ALU.SUBTRACT;
		}
		switch(fcn & 15) {
		case 0:
			return ALU.ADD;
		case 1:
			return ALU.SLL;
		case 2:
			return ALU.SUBTRACT;
		case 3:
			return ALU.SRL;
		case 4:
			return ALU.AND;
		case 5:
			return ALU.OR;
		case 7:
			return ALU.NOR;
		case 8:
			return ALU.MUL;
		}

		assert false;
		return 0;
	}
}
