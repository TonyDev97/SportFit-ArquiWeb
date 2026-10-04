package com.upc.sportfit.security.controllers;

import com.upc.sportfit.entidades.Rol;
import com.upc.sportfit.entidades.Usuario;
import com.upc.sportfit.repositorios.RolRepositorio;
import com.upc.sportfit.security.dtos.AuthRequestDTO;
import com.upc.sportfit.security.dtos.AuthResponseDTO;
import com.upc.sportfit.security.dtos.RegistroRequestDTO;
import com.upc.sportfit.security.services.CustomUserDetailsService;
import com.upc.sportfit.security.util.JwtUtil;
import com.upc.sportfit.servicios.UsuarioServicio;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioServicio usuarioServicio;
    private final RolRepositorio rolRepositorio;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UsuarioServicio usuarioServicio, RolRepositorio rolRepositorio) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioServicio = usuarioServicio;
        this.rolRepositorio = rolRepositorio;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getCorreo());
        final String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setRoles(roles);
        authResponseDTO.setJwt(token);
        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }

    @PostMapping("/auth/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO dto) {
        // Verificar si el correo ya existe
        if (usuarioServicio.buscarPorCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Buscar rol CLIENTE por defecto
        Rol rolCliente = rolRepositorio.findById(1)
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasenaHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setDni(dto.getDni());
        usuario.setTelefono(dto.getTelefono());
        usuario.setActivo(true);
        usuario.setRol(rolCliente);
        usuario.setCreadoPor("REGISTRO");
        usuario.setFCreacion(java.time.Instant.now());

        Usuario usuarioGuardado = usuarioServicio.insertar(usuario);

        // Generar JWT automáticamente
        final UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioGuardado.getCorreo());
        final String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setRoles(roles);
        authResponseDTO.setJwt(token);
        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }
}