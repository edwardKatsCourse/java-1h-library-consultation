package util;

import java.util.Map;
import java.util.TreeSet;


public class DBLib {

	public static <K, V> void putToMultivalueMap(Map<K,TreeSet<V>> map, K key, V value){
		TreeSet<V> tsb = map.get(key);
		if (tsb == null) {
			tsb = new TreeSet<>();
			map.put(key, tsb);
		}
		tsb.add(value);	
	}
	
	public static <K,V> void removeFromTreeSet(Map<K, TreeSet<V>> map, K key, V value){
		TreeSet<V> tsb = map.get(key);
		if (tsb == null) return;
		if (tsb.size() < 2)map.remove(key);
		else tsb.remove(value);
	}
}
