package at.jku.swtesting;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Assertions;
import nz.ac.waikato.modeljunit.Action;

public class RingBufferModelWithAdapter extends RingBufferModel {

	protected RingBuffer<String> ringBuffer = new RingBuffer<>(INIT_CAPACITY);

	@Override
	public void reset(boolean testing) {
		super.reset(testing);
		ringBuffer = new RingBuffer<>(capacity);
	}

	@Override
	@Action
	public void init() {
		super.init();
		ringBuffer = new RingBuffer<>(capacity);
		assertEquals(capacity, ringBuffer.capacity());
		assertEquals(size, ringBuffer.size());
	}
	
	@Override
	@Action
	public void setCapacity() {
		super.setCapacity();
		ringBuffer.setCapacity(capacity);
		assertEquals(capacity, ringBuffer.capacity());
	}

	@Override
	@Action
	public void enqueue() {
		super.enqueue();
		ringBuffer.enqueue("test");
		assertEquals(size , ringBuffer.size());
	}

	@Override
	@Action
	public void dequeue() {
		super.dequeue();
		ringBuffer.dequeue();
		assertEquals(size, ringBuffer.size());
	}

	@Override
	@Action
	public void peek() {
		super.peek();
		int rbOld = ringBuffer.size();
		ringBuffer.peek();
		assertEquals(rbOld, ringBuffer.size());
		assertEquals(size, ringBuffer.size());
	}

	@Override
	@Action
	public void capacity() { assertEquals(capacity, ringBuffer.capacity()); }
	
	@Override
	@Action
	public void size() { assertEquals(size, ringBuffer.size()); }
	
	@Override
	@Action
	public void isEmpty() { assertEquals(size == 0, ringBuffer.isEmpty()); }
	
	@Override
	@Action
	public void isFull() { assertEquals(size == capacity, ringBuffer.isFull()); }

	@Override
	@Action
	public void newRingBufferWithNegativeCapacity() {
		super.newRingBufferWithNegativeCapacity();
		try {
			ringBuffer = new RingBuffer<>(NEGATIVE_CAPACITY);
		} catch (Exception e) {
			assertEquals("Initial capacity is less than one", e.getMessage());
		}
		Assertions.assertThrows(IllegalArgumentException.class, () -> ringBuffer = new RingBuffer<>(NEGATIVE_CAPACITY));
	}

	@Override
	@Action
	public void setCapacityLowerThanSize() {
		super.setCapacityLowerThanSize();
		try {
			ringBuffer.setCapacity(SMALL_CAPACITY);
		} catch (Exception e) {
			assertEquals("New Capacity is lower than the current buffer size.", e.getMessage());
		};
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> ringBuffer.setCapacity(SMALL_CAPACITY));
	}

	@Override
	@Action
	public void dequeueFromEmptyRingBuffer() {
		super.dequeueFromEmptyRingBuffer();
		try {
			ringBuffer.dequeue();
		} catch (Exception e) {
			assertEquals("Empty ring buffer.", e.getMessage());
		}
		Assertions.assertThrows(RuntimeException.class, () -> ringBuffer.dequeue());
	}

	@Override
	@Action
	public void peekOfEmptyRingBuffer() {
		super.peekOfEmptyRingBuffer();
		try {
			ringBuffer.peek();
		} catch (Exception e) {
			assertEquals("Empty ring buffer.", e.getMessage());
		}
		Assertions.assertThrows(RuntimeException.class, () -> ringBuffer.peek());
	}


	public void main(String [] args){
		RingBufferModelWithAdapterTest test = new RingBufferModelWithAdapterTest();
		test.testRingBufferModelWithAdapter();
	}

}
