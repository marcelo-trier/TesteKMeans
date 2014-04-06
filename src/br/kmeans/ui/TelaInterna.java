package br.kmeans.ui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;


public class TelaInterna extends JInternalFrame {
	static int contadorJanela = 0;
	public int id = 0;
	private ImagePanel panel;
	public BufferedImage imagem = null;
	
	
	public TelaInterna(BufferedImage img ) {
		super( "", true, true, true, true );
		initTela( "", img);
		/*super( "Janela: " + contadorJanela, true, true, true, true );
		id = contadorJanela;
		contadorJanela++;
		setBounds(100, 100, 430, 260);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		imagem = img;
		panel = new ImagePanel( img );
		getContentPane().add(panel, BorderLayout.CENTER); */
	}

	public void initTela( String titulo, BufferedImage img ) {
		String oTitulo = "[JanelaId: " + contadorJanela + "] :: ";
		oTitulo += titulo;
		this.setTitle( oTitulo );
		id = contadorJanela;
		contadorJanela++;
		setBounds(100, 100, 430, 260);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		imagem = img;
		panel = new ImagePanel( img );
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	public TelaInterna( String titulo, BufferedImage img ) {
		super( "", true, true, true, true );
		initTela( titulo, img);
	}
	
	public BufferedImage getImage() {
		return imagem;
	}
	
}
