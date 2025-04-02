package ID3;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		ID3 id = new ID3();
		id.casos(new ArrayList<>(List.of("soleado", "caluroso", "normal", "falso", "no")));
		// si quieres comprobar mas casos tienes que ponerlos en el orden de y con valores ya establecidos
		//  TiempoExterior,Temperatura,Humedad,Viento,Jugar

	}
}
