package IA;
public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // Crear Nodos
        Nodo a = new Nodo("A", 1, 3);
        Nodo b = new Nodo("B", 2, 1);
        Nodo c = new Nodo("C", 2, 3);
        Nodo d = new Nodo("D", 3, 1);
        Nodo e = new Nodo("E", 2, 2);
        Nodo f = new Nodo("F", 1, 1);

        // Agregar Nodos al grafo
        grafo.agregarNodo(a);
        grafo.agregarNodo(b);
        grafo.agregarNodo(c);
        grafo.agregarNodo(d);
        grafo.agregarNodo(e);
        grafo.agregarNodo(f);

        // Agregar Aristas con costos
        grafo.agregarArista(a, b, 4);
        grafo.agregarArista(a, c, 2);
        grafo.agregarArista(b, d, 5);
        grafo.agregarArista(c, b, 1);
        grafo.agregarArista(c, e, 8);
        grafo.agregarArista(d, f, 3);
        grafo.agregarArista(e, d, 2);
        grafo.agregarArista(e, f, 5);

        // Definir inicio y objetivo
        Nodo nodoInicio = a;
        Nodo nodoObjetivo = f;

        System.out.println("Buscando camino de " + nodoInicio.nombre + " a " + nodoObjetivo.nombre);
        
        // Ejecutar los algoritmos
        BusquedaBFS.buscar(grafo, nodoInicio, nodoObjetivo);
        BusquedaDFS.buscar(grafo, nodoInicio, nodoObjetivo);
        BusquedaCostoUniforme.buscar(grafo, nodoInicio, nodoObjetivo);
    }
}
