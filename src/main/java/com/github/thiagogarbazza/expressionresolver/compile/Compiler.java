package com.github.thiagogarbazza.expressionresolver.compile;

import com.github.thiagogarbazza.expressionresolver.domain.Expression;
import com.github.thiagogarbazza.expressionresolver.parser.ExpressionLexer;
import com.github.thiagogarbazza.expressionresolver.parser.ExpressionParser;
import org.antlr.v4.runtime.tree.ParseTree;

import static com.github.thiagogarbazza.expressionresolver.compile.ParseTreeBuilder.getParseTreeBuilder;

public final class Compiler {

  private final LexerBuilder lexerBuilder = new LexerBuilder();
  private final ParserBuilder parserBuilder = new ParserBuilder();

  public ParseTree compile(Expression expression) {
    ExpressionInputStream in = new ExpressionInputStream(expression);
    ExpressionLexer lexer = lexerBuilder.build(in);
    ExpressionParser parser = parserBuilder.build(lexer);

    return getParseTreeBuilder().build(parser);
  }
}
