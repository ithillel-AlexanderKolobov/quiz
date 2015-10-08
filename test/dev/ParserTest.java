package dev;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * 
 * @author Kolobov Alexsander
 *
 */
public class ParserTest {
	private Parser parser;
	private static File testFile;
	private final String LATIN = "Hello teamed.io";;
	private final String LATIN_WITH_UNICODE = "Hello, ку ку шечка teamed.io ";

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Before
	public void initialize() throws Exception {
		parser = new Parser();
		testFile = testFolder.newFile("test.txt");
		parser.setFile(testFile);
	}
	private static String read() throws IOException{
		return new String(Files.readAllBytes(testFile.toPath()));
	}
	private static void write(String content) throws IOException{
		Files.write(testFile.toPath(), content.getBytes(Charset.defaultCharset()));
		}
	
	@Test
	public void testGetContentStringWithUnicode() throws IOException {	
		write(LATIN_WITH_UNICODE);
		assertTrue(LATIN_WITH_UNICODE.equals(parser.getContent()));
	}

	@Test
	public void testGetContentStringWithoutUnicode() throws IOException {	
		write(LATIN);
		assertTrue(LATIN.equals(parser.getContent()));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithoutUnicode()
			throws IOException {
		write(LATIN);
		assertTrue(LATIN.equals(parser.getContentWithoutUnicode()));
	}

	@Test
	public void testGetContentWithoutUnicodeStringWithUnicode() throws IOException {
		write(LATIN_WITH_UNICODE);
		assertFalse(LATIN_WITH_UNICODE.equals(parser.getContentWithoutUnicode()));
	}
	
	@Test
	public void testSaveContentWithoutUnicode() throws IOException {
		parser.saveContent(LATIN);
		assertTrue(LATIN.equals(read()));
	}

	@Test
	public void testSaveContentWithUnicode() throws IOException {
		parser.saveContent(LATIN_WITH_UNICODE);
		assertTrue(LATIN_WITH_UNICODE.equals(read()));
	}

}
