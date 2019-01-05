package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable()) {
			MA_RW_Latch.setRW_busy(true);
			if(MA_RW_Latch.flag == 0) {
				containingProcessor.EX_MA_Latch.setMA_enable(false);
				System.out.println("Number of cycles = "+Simulator.no_cycles);
				Simulator.setSimulationComplete(true);
			}
			else {
				if(MA_RW_Latch.flag == 4) {
					containingProcessor.getRegisterFile().setValue(MA_RW_Latch.return_address, MA_RW_Latch.ALU_Result);
				}
			}
		}
		MA_RW_Latch.setRW_busy(false);
		MA_RW_Latch.setRW_enable(false);
	}
}


