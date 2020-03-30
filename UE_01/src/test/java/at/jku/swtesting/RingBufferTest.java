package at.jku.swtesting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
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

	@BeforeEach

	public void  setUp() throws Exception {
		buffer = new RingBuffer<String>(3);
		buffer.enqueue(ITEM1);
		buffer.enqueue(ITEM2);
	}

	@AfterEach
	public void teardown() throws Exception {
		buffer = null;
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

}
