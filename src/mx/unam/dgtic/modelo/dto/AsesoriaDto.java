/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.dgtic.modelo.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * 
 */
public class AsesoriaDto {
    private long id = -1;
    private Date fecha;
    private SolicitanteDto solicitante;
    private PisoDto piso;
    private String observaciones;
    private List<ActividadDto> actividades;

    public AsesoriaDto() { }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AsesoriaDto other = (AsesoriaDto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.solicitante, other.solicitante)) {
            return false;
        }
        if (!Objects.equals(this.piso, other.piso)) {
            return false;
        }
        if (!Objects.equals(this.actividades, other.actividades)) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public SolicitanteDto getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(SolicitanteDto solicitante) {
        this.solicitante = solicitante;
    }

    public PisoDto getPiso() {
        return piso;
    }

    public void setPiso(PisoDto piso) {
        this.piso = piso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<ActividadDto> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadDto> actividades) {
        this.actividades = actividades;
    }
    
    
}
