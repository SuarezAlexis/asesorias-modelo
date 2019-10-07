/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dto;

import java.util.Objects;

/**
 *
 * 
 */
public class PisoDto {
    private short id;
    private String descripcion;
    private boolean habilitado;

    public PisoDto() {
    }

    @Override
    public String toString() {
        return "PisoDto { " + "id=" + id + ", descripcion=" + descripcion + ", habilitado=" + habilitado + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PisoDto other = (PisoDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.habilitado != other.habilitado) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
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
