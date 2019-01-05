package processor;

import processor.memorysystem.MainMemory;
import processor.pipeline.Cache;
import processor.pipeline.EX_IF_LatchType;
import processor.pipeline.EX_MA_LatchType;
import processor.pipeline.Execute;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.IF_OF_LatchType;
import processor.pipeline.InstructionFetch;
import processor.pipeline.MA_RW_LatchType;
import processor.pipeline.MemoryAccess;
import processor.pipeline.OF_EX_LatchType;
import processor.pipeline.OperandFetch;
import processor.pipeline.RegisterFile;
import processor.pipeline.RegisterWrite;

public class Processor {
	
	RegisterFile registerFile;
	MainMemory mainMemory;
	
	public IF_EnableLatchType IF_EnableLatch;
	public IF_OF_LatchType IF_OF_Latch;
	public OF_EX_LatchType OF_EX_Latch;
	public EX_MA_LatchType EX_MA_Latch;
	public EX_IF_LatchType EX_IF_Latch;
	public MA_RW_LatchType MA_RW_Latch;
	public Cache L1i_Cache;
	public Cache L1d_Cache;
	
	InstructionFetch IFUnit;
	OperandFetch OFUnit;
	Execute EXUnit;
	MemoryAccess MAUnit;
	RegisterWrite RWUnit;
	
	public Processor()
	{
		registerFile = new RegisterFile();
		mainMemory = new MainMemory();
		
		IF_EnableLatch = new IF_EnableLatchType();
		IF_OF_Latch = new IF_OF_LatchType();
		OF_EX_Latch = new OF_EX_LatchType();
		EX_MA_Latch = new EX_MA_LatchType();
		EX_IF_Latch = new EX_IF_LatchType();
		MA_RW_Latch = new MA_RW_LatchType();
		
		L1i_Cache = new Cache(256,1,this);
		L1d_Cache = new Cache(1,1,this);
		System.out.println("asdasasdasdas");
		IFUnit = new InstructionFetch(this, IF_EnableLatch, IF_OF_Latch, EX_IF_Latch);
		OFUnit = new OperandFetch(this, IF_OF_Latch, OF_EX_Latch);
		EXUnit = new Execute(this, OF_EX_Latch, EX_MA_Latch, EX_IF_Latch);
		MAUnit = new MemoryAccess(this, EX_MA_Latch, MA_RW_Latch);
		RWUnit = new RegisterWrite(this, MA_RW_Latch, IF_EnableLatch);
	}
	
	public void printState(int memoryStartingAddress, int memoryEndingAddress)
	{
		System.out.println(registerFile.getContentsAsString());
		
		System.out.println(mainMemory.getContentsAsString(memoryStartingAddress, memoryEndingAddress));		
	}
	
	public Cache getInstructionCacheLevel1() {
		return this.L1i_Cache;
	}
	
	public Cache getDataCacheLevel1() {
		return this.L1d_Cache;
	}
	
	public RegisterFile getRegisterFile() {
		return registerFile;
	}

	public void setRegisterFile(RegisterFile registerFile) {
		this.registerFile = registerFile;
	}

	public MainMemory getMainMemory() {
		return mainMemory;
	}

	public void setMainMemory(MainMemory mainMemory) {
		this.mainMemory = mainMemory;
	}

	public InstructionFetch getIFUnit() {
		return IFUnit;
	}

	public OperandFetch getOFUnit() {
		return OFUnit;
	}

	public Execute getEXUnit() {
		return EXUnit;
	}

	public MemoryAccess getMAUnit() {
		return MAUnit;
	}

	public RegisterWrite getRWUnit() {
		return RWUnit;
	}
	
	public int controlUnit(int a){
		String z = new String("");
		z = String.format("%32s", Integer.toBinaryString(a)).replace(' ', '0');
		z = z.substring(0,5);
		if(z.equals("11101")){
			return (0);
		}
		if(z.equals("11000") || z.equals("11001") || z.equals("11010") || z.equals("11011") || z.equals("11100")) {
			return (1);
		}
		if(z.equals("10111")) {
			return (2);
		}
		if(z.equals("10110")) {
			return (3);
		}
		return (4);
	}
	
	public int ALU_Unit(int rs1,int rs2,int rd,int immediate,int immediate_2,String opcode) {
		String zx = new String("");
		zx = opcode;
		if(zx.equals("00000")){
			return rs1+rs2;
		}
		else if(zx.equals("00001")) {
			return rs1+immediate;
		}
		else if(zx.equals("00010")) {
			return rs1-rs2;
		}
		else if(zx.equals("00011")) {
			return rs1-immediate;
		}
		else if(zx.equals("00100")) {
			return rs1*rs2;
		}
		else if(zx.equals("00101")) {
			return rs1*immediate;
		}
		else if(zx.equals("00110")) {
			this.getRegisterFile().setValue(31, rs1%rs2);
			return rs1/rs2;
		}
		else if(zx.equals("00111")) {
			this.getRegisterFile().setValue(31, rs1%immediate);
			return rs1/immediate;
		}
		else if(zx.equals("01000")) {
			if(rs1==1 && rs2==1) {
				return 1;
			}
			else return 0;
		}
		else if(zx.equals("01001")) {
			if(rs1==1 && immediate==1) {
				return 1;
			}
			else return 0;
		}
		else if(zx.equals("01010")) {
			if(rs1==1 || rs2==1) {
				return (1);
			}
			else return (0);
		}
		else if(zx.equals("01011")) {
			if(rs1==1 || immediate==1) {
				return (1);
			}
			else return (0);
		}
		else if(zx.equals("01100")) {
			if(rs1 == rs2) {
				return (0);
			}
			else return (1);
		}
		else if(zx.equals("01101")) {
			if(rs1 == immediate) {
				return (0);
			}
			else return (1);
		}
		else if(zx.equals("01110")) {
			if(rs1<rs2) {
				return (1);
			}
			return (0);
		}
		else if(zx.equals("01111")) {
			if(rs1<immediate) {
				return (1);
			}
			else return (0);
		}
		else if(zx.equals("10000")) {
			String z = new String(Integer.toBinaryString(rs1));
			z = z.substring(rs2, z.length()+1);
			for(int i=0; i<rs2;i++) {
				z=z.concat("0");
			}
			return(Integer.parseInt(z));
		}
		else if(zx.equals("10001")) {
			String z = new String(Integer.toBinaryString(rs1));
			z = z.substring(immediate, z.length()+1);
			for(int i=0; i<immediate;i++) {
				z=z.concat("0");
			}
			return(Integer.parseInt(z));
		}
		else if(zx.equals("10010")) {
			String z = new String(Integer.toBinaryString(rs1));
			z = z.substring(0,z.length()-rs2);
			String q = new String("");
			for(int i=0;i<rs2;i++) {
				q=q.concat("0");
			}
			q=q.concat(z);
			return(Integer.parseInt(q));
		}
		else if(zx.equals("10011")) {
			String z = new String(Integer.toBinaryString(rs1));
			z = z.substring(0,z.length()-immediate);
			String q = new String("");
			for(int i=0;i<immediate;i++) {
				q=q.concat("0");
			}
			q=q.concat(z);
			return(Integer.parseInt(q));
		}
		else if(zx.equals("10100")) {
			String z = new String(Integer.toBinaryString(rs1));
			z = z.substring(0,z.length()-rs2-1);
			String q = new String("");
			for(int i=0;i<rs2;i++) {
				q=q.concat("0");
			}
			q=q.concat(z);
			return(Integer.parseInt(q));
		}
		else if(zx.equals("10101")) {
			String z = new String(Integer.toBinaryString(rs1));
			String x = z.substring(0,0);
			z = z.substring(0,z.length()-immediate-1);
			String q = new String("");
			for(int i=0;i<immediate;i++) {
				q=q.concat(x);
			}
			q=q.concat(z);
			return(Integer.parseInt(q));
		}
		else if(zx.equals("10110")) {
			return (rs1+immediate);
		}
		else if(zx.equals("10111")) {
			return (rs2+immediate);
		}
		else if(zx.equals("11000")) {
			return (this.getRegisterFile().getProgramCounter() - 1 + rs1 + immediate_2);
		}
		else if(zx.equals("11001")) {
			if(rs1 == rs2) {
				return (this.getRegisterFile().getProgramCounter() - 1 + immediate);
			}
			else return (this.getRegisterFile().getProgramCounter());
		}
		else if(zx.equals("11010")) {
			if(rs1 != rs2) {
				return (this.getRegisterFile().getProgramCounter() - 1 + immediate);
			}
			else return (this.getRegisterFile().getProgramCounter());
		}
		else if(zx.equals("11011")) {
			if(rs1<rs2) {
				return (this.getRegisterFile().getProgramCounter() - 1 + immediate);
			}
			else return (this.getRegisterFile().getProgramCounter());
		}
		else if(zx.equals("11100")) {
			if(rs1>rs2) {
				return(this.getRegisterFile().getProgramCounter() - 1 + immediate);
			}
			else return (this.getRegisterFile().getProgramCounter());
		}
			
		return 0;
		
	}
	
	public int change(int rs1,int rs2,int rd,int immediate,int immediate_2,String opcode) {
		String zx = new String("");
		zx = opcode;
		if(zx.equals("11001")) {
			if(rs1 == rs2) {
				return 1;
			}
			else return -1;
		}
		else if(zx.equals("11010")) {
			if(rs1 != rs2) {
				return 1;
			}
			else return -1;
		}
		else if(zx.equals("11011")) {
			if(rs1<rs2) {
				return 1;
			}
			else return -1;
		}
		else if(zx.equals("11100")) {
			if(rs1>rs2) {
				return 1;
			}
			else return -1;
		}
			
		return 0;
	}
	
	public int destination_address(int inst) {
		String zx = new String("");
		zx = String.format("%32s", Integer.toBinaryString(inst)).replace(' ', '0');
		int rs2_address = (int)Long.parseLong(zx.substring(10,15),2);
		int rd_address = (int)Long.parseLong(zx.substring(15,20),2);
		zx = zx.substring(0,5);
		if(zx.equals("00000")){
			return rd_address;
		}
		else if(zx.equals("00001")) {
			return rs2_address;
		}
		else if(zx.equals("00010")) {
			return rd_address;
		}
		else if(zx.equals("00011")) {
			return rs2_address;
		}
		else if(zx.equals("00100")) {
			return rd_address;
		}
		else if(zx.equals("00101")) {
			return rs2_address;
		}
		else if(zx.equals("00110")) {
			return rd_address;
		}
		else if(zx.equals("00111")) {
			return rs2_address;
		}
		else if(zx.equals("01000")) {
			return rd_address;
		}
		else if(zx.equals("01001")) {
			return rs2_address;
		}
		else if(zx.equals("01010")) {
			return rd_address;
		}
		else if(zx.equals("01011")) {
			return rs2_address;
		}
		else if(zx.equals("01100")) {
			return rd_address;
		}
		else if(zx.equals("01101")) {
			return rs2_address;
		}
		else if(zx.equals("01110")) {
			return rd_address;
		}
		else if(zx.equals("01111")) {
			return rs2_address;
		}
		else if(zx.equals("10000")) {
			return rd_address;
		}
		else if(zx.equals("10001")) {
			return rs2_address;
		}
		else if(zx.equals("10010")) {
			return rd_address;
		}
		else if(zx.equals("10011")) {
			return rs2_address;
		}
		else if(zx.equals("10100")) {
			return rd_address;
		}
		else if(zx.equals("10101")) {
			return rs2_address;
		}
		else if(zx.equals("10110")) {
			return rs2_address;
		}
		else return(-1);
	}
	
	public int is_immediate(int inst) {
		String zx = new String("");
		zx = String.format("%32s", Integer.toBinaryString(inst)).replace(' ', '0');
		int rs2_address = -1;
		int rd_address = 1;
		zx = zx.substring(0,5);
		if(zx.equals("00000")){
			return rd_address;
		}
		else if(zx.equals("00001")) {
			return rs2_address;
		}
		else if(zx.equals("00010")) {
			return rd_address;
		}
		else if(zx.equals("00011")) {
			return rs2_address;
		}
		else if(zx.equals("00100")) {
			return rd_address;
		}
		else if(zx.equals("00101")) {
			return rs2_address;
		}
		else if(zx.equals("00110")) {
			return rd_address;
		}
		else if(zx.equals("00111")) {
			return rs2_address;
		}
		else if(zx.equals("01000")) {
			return rd_address;
		}
		else if(zx.equals("01001")) {
			return rs2_address;
		}
		else if(zx.equals("01010")) {
			return rd_address;
		}
		else if(zx.equals("01011")) {
			return rs2_address;
		}
		else if(zx.equals("01100")) {
			return rd_address;
		}
		else if(zx.equals("01101")) {
			return rs2_address;
		}
		else if(zx.equals("01110")) {
			return rd_address;
		}
		else if(zx.equals("01111")) {
			return rs2_address;
		}
		else if(zx.equals("10000")) {
			return rd_address;
		}
		else if(zx.equals("10001")) {
			return rs2_address;
		}
		else if(zx.equals("10010")) {
			return rd_address;
		}
		else if(zx.equals("10011")) {
			return rs2_address;
		}
		else if(zx.equals("10100")) {
			return rd_address;
		}
		else if(zx.equals("10101")) {
			return rs2_address;
		}
		else if(zx.equals("10110")) {
			return rs2_address;
		}
		else return(0);
	}
	
}
