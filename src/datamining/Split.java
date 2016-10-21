/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;
import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.text.BreakIterator;
import java.util.Locale;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

/**
 *
 * @author icechycoco
 */
public class Split extends ThaiWordFilter {
    private BreakIterator breaker = null;
  private Token thaiToken = null;

  public Split(TokenStream input) {
    super(input);
    breaker = BreakIterator.getWordInstance(new Locale("th"));
  }

  public Token next() throws IOException {
    if (thaiToken != null) {
      String text = thaiToken.termText();
      int start = breaker.current();
      int end = breaker.next();
      if (end != BreakIterator.DONE) {
        return new Token(text.substring(start, end), 
            thaiToken.startOffset()+start,
            thaiToken.startOffset()+end, thaiToken.type());
      }
      thaiToken = null;
    }
    Token tk = input.next();
    if (tk == null) {
      return null;
    }
    String text = tk.termText();
    if (UnicodeBlock.of(text.charAt(0)) != UnicodeBlock.THAI) {
      return new Token(text.toLowerCase(), 
                       tk.startOffset(), 
                       tk.endOffset(), 
                       tk.type());
    }
    thaiToken = tk;
    breaker.setText(text);
    int end = breaker.next();
    if (end != BreakIterator.DONE) {
      return new Token(text.substring(0, end), 
          thaiToken.startOffset(), 
          thaiToken.startOffset()+end,
          thaiToken.type());
    }
    return null;
  }
}
