/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.PapelTela;
import com.globaltec.fleetcontrol.business.entity.Tela;
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
public class PapelTelaDAO implements Serializable {

    public PapelTelaDAO(EntityManager em) {
        this.entityManager = em;
    }

    private EntityManager entityManager = null;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void create(PapelTela papelTela) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tela idTela = papelTela.getIdTela();

            if (idTela != null) {
                idTela = em.getReference(idTela.getClass(), idTela.getIdTela());
                papelTela.setIdTela(idTela);
            }

            Papel idPapel = papelTela.getIdPapel();

            if (idPapel != null) {
                idPapel = em.getReference(idPapel.getClass(), idPapel.getIdPapel());
                papelTela.setIdPapel(idPapel);
            }

            em.persist(papelTela);

            if (idTela != null) {
                idTela.getPapelTelaCollection().add(papelTela);
                idTela = em.merge(idTela);
            }

            if (idPapel != null) {
                idPapel.getPapelTelaCollection().add(papelTela);
                idPapel = em.merge(idPapel);
            }

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(PapelTela papelTela) throws NonexistentEntityException, Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PapelTela persistentPapelTela = em.find(PapelTela.class, papelTela.getIdPapelTela());
            Tela idTelaOld = persistentPapelTela.getIdTela();
            Tela idTelaNew = papelTela.getIdTela();
            Papel idPapelOld = persistentPapelTela.getIdPapel();
            Papel idPapelNew = papelTela.getIdPapel();

            if (idTelaNew != null) {
                idTelaNew = em.getReference(idTelaNew.getClass(), idTelaNew.getIdTela());
                papelTela.setIdTela(idTelaNew);
            }

            if (idPapelNew != null) {
                idPapelNew = em.getReference(idPapelNew.getClass(), idPapelNew.getIdPapel());
                papelTela.setIdPapel(idPapelNew);
            }

            papelTela = em.merge(papelTela);

            if (idTelaOld != null && !idTelaOld.equals(idTelaNew)) {
                idTelaOld.getPapelTelaCollection().remove(papelTela);
                idTelaOld = em.merge(idTelaOld);
            }

            if (idTelaNew != null && !idTelaNew.equals(idTelaOld)) {
                idTelaNew.getPapelTelaCollection().add(papelTela);
                idTelaNew = em.merge(idTelaNew);
            }

            if (idPapelOld != null && !idPapelOld.equals(idPapelNew)) {
                idPapelOld.getPapelTelaCollection().remove(papelTela);
                idPapelOld = em.merge(idPapelOld);
            }

            if (idPapelNew != null && !idPapelNew.equals(idPapelOld)) {
                idPapelNew.getPapelTelaCollection().add(papelTela);
                idPapelNew = em.merge(idPapelNew);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();

            if (msg == null || msg.length() == 0) {
                Integer id = papelTela.getIdPapelTela();

                if (findPapelTela(id) == null) {
                    throw new NonexistentEntityException("The papelTela with id " + id + " no longer exists.");
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
            PapelTela papelTela;

            try {
                papelTela = em.getReference(PapelTela.class, id);
                papelTela.getIdPapelTela();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The papelTela with id " + id + " no longer exists.", enfe);
            }

            Tela idTela = papelTela.getIdTela();

            if (idTela != null) {
                idTela.getPapelTelaCollection().remove(papelTela);
                idTela = em.merge(idTela);
            }

            Papel idPapel = papelTela.getIdPapel();

            if (idPapel != null) {
                idPapel.getPapelTelaCollection().remove(papelTela);
                idPapel = em.merge(idPapel);
            }

            em.remove(papelTela);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<PapelTela> findPapelTelaEntities() {
        return findPapelTelaEntities(true, -1, -1);
    }

    public List<PapelTela> findPapelTelaEntities(int maxResults, int firstResult) {
        return findPapelTelaEntities(false, maxResults, firstResult);
    }

    private List<PapelTela> findPapelTelaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PapelTela.class));
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

    public PapelTela findPapelTela(Integer id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(PapelTela.class, id);
        } finally {
            //em.close();
        }
    }

    public int getPapelTelaCount() {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PapelTela> rt = cq.from(PapelTela.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }
}
