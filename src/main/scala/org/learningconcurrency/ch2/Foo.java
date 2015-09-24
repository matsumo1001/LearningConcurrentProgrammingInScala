package org.learningconcurrency.ch2;

public class Foo {
	final private int a$;
	final private int b$;
	final public int a() {return a$;}
	public int b() {return b$;}
	public Foo(int a, int b) {
		a$ = a;
		b$ = b;
	}
}
