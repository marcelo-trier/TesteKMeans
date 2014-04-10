package br.kmeans.ui;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuComponent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImagePanel extends JPanel implements MouseListener {

	BufferedImage imagem = null;
	boolean _pontoMarcado = false;
	int pontoX = 0, pontoY = 0;

	public ImagePanel( BufferedImage img ) {
		super();
		imagem = img;
	}
	
	public void pegaPonto() {
		this.setCursor(Cursor.getPredefinedCursor( Cursor.CROSSHAIR_CURSOR ) );
		this.addMouseListener( this );
	}
	
	public boolean pontoMarcado() {
		return _pontoMarcado;
	}
	
	protected void paintComponent(Graphics gr ) {
    	super.paintComponent( gr );
    	
    	Graphics2D g = ( Graphics2D )gr;

    	if( imagem != null )
    	{
    		g.drawImage( imagem, 0, 0, null );
    	}
    }

    public void drawPoint( int x, int y ) {
	    Graphics2D g = imagem.createGraphics();
	    g.setColor(Color.RED);
	    g.fillOval(x, y, 10, 10 );
	    this.repaint();
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub		
		pontoX = e.getX();
		pontoY = e.getY();
		drawPoint( pontoX, pontoY );
		
		_pontoMarcado = true;
		
		this.setCursor(Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
		this.removeMouseListener( this );
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
}
