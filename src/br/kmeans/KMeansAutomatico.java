package br.kmeans;

import java.awt.image.BufferedImage;
import java.util.Random;

public class KMeansAutomatico extends PixelManager {

	BufferedImage _outImg;
	int _k = 0;
	PixelKmeans _matriz[][];
	PixelKmeans _centroides [];
	
	
	public KMeansAutomatico(BufferedImage i) {
		super(i);
		int w = _img.getWidth();
		int h = _img.getHeight();
		

		_outImg = new BufferedImage( w, h, _img.getType() );
		
		_matriz = new PixelKmeans[ w ][ h ];
	}

	public void setK( int novo ) {
		_k = novo;
		
		_centroides = new PixelKmeans [_k];
	}
	
	public void geraCentroides() {
		Random rand = new Random( System.currentTimeMillis() );
		int totX = _img.getWidth();
		int totY = _img.getHeight();
		int rx, ry;
		
		// gera k centróides
		int x = 0;
		for ( x=0; x < _k; x++ ) {
			rx = rand.nextInt( totX ) + 1;
			ry = rand.nextInt( totY ) + 1;
			
			PixelKmeans tmp = new PixelKmeans( _pix );
			tmp._centroide = tmp;
			tmp._distancia = 0;
			tmp._k = x;
			_centroides [x] = tmp;
			_matriz [rx][ry] = _centroides [x];
		}
	}
	
	public void execute() {
		geraCentroides();
		super.execute();
		
		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				PixelKmeans atual = _matriz[x][y];
				
				double dist = -1;
				for( int i=0; i<_k; i++ ) {
					PixelKmeans c = _centroides[ i ];

					double dtmp = 0;
					for( int cor=0; cor<3; cor++ ) {
						int cor1 = c._rgb[ cor ];
						int cor2 = atual._rgb[ cor ];
						int dif = cor1 - cor2;
						dtmp += Math.pow( dif, 2 );
					}

					if( dtmp < dist ){
						dist = dtmp;
						atual._centroide = c;
						atual._distancia = (int)dist;
						atual._k = i;
					}
				}
			}
		}
		
	}
	
	public void pixelLoop(int x, int y) {
		if( _matriz[x][y] != null )
			return;

		PixelKmeans tmp = new PixelKmeans( _pix );
		_matriz[x][y] = tmp;
	}

	public BufferedImage getImage() {
		return _outImg;
	}

}
