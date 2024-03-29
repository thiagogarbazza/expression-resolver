package com.github.thiagogarbazza.expressionresolver.interpreter.compile;

import com.github.thiagogarbazza.expressionresolver.exception.SyntaxExpressionException;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import static com.github.thiagogarbazza.expressionresolver.util.PropertieUtil.messageProperty;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class LexerErrorListener extends BaseErrorListener {

  private static final LexerErrorListener INSTANCE = new LexerErrorListener();

  @Override
  public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line,
    final int charPositionInLine, final String message, final RecognitionException e) {
    throw new SyntaxExpressionException(format(messageProperty("validation.expression.syntax-error"), line, charPositionInLine, message), e);
  }

  public static LexerErrorListener getLexerErrorListener() {
    return INSTANCE;
  }
}
