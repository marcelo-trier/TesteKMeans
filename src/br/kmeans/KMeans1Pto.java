package br.kmeans;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class KMeans1Pto extends PixelManager {

	int _k = 0;
	Point _pto;
	PixelKmeans _centroide;
	PixelKmeans _matriz[][];
	
	BufferedImage _outImg;
	
	public KMeans1Pto( BufferedImage i, int x, int y ) {
		super( i );
		_pto = new Point( x, y );
	}
	
	public void pixelLoop(int x, int y) {
		PixelKmeans tmp;
		tmp = new PixelKmeans( _pix, x, y );
		_matriz[x][y] = tmp;		
	}

	public void iniciaCentroide() {
		_centroide = _matriz[ _pto.x ][ _pto.y ];
		_centroide._distancia = 0;
		_centroide._centroide = _centroide;
		_centroide._k = 0;
	}
	
	public void init() {
		super.percorraTodosPixels();
		iniciaCentroide();
	}

	public void execute() {
		
	}

	public void geraImagem() {
		// TODO Auto-generated method stub
		
	}

	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
