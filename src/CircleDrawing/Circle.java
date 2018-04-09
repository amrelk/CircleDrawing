package CircleDrawing;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Circle {
	PApplet p;
	PVector center;
	float radius;
	ArrayList<Circle> adjacents = new ArrayList<Circle>();
	
	public Circle(PApplet _p, PVector _center, float _radius) {
		p = _p;
		center = _center;
		radius = _radius;
	}
	
	public void draw() {
		p.ellipse(center.x, center.y, radius*2, radius*2);
	}
	
	public void findAdjacents(Circle[] circles) {
		for (int i = 0; i < circles.length; i++) {
			if (PVector.sub(center, circles[i].center).mag() == radius + circles[i].radius) {
				adjacents.add(circles[i]);
			}
		}
	}
	
	public boolean testOverlap(Circle a, Circle[] circles) {
		for (int i = 0; i < circles.length; i++) {
			if (PVector.sub(a.center, circles[i].center).mag() < a.radius + circles[i].radius) {
				return true;
			}
		}
		return false;
	}
	
	public boolean testOverlap(Circle[] circles) {
		for (int i = 0; i < circles.length; i++) {
			if (PVector.sub(center, circles[i].center).mag() < radius + circles[i].radius) {
				return true;
			}
		}
		return false;
	}
	
	
}
