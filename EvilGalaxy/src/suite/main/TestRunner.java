package suite.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import suite.main.SuiteTests;

public class TestRunner implements Comparable<String> {

	private static String clazzName;

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SuiteTests.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		List<String> classesToSort = new ArrayList<>();
		for (Class<?> clazz : SuiteTests.getClasses()) {
			classesToSort.add(clazz.toString());
		}

		Collections.sort(classesToSort);

		for (String className : classesToSort) {
			clazzName = className;
			System.out.println("Test for " + clazzName + " successful? - " + result.wasSuccessful());
		}
	}

	@Override
	public int compareTo(String obj) {
		clazzName.compareTo(obj);
		return 0;
	}
}