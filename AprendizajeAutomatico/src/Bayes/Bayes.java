package Bayes;

import java.util.ArrayList;

import Auxiliar.Matriz;

public class Bayes {
	private ArrayList<Matriz> mediasClase;
	private ArrayList<Matriz> covarianzasClase;
	public Bayes() {
		mediasClase =new ArrayList<Matriz>();
		covarianzasClase =new ArrayList<Matriz>();
	}
	public void calcularMedia(ArrayList<Matriz> clase) {
		Matriz media = new Matriz(clase.get(0).getFilas(),clase.get(0).getColumnas());
		for(Matriz matriz:clase) {
			media.sumarV(matriz);
		}
		double den=(double)1/clase.size();
		media.multiplicarNatural(den);
		mediasClase.add(media);
	}
	public void calcularCovarianzas(ArrayList<Matriz> clase) {
		Matriz aux = new Matriz(clase.get(0).getColumnas(),clase.get(0).getColumnas());
		for(Matriz matriz:clase) {
			aux.sumarV(matriz.transpuesta().multiplicar(matriz));
		}
		double den=(double)1/clase.size();
		aux.multiplicarNatural(den);
		covarianzasClase.add(aux);
	}
	public int perteneceClase(Matriz test) {
		double min= Double.MAX_VALUE;
		int clase=-1;
		for(int i=0;i<mediasClase.size();i++) {
			Matriz aux=test.restar(mediasClase.get(i));
			Matriz auxt =aux.transpuesta();
			Matriz sol= aux.multiplicar(covarianzasClase.get(i).pseudoinversa()).multiplicar(auxt);
			if(min>sol.getElem(0, 0)) {
				clase=i;
				min=sol.getElem(0, 0);
			}
		}
		return clase;
	}
	public ArrayList<Matriz> getMediasClases(){
		return mediasClase;
	}
	
}
