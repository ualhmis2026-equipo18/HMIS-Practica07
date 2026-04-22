package practicas.practica04;

import java.util.TreeMap;
import java.util.TreeSet;

import practicas.auxiliar.Graph;
import practicas.auxiliar.Par;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Network<Vertex extends Comparable<Vertex>> implements Graph<Vertex>, Iterable<Vertex> {

	private boolean directed; 
	
	protected HashMap<Vertex, HashMap<Vertex, Double>> adjacencyList;
	private TreeMap<Vertex, Par<Vertex, Double>> salidaDijkstra = null;
	private Vertex sourceOld = null;
	
	public Network(){
		this.directed = true;
		this.adjacencyList = new HashMap<>();
	}
	
  	public void setDirected(boolean esDirected) {
  		this.directed = esDirected;
  	}

  	public boolean isDirected() {
  		return this.directed;
  	}

  	@Override
  	public boolean isEmpty() {
    	return this.adjacencyList.isEmpty();
  	} 

  	@Override
  	public void clear() {
		this.adjacencyList.clear();
	}

  	@Override
  	public int numberOfVertices() {
    	return this.adjacencyList.size();
  	} 

  	@Override
  	public int numberOfEdges() {
  		int count = 0;
  		for (HashMap<Vertex, Double> mapValues : this.adjacencyList.values())
  			count += mapValues.size();
  		return count;
  	} 

  	@Override
  	public boolean containsVertex(Vertex vertex) {
    	return this.adjacencyList.containsKey(vertex);
  	} 
  	
  	@Override
  	public boolean containsEdge(Vertex v1, Vertex v2) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		return neighbors == null ? false : neighbors.containsKey(v2);
  	} 

  	@Override
  	public double getWeight (Vertex v1, Vertex v2) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		if (neighbors == null) return -1;
  		Double weight = neighbors.get(v2);
  		return weight == null ? -1 : weight;
   	} 

  	@Override
  	public Double setWeight (Vertex v1, Vertex v2, double w) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		if (neighbors == null) return -1.;
		return neighbors.put(v2, w);
	}

  	public boolean isAdjacent (Vertex v1, Vertex v2) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		return neighbors != null && neighbors.containsKey(v2);
	}

  	public boolean addVertex(Vertex vertex) {
        if (this.adjacencyList.containsKey(vertex)) return false;
        this.adjacencyList.put(vertex, new HashMap<>());
        return true;
  	} 

  	public boolean addEdge(Vertex v1, Vertex v2, double w) {
  		if (!containsVertex(v1) || !containsVertex(v2)) return false;
  		this.adjacencyList.get(v1).put(v2, w);
       	if (!this.directed) {
        	this.adjacencyList.get(v2).put(v1, w);
       	}
    	return true;
  	} 

  	public boolean removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) return false;

        for (HashMap<Vertex, Double> itMap: this.adjacencyList.values()) {
        	itMap.remove(vertex);
        } 
        this.adjacencyList.remove(vertex);
        return true;
   	} 

  	public boolean removeEdge (Vertex v1, Vertex v2) {
    	if (!containsEdge(v1,v2)) return false;

    	this.adjacencyList.get(v1).remove(v2);
    	
    	if (!this.directed) {
        	this.adjacencyList.get(v2).remove(v1);
    	}
    	
    	return true;
  	} 
  	
	@Override
  	public TreeSet<Vertex> vertexSet() {
    	return new TreeSet<>(this.adjacencyList.keySet());
  	}

  	public TreeSet<Vertex> getNeighbors(Vertex v) {
  		//2 líneas
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v);
  		return neighbors == null ? null : new TreeSet<Vertex>(neighbors.keySet());
  		}

  	@Override
  	public String toString() {
  		TreeMap<Vertex, TreeMap<Vertex, Double>> resultado = new TreeMap<>();
		for(Entry<Vertex, HashMap<Vertex, Double>> aux : this.adjacencyList.entrySet()) {
			resultado.put(aux.getKey(), new TreeMap<>(aux.getValue()));
		}
    	return resultado.toString();
  	} 
	
	private TreeMap<Vertex, Par<Vertex, Double>> Dijkstra(Vertex source, Vertex destination) {
  		final double INFINITY = Double.MAX_VALUE;
      	TreeMap<Vertex, Par<Vertex, Double>> DS = new TreeMap<>();    	
    	TreeSet<Vertex> V_minus_S = new TreeSet<>();
     	    	
    	for (Vertex v : this.adjacencyList.keySet()){
    		if (v.equals(source))
    			DS.put(source, new Par<>(source, 0.0));
    		else {
    			DS.put(v, new Par<>(isAdjacent(source,v) ? source: null, isAdjacent(source,v) ? getWeight(source, v) : INFINITY));
    			V_minus_S.add(v);
    		}
    			
    	}
    	
		while (!V_minus_S.isEmpty()) {
		    double minWeight = INFINITY;
		    Vertex from = null;
		    
		    for (Vertex v : V_minus_S){
		    	if (DS.get(v).getValue() < minWeight) {
		    		minWeight = DS.get(v).getValue();
		    		from = v;
		    	}
		    }
	    	if (from == null) break;
	    	 
			V_minus_S.remove(from);
				
		    for (Vertex v : V_minus_S){
		    	if (isAdjacent(from,v)){
		    		if (DS.get(from).getValue() + getWeight(from,v) < DS.get(v).getValue()){
		    			DS.put(v, new Par<>(from, DS.get(from).getValue() + getWeight(from,v)));
		    		}
		    	}
		    }
		}
		return DS;
	}
	
	public ArrayList<Par<Vertex, Double>> getDijkstra(Vertex source, Vertex destination) {
		if (source == null || destination == null) return null;
    	
    	if (source.equals(destination))	return null;
    	
    	for (Vertex v: this.adjacencyList.keySet()) {
    		if (source.equals(v)) source = v;
    		if (destination.equals((v))) destination = v;
    	}
    	
    	if (!source.equals(this.sourceOld)) {
    		this.salidaDijkstra = Dijkstra(source, destination);
    		this.sourceOld = source;
    	}
    	
    	if (this.salidaDijkstra.get(destination).getKey() == null) return null;
    	
    	ArrayList<Par<Vertex, Double>> path = new ArrayList<>();
    	Stack<Vertex> pila = new Stack<>();
		pila.push(destination);
		while (!pila.peek().equals(source)) {
			pila.push(salidaDijkstra.get(pila.peek()).getKey());
		}
		while (!pila.isEmpty()) {
			Vertex current = pila.pop();
			path.add(new Par<>(current, salidaDijkstra.get(current).getValue()));
		}
		return path;
	}
	
	
	
	
	public ArrayList<Vertex> toArrayDepthFirst(Vertex start) {
		if (!this.adjacencyList.containsKey(start)) return null;
		ArrayList<Vertex> resultado = new ArrayList<>();
		Stack<Vertex> stack = new Stack<>();
		HashMap<Vertex, Boolean> visited = new  HashMap<>();
		for (Vertex vertex : this.adjacencyList.keySet()) {
			visited.put(vertex, false);
		}
	
		stack.push(start);
		while (!stack.isEmpty()) {
			Vertex current = stack.pop();
			if (visited.get(current)) continue;
			visited.put(current, true);
			resultado.add(current);
			for (Vertex to : this.getNeighbors(current)) {
    			if (visited.get(to)) continue;
				stack.push(to);
			}
		}
		return resultado;
	}
	
	public ArrayList<Vertex> toArrayBreadthFirst(Vertex start) {
		if (!this.adjacencyList.containsKey(start)) return null;
		ArrayList<Vertex> resultado = new ArrayList<>();
		LinkedList<Vertex> queue = new LinkedList<>();
		TreeMap<Vertex, Boolean> visited = new TreeMap<>();
		for (Vertex vertex: this.adjacencyList.keySet()) {
			visited.put(vertex, false);
		}
		queue.add(start);
		while (!queue.isEmpty()) {
			Vertex current = queue.poll();
			if (visited.get(current)) continue;
			visited.put(current, true);
			resultado.add(current);
			for (Vertex to : new TreeSet<>(this.adjacencyList.get(current).keySet())) {
				if (visited.get(to)) continue;
				queue.add(to);
			}
		}
		return resultado;
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new TreeSet<>(this.adjacencyList.keySet()).iterator();
	}
 } 
