/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.beans;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author gared
 */
public class Profesor implements Serializable {
 
    private int idProf;
    private String nombre;
    private Set<CorreoElectronico> correos;

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   

    public Set<CorreoElectronico> getCorreos() {
        return correos;
    }

    public void setCorreos(Set<CorreoElectronico> correos) {
        this.correos = correos;
    }
    
    
    
    
    
    
}
