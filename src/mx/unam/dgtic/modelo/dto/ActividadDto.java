/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  mx.unam.dgtic.modelo.dto;

import java.util.Date;
import java.util.Objects;

/**
 *
 * 
 */
public class ActividadDto {
    private long id = -1;
    private AsesoriaDto asesoria;
    private Date fecha;
    private Estado estado;
    private UsuarioDto tecnico;
    private TipoAsesoriaDto tipo;
    private SubtipoAsesoriaDto subtipo;
    private String observaciones;

    public ActividadDto() {
    }

    @Override
    public String toString() {
        return "ActividadDto { " + "id=" + id + ", asesoria=" + asesoria + ", fecha=" + fecha + ", estado=" + estado + ", tecnico=" + tecnico + ", tipo=" + tipo + ", subtipo=" + subtipo + ", observaciones=" + observaciones + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ActividadDto other = (ActividadDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.asesoria, other.asesoria)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.tecnico, other.tecnico)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.subtipo, other.subtipo)) {
            return false;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AsesoriaDto getAsesoria() {
        return asesoria;
    }

    public void setAsesoria(AsesoriaDto asesoria) {
        this.asesoria = asesoria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public UsuarioDto getTecnico() {
        return tecnico;
    }

    public void setTecnico(UsuarioDto tecnico) {
        this.tecnico = tecnico;
    }

    public TipoAsesoriaDto getTipo() {
        return tipo;
    }

    public void setTipo(TipoAsesoriaDto tipo) {
        this.tipo = tipo;
    }

    public SubtipoAsesoriaDto getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(SubtipoAsesoriaDto subtipo) {
        this.subtipo = subtipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
