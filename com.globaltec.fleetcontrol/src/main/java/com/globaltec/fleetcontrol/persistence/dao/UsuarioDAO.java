/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.persistence.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Carlos Octaviano
 */
public class UsuarioDAO implements Serializable {

    public UsuarioDAO(EntityManager em) {
        this.entityManager = em;
    }

    private EntityManager entityManager = null;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void create(Usuario usuario) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Papel idPapel = usuario.getIdPapel();

            if (idPapel != null) {
                idPapel = em.getReference(idPapel.getClass(), idPapel.getIdPapel());
                usuario.setIdPapel(idPapel);
            }

            em.persist(usuario);

            if (idPapel != null) {
                idPapel.getUsuarioCollection().add(usuario);
                idPapel = em.merge(idPapel);
            }

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Papel idPapelOld = persistentUsuario.getIdPapel();
            Papel idPapelNew = usuario.getIdPapel();

            if (idPapelNew != null) {
                idPapelNew = em.getReference(idPapelNew.getClass(), idPapelNew.getIdPapel());
                usuario.setIdPapel(idPapelNew);
            }

            usuario = em.merge(usuario);

            if (idPapelOld != null && !idPapelOld.equals(idPapelNew)) {
                idPapelOld.getUsuarioCollection().remove(usuario);
                idPapelOld = em.merge(idPapelOld);
            }

            if (idPapelNew != null && !idPapelNew.equals(idPapelOld)) {
                idPapelNew.getUsuarioCollection().add(usuario);
                idPapelNew = em.merge(idPapelNew);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();

            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();

                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;

            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }

            Papel idPapel = usuario.getIdPapel();

            if (idPapel != null) {
                idPapel.getUsuarioCollection().remove(usuario);
                idPapel = em.merge(idPapel);
            }

            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            return q.getResultList();
        } finally {
            //em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(Usuario.class, id);
        } finally {
            //em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

    public Usuario findUsuarioByLogin(String login) {
        EntityManager em = getEntityManager();

        try {
            return (Usuario) em.createNamedQuery("Usuario.findByNmLogin", Usuario.class).setParameter("nmLogin", login).getSingleResult();
        } finally {
            //em.close();
        }
    }
}
