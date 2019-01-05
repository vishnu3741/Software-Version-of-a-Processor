package processor.pipeline;
import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import generic.Event.EventType;
import processor.Clock;
import processor.Processor;

public class Cache implements Element{

	CacheLine [] cacheLines;
	int latency;
	Processor p;
	int address_to_replace;
	Element requesting_Type;
	
	public Cache(int no_lines,int latency_value,Processor p_set) {
		this.cacheLines = new CacheLine [no_lines];
		for(int i=0; i<this.cacheLines.length;i++) {
			this.cacheLines[i] = new CacheLine(-1,-1);
		}
		this.latency = latency_value;
		this.p = p_set;
		return;
	}
	
	public int cacheRead(int address) {
		int index = address%this.cacheLines.length;
		int tag = address/this.cacheLines.length;
		if(this.cacheLines[index].tag == tag) {
			return this.cacheLines[index].data;
		}
		return -1;
	}

	public int getLatency() {
		return this.latency;
	}
	
	@Override
	public void handleEvent(Event e) {
		if(e.getEventType() == EventType.MemoryRead) {
			MemoryReadEvent event = (MemoryReadEvent) e;
			if(cacheRead(event.getAddressToReadFrom()) == -1) {
				this.requesting_Type = event.getRequestingElement();
				this.address_to_replace = event.getAddressToReadFrom();
				Simulator.getEventQueue().addEvent( new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency,this,this.p.getMainMemory(),event.getAddressToReadFrom()));
			}
			else {
				Simulator.getEventQueue().addEvent( new MemoryResponseEvent(Clock.getCurrentTime(),this,event.getRequestingElement(),cacheRead(event.getAddressToReadFrom())));
			}
		}
		else if(e.getEventType() == EventType.MemoryResponse) {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			int index = this.address_to_replace%this.cacheLines.length;
			int tag = this.address_to_replace/this.cacheLines.length;
			this.cacheLines[index].tag = tag;
			this.cacheLines[index].data = event.getValue();
			Simulator.getEventQueue().addEvent( new MemoryResponseEvent(Clock.getCurrentTime(),this,this.requesting_Type,event.getValue()));
		}
		else if(e.getEventType() == EventType.MemoryWrite) {
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			int index = event.getAddressToWriteTo()%this.cacheLines.length;
			int tag = event.getAddressToWriteTo()/this.cacheLines.length;
			this.cacheLines[index].tag = tag;
			this.cacheLines[index].data = event.getValue();
			Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency,event.getRequestingElement(),p.getMainMemory(),event.getAddressToWriteTo(),event.getValue()));
		}
	}
	
}
