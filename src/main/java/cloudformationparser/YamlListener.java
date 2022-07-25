// Generated from Yaml.g4 by ANTLR 4.10.1
package cloudformationparser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link YamlParser}.
 */
public interface YamlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link YamlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(YamlParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(YamlParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(YamlParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(YamlParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(YamlParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(YamlParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#objectbody}.
	 * @param ctx the parse tree
	 */
	void enterObjectbody(YamlParser.ObjectbodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#objectbody}.
	 * @param ctx the parse tree
	 */
	void exitObjectbody(YamlParser.ObjectbodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#file2}.
	 * @param ctx the parse tree
	 */
	void enterFile2(YamlParser.File2Context ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#file2}.
	 * @param ctx the parse tree
	 */
	void exitFile2(YamlParser.File2Context ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(YamlParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(YamlParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#listitem}.
	 * @param ctx the parse tree
	 */
	void enterListitem(YamlParser.ListitemContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#listitem}.
	 * @param ctx the parse tree
	 */
	void exitListitem(YamlParser.ListitemContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#mappinglist}.
	 * @param ctx the parse tree
	 */
	void enterMappinglist(YamlParser.MappinglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#mappinglist}.
	 * @param ctx the parse tree
	 */
	void exitMappinglist(YamlParser.MappinglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(YamlParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(YamlParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(YamlParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(YamlParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#mapping}.
	 * @param ctx the parse tree
	 */
	void enterMapping(YamlParser.MappingContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#mapping}.
	 * @param ctx the parse tree
	 */
	void exitMapping(YamlParser.MappingContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#single_input}.
	 * @param ctx the parse tree
	 */
	void enterSingle_input(YamlParser.Single_inputContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#single_input}.
	 * @param ctx the parse tree
	 */
	void exitSingle_input(YamlParser.Single_inputContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#file_input}.
	 * @param ctx the parse tree
	 */
	void enterFile_input(YamlParser.File_inputContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#file_input}.
	 * @param ctx the parse tree
	 */
	void exitFile_input(YamlParser.File_inputContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#eval_input}.
	 * @param ctx the parse tree
	 */
	void enterEval_input(YamlParser.Eval_inputContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#eval_input}.
	 * @param ctx the parse tree
	 */
	void exitEval_input(YamlParser.Eval_inputContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(YamlParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(YamlParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#decorators}.
	 * @param ctx the parse tree
	 */
	void enterDecorators(YamlParser.DecoratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#decorators}.
	 * @param ctx the parse tree
	 */
	void exitDecorators(YamlParser.DecoratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#decorated}.
	 * @param ctx the parse tree
	 */
	void enterDecorated(YamlParser.DecoratedContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#decorated}.
	 * @param ctx the parse tree
	 */
	void exitDecorated(YamlParser.DecoratedContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#funcdef}.
	 * @param ctx the parse tree
	 */
	void enterFuncdef(YamlParser.FuncdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#funcdef}.
	 * @param ctx the parse tree
	 */
	void exitFuncdef(YamlParser.FuncdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(YamlParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(YamlParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void enterTypedargslist(YamlParser.TypedargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void exitTypedargslist(YamlParser.TypedargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#tfpdef}.
	 * @param ctx the parse tree
	 */
	void enterTfpdef(YamlParser.TfpdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#tfpdef}.
	 * @param ctx the parse tree
	 */
	void exitTfpdef(YamlParser.TfpdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#varargslist}.
	 * @param ctx the parse tree
	 */
	void enterVarargslist(YamlParser.VarargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#varargslist}.
	 * @param ctx the parse tree
	 */
	void exitVarargslist(YamlParser.VarargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#vfpdef}.
	 * @param ctx the parse tree
	 */
	void enterVfpdef(YamlParser.VfpdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#vfpdef}.
	 * @param ctx the parse tree
	 */
	void exitVfpdef(YamlParser.VfpdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(YamlParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(YamlParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimple_stmt(YamlParser.Simple_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimple_stmt(YamlParser.Simple_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSmall_stmt(YamlParser.Small_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSmall_stmt(YamlParser.Small_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpr_stmt(YamlParser.Expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpr_stmt(YamlParser.Expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#testlist_star_expr}.
	 * @param ctx the parse tree
	 */
	void enterTestlist_star_expr(YamlParser.Testlist_star_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#testlist_star_expr}.
	 * @param ctx the parse tree
	 */
	void exitTestlist_star_expr(YamlParser.Testlist_star_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#augassign}.
	 * @param ctx the parse tree
	 */
	void enterAugassign(YamlParser.AugassignContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#augassign}.
	 * @param ctx the parse tree
	 */
	void exitAugassign(YamlParser.AugassignContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#del_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDel_stmt(YamlParser.Del_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#del_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDel_stmt(YamlParser.Del_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#pass_stmt}.
	 * @param ctx the parse tree
	 */
	void enterPass_stmt(YamlParser.Pass_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#pass_stmt}.
	 * @param ctx the parse tree
	 */
	void exitPass_stmt(YamlParser.Pass_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFlow_stmt(YamlParser.Flow_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFlow_stmt(YamlParser.Flow_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(YamlParser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(YamlParser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(YamlParser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(YamlParser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(YamlParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(YamlParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#yield_stmt}.
	 * @param ctx the parse tree
	 */
	void enterYield_stmt(YamlParser.Yield_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#yield_stmt}.
	 * @param ctx the parse tree
	 */
	void exitYield_stmt(YamlParser.Yield_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#raise_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRaise_stmt(YamlParser.Raise_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#raise_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRaise_stmt(YamlParser.Raise_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void enterImport_stmt(YamlParser.Import_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void exitImport_stmt(YamlParser.Import_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#import_name}.
	 * @param ctx the parse tree
	 */
	void enterImport_name(YamlParser.Import_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#import_name}.
	 * @param ctx the parse tree
	 */
	void exitImport_name(YamlParser.Import_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#import_from}.
	 * @param ctx the parse tree
	 */
	void enterImport_from(YamlParser.Import_fromContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#import_from}.
	 * @param ctx the parse tree
	 */
	void exitImport_from(YamlParser.Import_fromContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#import_as_name}.
	 * @param ctx the parse tree
	 */
	void enterImport_as_name(YamlParser.Import_as_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#import_as_name}.
	 * @param ctx the parse tree
	 */
	void exitImport_as_name(YamlParser.Import_as_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#dotted_as_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_as_name(YamlParser.Dotted_as_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#dotted_as_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_as_name(YamlParser.Dotted_as_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#import_as_names}.
	 * @param ctx the parse tree
	 */
	void enterImport_as_names(YamlParser.Import_as_namesContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#import_as_names}.
	 * @param ctx the parse tree
	 */
	void exitImport_as_names(YamlParser.Import_as_namesContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#dotted_as_names}.
	 * @param ctx the parse tree
	 */
	void enterDotted_as_names(YamlParser.Dotted_as_namesContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#dotted_as_names}.
	 * @param ctx the parse tree
	 */
	void exitDotted_as_names(YamlParser.Dotted_as_namesContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_name(YamlParser.Dotted_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_name(YamlParser.Dotted_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#global_stmt}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_stmt(YamlParser.Global_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#global_stmt}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_stmt(YamlParser.Global_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#nonlocal_stmt}.
	 * @param ctx the parse tree
	 */
	void enterNonlocal_stmt(YamlParser.Nonlocal_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#nonlocal_stmt}.
	 * @param ctx the parse tree
	 */
	void exitNonlocal_stmt(YamlParser.Nonlocal_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#assert_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssert_stmt(YamlParser.Assert_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#assert_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssert_stmt(YamlParser.Assert_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCompound_stmt(YamlParser.Compound_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCompound_stmt(YamlParser.Compound_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(YamlParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(YamlParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(YamlParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(YamlParser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(YamlParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(YamlParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#try_stmt}.
	 * @param ctx the parse tree
	 */
	void enterTry_stmt(YamlParser.Try_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#try_stmt}.
	 * @param ctx the parse tree
	 */
	void exitTry_stmt(YamlParser.Try_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#with_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWith_stmt(YamlParser.With_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#with_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWith_stmt(YamlParser.With_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#with_item}.
	 * @param ctx the parse tree
	 */
	void enterWith_item(YamlParser.With_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#with_item}.
	 * @param ctx the parse tree
	 */
	void exitWith_item(YamlParser.With_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#except_clause}.
	 * @param ctx the parse tree
	 */
	void enterExcept_clause(YamlParser.Except_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#except_clause}.
	 * @param ctx the parse tree
	 */
	void exitExcept_clause(YamlParser.Except_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(YamlParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(YamlParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(YamlParser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(YamlParser.TestContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#test_nocond}.
	 * @param ctx the parse tree
	 */
	void enterTest_nocond(YamlParser.Test_nocondContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#test_nocond}.
	 * @param ctx the parse tree
	 */
	void exitTest_nocond(YamlParser.Test_nocondContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#lambdef}.
	 * @param ctx the parse tree
	 */
	void enterLambdef(YamlParser.LambdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#lambdef}.
	 * @param ctx the parse tree
	 */
	void exitLambdef(YamlParser.LambdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#lambdef_nocond}.
	 * @param ctx the parse tree
	 */
	void enterLambdef_nocond(YamlParser.Lambdef_nocondContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#lambdef_nocond}.
	 * @param ctx the parse tree
	 */
	void exitLambdef_nocond(YamlParser.Lambdef_nocondContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#or_test}.
	 * @param ctx the parse tree
	 */
	void enterOr_test(YamlParser.Or_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#or_test}.
	 * @param ctx the parse tree
	 */
	void exitOr_test(YamlParser.Or_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#and_test}.
	 * @param ctx the parse tree
	 */
	void enterAnd_test(YamlParser.And_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#and_test}.
	 * @param ctx the parse tree
	 */
	void exitAnd_test(YamlParser.And_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#not_test}.
	 * @param ctx the parse tree
	 */
	void enterNot_test(YamlParser.Not_testContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#not_test}.
	 * @param ctx the parse tree
	 */
	void exitNot_test(YamlParser.Not_testContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(YamlParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(YamlParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#comp_op}.
	 * @param ctx the parse tree
	 */
	void enterComp_op(YamlParser.Comp_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#comp_op}.
	 * @param ctx the parse tree
	 */
	void exitComp_op(YamlParser.Comp_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#star_expr}.
	 * @param ctx the parse tree
	 */
	void enterStar_expr(YamlParser.Star_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#star_expr}.
	 * @param ctx the parse tree
	 */
	void exitStar_expr(YamlParser.Star_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(YamlParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(YamlParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#xor_expr}.
	 * @param ctx the parse tree
	 */
	void enterXor_expr(YamlParser.Xor_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#xor_expr}.
	 * @param ctx the parse tree
	 */
	void exitXor_expr(YamlParser.Xor_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#and_expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expr(YamlParser.And_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#and_expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expr(YamlParser.And_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#shift_expr}.
	 * @param ctx the parse tree
	 */
	void enterShift_expr(YamlParser.Shift_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#shift_expr}.
	 * @param ctx the parse tree
	 */
	void exitShift_expr(YamlParser.Shift_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#arith_expr}.
	 * @param ctx the parse tree
	 */
	void enterArith_expr(YamlParser.Arith_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#arith_expr}.
	 * @param ctx the parse tree
	 */
	void exitArith_expr(YamlParser.Arith_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(YamlParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(YamlParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(YamlParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(YamlParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#power}.
	 * @param ctx the parse tree
	 */
	void enterPower(YamlParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#power}.
	 * @param ctx the parse tree
	 */
	void exitPower(YamlParser.PowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(YamlParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(YamlParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#testlist_comp}.
	 * @param ctx the parse tree
	 */
	void enterTestlist_comp(YamlParser.Testlist_compContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#testlist_comp}.
	 * @param ctx the parse tree
	 */
	void exitTestlist_comp(YamlParser.Testlist_compContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterTrailer(YamlParser.TrailerContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitTrailer(YamlParser.TrailerContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#subscriptlist}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptlist(YamlParser.SubscriptlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#subscriptlist}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptlist(YamlParser.SubscriptlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#subscript}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(YamlParser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#subscript}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(YamlParser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#sliceop}.
	 * @param ctx the parse tree
	 */
	void enterSliceop(YamlParser.SliceopContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#sliceop}.
	 * @param ctx the parse tree
	 */
	void exitSliceop(YamlParser.SliceopContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void enterExprlist(YamlParser.ExprlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#exprlist}.
	 * @param ctx the parse tree
	 */
	void exitExprlist(YamlParser.ExprlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#testlist}.
	 * @param ctx the parse tree
	 */
	void enterTestlist(YamlParser.TestlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#testlist}.
	 * @param ctx the parse tree
	 */
	void exitTestlist(YamlParser.TestlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#dictorsetmaker}.
	 * @param ctx the parse tree
	 */
	void enterDictorsetmaker(YamlParser.DictorsetmakerContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#dictorsetmaker}.
	 * @param ctx the parse tree
	 */
	void exitDictorsetmaker(YamlParser.DictorsetmakerContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#classdef}.
	 * @param ctx the parse tree
	 */
	void enterClassdef(YamlParser.ClassdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#classdef}.
	 * @param ctx the parse tree
	 */
	void exitClassdef(YamlParser.ClassdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(YamlParser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(YamlParser.ArglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(YamlParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(YamlParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#comp_iter}.
	 * @param ctx the parse tree
	 */
	void enterComp_iter(YamlParser.Comp_iterContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#comp_iter}.
	 * @param ctx the parse tree
	 */
	void exitComp_iter(YamlParser.Comp_iterContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#comp_for}.
	 * @param ctx the parse tree
	 */
	void enterComp_for(YamlParser.Comp_forContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#comp_for}.
	 * @param ctx the parse tree
	 */
	void exitComp_for(YamlParser.Comp_forContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#comp_if}.
	 * @param ctx the parse tree
	 */
	void enterComp_if(YamlParser.Comp_ifContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#comp_if}.
	 * @param ctx the parse tree
	 */
	void exitComp_if(YamlParser.Comp_ifContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#yield_expr}.
	 * @param ctx the parse tree
	 */
	void enterYield_expr(YamlParser.Yield_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#yield_expr}.
	 * @param ctx the parse tree
	 */
	void exitYield_expr(YamlParser.Yield_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#yield_arg}.
	 * @param ctx the parse tree
	 */
	void enterYield_arg(YamlParser.Yield_argContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#yield_arg}.
	 * @param ctx the parse tree
	 */
	void exitYield_arg(YamlParser.Yield_argContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(YamlParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(YamlParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(YamlParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(YamlParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link YamlParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(YamlParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link YamlParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(YamlParser.IntegerContext ctx);
}