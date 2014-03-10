package com.globaltec.fleetcontrol.test;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.business.facade.UsuarioFachada;
import java.util.Date;

/**
 *
 * @author Carlos Octaviano
 */
public class UsuarioTeste {

    public static void main(String[] args) {
        Usuario usuario = new Usuario(0, "admin@globaltec.com", "12345", "Administrador do Sistema", new Date());

        UsuarioFachada usuarioFachada = new UsuarioFachada();

        try {
            usuarioFachada.inserir(usuario);
            System.out.println("USUARIO inserido com sucesso !");

            //usuarioFachada.alterar(usuario);
            //System.out.println("USUARIO alterado com sucesso !");
            //usuarioFachada.excluir(usuario);
            //System.out.println("USUARIO excluido com sucesso !");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
