
public class arrayQueue<T>
{
    Object[] tempArray = new Object[10];
    T[] arr;
    int front;
    int back;

    // Constructor
    public arrayQueue() {
        arr = (T[]) tempArray;
        front = 0;
        back = 0;
    }

    @SuppressWarnings("unchecked")
    // function to grow array
    protected void grow_array() throws Exception {
        T[] new_arr = (T[]) new Object[arr.length * 2];

        for (int i = 0; !isEmpty(); i++) {
            new_arr[i] = dequeue();    // might throw an exception
        }
        front = 0;
        back = arr.length-1;
        arr = new_arr;
    }

    // function to check if array is empty
    public boolean isEmpty() {
        if (front == back) {
            return true;
        }
        return false;
    }

    // function to add item to queue
    public void enqueue(T item) throws Exception {
        // Check for Overflow..
        if ((back + 1) % arr.length == front) {
            grow_array();
        }
        arr[back++] = item;
        back = back% arr.length;
    }

    // function to remove item from queue
    public T dequeue () throws Exception {
        if (isEmpty()) {
            throw new Exception("Queue Underflow!");
        }
        T temp = arr[front];
        front = (front + 1) % arr.length;
        return temp;
    }

    // toString function
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = front; i <= back; i++) {
            result.append(arr[i]).append(" ");
        }
        return result.toString();
    }


}
