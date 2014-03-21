/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.test;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.business.facade.PapelFachada;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Carlos Octaviano
 */
public class PapelTeste {

    public static void main(String[] args) {
        //Papel papel = new Papel(0, "ADMIN", "Administrador", new Date());

        PapelFachada papelFachada = new PapelFachada();

        try {
            //papelFachada.inserir(papel);
            //System.out.println("PAPEL inserido com sucesso !");

            //usuarioFachada.alterar(papel);
            //System.out.println("PAPEL alterado com sucesso !");
            //usuarioFachada.excluir(papel);
            //System.out.println("PAPEL excluido com sucesso !");
            List<Papel> lp = papelFachada.recuperarTodos();

            for (Papel p : lp) {
                System.out.println("PAPEL: " + p.getCdPapel());
                Collection<Usuario> lu = (ArrayList<Usuario>) p.getUsuarios();

                for (Usuario u : lu) {
                    System.out.println("USUARIO: " + u.getNmLogin());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
