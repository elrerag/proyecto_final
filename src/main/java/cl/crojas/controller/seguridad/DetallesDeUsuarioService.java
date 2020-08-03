package cl.crojas.controller.seguridad;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.crojas.model.dao.UsuarioDao;
import cl.crojas.model.entity.Usuario;

@Service
public class DetallesDeUsuarioService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(DetallesDeUsuarioService.class);

    @Autowired
    private UsuarioDao daoUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = daoUsuario.findByCorreo(username);
        User usuarioSistema = null;

        if (usuario != null) {
            String rol = usuario.getRol().toString();
            String username_ = usuario.getCorreo();
            String password_ = usuario.getContrasenia();
            String nombre = usuario.getNombre();
            logger.warn("el usuario encontrado, nombre :" + nombre);

            // agregamos el rol a la lista de roles
            List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(rol));
            // acá se hace el login !!!
            usuarioSistema = new User(username_, password_, roles);
        } else {
            logger.warn("el usuario no pudo ser encontrado, username :" + username);
        }

        return usuarioSistema;
    }
    
}
