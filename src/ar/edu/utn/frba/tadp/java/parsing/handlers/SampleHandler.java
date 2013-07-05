package ar.edu.utn.frba.tadp.java.parsing.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import ar.edu.utn.frba.tadp.java.parsing.ast.ASTParsing;
import ar.edu.utn.frba.tadp.java.parsing.javamodel.JavaModelParsing;

public class SampleHandler extends AbstractHandler {

	public static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";

	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println("*******************************************");
		System.out.println("Parseo usando JavaModel");
		System.out.println("");
		new JavaModelParsing().analyseJavaModel();

		System.out.println("\n\n");
		System.out.println("*******************************************");
		System.out.println("Parseo usando el AST");
		System.out.println("");
		new ASTParsing().analyseAST();
		return null;
	}
}
