/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.business.facade;

import com.globaltec.fleetcontrol.business.entity.Tela;
import com.globaltec.fleetcontrol.persistence.dao.TelaDAO;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Carlos Octaviano
 */
public class TelaFachada implements ICrud<Tela> {

    @Override
    public void inserir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Tela)) {
            throw new RuntimeException("Somente um objeto do tipo Tela é esperado como parâmetro.");
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
        Tela tela = (Tela) parametros[0];
        tela.setDtInclusao(new Date());

        TelaDAO telaDAO = new TelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());
        telaDAO.create(tela);
    }

    @Override
    public void alterar(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Tela)) {
            throw new RuntimeException("Somente um objeto do tipo Tela é esperado como parâmetro.");
        }

        Tela tela = (Tela) parametros[0];

        TelaDAO telaDAO = new TelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        Tela tela_alt = telaDAO.findTelaByName(tela.getNmTela());
        tela_alt.setDsTela(tela.getDsTela());
        tela_alt.setDsCaminhoArquivo(tela.getDsCaminhoArquivo());
        tela_alt.setDtAlteracao(new Date());

        telaDAO.edit(tela_alt);
    }

    @Override
    public void excluir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Tela)) {
            throw new RuntimeException("Somente um objeto do tipo Tela é esperado como parâmetro.");
        }

        Tela tela = (Tela) parametros[0];

        TelaDAO telaDAO = new TelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        Tela papel_exc = telaDAO.findTelaByName(tela.getNmTela());

        telaDAO.destroy(papel_exc.getIdTela());
    }

    @Override
    public Tela recuperarPorId(Integer id) throws Exception {
        TelaDAO telaDAO = new TelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return telaDAO.findTela(id);
    }

    @Override
    public List<Tela> recuperarTodos() throws Exception {
        TelaDAO telaDAO = new TelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return telaDAO.findTelaEntities();
    }

    public Tela recuperarPorNome(String nmTela) throws Exception {
        TelaDAO telaDAO = new TelaDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return telaDAO.findTelaByName(nmTela);
    }
}
