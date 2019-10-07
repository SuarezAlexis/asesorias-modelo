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
public class TipoAsesoriaDto {
    private short id = -1;
    private String nombre;
    private String descripcion;
    private boolean habilitado;

    public TipoAsesoriaDto() {
    }

    @Override
    public String toString() {
        return "TipoAsesoriaDto { " + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", habilitado=" + habilitado + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TipoAsesoriaDto other = (TipoAsesoriaDto) obj;
        if (this.id != other.id) {
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

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
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
