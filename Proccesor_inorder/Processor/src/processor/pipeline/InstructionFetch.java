package processor.pipeline;

import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import generic.Element;
import generic.Event;

public class InstructionFetch implements Element{
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{	
		if(IF_EnableLatch.isIF_busy()) {
			return;
		}
		Simulator.getEventQueue().addEvent(new MemoryReadEvent( (Clock.getCurrentTime() + containingProcessor.getInstructionCacheLevel1().getLatency()),  this, containingProcessor.getInstructionCacheLevel1(), containingProcessor.getRegisterFile().getProgramCounter()));
		IF_EnableLatch.setIF_busy(true);	
	}
	/*public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{	
			Simulator.instruction_count++;
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			IF_OF_Latch.setInstruction(newInstruction);
			String s = new String("");
			s = String.format("%32s", Integer.toBinaryString(IF_OF_Latch.instruction)).replace(' ', '0');
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			if(s.substring(0, 5).equals("11101")) {
				IF_EnableLatch.setIF_enable(false);
			}
			IF_OF_Latch.setOF_enable(true);
		}
	}*/
	@Override
	public void handleEvent(Event e) {
		if(IF_OF_Latch.isOF_busy()) {
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			IF_OF_Latch.setInstruction(event.getValue());
			containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() + 1);
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);
		}
	}
}
