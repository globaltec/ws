/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.controller;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.business.facade.LoginFachada;
import com.globaltec.fleetcontrol.view.util.MensagemUtility;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Carlos Octaviano
 */
@ManagedBean(name = "loginManagedBean")
@SessionScoped
public class LoginManagedBean {

    private Usuario usuario;
    private final LoginFachada loginFachada = new LoginFachada();

    private String login;
    private String senha;
    private boolean usuarioLogado = false;

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(boolean usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String efetuarLogin() {
        try {
            this.usuario = loginFachada.login(this.getLogin(), this.getSenha());
            this.setUsuarioLogado(true);
            return "Principal.xhtml";
        } catch (LoginException e) {
            MensagemUtility.adicionarMensagemDeErro("formLogin", e.getMessage());
            this.setUsuarioLogado(false);
            return "/index.xhtml";
        }
    }

    public String efetuarLogout() {
        try {
            //loginFachada.logout();
            this.setUsuarioLogado(false);
            return "/index.xhtml";
        } catch (Exception e) {
            return "Principal.xhtml";
        }
    }
}
