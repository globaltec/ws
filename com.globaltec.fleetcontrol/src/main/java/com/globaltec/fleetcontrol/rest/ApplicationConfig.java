/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Carlos Octaviano
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        return super.getSingletons(); //To change body of generated methods, choose Tools | Templates.
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.globaltec.fleetcontrol.rest.MensagemResource.class);
        resources.add(com.globaltec.fleetcontrol.rest.SecurityInterceptor.class);
        resources.add(com.globaltec.fleetcontrol.rest.UsuarioResource.class);
    }
}
