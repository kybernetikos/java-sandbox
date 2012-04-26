package com.kybernetikos.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditDistance {
	/**
	 * Works out the minimal add / remove operations you need to change the before list into the after list.
	 * @param before
	 * @param after
	 * @return
	 */
	public static <T> List<Action<T>> calculate(List<T> before, List<T> after) {
		List<Action<T>>[][] editMatrix = calculateMatrix(before, after);
		return editMatrix[editMatrix.length - 1][editMatrix[0].length - 1];
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Based on the calculation for the Levenshtein distance.
	 * @param before
	 * @param after
	 * @return
	 */
	private static <T> List<Action<T>>[][] calculateMatrix(List<T> before, List<T> after) {
		List<Action<T>>[][] matrix = new ArrayList[before.size() + 1][];
		
		ArrayList<Action<T>> actions = new ArrayList<Action<T>>();
		for (int i = 0; i < matrix.length; ++i) {
			matrix[i] = new ArrayList[after.size() + 1];
			matrix[i][0] = new ArrayList<Action<T>>(actions);
			if (i < before.size()) {
				actions.add(new Delete<T>(before.get(i), 0));
			}
		}
		actions.clear();
		for (int j = 0; j < matrix[0].length; ++j) {
			matrix[0][j] = new ArrayList<Action<T>>(actions);
			if (j < after.size()) {
				actions.add(new Insert<T>(after.get(j), j));
			}
		}
		for (int j=1; j < after.size() + 1; ++j) {
			for (int i = 1; i < matrix.length; ++i) {
				if (before.get(i - 1).equals(after.get(j - 1))) {
					matrix[i][j] = matrix[i-1][j-1];
				} else {
					List<Action<T>> deletion = matrix[i-1][j];
					List<Action<T>> insertion = matrix[i][j-1];
					List<Action<T>> both = matrix[i-1][j-1];
					
					List<Action<T>> best = null;
					if (deletion.size() > insertion.size() && both.size() + 2 > insertion.size()) {
						best = new ArrayList<Action<T>>(insertion);
						best.add(new Insert<T>(after.get(j - 1), j - 1));
					} else if (deletion.size() > both.size() + 2 && insertion.size() > both.size() + 2) {
						best = new ArrayList<Action<T>>(both);
						best.add(new Delete<T>(before.get(i - 1), j));
						best.add(new Insert<T>(after.get(j - 1), j - 1));
					} else {
						best = new ArrayList<Action<T>>(deletion);
						best.add(new Delete<T>(before.get(i - 1), j));
					}
					matrix[i][j] = best;
				}
			}
		}
		return matrix;
	}

	public static void main(String[] args) {
		List<String> before = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
		List<String> after = Arrays.asList("abcdefghijklmnopqstruvwyxz".split(""));
		
		List<Action<String>> changes = calculate(before, after);
		System.err.println(changes.size()+" operations");
		ArrayList<String> working = new ArrayList<String>(before);
		System.err.println(working);
		for (Action<String> change : changes) {
			System.err.println("\t"+change);
			change.apply(working);
			System.err.println(working);
		}
	}
}
