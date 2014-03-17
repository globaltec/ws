/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.business.facade;

import com.globaltec.fleetcontrol.business.entity.UsuarioTela;
import com.globaltec.fleetcontrol.persistence.dao.UsuarioTelaDAO;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Carlos Octaviano
 */
public class UsuarioTelaFachada implements ICrud<UsuarioTela> {

    @Override
    public void inserir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof UsuarioTela)) {
            throw new RuntimeException("Somente um objeto do tipo UsuarioTela é esperado como parâmetro.");
        }

        /*
         * O método de um fachada é útil para isolar as regras de negócio.
         * Por exemplo, aqui poderia ser feita uma validação se o CNPJ está
         * correto e lançada uma exceção CnpjInvalidoException caso tivesse
         * algum erro. Toda a inserção e validações seriam codificadas somente,
         * uma vez independentes quantas views (web, desktop ou dispositivo
         * móvel) chamassem o método inserir.
         *
         * Somente objetos de negócio e tipos e estruturas do Java SE são
         * permitidos como parâmetros (String, Cliente, ArrayList, etc). Métodos
         * de um fachada nunca devem receber parâmetros que indiquem com qual
         * tipo de apresentação o sistema está trabalhando. Por exemplo, se
         * fosse passado um objeto do tipo HttpSession para esse método, o
         * código de negócio estaria preso a uma implementação para a web. ISSO
         * É ERRADO! Nunca se esqueça disso, independente da linguagem utilizada.
         *
         */
        UsuarioTela usuarioTela = (UsuarioTela) parametros[0];
        usuarioTela.setDtInclusao(new Date());

        UsuarioTelaDAO usuarioTelaDAO = new UsuarioTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());
        usuarioTelaDAO.create(usuarioTela);
    }

    @Override
    public void alterar(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof UsuarioTela)) {
            throw new RuntimeException("Somente um objeto do tipo UsuarioTela é esperado como parâmetro.");
        }

        UsuarioTela usuarioTela = (UsuarioTela) parametros[0];

        UsuarioTelaDAO usuarioTelaDAO = new UsuarioTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        usuarioTelaDAO.edit(usuarioTela);
    }

    @Override
    public void excluir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof UsuarioTela)) {
            throw new RuntimeException("Somente um objeto do tipo UsuarioTela é esperado como parâmetro.");
        }

        UsuarioTela usuarioTela = (UsuarioTela) parametros[0];

        UsuarioTelaDAO usuarioTelaDAO = new UsuarioTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        usuarioTelaDAO.destroy(usuarioTela.getIdUsuarioTela());
    }

    @Override
    public UsuarioTela recuperarPorId(Integer id) throws Exception {
        UsuarioTelaDAO usuarioTelaDAO = new UsuarioTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return usuarioTelaDAO.findUsuarioTela(id);
    }

    @Override
    public List<UsuarioTela> recuperarTodos() throws Exception {
        UsuarioTelaDAO usuarioTelaDAO = new UsuarioTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return usuarioTelaDAO.findUsuarioTelaEntities();
    }
}
