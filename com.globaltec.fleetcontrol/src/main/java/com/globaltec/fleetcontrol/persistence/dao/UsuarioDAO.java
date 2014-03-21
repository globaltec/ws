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
public class UsuarioDAO implements Serializable {

    private EntityManager em = null;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(Usuario usuario) {
        if (usuario.getPapeis() == null) {
            usuario.setPapeis(new ArrayList<Papel>());
        }

        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();

            Collection<Papel> attachedPapeis = new ArrayList<Papel>();

            for (Papel papeisPapelToAttach : usuario.getPapeis()) {
                papeisPapelToAttach = eManager.getReference(papeisPapelToAttach.getClass(), papeisPapelToAttach.getIdPapel());
                attachedPapeis.add(papeisPapelToAttach);
            }

            usuario.setPapeis(attachedPapeis);

            eManager.persist(usuario);

            for (Papel papeisPapel : usuario.getPapeis()) {
                papeisPapel.getUsuarios().add(usuario);
                papeisPapel = eManager.merge(papeisPapel);
            }

            eManager.getTransaction().commit();
        } finally {
            if (eManager != null) {
                //em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();

            Usuario persistentUsuario = eManager.find(Usuario.class, usuario.getIdUsuario());
            Collection<Papel> papeisOld = persistentUsuario.getPapeis();
            Collection<Papel> papeisNew = usuario.getPapeis();
            Collection<Papel> attachedPapeisNew = new ArrayList<Papel>();

            for (Papel papeisNewPapelToAttach : papeisNew) {
                papeisNewPapelToAttach = eManager.getReference(papeisNewPapelToAttach.getClass(), papeisNewPapelToAttach.getIdPapel());
                attachedPapeisNew.add(papeisNewPapelToAttach);
            }

            papeisNew = attachedPapeisNew;
            usuario.setPapeis(papeisNew);

            usuario = eManager.merge(usuario);

            for (Papel papeisOldPapel : papeisOld) {
                if (!papeisNew.contains(papeisOldPapel)) {
                    papeisOldPapel.getUsuarios().remove(usuario);
                    papeisOldPapel = eManager.merge(papeisOldPapel);
                }
            }

            for (Papel papeisNewPapel : papeisNew) {
                if (!papeisOld.contains(papeisNewPapel)) {
                    papeisNewPapel.getUsuarios().add(usuario);
                    papeisNewPapel = eManager.merge(papeisNewPapel);
                }
            }

            eManager.getTransaction().commit();
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

            Usuario usuario;

            try {
                usuario = eManager.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }

            Collection<Papel> papeis = usuario.getPapeis();

            for (Papel papeisPapel : papeis) {
                papeisPapel.getUsuarios().remove(usuario);
                papeisPapel = eManager.merge(papeisPapel);
            }

            eManager.remove(usuario);
            eManager.getTransaction().commit();
        } finally {
            if (eManager != null) {
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
        EntityManager eManager = getEntityManager();

        try {
            CriteriaQuery cq = eManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager eManager = getEntityManager();

        try {
            return eManager.find(Usuario.class, id);
        } finally {
            //em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager eManager = getEntityManager();

        try {
            CriteriaQuery cq = eManager.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(eManager.getCriteriaBuilder().count(rt));
            Query q = eManager.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

    public Usuario findUsuarioByLogin(String nmLogin) {
        EntityManager eManager = getEntityManager();

        try {
            return (Usuario) eManager.createNamedQuery("Usuario.findByNmLogin", Usuario.class).setParameter("nmLogin", nmLogin.toUpperCase()).getSingleResult();
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
