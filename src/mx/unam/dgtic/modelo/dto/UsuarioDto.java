/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  mx.unam.dgtic.modelo.dto;

import java.util.List;
import java.util.Objects;

/**
 *
 * 
 */
public class UsuarioDto {
    private String username;
    private String nombre;
    private String apellidos;
    private String password;
    private String email;
    private List<RolDto> roles;
    private boolean habilitado;

    public UsuarioDto() { }

    @Override
    public String toString() {
        return "UsuarioDto { " + "username=" + username + ", nombre=" + nombre + ", apellidos=" + apellidos + ", password=" + password + ", email=" + email + ", roles=" + roles + ", habilitado=" + habilitado + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioDto other = (UsuarioDto) obj;
        if (this.habilitado != other.habilitado) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<RolDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RolDto> roles) {
        this.roles = roles;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    
}
