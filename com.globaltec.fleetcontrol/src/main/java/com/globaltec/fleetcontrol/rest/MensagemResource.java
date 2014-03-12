/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.rest;

import javax.annotation.security.PermitAll;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

/**
 * REST Web Service
 *
 * @author Carlos Octaviano
 */
@Path("mensagem")
public class MensagemResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MensagemWS
     */
    public MensagemResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.globaltec.fleetcontrol.rest.MensagemResource
     *
     * @param title
     * @param lat
     * @param lng
     * @return an instance of java.lang.String
     */
    @PUT
    @PermitAll
    @Path("/{title}")
    public Response getText(@PathParam("title") String title,
            @MatrixParam("lat") double lat, @MatrixParam("lng") double lng) {
        Response response = null;

        if (title != null) {
            try {
                PushContext pushContext = PushContextFactory.getDefault().getPushContext();
                pushContext.push("/check-in", new CheckIn(title, lat, lng));

                response = Response.status(Response.Status.OK).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return response;
    }
}
