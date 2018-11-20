
/*************************************************************************
 * @author Mylie Walker
 *
 * Description: A term and its weight.
 * 
 *************************************************************************/
import java.util.Comparator;
public class Term implements Comparable<Term> {
	private final String myWord;
	private final double myWeight;
	/**
	 * The constructor for the Term class. Should set the values of word and
	 * weight to the inputs, and throw the exceptions listed below
	 * 
	 * @param word the word this term consists of
	 * @param weight the weight of this word in the Autocomplete algorithm
	 * @throws NullPointerException
	 *             if word is null
	 * @throws IllegalArgumentException
	 *             if weight is negative
	 */
	public Term(String word, double weight) {
		if (word == null) {
			throw new NullPointerException("Word is null");
		}		
		else myWord = word;
		if (weight < 0) {
			throw new IllegalArgumentException("Negative weight "+ weight);
		}
		else myWeight = weight;
	}
	
	/**
	 * The default sorting of Terms is lexicographical ordering.
	 */
	public int compareTo(Term that) {
		return myWord.compareTo(that.myWord);
	}
	/**
	 * Getter methods, use these in other classes which use Term
	 */
	public String getWord() {
		return myWord;
	}
	public double getWeight() {
		return myWeight;
	}
	public String toString() {
		return String.format("(%2.1f,%s)", myWeight, myWord);
	}
	
	@Override
	public boolean equals(Object o) {
		Term other = (Term) o;
		return this.compareTo(other) == 0;
	}
	/**
	 * A Comparator for comparing Terms using a set number of the letters they
	 * start with.
	 */
	public static class PrefixOrder implements Comparator<Term> {
		private final int myPrefixSize;
		/**
		 * 
		 * @param r the number of letters used to compare the terms
		 */
		public PrefixOrder(int r) {
			this.myPrefixSize = r;
		}
		/**
		 * Compares v and w lexicographically using only their first r letters.
		 * 
		 * @param v/w two Terms whose words are being compared
		 * @return a negative number if v is less than w, a positive number if 
		 * 		   v is greater than w, or 0 if v and w are considered equal
		 */
		public int compare(Term v, Term w) {
			if (myPrefixSize == 0) return 0;
			for (int i = 0; i < Math.min(myPrefixSize, Math.min(v.getWord().length(), w.getWord().length())); i++) {
				if(v.getWord().charAt(i) != w.getWord().charAt(i)) {
					return v.getWord().charAt(i) - w.getWord().charAt(i);
				}
			}
			if (v.getWord().length() < myPrefixSize || w.getWord().length() < myPrefixSize) {
				return v.getWord().length() - w.getWord().length();
			}
			return 0;
		}
	
	}
	/**
	 * A Comparator for comparing Terms using only their weights, in descending
	 * order.
	 */
	public static class ReverseWeightOrder implements Comparator<Term> {		
		@Override
		/**
		 * 
		 * @param v/w the terms whose weights are being compared
		 * @return a positive number if w's weight is greater than v's, a negative
		 * 		   number if v's weight is greater than w's, or 0 if the terms have
		 * 		   the same weights
		 */
		public int compare(Term v, Term w) {
			if (v.getWeight() < w.getWeight()) {
				return 1;
			}
			if (v.getWeight() > w.getWeight()) {
				return -1;
			}
			return 0;
		}
	}
	/**
	 * A Comparator for comparing Terms using only their weights, in ascending
	 * order.
	 */
	public static class WeightOrder implements Comparator<Term> {
		@Override
		/**
		 * 
		 * @param v/w the terms whose weights are being compared
		 * @return a positive number if v's weight is greater than w's, a negative
		 * 		   number if w's weight is greater than v's, or 0 if the terms have
		 * 		   the same weights
		 */
		public int compare(Term v, Term w) {
			if (v.getWeight() < w.getWeight()) {
				return -1;
			}
			if (v.getWeight() > w.getWeight()) {
				return 1;
			}
			return 0;
		}
	}
}
