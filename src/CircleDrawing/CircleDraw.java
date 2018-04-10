package CircleDrawing;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PVector;

public class CircleDraw extends PApplet {
	public static void main(String[] args) {PApplet.main("CircleDrawing.CircleDraw");}
	public void settings() {fullScreen();/*size(1000, 1000);*/}
	
	ArrayList<Circle> circles = new ArrayList<Circle>();
	Random prg = new Random();
	
	public void setup() {
		background(255);
		
		circles.add(new Circle(this, new PVector(width/2, height/2), 20));
		circles.add(new Circle(this, new PVector(width/2-30, height/2), 10));
		for(int i = 0; i < circles.size(); i++) {
			circles.get(i).draw();
			circles.get(i).findAdjacents(circles.toArray(new Circle[circles.size()]));
		}
		circles.add(newCircle(circles.get(0), circles.get(1)));
		circles.get(2).draw();
		delay(1000);
	}
	
	public void draw() {
		circles.add(newCircle(circles.get(circles.size()-2), circles.get(circles.size()-1)));
		circles.get(circles.size()-1).draw();
		//delay(5);
	}
	
	public Circle newCircle(Circle a, Circle b) {
		Circle result;
		PVector center = new PVector();
		float anglecompensation = degrees(PVector.sub(a.center, b.center).heading());
		float radius = ((a.radius + b.radius) / 2 + (float)(prg.nextGaussian()*2-.3));
		radius = Math.max(Math.min(radius, 100), 5);
		float angle = acos((pow(a.radius + b.radius,2) - pow(b.radius + radius,2) + pow(a.radius + radius,2))/(2*(a.radius + b.radius)*(a.radius + radius))) + PVector.sub(a.center, b.center).heading();
		center.x = -(radius + a.radius) * cos(angle) + a.center.x;
		center.y = -(radius + a.radius) * sin(angle) + a.center.y;
		result = new Circle(this,center,radius);
		if (result.testOverlap(circles.toArray(new Circle[circles.size()]))) {
			angle = -acos((pow(a.radius + b.radius,2) - pow(b.radius + radius,2) + pow(a.radius + radius,2))/(2*(a.radius + b.radius)*(a.radius + radius))) + PVector.sub(a.center, b.center).heading();
			anglecompensation = degrees(angle);
			result.center.x = -(radius + a.radius) * cos(angle) + a.center.x;
			result.center.y = -(radius + a.radius) * sin(angle) + a.center.y;
			while (result.testOverlap(circles.toArray(new Circle[circles.size()])) && radius > 0.1) {
				angle += 0.005;
				result.center.x = -(radius + a.radius) * cos(angle) + a.center.x;
				result.center.y = -(radius + a.radius) * sin(angle) + a.center.y;
				if (angle > 10 + prg.nextFloat() * TWO_PI) {
					radius -= 0.5;
					angle = 0;
				}
			}
		}
		return result;
	}
}
