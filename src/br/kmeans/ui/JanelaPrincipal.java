package br.kmeans.ui;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import br.kmeans.KMeans1Pto;
import br.kmeans.KMeansAutomatico;


public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;

	public void click1Pto() {
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		ti.registraPonto();
/*
		KMeans1Pto k1p = new KMeans1Pto( x, y );
		if( !k1p.getPoint() )
			return;
		k1p.init();
		k1p.execute();
		*/
	}
	
	public void click1PtoExecute() {

		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		long inicio = System.currentTimeMillis();
		KMeans1Pto k1p = new KMeans1Pto( getImage(), ti.pontoX(), ti.pontoY() );
		k1p.init();
		k1p.execute();
		BufferedImage out = k1p.geraImagem();

		long fim = System.currentTimeMillis();
		float tempo = fim - inicio;
		tempo = ( float )tempo / 1000;
		String msg = String.format("demora: %.02f seg", tempo );
		
		mostraImagem( msg, out );
	}
	
	
	public void clickKmeansAutomatico() {
		long inicio = System.currentTimeMillis();
		KMeansAutomatico kma = new KMeansAutomatico( getImage() );
		kma.setK( 3 );
		kma.init();
		int conta = 0;
		while( ! kma.fimExecucao() ) {
			conta++;
			kma.execute();
		}
		long fim = System.currentTimeMillis();
		float tempo = fim - inicio;
		tempo = ( float )tempo / 1000;
		String msg = "demora: " + ( fim - inicio ) + "ms  |  ";
		msg = String.format("demora: %.02f seg  |  interações: %d", tempo, conta );
		
		mostraImagem( msg, kma.getImage() );
	}
	
	public void mostraImagem( String titulo, BufferedImage imgOut ) {
		TelaInterna interno = new TelaInterna( titulo, imgOut );
		contentPane.add( interno );
		interno.setVisible( true );	
		
	}
	
	public void mostraImagem( BufferedImage imgOut ) {
		mostraImagem( "", imgOut );
	}

	public BufferedImage getImage() {
		// pega a janela ativa...
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		BufferedImage img;
		img = ti.getImage();
		return img;
	}

	public void clickSave() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
		File umDir = new File( System.getProperty( "user.dir" ) );
		fileChooser.setCurrentDirectory( umDir );
		if( fileChooser.showSaveDialog( this ) != JFileChooser.APPROVE_OPTION ) {
			return;
		}
		File salvar = fileChooser.getSelectedFile();
		//ImageIO.w
		BufferedImage img = getImage();
		ImageIO.write( img, "bmp", salvar );
	}

	
	public void clickOnLoad() throws Exception {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType( JFileChooser.OPEN_DIALOG );
		File umDir = new File( System.getProperty( "user.dir" ) );
		fileChooser.setCurrentDirectory( umDir );
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();
		BufferedImage imagem = ImageIO.read( file );
		TelaInterna interno = new TelaInterna( imagem );
		contentPane.add( interno );
		interno.setVisible( true );
	}
	
	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 399);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Load...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickOnLoad();
				} catch( Exception ex ) {
					
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickSave();
				} catch (Exception ex ) {
					
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenu mnImagens = new JMenu("Imagens");
		menuBar.add(mnImagens);
		
		JMenu mnProcessamento = new JMenu("Processamento");
		menuBar.add(mnProcessamento);
		
		JMenuItem mntmKmeansAutomtico = new JMenuItem("KMeans Automático");
		mntmKmeansAutomtico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickKmeansAutomatico();
			}
		});
		mnProcessamento.add(mntmKmeansAutomtico);
		
		JMenu mnKmeanspto = new JMenu("KMeans 1Pto");
		mnProcessamento.add(mnKmeanspto);
		
		JMenuItem mntmConfig = new JMenuItem("Marque ponto...");
		mntmConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click1Pto();
			}
		});
		mnKmeanspto.add(mntmConfig);
		
		JMenuItem mntmRoda = new JMenuItem("Roda...");
		mntmRoda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click1PtoExecute();
			}
		});
		mnKmeanspto.add(mntmRoda);
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}

