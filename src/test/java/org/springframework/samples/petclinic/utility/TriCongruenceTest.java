package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)

@ClauseDefinition(clause = 'a', def = "t1arr[0]")
@ClauseDefinition(clause = 'b', def = "t1arr[0] + t1arr[1] < t1arr[2]")

class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/**
	 For clause coverage we have or predicate of a and b clauses
	 As we are using clause coverage we should check all values for both clauses
	 which includes a = True and a = False
	 and b = True and b = False
	 We make two tests:
	 1) a = true and b = true
	 2) a = false and b = false
	 */

	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		}
	)
	@Test
	public void Line15ClauseCoverageTest1() {
		Triangle t1 = new Triangle(2, 2, 3);
		Triangle t2 = new Triangle(2, 2, 3);

		boolean areCongruent = TriCongruence.areCongruent(t1, t2);

		Assertions.assertTrue(areCongruent);
	}

	@ClauseCoverage(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true)
		}
	)
	@Test
	public void Line15ClauseCoverageTest2() {
		Triangle t1 = new Triangle(-1, 2, 3);
		Triangle t2 = new Triangle(-1, 2, 3);

		boolean areCongruent = TriCongruence.areCongruent(t1, t2);

		Assertions.assertFalse(areCongruent);
	}

	/**
	 For CACC, first we have to select a major clause
	 important point here is that {T F} is not feasible because of
	 the sort command in the method
	 we will choose second clause as major clause
	 we will make test where clauses' values will be:
	 {FT, FF}
	 */

	@CACC(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true)
		},
		majorClause = 'b',
		predicateValue = true
	)
	@Test
	public void Line15CACCTest1() {
		Triangle t1 = new Triangle(1, 1, 3);
		Triangle t2 = new Triangle(1, 1, 3);

		boolean areCongruent = TriCongruence.areCongruent(t1, t2);

		Assertions.assertFalse(areCongruent);
	}

	@CACC(
		predicate = "a + b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		majorClause = 'b',
		predicateValue = false
	)
	@Test
	public void Line15CACCTest2() {
		Triangle t1 = new Triangle(3, 3, 4);
		Triangle t2 = new Triangle(3, 3, 4);

		boolean areCongruent = TriCongruence.areCongruent(t1, t2);

		Assertions.assertTrue(areCongruent);
	}

	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
//		predicate = a predicate with any number of clauses
		return predicate;
	}
}
