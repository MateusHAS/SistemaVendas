package br.com.vendas.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.vendas.Bean.AutenticacaoBean;
import br.com.vendas.model.Usuario;

public class AutenticacaoListener implements PhaseListener{

	@Override
	public void afterPhase(PhaseEvent arg0) {
		
		String paginaAtual = Faces.getViewId();
		
		
		boolean pagAut = paginaAtual.contains("autenticacao.xhtml");
		
		if(!pagAut){
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		
			if(autenticacaoBean == null){
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
			
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
				
			
			if(usuario == null){
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
		
		}
		
		
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}
