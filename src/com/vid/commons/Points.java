package com.vid.commons;

public class Points {
	int[] xPoints;
	int[] yPoints;
	int size;
	int index;

	public Points(int size) {
		this.size = size;
		xPoints = new int[size];
		yPoints = new int[size];
	}

	public void addPoint(int x, int y) {
		xPoints[index] = x;
		yPoints[index] = y;
		index++;
	}

	public int[] getxPoints() {
		return xPoints;
	}

	public int[] getyPoints() {
		return yPoints;
	}

	public int getSize() {
		return size;
	}

}
