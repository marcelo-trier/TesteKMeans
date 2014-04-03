package br.kmeans;

import java.awt.image.BufferedImage;

public class KMeansAutomatico extends PixelManager {

	BufferedImage _outImg;
	
	public KMeansAutomatico(BufferedImage i) {
		super(i);
		_outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
	}

	public void pixelLoop(int x, int y) {
		
	}

	public BufferedImage getImage() {
		return _outImg;
	}
}
