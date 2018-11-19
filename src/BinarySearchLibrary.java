import java.util.*;

public class BinarySearchLibrary {
	
	public static <T>
	    int firstIndexSlow(List<T> list, 
	    		           T target, Comparator<T> comp) {
		int index = Collections.binarySearch(list, target,comp);
		
		if (index < 0) return index;
		
		while (0 <= index && comp.compare(list.get(index),target) == 0) {
			index -= 1;
		}
		return index+1;
	}
	
	/**
	 * Uses binary search to find the index of the first object in parameter
	 * list -- the first object o such that comp.compare(o,target) == 0.
	 * 
	 * This method should not call comparator.compare() more than 1+log n times
	 * @param list is the list of objects being searched
	 * @param target is the object being searched for
	 * @param comp is how comparisons are made
	 * @return index i such that comp.compare(list.get(i),target) == 0
	 * and there is no index < i such that this is true. Return -1
	 * if there is no such object in list.
	 */
	
	public static <T> int firstIndex(List<T> list, T target, Comparator<T> comp) {
		int low = -1;
		int mid;
		int high = list.size()-1;
		if (list.size() == 0) return -1;
		while (low+1 != high) {
			if (comp.compare(list.get(low+1), target) == 0) return low+1;
			mid = (low + high)/2;
			if (list.subList(low+1, mid+1).contains(target)) {
				high = mid;
			}
			else {
				low = mid;
			}
		}
		if (comp.compare(list.get(low+1), target) == 0) return low+1;
		return -1;
	}
	/**
	 * Uses binary search to find the index of the last object in parameter
	 * list -- the first object o such that comp.compare(o,target) == 0.
	 * 
	 * This method should not call comparator.compare() more than 1+log n times
	 * @param list is the list of objects being searched
	 * @param target is the object being searched for
	 * @param comp is how comparisons are made
	 * @return index i such that comp.compare(list.get(i),target) == 0
	 * and there is no index > i such that this is true. Return -1
	 * if there is no such object in list.
	 */
	public static <T> int lastIndex(List<T> list, T target, Comparator<T> comp) {	
		int low = 0;
		int mid;
		int high = list.size();
		if (list.size() == 0) return -1;
		while (low != high) {
			if (comp.compare(list.get(high-1), target) == 0) return high-1;
			mid = (low + high)/2;
			if (list.subList(mid, high).contains(target)) {
				low = mid;
			}
			else {
				high = mid;
			}
		}
		if (comp.compare(list.get(high-1), target) == 0) return high-1;
		return -1;
	}
	
}
