package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Carlos A. Octaviano
 */
public class UsuarioDAO extends EntidadeDAO<Usuario> {

    public static Usuario recuperarPorLogin(String nmLogin) {
        try {
            EntityManager em = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
            return (Usuario) em.createNamedQuery("Usuario.findByNmLogin").setParameter("nmLogin", nmLogin).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
