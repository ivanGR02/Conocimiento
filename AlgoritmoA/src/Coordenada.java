
public class Coordenada {
    private int x;
    private int y;

    // Constructor
    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Métodos para obtener y establecer los valores de x e y
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Método para imprimir las coordenadas
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Coordenada other = (Coordenada) obj;
        return x == other.x && y == other.y;
    }
      @Override
      public int hashCode() {
        int result = 17; // Valor arbitrario inicial
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }
     
    // Método para calcular la distancia entre dos coordenadas
    public double calcularDistancia(Coordenada otraCoordenada) {
        int dx = this.x - otraCoordenada.getX();
        int dy = this.y - otraCoordenada.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}