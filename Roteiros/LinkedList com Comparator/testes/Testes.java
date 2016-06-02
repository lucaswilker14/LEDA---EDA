package testes;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import adt.linkedList.ordered.OrderedSingleLinkedListImpl;
import comparadores.ComparatorAscendente;
import comparadores.ComparatorDescendente;

public class Testes {

		
	@Test
	public void test() {
		try {
			ComparatorDescendente<Integer> as = new ComparatorDescendente<>();
			OrderedSingleLinkedListImpl<Integer> list1 = new OrderedSingleLinkedListImpl<>(as);

			
			assertTrue(list1.isEmpty());
			
			System.out.println(Arrays.toString(list1.toArray()));
			
			list1.insert(3);
			list1.insert(2);
			list1.insert(4);
			list1.insert(5);
			list1.insert(1);
			list1.insert(0);
			
			assertFalse(list1.isEmpty());
			
			assertEquals(6, list1.size());
			
			assertEquals(2, list1.search(2).intValue());
			assertEquals(1, list1.search(1).intValue());
			assertEquals(5, list1.search(5).intValue());
			assertEquals(null, list1.search(9));
			assertEquals(null, list1.search(null));
			
			System.out.println(list1.minimum());
			
			list1.setComparator(new ComparatorAscendente<Integer>());
			
			System.out.println(list1.minimum());
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
			System.out.println(Arrays.toString(list1.toArray()));
			
			
			System.out.println(Arrays.toString(list1.toArray()));
			
			
			
			assertEquals(0, list1.minimum().intValue());
			
			list1.remove(4);
			System.out.println(Arrays.toString(list1.toArray()));
			list1.remove(5);
			System.out.println(Arrays.toString(list1.toArray()));
			list1.remove(9);
			System.out.println(Arrays.toString(list1.toArray()));
			list1.remove(null);
			System.out.println(Arrays.toString(list1.toArray()));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
