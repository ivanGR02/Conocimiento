package KMedios;

import java.util.ArrayList;
import Auxiliar.Matriz;

public class KMedios {
    private ArrayList<Matriz> mediasClase;
    private ArrayList<Matriz> casos;
    private double b = 2;
    private double tolerancia = 0.01;
    private ArrayList<ArrayList<Double>> probabilidades;

    public KMedios() {
        mediasClase = new ArrayList<>();
        casos = new ArrayList<>();
        probabilidades = new ArrayList<>();
    }

    public void inicializacionMedias(Matriz centro) {
        mediasClase.add(centro);
    }

    public int perteneceClase(Matriz test) {
        double max = Double.MIN_VALUE;
        int clase = -1;
        double denominador = 0;
        for (int i = 0; i < mediasClase.size(); i++) {
            double dis = mediasClase.get(i).distanciaK(test);
            denominador += Math.pow(1 / dis, 1 / (b - 1));
        }
        probabilidades.add(new ArrayList<>());
        for (int i = 0; i < mediasClase.size(); i++) {
            double dis = mediasClase.get(i).distanciaK(test);
            double prob = Math.pow((1 / dis) / denominador, 1 / (b - 1));
            probabilidades.get(probabilidades.size() - 1).add(prob);
            if (prob > max) {
                clase = i;
                max = prob;
            }
        }
        return clase;
    }

    public void agregarCaso(Matriz caso) {
        casos.add(caso);
    }

    private boolean actualizarClase() {
        boolean listo = true;
        double[][] u = new double[mediasClase.size()][casos.size()];

        for (int j = 0; j < casos.size(); j++) {
            Matriz muestra = casos.get(j);
            double[] distanciaKs = new double[mediasClase.size()];
            double suma = 0.0;
            for (int i = 0; i < mediasClase.size(); i++) {
                distanciaKs[i] = mediasClase.get(i).distanciaK(muestra);
                suma += Math.pow(1 / distanciaKs[i], 1 / (b - 1));
            }
            for (int i = 0; i < mediasClase.size(); i++) {
                u[i][j] = Math.pow((1 / distanciaKs[i]) / suma, b);
            }
        }

        for (int i = 0; i < mediasClase.size(); i++) {
            Matriz nuevaMedia = new Matriz(mediasClase.get(i).getFilas(), mediasClase.get(i).getColumnas());
            double sumaPonderada = 0.0;
            for (int j = 0; j < casos.size(); j++) {
                nuevaMedia.sumarVK(casos.get(j).multiplicarNaturalK(u[i][j]));
                sumaPonderada += u[i][j];
            }
            Matriz mediaActualizada = nuevaMedia.multiplicarNaturalK(1.0 / sumaPonderada);
            if (mediasClase.get(i).distanciaK(mediaActualizada) > tolerancia) {
                listo = false;
            }
            mediasClase.set(i, mediaActualizada);
        }

        return listo;
    }
    
 // Método getMediasClase agregado
    public ArrayList<Matriz> getMediasClase() {
        return mediasClase;
    }

    public void aprendizaje() {
        boolean listo = false;
        while (!listo) {
            listo = actualizarClase();
        }
    }
}