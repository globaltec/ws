/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.business.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Octaviano
 */
@Entity
@Table(catalog = "fleetcontrol", schema = "fleet", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cd_papel"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Papel.findAll", query = "SELECT p FROM Papel p"),
    @NamedQuery(name = "Papel.findByIdPapel", query = "SELECT p FROM Papel p WHERE p.idPapel = :idPapel"),
    @NamedQuery(name = "Papel.findByCdPapel", query = "SELECT p FROM Papel p WHERE p.cdPapel = :cdPapel"),})
public class Papel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_papel", nullable = false)
    private Integer idPapel;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "cd_papel", nullable = false, length = 15)
    private String cdPapel;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ds_papel", nullable = false, length = 100)
    private String dsPapel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_inclusao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInclusao;

    @Column(name = "dt_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAlteracao;

    @ManyToMany(mappedBy = "papeis", targetEntity = Usuario.class)
    private Collection<Usuario> usuarios;

    @ManyToMany
    @JoinTable(name = "papel_tela", joinColumns = @JoinColumn(name = "id_papel"), inverseJoinColumns = @JoinColumn(name = "id_tela"))
    private Collection<Tela> telas;

    public Papel() {
    }

    public Papel(Integer idPapel) {
        this.idPapel = idPapel;
    }

    public Papel(Integer idPapel, String cdPapel, String dsPapel, Date dtInclusao) {
        this.idPapel = idPapel;
        this.cdPapel = cdPapel;
        this.dsPapel = dsPapel;
        this.dtInclusao = dtInclusao;
    }

    public Integer getIdPapel() {
        return idPapel;
    }

    public void setIdPapel(Integer idPapel) {
        this.idPapel = idPapel;
    }

    public String getCdPapel() {
        return cdPapel;
    }

    public void setCdPapel(String cdPapel) {
        this.cdPapel = cdPapel;
    }

    public String getDsPapel() {
        return dsPapel;
    }

    public void setDsPapel(String dsPapel) {
        this.dsPapel = dsPapel;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public Date getDtAlteracao() {
        return dtAlteracao;
    }

    public void setDtAlteracao(Date dtAlteracao) {
        this.dtAlteracao = dtAlteracao;
    }

    /**
     * @return the usuarios
     */
    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the telas
     */
    public Collection<Tela> getTelas() {
        return telas;
    }

    /**
     * @param telas the telas to set
     */
    public void setTelas(Collection<Tela> telas) {
        this.telas = telas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPapel != null ? idPapel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Papel)) {
            return false;
        }
        Papel other = (Papel) object;
        if ((this.idPapel == null && other.idPapel != null) || (this.idPapel != null && !this.idPapel.equals(other.idPapel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.globaltec.fleetcontrol.business.entity.Papel[ idPapel=" + idPapel + " ]";
    }
}
