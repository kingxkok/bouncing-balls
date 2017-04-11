package application;

public abstract class Vector {
	protected double[] elements;
	
	public Vector(int dimension){
		elements = new double[dimension];
	}
	public Vector(){}
	
	public abstract double dotProduct(Vector other);
//	public abstract Vector crossProduct(Vector other);
	public  double magSquare(){ //magnitude/absolute value
		double mag = 0;
		for(double element : elements){
			mag += element*element;
		}
		return mag;
	} 
	
	public double mag(){
		double mag = 0;
		for(double element : elements){
			mag += element*element;
		}
		return Math.sqrt(mag);
	}
	
	public double[] getElements(){
		return elements;
	}
	
	public String toString(){
		String output = "(";
		for(double element : elements){
			output+=Math.round(element*100)/100.0 + ", ";
		}
		return output.substring(0,output.length()-2)+")";
	}
	
}
