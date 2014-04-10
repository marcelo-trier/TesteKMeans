package br.kmeans;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class KMeans1Pto extends PixelManager {

	int _k = 0;
	Point _pto;
	PixelKmeans[] _centroide = { null, null };
	PixelKmeans _matriz[][];
	static final int NCARACT = 3; // R , G , B
	static final float THRESHOLD = 0.2f; // porcentagem do peso da caracteristica! vixi!
	float[] _thresholdValues = new float[ NCARACT ];
	
	BufferedImage _outImg;
	
	public KMeans1Pto( BufferedImage i, int x, int y ) {
		super( i );
		_pto = new Point( x, y );
		_k = 2;
		
		_outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
		_matriz = new PixelKmeans[ _img.getWidth() ][ _img.getHeight() ];
	}
	
	public void pixelLoop(int x, int y) {
		PixelKmeans tmp;
		tmp = new PixelKmeans( _pix, x, y );
		_matriz[x][y] = tmp;		
	}

	public void iniciaCentroide() {
		_centroide[0] = _matriz[ _pto.x ][ _pto.y ];
		_centroide[0]._distancia = 0;
		_centroide[0]._centroide = _centroide[0];
		_centroide[0]._k = 0;
		_thresholdValues[ 0 ] = _centroide[0]._rgb[0] * THRESHOLD;
		_thresholdValues[ 1 ] = _centroide[0]._rgb[1] * THRESHOLD;
		_thresholdValues[ 2 ] = _centroide[0]._rgb[2] * THRESHOLD;
		
		
		_centroide[1] = new PixelKmeans( -1, -1 );
		_centroide[1]._distancia = 999999;
		_centroide[1]._centroide = _centroide[1];
		_centroide[1]._k = 1;
		int[] cores = { 0, 0, 0 }; // sim, tudo preto para o outro centroide... =)
		_centroide[1].setPixels( cores ); 
	}
	
	public void init() {
		PixelKmeans.normalizePixels = false;
		super.percorraTodosPixels();
		iniciaCentroide();
	}

/*
	public void someDistancias() {
		// aqui tem q somar todos R de um mesmo grupo 

		PixelKmeans atual;
		Arrays.fill( _caracElements, 0 );
		for( int k=0; k<_centroides.length; k++ ) {
			Arrays.fill( _caracSum[ k ], 0 );
		}

		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				atual = _matriz[x][y];
				_caracElements[ atual._k ]++;
				for( int cor=0; cor<NCARACT; cor++ ) {
					_caracSum[ atual._k ][ cor ] += atual._rgb[ cor ];
				}
			}
		}
	}
*/
	
	public void execute() {
		calculeTodasDistancias();
		//someDistancias();
	}

	public BufferedImage geraImagem() {
		WritableRaster outRaster = _outImg.getRaster();
		Raster r = _img.getData();
		int[] umPixel = new int[ r.getNumBands() ];
		umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				PixelKmeans atual = _matriz[x][y];
				for( int cor=0; cor<NCARACT; cor++ ){
					// para cada pixel, pegar a cor do centroide...
					int valorCor = (int)( atual._centroide._rgb[ cor ] );
					umPixel[ cor ] = valorCor;
				}
				//System.arraycopy( atual._rgb, 0, umPixel, 0, NCARACT );
				outRaster.setPixel( x,  y,  umPixel );
			}
		}
		return _outImg;
	}

	// distancia do ponto p2 em relacao ao centroide[ 0 ]
	public boolean distanceThreshold( PixelKmeans p2 ) {

		int dif = 0;
		for( int cor=0; cor<NCARACT; cor++ ) {
			
			float cor1 = _centroide[ 0 ]._rgb[ cor ];
			float cor2 = p2._rgb[ cor ];
			dif = ( int )Math.abs( cor1 - cor2 );
			if( dif > _thresholdValues[cor] ) // se a diferenca for maior que o limiar pretendido... faz o q?
				return false;
		}
		return true;
	}	
	
	public void calculeTodasDistancias() {
		PixelKmeans atual;

		float dist = 0;
		for (int y = 0; y < _img.getHeight(); y++) {
			for (int x = 0; x < _img.getWidth(); x++) {
				atual = _matriz[x][y];
				atual._distancia = 999; // resetando a distancia para ir pegando os novos centroides

				if (distanceThreshold(atual)) {
					atual._distancia = 1; // valor padrao
					atual._centroide = _centroide[0];
					atual._k = 0;
				} else {
					atual._distancia = 99; // valor padrao
					atual._centroide = _centroide[1];
					atual._k = 1;
				}
			}
		}
		
	}

	
	
}
