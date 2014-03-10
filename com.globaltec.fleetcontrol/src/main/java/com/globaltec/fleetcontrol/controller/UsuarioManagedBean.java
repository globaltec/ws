/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.controller;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.business.facade.UsuarioFachada;
import com.globaltec.fleetcontrol.view.util.MensagemUtility;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos Octaviano
 */
@ManagedBean(name = "usuarioManagedBean")
@SessionScoped
public class UsuarioManagedBean {

    private Usuario usuario;
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private UsuarioFachada usuarioFachada;

    /**
     * Creates a new instance of UsuarioManagedBean
     */
    public UsuarioManagedBean() {
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String alterar() {
        try {
            usuarioFachada.alterar(this.getUsuario());
            this.recuperarUsuarios();
            return "/usuario/ListarUsuarios";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formUsuario", e.getMessage());
            return "/usuario/AlterarUsuario";
        }
    }

    public String excluir() {
        try {
            usuarioFachada.excluir(this.usuario);
            this.recuperarUsuarios();
            return "/usuario/ListarUsuarios";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formUsuario", e.getMessage());
            return "/usuario/ExcluirUsuario";
        }
    }

    public String inserir() {
        try {
            usuarioFachada.inserir(this.getUsuario());
            this.recuperarUsuarios();
            return "/usuario/ListarUsuarios";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formUsuario", e.getMessage());
            return "/usuario/InserirUsuario";
        }
    }

    public String listar() throws Exception {
        this.recuperarUsuarios();
        return "/usuario/ListarUsuarios";
    }

    public String montarPaginaParaAlteracao() {
        return "/usuario/AlterarUsuario";
    }

    public String montarPaginaParaExclusao() {
        return "/usuario/ExcluirUsuario";
    }

    public String montarPaginaParaInsercao() {
        this.usuario = new Usuario();
        return "/usuario/InserirUsuario";
    }

    private void recuperarUsuarios() throws Exception {
        this.usuarios = usuarioFachada.recuperarTodos();
    }
}
