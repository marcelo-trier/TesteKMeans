package br.kmeans;

public class PixelKmeans {
	public static boolean normalizePixels = true;
	
	float[] _rgb;
	float _distancia = 999999999;
	int _k = -1;
	PixelKmeans _centroide;
	int _x, _y;

	public PixelKmeans( int[] pix, int x, int y ) {
		_rgb = new float[ 3 ];
		for( int i=0; i<3; i++ ) {
			int valor = pix[ i ];
			float f;
			if( normalizePixels )
				f = ( float )valor / 256;
			else
				f = ( float )valor;
			_rgb[i] = ( float )f; // rbg normalizadoooo
		}
		_x = x;
		_y = y;
	}
	
	public PixelKmeans( double[] pix, int x, int y ) {
		_rgb = new float[ 3 ];
		for( int i=0; i<_rgb.length; i++ )
			_rgb[ i ] = ( float )pix[ i ];
		_x = x;
		_y = y;
	}
	
	public PixelKmeans( float[] pix, int x, int y ) {
		_rgb = new float[ 3 ];
		for( int i=0; i<_rgb.length; i++ )
			_rgb[ i ] = pix[ i ];
		_x = x;
		_y = y;
	}
	
	public PixelKmeans( int x, int y ) {
		_x = x;
		_y = y;
		normalizePixels = false;
	}
	
	public void setPixels( int[] pix ) {
		_rgb = new float[ 3 ];
		for( int i=0; i<3; i++ ) {
			float valor = pix[ i ];
			if( normalizePixels ) // normalizar ou não?? eis a questão!
				valor = valor / 256;
			_rgb[i] = valor;
		}
	}
	
}
