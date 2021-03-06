/*
 * $Id: AbstractCompoundStatement.java,v 1.8 2005-07-19 08:40:03 mhw Exp $
 */

package org.codehaus.tagalog.script;

import java.util.Map;

import org.codehaus.tagalog.el.Expression;

/**
 * Abstract base class for {@link Statement}s that contain
 * {@link Expression}s as their body. The contained <code>Expression</code>
 * may also be a {@link Statement} wrapped in a {@link StatementExpression}.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision: 1.8 $
 */
public abstract class AbstractCompoundStatement
    extends AbstractStatement
    implements Statement
{
    private final Expression body;

    /**
     * @param body Body of the compound statement, or <code>null</code> if
     *             there is no body.
     */
    protected AbstractCompoundStatement(Expression body) {
        this.body = body;
    }

    /**
     * Evalutate the {@link Expression} that is the body of this statement.
     * Any return value from the expression is discarded.
     */
    public void execute(Map context) throws ScriptException {
        evaluateBody(context);
    }

    /**
     * Evaluate the {@link Expression} that is the body of this statement
     * against a given context, returning the evaluation result.
     *
     * @param context Evaluation context.
     * @return Evaluation result. <code>null</code> if there is no body,
     *         or evaluation of the body returned <code>null</code>
     * @throws ScriptException
     */
    protected final Object evaluateBody(Map context) throws ScriptException {
        if (body == null)
            return null;
        return evaluate("body", body, context);
    }
}
