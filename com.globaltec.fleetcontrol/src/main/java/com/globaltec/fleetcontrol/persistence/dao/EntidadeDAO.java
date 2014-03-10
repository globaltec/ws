/*
 * EntidadeDAO.java
 *
 * Created on 1 de Agosto de 2006, 14:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.persistence.dao;

import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author RdOC
 * @param <T>
 */
public abstract class EntidadeDAO<T> implements Serializable {

    public EntidadeDAO() {
        JPAUtilFleetControl.getInstance().iniciarTransacao();
    }

    public void alterar(T $objeto) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        gerenciadorDeEntidade.merge($objeto);
    }

    public T anexar(T $objeto) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        return gerenciadorDeEntidade.merge($objeto);
    }

    public void desfazer() throws PersistenceException {
        JPAUtilFleetControl.getInstance().desfazerTransacao();
    }

    public void excluir(T $objeto) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        gerenciadorDeEntidade.remove($objeto);
    }

    public void persistir() throws PersistenceException {
        JPAUtilFleetControl.getInstance().persistirTransacao();
    }

    public void inserir(T $objeto) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        gerenciadorDeEntidade.persist($objeto);
    }

    public static <T> void sincronizar(T $objeto) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        gerenciadorDeEntidade.refresh($objeto);
    }

    public void sincronizar() throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        gerenciadorDeEntidade.flush();
    }

    @SuppressWarnings("unchecked")
    public static <T> T recuperarPorId(Integer $identificadorDoObjeto, Class<T> $classe) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        return ((T) gerenciadorDeEntidade.find($classe, $identificadorDoObjeto));
    }

    public static <T> List recuperarTodos(String $atributoDeOrdenacao, Class<T> $classe) throws PersistenceException {
        EntityManager gerenciadorDeEntidade = JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades();
        return gerenciadorDeEntidade.createQuery("select obj from " + $classe.getSimpleName() + " as obj order by obj." + $atributoDeOrdenacao).getResultList();
    }
}
