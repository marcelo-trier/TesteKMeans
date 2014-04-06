package br.kmeans.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements MouseListener {

	BufferedImage imagem = null;
	
	public ImagePanel( BufferedImage img ) {
		super();
		imagem = img;
		this.addMouseListener( this );
	}
	
    @Override
    protected void paintComponent(Graphics gr ) {
    	super.paintComponent( gr );
    	
    	Graphics2D g = ( Graphics2D )gr;

    	if( imagem != null )
    	{
    		g.drawImage( imagem, 0, 0, null );
    	}
    }

    public void setPoint( int x, int y ) {
	    Graphics2D g = imagem.createGraphics();
	    g.setColor(Color.RED);
	    g.fillOval(x, y, 10, 10 );
	    this.repaint();
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		setPoint( x, y );
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
