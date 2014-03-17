/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.controller;

import com.globaltec.fleetcontrol.business.entity.PapelTela;
import com.globaltec.fleetcontrol.business.facade.PapelTelaFachada;
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
public class PapelTelaManagedBean {

    private PapelTela papelTela;
    private List<PapelTela> papelTelas = new ArrayList<PapelTela>();
    private PapelTelaFachada papelTelaFachada;

    /**
     * Creates a new instance of PapelTelaManagedBean
     */
    public PapelTelaManagedBean() {
    }

    /**
     * @return the papelTela
     */
    public PapelTela getPapelTela() {
        return this.papelTela;
    }

    /**
     * @param papelTela the papalTela to set
     */
    public void setTela(PapelTela papelTela) {
        this.papelTela = papelTela;
    }

    /**
     * @return the papelTelas
     */
    public List<PapelTela> getPapelTelas() {
        return this.papelTelas;
    }

    public String alterar() {
        try {
            this.papelTelaFachada.alterar(this.getPapelTela());
            this.recuperarPapelTelas();
            return "/papeltela/ListarPapelTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formPapelTela", e.getMessage());
            return "/papeltela/AlterarPapelTela";
        }
    }

    public String excluir() {
        try {
            this.papelTelaFachada.excluir(this.papelTela);
            this.recuperarPapelTelas();
            return "/papeltela/ListarPapelTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formPapelTela", e.getMessage());
            return "/papeltela/ExcluirPapelTela";
        }
    }

    public String inserir() {
        try {
            this.papelTelaFachada.inserir(this.getPapelTela());
            this.recuperarPapelTelas();
            return "/papeltela/ListarPapelTelas";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formPapelTela", e.getMessage());
            return "/papeltela/InserirPapelTela";
        }
    }

    public String listar() throws Exception {
        this.recuperarPapelTelas();
        return "/papeltela/ListarPapelTelas";
    }

    public String montarPaginaParaAlteracao() {
        return "/papeltela/AlterarPapelTela";
    }

    public String montarPaginaParaExclusao() {
        return "/papeltela/ExcluirPapelTela";
    }

    public String montarPaginaParaInsercao() {
        this.papelTela = new PapelTela();
        return "/papeltela/InserirPapelTela";
    }

    private void recuperarPapelTelas() throws Exception {
        this.papelTelas = papelTelaFachada.recuperarTodos();
    }
}
