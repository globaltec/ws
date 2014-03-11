/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

/**
 * REST Web Service
 *
 * @author Carlos Octaviano
 */
@Path("mensagem")
public class MensagemWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MensagemWS
     */
    public MensagemWS() {
    }

    /**
     * Retrieves representation of an instance of
     * com.globaltec.fleetcontrol.rest.MensagemWS
     *
     * @param title
     * @param lat
     * @param lng
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText(@QueryParam("title") String title,
            @QueryParam("lat") double lat, @QueryParam("lng") double lng) {
        try {
            if (title != null) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append(title);
                strBuilder.append("***");
                strBuilder.append(lat);
                strBuilder.append("***");
                strBuilder.append(lng);

                PushContext pushContext = PushContextFactory.getDefault().getPushContext();
                pushContext.push("/check-in", new CheckIn(title, lat, lng));

                return strBuilder.toString();
            } else {
                return "Parametros nulos";
            }
        } catch (Exception e) {
            return "NOK";
        }
    }

    /**
     * PUT method for updating or creating an instance of MensagemWS
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    /*@PUT
     @Consumes("text/plain")
     public void putText(String content) {
     }*/
}
