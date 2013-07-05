package ar.edu.utn.frba.tadp.java.parsing.javamodel;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;

import ar.edu.utn.frba.tadp.java.parsing.handlers.SampleHandler;

public class JavaModelParsing {

	public void analyseJavaModel() {
		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		// Loop over all projects
		for (IProject project : projects) {
			try {
				this.printProjectInfo(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	public void printProjectInfo(IProject project) throws CoreException,
			JavaModelException {
		System.out.println("Working in project " + project.getName());
		// Check if we have a Java project
		if (project.isNatureEnabled(SampleHandler.JDT_NATURE)) {
			IJavaProject javaProject = JavaCore.create(project);
			this.printPackageInfos(javaProject);
		}
	}

	public void printPackageInfos(IJavaProject javaProject)
			throws JavaModelException {
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			// Package fragments include all packages in the
			// classpath
			// We will only look at the package from the source
			// folder
			// K_BINARY would include also included JARS, e.g.
			// rt.jar
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				System.out.println("Package " + mypackage.getElementName());
				this.printICompilationUnitInfo(mypackage);

			}

		}
	}

	public void printICompilationUnitInfo(IPackageFragment mypackage)
			throws JavaModelException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			this.printCompilationUnitDetails(unit);

		}
	}

	public void printIFieldsDetails(IType type) throws JavaModelException {
		for (IField field : type.getFields()) {
			System.out.println("Field " + field.getElementName() + " of type "
					+ field.getTypeSignature() + " of itype "
					+ field.getDeclaringType());

		}
	}

	public void printCompilationUnitDetails(ICompilationUnit unit)
			throws JavaModelException {
		System.out.println("Source file " + unit.getElementName());
		Document doc = new Document(unit.getSource());
		System.out.println("Has number of lines: " + doc.getNumberOfLines());
		printIMethods(unit);
	}

	public void printIMethods(ICompilationUnit unit) throws JavaModelException {
		IType[] allTypes = unit.getAllTypes();
		for (IType type : allTypes) {
			this.printIMethodDetails(type);
			this.printIFieldsDetails(type);
		}
	}

	public void printIMethodDetails(IType type) throws JavaModelException {
		IMethod[] methods = type.getMethods();

		for (IMethod method : methods) {

			System.out.println("Method name " + method.getElementName());
			System.out.println("Signature " + method.getSignature());
			System.out.println("Return Type " + method.getReturnType());

		}
	}

}
