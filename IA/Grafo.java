package IA;

import java.util.*;

public class Grafo {
    private final Map<Nodo, List<Arista>> adjacencia = new HashMap<>();

    // Agrega un nuevo nodo al grafo
    public void agregarNodo(Nodo nodo) {
        adjacencia.putIfAbsent(nodo, new ArrayList<>());
    }

    // Agrega una arista (conexión) entre dos nodos
    public void agregarArista(Nodo origen, Nodo destino, double costo) {
        // Asegura que el nodo de origen exista en el mapa
        adjacencia.putIfAbsent(origen, new ArrayList<>());
        
        // Agrega la conexión
        adjacencia.get(origen).add(new Arista(destino, costo));
    }
    
    // Obtiene los vecinos de un nodo
    public List<Arista> getVecinos(Nodo nodo) {
        return adjacencia.getOrDefault(nodo, Collections.emptyList());
    }
}