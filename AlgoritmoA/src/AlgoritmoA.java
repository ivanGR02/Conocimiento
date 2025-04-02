import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import javax.sound.midi.SysexMessage;


public class AlgoritmoA {
	int filas,columnas,riesgostomados;
	double riesgo;
	HashMap<Coordenada,String> coordenadas;// la representacion del mapa
	HashMap<Nodo,Double> cerrados;// tiene el double para comparar si hay que reevaluar
	HashSet<Coordenada> recorridos;// nos sirve para ver si hemos pasado por un nodo usado para los waypoint.ya que al poder reevaluarse un nodo deja de estar en cerrados
	PriorityQueue<Nodo> abiertos;
	ArrayList<Coordenada> waypoints;
	// habria que ver los checkpoints como los ponemos
	Coordenada inicio;
	Coordenada fin;
	public AlgoritmoA(int filas,int columnas,HashMap<Coordenada,String> coordenadas,Coordenada inicio,Coordenada fin,ArrayList<Coordenada> waypoints) {
		this.filas=filas;
		this.columnas=columnas;
		this.coordenadas=coordenadas;
		this.inicio=inicio;
		this.fin=fin;
		this.waypoints=waypoints;
		cerrados = new HashMap<>();
		abiertos = new PriorityQueue<>();
		recorridos = new HashSet<>();
		riesgo=Math.sqrt(filas*filas+columnas*columnas)/10;
	}
	public ArrayList<Nodo> NextCheckPoint(Coordenada empiece, Nodo padre,Double distanciaRecorrida,Coordenada end){
		Nodo init= new Nodo(empiece,padre,distanciaRecorrida,end);
		 Nodo sol= null;
		 init.setH(end);
		 ArrayList<Nodo>vecinos= init.alrededor(filas, columnas,end);
		 abiertos.add(init);
		 boolean salida=false;
		 while(!abiertos.isEmpty() && !salida) {
			 Nodo abierto=abiertos.remove();
			 cerrados.put(abierto, abierto.getTotal());
			 recorridos.add(abierto.getCoordenada());
			 vecinos=abierto.alrededor(filas, columnas, end);
			 for(Nodo nodo: vecinos) {
				 String texto=coordenadas.get(nodo.getCoordenada());
				 if(nodo.getH()==0) {
					 salida=true;
					 sol=nodo;
				 }
				 if(coordenadas.get(nodo.getCoordenada()).equals("bloqueo")) {
					 cerrados.put(nodo,nodo.getTotal());
				 }
				 else if(cerrados.containsKey(nodo)) {
					 if(nodo.getTotal()<(cerrados.get(nodo))){
						 cerrados.remove(nodo);
						 abiertos.add(nodo);
					 }
				 }
				 else if(!abiertos.contains(nodo)) {
					 if(coordenadas.get(nodo.getCoordenada()).equals("riesgo")) {
						 nodo.setG(nodo.getG()+riesgo);
					 }
					 abiertos.add(nodo);
				 }
				 
			 }
			 
		 }
		 if(salida) {
			 recorridos.add(end);
			 return sol.recorrer(new ArrayList<Nodo>());
		 }
		 else {
			 return null;
		 }
		
		
	}
	
	public String Mensaje(ArrayList<Nodo>sol) {
		String res="hay solucion :) la distancia es la siguiente: "+sol.get(sol.size()-1).getG();
		 String camino="";
		 for(int i=0;i< sol.size()-1;i++) {
			 if(coordenadas.get(sol.get(i).getCoordenada()).equals("riesgo")) riesgostomados++;
			 camino+=sol.get(i).toString()+"-->";
		 }
		 camino+=sol.get(sol.size()-1);
		 res+="\nHa pasado por "+riesgostomados+" riesgos, por lo que ha incrementado su coste en "+riesgostomados*riesgo;
		 res+="\nel camino es el siguiente:"+camino;
		return res;
		
	}
	public void ResolverCheck() {
		if(waypoints.isEmpty()) {
			ArrayList<Nodo> sol = NextCheckPoint(inicio,null,0.0,fin);
			if(sol==null) System.out.println("No hay solución");
			else {
			System.out.println(Mensaje(sol));
			}
		}else {
			Coordenada nextPoint= waypoints.get(0);
			Coordenada lastPoint = null;
			ArrayList<Nodo> sol = NextCheckPoint(inicio,null,0.0,nextPoint);
			sol.remove(sol.size()-1);// eliminamos el ultimo porque sino sale repetido(cosas de la implementación)
			boolean bloqueo=false;
			waypoints.removeAll(recorridos);// quitamos todos los chechkpoint que hemos pisado incluso si no es nuestro destino
			while(!waypoints.isEmpty()&& sol!=null && !bloqueo) {
				cerrados = new HashMap<>();
				abiertos = new PriorityQueue<>();// para reiniciar el algoritmo y volver a lanzarlo
				lastPoint = nextPoint;
				nextPoint= waypoints.get(0);// miramos cual es el siguiente punto
				ArrayList next = NextCheckPoint(lastPoint,null,sol.get(sol.size()-1).getG(),nextPoint);
				if(next==null) bloqueo=true;// si devuelve null es que no hay solucion
				else {
					waypoints.removeAll(recorridos);
					sol.addAll(next);// ponemos los nuevos pasos que ha recorrido para al final mostrar el camino
					sol.remove(sol.size()-1);// eliminamos el ultimo porque sino sale repetido(cosas de la implementación)
				}
				
				
			}
			if(bloqueo) {
				System.out.println("No hay solución");
			}
			else {// ejecutamos el ultimo A* ahora con el final, el nextPoint es ha donde ha terminado el anterior A* y es el inicio del ultimo A*
				cerrados = new HashMap<>();
				abiertos = new PriorityQueue<>();
				ArrayList next = NextCheckPoint(nextPoint,null,sol.get(sol.size()-1).getG(),fin);
				if(next==null) {
					System.out.println("No hay solución");
				}
				else {
					sol.addAll(next);
					System.out.println(Mensaje(sol));
				}
			}
			
				
		}
	}
	
	public void Resolver() {
		 Nodo init= new Nodo(inicio,null,0,fin);
		 Nodo sol= null;
		 init.setH(fin);
		 ArrayList<Nodo>vecinos= init.alrededor(filas, columnas,fin);
		 abiertos.add(init);
		 boolean salida=false;
		 
		 while(!abiertos.isEmpty() && !salida) {
			 Nodo abierto=abiertos.remove();
			 cerrados.put(abierto, abierto.getTotal());
			 vecinos=abierto.alrededor(filas, columnas, fin);
			 for(Nodo nodo: vecinos) {
				 String texto=coordenadas.get(nodo.getCoordenada());
				 if(coordenadas.get(nodo.getCoordenada()).equals("final")) {
					 salida=true;
					 sol=nodo;
				 }
				 if(coordenadas.get(nodo.getCoordenada()).equals("bloqueo")) {
					 cerrados.put(nodo,nodo.getTotal());
				 }
				 else if(cerrados.containsKey(nodo)) {
					 if(nodo.getTotal()>(cerrados.get(nodo))){
						 cerrados.remove(nodo);
						 abiertos.add(nodo);
					 }
				 }
				 else if(!abiertos.contains(nodo)) {
					 abiertos.add(nodo);
				 }
				 
			 }
			 
		 }
		 if(salida) {
			 // podriamos mostrar el recorrido obtenido
			 System.out.println("hay soluciooooooooooooooooooooon la distancia es la siguiente: "+sol.getG());
			 ArrayList<Nodo> res=sol.recorrer(new ArrayList<Nodo>());
			 String camino="";
			 for(int i=0;i< res.size()-1;i++) {
				 camino+=res.get(i).toString()+"-->";
			 }
			 camino+=res.get(res.size()-1);
			 System.out.println("el camino es el siguiente:"+camino);
		 }
		 else {
			 System.out.println("No hay hiccup");
		 }
	}
}
