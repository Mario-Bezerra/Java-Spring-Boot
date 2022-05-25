package br.com.escola.Forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.escola.Forum.modelo.Usuario;
import br.com.escola.Forum.repository.UsuarioRepository;

public class AutenticacaoTokenFilter extends OncePerRequestFilter{

	private TokenServiceForum tokenService;
	
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoTokenFilter(TokenServiceForum tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperaToken(request);
		boolean valido = tokenService.isValidToken(token);
		if (valido) {
	        autenticarCliente(token);
	    }
		
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		Long usuarioId = tokenService.getIdUsuario(token);
		Usuario usuario = usuarioRepository.findById(usuarioId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public String recuperaToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
		return null;
		}
		
		return token.substring(7, token.length());
	}

}
