package Launcher;

import java.util.ArrayList;

import Auxiliar.Datos;
import Auxiliar.Matriz;

public class main {

	public static void main(String[] args) {
	  Datos datos= new Datos();
	  datos.leerArchivo();
	  datos.Bayes();
	  datos.Kmedios();
	  datos.Lloyd();
	  ArrayList<double[][]> test= new ArrayList<double[][]>();
      double[][] testIris1 = {// Iris setosa
          {5.1, 3.5, 1.4, 0.2}
      };

      double[][] testIris2 = {// Iris versicolor
          {6.9, 3.1, 4.9, 1.5}
      };

      double[][] testIris3 = { //Iris setosa
          {5.0, 3.4, 1.5, 0.2}
      };

        // Agregar las matrices al ArrayList
      test.add(testIris1);
      test.add(testIris2);
      test.add(testIris3);
	  for(int i=0; i<test.size();i++) {
		  Matriz prueba= new Matriz(test.get(i));
		  datos.tests(prueba);
	  }
	}

}
