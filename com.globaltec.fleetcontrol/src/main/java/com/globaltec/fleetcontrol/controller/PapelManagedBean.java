/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.controller;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.facade.PapelFachada;
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
public class PapelManagedBean {

    private Papel papel;
    private List<Papel> papeis = new ArrayList<Papel>();
    private PapelFachada papelFachada;

    /**
     * Creates a new instance of PapelManagedBean
     */
    public PapelManagedBean() {
    }

    /**
     * @return the papel
     */
    public Papel getPapel() {
        return this.papel;
    }

    /**
     * @param papel the papel to set
     */
    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    /**
     * @return the papels
     */
    public List<Papel> getPapels() {
        return this.papeis;
    }

    public String alterar() {
        try {
            this.papelFachada.alterar(this.getPapel());
            this.recuperarPapels();
            return "/papel/ListarPapels";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formPapel", e.getMessage());
            return "/papel/AlterarPapel";
        }
    }

    public String excluir() {
        try {
            this.papelFachada.excluir(this.papel);
            this.recuperarPapels();
            return "/papel/ListarPapels";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formPapel", e.getMessage());
            return "/papel/ExcluirPapel";
        }
    }

    public String inserir() {
        try {
            this.papelFachada.inserir(this.getPapel());
            this.recuperarPapels();
            return "/papel/ListarPapels";
        } catch (Exception e) {
            MensagemUtility.adicionarMensagemDeErro("formPapel", e.getMessage());
            return "/papel/InserirPapel";
        }
    }

    public String listar() throws Exception {
        this.recuperarPapels();
        return "/papel/ListarPapels";
    }

    public String montarPaginaParaAlteracao() {
        return "/papel/AlterarPapel";
    }

    public String montarPaginaParaExclusao() {
        return "/papel/ExcluirPapel";
    }

    public String montarPaginaParaInsercao() {
        this.papel = new Papel();
        return "/papel/InserirPapel";
    }

    private void recuperarPapels() throws Exception {
        this.papeis = papelFachada.recuperarTodos();
    }
}
