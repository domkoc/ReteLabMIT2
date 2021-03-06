package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		s.runCycle();
		print(s);
		boolean exit = false;
		do {
			String commandString = "";
			char character;
			while ((character = (char) System.in.read()) != '\n') {
				commandString += character;
			}
			switch (commandString) {
			case "start":
				s.raiseStart();
				s.runCycle();
				break;
			case "black":
				s.raiseBlack();
				s.runCycle();
				break;
			case "white":
				s.raiseWhite();
				s.runCycle();
				break;
			case "exit":
				exit = true;
				break;

			default:
				System.out.println("Ismeretlen parancs!");
				break;
			}
			print(s);
		} while (!exit);
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
