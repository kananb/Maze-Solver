package parse;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import path.Node;

public class ImageParser {
	
	private String filename, filepath;
	private File file;
	private BufferedImage image;
		
	private int[][] pixels;
	
	public ImageParser(String filename, String filepath) {
		this.filename = filename;
		this.filepath = filepath;
		this.file = new File(this.filepath, this.filename);
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseImage() {
		if (image != null) {
			boolean startFound = false, endFound = false;
			Color c;
			pixels = new int[image.getHeight()][image.getWidth()];
			for (int y=0;y<image.getHeight();y++) {
				for (int x=0;x<image.getWidth();x++) {
					c = new Color(image.getRGB(x, y));
					//if (c.getRed() != c.getGreen() && c.getGreen() != c.getBlue() && c.getBlue() != c.getRed()) System.out.printf("(%d, %d, %d)\n", c.getRed(), c.getGreen(), c.getBlue());
					pixels[y][x] = (c.getRed()+c.getGreen()+c.getBlue()<50) ? 1 : 0;
					if (c.getRed()>200 && c.getBlue()<200 && c.getGreen()<200 && !startFound) {
						pixels[y][x] = 2;
						//startFound = true;
					} else if (c.getBlue()>200 && c.getRed()<200 && c.getGreen()<200 && !endFound) {
						pixels[y][x] = 3;
						//endFound = true;
					}
				}
			}
		}
	}
	
	public void overlayPath(ArrayList<Node> path) {
		for (Node n : path) {
			if (!(n.isStart() || n.isEnd())) image.setRGB(n.getX(), n.getY(), new Color(200, 40, 180).getRGB());
		}
		try {
			ImageIO.write(image, "png", new File(filepath, filename.split("\\.")[0]+"-solved.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int[][] getPixels() {
		return pixels;
	}

	public void setPixels(int[][] pixels) {
		this.pixels = pixels;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (int r=0;r<pixels.length;r++) {
			for (int c=0;c<pixels[r].length;c++) {
				out.append(pixels[r][c]+" ");
			}
			out.append("\n");
		}
		return out.toString();
	}
}
