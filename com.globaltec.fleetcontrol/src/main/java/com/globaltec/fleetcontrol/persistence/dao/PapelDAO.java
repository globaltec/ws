/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.PapelTela;
import com.globaltec.fleetcontrol.business.entity.Usuario;
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
public class PapelDAO implements Serializable {

    public PapelDAO(EntityManager em) {
        this.entityManager = em;
    }

    private EntityManager entityManager = null;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void create(Papel papel) {
        if (papel.getUsuarioCollection() == null) {
            papel.setUsuarioCollection(new ArrayList<Usuario>());
        }

        if (papel.getPapelTelaCollection() == null) {
            papel.setPapelTelaCollection(new ArrayList<PapelTela>());
        }

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();

            for (Usuario usuarioCollectionUsuarioToAttach : papel.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }

            papel.setUsuarioCollection(attachedUsuarioCollection);
            Collection<PapelTela> attachedPapelTelaCollection = new ArrayList<PapelTela>();

            for (PapelTela papelTelaCollectionPapelTelaToAttach : papel.getPapelTelaCollection()) {
                papelTelaCollectionPapelTelaToAttach = em.getReference(papelTelaCollectionPapelTelaToAttach.getClass(), papelTelaCollectionPapelTelaToAttach.getIdPapelTela());
                attachedPapelTelaCollection.add(papelTelaCollectionPapelTelaToAttach);
            }

            papel.setPapelTelaCollection(attachedPapelTelaCollection);
            em.persist(papel);

            for (Usuario usuarioCollectionUsuario : papel.getUsuarioCollection()) {
                Papel oldIdPapelOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdPapel();
                usuarioCollectionUsuario.setIdPapel(papel);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);

                if (oldIdPapelOfUsuarioCollectionUsuario != null) {
                    oldIdPapelOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdPapelOfUsuarioCollectionUsuario = em.merge(oldIdPapelOfUsuarioCollectionUsuario);
                }
            }

            for (PapelTela papelTelaCollectionPapelTela : papel.getPapelTelaCollection()) {
                Papel oldIdPapelOfPapelTelaCollectionPapelTela = papelTelaCollectionPapelTela.getIdPapel();
                papelTelaCollectionPapelTela.setIdPapel(papel);
                papelTelaCollectionPapelTela = em.merge(papelTelaCollectionPapelTela);

                if (oldIdPapelOfPapelTelaCollectionPapelTela != null) {
                    oldIdPapelOfPapelTelaCollectionPapelTela.getPapelTelaCollection().remove(papelTelaCollectionPapelTela);
                    oldIdPapelOfPapelTelaCollectionPapelTela = em.merge(oldIdPapelOfPapelTelaCollectionPapelTela);
                }
            }

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Papel papel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Papel persistentPapel = em.find(Papel.class, papel.getIdPapel());
            Collection<Usuario> usuarioCollectionOld = persistentPapel.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = papel.getUsuarioCollection();
            Collection<PapelTela> papelTelaCollectionOld = persistentPapel.getPapelTelaCollection();
            Collection<PapelTela> papelTelaCollectionNew = papel.getPapelTelaCollection();
            List<String> illegalOrphanMessages = null;

            for (PapelTela papelTelaCollectionOldPapelTela : papelTelaCollectionOld) {
                if (!papelTelaCollectionNew.contains(papelTelaCollectionOldPapelTela)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }

                    illegalOrphanMessages.add("You must retain PapelTela " + papelTelaCollectionOldPapelTela + " since its idPapel field is not nullable.");
                }
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();

            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }

            usuarioCollectionNew = attachedUsuarioCollectionNew;
            papel.setUsuarioCollection(usuarioCollectionNew);
            Collection<PapelTela> attachedPapelTelaCollectionNew = new ArrayList<PapelTela>();

            for (PapelTela papelTelaCollectionNewPapelTelaToAttach : papelTelaCollectionNew) {
                papelTelaCollectionNewPapelTelaToAttach = em.getReference(papelTelaCollectionNewPapelTelaToAttach.getClass(), papelTelaCollectionNewPapelTelaToAttach.getIdPapelTela());
                attachedPapelTelaCollectionNew.add(papelTelaCollectionNewPapelTelaToAttach);
            }

            papelTelaCollectionNew = attachedPapelTelaCollectionNew;
            papel.setPapelTelaCollection(papelTelaCollectionNew);
            papel = em.merge(papel);

            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.setIdPapel(null);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }

            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Papel oldIdPapelOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdPapel();
                    usuarioCollectionNewUsuario.setIdPapel(papel);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);

                    if (oldIdPapelOfUsuarioCollectionNewUsuario != null && !oldIdPapelOfUsuarioCollectionNewUsuario.equals(papel)) {
                        oldIdPapelOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdPapelOfUsuarioCollectionNewUsuario = em.merge(oldIdPapelOfUsuarioCollectionNewUsuario);
                    }
                }
            }

            for (PapelTela papelTelaCollectionNewPapelTela : papelTelaCollectionNew) {
                if (!papelTelaCollectionOld.contains(papelTelaCollectionNewPapelTela)) {
                    Papel oldIdPapelOfPapelTelaCollectionNewPapelTela = papelTelaCollectionNewPapelTela.getIdPapel();
                    papelTelaCollectionNewPapelTela.setIdPapel(papel);
                    papelTelaCollectionNewPapelTela = em.merge(papelTelaCollectionNewPapelTela);

                    if (oldIdPapelOfPapelTelaCollectionNewPapelTela != null && !oldIdPapelOfPapelTelaCollectionNewPapelTela.equals(papel)) {
                        oldIdPapelOfPapelTelaCollectionNewPapelTela.getPapelTelaCollection().remove(papelTelaCollectionNewPapelTela);
                        oldIdPapelOfPapelTelaCollectionNewPapelTela = em.merge(oldIdPapelOfPapelTelaCollectionNewPapelTela);
                    }
                }
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();

            if (msg == null || msg.length() == 0) {
                Integer id = papel.getIdPapel();

                if (findPapel(id) == null) {
                    throw new NonexistentEntityException("The papel with id " + id + " no longer exists.");
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
            Papel papel;

            try {
                papel = em.getReference(Papel.class, id);
                papel.getIdPapel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The papel with id " + id + " no longer exists.", enfe);
            }

            List<String> illegalOrphanMessages = null;
            Collection<PapelTela> papelTelaCollectionOrphanCheck = papel.getPapelTelaCollection();

            for (PapelTela papelTelaCollectionOrphanCheckPapelTela : papelTelaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }

                illegalOrphanMessages.add("This Papel (" + papel + ") cannot be destroyed since the PapelTela " + papelTelaCollectionOrphanCheckPapelTela + " in its papelTelaCollection field has a non-nullable idPapel field.");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            Collection<Usuario> usuarioCollection = papel.getUsuarioCollection();

            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.setIdPapel(null);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }

            em.remove(papel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public List<Papel> findPapelEntities() {
        return findPapelEntities(true, -1, -1);
    }

    public List<Papel> findPapelEntities(int maxResults, int firstResult) {
        return findPapelEntities(false, maxResults, firstResult);
    }

    private List<Papel> findPapelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Papel.class));
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

    public Papel findPapel(Integer id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(Papel.class, id);
        } finally {
            //em.close();
        }
    }

    public int getPapelCount() {
        EntityManager em = getEntityManager();

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Papel> rt = cq.from(Papel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

    public Papel findPapelByCode(String cdPapel) {
        EntityManager em = getEntityManager();

        try {
            return (Papel) em.createNamedQuery("Papel.findByCdPapel", Papel.class).setParameter("cdPapel", cdPapel).getSingleResult();
        } finally {
            //em.close();
        }
    }
}
