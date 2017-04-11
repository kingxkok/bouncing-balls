package application;

public class Vector2D extends Vector {
	
	public Vector2D(double x, double y){
		super();
		elements = new double[2];
		elements[0]=x;
		elements[1]=y;
	}
	
	public void setElements(double x, double y){
		elements[0] = x;
		elements[1] = y;
	}
	
	public double dotProduct(Vector other){
		double dot = other.getElements()[0]*this.getElements()[0] 
				+ other.getElements()[1]*this.getElements()[1];
		return dot;
	}
	
	public Vector2D minus(Vector2D other){
		return new Vector2D(elements[0]-other.getElements()[0],
				elements[1]-other.getElements()[1]);
	}
	public Vector2D plus(Vector2D other){
		return new Vector2D(elements[0]+other.getElements()[0],
				elements[1]+other.getElements()[1]);
	}
	public Vector2D scalarProduct(double scalar){
		return new Vector2D(elements[0]*scalar, elements[1]*scalar);
	}
	public Vector2D reverse(){
		return new Vector2D(0-elements[0], 0-elements[1]);
	}
	
	
	
	
//	public Vector crossProduct(Vector other){	}
	
	
	
	
	
	
}
