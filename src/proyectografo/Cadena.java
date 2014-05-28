/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectografo;

import java.io.Serializable;
import java.util.Objects;


public class Cadena implements Serializable{
    private String cadena;

    public Cadena(String cadena) {
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    @Override
    public String toString() {
        return cadena;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.cadena);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cadena other = (Cadena) obj;
        if (Objects.equals(this.cadena, other.cadena)) {
            return false;
        }
        return true;
    }
    
    
}
