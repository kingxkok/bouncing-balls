package application;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball implements Bounce {
    
    private double radius;
    private double mass;
    private double elasticity;
    private Paint p;
    
    private String name;
    
    private Vector2D pos;
    private Vector2D vel;
    
    //constructor
    public Ball(String name, double xPosition, double yPosition, double xVel,
            double yVel,double radius, double mass, double e){
//        super((xPosition), (yPosition), radius  );
        this.radius = radius;
        this.mass = mass;
        pos = new Vector2D(xPosition, yPosition);
        vel = new Vector2D(xVel, yVel);
        this.name = name;
        elasticity = e;
        this.p = Color.GREEN;
    }
    public Ball(String name, double xPosition, double yPosition, double xVel,
            double yVel,double radius, double mass, double e, Paint p){
//        super((xPosition), (yPosition), radius  );
        this.radius = radius;
        this.mass = mass;
        pos = new Vector2D(xPosition, yPosition);
        vel = new Vector2D(xVel, yVel);
        this.name = name;
        elasticity = e;
        this.p = p;
    }
    //accessor
    protected Vector2D accel = new Vector2D(0, 0);
    public double getMass(){
        return mass;
    }
    public Vector2D getAcc(){
        return accel;
    }
    public void setAcc(double x, double y){
        accel.setElements(x, y);
    }
    public double getElas(){
        return elasticity;
    }
    public void setElas(double a){
        elasticity = a;
    }
    public Vector2D getPos(){
        return pos;
    }
    public Vector2D getVel(){
        return vel;
    }
    public double getRadius(){
        return radius;
    }
    public void setRadius(double r){
        radius = r;
    }
    public double getCenterX(){
        return pos.getElements()[0];
    }
    public double getCenterY(){
        return pos.getElements()[1];
    }
    
    public void setPos(Vector2D to){
        pos.elements[0] = to.elements[0];
        pos.elements[1] = to.elements[1];
    }
    public String getName(){
    	return name;
    }
    
    //mutators
    public void setVel(Vector2D to){
        vel.elements[0] = to.elements[0];
        vel.elements[1] = to.elements[1];
    }
    
    @Override
    public String toString(){
        return "Ball "+ name +" is at position:    " + pos.toString() + "    and is moving at velocity:    " + vel.toString();
    }
    
    public void bounce(Ball ball){
        
        Vector2D v1Minusv2 = this.getVel().minus(ball.getVel());
        Vector2D x1Minusx2 = this.getPos().minus(ball.getPos());
        
        //en.wikipedia.org/wiki/Elastic_collision
        double v1MinusThisScalar =  v1Minusv2.dotProduct(x1Minusx2)/x1Minusx2.magSquare()*2*ball.getMass()/(this.mass+ball.getMass());
        double v2MinusThisScalar =  (v1Minusv2.reverse()).dotProduct(x1Minusx2.reverse())/x1Minusx2.magSquare()*2*this.getMass()/(this.mass+ball.getMass());
        
        this.setVel(vel.minus( (x1Minusx2.scalarProduct(v1MinusThisScalar) ) ));
        ball.setVel(ball.getVel().minus( (x1Minusx2.reverse()).scalarProduct(v2MinusThisScalar)));
        if(Math.abs(this.getVel().mag()-ball.getVel().mag())>30){
        this.setVel(this.getVel().minus(this.getVel().scalarProduct(Math.sqrt(1-ball.getElas())*elasticity)));
        //this.setRadius(radius - Math.sqrt(ball.getElas()*elasticity)*0.01*radius);
        ball.setVel(ball.getVel().minus(ball.getVel().scalarProduct(Math.sqrt(1-this.getElas())*ball.getElas())));
        //ball.setRadius(ball.getRadius() - Math.sqrt(ball.getElas()*elasticity)*0.01*ball.getRadius());
        }
    }
    
    public double distanceTo(Ball ball){
        return (( this.getPos().minus(ball.getPos() ) ) ).mag();
    }
    
    public void checkCollision(Ball ball){
        if(this.distanceTo(ball) <= (ball.getRadius() + this.radius)){
            this.bounce(ball);
        }
    }
    public double density(){
        return mass/(Math.PI*radius*radius);
    }
    
    public void advance(){
        pos = pos.plus( vel );
//        super.setCenterX(pos.getElements()[0]);
//        super.setCenterY(pos.getElements()[1]);
    }
    
    public void tick(){
        pos = pos.plus( vel.scalarProduct(0.01) );
//        super.setCenterX(pos.getElements()[0]);
    //    super.setCenterY(pos.getElements()[1]);
    }
    
    public void advance(double time){
        pos = pos.plus( vel.scalarProduct(time) );
    }
    
    public static void main(String[] args) throws Exception{
        Ball a = new Ball("Alpha", 0 , 2 , 2, 2, 5, 100, 1);
        Ball b = new Ball("Beta", 14 , 13 , -2, -2, 5, 10, 1);
        
        System.out.println(a);
        System.out.println(b);
        
        for(int i = 0; i<200; i++){
            sleepCheckTickPrint(a,b);
        }
        
    }
    
    public void bounceDown(){ //bouncing against horizontal wall
        vel.setElements(vel.getElements()[0], Math.abs(vel.getElements()[1]));
    }
    public void bounceUp(){ //bouncing against horizontal wall
        vel.setElements(vel.getElements()[0], -Math.abs(vel.getElements()[1]));
    }
    
    public void bounceLeft(){ //bouncing against vertical wall
        vel.setElements(-Math.abs(vel.getElements()[0]), vel.getElements()[1]);
    }
    public void bounceRight(){ //bouncing against vertical wall
        vel.setElements(Math.abs(vel.getElements()[0]), vel.getElements()[1]);
    }
    
    public void checkWallCollision(int width, int height){
        if(this.getCenterX()-radius <= 0) bounceRight();
        if(this.getCenterX()+radius >= width) bounceLeft();
        if(this.getCenterY()+radius >= height) bounceUp();
        if(this.getCenterY()-radius <= 0) bounceDown();
    }
    public static void sleepCheckTickPrint(Ball a, Ball b) throws Exception{
        Thread.sleep(90);
        a.checkCollision(b);

        a.tick();
        b.tick();
        
        System.out.println(a);
        System.out.println(b);
    }
    public void paint(GraphicsContext gc1){
    	gc1.setFill(p);
    	gc1.fillOval(this.getCenterX()-this.getRadius(), this.getCenterY()-this.getRadius(),
                this.getRadius()*2, this.getRadius()*2);
    	
    }
    public void paint(GraphicsContext gc1,  Paint p){
    	gc1.setFill(p);
    	gc1.fillOval(this.getCenterX()-this.getRadius(), this.getCenterY()-this.getRadius(),
                this.getRadius()*2, this.getRadius()*2);
    	
    }
    
    
}




