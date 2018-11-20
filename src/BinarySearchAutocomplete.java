import java.util.*;

/**
 * 
 * Using a sorted array of Term objects, this implementation uses binary search
 * to find the top term(s).
 * 
 * @author Austin Lu, adapted from Kevin Wayne
 * @author Jeff Forbes
 * @author Owen Astrachan in Fall 2018, revised API
 * @author Mylie Walker, implemented topMatches
 */
public class BinarySearchAutocomplete implements Autocompletor {

	Term[] myTerms;

	/**
	 * Given arrays of words and weights, initialize myTerms to a corresponding
	 * array of Terms sorted lexicographically.
	 * 
	 * This constructor is written for you, but you may make modifications to
	 * it.
	 * 
	 * @param terms a list of words to form terms from
	 * @param weights a corresponding list of weights, such that terms[i] has
	 *        weight[i].
	 * @return a BinarySearchAutocomplete whose myTerms object has myTerms[i] =
	 *         a Term with word terms[i] and weight weights[i].
	 * @throws a
	 *             NullPointerException if either argument passed in is null
	 */
	public BinarySearchAutocomplete(String[] terms, double[] weights) {
		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
		
		myTerms = new Term[terms.length];
		
		for (int i = 0; i < terms.length; i++) {
			myTerms[i] = new Term(terms[i], weights[i]);
		}
		
		Arrays.sort(myTerms);
	}

	/**
	 * Uses binary search to find the index of the first Term in the passed in
	 * array which is considered equivalent by a comparator to the given key.
	 * This method should not call comparator.compare() more than 1+log n times,
	 * where n is the size of a.
	 * 
	 * @param a the array of Terms being searched
	 * @param key the key being searched for.
	 * @param comparator a comparator, used to determine equivalency between the
	 *        values in a and the key.
	 * @return The first index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int firstIndexOf(Term[] a, Term key, Comparator<Term> comparator) {	
		int index = BinarySearchLibrary.firstIndex(Arrays.asList(a), key, comparator);
		return index;
	}

	/**
	 * The same as firstIndexOf, but instead finding the index of the last Term.
	 * 
	 * @param a the array of Terms being searched
	 * @param key the key being searched for.
	 * @param comparator a comparator, used to determine equivalency between the
	 *        values in a and the key.
	 * @return The last index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int lastIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
		int index = BinarySearchLibrary.lastIndex(Arrays.asList(a), key, comparator);
		return index;
	}

	/**
	 * Returns an array containing k words in myTerms with the largest weight which 
	 * match the given prefix, in descending weight order.
	 * 
	 * @param prefix a prefix which all returned words must start with
	 * @param k the (maximum) number of words to be returned
	 * @return An array of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k
	 *         such words exist, return an array containing all those words If
	 *         no such words exist, return an empty array
	 * @throws a
	 *             NullPointerException if prefix is null
	 */
	@Override
	public List<Term> topMatches(String prefix, int k) {	
		LinkedList<Term> ret = new LinkedList<>();
		if (k < 0) {
			throw new IllegalArgumentException("Illegal value of k:"+k);
		}
		
		Comparator<Term> c = new Term.PrefixOrder(prefix.length());
		if (BinarySearchLibrary.firstIndex(Arrays.asList(myTerms), new Term(prefix, 0), c) < 0 ||
				BinarySearchLibrary.lastIndex(Arrays.asList(myTerms), new Term(prefix, 0), c) < 0) {
			return ret;
		}
		PriorityQueue<Term> pq = new PriorityQueue<Term>(10, new Term.WeightOrder());
		for (int i = BinarySearchLibrary.firstIndex(Arrays.asList(myTerms), new Term(prefix, 0), c); 
				i <= BinarySearchLibrary.lastIndex(Arrays.asList(myTerms), new Term(prefix, 0), c); i++) {
			Term t = myTerms[i];
			ret.add(t);
		}
		Collections.sort(ret, new Term.ReverseWeightOrder());
		System.out.println(ret);
		int numResults = Math.min(k, ret.size());
		return ret.subList(0, numResults);
	}
}
