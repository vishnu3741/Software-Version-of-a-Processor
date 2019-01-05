package processor.pipeline;

import generic.Operand;

public class OF_EX_LatchType {
	
	boolean EX_enable = false;
	boolean EX_busy = false;
	String opcode;
	Operand rs1 = new Operand();
	Operand rs2 = new Operand();
	Operand rd = new Operand();
	int rd_address;
	int flag;
	Operand Immediate1 = new Operand();
	Operand Immediate2 = new Operand();
	
	public void get_Operand_Values(int v1,int v2,int v3,int v4,int v5,int v6,int v7) {
		this.rs1.setValue(v1);
		this.rs2.setValue(v2);
		this.rd.setValue(v3);
		this.Immediate1.setValue(v4);
		this.Immediate2.setValue(v5);
		this.rd_address = v6;
		this.flag = v7;
		return;
	}
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	
	public boolean isEX_busy() {
		return EX_busy;
	}
	
	public void setEX_busy(boolean eX_busy) {
		EX_busy = eX_busy;
	}

}
