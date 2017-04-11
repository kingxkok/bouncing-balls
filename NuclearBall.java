package application;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class NuclearBall extends Ball{
	public NuclearBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
			double mass, double e) {
		super("nuclear", xPosition, yPosition, xVel, yVel, radius, mass, e);
		// TODO Auto-generated constructor stub
	}
	public NuclearBall(String name, double xPosition, double yPosition, double xVel, double yVel, double radius,
			double mass, double e, Paint p) {
		super("nuclear", xPosition, yPosition, xVel, yVel, radius, mass, e, p);
		// TODO Auto-generated constructor stub
	}
	public void explode(int count, int numOfPieces, ArrayList<Ball> balls){
		int control = 5;
			double angle = control*Math.PI*2/numOfPieces;
			double r2 = this.getRadius()/Math.sqrt(numOfPieces);
			double d = r2/Math.sin(angle/control);
			double absAngle = 0;
			for(int i = 0; i < numOfPieces/control; i++){
				balls.add(new Ball("Ara", this.getCenterX()+d*Math.cos(absAngle), this.getCenterY()+d*Math.sin(absAngle), 
						50*d*Math.cos(absAngle)+this.getVel().getElements()[0], 50*d*Math.sin(absAngle)+this.getVel().getElements()[1], r2, this.getMass()/numOfPieces,
						this.getElas(), Color.ORANGE ));
				absAngle+=angle;
			}
			if(count <= control){
				balls.add(new NuclearBall("nuclear", this.getCenterX(), this.getCenterY(), this.getVel().getElements()[0], this.getVel().getElements()[1], this.getRadius()*(1-1.0/control),
			this.getMass()*(1-1.0/control), this.getElas()));
				((NuclearBall)balls.get(balls.size()-1)).explode(count+1, numOfPieces, balls);
			}
				balls.remove(this);
		}
	public void explode(Ball ball, int numOfPieces, ArrayList<Ball> balls){
		this.explode(0, numOfPieces, balls);
	}
	
	
}
