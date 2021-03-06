/** Generated by YAKINDU Statechart Tools code generator. */
package hu.bme.mit.yakindu.analysis.example;

import hu.bme.mit.yakindu.analysis.IStatemachine;
import hu.bme.mit.yakindu.analysis.ITimerCallback;

public interface IExampleStatemachine extends ITimerCallback,IStatemachine {
	public interface SCInterface {
	
		public void raiseStart();
		
		public void raiseFeher();
		
		public void raiseFekete();
		
		public long getFeherIdo();
		
		public void setFeherIdo(long value);
		
		public long getFeketeIdo();
		
		public void setFeketeIdo(long value);
		
	}
	
	public SCInterface getSCInterface();
	
}
