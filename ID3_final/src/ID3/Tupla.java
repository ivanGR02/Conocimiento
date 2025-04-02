package ID3;

public class Tupla {

	double si,total;//cuantos hay y cuantos si
	double entropia;
	public Tupla(int total, int si){
		this.si=si;
		this.total=total;
	}
	public double entropia() {
		double no=total-si;
		return -(si/total)*log2(si/total)-(no/total)*log2(no/total);
	}
	
	public static double log2(double N){
 
        // SI es log2(0) tenemos que poner que el resultado es 0 para que no pete.
		 double result;
		if(N==0) {
			result=0;
		}else {
			result= Math.log(N) / Math.log(2);
		}
        return result;
    }
	public void addCaso(boolean si) {
		total++;
		if(si) {
			this.si++;
		}
	}
}