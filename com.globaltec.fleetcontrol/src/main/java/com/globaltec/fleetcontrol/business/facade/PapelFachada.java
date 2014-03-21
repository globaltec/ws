/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globaltec.fleetcontrol.business.facade;

import com.globaltec.fleetcontrol.business.entity.Papel;
import com.globaltec.fleetcontrol.persistence.dao.PapelDAO;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Carlos Octaviano
 */
public class PapelFachada implements ICrud<Papel> {

    @Override
    public void inserir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Papel)) {
            throw new RuntimeException("Somente um objeto do tipo Papel é esperado como parâmetro.");
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
        Papel papel = (Papel) parametros[0];
        papel.setDtInclusao(new Date());

        PapelDAO papelDAO = new PapelDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());
        papelDAO.create(papel);
    }

    @Override
    public void alterar(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Papel)) {
            throw new RuntimeException("Somente um objeto do tipo Papel é esperado como parâmetro.");
        }

        Papel papel = (Papel) parametros[0];

        PapelDAO papelDAO = new PapelDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        Papel papel_alt = papelDAO.findPapelByCode(papel.getCdPapel());
        papel_alt.setDsPapel(papel.getDsPapel());
        papel_alt.setDtAlteracao(new Date());

        papelDAO.edit(papel_alt);
    }

    @Override
    public void excluir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Papel)) {
            throw new RuntimeException("Somente um objeto do tipo Papel é esperado como parâmetro.");
        }

        Papel papel = (Papel) parametros[0];

        PapelDAO papelDAO = new PapelDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        Papel papel_exc = papelDAO.findPapelByCode(papel.getCdPapel());

        papelDAO.destroy(papel_exc.getIdPapel());
    }

    @Override
    public Papel recuperarPorId(Integer id) throws Exception {
        PapelDAO papelDAO = new PapelDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return papelDAO.findPapel(id);
    }

    @Override
    public List<Papel> recuperarTodos() throws Exception {
        PapelDAO papelDAO = new PapelDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return papelDAO.findPapelEntities();
    }

    public Papel recuperarPorCodigo(String cdPapel) throws Exception {
        PapelDAO papelDAO = new PapelDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return papelDAO.findPapelByCode(cdPapel);
    }
}
