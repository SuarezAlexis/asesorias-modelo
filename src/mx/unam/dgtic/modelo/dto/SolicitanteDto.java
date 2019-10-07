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
public class SolicitanteDto {
    private int id = -1;
    private String nombre;
    private String apellidos;
    private TipoSolicitanteDto tipo;
    private String contacto;
    private boolean habilitado;

    public SolicitanteDto() {
    }

    @Override
    public String toString() {
        return "SolicitanteDto { " + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", tipo=" + tipo + ", contacto=" + contacto + ", habilitado=" + habilitado + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SolicitanteDto other = (SolicitanteDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.habilitado != other.habilitado) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.contacto, other.contacto)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public TipoSolicitanteDto getTipo() {
        return tipo;
    }

    public void setTipo(TipoSolicitanteDto tipo) {
        this.tipo = tipo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    
}
