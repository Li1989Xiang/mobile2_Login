package mobile.test;

import java.util.Scanner;

import org.testng.annotations.Test;

/**
 * 解决Android登录问题
 */
public class Test00Wait {
	@Test
	public void waitForOperateion() {
		System.out.println("wait...");
		try (Scanner s = new Scanner(System.in)) {
			s.nextLine();
		}
	}
}
