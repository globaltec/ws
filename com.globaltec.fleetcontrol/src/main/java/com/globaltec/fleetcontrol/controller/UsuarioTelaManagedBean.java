/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.controller;

import com.globaltec.fleetcontrol.business.entity.UsuarioTela;
import com.globaltec.fleetcontrol.business.facade.UsuarioTelaFachada;
import com.globaltec.fleetcontrol.view.util.MensagemUtility;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos Octaviano
 */
@ManagedBean
@SessionScoped
public class UsuarioTelaManagedBean {

    private UsuarioTela usuarioTela;
    private List<UsuarioTela> usuarioTelas = new ArrayList<UsuarioTela>();
    private UsuarioTelaFachada usuarioTelaFachada;

    /**
     * Creates a new instance of UsuarioTelaManagedBean
     */
    public UsuarioTelaManagedBean() {
    }

    /**
     * @return the usuarioTela
     */
    public UsuarioTela getUsuarioTela() {
        return this.usuarioTela;
    }

    /**
     * @param usuarioTela the usuarioTela to set
     */
    public void setTela(UsuarioTela usuarioTela) {
        this.usuarioTela = usuarioTela;
    }

    /**
     * @return the usuarioTelas
     */
    public List<UsuarioTela> getUsuarioTelas() {
        return this.usuarioTelas;
    }

    public String alterar() {
        try {
            this.usuarioTelaFachada.alterar(this.getUsuarioTela());
            this.recuperarUsuarioTelas();
            return "/usuariotela/ListarUsuarioTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formUsuarioTela", e.getMessage());
            return "/usuariotela/AlterarUsuarioTela";
        }
    }

    public String excluir() {
        try {
            this.usuarioTelaFachada.excluir(this.usuarioTela);
            this.recuperarUsuarioTelas();
            return "/usuariotela/ListarUsuarioTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formUsuarioTela", e.getMessage());
            return "/usuariotela/ExcluirUsuarioTela";
        }
    }

    public String inserir() {
        try {
            this.usuarioTelaFachada.inserir(this.getUsuarioTela());
            this.recuperarUsuarioTelas();
            return "/usuariotela/ListarUsuarioTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formUsuarioTela", e.getMessage());
            return "/usuariotela/InserirUsuarioTela";
        }
    }

    public String listar() throws Exception {
        this.recuperarUsuarioTelas();
        return "/usuariotela/ListarUsuarioTelas";
    }

    public String montarPaginaParaAlteracao() {
        return "/usuariotela/AlterarUsuarioTela";
    }

    public String montarPaginaParaExclusao() {
        return "/usuariotela/ExcluirUsuarioTela";
    }

    public String montarPaginaParaInsercao() {
        this.usuarioTela = new UsuarioTela();
        return "/usuariotela/InserirUsuarioTela";
    }

    private void recuperarUsuarioTelas() throws Exception {
        this.usuarioTelas = usuarioTelaFachada.recuperarTodos();
    }
}
