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
import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Analyzer for Thai language. It uses java.text.BreakIterator to break words.
 * @author Samphan Raruenrom <samphan@osdev.co.th> for To-Be-One Technology Co., Ltd.
 * @version 0.2
 */
public class ThaiAnalyzer extends Analyzer {

    /**
     *
     * @param fieldName
     * @param reader
     * @return
     */
    @Override
  public TokenStream tokenStream(String fieldName, Reader reader) {
	  TokenStream ts = new StandardTokenizer(reader);
    ts = new StandardFilter(ts);
    ts = new ThaiWordFilter(ts);
    ts = new StopFilter(ts, StopAnalyzer.ENGLISH_STOP_WORDS);
    return ts;
  }
}
