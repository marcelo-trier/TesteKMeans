package br.kmeans;

public class PixelKmeans {
	int[] _rgb;
	int _k;
	PixelKmeans _centroide;
	int _distancia;
	int _x, _y;
	public PixelKmeans( int[] pix, int x, int y ) {
		_rgb = new int[ 3 ];
		for( int i=0; i<3; i++ )
			_rgb[i] = pix[i];
		_x = _y = 0;
		_distancia = 9999999; // inicio da distancia o mais longe possivel
	}
	
	
}
