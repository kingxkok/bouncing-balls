package application;

import javafx.scene.paint.Paint;

public class GravityBall extends Ball{
    public GravityBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
            double mass, double e) {
        super("black", xPosition, yPosition, xVel, yVel, radius, mass, e);
        
    }
    
    public GravityBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
            double mass, double e, Paint p) {
        super("black", xPosition, yPosition, xVel, yVel, radius, mass, e, p);
        
    }

    
 //   Vector2D accel = new Vector2D(0, 0);
    
    public void attract(GravityBall other){
        double xDif = other.getPos().getElements()[0] - getPos().getElements()[0];
        double yDif = other.getPos().getElements()[1] - getPos().getElements()[1];
        double dist = distanceTo(other);
        addAcc(1000*other.getMass()*xDif/Math.pow(dist, 3), 1000*other.getMass()*yDif/Math.pow(dist, 3));
        other.addAcc(-1000*this.getMass()*xDif/Math.pow(dist, 3), -1000*this.getMass()*yDif/Math.pow(dist, 3));
        
        
      //System.out.println("confirmed at " + other.getCenterX() + ", " + other.getCenterY() + " and " + this.getCenterX() + ", " + this.getCenterY());
    }
 /*   public Vector2D getAcc(){
        return accel;
    }
    public void setAcc(double x, double y){
        accel.setElements(x, y);
    }
    */
    public void tick(){
        setPos(getPos().plus( getVel().scalarProduct(0.01) ));
        setVel(getVel().plus(accel.scalarProduct(0.01)));
    }
    public void advance(double timePerTick){
        setPos(getPos().plus( getVel().scalarProduct(timePerTick) ));
        setVel(getVel().plus(accel.scalarProduct(timePerTick)));
    }
    public void addAcc(double x, double y){
        accel.getElements()[0] += x;
        accel.getElements()[1] += y;
    }
}




