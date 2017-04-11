package application;

import javafx.scene.paint.Paint;

public class ChargedBall extends GravityBall{
    int charge;
    private double magnitude;
    public ChargedBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
            double mass, double e, int c) {
        super(name, xPosition, yPosition, xVel, yVel, radius, mass, e);
        charge = c;
        magnitude = 1000;
        // TODO Auto-generated constructor stub
    }
    public ChargedBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
            double mass, double e, int c, double m) {
        super(name, xPosition, yPosition, xVel, yVel, radius, mass, e);
        charge = c;
        magnitude = m;
        // TODO Auto-generated constructor stub
    }
    public ChargedBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
            double mass, double e, int c, double m, Paint p) {
        super(name, xPosition, yPosition, xVel, yVel, radius, mass, e, p);
        charge = c;
        magnitude = m;
        // TODO Auto-generated constructor stub
    }
    public double getMagnitude(){
    	return magnitude;
    }
    
    public void charge(ChargedBall other){
        double xDif = other.getPos().getElements()[0] - getPos().getElements()[0];
        double yDif = other.getPos().getElements()[1] - getPos().getElements()[1];
        double dist = distanceTo(other);
        int c = -1*charge*other.getCharge();
        double qq = other.getMagnitude()*this.getMagnitude();
        addAcc(c*1000*qq/this.getMass()*xDif/Math.pow(dist, 3),
                c*1000*qq/this.getMass()*yDif/Math.pow(dist, 3));
        other.addAcc(c*-1000*qq/other.getMass()*xDif/Math.pow(dist, 3),
        		c*-1000*qq/other.getMass()*yDif/Math.pow(dist, 3));
      //System.out.println("confirmed at " + other.getCenterX() + ", " + other.getCenterY() + " and " + this.getCenterX() + ", " + this.getCenterY());
    }
    
    public int getCharge(){
        return charge;
    }
}


