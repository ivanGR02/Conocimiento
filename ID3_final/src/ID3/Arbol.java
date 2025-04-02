package ID3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arbol {
	HashMap<String,Rama> arbol;
	double min= Double.MAX_VALUE;
	String rasgo= "";// se refiere al titulo ej TiempoExterior, 
	ArrayList<Arbol> hijos;
	ArrayList<ArrayList<String>> datos;
	ArrayList<String> titulos;
	String nombre;
	
	public void analizarRama() {
		arbol.forEach((k,v) ->{
			if(min>v.entropia()) {
				min=v.entropia();
				rasgo =k;
			}
		});
		System.out.println("Mi Nodo es "+nombre);
		System.out.println("Me divido por "+rasgo);
		System.out.println("Los hijos son "+arbol.get(rasgo).clases());
		
	}
	public Arbol(ArrayList<ArrayList<String>> datos, ArrayList<String> titulos, String nombre) {
		this.datos=datos;
		this.titulos=titulos;
		this.nombre=nombre;
		arbol = new HashMap<String,Rama>();
		for(int i=0;i<titulos.size()-1;i++) {// -2 ya que la columna de Jugar no tiene rama
			arbol.put(titulos.get(i),new Rama());
		}
		for(ArrayList<String> linea:datos) {
			for(int i=0;i<titulos.size()-1;i++) {
				boolean si;
				if(linea.get(titulos.size()-1).equals("si")) {
					si=true;
				}else {
					si=false;
				}
				arbol.get(titulos.get(i)).addCaracteristica(linea.get(i), si);
			}
		}
	}
	public void redistribucion() {// crear las sublistas por cada rama
		int posicion=0;// columna que eliminamos para 
		for(int i=0;i<titulos.size();i++) {
			if(titulos.get(i).equals(rasgo)) posicion=i;
		}
		HashMap<String,ArrayList<ArrayList<String>>> sublistas = new HashMap<String,ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> datosaux=datos;
		for(String rama:arbol.get(rasgo).clases()) {
			ArrayList<ArrayList<String>> subdatos = new ArrayList<ArrayList<String>>();
			int i=0;
			while(i<datosaux.size()) {
				if(datosaux.get(i).get(posicion).equals(rama)) {
					ArrayList<String> aux = datosaux.get(i);
					aux.remove(posicion);
					subdatos.add(aux);// eliminamos la columna con menos merito y la añadimos a la subtabla
				}
				else i++;
			}
			sublistas.put(rama, subdatos);
		}
		ArrayList<String> nuevosTitulos=(ArrayList<String>) titulos.clone();
		nuevosTitulos.remove(rasgo);
		titulos=nuevosTitulos;
		ArrayList<Arbol> subArboles = new ArrayList<Arbol>();
		sublistas.forEach((nombre, tablas) -> {
				subArboles.add(new Arbol(tablas,nuevosTitulos,this.nombre+" -> "+nombre));
		});
		this.hijos=subArboles;
	}
	public ArrayList<Arbol> getHijos(){
		return hijos;
	}
	
	public int RamaFinal() {
		String jugar= datos.get(0).get(datos.get(0).size()-1);// miramos que el ultimo valor es un si o un no
		for(int i=0;i<datos.size();i++) {
			if(!datos.get(i).get(datos.get(0).size()-1).equals(jugar))
				return -1;// hay que seguir explorando la rama
		}
		if(jugar.equals("si"))return 1;// rama positiva
		return 0;// rama negativa
	}
	
	public String toString() {
		String aux="";
		if(RamaFinal()==0) {
			return nombre+" -\n";
			
		}else if(RamaFinal()==1) {
			return nombre+" +\n";
		} else {
			for(Arbol hijo:hijos) {
				aux+=hijo.toString();
			}
		}
		return aux;
		
	}

	public String caso(ArrayList<String> caso) {
		// miro si estoy en una rama final, si lo estoy miro que valor tengo
		if(RamaFinal()==1) {
			return "+";
		}else if(RamaFinal()==0) {
			return "-";
		}
		//no estoy en rama final, miro a cual de mis hijos voy con el nombre y me llamo recursivamente
		for(int i=0;i<caso.size();i++) {
			for(int j=0;j<hijos.size();j++) {
				if(hijos.get(j).nombre.endsWith(caso.get(i))) return hijos.get(j).caso(caso);
			}
		}
		return "";
	}

}
