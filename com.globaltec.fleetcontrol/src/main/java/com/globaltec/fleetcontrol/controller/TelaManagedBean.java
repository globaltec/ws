/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.controller;

import com.globaltec.fleetcontrol.business.entity.Tela;
import com.globaltec.fleetcontrol.business.facade.TelaFachada;
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
public class TelaManagedBean {

    private Tela tela;
    private List<Tela> telas = new ArrayList<Tela>();
    private TelaFachada telaFachada;

    /**
     * Creates a new instance of TelaManagedBean
     */
    public TelaManagedBean() {
    }

    /**
     * @return the tela
     */
    public Tela getTela() {
        return this.tela;
    }

    /**
     * @param tela the tela to set
     */
    public void setTela(Tela tela) {
        this.tela = tela;
    }

    /**
     * @return the telas
     */
    public List<Tela> getTelas() {
        return this.telas;
    }

    public String alterar() {
        try {
            this.telaFachada.alterar(this.getTela());
            this.recuperarTelas();
            return "/tela/ListarTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formTela", e.getMessage());
            return "/tela/AlterarTela";
        }
    }

    public String excluir() {
        try {
            this.telaFachada.excluir(this.tela);
            this.recuperarTelas();
            return "/tela/ListarTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formTela", e.getMessage());
            return "/tela/ExcluirTela";
        }
    }

    public String inserir() {
        try {
            this.telaFachada.inserir(this.getTela());
            this.recuperarTelas();
            return "/tela/ListarTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formTela", e.getMessage());
            return "/tela/InserirTela";
        }
    }

    public String listar() throws Exception {
        this.recuperarTelas();
        return "/tela/ListarTelas";
    }

    public String montarPaginaParaAlteracao() {
        return "/tela/AlterarTela";
    }

    public String montarPaginaParaExclusao() {
        return "/tela/ExcluirTela";
    }

    public String montarPaginaParaInsercao() {
        this.tela = new Tela();
        return "/tela/InserirTela";
    }

    private void recuperarTelas() throws Exception {
        this.telas = telaFachada.recuperarTodos();
    }
}
