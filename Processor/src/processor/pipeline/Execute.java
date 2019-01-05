package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{	
		if(OF_EX_Latch.isEX_enable()) {
			OF_EX_Latch.setEX_busy(true);
			if(EX_MA_Latch.isMA_busy()) {
				return;
			}
			
			EX_MA_Latch.flag = OF_EX_Latch.flag;
			EX_MA_Latch.return_address = OF_EX_Latch.rd_address;
			EX_MA_Latch.ALU_result = containingProcessor.ALU_Unit(OF_EX_Latch.rs1.getValue(), OF_EX_Latch.rs2.getValue(), OF_EX_Latch.rd.getValue(), OF_EX_Latch.Immediate1.getValue(),OF_EX_Latch.Immediate2.getValue(),OF_EX_Latch.opcode);
			EX_MA_Latch.rs1_for_store = OF_EX_Latch.rs1.getValue();
			
			if(OF_EX_Latch.opcode.equals("11000")) {
				containingProcessor.getRegisterFile().setProgramCounter(EX_MA_Latch.ALU_result);
				Simulator.getEventQueue().clear();
				containingProcessor.IF_EnableLatch.setIF_busy(false);
				containingProcessor.IF_OF_Latch.setOF_enable(false);
			}
			
			else if(OF_EX_Latch.opcode.equals("11001") || OF_EX_Latch.opcode.equals("11010") || OF_EX_Latch.opcode.equals("11011") || OF_EX_Latch.opcode.equals("11100")) {
				if(containingProcessor.change(OF_EX_Latch.rs1.getValue(), OF_EX_Latch.rs2.getValue(), OF_EX_Latch.rd.getValue(), OF_EX_Latch.Immediate1.getValue(),OF_EX_Latch.Immediate2.getValue(),OF_EX_Latch.opcode) == 1) {
					Simulator.no_extra_added_due_to_branch++;
					containingProcessor.getRegisterFile().setProgramCounter(EX_MA_Latch.ALU_result);
					Simulator.getEventQueue().clear();
					containingProcessor.IF_EnableLatch.setIF_busy(false);
					containingProcessor.IF_OF_Latch.setOF_enable(false);
				}
			}
			
			else if(EX_MA_Latch.flag == 0) {
				containingProcessor.IF_OF_Latch.setOF_enable(false);
			}
			OF_EX_Latch.setEX_busy(false);
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
		}
	}
}

