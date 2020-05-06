package at.jku.swtesting;

import java.util.Random;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

public class RingBufferModel implements FsmModel {

	protected int INIT_CAPACITY = 3;
	protected int RANDOM_CAPACITY = new Random().nextInt(40 - 1 + 1) + 1;
	protected int SMALL_CAPACITY = 1;
	protected int NEGATIVE_CAPACITY = -1;
	
	protected int size;
	protected int capacity;

	@Override
	public Object getState() {
		if (size == 0) return "EMPTY"; 
		else if (size == capacity) return "FULL";
		else if ((size > 0) && (size < capacity)) return "FILLED";
		else return "ERROR_UNEXPECTED_MODEL_STATE";
	}

	@Override
	public void reset(boolean testing) { 
		capacity = INIT_CAPACITY; 
		RANDOM_CAPACITY = new Random().nextInt(40 - 1 + 1) + 1;
		size = 0;
	}
	
	@Action
	public void init() { 
		capacity = INIT_CAPACITY; 
		size = 0;
	}
	
	@Action
	public void setCapacity() { capacity = RANDOM_CAPACITY; }
	public boolean setCapacityGuard() { return RANDOM_CAPACITY >= size; }
	
	// Size can only be increased when in empty or filled state, otherwise when called in full state do nothing
	@Action
	public void enqueue() { 
		if(size < capacity){
			size++;
		}
	}

	@Action
	public void dequeue() { size--; }
	public boolean dequeueGuard() { return size > 0; }
	
	@Action
	public void peek() { System.out.println("No change of any variables."); }
	public boolean peekGuard() { return size > 0; }

	@Action
	public void capacity() { System.out.println("No change of any variables."); }
	
	@Action
	public void size() { System.out.println("No change of any variables."); }
	
	@Action
	public void isEmpty() { System.out.println("No change of any variables."); }
	
	@Action
	public void isFull() { System.out.println("No change of any variables."); }
	
	// Additional actions for negative scenarios
	@Action
	public void newRingBufferWithNegativeCapacity() { 
		System.out.println("Exception because constructor parameter 'capacity' is lower than 1!");
	}
	
	@Action
	public void setCapacityLowerThanSize() { 
		System.out.println("Exception because the new capacity is lower than the size of the ring buffer!");
	}
	public boolean setCapacityLowerThanSizeGuard() { return SMALL_CAPACITY < size; }
	
	@Action
	public void dequeueFromEmptyRingBuffer() { System.out.println("Exception because of dequeueing from empty ring buffer!"); }
	public boolean dequeueFromEmptyRingBufferGuard() { return size == 0; }
	
	@Action
	public void peekOfEmptyRingBuffer() { System.out.println("Exception because the ring buffer is empty and therefore has no peak!"); }
	public boolean peekOfEmptyRingBufferGuard() { return size == 0; }

	public void main(String [] args){
		RingBufferModelTest test = new RingBufferModelTest();
		test.testRingBufferModel();
	}


}
