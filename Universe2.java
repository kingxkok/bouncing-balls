package application;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

import java.util.ArrayList;
import application.Ball;
import application.GravityBall;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.animation.AnimationTimer;

public class Universe2 extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);
	}

	// final int ticksPerFrame.get(0) = 40;
	final static ArrayList<Integer> ticksPerFrame = new ArrayList<Integer>();
	final static ArrayList<Boolean> relaunch = new ArrayList<Boolean>();

	final static double timePerTick = 0.00126;
	final static ArrayList<Double> score = new ArrayList<Double>();

	public void start(Stage primaryStage) throws Exception {
		score.add(0.0);
		score.add(0.0);
		ticksPerFrame.add(500); // adjust ticks per frame here
		relaunch.add(false);
		bigBang(primaryStage);
		javafx.application.Platform.runLater(new Runnable() {
			public void run() { 
				Stage stage = new Stage();
				Group controls = new Group();

				Button speedup = new Button("Faster");
				speedup.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						ticksPerFrame.add(ticksPerFrame.remove(0) * 2);
					}
				});

				Button slowdown = new Button("Slower");
				slowdown.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						ticksPerFrame.add(ticksPerFrame.remove(0) / 2);
					}
				});

				Button speedset = new Button("Set");
				speedset.setTranslateY(60);
				speedset.setTranslateX(120);

				final TextField setspeed = new TextField();
				setspeed.setPromptText("What speed?");
				setspeed.setTranslateY(60);
			
				speedset.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {

						ticksPerFrame.set(0, Integer.parseInt(setspeed.getText()));
					}
				});
				
				Button reset = new Button("Reset");
				reset.setTranslateX(120);
				reset.setTranslateY(120);
				reset.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						relaunch.add(!relaunch.remove(0));
						ticksPerFrame.set(0, 500);
					}
				});
				

				controls.getChildren().add(speedup);
				controls.getChildren().add(slowdown);
				controls.getChildren().get(1).setLayoutY(30);
				controls.getChildren().add(setspeed);
				controls.getChildren().add(speedset);
				controls.getChildren().add(reset);

				stage.setScene(new Scene(controls));
				stage.show();

			}
		});

		
		
	
	}

	

	public static void bigBang(final Stage primaryStage){
		score.set(0, 0.0);
		final int width = 1000;
		final int height = 800;
		final int cBallNum = 0; // chargedParticles
		ArrayList<Integer> trace = new ArrayList<Integer>();
		trace.add(0);
		final RadialGradient g = new RadialGradient(0, 0, 0.35, 0.35, 0.5, true, CycleMethod.NO_CYCLE,
				new Stop(0.0, Color.ANTIQUEWHITE), new Stop(1.0, Color.BLANCHEDALMOND));
		final ArrayList<Ball> balls = new ArrayList<Ball>();
	/*	int[][] territory = new int[width][height];
		for (int x = 0; x < territory.length; x++)
			for (int y = 0; y < territory[0].length; y++)
				territory[x][y] = -1;*/
		final ArrayList<NuclearBall> nBalls = new ArrayList<NuclearBall>();
		for (int i = 0; i < 2; i++) {
			nBalls.add(
					new NuclearBall("black", width * Math.random(), height * Math.random(), 20 * Math.random() - 10,
							20 * Math.random() - 10, Math.random() * 20 + 25, 20000, 1, Color.GREENYELLOW));
			balls.add(nBalls.get(i));
		}
		for (int i = 0; i < 0; i++) {
			balls.add(new Ball("Ara", (i % 50) * width / 60 + 10, i / 50 * height / 60 + 50, 200 * Math.random() - 100,
					200 * Math.random() - 100, 5, 200, 1));
		/*	for (int x = -(int) balls.get(i).getRadius(); x <= (int) balls.get(i).getRadius(); x++) {
				territory[(int) (x + balls.get(i).getCenterX())][(int) (balls.get(i).getCenterY()
						+ (int) Math.sqrt(Math.pow(balls.get(i).getRadius(), 2) - x * x))] = i;
				territory[(int) (x + balls.get(i).getCenterX())][(int) (balls.get(i).getCenterY()
						- (int) Math.sqrt(Math.pow(balls.get(i).getRadius(), 2) - x * x))] = i;
				territory[(int) (x - 1 + balls.get(i).getCenterX())][(int) (balls.get(i).getCenterY()
						+ (int) Math.sqrt(Math.pow(balls.get(i).getRadius(), 2) - (x - 1) * (x - 1)))] = i;
				territory[(int) (x - 1 + balls.get(i).getCenterX())][(int) (balls.get(i).getCenterY()
						- (int) Math.sqrt(Math.pow(balls.get(i).getRadius(), 2) - (x - 1) * (x - 1)))] = i;
			}*/
		}
		final ArrayList<GravityBall> bHoles = new ArrayList<GravityBall>();
		for (int i = 0; i < 0; i++) {
			bHoles.add(new GravityBall("black", width * Math.random(), height * Math.random(),
					200 * Math.random() - 100, 200 * Math.random() - 100, Math.random() * 20 + 5, 20000,
					Math.random() * 0.3 + 0.7, Color.BLACK));
			balls.add(bHoles.get(i));
		}
		final ChargedBall[] cBalls = new ChargedBall[cBallNum];
		for (int i = 0; i < cBalls.length; i++) {
			double k = Math.random();
			int mass;
			int size;
			if (k < 0.1) {
				k = 1;
				mass = 2000;
				size = 10;
			} else {
				k = -1;
				mass = 1;
				size = 1;
			}
			cBalls[i] = new ChargedBall("Ara", width * Math.random(), height * Math.random(), 200 * Math.random() - 100,
					200 * Math.random() - 100, Math.random() * 2 + size, mass, 1, (int) k);
			balls.add(cBalls[i]);
		}
		Group root = new Group();
		Scene scene = new Scene(root, width, height, Color.WHITE);

		Canvas layer1 = new Canvas(width, height);
		final GraphicsContext gc1 = layer1.getGraphicsContext2D();
		gc1.setFill(Color.GREEN);

		// gc1.fillOval(ball1.getCenterX()-ball1.getRadius(),ball1.getCenterY()-ball1.getRadius(),ball1.getRadius()*2,ball1.getRadius()*2);
		// gc1.fillOval(ball2.getCenterX()-ball2.getRadius(),ball2.getCenterY()-ball2.getRadius(),ball2.getRadius()*2,ball2.getRadius()*2);

		for (Ball ball : balls)
			gc1.fillOval(ball.getCenterX() - ball.getRadius(), ball.getCenterY() - ball.getRadius(),
					ball.getRadius() * 2, ball.getRadius() * 2);

		Pane pane = new Pane();
		pane.getChildren().add(layer1);
		// pane.getChildren().add(layer2);
		layer1.toFront();

		root.getChildren().add(pane);

		primaryStage.setScene(scene);
		primaryStage.show();

		// ((Ball)ball1).advance(10);
		// gc1.fillOval(ball1.getCenterX(),ball1.getCenterY(),ball1.getRadius()*2,ball1.getRadius()*2);

		primaryStage.show();

		
		
		layer1.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				GravityBall ball = new GravityBall("black", e.getX(), e.getY(), 0, 0, Math.random() * 20 + 5,
						500 * Math.random() + 10000000, Math.random() * 0.5 + 0.5, Color.BLACK);
				bHoles.add(ball);
				balls.add(ball);
			}

		});
		double acc = 1;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if(ke.getCode()==KeyCode.LEFT){
            		balls.get(0).setVel(balls.get(0).getVel().plus(new Vector2D(-acc, 0)));
            	}
            	if(ke.getCode()==KeyCode.RIGHT){
            		balls.get(0).setVel(balls.get(0).getVel().plus(new Vector2D(acc, 0)));
            	}
            	if(ke.getCode()==KeyCode.UP){
            		balls.get(0).setVel(balls.get(0).getVel().plus(new Vector2D(0, -acc)));
            	}
            	if(ke.getCode()==KeyCode.DOWN){
            		balls.get(0).setVel(balls.get(0).getVel().plus(new Vector2D(0, acc)));
            	}
            }
        });

		new AnimationTimer() {
			@Override
			public void handle(long now) {
				// gc1.fillOval(e.getX(),e.getY(),20,20);
				gc1.setFill(g);
				if(balls.get(0) == nBalls.get(0))
				score.set(0, Math.max(score.get(0), balls.get(0).getVel().mag()));
				for (Ball ball : balls)
					gc1.fillOval(ball.getCenterX() - ball.getRadius(), ball.getCenterY() - ball.getRadius(),
							ball.getRadius() * 2, ball.getRadius() * 2);
				for (int i = 0; i < ticksPerFrame.get(0); i++) {
					if(nBalls.size()>0){
					for (int k = 0; k < balls.size(); k++) {
						for (int y = k + 1; y < balls.size(); y++) {
							balls.get(k).checkCollision(balls.get(y));
									if (balls.get(k).getName().equals("nuclear")) 
										if(balls.get(k).distanceTo(balls.get(y)) <= (balls.get(k).getRadius() + balls.get(y).getRadius())){
										((NuclearBall) balls.get(k)).explode(balls.get(y), (int) (Math.random()*100+150),
												balls);
										ticksPerFrame.set(0, 1);
										}
							// if(balls.get(k).getName().equals("nuclear"))
							// ((NuclearBall)balls.get(k)).explode(balls.get(y),
							// (int)(Math.random()*5+1), balls);
							// ((GravityBall)balls.get(k)).attract((GravityBall)balls.get(y));
							/*
							 * if(balls.get(k).distanceTo(balls.get(y)) <
							 * balls.get(k).getRadius() +
							 * balls.get(y).getRadius()-1)
							 * if(balls.get(k).density() > ball.get(y).density){
							 * balls.add(k, new Ball("", )); balls.remove(k); }
							 */
						}
					}
					} else{
						for (int k = 0; k < balls.size(); k++)
							for (int y = k + 1; y < balls.size(); y++)
								balls.get(k).checkCollision(balls.get(y));
					}
					for (Ball ball : balls)
						ball.setAcc(0, 0);
					for (int k = 0; k < bHoles.size(); k++) {
						for (int y = k + 1; y < bHoles.size(); y++) {
							// if(bHoles[y].getMass()/Math.pow(bHoles[k].distanceTo(bHoles[y]),
							// 2) > 0.01 ||
							// bHoles[k].getMass()/Math.pow(bHoles[k].distanceTo(bHoles[y]),
							// 2) > 0.01)
							bHoles.get(k).attract(bHoles.get(y));
						}
					}
					for (int k = 0; k < cBalls.length; k++) {
						for (int y = k + 1; y < cBalls.length; y++) {
							// if(cBalls[y].getMass()/Math.pow(cBalls[k].distanceTo(cBalls[y]),
							// 2) > 0.01 ||
							// cBalls[k].getMass()/Math.pow(cBalls[k].distanceTo(cBalls[y]),
							// 2) > 0.01)
							cBalls[k].charge(cBalls[y]);
						}
					}
					
					for (int k = 0; k < bHoles.size(); k++) {
						for (int y = k + 1; y < bHoles.size(); y++) {
					bHoles.get(k).attract(bHoles.get(y));
					bHoles.get(k).paint(gc1, Color.BLACK);
						}
					}
							
					/*
					 * for(int k = 0; k < nBalls.size(); k++){ for(int y = k+1;
					 * y < nBalls.size(); y++){ //
					 * if(cBalls[y].getMass()/Math.pow(cBalls[k].distanceTo(
					 * cBalls[y]), 2) > 0.01 ||
					 * cBalls[k].getMass()/Math.pow(cBalls[k].distanceTo(cBalls[
					 * y]), 2) > 0.01) nBalls.get(k).explode(nBalls.get(y), 5,
					 * balls); if(nBalls.get(k).distanceTo(nBalls.get(y)) <=
					 * (nBalls.get(k).getRadius() + nBalls.get(y).getRadius())){
					 * balls.remove(nBalls.get(k)); nBalls.remove(k); } } }
					 */

					for (Ball ball : balls)
						ball.checkWallCollision(width, height);
					for (Ball ball : balls)
						ball.advance(timePerTick);
				}
				// if(trace.get(0)%500 == 0){
				gc1.setFill(Color.WHITE);
				gc1.fillRect(0, 0, width, height);
				// trace.set(0, 1);

				// }
				// trace.set(0, trace.get(0)+1);

				// gc1.setFill(Color.BLACK);
				// for(GravityBall ball : bHoles)
				// gc1.fillOval(ball.getCenterX()-ball.getRadius(),
				// ball.getCenterY()-ball.getRadius(),
				// ball.getRadius()*2, ball.getRadius()*2);

				for (Ball ball : balls){
					ball.paint(gc1);
					if(balls.indexOf(ball)==0)
						ball.paint(gc1, Color.DARKBLUE);
				}

				for (ChargedBall ball : cBalls) {
					if (ball.getCharge() == 1)
						gc1.setFill(Color.RED);
					else
						gc1.setFill(Color.BLUE);
					gc1.fillOval(ball.getCenterX() - ball.getRadius(), ball.getCenterY() - ball.getRadius(),
							ball.getRadius() * 2, ball.getRadius() * 2);
				}
				//System.out.println(score.get(0));
				
				if(relaunch.get(0)){
					score.set(1, Math.max(score.get(1), score.get(0)));
					System.out.println("Your score for this round is " + score.get(0)+ ".\nYour highscore is " + score.get(1));
					stop();
					relaunch.set(0, false);
					bigBang(primaryStage);
					
					
					
				}
			}
		}.start();
	}
	


}

/*
 * Plan for new collision checker due to the inefficiency of the n^2 order
 * algorithm (This has 2*pi*r*n order where r is the average radius) 1. Create a
 * 2D array of type int called territory and model it to the canvas in size
 * while setting every entry to -1 2. Modify the initial creation of the 'balls'
 * arrayList so that right after a ball is created, the pixels it occupies
 * correspondingly changes the entry in 'territory' to the ball's index. 3.
 * Every element of this matrix will have either a -1 or a greater integer; -1
 * will signify the absence of a ball in that pixel while a natural number will
 * be a marker of a corresponding ball's territorial right to that pixel. 4. The
 * invasiveProperty number signifies the index of the ball in the 'balls'
 * arrayList 5. In the animation timer, create a for loop that checks the
 * circumference surrounding each ball for the presence of an
 * 'invasiveProperty'. This can be done as follows; for(int x = 0; x <= radius;
 * x++)(check invasive property at (ball.getCenterX()+ x, ball.getCenterY() +
 * (int)(Math.sqrt(ball.getRadius()*ball.getRadius()-x*x)) 6. Have the ball
 * bounce with each invading ball with reference to the invasiveProperty as an
 * index
 */
