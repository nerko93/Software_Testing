package at.jku.swtesting;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("groupE")
@DisplayName("Group E - Assignment 1 - RingerBufferTests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RingBufferTest {

    private ArrayList<String> bufferList;
    private RingBuffer<String> buffer;
    private static final  String ITEM1 = "item1";
    private static final String ITEM2 = "item2";
    private static final String ITEM3 = "item3";

    @BeforeEach
    void init() {
        buffer = new RingBuffer<String>(3);
        bufferList = new ArrayList<String>();
        bufferList.add(ITEM1);
        bufferList.add(ITEM2);
        buffer.enqueue(bufferList.get(0));
        buffer.enqueue(bufferList.get(1));
    }

    @AfterAll
    public void teardown() {
        buffer = null;
        bufferList = null;
    }

    @Test
    @Disabled("Not yet implemented")
    public void test() {
        fail("Not yet implemented");  //TODO: Implement tests
    }

    @Test
    public void testCapacity() {
        assertEquals(3, buffer.capacity());
    }

    @Test
    public void testCapacityForNegativeCapacity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RingBuffer<>(-1);
        });
    }

    @Test
    public void testCapacityForZeroCapacity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new RingBuffer<>(0);
        });
    }

    @Test
    public void testSize(){
        assertEquals(2, buffer.size());
    }

    @Test
    public void testFull(){
        buffer.enqueue(ITEM3);
        assertTrue(buffer.isFull());
    }

    @Test
    public void testEmpty(){
        buffer.dequeue();
        buffer.dequeue();
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testEnqueue() {
        assertAll(
                () -> assertEquals(buffer.size(), 2),
                () -> assertEquals(buffer.capacity(), 3),
                () -> assertFalse(buffer.isEmpty()),
                () -> assertFalse(buffer.isFull())
        );
    }

    @Test
    public void testEnqueueOverfilling() {
        buffer.enqueue(ITEM3);
        buffer.enqueue((ITEM3));

        assertAll(
                () -> assertEquals(buffer.size(), 3),
                () -> assertEquals(buffer.capacity(), 3),
                () -> assertFalse(buffer.isEmpty()),
                () -> assertTrue(buffer.isFull())
        );
    }

    @Test
    public void testDequeue() {
        assertAll(
                () -> assertEquals(buffer.size(), 2),
                () -> assertEquals(ITEM1, buffer.dequeue(), "dequeueing not the first added item!"),
                () -> assertEquals(buffer.size(), 1)
        );
    }

    @Test
    public void testDequeueEmptyBuffer() {
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        RuntimeException exception = assertThrows(RuntimeException.class, ringBuffer::dequeue);
        assertTrue(exception.getMessage().contains("Empty ring buffer."));
    }

    @Test
    public void testEnqueueDequeueMulitple() {
        assertEquals(2, buffer.size());
        assertEquals(3, buffer.capacity());
        buffer.dequeue();
        buffer.dequeue();
        assertTrue(buffer.isEmpty());
        buffer.enqueue(ITEM1);
        buffer.enqueue(ITEM2);
        buffer.enqueue(ITEM3);
        buffer.enqueue(ITEM1);
        assertTrue(buffer.isFull());
        buffer.dequeue();
        buffer.dequeue();
        assertEquals(1, buffer.size());
        buffer.enqueue(ITEM2);
        assertEquals(2, buffer.size());
    }

    @Test
    public void testPeekItem(){
        assertEquals(ITEM1, buffer.peek());
    }

    @Test
    public void testPeekEmptyBuffer() {
        RingBuffer<String> ringBuffer = new RingBuffer<>(6);
        RuntimeException exception = assertThrows(RuntimeException.class, ringBuffer::peek);
        assertTrue(exception.getMessage().contains("Empty ring buffer."));
    }

    @Test
    public void testPeekBufferNotChanged(){
        buffer.peek();
        assertEquals(2, buffer.size());
    }

    @Test
    public void testSetCapacity() {
        buffer.setCapacity(3);
        assertAll(
                () -> assertFalse(buffer.isFull()),
                () -> assertEquals(buffer.size(), 2),
                () -> assertEquals(buffer.capacity(), 3),
                () -> assertEquals(ITEM1, buffer.dequeue()),
                () -> assertEquals(ITEM2, buffer.dequeue()),
                () -> assertTrue(buffer.isEmpty())
        );
    }

    @Test
    public void testSetCapacityGreaterThanBefore(){
        buffer.setCapacity(5);
        assertEquals(5, buffer.capacity());
    }

    @Test
    public void testSetCapacitySmallerThanBefore(){
        buffer.setCapacity(2);
        assertEquals(2, buffer.capacity());
    }

    @Test
    public void testSetCapacitySameSize() {
        assertEquals(buffer.size(), 2);
        buffer.setCapacity(7);
        assertEquals(buffer.size(), 2);
    }

    @Test
    public void testSetCapacityZero(){
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.setCapacity(0));
    }

    @Test
    public void TestSetCapacityGreaterButSizeUnchanged(){
        int tempSize = buffer.size();
        buffer.setCapacity(5);
        assertEquals(tempSize, buffer.size());
    }

    @Test
    public void testSetCapacitySmallerThanBufferSize(){
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.setCapacity(1));
    }

    @Test
    public void testSetCapacityIsNotFullAnymore() {
        buffer.enqueue(ITEM3);
        assertTrue(buffer.isFull());
        buffer.setCapacity(4);
        assertFalse(buffer.isFull());
    }

    @Test
    public void testSetCapacityIsStillEmpty() {
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);
        assertTrue(ringBuffer.isEmpty());
        ringBuffer.setCapacity(3);
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    public void testSetCapacityUnchangedCompareValues(){
        buffer.setCapacity(3);
        ArrayList<String> resultList = new ArrayList<>();
        int initialSize = buffer.size();
        for(int i = 0; i < initialSize; i++){
            resultList.add(buffer.dequeue());
        }
        assertArrayEquals(bufferList.toArray(), resultList.toArray());
    }

    @Test
    public void testSetCapacityGreaterThanCurrentSizeCompareValues() {
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
    public void testSetCapacityStillSameItemsInRightOrder() {
        buffer.enqueue(ITEM3);
        assertAll(
                () -> assertEquals(buffer.size(), 3),
                () -> assertEquals(buffer.capacity(), 3),
                () -> assertTrue(buffer.isFull())
        );

        buffer.setCapacity(4);
        assertAll(
                () -> assertEquals(buffer.size(), 3),
                () -> assertEquals(buffer.capacity(), 4),
                () -> assertFalse(buffer.isFull()),
                () -> assertEquals(ITEM1, buffer.dequeue()),
                () -> assertEquals(ITEM2, buffer.dequeue()),
                () -> assertEquals(ITEM3, buffer.dequeue()),
                () -> assertTrue(buffer.isEmpty())
        );
    }

}
