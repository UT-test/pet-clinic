package org.springframework.samples.petclinic.utility;


import static java.lang.Math.abs;

/**
 * Jeff Offutt
 * Java version Feb 2003
 * Classify triangles
 */
public class TriType {
	enum TryClass {

		IGNORE(0),
		SCALENE(1),
		ISO_SCALENE(2),
		EQUILATERAL(3),
		NOT_VALID(4);

		private final int value;

		TryClass(int i) {
			this.value = i;
		}

		public TryClass next() {
			return values()[ordinal() + 1];
		}
	}

	private static String instructions = "This is the ancient TriTyp program.";

	public TryClass classifyTriangle(int a, int b, int c) {
		int triangleCode = recognizeTriangleByCode(a, b, c);
		return TryClass.values()[triangleCode];
	}

	// ====================================
	// The main triangle classification method
	public int recognizeTriangleByCode(int Side1, int Side2, int Side3) {
		int triOut;
		// triOut is output from the routine:
		// Triang = 1 if triangle is scalene
		// Triang = 2 if triangle is isosceles
		// Triang = 3 if triangle is equilateral
		// Triang = 4 if not a triangle

		// After a quick confirmation that it’s a valid
		// triangle, detect any sides of equal length
		if (Side1 <= 0 || Side2 <= 0 || Side3 <= 0) {

			triOut = 4;

			return (triOut);
		}

		triOut = 0;

		if (Side1 == Side2)
			triOut = triOut + 1;

		if (Side1 == Side3)
			triOut = triOut + 2;

		if (Side2 == Side3)
			triOut = triOut + 3;

		// Confirm it’s a valid triangle before declaring
		// it to be scaleneif (Side1 + Side2 <= Side3 || Side2 + Side3 <= Side1 ||
		if (triOut == 0) {
			if (Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2)
				triOut = 4;
			else
				triOut = 1;
			return (triOut);
		}

		// Confirm it’s a valid triangle before declaring
		// it to be isosceles or equilateral
		if (triOut > 3)
			triOut = 3;
		else if (triOut == 1 && Side1 + Side2 > Side3)
			triOut = 2;
		else if (triOut == 2 && Side1 + Side3 > Side2)
			triOut = 2;
		else if (triOut == 3 && Side2 + Side3 > Side1)
			triOut = 2;
		else
			triOut = 4;
		return (triOut);
	}
}
