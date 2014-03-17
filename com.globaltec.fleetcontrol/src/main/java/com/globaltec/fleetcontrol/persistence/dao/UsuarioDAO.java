/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.Usuario;
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
public class UsuarioDAO implements Serializable {

    public UsuarioDAO(EntityManager em) {
        this.entityManager = em;
    }

    private EntityManager entityManager = null;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void create(Usuario usuario) {
        if (usuario.getUsuarioTelaCollection() == null) {
            usuario.setUsuarioTelaCollection(new ArrayList<UsuarioTela>());
        }

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Papel idPapel = usuario.getIdPapel();

            if (idPapel != null) {
                idPapel = em.getReference(idPapel.getClass(), idPapel.getIdPapel());
                usuario.setIdPapel(idPapel);
            }

            Collection<UsuarioTela> attachedUsuarioTelaCollection = new ArrayList<UsuarioTela>();

            for (UsuarioTela usuarioTelaCollectionUsuarioTelaToAttach : usuario.getUsuarioTelaCollection()) {
                usuarioTelaCollectionUsuarioTelaToAttach = em.getReference(usuarioTelaCollectionUsuarioTelaToAttach.getClass(), usuarioTelaCollectionUsuarioTelaToAttach.getIdUsuarioTela());
                attachedUsuarioTelaCollection.add(usuarioTelaCollectionUsuarioTelaToAttach);
            }

            usuario.setUsuarioTelaCollection(attachedUsuarioTelaCollection);
            em.persist(usuario);

            if (idPapel != null) {
                idPapel.getUsuarioCollection().add(usuario);
                idPapel = em.merge(idPapel);
            }

            for (UsuarioTela usuarioTelaCollectionUsuarioTela : usuario.getUsuarioTelaCollection()) {
                Usuario oldIdUsuarioOfUsuarioTelaCollectionUsuarioTela = usuarioTelaCollectionUsuarioTela.getIdUsuario();
                usuarioTelaCollectionUsuarioTela.setIdUsuario(usuario);
                usuarioTelaCollectionUsuarioTela = em.merge(usuarioTelaCollectionUsuarioTela);

                if (oldIdUsuarioOfUsuarioTelaCollectionUsuarioTela != null) {
                    oldIdUsuarioOfUsuarioTelaCollectionUsuarioTela.getUsuarioTelaCollection().remove(usuarioTelaCollectionUsuarioTela);
                    oldIdUsuarioOfUsuarioTelaCollectionUsuarioTela = em.merge(oldIdUsuarioOfUsuarioTelaCollectionUsuarioTela);
                }
            }

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                //em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Papel idPapelOld = persistentUsuario.getIdPapel();
            Papel idPapelNew = usuario.getIdPapel();
            Collection<UsuarioTela> usuarioTelaCollectionOld = persistentUsuario.getUsuarioTelaCollection();
            Collection<UsuarioTela> usuarioTelaCollectionNew = usuario.getUsuarioTelaCollection();
            List<String> illegalOrphanMessages = null;

            for (UsuarioTela usuarioTelaCollectionOldUsuarioTela : usuarioTelaCollectionOld) {
                if (!usuarioTelaCollectionNew.contains(usuarioTelaCollectionOldUsuarioTela)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }

                    illegalOrphanMessages.add("You must retain UsuarioTela " + usuarioTelaCollectionOldUsuarioTela + " since its idUsuario field is not nullable.");
                }
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            if (idPapelNew != null) {
                idPapelNew = em.getReference(idPapelNew.getClass(), idPapelNew.getIdPapel());
                usuario.setIdPapel(idPapelNew);
            }

            Collection<UsuarioTela> attachedUsuarioTelaCollectionNew = new ArrayList<UsuarioTela>();

            for (UsuarioTela usuarioTelaCollectionNewUsuarioTelaToAttach : usuarioTelaCollectionNew) {
                usuarioTelaCollectionNewUsuarioTelaToAttach = em.getReference(usuarioTelaCollectionNewUsuarioTelaToAttach.getClass(), usuarioTelaCollectionNewUsuarioTelaToAttach.getIdUsuarioTela());
                attachedUsuarioTelaCollectionNew.add(usuarioTelaCollectionNewUsuarioTelaToAttach);
            }

            usuarioTelaCollectionNew = attachedUsuarioTelaCollectionNew;
            usuario.setUsuarioTelaCollection(usuarioTelaCollectionNew);
            usuario = em.merge(usuario);

            if (idPapelOld != null && !idPapelOld.equals(idPapelNew)) {
                idPapelOld.getUsuarioCollection().remove(usuario);
                idPapelOld = em.merge(idPapelOld);
            }

            if (idPapelNew != null && !idPapelNew.equals(idPapelOld)) {
                idPapelNew.getUsuarioCollection().add(usuario);
                idPapelNew = em.merge(idPapelNew);
            }

            for (UsuarioTela usuarioTelaCollectionNewUsuarioTela : usuarioTelaCollectionNew) {
                if (!usuarioTelaCollectionOld.contains(usuarioTelaCollectionNewUsuarioTela)) {
                    Usuario oldIdUsuarioOfUsuarioTelaCollectionNewUsuarioTela = usuarioTelaCollectionNewUsuarioTela.getIdUsuario();
                    usuarioTelaCollectionNewUsuarioTela.setIdUsuario(usuario);
                    usuarioTelaCollectionNewUsuarioTela = em.merge(usuarioTelaCollectionNewUsuarioTela);

                    if (oldIdUsuarioOfUsuarioTelaCollectionNewUsuarioTela != null && !oldIdUsuarioOfUsuarioTelaCollectionNewUsuarioTela.equals(usuario)) {
                        oldIdUsuarioOfUsuarioTelaCollectionNewUsuarioTela.getUsuarioTelaCollection().remove(usuarioTelaCollectionNewUsuarioTela);
                        oldIdUsuarioOfUsuarioTelaCollectionNewUsuarioTela = em.merge(oldIdUsuarioOfUsuarioTelaCollectionNewUsuarioTela);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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

            List<String> illegalOrphanMessages = null;
            Collection<UsuarioTela> usuarioTelaCollectionOrphanCheck = usuario.getUsuarioTelaCollection();

            for (UsuarioTela usuarioTelaCollectionOrphanCheckUsuarioTela : usuarioTelaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }

                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioTela " + usuarioTelaCollectionOrphanCheckUsuarioTela + " in its usuarioTelaCollection field has a non-nullable idUsuario field.");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
            return (Usuario) em.createNamedQuery("Usuario.findByNmLogin", Usuario.class).setParameter("nmLogin", login.toUpperCase()).getSingleResult();
        } finally {
            //em.close();
        }
    }
}
