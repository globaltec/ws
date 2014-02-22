package com.globaltec.fleet.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
public class UsuarioWS {
	@GET
	@Path("/autentica")
	@Produces(MediaType.TEXT_PLAIN)
	public String autenticarUsuario(@QueryParam("usr") String usr,
			@QueryParam("pwd") String pwd) {
		if (usr.equals("admin") && pwd.equals("12345"))
			return "Administrador";
		else
			return "Usuario/Senha incorreto(s)";
	}
}
