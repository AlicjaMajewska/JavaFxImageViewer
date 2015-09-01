package dataprovider;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.capgemini.starterkit.javafx.dataprovider.data.AuthorVO;
import com.capgemini.starterkit.javafx.dataprovider.data.BookVO;
import com.capgemini.starterkit.javafx.dataprovider.data.LibraryVO;
import com.capgemini.starterkit.javafx.dataprovider.impl.Parser;

public class ParserTest {

	@Test
	public void testShouldSplitJsonArrayToObjectsList() {
		//given
		String jsonBookToArray = "[{\"id\":1,\"title\":\"Pierwsza książka\",\"authors\":\"Jan Kowalski\",\"libraryId\":1,\"libraryName\":\"Biblioteka Miejska\"},{\"id\":5,\"title\":\"Piąta książka\",\"authors\":\"Alicja Majewska\",\"libraryId\":4,\"libraryName\":\"Biblioteka im. Alicji Majewskiej\"}]";
		//when
		Collection<BookVO> convertToBookVO = Parser.convertToBookVO(jsonBookToArray);
		//then
		assertEquals(convertToBookVO.size(), 2);
		assertTrue( convertToBookVO.contains(new BookVO("Pierwsza książka", new AuthorVO("Jan", "Kowalski"), new LibraryVO("Biblioteka Miejska") )));
		assertTrue( convertToBookVO.contains(new BookVO("Piąta książka", new AuthorVO("Alicja", "Majewska"), new LibraryVO("Biblioteka im. Alicji Majewskiej") )));

	}

}
