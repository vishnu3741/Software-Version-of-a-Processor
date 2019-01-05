package processor.pipeline;

public class EX_IF_LatchType {
	
	int ALU_result;
	boolean EX_IF_enable = true;
	
	public boolean is_EX_IF_enable() {
		return EX_IF_enable;
	}

	public void set_EX_IF_enable(boolean ex_if_enable) {
		EX_IF_enable = ex_if_enable;
	}
}
