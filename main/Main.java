package main;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import parse.ImageParser;
import path.Dijkstra;
import path.Node;

/* To-do list:
 * Optimize the code
 * Make a JFrame to display the solved and unsolved mazes
 */

public class Main {
	public static void main(String[] args) {
		if (args.length >= 2) new Main(args[0], args[1]);
		else JOptionPane.showMessageDialog(null, "Usage:\n\tjava -jar *.jar <file path> <file name>");
	}
	
	private String filepath, filename;
	
	private ArrayList<Node> nodes;
	private ArrayList<Node> shortestPath;
	
	private ImageParser ip;
	private int[][] pixels;
	
	private Dijkstra d;
	
	public Main(String filepath, String filename) {
		this.filepath = filepath;
		this.filename = filename;
		init();
	}
	
	private void init() {
		this.ip = new ImageParser(filename, filepath);
		this.ip.parseImage();
		this.pixels = ip.getPixels();
		generateNodes();
		this.d = new Dijkstra(this.nodes);
		d.findShortestPath();
		this.shortestPath = (ArrayList<Node>) d.getShortest();
		this.ip.overlayPath(shortestPath);
	}
	
	private void generateNodes() {
		ArrayList<Node> newNodes = new ArrayList<Node>();
		int rows = pixels.length;
		int cols = pixels[rows-1].length;
		Node[][] tempNodes = new Node[rows][cols];
		
		for (int r=0;r<pixels.length;r++) {
			for (int c=0;c<pixels[r].length;c++) {
				tempNodes[r][c] = new Node(c, r);
				if (pixels[r][c]==1) {
					tempNodes[r][c].setWall(true);
				} else if (pixels[r][c]==2) {
					tempNodes[r][c].setStart(true);
					tempNodes[r][c].setDistance(0);
				} else if (pixels[r][c]==3) {
					tempNodes[r][c].setEnd(true);
				}
			}
		}
		for (int r=0;r<tempNodes.length;r++) {
			for (int c=0;c<tempNodes[r].length;c++) {
				if (c-1>=0) tempNodes[r][c].addNeighbor(tempNodes[r][c-1]);
				if (c+1<tempNodes[r].length) tempNodes[r][c].addNeighbor(tempNodes[r][c+1]);
				if (r-1>=0) tempNodes[r][c].addNeighbor(tempNodes[r-1][c]);
				if (r+1<tempNodes.length) tempNodes[r][c].addNeighbor(tempNodes[r+1][c]);
				if (!tempNodes[r][c].isWall())newNodes.add(tempNodes[r][c]);
			}
		}
		
		this.nodes = new ArrayList<Node>(newNodes);
	}
}
