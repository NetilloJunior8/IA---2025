package IA; 
import java.util.Objects;

public class Nodo {
    String nombre;
    int x;
    int y;

    // Constructor para nodos CON coordenadas
    public Nodo(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }

    // Constructor para nodos SIN coordenadas explícitas
    public Nodo(String nombre) {
        this(nombre, 0, 0); 
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nodo nodo = (Nodo) o;
        return Objects.equals(nombre, nodo.nombre);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}