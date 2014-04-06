package br.kmeans;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeansAutomatico extends PixelManager {

	List<PixelKmeans[]> _savedCentroides = new ArrayList< PixelKmeans[] >();
	
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
	
	public PixelKmeans[] geraNovosCentroides() {
		PixelKmeans[] arrayCentroides = new PixelKmeans[ _k ];
		
		// para cada centroide, pega o valor da media
		for( int k=0; k<_k; k++ ) {
			for( int cor=0; cor<NCARACT; cor++ ) {
				double soma = _caracSum[ k ][ cor ];
				int elementos = _caracElements[ k ];
				double media =  (double)soma/elementos;
				_caracSum[ k ][ cor ] = media;
			}
			PixelKmeans novoCentroide = new PixelKmeans( _caracSum[ k ], -1, -1 );
			arrayCentroides[ k ] = novoCentroide;
		}
		
		return arrayCentroides;
	}
	
	public PixelKmeans[] geraNovosCentroides__OLDDD() {
		PixelKmeans[] osNovos = new PixelKmeans[ _k ];

		for ( int i=0; i<_k; i++ ) {
			PixelKmeans tmp = new PixelKmeans( _centroides[i]._rgb, -1, -1 );
			osNovos[ i ] = tmp;
			tmp._k = i;
			tmp._distancia = 0;
			tmp._centroide = tmp;
		}
		return osNovos;
	}
	
	
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
	
	public boolean fimExecucao() {
		
		if( _savedCentroides.isEmpty() )
			return false;

		PixelKmeans[] olds = _savedCentroides.get( _savedCentroides.size() - 1 );
		for( int k=0; k<_k; k++ ) {
			float somaAtual = 0;
			float somaOld = 0;
			for( int cor=0; cor<NCARACT; cor++ ) {
				somaAtual += _centroides[ k ]._rgb[ cor ];
				somaOld += olds[ k ]._rgb[ cor ];
			}
			somaAtual = somaAtual*256;
			somaOld = somaOld*256;
			float diff = Math.abs( somaAtual - somaOld );

			// se algum dos centroides tiver uma diferenca de XX ainda não terminou
			if( diff > 3 )
				return false;
		}
		
		return true;
	}
	
	
	public void init() {
		// carrega todos pixels em estruturas...
		super.percorraTodosPixels();
		geraCentroides();
	}
	
	public void execute() {
		
		calculeTodasDistancias();
		someDistancias();
		PixelKmeans[] novosCentroides = geraNovosCentroides();

		// salva os centroides atuais em uma lista para futuramente comparar
		_savedCentroides.add( _centroides );
		// os centroides oficiais passam a ser os novos, para FAZER TUDO DE NOVO!!!
		_centroides = novosCentroides;

		// TODO: FALTA VERIFICAR CONDICAO DE PARADA
		
		geraImagem();
	}
	
	public void geraImagem() {
		WritableRaster outRaster = _outImg.getRaster();
		Raster r = _img.getData();
		int[] umPixel = new int[ r.getNumBands() ];
		umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				PixelKmeans atual = _matriz[x][y];
				for( int cor=0; cor<NCARACT; cor++ ){
					// para cada pixel, pegar a cor do centroide...
					int valorCor = (int)( atual._centroide._rgb[ cor ] * 256);
					umPixel[ cor ] = valorCor;
				}
				//System.arraycopy( atual._rgb, 0, umPixel, 0, NCARACT );
				outRaster.setPixel( x,  y,  umPixel );
			}
		}
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
