/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.Tela;
import com.globaltec.fleetcontrol.persistence.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Carlos Octaviano
 */
public class TelaDAO implements Serializable {

    private EntityManager em = null;

    public TelaDAO(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(Tela tela) {
        if (tela.getPapeis() == null) {
            tela.setPapeis(new ArrayList<Papel>());
        }

        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();

            Collection<Papel> attachedPapeis = new ArrayList<Papel>();

            for (Papel papeisPapelToAttach : tela.getPapeis()) {
                papeisPapelToAttach = eManager.getReference(papeisPapelToAttach.getClass(), papeisPapelToAttach.getIdPapel());
                attachedPapeis.add(papeisPapelToAttach);
            }

            tela.setPapeis(attachedPapeis);

            eManager.persist(tela);

            for (Papel papeisPapel : tela.getPapeis()) {
                papeisPapel.getTelas().add(tela);
                papeisPapel = eManager.merge(papeisPapel);
            }

            eManager.getTransaction().commit();
        } finally {
            if (eManager != null) {
                //em.close();
            }
        }
    }

    public void edit(Tela tela) throws NonexistentEntityException, Exception {
        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();

            Tela persistentTela = eManager.find(Tela.class, tela.getIdTela());
            Collection<Papel> papeisOld = persistentTela.getPapeis();
            Collection<Papel> papeisNew = tela.getPapeis();
            Collection<Papel> attachedPapeisNew = new ArrayList<Papel>();

            for (Papel papeisNewPapelToAttach : papeisNew) {
                papeisNewPapelToAttach = eManager.getReference(papeisNewPapelToAttach.getClass(), papeisNewPapelToAttach.getIdPapel());
                attachedPapeisNew.add(papeisNewPapelToAttach);
            }

            papeisNew = attachedPapeisNew;
            tela.setPapeis(papeisNew);

            tela = eManager.merge(tela);

            for (Papel papeisOldPapel : papeisOld) {
                if (!papeisNew.contains(papeisOldPapel)) {
                    papeisOldPapel.getTelas().remove(tela);
                    papeisOldPapel = eManager.merge(papeisOldPapel);
                }
            }

            for (Papel papeisNewPapel : papeisNew) {
                if (!papeisOld.contains(papeisNewPapel)) {
                    papeisNewPapel.getTelas().add(tela);
                    papeisNewPapel = eManager.merge(papeisNewPapel);
                }
            }

            eManager.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();

            if (msg == null || msg.length() == 0) {
                Integer id = tela.getIdTela();

                if (findTela(id) == null) {
                    throw new NonexistentEntityException("The tela with id " + id + " no longer exists.");
                }
            }

            throw ex;
        } finally {
            if (eManager != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();
            Tela tela;

            try {
                tela = eManager.getReference(Tela.class, id);
                tela.getIdTela();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tela with id " + id + " no longer exists.", enfe);
            }

            Collection<Papel> papeis = tela.getPapeis();

            for (Papel papeisPapel : papeis) {
                papeisPapel.getTelas().remove(tela);
                papeisPapel = eManager.merge(papeisPapel);
            }

            eManager.remove(tela);
            eManager.getTransaction().commit();
        } finally {
            if (eManager != null) {
                //em.close();
            }
        }
    }

    public List<Tela> findTelaEntities() {
        return findTelaEntities(true, -1, -1);
    }

    public List<Tela> findTelaEntities(int maxResults, int firstResult) {
        return findTelaEntities(false, maxResults, firstResult);
    }

    private List<Tela> findTelaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager eManager = getEntityManager();

        try {
            CriteriaQuery cq = eManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tela.class));
            Query q = eManager.createQuery(cq);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            return q.getResultList();
        } finally {
            //em.close();
        }
    }

    public Tela findTela(Integer id) {
        EntityManager eManager = getEntityManager();

        try {
            return eManager.find(Tela.class, id);
        } finally {
            //em.close();
        }
    }

    public int getTelaCount() {
        EntityManager eManager = getEntityManager();

        try {
            CriteriaQuery cq = eManager.getCriteriaBuilder().createQuery();
            Root<Tela> rt = cq.from(Tela.class);
            cq.select(eManager.getCriteriaBuilder().count(rt));
            Query q = eManager.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

    public Tela findTelaByName(String nmTela) {
        EntityManager eManager = getEntityManager();

        try {
            return (Tela) eManager.createNamedQuery("Tela.findByNmTela", Tela.class).setParameter("nmTela", nmTela).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            //em.close();
        }
    }
}
