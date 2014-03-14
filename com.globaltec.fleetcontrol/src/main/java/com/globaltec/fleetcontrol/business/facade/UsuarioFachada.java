package com.globaltec.fleetcontrol.business.facade;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.persistence.dao.UsuarioDAO;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Carlos A. Octaviano
 */
@Stateless
public class UsuarioFachada implements ICrud<Usuario> {

    public UsuarioFachada() {
    }

    @Override
    public void inserir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Usuario)) {
            throw new RuntimeException("Somente um objeto do tipo Usuario é esperado como parâmetro.");
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
        Usuario usuario = (Usuario) parametros[0];
        usuario.setDtInclusao(new Date());

        UsuarioDAO usuarioDAO = new UsuarioDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());
        usuarioDAO.create(usuario);
    }

    @Override
    public void alterar(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Usuario)) {
            throw new RuntimeException("Somente um objeto do tipo Usuario é esperado como parâmetro.");
        }

        Usuario usuario = (Usuario) parametros[0];

        UsuarioDAO usuarioDAO = new UsuarioDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        Usuario usuario_alt = usuarioDAO.findUsuarioByLogin(usuario.getNmLogin());
        usuario_alt.setSnUsuario(usuario.getSnUsuario());
        usuario_alt.setNmUsuario(usuario.getNmUsuario());
        usuario_alt.setDtAlteracao(new Date());

        usuarioDAO.edit(usuario_alt);
    }

    @Override
    public void excluir(Object... parametros) throws Exception {
        if (parametros.length != 1 || !(parametros[0] instanceof Usuario)) {
            throw new RuntimeException("Somente um objeto do tipo Usuario é esperado como parâmetro.");
        }

        Usuario usuario = (Usuario) parametros[0];

        UsuarioDAO usuarioDAO = new UsuarioDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        Usuario usuario_exc = usuarioDAO.findUsuarioByLogin(usuario.getNmLogin());

        usuarioDAO.destroy(usuario_exc.getIdUsuario());
    }

    @Override
    public Usuario recuperarPorId(Integer id) throws Exception {
        UsuarioDAO usuarioDAO = new UsuarioDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return usuarioDAO.findUsuario(id);
    }

    @Override
    public List<Usuario> recuperarTodos() throws Exception {
        UsuarioDAO usuarioDAO = new UsuarioDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

        return usuarioDAO.findUsuarioEntities();
    }
}
