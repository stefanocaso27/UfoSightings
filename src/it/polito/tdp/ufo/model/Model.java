package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SimpleDirectedGraph<Stato, DefaultEdge> grafo;
	private SightingsDAO dao;
	private List<Stato> stati;
	
	public Model() {
		this.dao = new SightingsDAO();
		this.stati = new ArrayList<Stato>();
	}
	
	public List<Avvistamenti> tendina() {
		List<Avvistamenti> lista = dao.listTendina();
		
		return lista;
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleDirectedGraph<Stato, DefaultEdge>(DefaultEdge.class);
		this.stati = dao.getStati(anno);
		
		Graphs.addAllVertices(this.grafo, this.stati);
		
		for(Stato s1 : this.grafo.vertexSet()) {
			for(Stato s2 : this.grafo.vertexSet()) {
				if(!s1.equals(s2)) {
					if(dao.esisteArco(s1, s2, anno))
						this.grafo.addEdge(s1, s2);
				}
			}
		}
		
		System.out.println("Grafo creato!");
		System.out.println("# vertici: " + this.grafo.vertexSet().size());
		System.out.println("# archi: " + this.grafo.edgeSet().size());	
	}
	
	

}
