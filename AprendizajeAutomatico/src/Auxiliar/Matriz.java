package Auxiliar;

import java.util.Arrays;

public class Matriz {
    private final int filas;
    private final int columnas;
    private final double[][] matriz;

    // Constructor
    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new double[filas][columnas];
    }

    // Constructor que acepta una matriz predefinida
    public Matriz(double[][] ds) {
        this.filas = ds.length;
        this.columnas = ds[0].length;
        this.matriz = ds;
    }

    // Sumar dos matrices
    
    public Matriz sumar(Matriz otraMatriz) {
        if (this.filas != otraMatriz.filas || this.columnas != otraMatriz.columnas) {
            throw new IllegalArgumentException("Las matrices deben tener las mismas dimensiones.");
        }
        Matriz resultado = new Matriz(filas, columnas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado.matriz[i][j] = this.matriz[i][j] + otraMatriz.matriz[i][j];
            }
        }
        return resultado;
    }
    public void sumarV(Matriz otraMatriz) {
        if (this.filas != otraMatriz.filas || this.columnas != otraMatriz.columnas) {
            throw new IllegalArgumentException("Las matrices deben tener las mismas dimensiones.");
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.matriz[i][j] = this.matriz[i][j] + otraMatriz.matriz[i][j];
            }
        }
    }
    // Restar dos matrices
    public Matriz restar(Matriz otraMatriz) {
        if (this.filas != otraMatriz.filas || this.columnas != otraMatriz.columnas) {
            throw new IllegalArgumentException("Las matrices deben tener las mismas dimensiones.");
        }
        Matriz resultado = new Matriz(filas, columnas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado.matriz[i][j] = this.matriz[i][j] - otraMatriz.matriz[i][j];
            }
        }
        return resultado;
    }

    // Multiplicar dos matrices
    public Matriz multiplicar(Matriz otraMatriz) {
        if (this.columnas != otraMatriz.filas) {
            throw new IllegalArgumentException("El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
        }
        Matriz resultado = new Matriz(this.filas, otraMatriz.columnas);
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < otraMatriz.columnas; j++) {
                double sum = 0;
                for (int k = 0; k < this.columnas; k++) {
                    sum += this.matriz[i][k] * otraMatriz.matriz[k][j];
                }
                resultado.matriz[i][j] = sum;
            }
        }
        return resultado;
    }
    public void multiplicarV(Matriz otraMatriz) {
        if (this.columnas != otraMatriz.filas) {
            throw new IllegalArgumentException("El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
        }
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < otraMatriz.columnas; j++) {
                double sum = 0;
                for (int k = 0; k < this.columnas; k++) {
                    sum += this.matriz[i][k] * otraMatriz.matriz[k][j];
                }
                this.matriz[i][j] = sum;
            }
        }

    }
    
    // Multiplicar dos matrices
    public Matriz multiplicarNatural(double v) {
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
               this.matriz[i][j] *= v;
            }
        }
        return this;
    }

    // Calcular la covarianza
    public Matriz covarianza() {
        Matriz transpuesta = this.transpuesta();
        int n = this.columnas;
        Matriz resultado = new Matriz(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double cov = 0;
                for (int k = 0; k < this.filas; k++) {
                    cov += (this.matriz[k][i] - this.promedioColumna(i)) * (transpuesta.matriz[j][k] - transpuesta.promedioColumna(j));
                }
                resultado.matriz[i][j] = cov / (this.filas - 1);
            }
        }
        return resultado;
    }

    // Método para obtener la transpuesta de la matriz
    public Matriz transpuesta() {
        Matriz transpuesta = new Matriz(columnas, filas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                transpuesta.matriz[j][i] = matriz[i][j];
            }
        }
        return transpuesta;
    }

    // Método para calcular el promedio de una columna
    private double promedioColumna(int columna) {
        double sum = 0;
        for (int i = 0; i < filas; i++) {
            sum += matriz[i][columna];
        }
        return sum / filas;
    }

    // Método para imprimir la matriz
    public void imprimir() {
        for (int i = 0; i < filas; i++) {
            System.out.println(Arrays.toString(matriz[i]));
        }
    }

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}
	public double getElem(int fila,int columna) {
		return matriz[fila][columna];
	}
	public Matriz pseudoinversa() {
        Matriz transpuesta = this.transpuesta();
        Matriz multiplicacion = transpuesta.multiplicar(this);
        Matriz inversaMultiplicacion = multiplicacion.inversa();

        return inversaMultiplicacion.multiplicar(transpuesta);
    }

    // Obtener el determinante de una matriz 2x2
    private double determinante2x2(double[][] m) {
        return m[0][0] * m[1][1] - m[0][1] * m[1][0];
    }

    // Obtener el submatriz eliminando la fila y la columna dadas
    private double[][] submatriz(double[][] m, int fila, int columna) {
        int rows = m.length;
        int cols = m[0].length;
        double[][] submat = new double[rows - 1][cols - 1];
        for (int i = 0, r = 0; i < rows; i++) {
            if (i == fila) continue;
            for (int j = 0, c = 0; j < cols; j++) {
                if (j == columna) continue;
                submat[r][c] = m[i][j];
                c++;
            }
            r++;
        }
        return submat;
    }

    // Calcular la inversa de la matriz utilizando el método de adjuntos
    public Matriz inversa() {
        if (filas != columnas) {
            throw new ArithmeticException("La matriz no es cuadrada, por lo tanto, no se puede calcular su inversa.");
        }

        double determinante = determinante(matriz);
        if (determinante == 0) {
            throw new ArithmeticException("La matriz no es invertible.");
        }

        double[][] adjunta = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                double signo = (i + j) % 2 == 0 ? 1 : -1;
                adjunta[j][i] = signo * determinante2x2(submatriz(matriz, i, j));
            }
        }

        double factor = 1.0 / determinante;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                adjunta[i][j] *= factor;
            }
        }

        return new Matriz(adjunta);
    }

    // Calcular el determinante de una matriz cuadrada
    private double determinante(double[][] m) {
        int n = m.length;
        if (n == 1) {
            return m[0][0];
        } else if (n == 2) {
            return determinante2x2(m);
        } else {
            double det = 0;
            for (int j = 0; j < n; j++) {
                double[][] submat = submatriz(m, 0, j);
                det += Math.pow(-1, j) * m[0][j] * determinante(submat);
            }
            return det;
        }
    }
    public double distancia(Matriz m) {// se usa en Lloyd
    	double sol=0;
    	if(this.columnas==m.getColumnas() && this.filas==m.getFilas()) {
    		  for (int i = 0; i < filas; i++) {
    	            for (int j = 0; j < columnas; j++) {
    	                sol+= Math.pow((this.getElem(i, j)-m.getElem(i, j)),2);
    	            }
    	        }
    	}
    	return sol;
    }
    public void setElem(int fil,int col,double valor) {
    	matriz[fil][col]=valor;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                sb.append(matriz[i][j]);
                if (j < columnas - 1) {
                    sb.append("\t"); // Agrega un tabulador entre elementos de la misma fila
                }
            }
            sb.append("\n"); // Agrega un salto de línea al final de cada fila
        }
        return sb.toString();
    }


        // Método para calcular la distancia euclidiana
    public double distanciaK(Matriz m) {
        double sol = 0;
        if (this.columnas == m.getColumnas() && this.filas == m.getFilas()) {
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    sol += Math.pow(this.matriz[i][j] - m.matriz[i][j], 2);
                }
            }
        }
        return Math.sqrt(sol);
    }
    
 // Método sumar en su lugar
    public void sumarVK(Matriz otraMatriz) {
        if (this.filas != otraMatriz.filas || this.columnas != otraMatriz.columnas) {
            throw new IllegalArgumentException("Las matrices deben tener las mismas dimensiones.");
        }
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.matriz[i][j] += otraMatriz.matriz[i][j];
            }
        }
    }

 // Multiplicar dos matrices
    public Matriz multiplicarNaturalK(double escalar) {
    	 Matriz resultado = new Matriz(filas, columnas);
         for (int i = 0; i < this.filas; i++) {
             for (int j = 0; j < this.columnas; j++) {
                 resultado.matriz[i][j] = this.matriz[i][j] * escalar;
             }
         }
         return resultado;
    }
    
    // Multiplicar dos matrices
    public Matriz multiplicarK(Matriz otraMatriz) {
    	 if (this.columnas != otraMatriz.filas) {
             throw new IllegalArgumentException("El número de columnas de la primera matriz debe ser igual al número de filas de la segunda matriz.");
         }
         Matriz resultado = new Matriz(this.filas, otraMatriz.columnas);
         for (int i = 0; i < this.filas; i++) {
             for (int j = 0; j < otraMatriz.columnas; j++) {
                 double sum = 0;
                 for (int k = 0; k < this.columnas; k++) {
                     sum += this.matriz[i][k] * otraMatriz.matriz[k][j];
                 }
                 resultado.matriz[i][j] = sum;
             }
         }
         return resultado;
    }

}
