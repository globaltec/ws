/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.PapelTela;
import com.globaltec.fleetcontrol.business.entity.Tela;
import com.globaltec.fleetcontrol.business.entity.UsuarioTela;
import com.globaltec.fleetcontrol.persistence.dao.exceptions.IllegalOrphanException;
import com.globaltec.fleetcontrol.persistence.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
public class TelaDAO implements Serializable {

    public TelaDAO(EntityManager em) {
        this.entityManager = em;
    }

    private EntityManager entityManager = null;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void create(Tela tela) {
        if (tela.getUsuarioTelaCollection() == null) {
            tela.setUsuarioTelaCollection(new ArrayList<UsuarioTela>());
        }
        if (tela.getPapelTelaCollection() == null) {
            tela.setPapelTelaCollection(new ArrayList<PapelTela>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UsuarioTela> attachedUsuarioTelaCollection = new ArrayList<UsuarioTela>();

            for (UsuarioTela usuarioTelaCollectionUsuarioTelaToAttach : tela.getUsuarioTelaCollection()) {
                usuarioTelaCollectionUsuarioTelaToAttach = em.getReference(usuarioTelaCollectionUsuarioTelaToAttach.getClass(), usuarioTelaCollectionUsuarioTelaToAttach.getIdUsuarioTela());
                attachedUsuarioTelaCollection.add(usuarioTelaCollectionUsuarioTelaToAttach);
            }

            tela.setUsuarioTelaCollection(attachedUsuarioTelaCollection);
            Collection<PapelTela> attachedPapelTelaCollection = new ArrayList<PapelTela>();

            for (PapelTela papelTelaCollectionPapelTelaToAttach : tela.getPapelTelaCollection()) {
                papelTelaCollectionPapelTelaToAttach = em.getReference(papelTelaCollectionPapelTelaToAttach.getClass(), papelTelaCollectionPapelTelaToAttach.getIdPapelTela());
                attachedPapelTelaCollection.add(papelTelaCollectionPapelTelaToAttach);
            }

            tela.setPapelTelaCollection(attachedPapelTelaCollection);
            em.persist(tela);

            for (UsuarioTela usuarioTelaCollectionUsuarioTela : tela.getUsuarioTelaCollection()) {
                Tela oldIdTelaOfUsuarioTelaCollectionUsuarioTela = usuarioTelaCollectionUsuarioTela.getIdTela();
                usuarioTelaCollectionUsuarioTela.setIdTela(tela);
                usuarioTelaCollectionUsuarioTela = em.merge(usuarioTelaCollectionUsuarioTela);

                if (oldIdTelaOfUsuarioTelaCollectionUsuarioTela != null) {
                    oldIdTelaOfUsuarioTelaCollectionUsuarioTela.getUsuarioTelaCollection().remove(usuarioTelaCollectionUsuarioTela);
                    oldIdTelaOfUsuarioTelaCollectionUsuarioTela = em.merge(oldIdTelaOfUsuarioTelaCollectionUsuarioTela);
                }
            }

            for (PapelTela papelTelaCollectionPapelTela : tela.getPapelTelaCollection()) {
                Tela oldIdTelaOfPapelTelaCollectionPapelTela = papelTelaCollectionPapelTela.getIdTela();
                papelTelaCollectionPapelTela.setIdTela(tela);
                papelTelaCollectionPapelTela = em.merge(papelTelaCollectionPapelTela);

                if (oldIdTelaOfPapelTelaCollectionPapelTela != null) {
                    oldIdTelaOfPapelTelaCollectionPapelTela.getPapelTelaCollection().remove(papelTelaCollectionPapelTela);
                    oldIdTelaOfPapelTelaCollectionPapelTela = em.merge(oldIdTelaOfPapelTelaCollectionPapelTela);
                }
            }

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Tela tela) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tela persistentTela = em.find(Tela.class, tela.getIdTela());
            Collection<UsuarioTela> usuarioTelaCollectionOld = persistentTela.getUsuarioTelaCollection();
            Collection<UsuarioTela> usuarioTelaCollectionNew = tela.getUsuarioTelaCollection();
            Collection<PapelTela> papelTelaCollectionOld = persistentTela.getPapelTelaCollection();
            Collection<PapelTela> papelTelaCollectionNew = tela.getPapelTelaCollection();
            List<String> illegalOrphanMessages = null;

            for (UsuarioTela usuarioTelaCollectionOldUsuarioTela : usuarioTelaCollectionOld) {
                if (!usuarioTelaCollectionNew.contains(usuarioTelaCollectionOldUsuarioTela)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }

                    illegalOrphanMessages.add("You must retain UsuarioTela " + usuarioTelaCollectionOldUsuarioTela + " since its idTela field is not nullable.");
                }
            }

            for (PapelTela papelTelaCollectionOldPapelTela : papelTelaCollectionOld) {
                if (!papelTelaCollectionNew.contains(papelTelaCollectionOldPapelTela)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }

                    illegalOrphanMessages.add("You must retain PapelTela " + papelTelaCollectionOldPapelTela + " since its idTela field is not nullable.");
                }
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            Collection<UsuarioTela> attachedUsuarioTelaCollectionNew = new ArrayList<UsuarioTela>();

            for (UsuarioTela usuarioTelaCollectionNewUsuarioTelaToAttach : usuarioTelaCollectionNew) {
                usuarioTelaCollectionNewUsuarioTelaToAttach = em.getReference(usuarioTelaCollectionNewUsuarioTelaToAttach.getClass(), usuarioTelaCollectionNewUsuarioTelaToAttach.getIdUsuarioTela());
                attachedUsuarioTelaCollectionNew.add(usuarioTelaCollectionNewUsuarioTelaToAttach);
            }

            usuarioTelaCollectionNew = attachedUsuarioTelaCollectionNew;
            tela.setUsuarioTelaCollection(usuarioTelaCollectionNew);
            Collection<PapelTela> attachedPapelTelaCollectionNew = new ArrayList<PapelTela>();

            for (PapelTela papelTelaCollectionNewPapelTelaToAttach : papelTelaCollectionNew) {
                papelTelaCollectionNewPapelTelaToAttach = em.getReference(papelTelaCollectionNewPapelTelaToAttach.getClass(), papelTelaCollectionNewPapelTelaToAttach.getIdPapelTela());
                attachedPapelTelaCollectionNew.add(papelTelaCollectionNewPapelTelaToAttach);
            }

            papelTelaCollectionNew = attachedPapelTelaCollectionNew;
            tela.setPapelTelaCollection(papelTelaCollectionNew);
            tela = em.merge(tela);

            for (UsuarioTela usuarioTelaCollectionNewUsuarioTela : usuarioTelaCollectionNew) {
                if (!usuarioTelaCollectionOld.contains(usuarioTelaCollectionNewUsuarioTela)) {
                    Tela oldIdTelaOfUsuarioTelaCollectionNewUsuarioTela = usuarioTelaCollectionNewUsuarioTela.getIdTela();
                    usuarioTelaCollectionNewUsuarioTela.setIdTela(tela);
                    usuarioTelaCollectionNewUsuarioTela = em.merge(usuarioTelaCollectionNewUsuarioTela);

                    if (oldIdTelaOfUsuarioTelaCollectionNewUsuarioTela != null && !oldIdTelaOfUsuarioTelaCollectionNewUsuarioTela.equals(tela)) {
                        oldIdTelaOfUsuarioTelaCollectionNewUsuarioTela.getUsuarioTelaCollection().remove(usuarioTelaCollectionNewUsuarioTela);
                        oldIdTelaOfUsuarioTelaCollectionNewUsuarioTela = em.merge(oldIdTelaOfUsuarioTelaCollectionNewUsuarioTela);
                    }
                }
            }

            for (PapelTela papelTelaCollectionNewPapelTela : papelTelaCollectionNew) {
                if (!papelTelaCollectionOld.contains(papelTelaCollectionNewPapelTela)) {
                    Tela oldIdTelaOfPapelTelaCollectionNewPapelTela = papelTelaCollectionNewPapelTela.getIdTela();
                    papelTelaCollectionNewPapelTela.setIdTela(tela);
                    papelTelaCollectionNewPapelTela = em.merge(papelTelaCollectionNewPapelTela);

                    if (oldIdTelaOfPapelTelaCollectionNewPapelTela != null && !oldIdTelaOfPapelTelaCollectionNewPapelTela.equals(tela)) {
                        oldIdTelaOfPapelTelaCollectionNewPapelTela.getPapelTelaCollection().remove(papelTelaCollectionNewPapelTela);
                        oldIdTelaOfPapelTelaCollectionNewPapelTela = em.merge(oldIdTelaOfPapelTelaCollectionNewPapelTela);
                    }
                }
            }

            em.getTransaction().commit();
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
            if (em != null) {
                //em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tela tela;

            try {
                tela = em.getReference(Tela.class, id);
                tela.getIdTela();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tela with id " + id + " no longer exists.", enfe);
            }

            List<String> illegalOrphanMessages = null;
            Collection<UsuarioTela> usuarioTelaCollectionOrphanCheck = tela.getUsuarioTelaCollection();

            for (UsuarioTela usuarioTelaCollectionOrphanCheckUsuarioTela : usuarioTelaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }

                illegalOrphanMessages.add("This Tela (" + tela + ") cannot be destroyed since the UsuarioTela " + usuarioTelaCollectionOrphanCheckUsuarioTela + " in its usuarioTelaCollection field has a non-nullable idTela field.");
            }

            Collection<PapelTela> papelTelaCollectionOrphanCheck = tela.getPapelTelaCollection();

            for (PapelTela papelTelaCollectionOrphanCheckPapelTela : papelTelaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }

                illegalOrphanMessages.add("This Tela (" + tela + ") cannot be destroyed since the PapelTela " + papelTelaCollectionOrphanCheckPapelTela + " in its papelTelaCollection field has a non-nullable idTela field.");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            em.remove(tela);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
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
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tela.class));
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

    public Tela findTela(Integer id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(Tela.class, id);
        } finally {
            //em.close();
        }
    }

    public int getTelaCount() {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tela> rt = cq.from(Tela.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

    public Tela findTelaByName(String nmTela) {
        EntityManager em = getEntityManager();

        try {
            return (Tela) em.createNamedQuery("Tela.findByNmTela", Tela.class).setParameter("nmTela", nmTela).getSingleResult();
        } finally {
            //em.close();
        }
    }
}
