/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Tela;
import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.business.entity.UsuarioTela;
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
public class UsuarioTelaDAO implements Serializable {

    public UsuarioTelaDAO(EntityManager em) {
        this.entityManager = em;
    }

    private EntityManager entityManager = null;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void create(UsuarioTela usuarioTela) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = usuarioTela.getIdUsuario();

            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                usuarioTela.setIdUsuario(idUsuario);
            }

            Tela idTela = usuarioTela.getIdTela();

            if (idTela != null) {
                idTela = em.getReference(idTela.getClass(), idTela.getIdTela());
                usuarioTela.setIdTela(idTela);
            }

            em.persist(usuarioTela);

            if (idUsuario != null) {
                idUsuario.getUsuarioTelaCollection().add(usuarioTela);
                idUsuario = em.merge(idUsuario);
            }

            if (idTela != null) {
                idTela.getUsuarioTelaCollection().add(usuarioTela);
                idTela = em.merge(idTela);
            }

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(UsuarioTela usuarioTela) throws NonexistentEntityException, Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioTela persistentUsuarioTela = em.find(UsuarioTela.class, usuarioTela.getIdUsuarioTela());
            Usuario idUsuarioOld = persistentUsuarioTela.getIdUsuario();
            Usuario idUsuarioNew = usuarioTela.getIdUsuario();
            Tela idTelaOld = persistentUsuarioTela.getIdTela();
            Tela idTelaNew = usuarioTela.getIdTela();

            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                usuarioTela.setIdUsuario(idUsuarioNew);
            }

            if (idTelaNew != null) {
                idTelaNew = em.getReference(idTelaNew.getClass(), idTelaNew.getIdTela());
                usuarioTela.setIdTela(idTelaNew);
            }

            usuarioTela = em.merge(usuarioTela);

            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getUsuarioTelaCollection().remove(usuarioTela);
                idUsuarioOld = em.merge(idUsuarioOld);
            }

            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getUsuarioTelaCollection().add(usuarioTela);
                idUsuarioNew = em.merge(idUsuarioNew);
            }

            if (idTelaOld != null && !idTelaOld.equals(idTelaNew)) {
                idTelaOld.getUsuarioTelaCollection().remove(usuarioTela);
                idTelaOld = em.merge(idTelaOld);
            }

            if (idTelaNew != null && !idTelaNew.equals(idTelaOld)) {
                idTelaNew.getUsuarioTelaCollection().add(usuarioTela);
                idTelaNew = em.merge(idTelaNew);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();

            if (msg == null || msg.length() == 0) {
                Integer id = usuarioTela.getIdUsuarioTela();

                if (findUsuarioTela(id) == null) {
                    throw new NonexistentEntityException("The usuarioTela with id " + id + " no longer exists.");
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
            UsuarioTela usuarioTela;

            try {
                usuarioTela = em.getReference(UsuarioTela.class, id);
                usuarioTela.getIdUsuarioTela();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioTela with id " + id + " no longer exists.", enfe);
            }

            Usuario idUsuario = usuarioTela.getIdUsuario();

            if (idUsuario != null) {
                idUsuario.getUsuarioTelaCollection().remove(usuarioTela);
                idUsuario = em.merge(idUsuario);
            }

            Tela idTela = usuarioTela.getIdTela();

            if (idTela != null) {
                idTela.getUsuarioTelaCollection().remove(usuarioTela);
                idTela = em.merge(idTela);
            }

            em.remove(usuarioTela);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<UsuarioTela> findUsuarioTelaEntities() {
        return findUsuarioTelaEntities(true, -1, -1);
    }

    public List<UsuarioTela> findUsuarioTelaEntities(int maxResults, int firstResult) {
        return findUsuarioTelaEntities(false, maxResults, firstResult);
    }

    private List<UsuarioTela> findUsuarioTelaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioTela.class));
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

    public UsuarioTela findUsuarioTela(Integer id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(UsuarioTela.class, id);
        } finally {
            //em.close();
        }
    }

    public int getUsuarioTelaCount() {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioTela> rt = cq.from(UsuarioTela.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
}
