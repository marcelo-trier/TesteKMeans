package br.kmeans.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LeitorPontos {

	ArrayList<int[]> kPontos[] = new ArrayList[ 10 ];
	
	public void carreguePontos( File f, int k ) throws Exception {
		FileReader fr = new FileReader(f);
		BufferedReader lerArq = new BufferedReader(fr);
		String linha = "";
        // cada linha deve ser: x,y sem espaços...
        while( ( linha = lerArq.readLine() ) != null )
        {
            String divisao[] = linha.split(",");
            int x = Integer.parseInt( divisao[0] );
            int y = Integer.parseInt( divisao[1] );
            int[] p = { x, y };
            kPontos[ k ].add( p );
        }
		
		
	}

	
	public void carreguePontos( ) throws Exception {
        String base = "pontos%02d.txt";
        String linha;
        int x, y;
        
        
        for( int i=0; i<10; i++ ) {
        	ArrayList<int[]> pontos = new ArrayList<int[]>();
        	kPontos[ i ] = pontos;
        	String nome = String.format( base, i );
        	
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
        	
            // cada linha deve ser: x,y sem espaços...
            while( ( linha = lerArq.readLine() ) != null )
            {
                String divisao[] = linha.split(",");
                x = Integer.parseInt( divisao[0] );
                y = Integer.parseInt( divisao[1] );
                int[] p = { x, y };
                pontos.add( p );
            }
        }
        
        
        try {


        
        } catch( Exception ex ) {
            // deve fazer algo... ou não!
        }		
	}
	
}
