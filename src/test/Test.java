package test;

import java.util.TreeSet;

import dao.Book;

public class Test {

	public static void main(String[] args) {
		TreeSet<Book> tsb = new TreeSet<Book>();
		for (int i=0; i<20; i++) tsb.add(Book.getRandomBook());
		for(Book book : tsb)System.out.println(book);
		 		 
	}

}
