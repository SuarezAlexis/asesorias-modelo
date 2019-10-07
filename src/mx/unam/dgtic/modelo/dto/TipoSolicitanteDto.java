/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  mx.unam.dgtic.modelo.dto;

import java.util.Objects;

/**
 *
 * 
 */
public class TipoSolicitanteDto {
    private String nombre;
    private String descripcion;
    private boolean habilitado;

    public TipoSolicitanteDto() {
    }

    @Override
    public String toString() {
        return "TipoSolicitanteDto { " + "nombre=" + nombre + ", descripcion=" + descripcion + ", habilitado=" + habilitado + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TipoSolicitanteDto other = (TipoSolicitanteDto) obj;
        if (this.habilitado != other.habilitado) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
        
}
