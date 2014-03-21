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
    @UniqueConstraint(columnNames = {"nm_tela"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tela.findAll", query = "SELECT t FROM Tela t"),
    @NamedQuery(name = "Tela.findByIdTela", query = "SELECT t FROM Tela t WHERE t.idTela = :idTela"),
    @NamedQuery(name = "Tela.findByNmTela", query = "SELECT t FROM Tela t WHERE t.nmTela = :nmTela"),
    @NamedQuery(name = "Tela.findByDsCaminhoArquivo", query = "SELECT t FROM Tela t WHERE t.dsCaminhoArquivo = :dsCaminhoArquivo")})
public class Tela implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tela", nullable = false)
    private Integer idTela;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nm_tela", nullable = false, length = 100)
    private String nmTela;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ds_tela", nullable = false, length = 200)
    private String dsTela;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "ds_caminho_arquivo", nullable = false, length = 500)
    private String dsCaminhoArquivo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_inclusao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInclusao;

    @Column(name = "dt_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAlteracao;

    @ManyToMany(mappedBy = "telas", targetEntity = Papel.class)
    private Collection<Papel> papeis;

    public Tela() {
    }

    public Tela(Integer idTela) {
        this.idTela = idTela;
    }

    public Tela(Integer idTela, String nmTela, String dsTela, String dsCaminhoArquivo, Date dtInclusao) {
        this.idTela = idTela;
        this.nmTela = nmTela;
        this.dsTela = dsTela;
        this.dsCaminhoArquivo = dsCaminhoArquivo;
        this.dtInclusao = dtInclusao;
    }

    public Integer getIdTela() {
        return idTela;
    }

    public void setIdTela(Integer idTela) {
        this.idTela = idTela;
    }

    public String getNmTela() {
        return nmTela;
    }

    public void setNmTela(String nmTela) {
        this.nmTela = nmTela;
    }

    public String getDsTela() {
        return dsTela;
    }

    public void setDsTela(String dsTela) {
        this.dsTela = dsTela;
    }

    public String getDsCaminhoArquivo() {
        return dsCaminhoArquivo;
    }

    public void setDsCaminhoArquivo(String dsCaminhoArquivo) {
        this.dsCaminhoArquivo = dsCaminhoArquivo;
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
     * @return the papeis
     */
    public Collection<Papel> getPapeis() {
        return papeis;
    }

    /**
     * @param papeis the papeis to set
     */
    public void setPapeis(Collection<Papel> papeis) {
        this.papeis = papeis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTela != null ? idTela.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tela)) {
            return false;
        }
        Tela other = (Tela) object;
        if ((this.idTela == null && other.idTela != null) || (this.idTela != null && !this.idTela.equals(other.idTela))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.globaltec.fleetcontrol.business.entity.Tela[ idTela=" + idTela + " ]";
    }
}
