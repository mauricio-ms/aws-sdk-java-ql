package yamlparser;

// Generated from YamlParser.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YamlParser extends Parser {
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
		RULE_file = 0, RULE_document = 1, RULE_list = 2, RULE_listitem = 3, RULE_mappinglist = 4, 
		RULE_key = 5, RULE_value = 6, RULE_value_scalar = 7, RULE_multiline_string = 8, 
		RULE_string_literal = 9, RULE_string_folded = 10, RULE_string_double_quoted = 11, 
		RULE_mapping = 12, RULE_flowlist = 13, RULE_flowmappinglist = 14, RULE_number = 15, 
		RULE_integer = 16;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "document", "list", "listitem", "mappinglist", "key", "value", 
			"value_scalar", "multiline_string", "string_literal", "string_folded", 
			"string_double_quoted", "mapping", "flowlist", "flowmappinglist", "number", 
			"integer"
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

	@Override
	public String getGrammarFileName() { return "YamlParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public YamlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(YamlParser.EOF, 0); }
		public List<DocumentContext> document() {
			return getRuleContexts(DocumentContext.class);
		}
		public DocumentContext document(int i) {
			return getRuleContext(DocumentContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public List<TerminalNode> DOCUMENTSTART() { return getTokens(YamlParser.DOCUMENTSTART); }
		public TerminalNode DOCUMENTSTART(int i) {
			return getToken(YamlParser.DOCUMENTSTART, i);
		}
		public List<TerminalNode> DOCUMENTEND() { return getTokens(YamlParser.DOCUMENTEND); }
		public TerminalNode DOCUMENTEND(int i) {
			return getToken(YamlParser.DOCUMENTEND, i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitFile(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(34);
					match(NEWLINE);
					}
					} 
				}
				setState(39);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			{
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOCUMENTSTART) {
				{
				setState(40);
				match(DOCUMENTSTART);
				setState(42);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(41);
					match(NEWLINE);
					}
					break;
				}
				}
			}

			setState(46);
			document();
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOCUMENTEND) {
				{
				setState(47);
				match(DOCUMENTEND);
				setState(48);
				match(NEWLINE);
				}
			}

			setState(69);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(54);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==NEWLINE) {
						{
						{
						setState(51);
						match(NEWLINE);
						}
						}
						setState(56);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					{
					setState(57);
					match(DOCUMENTSTART);
					setState(58);
					match(NEWLINE);
					}
					setState(60);
					document();
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==DOCUMENTEND) {
						{
						setState(61);
						match(DOCUMENTEND);
						setState(63);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
						case 1:
							{
							setState(62);
							match(NEWLINE);
							}
							break;
						}
						}
					}

					}
					} 
				}
				setState(71);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(72);
				match(NEWLINE);
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DocumentContext extends ParserRuleContext {
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public MappinglistContext mappinglist() {
			return getRuleContext(MappinglistContext.class,0);
		}
		public FlowlistContext flowlist() {
			return getRuleContext(FlowlistContext.class,0);
		}
		public FlowmappinglistContext flowmappinglist() {
			return getRuleContext(FlowmappinglistContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitDocument(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_document);
		try {
			setState(85);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				list();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				mappinglist();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				flowlist();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				flowmappinglist();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(84);
				value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public List<ListitemContext> listitem() {
			return getRuleContexts(ListitemContext.class);
		}
		public ListitemContext listitem(int i) {
			return getRuleContext(ListitemContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitList(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(97); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(90);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==NEWLINE) {
						{
						{
						setState(87);
						match(NEWLINE);
						}
						}
						setState(92);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(93);
					listitem();
					setState(95);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						setState(94);
						match(NEWLINE);
						}
						break;
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(99); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListitemContext extends ParserRuleContext {
		public TerminalNode MINUS() { return getToken(YamlParser.MINUS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode INDENT() { return getToken(YamlParser.INDENT, 0); }
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public TerminalNode DEDENT() { return getToken(YamlParser.DEDENT, 0); }
		public MappinglistContext mappinglist() {
			return getRuleContext(MappinglistContext.class,0);
		}
		public FlowlistContext flowlist() {
			return getRuleContext(FlowlistContext.class,0);
		}
		public TerminalNode ANCHOR() { return getToken(YamlParser.ANCHOR, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public TerminalNode ALIAS() { return getToken(YamlParser.ALIAS, 0); }
		public ListitemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listitem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterListitem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitListitem(this);
		}
	}

	public final ListitemContext listitem() throws RecognitionException {
		ListitemContext _localctx = new ListitemContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_listitem);
		int _la;
		try {
			setState(131);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				match(MINUS);
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ANCHOR) {
					{
					setState(102);
					match(ANCHOR);
					}
				}

				setState(125);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(105);
					value();
					}
					break;
				case 2:
					{
					setState(107); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(106);
						match(NEWLINE);
						}
						}
						setState(109); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==NEWLINE );
					setState(111);
					match(INDENT);
					setState(112);
					list();
					setState(113);
					match(DEDENT);
					}
					break;
				case 3:
					{
					setState(116); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(115);
						match(NEWLINE);
						}
						}
						setState(118); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==NEWLINE );
					setState(120);
					match(INDENT);
					setState(121);
					mappinglist();
					setState(122);
					match(DEDENT);
					}
					break;
				case 4:
					{
					setState(124);
					flowlist();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(127);
				match(MINUS);
				setState(128);
				match(ALIAS);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				match(MINUS);
				setState(130);
				match(NEWLINE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MappinglistContext extends ParserRuleContext {
		public List<MappingContext> mapping() {
			return getRuleContexts(MappingContext.class);
		}
		public MappingContext mapping(int i) {
			return getRuleContext(MappingContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public MappinglistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mappinglist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterMappinglist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitMappinglist(this);
		}
	}

	public final MappinglistContext mappinglist() throws RecognitionException {
		MappinglistContext _localctx = new MappinglistContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_mappinglist);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(143); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(136);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==NEWLINE) {
						{
						{
						setState(133);
						match(NEWLINE);
						}
						}
						setState(138);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(139);
					mapping();
					setState(141);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						setState(140);
						match(NEWLINE);
						}
						break;
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(145); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyContext extends ParserRuleContext {
		public TerminalNode STRING_MY() { return getToken(YamlParser.STRING_MY, 0); }
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitKey(this);
		}
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_key);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(STRING_MY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public Value_scalarContext value_scalar() {
			return getRuleContext(Value_scalarContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public TerminalNode INDENT() { return getToken(YamlParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(YamlParser.DEDENT, 0); }
		public Multiline_stringContext multiline_string() {
			return getRuleContext(Multiline_stringContext.class,0);
		}
		public String_literalContext string_literal() {
			return getRuleContext(String_literalContext.class,0);
		}
		public String_foldedContext string_folded() {
			return getRuleContext(String_foldedContext.class,0);
		}
		public String_double_quotedContext string_double_quoted() {
			return getRuleContext(String_double_quotedContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_value);
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(149);
				value_scalar();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(150);
				match(NEWLINE);
				setState(151);
				match(INDENT);
				setState(152);
				value_scalar();
				setState(153);
				match(NEWLINE);
				setState(154);
				match(DEDENT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				multiline_string();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(157);
				string_literal();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(158);
				string_folded();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(159);
				string_double_quoted();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Value_scalarContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode STRING_MY() { return getToken(YamlParser.STRING_MY, 0); }
		public Value_scalarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value_scalar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterValue_scalar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitValue_scalar(this);
		}
	}

	public final Value_scalarContext value_scalar() throws RecognitionException {
		Value_scalarContext _localctx = new Value_scalarContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_value_scalar);
		try {
			setState(164);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_INTEGER:
			case OCT_INTEGER:
			case HEX_INTEGER:
			case BIN_INTEGER:
			case FLOAT_NUMBER:
			case IMAG_NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(162);
				number();
				}
				break;
			case STRING_MY:
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				match(STRING_MY);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiline_stringContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public TerminalNode INDENT() { return getToken(YamlParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(YamlParser.DEDENT, 0); }
		public List<Value_scalarContext> value_scalar() {
			return getRuleContexts(Value_scalarContext.class);
		}
		public Value_scalarContext value_scalar(int i) {
			return getRuleContext(Value_scalarContext.class,i);
		}
		public List<Multiline_stringContext> multiline_string() {
			return getRuleContexts(Multiline_stringContext.class);
		}
		public Multiline_stringContext multiline_string(int i) {
			return getRuleContext(Multiline_stringContext.class,i);
		}
		public Multiline_stringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiline_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterMultiline_string(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitMultiline_string(this);
		}
	}

	public final Multiline_stringContext multiline_string() throws RecognitionException {
		Multiline_stringContext _localctx = new Multiline_stringContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_multiline_string);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DECIMAL_INTEGER) | (1L << OCT_INTEGER) | (1L << HEX_INTEGER) | (1L << BIN_INTEGER) | (1L << FLOAT_NUMBER) | (1L << IMAG_NUMBER) | (1L << STRING_MY))) != 0)) {
				{
				setState(166);
				value_scalar();
				}
			}

			setState(169);
			match(NEWLINE);
			setState(170);
			match(INDENT);
			setState(180); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(180);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
					case 1:
						{
						setState(171);
						value_scalar();
						setState(176);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(172);
								match(NEWLINE);
								setState(173);
								value_scalar();
								}
								} 
							}
							setState(178);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
						}
						}
						break;
					case 2:
						{
						setState(179);
						multiline_string();
						}
						break;
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(182); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NEWLINE) {
				{
				setState(184);
				match(NEWLINE);
				}
			}

			setState(187);
			match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_literalContext extends ParserRuleContext {
		public TerminalNode LITERIAL_STR_IND() { return getToken(YamlParser.LITERIAL_STR_IND, 0); }
		public List<TerminalNode> STRING_MY() { return getTokens(YamlParser.STRING_MY); }
		public TerminalNode STRING_MY(int i) {
			return getToken(YamlParser.STRING_MY, i);
		}
		public List<TerminalNode> NEWLINE_STR_LITERAL() { return getTokens(YamlParser.NEWLINE_STR_LITERAL); }
		public TerminalNode NEWLINE_STR_LITERAL(int i) {
			return getToken(YamlParser.NEWLINE_STR_LITERAL, i);
		}
		public String_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterString_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitString_literal(this);
		}
	}

	public final String_literalContext string_literal() throws RecognitionException {
		String_literalContext _localctx = new String_literalContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_string_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(LITERIAL_STR_IND);
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE_STR_LITERAL) {
				{
				{
				setState(191); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(190);
					match(NEWLINE_STR_LITERAL);
					}
					}
					setState(193); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NEWLINE_STR_LITERAL );
				setState(195);
				match(STRING_MY);
				}
				}
				setState(200);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_foldedContext extends ParserRuleContext {
		public TerminalNode FOLD_STR_IND() { return getToken(YamlParser.FOLD_STR_IND, 0); }
		public List<TerminalNode> STRING_MY() { return getTokens(YamlParser.STRING_MY); }
		public TerminalNode STRING_MY(int i) {
			return getToken(YamlParser.STRING_MY, i);
		}
		public List<TerminalNode> NEWLINE_STR_LITERAL() { return getTokens(YamlParser.NEWLINE_STR_LITERAL); }
		public TerminalNode NEWLINE_STR_LITERAL(int i) {
			return getToken(YamlParser.NEWLINE_STR_LITERAL, i);
		}
		public String_foldedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_folded; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterString_folded(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitString_folded(this);
		}
	}

	public final String_foldedContext string_folded() throws RecognitionException {
		String_foldedContext _localctx = new String_foldedContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_string_folded);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(FOLD_STR_IND);
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE_STR_LITERAL) {
				{
				{
				setState(203); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(202);
					match(NEWLINE_STR_LITERAL);
					}
					}
					setState(205); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NEWLINE_STR_LITERAL );
				setState(207);
				match(STRING_MY);
				}
				}
				setState(212);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_double_quotedContext extends ParserRuleContext {
		public List<TerminalNode> DOUBLE_QUOTE() { return getTokens(YamlParser.DOUBLE_QUOTE); }
		public TerminalNode DOUBLE_QUOTE(int i) {
			return getToken(YamlParser.DOUBLE_QUOTE, i);
		}
		public List<TerminalNode> STRING_MY() { return getTokens(YamlParser.STRING_MY); }
		public TerminalNode STRING_MY(int i) {
			return getToken(YamlParser.STRING_MY, i);
		}
		public List<TerminalNode> NEWLINE_STR_QUOTE() { return getTokens(YamlParser.NEWLINE_STR_QUOTE); }
		public TerminalNode NEWLINE_STR_QUOTE(int i) {
			return getToken(YamlParser.NEWLINE_STR_QUOTE, i);
		}
		public String_double_quotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_double_quoted; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterString_double_quoted(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitString_double_quoted(this);
		}
	}

	public final String_double_quotedContext string_double_quoted() throws RecognitionException {
		String_double_quotedContext _localctx = new String_double_quotedContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_string_double_quoted);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(DOUBLE_QUOTE);
			setState(214);
			match(STRING_MY);
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE_STR_QUOTE) {
				{
				{
				setState(216); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(215);
					match(NEWLINE_STR_QUOTE);
					}
					}
					setState(218); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NEWLINE_STR_QUOTE );
				setState(220);
				match(STRING_MY);
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(226);
			match(DOUBLE_QUOTE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MappingContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public TerminalNode COLON() { return getToken(YamlParser.COLON, 0); }
		public TerminalNode INDENT() { return getToken(YamlParser.INDENT, 0); }
		public MappinglistContext mappinglist() {
			return getRuleContext(MappinglistContext.class,0);
		}
		public TerminalNode DEDENT() { return getToken(YamlParser.DEDENT, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(YamlParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(YamlParser.NEWLINE, i);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public FlowmappinglistContext flowmappinglist() {
			return getRuleContext(FlowmappinglistContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public MappingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapping; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterMapping(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitMapping(this);
		}
	}

	public final MappingContext mapping() throws RecognitionException {
		MappingContext _localctx = new MappingContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_mapping);
		int _la;
		try {
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				key();
				setState(229);
				match(COLON);
				setState(231); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(230);
					match(NEWLINE);
					}
					}
					setState(233); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NEWLINE );
				setState(235);
				match(INDENT);
				setState(236);
				mappinglist();
				setState(237);
				match(DEDENT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				key();
				setState(240);
				match(COLON);
				setState(242); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(241);
					match(NEWLINE);
					}
					}
					setState(244); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NEWLINE );
				setState(246);
				match(INDENT);
				setState(247);
				list();
				setState(248);
				match(DEDENT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(250);
				key();
				setState(251);
				match(COLON);
				setState(252);
				flowmappinglist();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(254);
				key();
				setState(255);
				match(COLON);
				setState(257);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
				case 1:
					{
					setState(256);
					value();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FlowlistContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACK() { return getToken(YamlParser.OPEN_BRACK, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public TerminalNode CLOSE_BRACK() { return getToken(YamlParser.CLOSE_BRACK, 0); }
		public List<TerminalNode> COMMA() { return getTokens(YamlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(YamlParser.COMMA, i);
		}
		public FlowlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_flowlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterFlowlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitFlowlist(this);
		}
	}

	public final FlowlistContext flowlist() throws RecognitionException {
		FlowlistContext _localctx = new FlowlistContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_flowlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(OPEN_BRACK);
			setState(262);
			value();
			setState(267);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(263);
				match(COMMA);
				setState(264);
				value();
				}
				}
				setState(269);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(270);
			match(CLOSE_BRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FlowmappinglistContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACE() { return getToken(YamlParser.OPEN_BRACE, 0); }
		public List<MappingContext> mapping() {
			return getRuleContexts(MappingContext.class);
		}
		public MappingContext mapping(int i) {
			return getRuleContext(MappingContext.class,i);
		}
		public TerminalNode CLOSE_BRACE() { return getToken(YamlParser.CLOSE_BRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(YamlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(YamlParser.COMMA, i);
		}
		public FlowmappinglistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_flowmappinglist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterFlowmappinglist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitFlowmappinglist(this);
		}
	}

	public final FlowmappinglistContext flowmappinglist() throws RecognitionException {
		FlowmappinglistContext _localctx = new FlowmappinglistContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_flowmappinglist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			match(OPEN_BRACE);
			setState(273);
			mapping();
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(274);
				match(COMMA);
				setState(275);
				mapping();
				}
				}
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(281);
			match(CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public TerminalNode FLOAT_NUMBER() { return getToken(YamlParser.FLOAT_NUMBER, 0); }
		public TerminalNode IMAG_NUMBER() { return getToken(YamlParser.IMAG_NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_number);
		try {
			setState(286);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_INTEGER:
			case OCT_INTEGER:
			case HEX_INTEGER:
			case BIN_INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(283);
				integer();
				}
				break;
			case FLOAT_NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(284);
				match(FLOAT_NUMBER);
				}
				break;
			case IMAG_NUMBER:
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				match(IMAG_NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerContext extends ParserRuleContext {
		public TerminalNode DECIMAL_INTEGER() { return getToken(YamlParser.DECIMAL_INTEGER, 0); }
		public TerminalNode OCT_INTEGER() { return getToken(YamlParser.OCT_INTEGER, 0); }
		public TerminalNode HEX_INTEGER() { return getToken(YamlParser.HEX_INTEGER, 0); }
		public TerminalNode BIN_INTEGER() { return getToken(YamlParser.BIN_INTEGER, 0); }
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YamlParserListener ) ((YamlParserListener)listener).exitInteger(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_integer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DECIMAL_INTEGER) | (1L << OCT_INTEGER) | (1L << HEX_INTEGER) | (1L << BIN_INTEGER))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\"\u0123\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0001\u0000\u0005\u0000$\b\u0000\n\u0000\f\u0000"+
		"\'\t\u0000\u0001\u0000\u0001\u0000\u0003\u0000+\b\u0000\u0003\u0000-\b"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u00002\b\u0000\u0001"+
		"\u0000\u0005\u00005\b\u0000\n\u0000\f\u00008\t\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000@\b"+
		"\u0000\u0003\u0000B\b\u0000\u0005\u0000D\b\u0000\n\u0000\f\u0000G\t\u0000"+
		"\u0001\u0000\u0005\u0000J\b\u0000\n\u0000\f\u0000M\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001V\b\u0001\u0001\u0002\u0005\u0002Y\b\u0002\n\u0002\f\u0002"+
		"\\\t\u0002\u0001\u0002\u0001\u0002\u0003\u0002`\b\u0002\u0004\u0002b\b"+
		"\u0002\u000b\u0002\f\u0002c\u0001\u0003\u0001\u0003\u0003\u0003h\b\u0003"+
		"\u0001\u0003\u0001\u0003\u0004\u0003l\b\u0003\u000b\u0003\f\u0003m\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0004\u0003u\b"+
		"\u0003\u000b\u0003\f\u0003v\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003~\b\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003\u0084\b\u0003\u0001\u0004\u0005\u0004\u0087"+
		"\b\u0004\n\u0004\f\u0004\u008a\t\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"\u008e\b\u0004\u0004\u0004\u0090\b\u0004\u000b\u0004\f\u0004\u0091\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006\u00a1\b\u0006\u0001\u0007\u0001\u0007\u0003\u0007\u00a5"+
		"\b\u0007\u0001\b\u0003\b\u00a8\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0005\b\u00af\b\b\n\b\f\b\u00b2\t\b\u0001\b\u0004\b\u00b5\b\b\u000b"+
		"\b\f\b\u00b6\u0001\b\u0003\b\u00ba\b\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0004\t\u00c0\b\t\u000b\t\f\t\u00c1\u0001\t\u0005\t\u00c5\b\t\n\t\f\t"+
		"\u00c8\t\t\u0001\n\u0001\n\u0004\n\u00cc\b\n\u000b\n\f\n\u00cd\u0001\n"+
		"\u0005\n\u00d1\b\n\n\n\f\n\u00d4\t\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0004\u000b\u00d9\b\u000b\u000b\u000b\f\u000b\u00da\u0001\u000b\u0005"+
		"\u000b\u00de\b\u000b\n\u000b\f\u000b\u00e1\t\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0004\f\u00e8\b\f\u000b\f\f\f\u00e9\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0004\f\u00f3\b\f\u000b\f\f"+
		"\f\u00f4\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0003\f\u0102\b\f\u0003\f\u0104\b\f\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0005\r\u010a\b\r\n\r\f\r\u010d\t\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0115\b"+
		"\u000e\n\u000e\f\u000e\u0118\t\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0003\u000f\u011f\b\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0000\u0000\u0011\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u001e \u0000\u0001\u0001\u0000\u0005"+
		"\b\u0147\u0000%\u0001\u0000\u0000\u0000\u0002U\u0001\u0000\u0000\u0000"+
		"\u0004a\u0001\u0000\u0000\u0000\u0006\u0083\u0001\u0000\u0000\u0000\b"+
		"\u008f\u0001\u0000\u0000\u0000\n\u0093\u0001\u0000\u0000\u0000\f\u00a0"+
		"\u0001\u0000\u0000\u0000\u000e\u00a4\u0001\u0000\u0000\u0000\u0010\u00a7"+
		"\u0001\u0000\u0000\u0000\u0012\u00bd\u0001\u0000\u0000\u0000\u0014\u00c9"+
		"\u0001\u0000\u0000\u0000\u0016\u00d5\u0001\u0000\u0000\u0000\u0018\u0103"+
		"\u0001\u0000\u0000\u0000\u001a\u0105\u0001\u0000\u0000\u0000\u001c\u0110"+
		"\u0001\u0000\u0000\u0000\u001e\u011e\u0001\u0000\u0000\u0000 \u0120\u0001"+
		"\u0000\u0000\u0000\"$\u0005\u0003\u0000\u0000#\"\u0001\u0000\u0000\u0000"+
		"$\'\u0001\u0000\u0000\u0000%#\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000"+
		"\u0000&,\u0001\u0000\u0000\u0000\'%\u0001\u0000\u0000\u0000(*\u0005\f"+
		"\u0000\u0000)+\u0005\u0003\u0000\u0000*)\u0001\u0000\u0000\u0000*+\u0001"+
		"\u0000\u0000\u0000+-\u0001\u0000\u0000\u0000,(\u0001\u0000\u0000\u0000"+
		",-\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000.1\u0003\u0002\u0001"+
		"\u0000/0\u0005\r\u0000\u000002\u0005\u0003\u0000\u00001/\u0001\u0000\u0000"+
		"\u000012\u0001\u0000\u0000\u00002E\u0001\u0000\u0000\u000035\u0005\u0003"+
		"\u0000\u000043\u0001\u0000\u0000\u000058\u0001\u0000\u0000\u000064\u0001"+
		"\u0000\u0000\u000067\u0001\u0000\u0000\u000079\u0001\u0000\u0000\u0000"+
		"86\u0001\u0000\u0000\u00009:\u0005\f\u0000\u0000:;\u0005\u0003\u0000\u0000"+
		";<\u0001\u0000\u0000\u0000<A\u0003\u0002\u0001\u0000=?\u0005\r\u0000\u0000"+
		">@\u0005\u0003\u0000\u0000?>\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000"+
		"\u0000@B\u0001\u0000\u0000\u0000A=\u0001\u0000\u0000\u0000AB\u0001\u0000"+
		"\u0000\u0000BD\u0001\u0000\u0000\u0000C6\u0001\u0000\u0000\u0000DG\u0001"+
		"\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000"+
		"FK\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000HJ\u0005\u0003\u0000"+
		"\u0000IH\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000"+
		"\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000\u0000MK\u0001"+
		"\u0000\u0000\u0000NO\u0005\u0000\u0000\u0001O\u0001\u0001\u0000\u0000"+
		"\u0000PV\u0003\u0004\u0002\u0000QV\u0003\b\u0004\u0000RV\u0003\u001a\r"+
		"\u0000SV\u0003\u001c\u000e\u0000TV\u0003\f\u0006\u0000UP\u0001\u0000\u0000"+
		"\u0000UQ\u0001\u0000\u0000\u0000UR\u0001\u0000\u0000\u0000US\u0001\u0000"+
		"\u0000\u0000UT\u0001\u0000\u0000\u0000V\u0003\u0001\u0000\u0000\u0000"+
		"WY\u0005\u0003\u0000\u0000XW\u0001\u0000\u0000\u0000Y\\\u0001\u0000\u0000"+
		"\u0000ZX\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[]\u0001\u0000"+
		"\u0000\u0000\\Z\u0001\u0000\u0000\u0000]_\u0003\u0006\u0003\u0000^`\u0005"+
		"\u0003\u0000\u0000_^\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000"+
		"`b\u0001\u0000\u0000\u0000aZ\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000"+
		"\u0000ca\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000d\u0005\u0001"+
		"\u0000\u0000\u0000eg\u0005\u000b\u0000\u0000fh\u0005\u001b\u0000\u0000"+
		"gf\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000h}\u0001\u0000\u0000"+
		"\u0000i~\u0003\f\u0006\u0000jl\u0005\u0003\u0000\u0000kj\u0001\u0000\u0000"+
		"\u0000lm\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000\u0000mn\u0001\u0000"+
		"\u0000\u0000no\u0001\u0000\u0000\u0000op\u0005\u0001\u0000\u0000pq\u0003"+
		"\u0004\u0002\u0000qr\u0005\u0002\u0000\u0000r~\u0001\u0000\u0000\u0000"+
		"su\u0005\u0003\u0000\u0000ts\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000"+
		"\u0000vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wx\u0001\u0000"+
		"\u0000\u0000xy\u0005\u0001\u0000\u0000yz\u0003\b\u0004\u0000z{\u0005\u0002"+
		"\u0000\u0000{~\u0001\u0000\u0000\u0000|~\u0003\u001a\r\u0000}i\u0001\u0000"+
		"\u0000\u0000}k\u0001\u0000\u0000\u0000}t\u0001\u0000\u0000\u0000}|\u0001"+
		"\u0000\u0000\u0000~\u0084\u0001\u0000\u0000\u0000\u007f\u0080\u0005\u000b"+
		"\u0000\u0000\u0080\u0084\u0005\u001c\u0000\u0000\u0081\u0082\u0005\u000b"+
		"\u0000\u0000\u0082\u0084\u0005\u0003\u0000\u0000\u0083e\u0001\u0000\u0000"+
		"\u0000\u0083\u007f\u0001\u0000\u0000\u0000\u0083\u0081\u0001\u0000\u0000"+
		"\u0000\u0084\u0007\u0001\u0000\u0000\u0000\u0085\u0087\u0005\u0003\u0000"+
		"\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\u008a\u0001\u0000\u0000"+
		"\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000"+
		"\u0000\u0089\u008b\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000"+
		"\u0000\u008b\u008d\u0003\u0018\f\u0000\u008c\u008e\u0005\u0003\u0000\u0000"+
		"\u008d\u008c\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000"+
		"\u008e\u0090\u0001\u0000\u0000\u0000\u008f\u0088\u0001\u0000\u0000\u0000"+
		"\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0001\u0000\u0000\u0000\u0092\t\u0001\u0000\u0000\u0000\u0093"+
		"\u0094\u0005\u001d\u0000\u0000\u0094\u000b\u0001\u0000\u0000\u0000\u0095"+
		"\u00a1\u0003\u000e\u0007\u0000\u0096\u0097\u0005\u0003\u0000\u0000\u0097"+
		"\u0098\u0005\u0001\u0000\u0000\u0098\u0099\u0003\u000e\u0007\u0000\u0099"+
		"\u009a\u0005\u0003\u0000\u0000\u009a\u009b\u0005\u0002\u0000\u0000\u009b"+
		"\u00a1\u0001\u0000\u0000\u0000\u009c\u00a1\u0003\u0010\b\u0000\u009d\u00a1"+
		"\u0003\u0012\t\u0000\u009e\u00a1\u0003\u0014\n\u0000\u009f\u00a1\u0003"+
		"\u0016\u000b\u0000\u00a0\u0095\u0001\u0000\u0000\u0000\u00a0\u0096\u0001"+
		"\u0000\u0000\u0000\u00a0\u009c\u0001\u0000\u0000\u0000\u00a0\u009d\u0001"+
		"\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u009f\u0001"+
		"\u0000\u0000\u0000\u00a1\r\u0001\u0000\u0000\u0000\u00a2\u00a5\u0003\u001e"+
		"\u000f\u0000\u00a3\u00a5\u0005\u001d\u0000\u0000\u00a4\u00a2\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a5\u000f\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a8\u0003\u000e\u0007\u0000\u00a7\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000"+
		"\u0000\u0000\u00a9\u00aa\u0005\u0003\u0000\u0000\u00aa\u00b4\u0005\u0001"+
		"\u0000\u0000\u00ab\u00b0\u0003\u000e\u0007\u0000\u00ac\u00ad\u0005\u0003"+
		"\u0000\u0000\u00ad\u00af\u0003\u000e\u0007\u0000\u00ae\u00ac\u0001\u0000"+
		"\u0000\u0000\u00af\u00b2\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000"+
		"\u0000\u0000\u00b0\u00b1\u0001\u0000\u0000\u0000\u00b1\u00b5\u0001\u0000"+
		"\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b5\u0003\u0010"+
		"\b\u0000\u00b4\u00ab\u0001\u0000\u0000\u0000\u00b4\u00b3\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7\u00b9\u0001\u0000\u0000"+
		"\u0000\u00b8\u00ba\u0005\u0003\u0000\u0000\u00b9\u00b8\u0001\u0000\u0000"+
		"\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00bb\u0001\u0000\u0000"+
		"\u0000\u00bb\u00bc\u0005\u0002\u0000\u0000\u00bc\u0011\u0001\u0000\u0000"+
		"\u0000\u00bd\u00c6\u0005\u0018\u0000\u0000\u00be\u00c0\u0005!\u0000\u0000"+
		"\u00bf\u00be\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000\u0000\u0000"+
		"\u00c1\u00bf\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u00c5\u0005\u001d\u0000\u0000"+
		"\u00c4\u00bf\u0001\u0000\u0000\u0000\u00c5\u00c8\u0001\u0000\u0000\u0000"+
		"\u00c6\u00c4\u0001\u0000\u0000\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000"+
		"\u00c7\u0013\u0001\u0000\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000"+
		"\u00c9\u00d2\u0005\u0019\u0000\u0000\u00ca\u00cc\u0005!\u0000\u0000\u00cb"+
		"\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd"+
		"\u00cb\u0001\u0000\u0000\u0000\u00cd\u00ce\u0001\u0000\u0000\u0000\u00ce"+
		"\u00cf\u0001\u0000\u0000\u0000\u00cf\u00d1\u0005\u001d\u0000\u0000\u00d0"+
		"\u00cb\u0001\u0000\u0000\u0000\u00d1\u00d4\u0001\u0000\u0000\u0000\u00d2"+
		"\u00d0\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000\u00d3"+
		"\u0015\u0001\u0000\u0000\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d5"+
		"\u00d6\u0005\u001a\u0000\u0000\u00d6\u00df\u0005\u001d\u0000\u0000\u00d7"+
		"\u00d9\u0005\"\u0000\u0000\u00d8\u00d7\u0001\u0000\u0000\u0000\u00d9\u00da"+
		"\u0001\u0000\u0000\u0000\u00da\u00d8\u0001\u0000\u0000\u0000\u00da\u00db"+
		"\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000\u00dc\u00de"+
		"\u0005\u001d\u0000\u0000\u00dd\u00d8\u0001\u0000\u0000\u0000\u00de\u00e1"+
		"\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000\u0000\u0000\u00df\u00e0"+
		"\u0001\u0000\u0000\u0000\u00e0\u00e2\u0001\u0000\u0000\u0000\u00e1\u00df"+
		"\u0001\u0000\u0000\u0000\u00e2\u00e3\u0005\u001a\u0000\u0000\u00e3\u0017"+
		"\u0001\u0000\u0000\u0000\u00e4\u00e5\u0003\n\u0005\u0000\u00e5\u00e7\u0005"+
		"\u0013\u0000\u0000\u00e6\u00e8\u0005\u0003\u0000\u0000\u00e7\u00e6\u0001"+
		"\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u00e7\u0001"+
		"\u0000\u0000\u0000\u00e9\u00ea\u0001\u0000\u0000\u0000\u00ea\u00eb\u0001"+
		"\u0000\u0000\u0000\u00eb\u00ec\u0005\u0001\u0000\u0000\u00ec\u00ed\u0003"+
		"\b\u0004\u0000\u00ed\u00ee\u0005\u0002\u0000\u0000\u00ee\u0104\u0001\u0000"+
		"\u0000\u0000\u00ef\u00f0\u0003\n\u0005\u0000\u00f0\u00f2\u0005\u0013\u0000"+
		"\u0000\u00f1\u00f3\u0005\u0003\u0000\u0000\u00f2\u00f1\u0001\u0000\u0000"+
		"\u0000\u00f3\u00f4\u0001\u0000\u0000\u0000\u00f4\u00f2\u0001\u0000\u0000"+
		"\u0000\u00f4\u00f5\u0001\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f7\u0005\u0001\u0000\u0000\u00f7\u00f8\u0003\u0004\u0002"+
		"\u0000\u00f8\u00f9\u0005\u0002\u0000\u0000\u00f9\u0104\u0001\u0000\u0000"+
		"\u0000\u00fa\u00fb\u0003\n\u0005\u0000\u00fb\u00fc\u0005\u0013\u0000\u0000"+
		"\u00fc\u00fd\u0003\u001c\u000e\u0000\u00fd\u0104\u0001\u0000\u0000\u0000"+
		"\u00fe\u00ff\u0003\n\u0005\u0000\u00ff\u0101\u0005\u0013\u0000\u0000\u0100"+
		"\u0102\u0003\f\u0006\u0000\u0101\u0100\u0001\u0000\u0000\u0000\u0101\u0102"+
		"\u0001\u0000\u0000\u0000\u0102\u0104\u0001\u0000\u0000\u0000\u0103\u00e4"+
		"\u0001\u0000\u0000\u0000\u0103\u00ef\u0001\u0000\u0000\u0000\u0103\u00fa"+
		"\u0001\u0000\u0000\u0000\u0103\u00fe\u0001\u0000\u0000\u0000\u0104\u0019"+
		"\u0001\u0000\u0000\u0000\u0105\u0106\u0005\u0014\u0000\u0000\u0106\u010b"+
		"\u0003\f\u0006\u0000\u0107\u0108\u0005\u0012\u0000\u0000\u0108\u010a\u0003"+
		"\f\u0006\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u010a\u010d\u0001\u0000"+
		"\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000"+
		"\u0000\u0000\u010c\u010e\u0001\u0000\u0000\u0000\u010d\u010b\u0001\u0000"+
		"\u0000\u0000\u010e\u010f\u0005\u0015\u0000\u0000\u010f\u001b\u0001\u0000"+
		"\u0000\u0000\u0110\u0111\u0005\u0016\u0000\u0000\u0111\u0116\u0003\u0018"+
		"\f\u0000\u0112\u0113\u0005\u0012\u0000\u0000\u0113\u0115\u0003\u0018\f"+
		"\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0115\u0118\u0001\u0000\u0000"+
		"\u0000\u0116\u0114\u0001\u0000\u0000\u0000\u0116\u0117\u0001\u0000\u0000"+
		"\u0000\u0117\u0119\u0001\u0000\u0000\u0000\u0118\u0116\u0001\u0000\u0000"+
		"\u0000\u0119\u011a\u0005\u0017\u0000\u0000\u011a\u001d\u0001\u0000\u0000"+
		"\u0000\u011b\u011f\u0003 \u0010\u0000\u011c\u011f\u0005\t\u0000\u0000"+
		"\u011d\u011f\u0005\n\u0000\u0000\u011e\u011b\u0001\u0000\u0000\u0000\u011e"+
		"\u011c\u0001\u0000\u0000\u0000\u011e\u011d\u0001\u0000\u0000\u0000\u011f"+
		"\u001f\u0001\u0000\u0000\u0000\u0120\u0121\u0007\u0000\u0000\u0000\u0121"+
		"!\u0001\u0000\u0000\u0000)%*,16?AEKUZ_cgmv}\u0083\u0088\u008d\u0091\u00a0"+
		"\u00a4\u00a7\u00b0\u00b4\u00b6\u00b9\u00c1\u00c6\u00cd\u00d2\u00da\u00df"+
		"\u00e9\u00f4\u0101\u0103\u010b\u0116\u011e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}