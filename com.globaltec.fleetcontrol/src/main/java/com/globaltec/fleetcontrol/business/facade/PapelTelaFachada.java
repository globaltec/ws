/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.business.facade;

import com.globaltec.fleetcontrol.business.entity.PapelTela;
import com.globaltec.fleetcontrol.persistence.dao.PapelTelaDAO;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Carlos Octaviano
 */
public class PapelTelaFachada implements ICrud<PapelTela> {

    @Override
    public void inserir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof PapelTela)) {
            throw new RuntimeException("Somente um objeto do tipo PapelTela é esperado como parâmetro.");
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
        PapelTela papelTela = (PapelTela) parametros[0];
        papelTela.setDtInclusao(new Date());

        PapelTelaDAO papelTelaDAO = new PapelTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());
        papelTelaDAO.create(papelTela);
    }

    @Override
    public void alterar(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof PapelTela)) {
            throw new RuntimeException("Somente um objeto do tipo PapelTela é esperado como parâmetro.");
        }

        PapelTela papelTela = (PapelTela) parametros[0];

        PapelTelaDAO papelTelaDAO = new PapelTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        papelTelaDAO.edit(papelTela);
    }

    @Override
    public void excluir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof PapelTela)) {
            throw new RuntimeException("Somente um objeto do tipo PapelTela é esperado como parâmetro.");
        }

        PapelTela papelTela = (PapelTela) parametros[0];

        PapelTelaDAO papelTelaDAO = new PapelTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        papelTelaDAO.destroy(papelTela.getIdPapelTela());
    }

    @Override
    public PapelTela recuperarPorId(Integer id) throws Exception {
        PapelTelaDAO papelTelaDAO = new PapelTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return papelTelaDAO.findPapelTela(id);
    }

    @Override
    public List<PapelTela> recuperarTodos() throws Exception {
        PapelTelaDAO papelTelaDAO = new PapelTelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return papelTelaDAO.findPapelTelaEntities();
    }
}
