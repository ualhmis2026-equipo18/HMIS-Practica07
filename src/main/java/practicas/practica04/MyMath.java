package practicas.practica04;

import java.util.ArrayList;

public class MyMath{
	public static double getCosineDistance(ArrayList<Double> arr1, ArrayList<Double> arr2) {
		if (arr1 == null || arr2 == null) return 1.0;
		if (arr1.size() != arr2.size()) return 1.0;

		double productoEscalar = 0.0;
		double norma1 = 0.0;
		double norma2 = 0.0;

		for (int i = 0; i < arr1.size(); i++) {
			double u = arr1.get(i);
			double v = arr2.get(i);

			productoEscalar += u * v;
			norma1 += u * u;
			norma2 += v * v;
		}

		if (norma1 == 0 || norma2 == 0) return -1.0;

		double similitud = productoEscalar / (Math.sqrt(norma1) * Math.sqrt(norma2));

		return 1.0 - similitud;
	}
} 
