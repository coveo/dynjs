package org.dynjs.runtime.interp;

import org.dynjs.parser.Statement;
import org.dynjs.runtime.AbstractJavascriptFunction;
import org.dynjs.runtime.Completion;
import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.LexicalEnvironment;
import org.dynjs.runtime.Types;

public class InterpretedFunction extends AbstractJavascriptFunction {

    public InterpretedFunction(Statement body, LexicalEnvironment scope, boolean strict, String[] formalParameters) {
        super(body, scope, strict, formalParameters);
        this.setDebugContext("<anonymous>");
    }

    @Override
    public Object call(ExecutionContext context) {
        InterpretingVisitor visitor = new InterpretingVisitor();
        getBody().accept(context, visitor, isStrict());
        Completion completion = (Completion) visitor.pop();
        if (completion.type == Completion.Type.RETURN) {
            return Types.getValue(context, completion.value);
        } else {
            return Types.UNDEFINED;
        }
    }

}
