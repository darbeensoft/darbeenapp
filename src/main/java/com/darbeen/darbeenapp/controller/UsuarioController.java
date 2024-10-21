    package com.darbeen.darbeenapp.controller;

    import com.darbeen.darbeenapp.entity.Usuario;
    import com.darbeen.darbeenapp.repository.UsuarioRepository;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/usuarios")
    public class UsuarioController {

        private final UsuarioRepository usuarioRepository;

        public UsuarioController(UsuarioRepository usuarioRepository) {
            this.usuarioRepository = usuarioRepository;
        }

        @GetMapping
        public List<Usuario> getUsuarios() {
            return usuarioRepository.findAll();
        }

        @PostMapping
        public Usuario createUsuario(@RequestBody Usuario usuario) {
            return usuarioRepository.save(usuario);
        }
    }
