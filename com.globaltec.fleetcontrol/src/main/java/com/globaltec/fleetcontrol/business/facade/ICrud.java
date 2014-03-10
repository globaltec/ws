package com.globaltec.fleetcontrol.business.facade;

import java.util.List;

/**
 *
 * @author RdOC
 */
public interface ICrud<T> {

    public void inserir(Object... parametros) throws Exception;

    public void alterar(Object... parametros) throws Exception;

    public void excluir(Object... parametros) throws Exception;

    public T recuperarPorId(Integer id) throws Exception;

    public List<T> recuperarTodos() throws Exception;

}
