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
@Table(name = "usuario_tela", catalog = "fleetcontrol", schema = "fleet", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_usuario", "id_tela"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioTela.findAll", query = "SELECT u FROM UsuarioTela u"),
    @NamedQuery(name = "UsuarioTela.findByIdUsuarioTela", query = "SELECT u FROM UsuarioTela u WHERE u.idUsuarioTela = :idUsuarioTela"),
    @NamedQuery(name = "UsuarioTela.findByTpPermissao", query = "SELECT u FROM UsuarioTela u WHERE u.tpPermissao = :tpPermissao")})
public class UsuarioTela implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario_tela", nullable = false)
    private Integer idUsuarioTela;

    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_inclusao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInclusao;

    @Basic(optional = false)
    @NotNull
    @Column(name = "tp_permissao", nullable = false)
    private char tpPermissao;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    @JoinColumn(name = "id_tela", referencedColumnName = "id_tela", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tela idTela;

    public UsuarioTela() {
    }

    public UsuarioTela(Integer idUsuarioTela) {
        this.idUsuarioTela = idUsuarioTela;
    }

    public UsuarioTela(Integer idUsuarioTela, Date dtInclusao, char tpPermissao) {
        this.idUsuarioTela = idUsuarioTela;
        this.dtInclusao = dtInclusao;
        this.tpPermissao = tpPermissao;
    }

    public Integer getIdUsuarioTela() {
        return idUsuarioTela;
    }

    public void setIdUsuarioTela(Integer idUsuarioTela) {
        this.idUsuarioTela = idUsuarioTela;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public char getTpPermissao() {
        return tpPermissao;
    }

    public void setTpPermissao(char tpPermissao) {
        this.tpPermissao = tpPermissao;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Tela getIdTela() {
        return idTela;
    }

    public void setIdTela(Tela idTela) {
        this.idTela = idTela;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioTela != null ? idUsuarioTela.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioTela)) {
            return false;
        }
        UsuarioTela other = (UsuarioTela) object;
        if ((this.idUsuarioTela == null && other.idUsuarioTela != null) || (this.idUsuarioTela != null && !this.idUsuarioTela.equals(other.idUsuarioTela))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.globaltec.fleetcontrol.business.entity.UsuarioTela[ idUsuarioTela=" + idUsuarioTela + " ]";
    }
}
