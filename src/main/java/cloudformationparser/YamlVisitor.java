// Generated from Yaml.g4 by ANTLR 4.10.1
package cloudformationparser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link YamlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface YamlVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link YamlParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(YamlParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(YamlParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(YamlParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#objectbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectbody(YamlParser.ObjectbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#file2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile2(YamlParser.File2Context ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(YamlParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#listitem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListitem(YamlParser.ListitemContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#mappinglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappinglist(YamlParser.MappinglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#key}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKey(YamlParser.KeyContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(YamlParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#tagArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagArray(YamlParser.TagArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(YamlParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(YamlParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#mapping}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapping(YamlParser.MappingContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#single_input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_input(YamlParser.Single_inputContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#file_input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile_input(YamlParser.File_inputContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#eval_input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEval_input(YamlParser.Eval_inputContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(YamlParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#decorators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorators(YamlParser.DecoratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#decorated}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorated(YamlParser.DecoratedContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#funcdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncdef(YamlParser.FuncdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(YamlParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#typedargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedargslist(YamlParser.TypedargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#tfpdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTfpdef(YamlParser.TfpdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#varargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarargslist(YamlParser.VarargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#vfpdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVfpdef(YamlParser.VfpdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(YamlParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_stmt(YamlParser.Simple_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSmall_stmt(YamlParser.Small_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#expr_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(YamlParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#testlist_star_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestlist_star_expr(YamlParser.Testlist_star_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#augassign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAugassign(YamlParser.AugassignContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#del_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDel_stmt(YamlParser.Del_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#pass_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPass_stmt(YamlParser.Pass_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#flow_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlow_stmt(YamlParser.Flow_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#break_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(YamlParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#continue_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_stmt(YamlParser.Continue_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(YamlParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#yield_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYield_stmt(YamlParser.Yield_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#raise_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRaise_stmt(YamlParser.Raise_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#import_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmt(YamlParser.Import_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#import_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_name(YamlParser.Import_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#import_from}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_from(YamlParser.Import_fromContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#import_as_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_as_name(YamlParser.Import_as_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#dotted_as_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_as_name(YamlParser.Dotted_as_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#import_as_names}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_as_names(YamlParser.Import_as_namesContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#dotted_as_names}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_as_names(YamlParser.Dotted_as_namesContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#dotted_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_name(YamlParser.Dotted_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#global_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_stmt(YamlParser.Global_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#nonlocal_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonlocal_stmt(YamlParser.Nonlocal_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#assert_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssert_stmt(YamlParser.Assert_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_stmt(YamlParser.Compound_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(YamlParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(YamlParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#for_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stmt(YamlParser.For_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#try_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTry_stmt(YamlParser.Try_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#with_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWith_stmt(YamlParser.With_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#with_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWith_item(YamlParser.With_itemContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#except_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExcept_clause(YamlParser.Except_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(YamlParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(YamlParser.TestContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#test_nocond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest_nocond(YamlParser.Test_nocondContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#lambdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdef(YamlParser.LambdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#lambdef_nocond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdef_nocond(YamlParser.Lambdef_nocondContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#or_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_test(YamlParser.Or_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#and_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_test(YamlParser.And_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#not_test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot_test(YamlParser.Not_testContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(YamlParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#comp_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_op(YamlParser.Comp_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#star_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStar_expr(YamlParser.Star_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(YamlParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#xor_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXor_expr(YamlParser.Xor_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#and_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expr(YamlParser.And_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#shift_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShift_expr(YamlParser.Shift_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#arith_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_expr(YamlParser.Arith_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(YamlParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(YamlParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#power}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(YamlParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(YamlParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#testlist_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestlist_comp(YamlParser.Testlist_compContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailer(YamlParser.TrailerContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#subscriptlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptlist(YamlParser.SubscriptlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#subscript}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript(YamlParser.SubscriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#sliceop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSliceop(YamlParser.SliceopContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#exprlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprlist(YamlParser.ExprlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#testlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestlist(YamlParser.TestlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#dictorsetmaker}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictorsetmaker(YamlParser.DictorsetmakerContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#classdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdef(YamlParser.ClassdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#arglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglist(YamlParser.ArglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(YamlParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#comp_iter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_iter(YamlParser.Comp_iterContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#comp_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_for(YamlParser.Comp_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#comp_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_if(YamlParser.Comp_ifContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#yield_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYield_expr(YamlParser.Yield_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#yield_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYield_arg(YamlParser.Yield_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(YamlParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(YamlParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link YamlParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(YamlParser.IntegerContext ctx);
}