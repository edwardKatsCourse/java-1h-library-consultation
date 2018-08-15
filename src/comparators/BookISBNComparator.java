package comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dao.Book;

public class BookISBNComparator implements Comparator<Book>{

	@Override
	public int compare(Book book1, Book book2) {
		Long isbn1 = book1.getISBN();
		Long isbn2 = book2.getISBN();
		return isbn1.compareTo(isbn2);
	}

    public static void main(String[] args) {
        //retainAll - intersection (пересечение)
		List<String> letters_1 = new ArrayList<>();
		letters_1.add("a");
		letters_1.add("b");
		letters_1.add("c");
		letters_1.add("d");

		List<String> letters_2 = new ArrayList<>();
		letters_2.add("c");
		letters_2.add("d");
		letters_2.add("e");
		letters_2.add("а");


		System.out.println("list 1: before");
		System.out.println(letters_1);
		letters_1.removeAll(letters_2);
		System.out.println("list 1: after");
		System.out.println(letters_1);
		System.out.println("list 2");
		System.out.println(letters_2);

		//list.retain -> "this" | "this collection"
		//LIST всеДанные: 	 1 2 3 4
		//LIST важныеДанные:     3 4
		//всеДанные.убериВсеЧегоНетВ(важныеДанные) -> 3 4

		//removeAll
		//список1.удалиВсеЧтоЕстьВ(список2)



    }

}
