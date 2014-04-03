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
		super( "Janela: " + contadorJanela, true, true, true, true );
		id = contadorJanela;
		contadorJanela++;
		setBounds(100, 100, 430, 260);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		imagem = img;
		panel = new ImagePanel( img );
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	public BufferedImage getImage() {
		return imagem;
	}
	
}
