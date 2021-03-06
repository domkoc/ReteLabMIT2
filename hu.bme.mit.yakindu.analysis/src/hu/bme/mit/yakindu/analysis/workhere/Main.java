package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.sgraph.Trigger;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		//secondPart(root);
		fourthPart(root);
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	
	private static void fourthPart(EObject root) {
		Statechart s = (Statechart) root;
		System.out.println("import java.io.IOException;"); 
		System.out.println();
		System.out.println("import hu.bme.mit.yakindu.analysis.RuntimeService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.TimerService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;");
		System.out.println();
		System.out.println("public class RunStatechart {");
		System.out.println("    public static void main(String[] args) throws IOException {");
		System.out.println("        ExampleStatemachine s = new ExampleStatemachine();");
		System.out.println("        s.setTimer(new TimerService());");
		System.out.println("        RuntimeService.getInstance().registerStatemachine(s, 200);");
		System.out.println("        s.init();");
		System.out.println("        s.enter();");
		System.out.println("        s.runCycle();");
		System.out.println("        print(s);");
		System.out.println("        boolean exit = false;");
		System.out.println("        do {\r\n");
		System.out.println("            String commandString = \"\";");
		System.out.println("            char character;\r\n");
		System.out.println("            while ((character = (char) System.in.read()) != '\\n') {");
		System.out.println("                commandString += character;");
		System.out.println("            }");
		System.out.println("            switch (commandString) {");
		TreeIterator<EObject> mainIterator = s.eAllContents();
		while (mainIterator.hasNext()) {
			EObject content = mainIterator.next();
			if (content instanceof EventDefinition) {
				EventDefinition eventDefinition = (EventDefinition) content;
				System.out.println("            case \"" + eventDefinition.getName() + "\":");
				System.out.println("                s.raise" + eventDefinition.getName().substring(0, 1).toUpperCase() + eventDefinition.getName().substring(1) + "();");
				System.out.println("                s.runCycle();");
				System.out.println("                break;");

			}
			
		}
		System.out.println("            case \"exit\":");
		System.out.println("                exit = true;");
		System.out.println("                break;");
		System.out.println("            default:");
		System.out.println("                System.out.println(\"Ismeretlen parancs!\");");
		System.out.println("                break;");
		System.out.println("            }");
		System.out.println("            print(s);");
		System.out.println("        } while (!exit);");
		System.out.println("        System.exit(0);");
		System.out.println("    }");
		System.out.println("    public static void print(IExampleStatemachine s) {");
		TreeIterator<EObject> printIterator = s.eAllContents();
		while (printIterator.hasNext()) {
			EObject content = printIterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variableDefinition = (VariableDefinition) content;
				System.out.println("        System.out.println(\"" + variableDefinition.getName().charAt(0) + " = \" + s.getSCInterface().get" + variableDefinition.getName().substring(0, 1).toUpperCase() + variableDefinition.getName().substring(1) + "());");
			}
		}
		System.out.println("    }");
		System.out.println("}");
	}

	private static void secondPart(EObject root) {
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		int suffix = 0;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				if (state.getName() == "") {
					TreeIterator<EObject> insideIterator = s.eAllContents();
					while (insideIterator.hasNext()) {
						EObject insideContent = insideIterator.next();
						if(insideContent instanceof State) {
							State insideState = (State) insideContent;
							if (insideState.getName() == "State"+suffix) {
								suffix++;
								insideIterator = s.eAllContents();
							}
						}
					}
					System.out.println("N??vtelen ??llapot! Javasolt n??v: " + "State" + suffix);
					suffix++;
				}
				if (state.getOutgoingTransitions().isEmpty()) {
					System.out.println("Csapda: " + state.getName());
				} else {
					System.out.println(state.getName());
				}
			}
			if (content instanceof Transition) {
				Transition transition = (Transition) content;
				System.out.println(transition.getSource().getName() + " -> " + transition.getTarget().getName());
			}
			
		}
	}
}
