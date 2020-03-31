package at.jku.swtesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

@TestInstance(Lifecycle.PER_CLASS)
public class RingBufferTest{

	private RingBuffer<String> buffer;
	private static final  String ITEM1 = "item1";
	private static final String ITEM2 = "item2";
	private static final String ITEM3 = "item3";
	private ArrayList<String> bufferList;

	@BeforeEach

	public void  setUp() throws Exception {
		buffer = new RingBuffer<String>(3);
		bufferList = new ArrayList<String>();
		bufferList.add(ITEM1);
		bufferList.add(ITEM2);
		buffer.enqueue(bufferList.get(0));
		buffer.enqueue(bufferList.get(1));
	}

	@AfterAll
	public void teardown() throws Exception {
		buffer = null;
		bufferList = null;
	} 

	@Test
	public void TestCapacity() {
		assertEquals(3, buffer.capacity());
	}

	@Test
	public void TestSize (){
		assertEquals(2, buffer.size());
	}

	@Test
	public void TestFull(){
		buffer.enqueue(ITEM3);
		assertTrue(buffer.isFull()); 
	}

	@Test
	public void TestEmpty(){
		buffer.dequeue();
		buffer.dequeue();
		assertTrue(buffer.isEmpty()); 
	}

	@Test
	public void TestPeekItem(){
		assertTrue(ITEM1.equals(buffer.peek())); 
	}

	@Test
	public void TestPeekBufferNotChanged(){
		buffer.peek();
		assertEquals(2, buffer.size()); 
	}

	@Test
	public void TestSetCapacityGreaterThanBefore(){
		buffer.setCapacity(5);
		assertEquals(5, buffer.capacity());
	}

	@Test
	public void TestSetCapacitySmallerThanBefore(){
		buffer.setCapacity(2);
		assertEquals(2, buffer.capacity());
	}

	@Test 

	public void TestSetCapacityZero(){
		assertThrows(IllegalArgumentException.class, () -> buffer.setCapacity(0));
	}

	@Test
	public void TestSetCapacityUnchanged(){
		buffer.setCapacity(3);
		assertEquals(3,buffer.capacity());
	}

	@Test
	public void TestSetCapacityGreaterButSizeUnchanged(){
		int tempSize = buffer.size();
		buffer.setCapacity(5);
		assertEquals(tempSize, buffer.size());
	}

	@Test
	public void TestSetCapacitySmallerThanBufferSize(){
		assertThrows(IndexOutOfBoundsException.class, () -> buffer.setCapacity(1));
	}

	@Test
	public void TestSetCapacityUnchangedCompareSize(){
		int tempBuffer = buffer.size();
		buffer.setCapacity(3);
		assertEquals(tempBuffer, buffer.size());
	}

	@Test
	public void TestSetCapacityGreaterThanCurrentSizeComapareValues(){
		bufferList.add(ITEM3);
		buffer.enqueue(bufferList.get(2));
		
		int newCapacity = 5;
		buffer.setCapacity(newCapacity);
		ArrayList<String> resultList = new ArrayList<String>();
		
		int initialSize = buffer.size();
		
		for(int i = 0; i < initialSize; i++){
			resultList.add(buffer.dequeue());
		}

		assertArrayEquals(bufferList.toArray(), resultList.toArray());
	}

	@Test
	public void TestSetCapacityUnchangedCompareValues(){
		buffer.setCapacity(3);
		ArrayList<String> resultList = new ArrayList<String>();
		int initialSize = buffer.size();
		for(int i = 0; i < initialSize; i++){
			resultList.add(buffer.dequeue());
		}
		assertArrayEquals(bufferList.toArray(), resultList.toArray());
	}
	

}
