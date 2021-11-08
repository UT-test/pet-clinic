package org.springframework.samples.petclinic.utility;

import java.util.Arrays;

public class TriCongruence {

	public static boolean areCongruent(Triangle t1, Triangle t2){
		double[] t1arr = {t1.a, t1.b, t1.c};
		double[] t2arr = {t2.a, t2.b, t2.c};

		Arrays.sort(t1arr);
		Arrays.sort(t2arr);

		if (t1arr[0] != t2arr[0] || t1arr[1] != t2arr[1] || t1arr[2] != t2arr[2]) return false;
		else if (t1arr[0] < 0 || t1arr[0] + t1arr[1] < t1arr[2]) return false;
		else return true;
	}
}
