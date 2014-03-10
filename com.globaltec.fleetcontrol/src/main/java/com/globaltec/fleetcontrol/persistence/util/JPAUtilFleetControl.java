package com.globaltec.fleetcontrol.persistence.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Reinaldo de Oliveira Castro
 * @enterprise Intellegentia Tecnologia Ltda.
 */
public class JPAUtilFleetControl extends JPAUtil {

    private EntityManagerFactory entityManagerFactory;
    private final ThreadLocal<EntityManager> threadDoEntityManager = new ThreadLocal<EntityManager>();
    private final ThreadLocal<EntityTransaction> threadDaTransacao = new ThreadLocal<EntityTransaction>();

    /**
     * Atributo para implementacao do padrao de projeto Singleton para que nao
     * seja necessario as classes DAO criar um objeto desta classe cada vez que
     * uma operacao no banco de dados via JPA seja necessaria.
     */
    private static final JPAUtilFleetControl instance = new JPAUtilFleetControl();

    /*
     * Construtor privado (padr�oo de projeto Singleton).
     */
    private JPAUtilFleetControl() {
        try {
            this.entityManagerFactory = Persistence.createEntityManagerFactory("fleetcontrolPU");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * M�todo que d� acesso a �nica instancia dessa classe (padr�o de projeto Singleton).
     */
    public static JPAUtilFleetControl getInstance() {
        return instance;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    @Override
    public ThreadLocal<EntityManager> getThreadDoEntityManager() {
        return this.threadDoEntityManager;
    }

    @Override
    public ThreadLocal<EntityTransaction> getThreadDaTransacao() {
        return this.threadDaTransacao;
    }
}
