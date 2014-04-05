package br.kmeans;

import java.awt.image.BufferedImage;
import java.util.Random;

public class KMeansAutomatico extends PixelManager {

	BufferedImage _outImg;
	int _k = 0;
	PixelKmeans _matriz[][];
	PixelKmeans _centroides [];
	
	static int NCARACT = 3; // R , G , B
	
	// soma de cada caracteristica
	double _caracSum[][];
	int _caracElements[];
	
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
		_caracSum = new double[_k][NCARACT];
		_caracElements = new int[_k];
	}
	
	public void geraCentroides() {
		Random rand = new Random( System.currentTimeMillis() );
		int totX = _img.getWidth();
		int totY = _img.getHeight();
		int rx, ry;
		
		// gera k centróides
		
		for ( int i=0; i<_k; i++ ) {
			rx = rand.nextInt( totX ) + 1;
			ry = rand.nextInt( totY ) + 1;
		
			PixelKmeans tmp = _matriz[ rx ][ ry ];
			_centroides[ i ] = tmp;
			tmp._k = i;
			tmp._distancia = 0;
			tmp._centroide = tmp;
		}
	}

	public int getDistance( PixelKmeans p1, PixelKmeans p2 ) {

		int dif = 0;
		for( int cor=0; cor<3; cor++ ) {
			int cor1 = p1._rgb[ cor ];
			int cor2 = p2._rgb[ cor ];
			dif += Math.abs( cor1 - cor2 );
		}
		return dif;
	}
	
	public void calculeTodasDistancias() {
		PixelKmeans atual;
		int dist = 0;
		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				atual = _matriz[x][y];
				
				for( int i=0; i<_k; i++ ) {
					// pega cada um dos centroides...
					PixelKmeans c = _centroides[ i ];

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
	
	public void rodaKMeans() {
		/* aqui tem q somar todos R de um mesmo grupo e dividir pelo número 
		 * de elementos
		 */
		PixelKmeans atual;
		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				atual = _matriz[x][y];
				_caracElements[ atual._k ]++;
				for( int cor=0; cor<NCARACT; cor++ ) {
					_caracSum[ atual._k ][ cor ] = atual._rgb[ cor ];
				}
			}
		}
		for( int cor=0; cor<NCARACT; cor++ ) {
			parei aqui....
			deixei com erro de proposito
			apagar estas linhas e continuar
			AQUI PRECISA ACHAR A MÉDIA DE: R, G, e B
			E VERIFICAR SE EXISTE ALGUM PONTO COM ESSES R, G, B
			caso não exista, refazer o calculo das distancias com base nele.
			será que pode um CENTRÓIDE conter R, G e B, que não é um ponto válido??
		}
		
	}
	
	public void execute() {
		// carrega todos pixels em estruturas...
		super.execute();
		geraCentroides();

		calculeTodasDistancias();
		
		rodaKMeans();
		
	}
	
	public void pixelLoop(int x, int y) {
		PixelKmeans tmp;
		if( _matriz[x][y] != null )
			return;

		tmp = new PixelKmeans( _pix, x, y );
		_matriz[x][y] = tmp;
	}

	public BufferedImage getImage() {
		return _outImg;
	}

}
