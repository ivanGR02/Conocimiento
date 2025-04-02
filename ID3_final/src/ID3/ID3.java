package ID3;

import java.util.ArrayList;
import java.util.List;

public class ID3 {

	Arbol inicio;
    public static ArrayList<ArrayList<String>> datos() {
        ArrayList<ArrayList<String>> datos = new ArrayList<>();

        // Agregando los datos a la lista
        datos.add(new ArrayList<>(List.of("soleado", "caluroso", "alta", "falso", "no")));
        datos.add(new ArrayList<>(List.of("soleado", "caluroso", "alta", "verdad", "no")));
        datos.add(new ArrayList<>(List.of("nublado", "caluroso", "alta", "falso", "si")));
        datos.add(new ArrayList<>(List.of("lluvioso", "templado", "alta", "falso", "si")));
        datos.add(new ArrayList<>(List.of("lluvioso", "frio", "normal", "falso", "si")));
        datos.add(new ArrayList<>(List.of("lluvioso", "frio", "normal", "verdad", "no")));
        datos.add(new ArrayList<>(List.of("nublado", "frio", "normal", "verdad", "si")));
        datos.add(new ArrayList<>(List.of("soleado", "templado", "alta", "falso", "no")));
        datos.add(new ArrayList<>(List.of("soleado", "frio", "normal", "falso", "si")));
        datos.add(new ArrayList<>(List.of("lluvioso", "templado", "normal", "falso", "si")));
        datos.add(new ArrayList<>(List.of("soleado", "templado", "normal", "verdad", "si")));
        datos.add(new ArrayList<>(List.of("nublado", "templado", "alta", "verdad", "si")));
        datos.add(new ArrayList<>(List.of("nublado", "caluroso", "normal", "falso", "si")));
        datos.add(new ArrayList<>(List.of("lluvioso", "templado", "alta", "verdad", "no")));

        return datos;
    }
    public static ArrayList<String> titulos() {
        ArrayList<String> titulos = new ArrayList<>();
        titulos.add("TiempoExterior");
        titulos.add("Temperatura");
        titulos.add("Humedad");
        titulos.add("Viento");
        titulos.add("Jugar");
        return titulos;
    }
    
    private void formarArbol(Arbol inicio) {
    	inicio.analizarRama();
    	inicio.redistribucion();
    	for(Arbol hijo:inicio.getHijos()){
    		if(hijo.RamaFinal()==-1) {
    			System.out.print(hijo.rasgo);
    			formarArbol(hijo);
    		}
    	}
    }
    public void casos(ArrayList<String> caso) {
    	System.out.println(caso+" y el resultado es "+inicio.caso(caso));
    }
    public ID3() {
    	inicio=new Arbol(this.datos(),this.titulos(),"Inicio");
    	formarArbol(inicio);
    	
    
    	System.out.println(inicio);
    }
    // Agregando los datos a la lista
   
	
	
}
