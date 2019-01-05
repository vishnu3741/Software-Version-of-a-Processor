package processor.pipeline;

public class EX_MA_LatchType {
	
	int ALU_result;
	int rs1_for_store;
	int flag;
	int return_address;
	boolean MA_enable = false;
	boolean MA_busy = false;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	
	public void setMA_busy(boolean mA_busy) {
		MA_busy = mA_busy;
	}
	
	public boolean isMA_busy() {
		return MA_busy;
	}

}
