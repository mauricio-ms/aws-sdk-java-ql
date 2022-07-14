package yamlparser;

// Generated from YamlLexer.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YamlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INDENT=1, DEDENT=2, NEWLINE=3, BYTES_LITERAL=4, DECIMAL_INTEGER=5, OCT_INTEGER=6, 
		HEX_INTEGER=7, BIN_INTEGER=8, FLOAT_NUMBER=9, IMAG_NUMBER=10, MINUS=11, 
		DOCUMENTSTART=12, DOCUMENTEND=13, AMPERSAND=14, STAR=15, OPEN_PAREN=16, 
		CLOSE_PAREN=17, COMMA=18, COLON=19, OPEN_BRACK=20, CLOSE_BRACK=21, OPEN_BRACE=22, 
		CLOSE_BRACE=23, LITERIAL_STR_IND=24, FOLD_STR_IND=25, DOUBLE_QUOTE=26, 
		ANCHOR=27, ALIAS=28, STRING_MY=29, SKIP1=30, UNKNOWN_CHAR=31, SKIP2=32, 
		NEWLINE_STR_LITERAL=33, NEWLINE_STR_QUOTE=34;
	public static final int
		FLOW=1, LITERAL_STRING=2, DOUBLE_QUOTE_STR=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "FLOW", "LITERAL_STRING", "DOUBLE_QUOTE_STR"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"NEWLINE", "BYTES_LITERAL", "DECIMAL_INTEGER", "OCT_INTEGER", "HEX_INTEGER", 
			"BIN_INTEGER", "FLOAT_NUMBER", "IMAG_NUMBER", "MINUS", "DOCUMENTSTART", 
			"DOCUMENTEND", "AMPERSAND", "STAR", "OPEN_PAREN", "CLOSE_PAREN", "COMMA", 
			"COLON", "OPEN_BRACK", "CLOSE_BRACK", "OPEN_BRACE", "CLOSE_BRACE", "LITERIAL_STR_IND", 
			"FOLD_STR_IND", "DOUBLE_QUOTE", "ANCHOR", "ALIAS", "NAME", "STRING_MY", 
			"STRING_MY_START", "SKIP1", "UNKNOWN_CHAR", "NON_ZERO_DIGIT", "DIGIT", 
			"OCT_DIGIT", "HEX_DIGIT", "BIN_DIGIT", "POINT_FLOAT", "EXPONENT_FLOAT", 
			"INT_PART", "FRACTION", "EXPONENT", "SHORT_BYTES", "LONG_BYTES", "LONG_BYTES_ITEM", 
			"SHORT_BYTES_CHAR_NO_SINGLE_QUOTE", "SHORT_BYTES_CHAR_NO_DOUBLE_QUOTE", 
			"LONG_BYTES_CHAR", "BYTES_ESCAPE_SEQ", "SPACES", "COMMENT", "LINE_JOINING", 
			"DECIMAL_INTEGER2", "OCT_INTEGER2", "HEX_INTEGER2", "BIN_INTEGER2", "FLOAT_NUMBER2", 
			"IMAG_NUMBER2", "STRING_MY_2", "STRING_MY_START_2", "COMMA2", "COLON2", 
			"SKIP2", "CLOSE_BRACK2", "CLOSE_BRACE2", "NEWLINE_STR_LITERAL", "STRING_MY_3", 
			"STRING_MY_4", "NEWLINE_STR_QUOTE", "DOUBLE_QUOTE2"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "'-'", 
			null, null, "'&'", "'*'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INDENT", "DEDENT", "NEWLINE", "BYTES_LITERAL", "DECIMAL_INTEGER", 
			"OCT_INTEGER", "HEX_INTEGER", "BIN_INTEGER", "FLOAT_NUMBER", "IMAG_NUMBER", 
			"MINUS", "DOCUMENTSTART", "DOCUMENTEND", "AMPERSAND", "STAR", "OPEN_PAREN", 
			"CLOSE_PAREN", "COMMA", "COLON", "OPEN_BRACK", "CLOSE_BRACK", "OPEN_BRACE", 
			"CLOSE_BRACE", "LITERIAL_STR_IND", "FOLD_STR_IND", "DOUBLE_QUOTE", "ANCHOR", 
			"ALIAS", "STRING_MY", "SKIP1", "UNKNOWN_CHAR", "SKIP2", "NEWLINE_STR_LITERAL", 
			"NEWLINE_STR_QUOTE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}



	  /**
	   * A queue where extra tokens are pushed on (see the NEWLINE lexer rule for instance).
	   * Tokens are returned from the queue first. Once the queue is empty, lexer goes back to input stream for producing tokens.
	   */
	  private java.util.LinkedList<Token> tokens = new java.util.LinkedList<>();

	  // The stack that keeps track of the indentation level.
	  private java.util.Stack<Integer> indents = new java.util.Stack<>();

	  // The amount of opened braces, brackets and parenthesis.
	  private int opened = 0;

	  // The most recently produced token.
	  private Token lastToken = null;

	  /**
	   * Add a token to the queue. Tokens are returned from the queue first, before new tokens are extracted from input stream.
	   */
	  public void schedule(Token t) {
	    tokens.offer(t);
	  }

	  @Override
	  public Token nextToken() {

	      if (tokens.isEmpty()) {
	          Token next = super.nextToken();

	          if (next.getType() == EOF) {
	              processEOF_NextToken();
	              next = tokens.poll();
	          } else if(next.getType() == NEWLINE) {
	              processNEWLINE_NextToken();
	              next = tokens.poll();
	          }

	          if (lastToken != null && lastToken.getType() == MINUS) {
	              switch(next.getType()) {
	                  case MINUS:
	                    next = commonToken(YamlParser.NEWLINE, "\n");
	                    createAndScheduleIndent(this._tokenStartCharPositionInLine);
	                    schedule(commonToken(YamlParser.MINUS, "-"));
	                  break;
	                  case STRING_MY:
	                    int indent = this._tokenStartCharPositionInLine;
	                    Token afterNext = super.nextToken();
	                    if(afterNext.getType() == COLON) {
	                        createAndScheduleIndent(indent); 					//2 - after next (INDENT)
	                        schedule(next);										//3 - last (string)
	                        next = commonToken(YamlParser.NEWLINE, "\n");  //1 - next (NEWLINE)
	                    }

	                    if (afterNext.getType() == EOF) {
	                        processEOF_NextToken();
	                    } else if(afterNext.getType() == NEWLINE) {
	                        processNEWLINE_NextToken();
	                    }
	                    else {
	                        schedule(afterNext);
	                    }
	                    break;
	              }
	          }

	          this.lastToken = next;
	      } else {
	          this.lastToken = tokens.poll();
	      }

	      return this.lastToken;
	  }

	  private void processEOF_NextToken() {
	      schedule(commonToken(YamlParser.NEWLINE, "\n"));

	      if (!this.indents.isEmpty()) {

	          // Now emit as much DEDENT tokens as needed.
	          while (!indents.isEmpty()) {
	              this.schedule(createDedent());
	              indents.pop();
	          }
	      }

	      // Put the EOF back on the token stream.
	      this.schedule(commonToken(YamlParser.EOF, "<EOF>"));
	  }

	  private void processNEWLINE_NextToken() {
	      //String newLine = getText().replaceAll("[^\r\n]+", "");
	      String spaces = getText().replaceAll("[\r\n]+", "");

	      schedule(commonToken(NEWLINE, "\n"));

	      int indent = getIndentationCount(spaces);
	      int previous = indents.isEmpty() ? 0 : indents.peek();

	      if (indent == previous) {
	          // skip indents of the same size as the present indent-size
	          //skip();
	      }
	      else if (indent > previous) {
	          indents.push(indent);
	          schedule(commonToken(YamlParser.INDENT, spaces));
	      }
	      else {
	          // Possibly emit more than 1 DEDENT token.
	          while(!indents.isEmpty() && indents.peek() > indent) {
	              this.schedule(createDedent());
	              indents.pop();
	          }
	      }

	  }

	  private Token createDedent() {
	    CommonToken dedent = commonToken(YamlParser.DEDENT, "");
	    dedent.setLine(this.lastToken.getLine());
	    return dedent;
	  }

	  private CommonToken commonToken(int type, String text) {
	    int stop = this.getCharIndex() - 1;
	    int start = text.isEmpty() ? stop : stop - text.length() + 1;
	    return new CommonToken(this._tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, start, stop);
	  }

	  /**
	   * Create a Indent token if given indentation level is greater and schedule it in 'tokens' queue.
	   * @param indent indentation level
	   */
	  private void createAndScheduleIndent(int indent) {
	      int previous = indents.isEmpty() ? 0 : indents.peek();
	      if (indent > previous) {
	          indents.push(indent);
	          schedule(commonToken(YamlParser.INDENT, "-"));
	      }
	  }

	  // Calculates the indentation of the provided spaces, taking the
	  // following rules into account:
	  //
	  // "Tabs are replaced (from left to right) by one to eight spaces
	  //  such that the total number of characters up to and including
	  //  the replacement is a multiple of eight [...]"
	  //
	  //  -- https://docs.python.org/3.1/reference/lexical_analysis.html#indentation
	  static int getIndentationCount(String spaces) {

	    int count = 0;

	    for (char ch : spaces.toCharArray()) {
	      switch (ch) {
	        case '\t':
	          count += 8 - (count % 8);
	          break;
	        default:
	          // A normal space char.
	          count++;
	      }
	    }

	    return count;
	  }

	  boolean atStartOfInput() {
	    return super.getCharPositionInLine() == 0 && super.getLine() == 1;
	  }

	  /**
	   * Indentation of a string literal. '-1' means the value is not set.
	   */
	  private int string_literal_start = -1;



	public YamlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "YamlLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 0:
			NEWLINE_action((RuleContext)_localctx, actionIndex);
			break;
		case 13:
			OPEN_PAREN_action((RuleContext)_localctx, actionIndex);
			break;
		case 14:
			CLOSE_PAREN_action((RuleContext)_localctx, actionIndex);
			break;
		case 17:
			OPEN_BRACK_action((RuleContext)_localctx, actionIndex);
			break;
		case 18:
			CLOSE_BRACK_action((RuleContext)_localctx, actionIndex);
			break;
		case 19:
			OPEN_BRACE_action((RuleContext)_localctx, actionIndex);
			break;
		case 20:
			CLOSE_BRACE_action((RuleContext)_localctx, actionIndex);
			break;
		case 21:
			LITERIAL_STR_IND_action((RuleContext)_localctx, actionIndex);
			break;
		case 22:
			FOLD_STR_IND_action((RuleContext)_localctx, actionIndex);
			break;
		case 64:
			NEWLINE_STR_LITERAL_action((RuleContext)_localctx, actionIndex);
			break;
		case 65:
			STRING_MY_3_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void NEWLINE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

			     int next = _input.LA(1);
			     if (opened > 0 || next == '\r' || next == '\n' || next == '#') {
			         // If we're inside a list or on a blank line, ignore all indents,
			         // dedents and line breaks.
			         skip();
			     }
			   
			break;
		}
	}
	private void OPEN_PAREN_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			opened++;
			break;
		}
	}
	private void CLOSE_PAREN_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			opened--;
			break;
		}
	}
	private void OPEN_BRACK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			opened++;
			break;
		}
	}
	private void CLOSE_BRACK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			opened--;
			break;
		}
	}
	private void OPEN_BRACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			opened++;
			break;
		}
	}
	private void CLOSE_BRACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:
			opened--;
			break;
		}
	}
	private void LITERIAL_STR_IND_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 7:
			string_literal_start=-1;
			break;
		}
	}
	private void FOLD_STR_IND_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 8:
			string_literal_start=-1;
			break;
		}
	}
	private void NEWLINE_STR_LITERAL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 9:

			     int next = _input.LA(1);
			     if(!(opened > 0 || next == '\r' || next == '\n' || next == '#')) {
			       int indent = indents.isEmpty() ? 0 : indents.peek();
			       int space_count = getIndentationCount(getText().replaceAll("[\r\n]+", ""));
			       if(space_count <= indent) {
			         popMode();
			         setType(NEWLINE);
			       }
			       else if (string_literal_start == -1) {
			         string_literal_start = space_count;
			       }
			     }
			   
			break;
		}
	}
	private void STRING_MY_3_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 10:

			     int extraSpace = _tokenStartCharPositionInLine - string_literal_start;
			     if(extraSpace > 0) {
			       StringBuilder builder = new StringBuilder();
			       for (int i = 0; i < extraSpace; i++) {
			         builder.append(' ');
			       }
			       builder.append(getText());
			       setText(builder.toString());
			     }
			   
			break;
		}
	}
	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return NEWLINE_sempred((RuleContext)_localctx, predIndex);
		case 9:
			return DOCUMENTSTART_sempred((RuleContext)_localctx, predIndex);
		case 10:
			return DOCUMENTEND_sempred((RuleContext)_localctx, predIndex);
		case 28:
			return STRING_MY_START_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean NEWLINE_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return atStartOfInput();
		}
		return true;
	}
	private boolean DOCUMENTSTART_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return  super.getCharPositionInLine() == 0 ;
		}
		return true;
	}
	private boolean DOCUMENTEND_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return  super.getCharPositionInLine() == 0 ;
		}
		return true;
	}
	private boolean STRING_MY_START_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return _input.LA(3) != 45;
		case 4:
			return super.getCharPositionInLine() != 0;
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0000\"\u0266\u0006\uffff\uffff\u0006\uffff\uffff\u0006\uffff\uffff"+
		"\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002"+
		"7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007;\u0002"+
		"<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007?\u0002@\u0007@\u0002"+
		"A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007D\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0003\u0000\u0092\b\u0000\u0001\u0000\u0001\u0000\u0003\u0000"+
		"\u0096\b\u0000\u0001\u0000\u0003\u0000\u0099\b\u0000\u0003\u0000\u009b"+
		"\b\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001\u00a1"+
		"\b\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u00a5\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0005\u0002\u00a9\b\u0002\n\u0002\f\u0002\u00ac\t\u0002\u0001"+
		"\u0002\u0004\u0002\u00af\b\u0002\u000b\u0002\f\u0002\u00b0\u0003\u0002"+
		"\u00b3\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0004\u0003\u00b8\b"+
		"\u0003\u000b\u0003\f\u0003\u00b9\u0001\u0004\u0001\u0004\u0001\u0004\u0004"+
		"\u0004\u00bf\b\u0004\u000b\u0004\f\u0004\u00c0\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0004\u0005\u00c6\b\u0005\u000b\u0005\f\u0005\u00c7\u0001"+
		"\u0006\u0001\u0006\u0003\u0006\u00cc\b\u0006\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\u00d0\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u001a\u0004\u001a\u0113\b\u001a\u000b\u001a\f\u001a\u0114\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0004\u001b\u011c"+
		"\b\u001b\u000b\u001b\f\u001b\u011d\u0001\u001b\u0005\u001b\u0121\b\u001b"+
		"\n\u001b\f\u001b\u0124\t\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u0132\b\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0003\u001d\u0137\b\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001"+
		"!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0003$\u0148\b$\u0001$\u0001$\u0001"+
		"$\u0001$\u0003$\u014e\b$\u0001%\u0001%\u0003%\u0152\b%\u0001%\u0001%\u0001"+
		"&\u0004&\u0157\b&\u000b&\f&\u0158\u0001\'\u0001\'\u0004\'\u015d\b\'\u000b"+
		"\'\f\'\u015e\u0001(\u0001(\u0003(\u0163\b(\u0001(\u0004(\u0166\b(\u000b"+
		"(\f(\u0167\u0001)\u0001)\u0001)\u0005)\u016d\b)\n)\f)\u0170\t)\u0001)"+
		"\u0001)\u0001)\u0001)\u0005)\u0176\b)\n)\f)\u0179\t)\u0001)\u0003)\u017c"+
		"\b)\u0001*\u0001*\u0001*\u0001*\u0001*\u0005*\u0183\b*\n*\f*\u0186\t*"+
		"\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0005*\u0190"+
		"\b*\n*\f*\u0193\t*\u0001*\u0001*\u0001*\u0003*\u0198\b*\u0001+\u0001+"+
		"\u0003+\u019c\b+\u0001,\u0003,\u019f\b,\u0001-\u0003-\u01a2\b-\u0001."+
		"\u0003.\u01a5\b.\u0001/\u0001/\u0001/\u00010\u00040\u01ab\b0\u000b0\f"+
		"0\u01ac\u00011\u00011\u00051\u01b1\b1\n1\f1\u01b4\t1\u00012\u00012\u0003"+
		"2\u01b8\b2\u00012\u00032\u01bb\b2\u00012\u00012\u00032\u01bf\b2\u0001"+
		"3\u00013\u00053\u01c3\b3\n3\f3\u01c6\t3\u00013\u00043\u01c9\b3\u000b3"+
		"\f3\u01ca\u00033\u01cd\b3\u00013\u00013\u00014\u00014\u00014\u00044\u01d4"+
		"\b4\u000b4\f4\u01d5\u00014\u00014\u00015\u00015\u00015\u00045\u01dd\b"+
		"5\u000b5\f5\u01de\u00015\u00015\u00016\u00016\u00016\u00046\u01e6\b6\u000b"+
		"6\f6\u01e7\u00016\u00016\u00017\u00017\u00037\u01ee\b7\u00017\u00017\u0001"+
		"8\u00018\u00038\u01f4\b8\u00018\u00018\u00018\u00018\u00019\u00019\u0001"+
		"9\u00019\u00019\u00049\u01ff\b9\u000b9\f9\u0200\u00019\u00059\u0204\b"+
		"9\n9\f9\u0207\t9\u00019\u00019\u0001:\u0001:\u0001:\u0001:\u0001:\u0003"+
		":\u0210\b:\u0001;\u0001;\u0001;\u0001;\u0001<\u0001<\u0001<\u0001<\u0001"+
		"=\u0001=\u0001=\u0001=\u0003=\u021e\b=\u0001=\u0001=\u0003=\u0222\b=\u0003"+
		"=\u0224\b=\u0001=\u0001=\u0001>\u0001>\u0001>\u0001>\u0001>\u0001?\u0001"+
		"?\u0001?\u0001?\u0001?\u0001@\u0003@\u0233\b@\u0001@\u0001@\u0003@\u0237"+
		"\b@\u0001@\u0005@\u023a\b@\n@\f@\u023d\t@\u0001@\u0001@\u0001A\u0005A"+
		"\u0242\bA\nA\fA\u0245\tA\u0001A\u0001A\u0001A\u0001A\u0001B\u0001B\u0001"+
		"B\u0005B\u024e\bB\nB\fB\u0251\tB\u0001B\u0001B\u0001C\u0003C\u0256\bC"+
		"\u0001C\u0001C\u0003C\u025a\bC\u0001C\u0005C\u025d\bC\nC\fC\u0260\tC\u0001"+
		"D\u0001D\u0001D\u0001D\u0001D\u0002\u0184\u0191\u0000E\u0004\u0003\u0006"+
		"\u0004\b\u0005\n\u0006\f\u0007\u000e\b\u0010\t\u0012\n\u0014\u000b\u0016"+
		"\f\u0018\r\u001a\u000e\u001c\u000f\u001e\u0010 \u0011\"\u0012$\u0013&"+
		"\u0014(\u0015*\u0016,\u0017.\u00180\u00192\u001a4\u001b6\u001c8\u0000"+
		":\u001d<\u0000>\u001e@\u001fB\u0000D\u0000F\u0000H\u0000J\u0000L\u0000"+
		"N\u0000P\u0000R\u0000T\u0000V\u0000X\u0000Z\u0000\\\u0000^\u0000`\u0000"+
		"b\u0000d\u0000f\u0000h\u0000j\u0000l\u0000n\u0000p\u0000r\u0000t\u0000"+
		"v\u0000x\u0000z\u0000|\u0000~ \u0080\u0000\u0082\u0000\u0084!\u0086\u0000"+
		"\u0088\u0000\u008a\"\u008c\u0000\u0004\u0000\u0001\u0002\u0003\u001c\u0002"+
		"\u0000BBbb\u0002\u0000RRrr\u0002\u0000OOoo\u0002\u0000XXxx\u0002\u0000"+
		"JJjj\u0003\u000009AZaz\u0005\u0000\n\n\r\r  \"\"::\u0003\u0000\n\n\r\r"+
		"  \u0005\u0000\n\n\r\r  ##::\n\u0000\n\n\r\r  \"#&&**--::[[{|\u0004\u0000"+
		"\n\n\r\r  --\u0001\u000019\u0001\u000009\u0001\u000007\u0003\u000009A"+
		"Faf\u0001\u000001\u0002\u0000EEee\u0002\u0000++--\u0005\u0000\u0000\t"+
		"\u000b\f\u000e&([]\u007f\u0005\u0000\u0000\t\u000b\f\u000e!#[]\u007f\u0002"+
		"\u0000\u0000[]\u007f\u0001\u0000\u0000\u007f\u0002\u0000\t\t  \u0002\u0000"+
		"\n\n\r\r\n\u0000\n\n\r\r  \"\",,::[[]]{{}}\n\u0000\n\n\r\r  ##,,::[[]"+
		"]{{}}\n\u0000\n\n\r\r  \"#,-::[[]]{{}}\u0004\u0000\n\n\r\r\"\"\\\\\u0295"+
		"\u0000\u0004\u0001\u0000\u0000\u0000\u0000\u0006\u0001\u0000\u0000\u0000"+
		"\u0000\b\u0001\u0000\u0000\u0000\u0000\n\u0001\u0000\u0000\u0000\u0000"+
		"\f\u0001\u0000\u0000\u0000\u0000\u000e\u0001\u0000\u0000\u0000\u0000\u0010"+
		"\u0001\u0000\u0000\u0000\u0000\u0012\u0001\u0000\u0000\u0000\u0000\u0014"+
		"\u0001\u0000\u0000\u0000\u0000\u0016\u0001\u0000\u0000\u0000\u0000\u0018"+
		"\u0001\u0000\u0000\u0000\u0000\u001a\u0001\u0000\u0000\u0000\u0000\u001c"+
		"\u0001\u0000\u0000\u0000\u0000\u001e\u0001\u0000\u0000\u0000\u0000 \u0001"+
		"\u0000\u0000\u0000\u0000\"\u0001\u0000\u0000\u0000\u0000$\u0001\u0000"+
		"\u0000\u0000\u0000&\u0001\u0000\u0000\u0000\u0000(\u0001\u0000\u0000\u0000"+
		"\u0000*\u0001\u0000\u0000\u0000\u0000,\u0001\u0000\u0000\u0000\u0000."+
		"\u0001\u0000\u0000\u0000\u00000\u0001\u0000\u0000\u0000\u00002\u0001\u0000"+
		"\u0000\u0000\u00004\u0001\u0000\u0000\u0000\u00006\u0001\u0000\u0000\u0000"+
		"\u0000:\u0001\u0000\u0000\u0000\u0000>\u0001\u0000\u0000\u0000\u0000@"+
		"\u0001\u0000\u0000\u0000\u0001j\u0001\u0000\u0000\u0000\u0001l\u0001\u0000"+
		"\u0000\u0000\u0001n\u0001\u0000\u0000\u0000\u0001p\u0001\u0000\u0000\u0000"+
		"\u0001r\u0001\u0000\u0000\u0000\u0001t\u0001\u0000\u0000\u0000\u0001v"+
		"\u0001\u0000\u0000\u0000\u0001z\u0001\u0000\u0000\u0000\u0001|\u0001\u0000"+
		"\u0000\u0000\u0001~\u0001\u0000\u0000\u0000\u0001\u0080\u0001\u0000\u0000"+
		"\u0000\u0001\u0082\u0001\u0000\u0000\u0000\u0002\u0084\u0001\u0000\u0000"+
		"\u0000\u0002\u0086\u0001\u0000\u0000\u0000\u0003\u0088\u0001\u0000\u0000"+
		"\u0000\u0003\u008a\u0001\u0000\u0000\u0000\u0003\u008c\u0001\u0000\u0000"+
		"\u0000\u0004\u009a\u0001\u0000\u0000\u0000\u0006\u009e\u0001\u0000\u0000"+
		"\u0000\b\u00b2\u0001\u0000\u0000\u0000\n\u00b4\u0001\u0000\u0000\u0000"+
		"\f\u00bb\u0001\u0000\u0000\u0000\u000e\u00c2\u0001\u0000\u0000\u0000\u0010"+
		"\u00cb\u0001\u0000\u0000\u0000\u0012\u00cf\u0001\u0000\u0000\u0000\u0014"+
		"\u00d3\u0001\u0000\u0000\u0000\u0016\u00d5\u0001\u0000\u0000\u0000\u0018"+
		"\u00da\u0001\u0000\u0000\u0000\u001a\u00df\u0001\u0000\u0000\u0000\u001c"+
		"\u00e1\u0001\u0000\u0000\u0000\u001e\u00e3\u0001\u0000\u0000\u0000 \u00e6"+
		"\u0001\u0000\u0000\u0000\"\u00e9\u0001\u0000\u0000\u0000$\u00eb\u0001"+
		"\u0000\u0000\u0000&\u00ed\u0001\u0000\u0000\u0000(\u00f2\u0001\u0000\u0000"+
		"\u0000*\u00f5\u0001\u0000\u0000\u0000,\u00fa\u0001\u0000\u0000\u0000."+
		"\u00fd\u0001\u0000\u0000\u00000\u0102\u0001\u0000\u0000\u00002\u0107\u0001"+
		"\u0000\u0000\u00004\u010b\u0001\u0000\u0000\u00006\u010e\u0001\u0000\u0000"+
		"\u00008\u0112\u0001\u0000\u0000\u0000:\u0116\u0001\u0000\u0000\u0000<"+
		"\u0131\u0001\u0000\u0000\u0000>\u0136\u0001\u0000\u0000\u0000@\u013a\u0001"+
		"\u0000\u0000\u0000B\u013c\u0001\u0000\u0000\u0000D\u013e\u0001\u0000\u0000"+
		"\u0000F\u0140\u0001\u0000\u0000\u0000H\u0142\u0001\u0000\u0000\u0000J"+
		"\u0144\u0001\u0000\u0000\u0000L\u014d\u0001\u0000\u0000\u0000N\u0151\u0001"+
		"\u0000\u0000\u0000P\u0156\u0001\u0000\u0000\u0000R\u015a\u0001\u0000\u0000"+
		"\u0000T\u0160\u0001\u0000\u0000\u0000V\u017b\u0001\u0000\u0000\u0000X"+
		"\u0197\u0001\u0000\u0000\u0000Z\u019b\u0001\u0000\u0000\u0000\\\u019e"+
		"\u0001\u0000\u0000\u0000^\u01a1\u0001\u0000\u0000\u0000`\u01a4\u0001\u0000"+
		"\u0000\u0000b\u01a6\u0001\u0000\u0000\u0000d\u01aa\u0001\u0000\u0000\u0000"+
		"f\u01ae\u0001\u0000\u0000\u0000h\u01b5\u0001\u0000\u0000\u0000j\u01cc"+
		"\u0001\u0000\u0000\u0000l\u01d0\u0001\u0000\u0000\u0000n\u01d9\u0001\u0000"+
		"\u0000\u0000p\u01e2\u0001\u0000\u0000\u0000r\u01ed\u0001\u0000\u0000\u0000"+
		"t\u01f3\u0001\u0000\u0000\u0000v\u01f9\u0001\u0000\u0000\u0000x\u020f"+
		"\u0001\u0000\u0000\u0000z\u0211\u0001\u0000\u0000\u0000|\u0215\u0001\u0000"+
		"\u0000\u0000~\u0223\u0001\u0000\u0000\u0000\u0080\u0227\u0001\u0000\u0000"+
		"\u0000\u0082\u022c\u0001\u0000\u0000\u0000\u0084\u0236\u0001\u0000\u0000"+
		"\u0000\u0086\u0243\u0001\u0000\u0000\u0000\u0088\u024f\u0001\u0000\u0000"+
		"\u0000\u008a\u0259\u0001\u0000\u0000\u0000\u008c\u0261\u0001\u0000\u0000"+
		"\u0000\u008e\u008f\u0004\u0000\u0000\u0000\u008f\u009b\u0003d0\u0000\u0090"+
		"\u0092\u0005\r\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0091\u0092"+
		"\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0096"+
		"\u0005\n\u0000\u0000\u0094\u0096\u0005\r\u0000\u0000\u0095\u0091\u0001"+
		"\u0000\u0000\u0000\u0095\u0094\u0001\u0000\u0000\u0000\u0096\u0098\u0001"+
		"\u0000\u0000\u0000\u0097\u0099\u0003d0\u0000\u0098\u0097\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u009b\u0001\u0000\u0000"+
		"\u0000\u009a\u008e\u0001\u0000\u0000\u0000\u009a\u0095\u0001\u0000\u0000"+
		"\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009d\u0006\u0000\u0000"+
		"\u0000\u009d\u0005\u0001\u0000\u0000\u0000\u009e\u00a0\u0007\u0000\u0000"+
		"\u0000\u009f\u00a1\u0007\u0001\u0000\u0000\u00a0\u009f\u0001\u0000\u0000"+
		"\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1\u00a4\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a5\u0003V)\u0000\u00a3\u00a5\u0003X*\u0000\u00a4\u00a2"+
		"\u0001\u0000\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a5\u0007"+
		"\u0001\u0000\u0000\u0000\u00a6\u00aa\u0003B\u001f\u0000\u00a7\u00a9\u0003"+
		"D \u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a9\u00ac\u0001\u0000\u0000"+
		"\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00aa\u00ab\u0001\u0000\u0000"+
		"\u0000\u00ab\u00b3\u0001\u0000\u0000\u0000\u00ac\u00aa\u0001\u0000\u0000"+
		"\u0000\u00ad\u00af\u00050\u0000\u0000\u00ae\u00ad\u0001\u0000\u0000\u0000"+
		"\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000"+
		"\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u00b3\u0001\u0000\u0000\u0000"+
		"\u00b2\u00a6\u0001\u0000\u0000\u0000\u00b2\u00ae\u0001\u0000\u0000\u0000"+
		"\u00b3\t\u0001\u0000\u0000\u0000\u00b4\u00b5\u00050\u0000\u0000\u00b5"+
		"\u00b7\u0007\u0002\u0000\u0000\u00b6\u00b8\u0003F!\u0000\u00b7\u00b6\u0001"+
		"\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001"+
		"\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u000b\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bc\u00050\u0000\u0000\u00bc\u00be\u0007\u0003"+
		"\u0000\u0000\u00bd\u00bf\u0003H\"\u0000\u00be\u00bd\u0001\u0000\u0000"+
		"\u0000\u00bf\u00c0\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c1\u0001\u0000\u0000\u0000\u00c1\r\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c3\u00050\u0000\u0000\u00c3\u00c5\u0007\u0000\u0000\u0000\u00c4"+
		"\u00c6\u0003J#\u0000\u00c5\u00c4\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001"+
		"\u0000\u0000\u0000\u00c7\u00c5\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001"+
		"\u0000\u0000\u0000\u00c8\u000f\u0001\u0000\u0000\u0000\u00c9\u00cc\u0003"+
		"L$\u0000\u00ca\u00cc\u0003N%\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000"+
		"\u00cb\u00ca\u0001\u0000\u0000\u0000\u00cc\u0011\u0001\u0000\u0000\u0000"+
		"\u00cd\u00d0\u0003\u0010\u0006\u0000\u00ce\u00d0\u0003P&\u0000\u00cf\u00cd"+
		"\u0001\u0000\u0000\u0000\u00cf\u00ce\u0001\u0000\u0000\u0000\u00d0\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0007\u0004\u0000\u0000\u00d2\u0013"+
		"\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005-\u0000\u0000\u00d4\u0015\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d6\u0004\t\u0001\u0000\u00d6\u00d7\u0005-"+
		"\u0000\u0000\u00d7\u00d8\u0005-\u0000\u0000\u00d8\u00d9\u0005-\u0000\u0000"+
		"\u00d9\u0017\u0001\u0000\u0000\u0000\u00da\u00db\u0004\n\u0002\u0000\u00db"+
		"\u00dc\u0005.\u0000\u0000\u00dc\u00dd\u0005.\u0000\u0000\u00dd\u00de\u0005"+
		".\u0000\u0000\u00de\u0019\u0001\u0000\u0000\u0000\u00df\u00e0\u0005&\u0000"+
		"\u0000\u00e0\u001b\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005*\u0000\u0000"+
		"\u00e2\u001d\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005(\u0000\u0000\u00e4"+
		"\u00e5\u0006\r\u0001\u0000\u00e5\u001f\u0001\u0000\u0000\u0000\u00e6\u00e7"+
		"\u0005)\u0000\u0000\u00e7\u00e8\u0006\u000e\u0002\u0000\u00e8!\u0001\u0000"+
		"\u0000\u0000\u00e9\u00ea\u0005,\u0000\u0000\u00ea#\u0001\u0000\u0000\u0000"+
		"\u00eb\u00ec\u0005:\u0000\u0000\u00ec%\u0001\u0000\u0000\u0000\u00ed\u00ee"+
		"\u0005[\u0000\u0000\u00ee\u00ef\u0006\u0011\u0003\u0000\u00ef\u00f0\u0001"+
		"\u0000\u0000\u0000\u00f0\u00f1\u0006\u0011\u0004\u0000\u00f1\'\u0001\u0000"+
		"\u0000\u0000\u00f2\u00f3\u0005]\u0000\u0000\u00f3\u00f4\u0006\u0012\u0005"+
		"\u0000\u00f4)\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005{\u0000\u0000\u00f6"+
		"\u00f7\u0006\u0013\u0006\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8"+
		"\u00f9\u0006\u0013\u0004\u0000\u00f9+\u0001\u0000\u0000\u0000\u00fa\u00fb"+
		"\u0005}\u0000\u0000\u00fb\u00fc\u0006\u0014\u0007\u0000\u00fc-\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fe\u0005|\u0000\u0000\u00fe\u00ff\u0006\u0015\b"+
		"\u0000\u00ff\u0100\u0001\u0000\u0000\u0000\u0100\u0101\u0006\u0015\t\u0000"+
		"\u0101/\u0001\u0000\u0000\u0000\u0102\u0103\u0005>\u0000\u0000\u0103\u0104"+
		"\u0006\u0016\n\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105\u0106\u0006"+
		"\u0016\t\u0000\u01061\u0001\u0000\u0000\u0000\u0107\u0108\u0005\"\u0000"+
		"\u0000\u0108\u0109\u0001\u0000\u0000\u0000\u0109\u010a\u0006\u0017\u000b"+
		"\u0000\u010a3\u0001\u0000\u0000\u0000\u010b\u010c\u0003\u001a\u000b\u0000"+
		"\u010c\u010d\u00038\u001a\u0000\u010d5\u0001\u0000\u0000\u0000\u010e\u010f"+
		"\u0003\u001c\f\u0000\u010f\u0110\u00038\u001a\u0000\u01107\u0001\u0000"+
		"\u0000\u0000\u0111\u0113\u0007\u0005\u0000\u0000\u0112\u0111\u0001\u0000"+
		"\u0000\u0000\u0113\u0114\u0001\u0000\u0000\u0000\u0114\u0112\u0001\u0000"+
		"\u0000\u0000\u0114\u0115\u0001\u0000\u0000\u0000\u01159\u0001\u0000\u0000"+
		"\u0000\u0116\u0122\u0003<\u001c\u0000\u0117\u0121\b\u0006\u0000\u0000"+
		"\u0118\u0119\u0005:\u0000\u0000\u0119\u0121\b\u0007\u0000\u0000\u011a"+
		"\u011c\u0005 \u0000\u0000\u011b\u011a\u0001\u0000\u0000\u0000\u011c\u011d"+
		"\u0001\u0000\u0000\u0000\u011d\u011b\u0001\u0000\u0000\u0000\u011d\u011e"+
		"\u0001\u0000\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000\u011f\u0121"+
		"\b\b\u0000\u0000\u0120\u0117\u0001\u0000\u0000\u0000\u0120\u0118\u0001"+
		"\u0000\u0000\u0000\u0120\u011b\u0001\u0000\u0000\u0000\u0121\u0124\u0001"+
		"\u0000\u0000\u0000\u0122\u0120\u0001\u0000\u0000\u0000\u0122\u0123\u0001"+
		"\u0000\u0000\u0000\u0123;\u0001\u0000\u0000\u0000\u0124\u0122\u0001\u0000"+
		"\u0000\u0000\u0125\u0132\b\t\u0000\u0000\u0126\u0127\u0005:\u0000\u0000"+
		"\u0127\u0132\b\u0007\u0000\u0000\u0128\u0129\u0005-\u0000\u0000\u0129"+
		"\u0132\b\n\u0000\u0000\u012a\u012b\u0004\u001c\u0003\u0000\u012b\u012c"+
		"\u0005-\u0000\u0000\u012c\u0132\u0005-\u0000\u0000\u012d\u012e\u0004\u001c"+
		"\u0004\u0000\u012e\u012f\u0005-\u0000\u0000\u012f\u0130\u0005-\u0000\u0000"+
		"\u0130\u0132\u0005-\u0000\u0000\u0131\u0125\u0001\u0000\u0000\u0000\u0131"+
		"\u0126\u0001\u0000\u0000\u0000\u0131\u0128\u0001\u0000\u0000\u0000\u0131"+
		"\u012a\u0001\u0000\u0000\u0000\u0131\u012d\u0001\u0000\u0000\u0000\u0132"+
		"=\u0001\u0000\u0000\u0000\u0133\u0137\u0003d0\u0000\u0134\u0137\u0003"+
		"f1\u0000\u0135\u0137\u0003h2\u0000\u0136\u0133\u0001\u0000\u0000\u0000"+
		"\u0136\u0134\u0001\u0000\u0000\u0000\u0136\u0135\u0001\u0000\u0000\u0000"+
		"\u0137\u0138\u0001\u0000\u0000\u0000\u0138\u0139\u0006\u001d\f\u0000\u0139"+
		"?\u0001\u0000\u0000\u0000\u013a\u013b\t\u0000\u0000\u0000\u013bA\u0001"+
		"\u0000\u0000\u0000\u013c\u013d\u0007\u000b\u0000\u0000\u013dC\u0001\u0000"+
		"\u0000\u0000\u013e\u013f\u0007\f\u0000\u0000\u013fE\u0001\u0000\u0000"+
		"\u0000\u0140\u0141\u0007\r\u0000\u0000\u0141G\u0001\u0000\u0000\u0000"+
		"\u0142\u0143\u0007\u000e\u0000\u0000\u0143I\u0001\u0000\u0000\u0000\u0144"+
		"\u0145\u0007\u000f\u0000\u0000\u0145K\u0001\u0000\u0000\u0000\u0146\u0148"+
		"\u0003P&\u0000\u0147\u0146\u0001\u0000\u0000\u0000\u0147\u0148\u0001\u0000"+
		"\u0000\u0000\u0148\u0149\u0001\u0000\u0000\u0000\u0149\u014e\u0003R\'"+
		"\u0000\u014a\u014b\u0003P&\u0000\u014b\u014c\u0005.\u0000\u0000\u014c"+
		"\u014e\u0001\u0000\u0000\u0000\u014d\u0147\u0001\u0000\u0000\u0000\u014d"+
		"\u014a\u0001\u0000\u0000\u0000\u014eM\u0001\u0000\u0000\u0000\u014f\u0152"+
		"\u0003P&\u0000\u0150\u0152\u0003L$\u0000\u0151\u014f\u0001\u0000\u0000"+
		"\u0000\u0151\u0150\u0001\u0000\u0000\u0000\u0152\u0153\u0001\u0000\u0000"+
		"\u0000\u0153\u0154\u0003T(\u0000\u0154O\u0001\u0000\u0000\u0000\u0155"+
		"\u0157\u0003D \u0000\u0156\u0155\u0001\u0000\u0000\u0000\u0157\u0158\u0001"+
		"\u0000\u0000\u0000\u0158\u0156\u0001\u0000\u0000\u0000\u0158\u0159\u0001"+
		"\u0000\u0000\u0000\u0159Q\u0001\u0000\u0000\u0000\u015a\u015c\u0005.\u0000"+
		"\u0000\u015b\u015d\u0003D \u0000\u015c\u015b\u0001\u0000\u0000\u0000\u015d"+
		"\u015e\u0001\u0000\u0000\u0000\u015e\u015c\u0001\u0000\u0000\u0000\u015e"+
		"\u015f\u0001\u0000\u0000\u0000\u015fS\u0001\u0000\u0000\u0000\u0160\u0162"+
		"\u0007\u0010\u0000\u0000\u0161\u0163\u0007\u0011\u0000\u0000\u0162\u0161"+
		"\u0001\u0000\u0000\u0000\u0162\u0163\u0001\u0000\u0000\u0000\u0163\u0165"+
		"\u0001\u0000\u0000\u0000\u0164\u0166\u0003D \u0000\u0165\u0164\u0001\u0000"+
		"\u0000\u0000\u0166\u0167\u0001\u0000\u0000\u0000\u0167\u0165\u0001\u0000"+
		"\u0000\u0000\u0167\u0168\u0001\u0000\u0000\u0000\u0168U\u0001\u0000\u0000"+
		"\u0000\u0169\u016e\u0005\'\u0000\u0000\u016a\u016d\u0003\\,\u0000\u016b"+
		"\u016d\u0003b/\u0000\u016c\u016a\u0001\u0000\u0000\u0000\u016c\u016b\u0001"+
		"\u0000\u0000\u0000\u016d\u0170\u0001\u0000\u0000\u0000\u016e\u016c\u0001"+
		"\u0000\u0000\u0000\u016e\u016f\u0001\u0000\u0000\u0000\u016f\u0171\u0001"+
		"\u0000\u0000\u0000\u0170\u016e\u0001\u0000\u0000\u0000\u0171\u017c\u0005"+
		"\'\u0000\u0000\u0172\u0177\u0005\"\u0000\u0000\u0173\u0176\u0003^-\u0000"+
		"\u0174\u0176\u0003b/\u0000\u0175\u0173\u0001\u0000\u0000\u0000\u0175\u0174"+
		"\u0001\u0000\u0000\u0000\u0176\u0179\u0001\u0000\u0000\u0000\u0177\u0175"+
		"\u0001\u0000\u0000\u0000\u0177\u0178\u0001\u0000\u0000\u0000\u0178\u017a"+
		"\u0001\u0000\u0000\u0000\u0179\u0177\u0001\u0000\u0000\u0000\u017a\u017c"+
		"\u0005\"\u0000\u0000\u017b\u0169\u0001\u0000\u0000\u0000\u017b\u0172\u0001"+
		"\u0000\u0000\u0000\u017cW\u0001\u0000\u0000\u0000\u017d\u017e\u0005\'"+
		"\u0000\u0000\u017e\u017f\u0005\'\u0000\u0000\u017f\u0180\u0005\'\u0000"+
		"\u0000\u0180\u0184\u0001\u0000\u0000\u0000\u0181\u0183\u0003Z+\u0000\u0182"+
		"\u0181\u0001\u0000\u0000\u0000\u0183\u0186\u0001\u0000\u0000\u0000\u0184"+
		"\u0185\u0001\u0000\u0000\u0000\u0184\u0182\u0001\u0000\u0000\u0000\u0185"+
		"\u0187\u0001\u0000\u0000\u0000\u0186\u0184\u0001\u0000\u0000\u0000\u0187"+
		"\u0188\u0005\'\u0000\u0000\u0188\u0189\u0005\'\u0000\u0000\u0189\u0198"+
		"\u0005\'\u0000\u0000\u018a\u018b\u0005\"\u0000\u0000\u018b\u018c\u0005"+
		"\"\u0000\u0000\u018c\u018d\u0005\"\u0000\u0000\u018d\u0191\u0001\u0000"+
		"\u0000\u0000\u018e\u0190\u0003Z+\u0000\u018f\u018e\u0001\u0000\u0000\u0000"+
		"\u0190\u0193\u0001\u0000\u0000\u0000\u0191\u0192\u0001\u0000\u0000\u0000"+
		"\u0191\u018f\u0001\u0000\u0000\u0000\u0192\u0194\u0001\u0000\u0000\u0000"+
		"\u0193\u0191\u0001\u0000\u0000\u0000\u0194\u0195\u0005\"\u0000\u0000\u0195"+
		"\u0196\u0005\"\u0000\u0000\u0196\u0198\u0005\"\u0000\u0000\u0197\u017d"+
		"\u0001\u0000\u0000\u0000\u0197\u018a\u0001\u0000\u0000\u0000\u0198Y\u0001"+
		"\u0000\u0000\u0000\u0199\u019c\u0003`.\u0000\u019a\u019c\u0003b/\u0000"+
		"\u019b\u0199\u0001\u0000\u0000\u0000\u019b\u019a\u0001\u0000\u0000\u0000"+
		"\u019c[\u0001\u0000\u0000\u0000\u019d\u019f\u0007\u0012\u0000\u0000\u019e"+
		"\u019d\u0001\u0000\u0000\u0000\u019f]\u0001\u0000\u0000\u0000\u01a0\u01a2"+
		"\u0007\u0013\u0000\u0000\u01a1\u01a0\u0001\u0000\u0000\u0000\u01a2_\u0001"+
		"\u0000\u0000\u0000\u01a3\u01a5\u0007\u0014\u0000\u0000\u01a4\u01a3\u0001"+
		"\u0000\u0000\u0000\u01a5a\u0001\u0000\u0000\u0000\u01a6\u01a7\u0005\\"+
		"\u0000\u0000\u01a7\u01a8\u0007\u0015\u0000\u0000\u01a8c\u0001\u0000\u0000"+
		"\u0000\u01a9\u01ab\u0007\u0016\u0000\u0000\u01aa\u01a9\u0001\u0000\u0000"+
		"\u0000\u01ab\u01ac\u0001\u0000\u0000\u0000\u01ac\u01aa\u0001\u0000\u0000"+
		"\u0000\u01ac\u01ad\u0001\u0000\u0000\u0000\u01ade\u0001\u0000\u0000\u0000"+
		"\u01ae\u01b2\u0005#\u0000\u0000\u01af\u01b1\b\u0017\u0000\u0000\u01b0"+
		"\u01af\u0001\u0000\u0000\u0000\u01b1\u01b4\u0001\u0000\u0000\u0000\u01b2"+
		"\u01b0\u0001\u0000\u0000\u0000\u01b2\u01b3\u0001\u0000\u0000\u0000\u01b3"+
		"g\u0001\u0000\u0000\u0000\u01b4\u01b2\u0001\u0000\u0000\u0000\u01b5\u01b7"+
		"\u0005\\\u0000\u0000\u01b6\u01b8\u0003d0\u0000\u01b7\u01b6\u0001\u0000"+
		"\u0000\u0000\u01b7\u01b8\u0001\u0000\u0000\u0000\u01b8\u01be\u0001\u0000"+
		"\u0000\u0000\u01b9\u01bb\u0005\r\u0000\u0000\u01ba\u01b9\u0001\u0000\u0000"+
		"\u0000\u01ba\u01bb\u0001\u0000\u0000\u0000\u01bb\u01bc\u0001\u0000\u0000"+
		"\u0000\u01bc\u01bf\u0005\n\u0000\u0000\u01bd\u01bf\u0005\r\u0000\u0000"+
		"\u01be\u01ba\u0001\u0000\u0000\u0000\u01be\u01bd\u0001\u0000\u0000\u0000"+
		"\u01bfi\u0001\u0000\u0000\u0000\u01c0\u01c4\u0003B\u001f\u0000\u01c1\u01c3"+
		"\u0003D \u0000\u01c2\u01c1\u0001\u0000\u0000\u0000\u01c3\u01c6\u0001\u0000"+
		"\u0000\u0000\u01c4\u01c2\u0001\u0000\u0000\u0000\u01c4\u01c5\u0001\u0000"+
		"\u0000\u0000\u01c5\u01cd\u0001\u0000\u0000\u0000\u01c6\u01c4\u0001\u0000"+
		"\u0000\u0000\u01c7\u01c9\u00050\u0000\u0000\u01c8\u01c7\u0001\u0000\u0000"+
		"\u0000\u01c9\u01ca\u0001\u0000\u0000\u0000\u01ca\u01c8\u0001\u0000\u0000"+
		"\u0000\u01ca\u01cb\u0001\u0000\u0000\u0000\u01cb\u01cd\u0001\u0000\u0000"+
		"\u0000\u01cc\u01c0\u0001\u0000\u0000\u0000\u01cc\u01c8\u0001\u0000\u0000"+
		"\u0000\u01cd\u01ce\u0001\u0000\u0000\u0000\u01ce\u01cf\u00063\r\u0000"+
		"\u01cfk\u0001\u0000\u0000\u0000\u01d0\u01d1\u00050\u0000\u0000\u01d1\u01d3"+
		"\u0007\u0002\u0000\u0000\u01d2\u01d4\u0003F!\u0000\u01d3\u01d2\u0001\u0000"+
		"\u0000\u0000\u01d4\u01d5\u0001\u0000\u0000\u0000\u01d5\u01d3\u0001\u0000"+
		"\u0000\u0000\u01d5\u01d6\u0001\u0000\u0000\u0000\u01d6\u01d7\u0001\u0000"+
		"\u0000\u0000\u01d7\u01d8\u00064\u000e\u0000\u01d8m\u0001\u0000\u0000\u0000"+
		"\u01d9\u01da\u00050\u0000\u0000\u01da\u01dc\u0007\u0003\u0000\u0000\u01db"+
		"\u01dd\u0003H\"\u0000\u01dc\u01db\u0001\u0000\u0000\u0000\u01dd\u01de"+
		"\u0001\u0000\u0000\u0000\u01de\u01dc\u0001\u0000\u0000\u0000\u01de\u01df"+
		"\u0001\u0000\u0000\u0000\u01df\u01e0\u0001\u0000\u0000\u0000\u01e0\u01e1"+
		"\u00065\u000f\u0000\u01e1o\u0001\u0000\u0000\u0000\u01e2\u01e3\u00050"+
		"\u0000\u0000\u01e3\u01e5\u0007\u0000\u0000\u0000\u01e4\u01e6\u0003J#\u0000"+
		"\u01e5\u01e4\u0001\u0000\u0000\u0000\u01e6\u01e7\u0001\u0000\u0000\u0000"+
		"\u01e7\u01e5\u0001\u0000\u0000\u0000\u01e7\u01e8\u0001\u0000\u0000\u0000"+
		"\u01e8\u01e9\u0001\u0000\u0000\u0000\u01e9\u01ea\u00066\u0010\u0000\u01ea"+
		"q\u0001\u0000\u0000\u0000\u01eb\u01ee\u0003L$\u0000\u01ec\u01ee\u0003"+
		"N%\u0000\u01ed\u01eb\u0001\u0000\u0000\u0000\u01ed\u01ec\u0001\u0000\u0000"+
		"\u0000\u01ee\u01ef\u0001\u0000\u0000\u0000\u01ef\u01f0\u00067\u0011\u0000"+
		"\u01f0s\u0001\u0000\u0000\u0000\u01f1\u01f4\u0003\u0010\u0006\u0000\u01f2"+
		"\u01f4\u0003P&\u0000\u01f3\u01f1\u0001\u0000\u0000\u0000\u01f3\u01f2\u0001"+
		"\u0000\u0000\u0000\u01f4\u01f5\u0001\u0000\u0000\u0000\u01f5\u01f6\u0007"+
		"\u0004\u0000\u0000\u01f6\u01f7\u0001\u0000\u0000\u0000\u01f7\u01f8\u0006"+
		"8\u0012\u0000\u01f8u\u0001\u0000\u0000\u0000\u01f9\u0205\u0003x:\u0000"+
		"\u01fa\u0204\b\u0018\u0000\u0000\u01fb\u01fc\u0005:\u0000\u0000\u01fc"+
		"\u0204\b\u0007\u0000\u0000\u01fd\u01ff\u0005 \u0000\u0000\u01fe\u01fd"+
		"\u0001\u0000\u0000\u0000\u01ff\u0200\u0001\u0000\u0000\u0000\u0200\u01fe"+
		"\u0001\u0000\u0000\u0000\u0200\u0201\u0001\u0000\u0000\u0000\u0201\u0202"+
		"\u0001\u0000\u0000\u0000\u0202\u0204\b\u0019\u0000\u0000\u0203\u01fa\u0001"+
		"\u0000\u0000\u0000\u0203\u01fb\u0001\u0000\u0000\u0000\u0203\u01fe\u0001"+
		"\u0000\u0000\u0000\u0204\u0207\u0001\u0000\u0000\u0000\u0205\u0203\u0001"+
		"\u0000\u0000\u0000\u0205\u0206\u0001\u0000\u0000\u0000\u0206\u0208\u0001"+
		"\u0000\u0000\u0000\u0207\u0205\u0001\u0000\u0000\u0000\u0208\u0209\u0006"+
		"9\u0013\u0000\u0209w\u0001\u0000\u0000\u0000\u020a\u0210\b\u001a\u0000"+
		"\u0000\u020b\u020c\u0005:\u0000\u0000\u020c\u0210\b\u0007\u0000\u0000"+
		"\u020d\u020e\u0005-\u0000\u0000\u020e\u0210\b\u0007\u0000\u0000\u020f"+
		"\u020a\u0001\u0000\u0000\u0000\u020f\u020b\u0001\u0000\u0000\u0000\u020f"+
		"\u020d\u0001\u0000\u0000\u0000\u0210y\u0001\u0000\u0000\u0000\u0211\u0212"+
		"\u0005,\u0000\u0000\u0212\u0213\u0001\u0000\u0000\u0000\u0213\u0214\u0006"+
		";\u0014\u0000\u0214{\u0001\u0000\u0000\u0000\u0215\u0216\u0005:\u0000"+
		"\u0000\u0216\u0217\u0001\u0000\u0000\u0000\u0217\u0218\u0006<\u0015\u0000"+
		"\u0218}\u0001\u0000\u0000\u0000\u0219\u0224\u0003d0\u0000\u021a\u0224"+
		"\u0003f1\u0000\u021b\u0224\u0003h2\u0000\u021c\u021e\u0005\r\u0000\u0000"+
		"\u021d\u021c\u0001\u0000\u0000\u0000\u021d\u021e\u0001\u0000\u0000\u0000"+
		"\u021e\u021f\u0001\u0000\u0000\u0000\u021f\u0222\u0005\n\u0000\u0000\u0220"+
		"\u0222\u0005\r\u0000\u0000\u0221\u021d\u0001\u0000\u0000\u0000\u0221\u0220"+
		"\u0001\u0000\u0000\u0000\u0222\u0224\u0001\u0000\u0000\u0000\u0223\u0219"+
		"\u0001\u0000\u0000\u0000\u0223\u021a\u0001\u0000\u0000\u0000\u0223\u021b"+
		"\u0001\u0000\u0000\u0000\u0223\u0221\u0001\u0000\u0000\u0000\u0224\u0225"+
		"\u0001\u0000\u0000\u0000\u0225\u0226\u0006=\f\u0000\u0226\u007f\u0001"+
		"\u0000\u0000\u0000\u0227\u0228\u0005]\u0000\u0000\u0228\u0229\u0001\u0000"+
		"\u0000\u0000\u0229\u022a\u0006>\u0016\u0000\u022a\u022b\u0006>\u0017\u0000"+
		"\u022b\u0081\u0001\u0000\u0000\u0000\u022c\u022d\u0005}\u0000\u0000\u022d"+
		"\u022e\u0001\u0000\u0000\u0000\u022e\u022f\u0006?\u0018\u0000\u022f\u0230"+
		"\u0006?\u0017\u0000\u0230\u0083\u0001\u0000\u0000\u0000\u0231\u0233\u0005"+
		"\r\u0000\u0000\u0232\u0231\u0001\u0000\u0000\u0000\u0232\u0233\u0001\u0000"+
		"\u0000\u0000\u0233\u0234\u0001\u0000\u0000\u0000\u0234\u0237\u0005\n\u0000"+
		"\u0000\u0235\u0237\u0005\r\u0000\u0000\u0236\u0232\u0001\u0000\u0000\u0000"+
		"\u0236\u0235\u0001\u0000\u0000\u0000\u0237\u023b\u0001\u0000\u0000\u0000"+
		"\u0238\u023a\u0007\u0016\u0000\u0000\u0239\u0238\u0001\u0000\u0000\u0000"+
		"\u023a\u023d\u0001\u0000\u0000\u0000\u023b\u0239\u0001\u0000\u0000\u0000"+
		"\u023b\u023c\u0001\u0000\u0000\u0000\u023c\u023e\u0001\u0000\u0000\u0000"+
		"\u023d\u023b\u0001\u0000\u0000\u0000\u023e\u023f\u0006@\u0019\u0000\u023f"+
		"\u0085\u0001\u0000\u0000\u0000\u0240\u0242\b\u0017\u0000\u0000\u0241\u0240"+
		"\u0001\u0000\u0000\u0000\u0242\u0245\u0001\u0000\u0000\u0000\u0243\u0241"+
		"\u0001\u0000\u0000\u0000\u0243\u0244\u0001\u0000\u0000\u0000\u0244\u0246"+
		"\u0001\u0000\u0000\u0000\u0245\u0243\u0001\u0000\u0000\u0000\u0246\u0247"+
		"\u0006A\u001a\u0000\u0247\u0248\u0001\u0000\u0000\u0000\u0248\u0249\u0006"+
		"A\u0013\u0000\u0249\u0087\u0001\u0000\u0000\u0000\u024a\u024e\b\u001b"+
		"\u0000\u0000\u024b\u024c\u0005\\\u0000\u0000\u024c\u024e\u0005\\\u0000"+
		"\u0000\u024d\u024a\u0001\u0000\u0000\u0000\u024d\u024b\u0001\u0000\u0000"+
		"\u0000\u024e\u0251\u0001\u0000\u0000\u0000\u024f\u024d\u0001\u0000\u0000"+
		"\u0000\u024f\u0250\u0001\u0000\u0000\u0000\u0250\u0252\u0001\u0000\u0000"+
		"\u0000\u0251\u024f\u0001\u0000\u0000\u0000\u0252\u0253\u0006B\u0013\u0000"+
		"\u0253\u0089\u0001\u0000\u0000\u0000\u0254\u0256\u0005\r\u0000\u0000\u0255"+
		"\u0254\u0001\u0000\u0000\u0000\u0255\u0256\u0001\u0000\u0000\u0000\u0256"+
		"\u0257\u0001\u0000\u0000\u0000\u0257\u025a\u0005\n\u0000\u0000\u0258\u025a"+
		"\u0005\r\u0000\u0000\u0259\u0255\u0001\u0000\u0000\u0000\u0259\u0258\u0001"+
		"\u0000\u0000\u0000\u025a\u025e\u0001\u0000\u0000\u0000\u025b\u025d\u0007"+
		"\u0016\u0000\u0000\u025c\u025b\u0001\u0000\u0000\u0000\u025d\u0260\u0001"+
		"\u0000\u0000\u0000\u025e\u025c\u0001\u0000\u0000\u0000\u025e\u025f\u0001"+
		"\u0000\u0000\u0000\u025f\u008b\u0001\u0000\u0000\u0000\u0260\u025e\u0001"+
		"\u0000\u0000\u0000\u0261\u0262\u0005\"\u0000\u0000\u0262\u0263\u0001\u0000"+
		"\u0000\u0000\u0263\u0264\u0006D\u001b\u0000\u0264\u0265\u0006D\u0017\u0000"+
		"\u0265\u008d\u0001\u0000\u0000\u0000H\u0000\u0001\u0002\u0003\u0091\u0095"+
		"\u0098\u009a\u00a0\u00a4\u00aa\u00b0\u00b2\u00b9\u00c0\u00c7\u00cb\u00cf"+
		"\u0114\u011d\u0120\u0122\u0131\u0136\u0147\u014d\u0151\u0158\u015e\u0162"+
		"\u0167\u016c\u016e\u0175\u0177\u017b\u0184\u0191\u0197\u019b\u019e\u01a1"+
		"\u01a4\u01ac\u01b2\u01b7\u01ba\u01be\u01c4\u01ca\u01cc\u01d5\u01de\u01e7"+
		"\u01ed\u01f3\u0200\u0203\u0205\u020f\u021d\u0221\u0223\u0232\u0236\u023b"+
		"\u0243\u024d\u024f\u0255\u0259\u025e\u001c\u0001\u0000\u0000\u0001\r\u0001"+
		"\u0001\u000e\u0002\u0001\u0011\u0003\u0005\u0001\u0000\u0001\u0012\u0004"+
		"\u0001\u0013\u0005\u0001\u0014\u0006\u0001\u0015\u0007\u0005\u0002\u0000"+
		"\u0001\u0016\b\u0005\u0003\u0000\u0006\u0000\u0000\u0007\u0005\u0000\u0007"+
		"\u0006\u0000\u0007\u0007\u0000\u0007\b\u0000\u0007\t\u0000\u0007\n\u0000"+
		"\u0007\u001d\u0000\u0007\u0012\u0000\u0007\u0013\u0000\u0007\u0015\u0000"+
		"\u0004\u0000\u0000\u0007\u0017\u0000\u0001@\t\u0001A\n\u0007\u001a\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}