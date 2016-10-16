package Algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class GeneticAlgorithmMath {

	static HashMap<Integer, Double[]> population = new HashMap<>();
	static HashMap<Integer, Double> top2 = new HashMap<>();
	static int populationSize = 5;
	static int arraySize = 4;
	static int topParent = 2; // get top two parents
	static int childrenRepo = 3; // number of children to reproduce
	// static JSONObject pAndC = new JSONObject();
	static JSONObject solution = new JSONObject();

	// static JSONObject fitnessValues = new JSONObject();
	// 1. 5,2,4,8
	//
	public static void InitialPop(int target) {
		for (int i = 1; i <= populationSize; i++) {
			Double[] randomArray = new Double[arraySize];
			for (int j = 0; j < randomArray.length; j++) {
				randomArray[j] = Math.random() * target;
			}
			population.put(i, randomArray);
		}
	}

	// calculate fitness
	public static JSONObject calculateFitness(int target) {
		JSONObject fitnessValues = new JSONObject();
		HashMap<Integer, Double> fit = new HashMap<>();
		for (Integer i : population.keySet()) {
			Double[] pop = population.get(i);
			double value = calc(pop);
			double fitLevel = Math.abs(target - value);
			fit.put(i, fitLevel);
		}

		fitnessValues.put("fitnessValue",fit);
		// for(Integer i : population.keySet()){
		// double fitLevel = fit.get(i);
		// double adder = 0;
		// for(int j = 1; j < population.size(); j++){
		// if(j != i){
		// adder += fit.get(j);
		// }
		// }
		// fitLevel -= adder;
		// fit.put(i, fitLevel);
		// }
		// Stream<Map.Entry<Integer, Double>> sort =
		// fit.entrySet().stream().sorted(Map.Entry.comparingByValue());
		top2 = (HashMap<Integer, Double>) fit.entrySet().stream().sorted(HashMap.Entry.comparingByValue())
				.limit(topParent).collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue));
		top2.entrySet().stream().sorted(HashMap.Entry.comparingByValue()).forEachOrdered(System.out::println);
		return fitnessValues;
		// for(Integer key : ((HashMap<Integer,Double>) sorted).keySet()){
		// String k = key.toString();
		// String value = ((HashMap<Integer,Double>)
		// sorted).get(key).toString();
		// System.out.println(k+" "+value);
		// }
		// System.out.println("fit");
		// fit.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(System.out::println);
	}

	@SuppressWarnings("unchecked")
	public static JSONObject getBreedandCombine(int target) {
		JSONObject rst = new JSONObject();
		JSONObject pAndC = new JSONObject();
		JSONObject fitnessValues = calculateFitness(target);
		HashMap<Integer, Double[]> children = new HashMap<>();
		int parent1 = 0;
		int parent2 = 0;
		Double[] parent1Array = new Double[arraySize];
		Double[] parent2Array = new Double[arraySize];

		// breed here
		int NoOfOffsprings = 1;
		while (NoOfOffsprings <= childrenRepo) {
			for (Integer key : top2.keySet()) {
				if (parent1 == 0) {
					parent1 = key;
				} else {
					parent2 = key;
				}
			}
			parent1Array = population.get(parent1);
			parent2Array = population.get(parent2);

			for (Integer key : population.keySet()) {
				if (key != parent1 && key != parent2) {
					int pickParent = (Math.random() > .5) ? parent1 : parent2;
					Double[] firstParent = population.get(pickParent);
					Double[] secondParent = population.get(key);
					Double[] child = new Double[arraySize];
					child[0] = secondParent[0];
					child[1] = (Math.random() > .5) ? (Math.random()) : secondParent[1];
					child[2] = (Math.random() > .5) ? (Math.random()) : firstParent[2] - (Math.random());
					child[3] = (Math.random() > .5) ? (Math.random()) : firstParent[3] - (Math.random());
					pAndC.put("parent1", Arrays.toString(firstParent));
					pAndC.put("parent2", Arrays.toString(firstParent));
					pAndC.put("child", Arrays.toString(child));
					children.put(key, child);
					NoOfOffsprings++;
				}
			}
		}
		population.clear();
		population = (HashMap<Integer, Double[]>) children.clone();
		population.put(parent1, parent1Array);
		population.put(parent2, parent2Array);
		rst.put("parents", pAndC);
		rst.put("fitnessValues", fitnessValues);
		if(evaluate(target)) rst.put("solution", solution);
		return rst;

	}

	private static double calc(Double[] arr) {
		double sumValue = arr[0] + (2 * arr[1]) + (3 * arr[2]) + (4 * arr[3]);
		return sumValue;
	}

	public static boolean evaluate(int target) {
		for (Integer key : population.keySet()) {
			Double[] arrayValue = population.get(key);
			double sumValue = arrayValue[0] + (2 * arrayValue[1]) + (3 * arrayValue[2]) + (4 * arrayValue[3]);
			if ((int) (sumValue) == target) {
				solution.put("Solved", "a + 2b + 3c + 4d = " + target);
				solution.put("Solution", "1 * " + arrayValue[0] + " + 2 * " + arrayValue[1] + " + 3 * " + arrayValue[2]
						+ " + 4 * " + arrayValue[3]);
				solution.put("Calculated", arrayValue[0] + " + " + (2 * arrayValue[1]) + " + " + (3 * arrayValue[2])
						+ " + " + (4 * arrayValue[3]) + " = " + sumValue);
				return true;
			} else if ((int) (sumValue) < target) {
				InitialPop(target);
			} else {
				// jsonObject.put("sumValue", sumValue);
				// System.out.println(sumValue);
			}
		}
		return false;
	}
}
