import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {
	Coordenada coordenada;
	Nodo padre;
	double g, h;//g es el coste acumulado y h el coste estimado;
	double penalizacion;
	public Nodo(Coordenada coor, Nodo padre, double g,Coordenada fin) {
       this.coordenada=coor;
       this.padre=padre;
       this.g=g;
       this.setH(fin);
	}
	
	@Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (otro == null || getClass() != otro.getClass()) {
            return false;
        }
        Nodo nodo = (Nodo) otro;
        return coordenada.equals(nodo.coordenada);
    }
	
	 public int hashCode() {
	        int result = 17; // Valor arbitrario inicial
	        result = 31 * result + this.coordenada.getX();
	        result = 31 * result + this.coordenada.getY();
	        return result;
	    }
	
	@Override
	public int compareTo(Nodo otroNodo) {
		  double sumaEste = this.g + this.h;
		    double sumaOtro = otroNodo.g + otroNodo.h;
		    
		    if (sumaEste < sumaOtro) {
		        return -1; // Este nodo es mejor que el otro
		    } else if (sumaEste > sumaOtro) {
		        return 1; // Este nodo es peor que el otro
		    } else {
		        return 0; // Ambos nodos tienen la misma suma de g + h
		    }
	}
	
	public ArrayList<Nodo> recorrer(ArrayList<Nodo> sol){
	  if(padre!=null)
		  padre.recorrer(sol);
	  sol.add(this);
	  return sol; 
	}
	
	
	public ArrayList<Nodo> alrededor(int filas,int columnas, Coordenada fin){
	    ArrayList<Nodo> nodosAdyacentes = new ArrayList<>();
	    int fila= this.getCoordenada().getX();//ojooo
	    int columna= this.getCoordenada().getY();
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
            	 double dist;
            	 if(i>=0 && j>=0 && i<filas &&j<columnas) {
            		 if (Math.abs(i - fila) == 1 && Math.abs(j - columna) == 1) {
                         // Diagonal, distancia raíz de dos
                         dist = Math.sqrt(2);
                     } else {
                         // No diagonal, distancia uno
                         dist = 1;
                     }
            		 if(j!=columna || i!=fila) {
            			  Nodo vecino = new Nodo(new Coordenada(i, j), this, this.getG() + dist, fin);
            			  if(this.padre != vecino)
                          nodosAdyacentes.add(vecino);  
            		 }
                   
            	 }
                
            }
        }

        return nodosAdyacentes;
	}
	
	public Nodo getPadre() {
		return padre;
	}
	public double getG() {
		return g;
	}
	public Double getTotal() {
		return g+h;
	}
	public void setG(double g) {
		this.g = g;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public void setH(Coordenada destino) {
		 int dx = this.coordenada.getX() - destino.getX();
	     int dy = this.coordenada.getY() - destino.getY();
	     this.h= Math.sqrt(dx * dx + dy * dy);
	}
	
	public Coordenada getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	@Override
	  public String toString() {
		return " coordenadas "+coordenada;  
	  }
	
}
