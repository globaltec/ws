/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.rest;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.business.facade.LoginFachada;
import javax.annotation.security.PermitAll;
import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Carlos Octaviano
 */
@Path("usuario")
public class UsuarioResource {

    @Context
    private UriInfo context;

    private final String PARAMETROS_NULOS = "Parametros nulos";

    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.globaltec.fleetcontrol.rest.UsuarioResource
     *
     * @param usr
     * @param pwd
     * @return an instance of java.lang.String
     */
    @GET
    @PermitAll
    @Produces("text/plain")
    public String getText(@QueryParam("usr") String usr,
            @QueryParam("pwd") String pwd) {
        if (usr != null && pwd != null) {
            LoginFachada loginFachada = new LoginFachada();

            try {
                Usuario usuario = loginFachada.login(usr, pwd);

                return usuario.getNmUsuario();
            } catch (LoginException e) {
                return "NOK";
            } catch (Exception e) {
                return "NOK";
            }
        } else {
            return PARAMETROS_NULOS;
        }
    }
}
