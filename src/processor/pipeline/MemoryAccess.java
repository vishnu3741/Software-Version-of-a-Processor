package processor.pipeline;

import generic.Element;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import generic.Event;
import processor.Clock;
import processor.Processor;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA() {
		if(EX_MA_Latch.isMA_enable()) {
			if(EX_MA_Latch.isMA_busy()) {
				return;
			}
			if(EX_MA_Latch.flag == 2 || EX_MA_Latch.flag ==3) {
				if(EX_MA_Latch.flag == 3) {
					Simulator.getEventQueue().addEvent(new MemoryReadEvent( (Clock.getCurrentTime() + containingProcessor.getDataCacheLevel1().getLatency()), this, containingProcessor.getDataCacheLevel1(), EX_MA_Latch.ALU_result));
					EX_MA_Latch.setMA_busy(true);	
				}
				else {
					Simulator.getEventQueue().addEvent(new MemoryWriteEvent( (Clock.getCurrentTime() + containingProcessor.getDataCacheLevel1().getLatency()), this, containingProcessor.getDataCacheLevel1(), EX_MA_Latch.ALU_result, EX_MA_Latch.rs1_for_store));
					EX_MA_Latch.setMA_busy(true);
				}
			}
			else if(EX_MA_Latch.flag == 0) {
				MA_RW_Latch.ALU_Result = EX_MA_Latch.ALU_result;
				MA_RW_Latch.return_address = EX_MA_Latch.return_address;
				MA_RW_Latch.flag = EX_MA_Latch.flag;
				containingProcessor.OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
			else {
				MA_RW_Latch.ALU_Result = EX_MA_Latch.ALU_result;
				MA_RW_Latch.return_address = EX_MA_Latch.return_address;
				MA_RW_Latch.flag = EX_MA_Latch.flag;
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
		}
	}
	
	@Override
	public void handleEvent(Event e)
	{
		if(MA_RW_Latch.isRW_busy()) {
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {
			if(e.getEventType() == Event.EventType.MemoryResponse) {
				MemoryResponseEvent event = (MemoryResponseEvent) e;
				MA_RW_Latch.ALU_Result = event.getValue();
				MA_RW_Latch.flag = 4;
				MA_RW_Latch.return_address = EX_MA_Latch.return_address;
				EX_MA_Latch.setMA_busy(false);
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
			else {
				MemoryWriteEvent event = (MemoryWriteEvent) e;
				if(event.getValue() == -10 && event.getAddressToWriteTo() == -10) {
					EX_MA_Latch.setMA_busy(false);
					EX_MA_Latch.setMA_enable(false);
					MA_RW_Latch.setRW_enable(true);
				}
			}
		}
	}

}