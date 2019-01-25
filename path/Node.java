package path;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
	
	private int x, y;
	private double distance;
	private boolean checked, end, start, path, beingViewed, wall;
	
	private List<Node> neighbors = new ArrayList<Node>();
	private Node previous;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.checked = false;
		this.end = false;
		this.start = false;
		this.distance = Double.POSITIVE_INFINITY;
		this.previous = null;
	}
	
	public void setWall(boolean wall) {
		if (!end && !start) this.wall = wall;
		if (checked) checked = false;
	}
	public boolean isWall() {
		return wall;
	}
	public void check() {
		checked = true;
	}
	public boolean hasBeenChecked() {
		return checked;
	}
	public boolean isBeingViewed() {
		return beingViewed;
	}
	public void setViewed(boolean beingViewed) {
		this.beingViewed = beingViewed;
	}
	public boolean isPartOfPath() {
		return path;
	}
	public void setPath(boolean path) {
		this.path = path;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
		if (start && end) end = false;
		if (start && wall) wall = false;
	}
	public boolean isEnd() {
		return end;
	}
	public void setEnd(boolean end) {
		this.end = end;
		if (end && start) start = false;
		if (end && wall) wall = false;
	}	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public void addNeighbor(Node n) {
		neighbors.add(n);
	}
	public List<Node> getNeightbors() {
		return neighbors;
	}
	public Node getPrevious() {
		return previous;
	}
	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public int compareTo(Node n) {
		if (n.getDistance()>this.getDistance()) {
			return -1;
		} else if (n.getDistance()<this.getDistance()) {
			return 1;
		} else {
			return 0;
		}
	}
	public String toString() {
		return ""+distance;
	}
}
