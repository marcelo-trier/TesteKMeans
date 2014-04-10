package br.kmeans;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class KMeansSupervisionado extends PixelManager {

	PixelKmeans _matriz[][];
	BufferedImage _outImg;
	static final int NCARAC = 3; // R G B
	static final int NCENTR = 4;
	static final int NPONTOS = 20; // pontos do supervisionado
	//static final int NPONTOS = 5; // pontos do supervisionado
	PixelKmeans _centroide[] = { null, null, null, null };
	
	int[][] apX = {
			{ 192, 278, 375, 530, 533, 533, 532, 534, 532, 534, 465, 281, 279, 368, 418, 477, 405, 405, 405, 405 },
			{ 2  ,  5 ,  4 ,  2 , 13 ,  8 ,  9 , 71 , 114, 116, 110, 138, 138, 148, 137, 153, 186, 184, 231, 310 },
			{ 33 ,  15, 45 , 136, 65 , 87 , 86 , 145, 173, 186, 207, 206, 225, 188, 81 , 123, 176, 213, 231, 213 },
			{ 312, 342, 366, 398, 257, 310, 375, 414, 353, 345, 433, 424, 139, 152, 201, 155, 208, 230, 277, 340 },
	};

	int[][] apY = {
			{ 8  , 8  , 9  , 34 , 54 , 75 , 97 , 124, 150, 176, 157, 119, 49 , 68 , 73 , 83 , 35 , 75 , 115, 145 },
			{ 2  , 5  , 52 , 118, 138, 185, 236, 18 , 144, 189, 233, 11 , 38 , 52 , 120, 165, 192, 227, 212, 206 },
			{ 246, 260, 278, 292, 314, 323, 292, 303, 306, 315, 323, 325, 329, 329, 320, 321, 328, 327, 115, 328 },
			{ 193, 187, 215, 216, 236, 231, 228, 237, 253, 256, 270, 290, 250, 253, 282, 270, 305, 318, 317, 326 },
	}; 
	/*
	int[][] apX = {
			{ 192, 278, 375, 530, 533, 533, 532, 534, 532, 534, 465, 281, 279, 368, 418, 477, 405, 405, 405, 405 },
			{ 192, 278, 375, 530, 533, 533, 532, 534, 532, 534, 465, 281, 279, 368, 418, 477, 405, 405, 405, 405 },
			{ 192, 278, 375, 530, 533, 533, 532, 534, 532, 534, 465, 281, 279, 368, 418, 477, 405, 405, 405, 405 },
			{ 192, 278, 375, 530, 533, 533, 532, 534, 532, 534, 465, 281, 279, 368, 418, 477, 405, 405, 405, 405 },
	};

	int[][] apY = {
			{ 8  , 8  , 9  , 34 , 54 , 75 , 97 , 124, 150, 176, 157, 119, 49 , 68 , 73 , 83 , 35 , 75 , 115, 145 },
			{ 8  , 8  , 9  , 34 , 54 , 75 , 97 , 124, 150, 176, 157, 119, 49 , 68 , 73 , 83 , 35 , 75 , 115, 145 },
			{ 8  , 8  , 9  , 34 , 54 , 75 , 97 , 124, 150, 176, 157, 119, 49 , 68 , 73 , 83 , 35 , 75 , 115, 145 },
			{ 8  , 8  , 9  , 34 , 54 , 75 , 97 , 124, 150, 176, 157, 119, 49 , 68 , 73 , 83 , 35 , 75 , 115, 145 },
	}; */
	
	//int[] ap0X = { 192, 278, 375, 530, 533, 533, 532, 534, 532, 534, 465, 281, 279, 368, 418, 477, 405, 405, 405, 405 };

	//int[] ap0Y = { 8  , 8  , 9  , 34 , 54 , 75 , 97 , 124, 150, 176, 157, 119, 49 , 68 , 73 , 83 , 35 , 75 , 115, 145 };
	
	
	public KMeansSupervisionado(BufferedImage i) {
		super(i);

		_outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
		_matriz = new PixelKmeans[ _img.getWidth() ][ _img.getHeight() ];
		PixelKmeans.normalizePixels = false;
	}

	public void pixelLoop(int x, int y) {
		PixelKmeans tmp;
		tmp = new PixelKmeans( _pix, x, y );
		_matriz[x][y] = tmp;
	}

	public void criaCentroides() {
		for( int k=0; k<NCENTR; k++ ) {
			int[] cores = { 0, 0, 0 }; // media das cores dos seus pontos

			for( int pto=0; pto<apX[k].length; pto++ ) {
				PixelKmeans atual = _matriz[ apX[k][pto] ][ apY[k][pto] ];
				cores[ 0 ] += atual._rgb[ 0 ];
				cores[ 1 ] += atual._rgb[ 1 ];
				cores[ 2 ] += atual._rgb[ 2 ];
			}
			cores[0] = ( int )( cores[0] / apX[k].length );
			cores[1] = ( int )( cores[1] / apX[k].length );
			cores[2] = ( int )( cores[2] / apX[k].length );

			_centroide[ k ] = new PixelKmeans( cores, -1, -1 );
			_centroide[ k ]._distancia = 0;
			_centroide[ k ]._k = k;
		}
	}
	
	public void init() {
		percorraTodosPixels();
		criaCentroides();
	}

	
	public float getDistance( PixelKmeans p1, PixelKmeans p2 ) {

		float dif = 0;
		for( int cor=0; cor<3; cor++ ) {
			float cor1 = p1._rgb[ cor ];
			float cor2 = p2._rgb[ cor ];
			dif += Math.abs( cor1 - cor2 );
		}
		return dif;
	}
	
	
	public void calculeTodasDistancias() {
		PixelKmeans atual;
		float dist = 0;
		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				atual = _matriz[x][y];
				atual._distancia = 999; // resetando a distancia para ir pegando os novos centroides
				
				for( int i=0; i<NCENTR; i++ ) {
					// pega cada um dos centroides...
					PixelKmeans c = _centroide[ i ];

					// calcula a distancia baseado em uma formula da literatura...
					dist = getDistance( c, atual );

					if( dist < atual._distancia ) {
						atual._distancia = dist;
						atual._centroide = c;
						atual._k = i;
					}
				}
			}
		}
		
	}
	
	
	public void execute() {
		calculeTodasDistancias();
	}

	
	public BufferedImage geraImagem() {
		WritableRaster outRaster = _outImg.getRaster();
		Raster r = _img.getData();
		int[] umPixel = new int[ r.getNumBands() ];
		umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				PixelKmeans atual = _matriz[x][y];
				for( int cor=0; cor<NCARAC; cor++ ){
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
	
}
