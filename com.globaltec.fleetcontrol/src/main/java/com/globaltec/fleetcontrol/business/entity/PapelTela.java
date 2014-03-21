/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.business.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Octaviano
 */
@Entity
@Table(name = "papel_tela", catalog = "fleetcontrol", schema = "fleet", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_papel", "id_tela"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PapelTela.findAll", query = "SELECT p FROM PapelTela p"),
    @NamedQuery(name = "PapelTela.findByIdPapelTela", query = "SELECT p FROM PapelTela p WHERE p.idPapelTela = :idPapelTela")})
public class PapelTela implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_papel_tela", nullable = false)
    private Integer idPapelTela;

    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_inclusao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInclusao;

    @JoinColumn(name = "id_tela", referencedColumnName = "id_tela", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tela idTela;

    @JoinColumn(name = "id_papel", referencedColumnName = "id_papel", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Papel idPapel;

    public PapelTela() {
    }

    public PapelTela(Integer idPapelTela) {
        this.idPapelTela = idPapelTela;
    }

    public PapelTela(Integer idPapelTela, Date dtInclusao) {
        this.idPapelTela = idPapelTela;
        this.dtInclusao = dtInclusao;
    }

    public Integer getIdPapelTela() {
        return idPapelTela;
    }

    public void setIdPapelTela(Integer idPapelTela) {
        this.idPapelTela = idPapelTela;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public Tela getIdTela() {
        return idTela;
    }

    public void setIdTela(Tela idTela) {
        this.idTela = idTela;
    }

    public Papel getIdPapel() {
        return idPapel;
    }

    public void setIdPapel(Papel idPapel) {
        this.idPapel = idPapel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPapelTela != null ? idPapelTela.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PapelTela)) {
            return false;
        }
        PapelTela other = (PapelTela) object;
        if ((this.idPapelTela == null && other.idPapelTela != null) || (this.idPapelTela != null && !this.idPapelTela.equals(other.idPapelTela))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.globaltec.fleetcontrol.business.entity.PapelTela[ idPapelTela=" + idPapelTela + " ]";
    }
}
