/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

/**
 *
 * @author icechycoco
 */
import java.io.StringReader;
import junit.framework.TestCase;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;


public class DataMining extends TestCase {
    public static void main(String args[]) {
        
    }
    

	public void assertAnalyzesTo(Analyzer a, String input, String[] output)
		throws Exception {

		TokenStream ts = a.tokenStream("dummy", new StringReader(input));

		for (int i = 0; i < output.length; i++) {
			Token t = ts.next();
			assertNotNull(t);
			assertEquals(t.termText(), output[i]);
		}
		assertNull(ts.next());
		ts.close();
	}

	public void testAnalyzer() throws Exception {
		ThaiAnalyzer analyzer = new ThaiAnalyzer();
	
		assertAnalyzesTo(analyzer, "", new String[] {});

		assertAnalyzesTo(
			analyzer,
			"การที่ได้ต้องแสดงว่างานดี",
			new String[] { "การ", "ที่", "ได้", "ต้อง", "แสดง", "ว่า", "งาน", "ดี"});

		assertAnalyzesTo(
			analyzer,
			"บริษัทชื่อ XY&Z - คุยกับ xyz@demo.com",
			new String[] { "บริษัท", "ชื่อ", "xy&z", "คุย", "กับ", "xyz@demo.com" });

    // English stop words
		assertAnalyzesTo(
			analyzer,
			"ประโยคว่า The quick brown fox jumped over the lazy dogs",
			new String[] { "ประโยค", "ว่า", "quick", "brown", "fox", "jumped", "over", "lazy", "dogs" });
	}
}
