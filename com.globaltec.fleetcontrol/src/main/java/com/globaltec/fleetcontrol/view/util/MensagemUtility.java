/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.view.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ivan Joao Foschini
 */
public class MensagemUtility {

    public static void adicionarMensagemDeErro(String idDoComponente, String mensagem) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(idDoComponente, facesMessage);
    }

    public static void adicionarMensagemDeSucesso(String idDoComponente, String mensagem) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(idDoComponente, facesMessage);
    }
}
