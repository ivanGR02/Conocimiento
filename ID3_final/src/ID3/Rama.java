package ID3;

import java.util.HashMap;
import java.util.ArrayList;

public class Rama {
	HashMap<String,Tupla> Rama;
	double entropia;
	public Rama() {
		this.Rama= new HashMap<String,Tupla>();
	}
	public Rama(HashMap<String,Tupla> Rama) {
		this.Rama=Rama;
	}
	public double entropia() {
		double totalCasos=0;
		for(String nombre:Rama.keySet()) {
			totalCasos+=Rama.get(nombre).total;
		}
		double entropia=0;
		for(String nombre:Rama.keySet()) {
			entropia+= (Rama.get(nombre).total/totalCasos)*Rama.get(nombre).entropia();
		}
		this.entropia=entropia;
		return entropia;
	}
	public void addCaracteristica(String Nombre, Boolean si) {
		if(Rama.containsKey(Nombre)) {
			Rama.get(Nombre).addCaso(si);
		}else {
			Tupla aux=new Tupla(0,0);
			aux.addCaso(si);
			Rama.put(Nombre, aux);
		}
	}
	public ArrayList<String> clases(){
		
		return new ArrayList<>(Rama.keySet());
		
	}
}
