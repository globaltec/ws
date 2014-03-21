package com.globaltec.fleetcontrol.business.facade;

import com.globaltec.fleetcontrol.business.entity.Usuario;
import com.globaltec.fleetcontrol.persistence.dao.UsuarioDAO;
import com.globaltec.fleetcontrol.persistence.util.JPAUtilFleetControl;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Carlos Octaviano
 */
@Stateless
public class LoginFachada {

    private static final String NAO_FOI_POSSIVEL_EFETUAR_O_LOGIN = "N\u00e3o foi poss\u00edvel efetuar o login";
    private static final String NAO_FOI_POSSIVEL_EFETUAR_O_LOGOUT = "N\u00e3o foi poss\u00edvel efetuar o logout";
    private static final String NAO_FOI_POSSIVEL_CONECTAR_BD = "N\u00e3o foi poss\u00edvel conectar com o banco de dados";
    private static final String NAO_FOI_POSSIVEL_PERSISTIR_BD = "Erro ao realizar consulta no banco de dados";

    public Usuario login(String login, String senha) throws LoginException {
        try {
            UsuarioDAO user = new UsuarioDAO(JPAUtilFleetControl.getInstance().recuperarGerenciadorDeEntidades());

            Usuario usuario = user.findUsuarioByLogin(login);

            if (usuario != null) {
                if (usuario.getSnUsuario().toUpperCase().equals(senha.toUpperCase())) {
                    return usuario;
                } else {
                    throw new LoginException(NAO_FOI_POSSIVEL_EFETUAR_O_LOGIN);
                }
            } else {
                throw new LoginException(NAO_FOI_POSSIVEL_EFETUAR_O_LOGIN);
            }
        } catch (LoginException e) {
            throw new LoginException(NAO_FOI_POSSIVEL_EFETUAR_O_LOGIN);
        } catch (PersistenceException e) {
            throw new PersistenceException(NAO_FOI_POSSIVEL_PERSISTIR_BD);
        } catch (Exception e) {
            throw new RuntimeException(NAO_FOI_POSSIVEL_EFETUAR_O_LOGIN);
        }
    }

    /*public void logout() throws LoginException {
     HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
     try {
     request.logout();
     } catch (ServletException ex) {
     throw new LoginException(NAO_FOI_POSSIVEL_EFETUAR_O_LOGOUT);
     }
     }*/
}
