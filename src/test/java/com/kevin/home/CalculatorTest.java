package com.kevin.home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kevin.home.domain.Calculator;

public class CalculatorTest {
	
	Calculator calculator;
	
	@BeforeEach
	public void setUp() {
		calculator = new Calculator();
	}
	
	@Test
	void testAddition1() {
		assertEquals(20, calculator.addition(15, 5));
	}
	
	@Test
	void testAddition2() {
		assertEquals(30, calculator.addition(20, 10));
	}
}
