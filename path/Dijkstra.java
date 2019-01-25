package path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

	private List<Node> nodes;
	private List<Node> shortest = new ArrayList<Node>(), seen = new ArrayList<Node>();

	public Dijkstra(List<Node> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}
	
	public void findShortestPath()  {
		Node c = null;
		Node end = null;
		double distance;
		while (end == null) {
			Collections.sort(nodes);
			c = nodes.get(0);
			if (!c.isWall()) {
				c.check();
				seen.add(c);
				nodes.remove(c);
				Collections.sort(seen);
				for (Node current : seen) {
					if (current.isEnd()) {
						end = current;
						break;
					}
					for (Node n : current.getNeightbors()) {
						if (!n.isWall()) {
							n.setViewed(true);
							if (!n.hasBeenChecked()) {
								distance = dist(current, n)+current.getDistance();
								if (n.getDistance()>distance) {
									n.setDistance(distance);
									n.setPrevious(current);
								}
							}
							n.setViewed(false);
						}
					}
				}
			} else {
				nodes.remove(c);
			}
		}
		while (true) {
			shortest.add(end);
			end.setPath(true);
			end = end.getPrevious();
			if (end == null) break;
		}
	}

	private double dist(Node n1, Node n2) {
		return Math.sqrt((Math.abs(n1.getX() - n2.getX())) + (Math.abs(n1.getY() - n2.getY())));
	}

	public List<Node> getShortest() {
		return shortest;
	}
}
