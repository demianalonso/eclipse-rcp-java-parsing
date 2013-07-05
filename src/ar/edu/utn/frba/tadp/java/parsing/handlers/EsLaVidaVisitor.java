package ar.edu.utn.frba.tadp.java.parsing.handlers;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;

public class EsLaVidaVisitor extends ASTVisitor {
	int cantidadFors = 0;
	int cantidadIfs = 0;

	@Override
	public boolean visit(ForStatement node) {
		cantidadFors++;
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		cantidadIfs++;
		return super.visit(node);
	}

	public boolean validar() {
		return cantidadFors == 1 && cantidadIfs == 1;
	}
}