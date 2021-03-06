package org.panda_lang.panda.language.resource.syntax.expressions.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.language.architecture.statement.Scope;
import org.panda_lang.language.architecture.statement.Variable;
import org.panda_lang.language.architecture.statement.VariableAccessor;
import org.panda_lang.language.architecture.statement.VariableData;
import org.panda_lang.language.architecture.type.signature.Signature;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.language.interpreter.parser.expression.ExpressionContext;
import org.panda_lang.language.interpreter.parser.expression.ExpressionResult;
import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparserType;
import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparserWorker;
import org.panda_lang.language.interpreter.token.SourceStream;
import org.panda_lang.language.interpreter.token.TokenInfo;
import org.panda_lang.language.resource.syntax.TokenTypes;
import org.panda_lang.language.resource.syntax.keyword.Keywords;
import org.panda_lang.panda.language.interpreter.parser.PandaSourceReader;
import org.panda_lang.panda.language.resource.syntax.scope.variable.VariableDataInitializer;
import org.panda_lang.panda.language.resource.syntax.type.SignatureParser;
import org.panda_lang.panda.language.resource.syntax.type.SignatureSource;
import org.panda_lang.utilities.commons.function.Option;

public final class DeclarationExpressionSubparser implements ExpressionSubparser {

    @Override
    public ExpressionSubparserWorker createWorker(Context<?> context) {
        return new Worker().withSubparser(this);
    }

    @Override
    public ExpressionSubparserType type() {
        return ExpressionSubparserType.INDIVIDUAL;
    }

    @Override
    public int minimalRequiredLengthOfSource() {
        return 2;
    }

    @Override
    public String name() {
        return "declaration";
    }

    private static final class Worker extends AbstractExpressionSubparserWorker implements ExpressionSubparserWorker {

        private static final SignatureParser SIGNATURE_PARSER = new SignatureParser();

        @Override
        public @Nullable ExpressionResult next(ExpressionContext<?> expressionContext, TokenInfo token) {
            if (token.getType() != TokenTypes.UNKNOWN && token.getType() != TokenTypes.KEYWORD) {
                return null;
            }

            SourceStream stream = expressionContext.getSynchronizedSource().getCurrentlyAvailableSource().toStream();
            PandaSourceReader sourceReader = new PandaSourceReader(stream);

            boolean mutable = sourceReader.optionalRead(() -> sourceReader.read(Keywords.MUT)).isDefined();
            boolean nillable = sourceReader.optionalRead(() -> sourceReader.read(Keywords.NIL)).isDefined();
            boolean let = sourceReader.optionalRead(() -> sourceReader.read(Keywords.LET)).isDefined();

            Option<SignatureSource> signatureSource = let
                    ? Option.none()
                    : sourceReader.readSignature();

            if (!let && signatureSource.isEmpty()) {
                if (mutable || nillable) {
                    ExpressionResult.error("Missing variable signature", token.toSnippet());
                }

                return null;
            }

            Option<TokenInfo> name = sourceReader.read(TokenTypes.UNKNOWN);

            if (name.isEmpty()) {
                // Skip variable names read as signatures
                // throw new PandaParserFailure(context, "Missing variable name");
                return null;
            }

            Option<Signature> signature = Option.none();

            if (signatureSource.isDefined()) {
                try {
                    signature = Option.of(SIGNATURE_PARSER.parse(expressionContext, signatureSource.get(), false, null));
                } catch (PandaParserFailure failure) {
                    return ExpressionResult.error(failure.getMessage(), failure.getIndicatedSource().getSource());
                }
            }

            expressionContext.getSynchronizedSource().next(stream.getReadLength() - 1);
            Context<?> context = expressionContext.toContext();
            Scope scope = context.getScope();

            VariableDataInitializer dataInitializer = new VariableDataInitializer(context, scope);
            VariableData variableData = signature
                    .map(value -> dataInitializer.createVariableData(value, name.get(), mutable, nillable))
                    .orElseGet(() -> dataInitializer.createVariableData(name.get(), mutable, nillable));

            Variable variable = scope.createVariable(variableData);
            return ExpressionResult.of(new VariableExpression(new VariableAccessor(variable)));
        }

    }

}
