package model;

import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;
import util.DBLib;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class Library implements ILibrary {

    public static final TreeSet<Book> EMPTY_TREE_SET = new TreeSet<Book>();

    //Основной "источник правды"
    private HashMap<Long, Book> isbnHM;

    //Подтанцовка для "очень быстрого поиска"
    private TreeMap<Author, TreeSet<Book>> authorTM;
    private TreeMap<String, TreeSet<Book>> titleTM;
    private HashMap<Publisher, TreeSet<Book>> publisherHM;
    private TreeMap<String, TreeSet<Book>> publisherNameTM;
    private TreeMap<String, TreeSet<Book>> publisherCountryTM;
    private TreeMap<LocalDate, TreeSet<Book>> editionTM;
    private TreeMap<Double, TreeSet<Book>> priceTM;

    public Library() {
        emptyLibrary();
    }

    private void emptyLibrary() {
        isbnHM = new HashMap<Long, Book>();
        authorTM = new TreeMap<Author, TreeSet<Book>>();
        titleTM = new TreeMap<String, TreeSet<Book>>();
        publisherHM = new HashMap<Publisher, TreeSet<Book>>();
        publisherNameTM = new TreeMap<String, TreeSet<Book>>();
        publisherCountryTM = new TreeMap<String, TreeSet<Book>>();
        editionTM = new TreeMap<LocalDate, TreeSet<Book>>();
        priceTM = new TreeMap<Double, TreeSet<Book>>();
    }

    @Override
    public boolean addBook(Book book) {
        if (book == null) return false;
        Book oldValue = isbnHM.putIfAbsent(book.getISBN(), book);
        boolean exists = oldValue != null;

        if (exists) {
            return false;
        }

        for (Author author : book.getAuthors()) {
            DBLib.putToMultivalueMap(authorTM, author, book);
        }
        DBLib.putToMultivalueMap(titleTM, book.getTitle(), book);
        DBLib.putToMultivalueMap(publisherHM, book.getPublisher(), book);
        DBLib.putToMultivalueMap(publisherNameTM, book.getPublisher().getName(), book);
        DBLib.putToMultivalueMap(publisherCountryTM, book.getPublisher().getCountry().name(), book);
        DBLib.putToMultivalueMap(editionTM, book.getEdition(), book);
        DBLib.putToMultivalueMap(priceTM, book.getPrice(), book);

        return true;
    }

    @Override
    public boolean addAll(Collection<Book> bCollection) {
        boolean res = true;
        for (Book book : bCollection) res = addBook(book) && res;
        return res;
    }

    @Override
    public boolean addLibrary(Library lib) {
        boolean res = true;
        for (Book book : lib.getAllBooks()) res = addBook(book) && res;
        return res;
    }

    @Override
    public void fillRandomLibrary(int numBooks) {
        emptyLibrary();
        for (int counter = 0; counter < numBooks; counter++)
            addBook(Book.getRandomBook());

    }

    @Override
    public void fillWithIterable(Iterable<Book> iterable) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(Book book) {
        return isbnHM.containsKey(book.getISBN());
    }

    @Override
    /**/ public boolean containsAll(Collection<Book> bCollection) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    /**/ public Iterable<Book> containsAtLeastOne(Collection<Book> bCollection) {
        // TODO Auto-generated method stub
        return null;
    }
	
	/*private <K> void removeFromTreeSet(Map<K, TreeSet<Book>> map, K key, Book book){
		TreeSet<Book> tsb = map.get(key);
		if (tsb == null) return;
		if (tsb.size() < 2)map.remove(key);
		else tsb.remove(book);
	}*/

    @Override
    public boolean remove(Book book) {
        boolean res = isbnHM.remove(book.getISBN()) != null;
        if (res) {
            DBLib.removeFromTreeSet(titleTM, book.getTitle(), book);
            DBLib.removeFromTreeSet(publisherHM, book.getPublisher(), book);
            DBLib.removeFromTreeSet(publisherNameTM, book.getPublisher().getName(), book);
            DBLib.removeFromTreeSet(publisherCountryTM, book.getPublisher().getCountry().name(), book);
            DBLib.removeFromTreeSet(editionTM, book.getEdition(), book);
            DBLib.removeFromTreeSet(priceTM, book.getPrice(), book);
            for (Author author : book.getAuthors()) DBLib.removeFromTreeSet(authorTM, author, book);

        }
        return false;
    }

    @Override
    public Iterable<Book> removeAll(Collection<Book> bCollection) {
        // TODO Auto-generated method stub
        //1. пройтись по всей нашей библиотеке и удалить все, что сказал удалить copyRight
        //2. пройтись по ВСЕМ мапам и обновить значения
        return null;
    }

    @Override
    public Iterable<Book> retainAll(Collection<Book> copyRight) {
        //1. пройтись по всей нашей библиотеке и удалить все, что чего нету у copyRight
        //2. пройтись по ВСЕМ мапам и обновить значения
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean correctBookISBN(long isbn, long newISBN) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean correctBookAuthors(long isbn, Set<Author> newAuthors) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean correctBookTitle(long isbn, String newTitle) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean correctBookPublisher(long isbn, Publisher newPublisher) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean correctBookEditionDate(long isbn, LocalDate newEditionDate) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean correctBookPrice(long isbn, double newPrice) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean correctBookWithPattern(long isbn, Book pattern) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Book getBookByISBN(long isbn) {
        return isbnHM.get(isbn);
    }

    @Override
    public Iterable<Book> getBooksByAuthor(Author author) {
        Iterable<Book> res = authorTM.get(author);
        return res == null ? EMPTY_TREE_SET : res;
    }

    @Override
    public Iterable<Book> getBooksByAllAuthors(Collection<Author> aCollection) {
        TreeSet<Book> tsb = new TreeSet<>();
        boolean first = true;
        TreeSet<Book> tsa = null;
        for (Author author : aCollection) {
            tsa = authorTM.get(author);
            if (tsa == null) return EMPTY_TREE_SET;
            if (first) {
                tsb.addAll(tsa);
                first = false;
            } else tsb.retainAll(tsa);
        }
        return tsb;
    }

    @Override
    public Iterable<Book> getBooksByAtLeastOneAuthor(Collection<Author> aCollection) {
        TreeSet<Book> tsb = new TreeSet<>();
        TreeSet<Book> tsa;
        for (Author author : aCollection) {
            tsa = authorTM.get(author);
            if (tsa != null) tsb.addAll(tsa);
        }
        return tsb;
    }

    @Override
    public Iterable<Book> getBooksByTitle(String title) {
        Iterable<Book> res = titleTM.get(title);
        return res == null ? EMPTY_TREE_SET : res;
    }

    @Override
    public Iterable<Book> getBooksByPublisher(Publisher publisher) {
        Iterable<Book> res = publisherHM.get(publisher);
        return res == null ? EMPTY_TREE_SET : res;
    }

    @Override
    public Iterable<Book> getBooksByPublisherName(String pName) {
        Iterable<Book> res = publisherNameTM.get(pName);
        return res == null ? EMPTY_TREE_SET : res;
    }

    @Override
    public Iterable<Book> getBooksByPublisherCountry(Countries pCountry) {
        Iterable<Book> res = publisherCountryTM.get(pCountry);
        return res == null ? EMPTY_TREE_SET : res;
    }

    @Override
    public Iterable<Book> getAllBooks() {
        TreeSet<Book> tsb = new TreeSet<Book>();
        tsb.addAll(isbnHM.values());
        return tsb;
    }

    /**
     * A. Smith, D. Davidson "The book"
     * A. Smith  "Smith book"
     * D. Davidson "davidson"
     * D. Davidson , S. Angels "Zoo"
     *
     *    "Zoo"
     * 1. "davidson"
     * 2. "The book"
     * 3.
     * 4. "Smith book"
     *
     *
     */
    //ищем
    public Iterable<Book> getAllBooksSortedByAuthors() {
        //Куда складывать результат (книга уникальная)
        LinkedHashSet<Book> res = new LinkedHashSet<>();
        for (TreeSet<Book> tsb : authorTM.values()) {
            res.addAll(tsb);
        }
        return res;
    }

    @Override
    public Iterable<Book> getAllBooksSortedByTitle() {
        ArrayList<Book> alb = new ArrayList<>();
        for (TreeSet<Book> tsb : titleTM.values()) alb.addAll(tsb);
        return alb;
    }

    @Override
    /**/ public Iterable<Book> getAllBooksSortedByPublisherNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /**/ public Iterable<Book> getAllBooksSortedByPublisherCountries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /**/ public Iterable<Book> getAllBooksSortedByEditionDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    /**/ public Iterable<Book> getAllBooksSortedByPrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPrintedBefore(LocalDate max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPrintedBefore(int year) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPrintedAfter(LocalDate min) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPrintedAfter(int year) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPrintedInRange(LocalDate min, LocalDate max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPrintedInRange(int yearMin, int yearMax) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksCheaperThan(double maxPrice) {
        //1. Map - тащит ОДИН элемент (по одному ключу)
        //2. Перебирать по одному: Iterable, foreach

        //for ... Entry ... entrySet()
        //складывать подходящие книги в Set/List

        //double maxPrice = 50.0
        //0..50..75..100
        //при первом несовпадении цены - break из цилка по Map

//        for (Map.Entry<Double, TreeSet<Book>> m : priceTM.entrySet()) {
//          передали в поиске maxPrice = 50.0
//          m.getKey() == 10 ->    добавляем
//          m.getKey() == 20 ->    добавляем
//          m.getKey() == 35 ->    добавляем
//          m.getKey() == 49.99 -> добавляем
//          m.getKey() == 51.99 ->НЕ добавляем -> break
//        }
//        возвращаем все, что совпало

        return null;
    }

    @Override
    public Iterable<Book> getBooksMoreExpensiveThan(double minPrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<Book> getBooksPricedInRange(double minPrice, double maxPrice) {
        // TODO Auto-generated method stub

        //val list: List
        //list.add

        //Person person

        //Stream API
        return new A();
    }

    @Override
    public int size() {
        return isbnHM.size();
    }

    @Override
    public boolean isEmpty() {
        return isbnHM.isEmpty();
    }

    @Override
    public void clear() {
        emptyLibrary();
    }

}

class A implements Iterable<Book> {
    @Override
    public Iterator<Book> iterator() {
        return null;
    }
}
