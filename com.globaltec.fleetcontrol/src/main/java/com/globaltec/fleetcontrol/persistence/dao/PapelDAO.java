/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.business.entity.Tela;
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
public class PapelDAO implements Serializable {

    private EntityManager em = null;

    public PapelDAO(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(Papel papel) {
        if (papel.getUsuarios() == null) {
            papel.setUsuarios(new ArrayList<Usuario>());
        }

        if (papel.getTelas() == null) {
            papel.setTelas(new ArrayList<Tela>());
        }

        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();

            Collection<Usuario> attachedUsuarios = new ArrayList<Usuario>();

            for (Usuario usuariosUsuarioToAttach : papel.getUsuarios()) {
                usuariosUsuarioToAttach = eManager.getReference(usuariosUsuarioToAttach.getClass(), usuariosUsuarioToAttach.getIdUsuario());
                attachedUsuarios.add(usuariosUsuarioToAttach);
            }

            papel.setUsuarios(attachedUsuarios);
            Collection<Tela> attachedTelas = new ArrayList<Tela>();

            for (Tela telasTelaToAttach : papel.getTelas()) {
                telasTelaToAttach = eManager.getReference(telasTelaToAttach.getClass(), telasTelaToAttach.getIdTela());
                attachedTelas.add(telasTelaToAttach);
            }

            papel.setTelas(attachedTelas);

            eManager.persist(papel);

            for (Usuario usuariosUsuario : papel.getUsuarios()) {
                usuariosUsuario.getPapeis().add(papel);
                usuariosUsuario = eManager.merge(usuariosUsuario);
            }

            for (Tela telasTela : papel.getTelas()) {
                telasTela.getPapeis().add(papel);
                telasTela = eManager.merge(telasTela);
            }

            eManager.getTransaction().commit();
        } finally {
            if (eManager != null) {
                //em.close();
            }
        }
    }

    public void edit(Papel papel) throws NonexistentEntityException, Exception {
        EntityManager eManager = null;

        try {
            eManager = getEntityManager();
            eManager.getTransaction().begin();

            Papel persistentPapel = eManager.find(Papel.class, papel.getIdPapel());
            Collection<Usuario> usuariosOld = persistentPapel.getUsuarios();
            Collection<Usuario> usuariosNew = papel.getUsuarios();
            Collection<Tela> telasOld = persistentPapel.getTelas();
            Collection<Tela> telasNew = papel.getTelas();
            Collection<Usuario> attachedUsuariosNew = new ArrayList<Usuario>();

            for (Usuario usuariosNewUsuarioToAttach : usuariosNew) {
                usuariosNewUsuarioToAttach = eManager.getReference(usuariosNewUsuarioToAttach.getClass(), usuariosNewUsuarioToAttach.getIdUsuario());
                attachedUsuariosNew.add(usuariosNewUsuarioToAttach);
            }

            usuariosNew = attachedUsuariosNew;
            papel.setUsuarios(usuariosNew);
            Collection<Tela> attachedTelasNew = new ArrayList<Tela>();

            for (Tela telasNewTelaToAttach : telasNew) {
                telasNewTelaToAttach = eManager.getReference(telasNewTelaToAttach.getClass(), telasNewTelaToAttach.getIdTela());
                attachedTelasNew.add(telasNewTelaToAttach);
            }

            telasNew = attachedTelasNew;
            papel.setTelas(telasNew);

            papel = eManager.merge(papel);

            for (Usuario usuariosOldUsuario : usuariosOld) {
                if (!usuariosNew.contains(usuariosOldUsuario)) {
                    usuariosOldUsuario.getPapeis().remove(papel);
                    usuariosOldUsuario = eManager.merge(usuariosOldUsuario);
                }
            }

            for (Usuario usuariosNewUsuario : usuariosNew) {
                if (!usuariosOld.contains(usuariosNewUsuario)) {
                    usuariosNewUsuario.getPapeis().add(papel);
                    usuariosNewUsuario = eManager.merge(usuariosNewUsuario);
                }
            }

            for (Tela telasOldTela : telasOld) {
                if (!telasNew.contains(telasOldTela)) {
                    telasOldTela.getPapeis().remove(papel);
                    telasOldTela = eManager.merge(telasOldTela);
                }
            }

            for (Tela telasNewTela : telasNew) {
                if (!telasOld.contains(telasNewTela)) {
                    telasNewTela.getPapeis().add(papel);
                    telasNewTela = eManager.merge(telasNewTela);
                }
            }

            eManager.getTransaction().commit();
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

            Papel papel;

            try {
                papel = eManager.getReference(Papel.class, id);
                papel.getIdPapel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The papel with id " + id + " no longer exists.", enfe);
            }

            Collection<Usuario> usuarios = papel.getUsuarios();

            for (Usuario usuariosUsuario : usuarios) {
                usuariosUsuario.getPapeis().remove(papel);
                usuariosUsuario = eManager.merge(usuariosUsuario);
            }

            Collection<Tela> telas = papel.getTelas();

            for (Tela telasTela : telas) {
                telasTela.getPapeis().remove(papel);
                telasTela = eManager.merge(telasTela);
            }

            eManager.remove(papel);
            eManager.getTransaction().commit();
        } finally {
            if (eManager != null) {
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
        EntityManager eManager = getEntityManager();

        try {
            CriteriaQuery cq = eManager.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Papel.class));
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

    public Papel findPapel(Integer id) {
        EntityManager eManager = getEntityManager();

        try {
            return eManager.find(Papel.class, id);
        } finally {
            //em.close();
        }
    }

    public int getPapelCount() {
        EntityManager eManager = getEntityManager();

        try {
            CriteriaQuery cq = eManager.getCriteriaBuilder().createQuery();
            Root<Papel> rt = cq.from(Papel.class);
            cq.select(eManager.getCriteriaBuilder().count(rt));
            Query q = eManager.createQuery(cq);

            return ((Long) q.getSingleResult()).intValue();
        } finally {
            //em.close();
        }
    }

    public Papel findPapelByCode(String cdPapel) {
        EntityManager eManager = getEntityManager();

        try {
            return (Papel) eManager.createNamedQuery("Papel.findByCdPapel", Papel.class).setParameter("cdPapel", cdPapel).getSingleResult();
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
