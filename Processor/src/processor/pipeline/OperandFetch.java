package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class OperandFetch {
	
	Processor containingProcessor;
	static int j=0;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{	
			IF_OF_Latch.setOF_busy(true);
			if(OF_EX_Latch.isEX_busy()) {
				return;
			}
			containingProcessor.controlUnit(IF_OF_Latch.instruction);
			String s = new String("");
			s = String.format("%32s", Integer.toBinaryString(IF_OF_Latch.instruction)).replace(' ', '0');
			int v1 = containingProcessor.getRegisterFile().getValue((int)Long.parseLong(s.substring(5,10),2));
			int v2 = containingProcessor.getRegisterFile().getValue((int)Long.parseLong(s.substring(10,15),2));
			int v3 = containingProcessor.getRegisterFile().getValue((int)Long.parseLong(s.substring(15,20),2));
			int return_address = containingProcessor.destination_address(IF_OF_Latch.instruction);
			int flag = containingProcessor.controlUnit(IF_OF_Latch.instruction);
			int v4;
			int v5;
			if(s.substring(15,16).equals("1")) {
				v4 = -(131072-Integer.parseInt(s.substring(15,32),2));
			}
			else v4 = Integer.parseInt(s.substring(15,32),2);
			
			if(s.substring(10,11).equals("1")) {
				v5 = -(4194304-Integer.parseInt(s.substring(10,32),2));
			}
			else v5 = Integer.parseInt(s.substring(10,32),2);
			
			 if(s.substring(0,5).equals("10111")) {
				if(containingProcessor.OF_EX_Latch.isEX_enable()) {
					if(containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(10,15),2) || containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.EX_MA_Latch.isMA_enable()) {
					if(containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(10,15),2) || containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.MA_RW_Latch.isRW_enable()) {
					if(containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(10,15),2) || containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
			}
			
			else if(s.substring(0,5).equals("10110")) {
				if(containingProcessor.OF_EX_Latch.isEX_enable()) {
					if(containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.EX_MA_Latch.isMA_enable()) {
					if(containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}

				if(containingProcessor.MA_RW_Latch.isRW_enable()) {
					if(containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
			}
				
			/*else if(s.substring(0,5).equals("11000")) {
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.ALU_Unit(v1, v2, v3, v4, v5, s.substring(0,5)));
				containingProcessor.IF_OF_Latch.setOF_enable(false);
			}*/
			else if(s.substring(0,5).equals("11001") || s.substring(0,5).equals("11010") || s.substring(0,5).equals("11011") || s.substring(0,5).equals("11100")) {
				if(containingProcessor.OF_EX_Latch.isEX_enable()) {
					if((containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(5,10),2)) || (containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(10,15),2)) ) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.EX_MA_Latch.isMA_enable()) {
					if((containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) || (containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(10,15),2))) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.MA_RW_Latch.isRW_enable()) {
					if((containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) || (containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(10,15),2))) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				/*if(containingProcessor.change(v1, v2, v3, v4, v5, s.substring(0,5))==1) {
					int a = containingProcessor.ALU_Unit(v1, v2, v3, v4, v5, s.substring(0,5));
					containingProcessor.getRegisterFile().setProgramCounter(a);
				}*/
			}
			
			else if(containingProcessor.is_immediate(IF_OF_Latch.instruction) == 1) {
				if(containingProcessor.OF_EX_Latch.isEX_enable()) {
					if((containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(5,10),2)) || (containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(10,15),2)) ) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.EX_MA_Latch.isMA_enable()) {
					if((containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) || (containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(10,15),2))) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.MA_RW_Latch.isRW_enable()) {
					if((containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) || (containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(10,15),2))) {
						Simulator.no_times_stalled++;
						return;
					}
				}
			}
			
			else if(containingProcessor.is_immediate(IF_OF_Latch.instruction) == -1) {
				if(containingProcessor.OF_EX_Latch.isEX_enable()) {
					if(containingProcessor.OF_EX_Latch.rd_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.EX_MA_Latch.isMA_enable()) {
					if(containingProcessor.EX_MA_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
				if(containingProcessor.MA_RW_Latch.isRW_enable()) {
					if(containingProcessor.MA_RW_Latch.return_address == (int)Long.parseLong(s.substring(5,10),2)) {
						Simulator.no_times_stalled++;
						return;
					}
				}
			}
			
			OF_EX_Latch.opcode = s.substring(0,5);
			OF_EX_Latch.get_Operand_Values(v1, v2, v3, v4, v5, return_address, flag);
			if(OF_EX_Latch.opcode.equals("11101")) {
				containingProcessor.IF_EnableLatch.setIF_enable(false);
			}
			IF_OF_Latch.setOF_busy(false);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
