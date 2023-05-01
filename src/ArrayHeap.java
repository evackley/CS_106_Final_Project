
    import java.util.ArrayList;

    /**@author Eva Ackley
     * This program creates a priority queue using a Heap data structure that utilizes an ArrayList
     * @param <E> This class is written with generics so it can be implemented with various data types
     */
    public class ArrayHeap<E extends Comparable<E>> implements PriorityQueue<E> {

        private ArrayList<E> heap;

        /**
         * A Constructor method for an empty ArrayHeap
         */
        public ArrayHeap() {
            heap = new ArrayList<E>();
        }

        /**
         * Inserts an element into the ArrayHeap, maintaining heap properties
         * @param element The element to be inserted into the heap
         */
        public void insert(E element) {
            if (isEmpty()) {
                heap.add(0, element); //Adds the first element as the root of the heap, no need to bubble up
            }
            else {
                int position = this.size(); //Will be inserted at the end of the array
                heap.add(position, element);
                while (element.compareTo(heap.get(parent(position))) > 0) { //While the newly inserted element is greater than its parent
                    bubbleUp(position); //Switches positions with its parent
                    position = parent(position); //The new position is the previous position of the parent
                }
            }
        }

        /**
         * An access method for the maximum element in the MaxHeap
         * @return The element which is at the top of the heap, null if the heap is empty
         */
        public E max() {
            if (isEmpty()) {
                return null; //There are no elements in the heap
            }
            else {
                return this.heap.get(0); //This is the element at the top of the heap
            }
        }

        /**
         * Puts the elements of an array into a heap, updates the order of the array to match
         * @param array The array which will be made into a max heap
         */
        private void buildMaxHeap(ArrayList<E> array) {
            for (E element : array) {
                this.insert(element); //Put each element of the array into a heap
            }
            array = heap; //Updates the order of the array to the heap order
        }

        /**
         * Sorts an ArrayList using a MaxHeap and heapSort
         * @param array The array of elements that will be sorted
         * @return An ArrayList of sorted elements
         */
        public ArrayList<E> sort(ArrayList<E> array) {
            buildMaxHeap(array); //Uses the buildMaxHeap method to make this array a max heap
            ArrayList<E> sorted = new ArrayList<E>();
            int size = this.size();
            for (int i = 0; i < size; i++) {
                sorted.add(this.removeMax()); //Removes the max element of the heap each to time to get the elements in sorted order
            }
            return sorted;
        }

        /**
         * Removes the max element from the heap, while maintaining heap structure
         * @return The element which was previously the maximum element of the heap
         */
        public E removeMax() {
            if (isEmpty()) {
                return null; //There is nothing to remove
            }
            E max = this.max(); //Need a reference to the max element
            int index = this.size() - 1;
            E tempMax = heap.get(index);
            heap.set(0, tempMax); //Replace the max element with the element at the end of the array
            heap.remove(index); //Remove the reference to the element at the end of the array
            index = 0; //The index of the element which is temporarily the max is now 0
            while(leftChild(index) < this.size()) { //Checks that children are not off the end of array
                if (rightChild(index) < this.size()) { //These prevent array index out of bound errors
                    E left = heap.get(leftChild(index));
                    E right = heap.get(rightChild(index));
                    if (tempMax.compareTo(left) > 0 && tempMax.compareTo(right) > 0) {
                        break; //The element is greater than both of its children
                    }
                    else if (left.compareTo(right) > 0) { //left child is greater than right child
                        swap(index, leftChild(index)); //Swap the element with its left child
                        index = leftChild(index); //Update the index, so this process can be repeated
                    }
                    else { //right child is greater than left child
                        swap(index, rightChild(index)); //Swap the element with its right child
                        index = rightChild(index); //Update the index
                    }
                }
                else { //The element only has a left child
                    E left = heap.get(leftChild(index));
                    if (tempMax.compareTo(left) > 0) {
                        break; //The element is greater than its left child
                    }
                    else { //The element is less than its left child
                        swap(index, leftChild(index)); //Swap the element with its left child
                        index = leftChild(index);
                    }
                }
            }
            return max;
        }

        /**
         * An access method for the size of the heap, using the ArrayList method size
         * @return The number of elements in the array
         */
        public int size() {
            return this.heap.size();
        }

        /**
         * Checks if the heap is empty
         * @return True if there are no elements in the array, false if else
         */
        public boolean isEmpty() {
            return this.size() == 0;
        }

        /**
         * Swaps the positions in the heap of two elements
         * @param i The index of the first element to be swapped
         * @param j The index of the second element to be swapped
         */
        private void swap(int i, int j) {
            E temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        /**
         * Calculates the index of the parent of a given index
         * @param i The index of the child
         * @return The index of the parent
         */
        private int parent(int i) {
            if (i == 0) { //It is the max of the heap
                return 0;
            }
            else {
                return i - ((i / 2) + 1); //This uses the relationship between parent and child to find parent index
            }
        }

        /**
         * Calculates the index of the left child of a given index, may be off the end of the array
         * @param i The index of the parent
         * @return The index of the left child of the given index
         */
        private int leftChild(int i) {
            return 2 * i + 1; //Relationship between parent and left child
        }

        /**
         * Calculates the index of the right child of a given index, may be off the end of the array
         * @param i The index of the parent
         * @return The index of the right child of the given index
         */
        private int rightChild(int i) {
            return 2 * i + 2; //Relationship between parent and right child
        }

        /**
         * To maintain heap structure, swaps the element at a given index with its parent
         * @param i The index to be swapped with its parent
         */
        private void bubbleUp(int i) {
            int parent = parent(i);
            swap(i, parent);
        }

        /**
         * A method to calculate the log base two of a given number
         * @param result The number we wish to calculate the log of
         * @return An integer approximation of the log2 of a number
         */
        public static int log2(int result) {
            return (int) (Math.log(result) / Math.log(2)); //Uses logarithm principles
        }

        /**
         * A toString method for a heap which presents it as a binary tree
         * @return A String method for a heap which has 1 element, then 2 elements, 4, ... per row
         */
        public String toString() {
            String results = "";
            int numRows = log2(this.size()) + 1; //There will be this many rows based on the number of elements
            int count = 0; //Which element should be added to the string
            for (int i = 0; i < numRows; i++) { //Keeps track of which row
                for (int j = 0; j < Math.pow(2, i); j++) { //Will have as many elements as 2^i (row we are on)
                    if (count < this.size()) {
                        results += heap.get(count).toString() + " "; //Adds the desired element to the String
                        count++;
                    }
                    else {
                        break; //Do not want an array index out of bounds
                    }
                }
                results += "\n"; //Add a new row
            }
            return results;
        }
    }

