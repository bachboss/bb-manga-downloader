/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.ult;

import bbmangadownloader.entity.Chapter;
import bbmangadownloader.entity.Manga;
import java.text.SimpleDateFormat;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Bach
 */
public class HeuristicTest {

    public HeuristicTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
//
//    /**
//     * Test of doSplitHeuristic method, of class Heuristic.
//     */
//    @Test
//    public void testDoSplitHeuristic() {
//        System.out.println("doSplitHeuristic");
//        String text = "";
//        String[] expResult = null;
//        String[] result = Heuristic.doSplitHeuristic(text);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNumberOfDigitInString method, of class Heuristic.
//     */
//    @Test
//    public void testGetNumberOfDigitInString() {
//        System.out.println("getNumberOfDigitInString");
//        String text = "";
//        List<Integer> listParsedNumber = null;
//        int expResult = 0;
//        int result = Heuristic.getNumberOfDigitInString(text, listParsedNumber);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRatio method, of class Heuristic.
//     */
//    @Test
//    public void testGetRatio() {
//        System.out.println("getRatio");
//        int a = 0;
//        int b = 0;
//        int total = 0;
//        int expResult = 0;
//        int result = Heuristic.getRatio(a, b, total);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isTwoWordAccecptable method, of class Heuristic.
//     */
//    @Test
//    public void testIsTwoWordAccecptable() {
//        System.out.println("isTwoWordAccecptable");
//        String word1 = "";
//        String word2 = "";
//        int expResult = 0;
//        int result = Heuristic.isTwoWordAccecptable(word1, word2);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of doCheck method, of class Heuristic.
//     */
//    @Test
//    public void testDoCheck() {
//        System.out.println("doCheck");
//        Node n = null;
//        Manga m = null;
//        StringBuilder sb = null;
//        boolean expResult = false;
//        boolean result = Heuristic.doCheck(n, m, sb);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isContainChapter method, of class Heuristic.
//     */
//    @Test
//    public void testIsContainChapter() {
//        System.out.println("isContainChapter");
//        String askingTextLowerCase = "";
//        boolean expResult = false;
//        boolean result = Heuristic.isContainChapter(askingTextLowerCase);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of tryGetChapterNumber method, of class Heuristic.
     */
    @Test
    public void testTryGetChapterNumber() {
        System.out.println("tryGetChapterNumber");
//         public static final String PATTERN_CHAPTER_REGEX_ENGLISH = "chap|chapter|ch|oneshot";
//    public static final String PATTERN_CHAPTER_REGEX_VNESE = "hồi|chương|tập|tiết";
//    public static final String PATTERN_CHAPTER_REGEX_CHINESE = "集";

        Manga m = new Manga(null, "ABC XYZ", null);
        {
            Chapter[] chapters = new Chapter[]{
                new Chapter(-1F, "Chapter 1", null, m),
                new Chapter(-1F, "Ch.1", null, m),
                new Chapter(-1F, "Hồi 1", null, m),
                new Chapter(-1F, "Chap 1", null, m),
                new Chapter(-1F, "Chương 1", null, m),
                new Chapter(-1F, "Tiết 1", null, m),
                new Chapter(-1F, "Tập 1", null, m),
                new Chapter(-1F, "1集", null, m),
                new Chapter(-1F, "ABC XYZ Chapter 1", null, m),
                new Chapter(-1F, "ABC XYZ Ch.1", null, m),
                new Chapter(-1F, "ABC XYZ Hồi 1", null, m),
                new Chapter(-1F, "ABC XYZ Chap 1", null, m),
                new Chapter(-1F, "ABC XYZ Chương 1", null, m),
                new Chapter(-1F, "ABC XYZ Tiết 1", null, m),
                new Chapter(-1F, "ABC XYZ Tập 1", null, m),
                new Chapter(-1F, "ABC XYZ 1集", null, m),
                new Chapter(-1F, "ABC XYZ Chapter 1", null, m),
                new Chapter(-1F, "ABC XYZ Ch.1", null, m),
                new Chapter(-1F, "ABC XYZ Hồi 1", null, m),
                new Chapter(-1F, "ABC XYZ Chap 1", null, m),
                new Chapter(-1F, "ABC XYZ Chương 1", null, m),
                new Chapter(-1F, "ABC XYZ Tiết 1", null, m),
                new Chapter(-1F, "ABC XYZ Tập 1", null, m),
                new Chapter(-1F, "ABC XYZ 1集", null, m),
                new Chapter(-1F, "Vol 2 Chapter 1", null, m),
                new Chapter(-1F, "Vol 2  Ch.1", null, m),
                new Chapter(-1F, "Vol 2  Hồi 1", null, m),
                new Chapter(-1F, "Vol 2  Chap 1", null, m),
                new Chapter(-1F, "Vol 2  Chương 1", null, m),
                new Chapter(-1F, "Vol 2  Tiết 1", null, m),
                new Chapter(-1F, "Vol 2  Tập 1", null, m),
                //
                new Chapter(-1F, "Vol.2 Chapter 1", null, m),
                new Chapter(-1F, "Vol.2  Ch.1", null, m),
                new Chapter(-1F, "Vol.2  Hồi 1", null, m),
                new Chapter(-1F, "Vol.2  Chap 1", null, m),
                new Chapter(-1F, "Vol.2  Chương 1", null, m),
                new Chapter(-1F, "Vol.2  Tiết 1", null, m),
                new Chapter(-1F, "Vol.2  Tập 1", null, m)
            };
            for (int i = 0; i < chapters.length; i++) {
                float value = (Heuristic.tryGetChapterNumber(chapters[i]));
                if (value != 1F) {
                    System.out.println("Error Here !");
                    throw new AssertionError("Error: " + chapters[i].getDisplayName());
                }
            }
        }
        {
            Chapter[] chapters = new Chapter[]{
                new Chapter(195, "Kimi no Iru Machi Vol.021 Ch.195: Goya Night ★", null, m),
                new Chapter(193, "Kimi no Iru Machi Vol.020 Ch.193: Don't Touch Me!", null, m)
            };
            Float[] expResults = new Float[]{
                195F,
                193F
            };
            Float[] results = new Float[chapters.length];
            for (int i = 0; i < chapters.length; i++) {
                results[i] = (Heuristic.tryGetChapterNumber(chapters[i]));
            }
            assertArrayEquals(expResults, results);
        }

        // TODO review the generated test code and remove the default call to fail.        
//        fail("The test case is a prototype.");
    }
//    /**
//     * Test of repairXML method, of class Heuristic.
//     */
//    @Test
//    public void testRepairXML() {
//        System.out.println("repairXML");
//        String text = "";
//        String expResult = "";
//        String result = Heuristic.repairXML(text);
//    assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    @Test
    public void testGetDate() {
        System.out.println("testGetDate");
        String[] data = new String[]{
            "A year ago", "A month ago", "A week ago", "A day ago", "An hour ago", "A minute ago",
            "2 years ago", " 2 months ago", "2 week ago", "10 days ago", "10 hours ago", "10 minutes ago"
        };

        for (String str : data) {
            String s = DateTimeUtilities.getStringFromDate(Heuristic.getDate(str),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            System.out.println(str + "\t" + s);
        }
    }
}
