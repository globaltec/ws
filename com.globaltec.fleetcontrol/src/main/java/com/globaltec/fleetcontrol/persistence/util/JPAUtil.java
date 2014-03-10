package com.globaltec.fleetcontrol.persistence.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author RdOC
 */
public abstract class JPAUtil {
    /*
     * Métodos abstratos que são implementados pelas subclasses para que
     * seja possível aplicar o padrão de projeto Template Method nesta classe.
     *
     * Todos são utilizados diretamente (por exemplo, this.getThreadDeSessao().get()),
     * porque as variáveis dos get's são inicializadas estaticamente nas subclasses,
     * não tendo como resultar null qualquer chamada dos métodos abstratos abaixo e,
     * conseqüentemente, dar um NullPointerException.
     */

    public abstract EntityManagerFactory getEntityManagerFactory();

    public abstract ThreadLocal<EntityManager> getThreadDoEntityManager();

    public abstract ThreadLocal<EntityTransaction> getThreadDaTransacao();

    public EntityManager recuperarGerenciadorDeEntidades() throws PersistenceException {
        EntityManager gerenciadorDeEntidades = this.getThreadDoEntityManager().get();

        try {
            if (gerenciadorDeEntidades == null) {
                gerenciadorDeEntidades = this.getEntityManagerFactory().createEntityManager();
                this.getThreadDoEntityManager().set(gerenciadorDeEntidades);
            }
        } catch (Exception e) {
            throw (new PersistenceException(e));
        }

        return gerenciadorDeEntidades;
    }

    public void fecharGerenciadorDeEntidades() throws PersistenceException {
        try {
            EntityManager gerenciadorDeEntidades = this.getThreadDoEntityManager().get();

            if (gerenciadorDeEntidades != null && gerenciadorDeEntidades.isOpen()) {
                gerenciadorDeEntidades.close();
            }
        } catch (Exception e) {
            throw (new PersistenceException(e));
        } finally {
            /**
             * Para garantir que tanto o gerenciador de entidades quanto a
             * transação dos ThreadLocal's serão liberados.
             */
            this.getThreadDoEntityManager().set(null);
            this.getThreadDaTransacao().set(null);
        }
    }

    public void iniciarTransacao() throws PersistenceException {
        EntityTransaction transacao = this.getThreadDaTransacao().get();

        try {
            if (transacao == null) {
                transacao = this.recuperarGerenciadorDeEntidades().getTransaction();
                transacao.begin();
                this.getThreadDaTransacao().set(transacao);
            }
        } catch (Exception e) {
            throw (new PersistenceException(e));
        }
    }

    public void persistirTransacao() throws PersistenceException {
        EntityTransaction transacao = this.getThreadDaTransacao().get();

        try {
            this.getThreadDaTransacao().set(null);

            if ((transacao != null) && transacao.isActive()) {
                transacao.commit();
            }
        } catch (Exception e) {
            this.desfazerTransacao();
            throw (new PersistenceException(e));
        }
    }

    public void desfazerTransacao() throws PersistenceException {
        EntityTransaction transacao = this.getThreadDaTransacao().get();

        try {
            if ((transacao != null) && transacao.isActive()) {
                transacao.rollback();
            }
        } catch (Exception e) {
            throw (new PersistenceException(e));
        } finally {
            this.fecharGerenciadorDeEntidades();
        }
    }
}
