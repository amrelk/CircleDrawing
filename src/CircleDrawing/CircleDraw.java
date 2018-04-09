package CircleDrawing;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class CircleDraw extends PApplet {
	public static void main(String[] args) {PApplet.main("CircleDrawing.CircleDraw");}
	public void settings() {size(1000, 1000);}
	
	ArrayList<Circle> circles = new ArrayList<Circle>();
	
	public void setup() {
		background(255);
		
		circles.add(new Circle(this, new PVector(500, 500), 60));
		circles.add(new Circle(this, new PVector(400, 500), 40));
		for(int i = 0; i < circles.size(); i++) {
			circles.get(i).draw();
			circles.get(i).findAdjacents(circles.toArray(new Circle[circles.size()]));
		}
		circles.add(newCircle(circles.get(0), circles.get(1)));
		circles.get(2).draw();
		delay(1000);
		circles.add(newCircle(circles.get(1), circles.get(2)));
		circles.get(3).draw();
	}
	
	public void draw() {
		
	}
	
	public Circle newCircle(Circle a, Circle b) {
		Circle result;
		PVector center = new PVector();
		float anglecompensation = degrees(PVector.sub(a.center, b.center).heading());
		float radius = (a.radius + b.radius) / 2;
		float angle = acos((pow(a.radius + b.radius,2) - pow(b.radius + radius,2) + pow(a.radius + radius,2))/(2*(a.radius + b.radius)*(a.radius + radius))) + PVector.sub(a.center, b.center).heading();
		center.x = -(radius + a.radius) * cos(angle) + a.center.x;
		center.y = -(radius + a.radius) * sin(angle) + a.center.y;
		result = new Circle(this,center,radius);
		if (result.testOverlap(circles.toArray(new Circle[circles.size()]))) {
			float out1 = degrees((HALF_PI - PVector.sub(a.center, b.center).heading()) * 2);
			angle += (HALF_PI - PVector.sub(a.center, b.center).heading()) * 2;
			anglecompensation = degrees(angle);
			result.center.x = -(radius + a.radius) * cos(angle) + a.center.x;
			result.center.y = -(radius + a.radius) * sin(angle) + a.center.y;
		}
		return result;
	}
}
