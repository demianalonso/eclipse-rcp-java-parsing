package ar.edu.utn.frba.tadp.java.parsing.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {
	List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private final CompilationUnit parse;

	public MethodVisitor(CompilationUnit parse) {
		this.parse = parse;
	}

	@Override
	public boolean visit(CastExpression node) {
		System.out.println("Se esta casteando en la linea "
				+ parse.getLineNumber(node.getStartPosition())
				+ " columna "
				+ parse.getColumnNumber(node.getStartPosition())
				+ "... manejalo por favor.");

		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		methods.add(node);
		return super.visit(node);
	}

	public List<MethodDeclaration> getMethods() {
		return methods;
	}

}