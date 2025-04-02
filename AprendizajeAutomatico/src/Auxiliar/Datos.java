package Auxiliar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import Bayes.Bayes;
import KMedios.KMedios;
import Lloyd.Lloyd;

public class Datos {
	ArrayList<Matriz> casos = new ArrayList<Matriz>();
	ArrayList<Matriz> casosLloyd = new ArrayList<Matriz>();
	Map<String,ArrayList<Matriz>> casosBayes= new HashMap<String,ArrayList<Matriz>>();
	ArrayList<Double> centrosIniciales = new ArrayList<>(Arrays.asList(4.6, 3.0, 4.0, 0.0));
	ArrayList<Double> centrosIniciales2 = new ArrayList<>(Arrays.asList(6.8, 3.4, 4.6, 0.7));
	ArrayList<String> nombreClase=new ArrayList<>(Arrays.asList("Iris-setosa","Iris-versicolor"));
	Bayes bay;
	KMedios km;
	Lloyd ly;
	public void leerArchivo() {
        String archivo = "Iris2Clases.txt";
        
        // Intentar leer el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            
            // Leer cada línea del archivo
            while ((linea = br.readLine()) != null) {
            	Matriz aux=new Matriz(1,4);
            	Matriz aux2=new Matriz(1,4);
                // Dividir la línea en valores separados por comas
                String[] valoresString = linea.split(",");
                double[] valores = new double[valoresString.length - 1]; // Excluyendo la última columna
                
                for (int i = 0; i < valores.length; i++) {
                    aux.setElem(0, i, Double.parseDouble(valoresString[i]));
                    aux2.setElem(0, i, Double.parseDouble(valoresString[i]));
                }
                if( casosBayes.containsKey(valoresString[valoresString.length-1])) {
                	casosBayes.get(valoresString[valoresString.length-1]).add(aux);
                }else {
                	ArrayList<Matriz> list = new ArrayList<Matriz>();
                	list.add(aux);
                	casosBayes.put(valoresString[valoresString.length-1], list);
                }
               
                // Agregar los valores a la lista
                casos.add(aux);
                casosLloyd.add(aux2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int check=3;
        check++;
    }
	public void Bayes() {// devuelve los valores medios de las clases
		Bayes bayes = new Bayes();
		int i=0;
		System.out.println("Bayes");
        for(Map.Entry<String, ArrayList<Matriz>> entry: casosBayes.entrySet()){
            bayes.calcularMedia(entry.getValue());
            bayes.calcularCovarianzas(entry.getValue());
            System.out.println("la clase "+ entry.getKey()+" cuya media es: \n "+ bayes.getMediasClases().get(i));
            i++;
        }
        this.bay=bayes;
	}
	public void Kmedios() {
	    KMedios kmedios = new KMedios();
	    Matriz centroInicial = new Matriz(1, 4);
	    System.out.println("Kmedios");

	    for (int i = 0; i < centroInicial.getColumnas(); i++) {
	        centroInicial.setElem(0, i, centrosIniciales.get(i));
	    }
	    kmedios.inicializacionMedias(centroInicial);

	    Matriz centroInicial2 = new Matriz(1, 4);
	    for (int i = 0; i < centroInicial2.getColumnas(); i++) {
	        centroInicial2.setElem(0, i, centrosIniciales2.get(i));
	    }
	    kmedios.inicializacionMedias(centroInicial2);

	    // Agregar casos al KMedios
	    for (Matriz m : casos) {
	        kmedios.agregarCaso(m);
	    }

	    // Calcular los centros
	    kmedios.aprendizaje();

	    // Imprimir los centros finales
	    for (int i = 0; i < kmedios.getMediasClase().size(); i++) {
	        System.out.println("La clase " + nombreClase.get(i) + " cuya media es:");
	        System.out.println(kmedios.getMediasClase().get(i));
	    }
	    this.km=kmedios;
	}
	public void Lloyd() {// devuelve los valores medios de las clases
		Lloyd lloyd = new Lloyd();
		Matriz centroInicial= new Matriz(1,4);
		System.out.println("Lloyd");
		for(int i =0;i<centroInicial.getColumnas();i++) {
			centroInicial.setElem(0, i, centrosIniciales.get(i));
		}
		lloyd.inicializacionMedias(centroInicial);
		Matriz centroInicial2= new Matriz(1,4);
		for(int i =0;i<centroInicial.getColumnas();i++) {
			centroInicial2.setElem(0, i, centrosIniciales2.get(i));
		}
		lloyd.inicializacionMedias(centroInicial2);
		for(Matriz m: casosLloyd) {
			lloyd.actualizarClase(lloyd.perteneceClase(m), m);
		}
		for(int i=0;i<lloyd.getMediasClases().size();i++){
			System.out.println("La clase "+ nombreClase.get(i) + " cuya media es: \n " +lloyd.getMediasClases().get(i));
        }
		this.ly=lloyd;
	}
	public void tests(Matriz m) {
		System.out.print("la matriz "+m);
		System.out.println("segun Bayes pertenece a "+ nombreClase.get(bay.perteneceClase(m)));
		System.out.println("segun Kmedios pertenece a "+ nombreClase.get(km.perteneceClase(m)));
		System.out.println("segun Lloyd pertenece a "+ nombreClase.get(ly.perteneceClase(m)));
		System.out.println();
	}
}
