package processor.pipeline;

public class CacheLine {
	int data;
	int tag;
	
	public CacheLine(int data_initial,int tag_initial) {
		this.data = data_initial;
		this.tag = tag_initial;
	}
	
	public void write(int value_write, int tag_write) {
		this.data = value_write;
		this.tag = tag_write;
	}
	
	public int read() {
		return data;
	}
}
