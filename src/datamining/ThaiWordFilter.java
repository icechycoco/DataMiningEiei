/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;
import java.io.IOException;
import java.util.Locale;
import java.lang.Character.UnicodeBlock;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import java.text.BreakIterator;
/**
 *
 * @author icechycoco
 */
    

/**
 * TokenFilter that use java.text.BreakIterator to break each 
 * Token that is Thai into separate Token(s) for each Thai word.
 * @author Samphan Raruenrom <samphan@osdev.co.th> for To-Be-One Technology Co., Ltd.
 * @version 0.2
 */
public class ThaiWordFilter extends TokenFilter {
  
  private BreakIterator breaker = null;
  private Token thaiToken = null;
  
  public ThaiWordFilter(TokenStream input) {
    super(input);
    breaker = BreakIterator.getWordInstance(new Locale("th"));
  }
  
    /**
     *
     * @return
     * @throws IOException
     */
    @Override
  public Token next() throws IOException {
    if (thaiToken != null) {
      String text = thaiToken.termText();
      int start = breaker.current();
      int end = breaker.next();
      if (end != BreakIterator.DONE) {
        return new Token(text.substring(start, end), 
            thaiToken.startOffset()+start, thaiToken.startOffset()+end, thaiToken.type());
      }
      thaiToken = null;
    }
    Token tk = input.next();
    if (tk == null) {
      return null;
    }
    String text = tk.termText();
    if (UnicodeBlock.of(text.charAt(0)) != UnicodeBlock.THAI) {
      return new Token(text.toLowerCase(), tk.startOffset(), tk.endOffset(), tk.type());
    }
    thaiToken = tk;
    breaker.setText(text);
    int end = breaker.next();
    if (end != BreakIterator.DONE) {
      return new Token(text.substring(0, end), 
          thaiToken.startOffset(), thaiToken.startOffset()+end, thaiToken.type());
    }
    return null;
  }
}

