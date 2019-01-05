package generic;

import java.io.DataInputStream;
import generic.Statistics;
import java.io.FileInputStream;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	public static EventQueue eventQueue;
	public static int instruction_count=0;
	public static int no_cycles=0;
	public static int no_times_stalled=0;
	public static int no_extra_added_due_to_branch=0;
	
	public static EventQueue getEventQueue ( )
	{
		return eventQueue ;
	}

	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		eventQueue = new EventQueue();
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);
			InputStream is = null;
			DataInputStream dis = null;
			int j=0;
			try{
				is = new FileInputStream(assemblyProgramFile);
				dis = new DataInputStream(is);
				int k=0;
				while(dis.available()>0){
					if(k==0){
						processor.getRegisterFile().setProgramCounter(dis.readInt());
						k=1;
					}
					else{
						processor.getMainMemory().setWord(j,dis.readInt());
						j++;
					}
				}
				System.out.println(processor.getMainMemory().getContentsAsString(0, 30));
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{	
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Simulator.no_cycles++;
			Clock.incrementClock();
		}
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
		if(simulationComplete = true) {
			Statistics.setNumberOfInstructions(instruction_count);
			Statistics.setNumberOfCycles(no_cycles);
		}
	}
}
