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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos Octaviano
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nm_login"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNmLogin", query = "SELECT u FROM Usuario u WHERE u.nmLogin = :nmLogin"),
    @NamedQuery(name = "Usuario.findByNmUsuario", query = "SELECT u FROM Usuario u WHERE u.nmUsuario = :nmUsuario")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "Usuario_Generator", sequenceName = "usuario_id_usuario_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Usuario_Generator")
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Basic(optional = false)
    @Column(name = "nm_login", nullable = false, length = 100)
    private String nmLogin;

    @Basic(optional = false)
    @Column(name = "sn_usuario", nullable = false, length = 16)
    private String snUsuario;

    @Basic(optional = false)
    @Column(name = "nm_usuario", nullable = false, length = 200)
    private String nmUsuario;

    @Basic(optional = false)
    @Column(name = "dt_inclusao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInclusao;

    @Column(name = "dt_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAlteracao;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String nmLogin, String snUsuario, String nmUsuario, Date dtInclusao) {
        this.idUsuario = idUsuario;
        this.nmLogin = nmLogin;
        this.snUsuario = snUsuario;
        this.nmUsuario = nmUsuario;
        this.dtInclusao = dtInclusao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNmLogin() {
        return nmLogin;
    }

    public void setNmLogin(String nmLogin) {
        this.nmLogin = nmLogin;
    }

    public String getSnUsuario() {
        return snUsuario;
    }

    public void setSnUsuario(String snUsuario) {
        this.snUsuario = snUsuario;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.globaltec.fleetcontrol.business.model.Usuario[ idUsuario=" + idUsuario + " ]";
    }
}
