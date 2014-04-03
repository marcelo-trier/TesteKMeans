package br.kmeans;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public abstract class PixelManager {

	protected BufferedImage _img;
	protected Raster _raster;
	protected int[] _pix;
	
	public PixelManager( BufferedImage i ) {
		setImg( i );
	}

	public void setImg( BufferedImage novaImg ) {
		_img = novaImg;
		_raster = _img.getRaster();
		int numeroBandas = _raster.getNumBands();
		_pix = new int[ numeroBandas ];
	}
	
	
	public void execute() {
		int w = _img.getWidth();
		int h = _img.getHeight();

		int x, y;
		try {
		for( y=0; y<h; y++ ) {
			for( x=0; x<w; x++ ) {
				_pix = _raster.getPixel(x, y, _pix );
				pixelLoop( x, y );
			}
		}
		
		} catch( Exception ex ) {
			int aa = 0;
			aa++;
		}
	}
	
	public abstract void pixelLoop( int x, int y );
}
