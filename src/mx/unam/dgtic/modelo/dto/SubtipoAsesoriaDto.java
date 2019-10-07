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
public class SubtipoAsesoriaDto {
    private short id = -1;
    private TipoAsesoriaDto tipo;
    private String nombre;
    private String descripcion;
    private boolean habilitado;

    public SubtipoAsesoriaDto() {
    }

    @Override
    public String toString() {
        return "SubtipoAsesoriaDto { " + "id=" + id + ", tipo=" + tipo + ", nombre=" + nombre + ", descripcion=" + descripcion + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SubtipoAsesoriaDto other = (SubtipoAsesoriaDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
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

    public TipoAsesoriaDto getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoAsesoriaDto tipo) {
        this.tipo = tipo;
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
