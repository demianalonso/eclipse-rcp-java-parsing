package ar.edu.utn.frba.tadp.java.parsing.ast;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import ar.edu.utn.frba.tadp.java.parsing.handlers.EsLaVidaVisitor;
import ar.edu.utn.frba.tadp.java.parsing.handlers.MethodVisitor;
import ar.edu.utn.frba.tadp.java.parsing.handlers.SampleHandler;

public class ASTParsing {

	public Object analyseAST()
			throws ExecutionException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		// Loop over all projects
		for (IProject project : projects) {
			try {
				if (project.isNatureEnabled(SampleHandler.JDT_NATURE)) {
					this.analyseMethods(project);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void analyseMethods(IProject project)
			throws JavaModelException {
		IPackageFragment[] packages = JavaCore.create(project)
				.getPackageFragments();
		// parse(JavaCore.create(project));
		for (IPackageFragment mypackage : packages) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				this.createAST(mypackage);
			}

		}
	}

	public void createAST(IPackageFragment mypackage) throws JavaModelException {
		System.out
				.println("---------------------------------------------------");
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {

			System.out.println(unit.getElementName());
			CompilationUnit parse = this.parse(unit);
			MethodVisitor visitor = new MethodVisitor(parse);
			parse.accept(visitor);
			System.out.println();
			for (MethodDeclaration method : visitor.getMethods()) {
				System.out.println("Method name: " + method.getName());
				System.out.println("Return type: " + method.getReturnType2());
				System.out.println("Parameters: ");
				for (SingleVariableDeclaration variableDeclaration : (List<SingleVariableDeclaration>) method
						.parameters()) {
					System.out.println("\t" + variableDeclaration.getName()
							+ ": " + variableDeclaration.getType());
				}

				EsLaVidaVisitor esLaVida = new EsLaVidaVisitor();
				method.accept(esLaVida);
				if (esLaVida.validar()) {
					System.out.println("* ESTE METODO ES LA VIDA.*");
				}
				System.out.println();
			}

		}
	}

	public CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}

}
