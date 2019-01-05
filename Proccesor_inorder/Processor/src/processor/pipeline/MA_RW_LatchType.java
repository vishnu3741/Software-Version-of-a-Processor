package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable = false;
	boolean RW_busy = false;
	int ALU_Result = -1;
	int return_address;
	int flag;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	
	public boolean isRW_busy() {
		return RW_busy;
	}
	
	public void setRW_busy(boolean rW_busy) {
		RW_busy = rW_busy;
	}

}
