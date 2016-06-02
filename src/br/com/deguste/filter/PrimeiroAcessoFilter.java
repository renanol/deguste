package br.com.deguste.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.deguste.model.entity.Usuario;
import br.com.deguste.util.NavigationUtil;
import br.com.deguste.util.SessionControl;



public class PrimeiroAcessoFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		if(session != null){
			Usuario usuario = (Usuario) session.getAttribute(SessionControl.USUARIO);
			if(usuario != null)
				if (usuario.isPrimeiroAcesso())
					chain.doFilter(request, response);
				else
					res.sendRedirect("pages/init.jsf");
			else
				res.sendRedirect(NavigationUtil.TO_HOME);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}