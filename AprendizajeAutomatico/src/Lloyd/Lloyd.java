package Lloyd;

import java.util.ArrayList;

import Auxiliar.Matriz;

public class Lloyd {
	private ArrayList<Matriz> mediasClase;
	private int maxIteraciones=10;
	private double razonAprendizaje=0.1;
	private double tolerancia=10e-10;
	public Lloyd() {
		mediasClase= new ArrayList<Matriz>();
	}
	public void inicializacionMedias(Matriz centro) {
		mediasClase.add(centro);
	}
	public int perteneceClase(Matriz test) {
		double min= Double.MAX_VALUE;
		int clase=-1;
		for(int i=0;i<mediasClase.size();i++) {
			double sol= mediasClase.get(i).distancia(test);
			if(min>sol) {
				clase=i;
				min=sol;
			}
		}
		return clase;
	}
	public void actualizarClase(int clase,Matriz m) {
		boolean saqueme = false;
		for(int iteraciones=0;iteraciones<maxIteraciones && !saqueme ;iteraciones++) {
			Matriz clasemenosMatriz=m.restar(mediasClase.get(clase));
			Matriz variacion= clasemenosMatriz.multiplicarNatural(razonAprendizaje);
			Matriz mt1= mediasClase.get(clase).sumar(variacion);
			Matriz mt =mediasClase.get(clase);
			double cambio= mt1.distancia(mt);
			Matriz res= (mediasClase.get(clase).restar(m)).multiplicarNatural(razonAprendizaje);
			Matriz aux=mediasClase.get(clase).sumar(res);
			if(cambio<tolerancia) saqueme=true;
			mediasClase.set(clase, mt1);
		}
	}
	public ArrayList<Matriz> getMediasClases(){
		return mediasClase;
	}
}
