package IA;

public class Heuristica {

 /**
  * Calcula la distancia euclidiana entre dos nodos.
  * h(n) = sqrt((n.x - objetivo.x)^2 + (n.y - objetivo.y)^2)
  * @param n El nodo actual.
  * @param objetivo El nodo objetivo.
  * @return El costo estimado (heurístico) desde n hasta el objetivo.
  */
 public static double distanciaEuclidiana(Nodo n, Nodo objetivo) {
     int dx = n.x - objetivo.x;
     int dy = n.y - objetivo.y;
     return Math.sqrt(dx * dx + dy * dy);
 }
}