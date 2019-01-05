package generic;

import java.io.*;
import java.io.FileInputStream;
import generic.Operand.OperandType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	static int firstCodeAddress;
	static DataOutputStream dos;
	public static void setupSimulation(String assemblyProgramFile, String objectProgramFile)
	{	
		firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	static char flip(char c){
		return (c == '0') ? '1' : '0';
	}
	static String twosComplement(String bin){
		String twos = "", ones = "";
		for (int i = 0; i < bin.length(); i++){
			ones += flip(bin.charAt(i));
			}
		int number0 = Integer.parseInt(ones, 2);
		StringBuilder builder = new StringBuilder(ones);
		boolean b = false;
		for (int i = ones.length() - 1; i > 0; i--){
			if (ones.charAt(i) == '1'){
				builder.setCharAt(i, '0');
			}
			else{
				builder.setCharAt(i, '1');
				b = true;
				break;
			}
		}
		if (!b)	builder.append("1", 0, 7);
		twos = builder.toString();
		return twos;
	}
	public static void assemble(String objectProgramFile)
	{	
		try{
			FileOutputStream fos = new FileOutputStream(objectProgramFile);
			dos = new DataOutputStream(fos);
			int z = ParsedProgram.symtab.get("main");
			dos.writeInt(z);
			for (int i = 0; i < ParsedProgram.data.size(); i++) {
				dos.writeInt(ParsedProgram.data.get(i));
			}
		}
		catch (IOException e) {
			System.out.println("Error in creating File");
		}
		for(int j=0; j<ParsedProgram.code.size(); j++){
			switch(ParsedProgram.code.get(j).operationType)
			{
				case add :{
						String s = new String("00000");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case sub :{
						String s = new String("00010");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case mul :{
						String s = new String("00100");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					} 
				case div :{
						String s = new String("00110");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case and :{
						String s = new String("01000");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case or :{
						String s = new String("01010");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case xor :{
						String s = new String("01100");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					} 
				case slt :{
						String s = new String("01110");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case sll :{
						String s = new String("10000");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case srl :{
						String s = new String("10010");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case sra :{
						String s = new String("10100");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%12s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case addi :{
						String s = new String("00001");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case subi :{
						String s = new String("00011");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case muli :{
						String s = new String("00101");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case divi :{
						String s = new String("00111");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case andi :{
						String s = new String("01001");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case ori :{
						String s = new String("01011");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case xori :{
						String s = new String("01101");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case slti :{
						String s = new String("01111");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case slli :{
						String s = new String("10001");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case srli :{
						String s = new String("10111");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case srai :{
						String s = new String("10101");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }						
						break;
					}
				case load :{						
						String s = new String("10110");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;					
					}
				case store :{						
						String s = new String("10111");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case beq :{						
						String s = new String("11001");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						if(ParsedProgram.code.get(j).destinationOperand.labelValue == null){
							a = "";
							a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
							s = s.concat(a);
						}
						else{
							int add = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
							int temp = ParsedProgram.code.get(j).getProgramCounter();
							temp = add-temp;
							if(temp<0){
								temp=-temp;
								String q = new String("");
								q = q.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								q = twosComplement(q);
								s = s.concat(q);
								}
							else{
								a = "";
								a = a.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								s = s.concat(a);
								}
						}
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					} 
				case bne :{						
						String s = new String("11010");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						if(ParsedProgram.code.get(j).destinationOperand.labelValue == null){
							a = "";
							a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
							s = s.concat(a);
						}
						else{
							int add = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
							int temp = ParsedProgram.code.get(j).getProgramCounter();
							temp = add-temp;
							if(temp<0){
								temp=-temp;
								String q = new String("");
								q = q.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								q = twosComplement(q);
								s = s.concat(q);
								}
							else{
								a = "";
								a = a.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								s = s.concat(a);
							}
						}
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case blt :{						
						String s = new String("11011");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						if(ParsedProgram.code.get(j).destinationOperand.labelValue == null){
							a = "";
							a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
							s = s.concat(a);
						}
						else{
							int add = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
							int temp = ParsedProgram.code.get(j).getProgramCounter();
							temp = add-temp;
							if(temp<0){
								temp=-temp;
								String q = new String("");
								q = q.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								q = twosComplement(q);
								s = s.concat(q);
								}
							else{
								a = "";
								a = a.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								s = s.concat(a);
							}
						}
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					} 
				case bgt :{						
						String s = new String("11100");
						String a = new String("");
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand1.value)).replace(' ', '0');
						s = s.concat(a);
						a = "";
						a = a.format("%5s", Integer.toBinaryString(ParsedProgram.code.get(j).sourceOperand2.value)).replace(' ', '0');
						s = s.concat(a);
						if(ParsedProgram.code.get(j).destinationOperand.labelValue == null){
							a = "";
							a = a.format("%17s", Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
							s = s.concat(a);
						}
						else{
							int add = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
							int temp = ParsedProgram.code.get(j).getProgramCounter();
							temp = add-temp;
							if(temp<0){
								temp=-temp;
								String q = new String("");
								q = q.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								q = twosComplement(q);
								s = s.concat(q);
								}
							else{
								a = "";
								a = a.format("%17s", Integer.toBinaryString(temp)).replace(' ', '0');
								s = s.concat(a);
							}
						}
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				case jmp :{
						String s = new String("11000");
						if(ParsedProgram.code.get(j).destinationOperand.labelValue == null){
							String q = new String("");		
							q = q.format("%22s",Integer.toBinaryString(ParsedProgram.code.get(j).destinationOperand.value)).replace(' ', '0');
							s = s.concat(q);
							s = s.concat("00000");
						}
						else{	
							s=s.concat("00000");
							int add = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
							int temp = ParsedProgram.code.get(j).getProgramCounter();
							temp = add-temp;
							if(temp<0){
								temp=-temp;
								String q = new String("");
								q = q.format("%22s", Integer.toBinaryString(temp)).replace(' ', '0');
								q = twosComplement(q);
								s = s.concat(q);
								}
							else{
								String q = new String("");
								q = "";
								q = q.format("%22s", Integer.toBinaryString(temp)).replace(' ', '0');
								s = s.concat(q);
							}
						}
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}	
				case end :{
						String s = new String("11101");
						String a = new String("");
						a = a.format("%27s", Integer.toBinaryString(0)).replace(' ', '0');
						s = s.concat(a);
						int v = (int)Long.parseLong(s,2);
						try { dos.writeInt(v); }
						catch (IOException e) { System.out.println("Error in creating File"); }
						break;
					}
				default: Misc.printErrorAndExit("unknown instruction!!");
			}
		}
	}
	
}
