grammar DynamicQueryGrammar;
@header {
package br.com.dillmann.dynamicquery.grammar.dsl;
}

root: expressions EOF;
expressions: expression (logicalOperator expression)*;
expression: negation | predicates | '(' expressions ')';
negation: 'not(' expressions ')';
predicates: predicate (logicalOperator predicate)*;
predicate: predicateType '(' parameters ')';
predicateType
    : 'equals'
    | 'notEquals'
    | 'equalsIgnoreCase'
    | 'notEqualsIgnoreCase'
    | 'lessThan'
    | 'lessOrEquals'
    | 'greaterThan'
    | 'greaterOrEquals'
    | 'between'
    | 'notBetween'
    | 'in'
    | 'notIn'
    | 'like'
    | 'notLike'
    | 'likeIgnoreCase'
    | 'notLikeIgnoreCase'
    | 'isNull'
    | 'isNotNull'
    | 'isEmpty'
    | 'isNotEmpty';
operation: operationType '(' parameters* ')';
operationType
    : 'abs'
    | 'avg'
    | 'ceiling'
    | 'coalesce'
    | 'concat'
    | 'count'
    | 'countDistinct'
    | 'currentDate'
    | 'currentTime'
    | 'currentTimestamp'
    | 'diff'
    | 'exp'
    | 'floor'
    | 'length'
    | 'localDate'
    | 'localDateTime'
    | 'localTime'
    | 'locate'
    | 'lower'
    | 'max'
    | 'min'
    | 'mod'
    | 'neg'
    | 'power'
    | 'prod'
    | 'quot'
    | 'size'
    | 'sqrt'
    | 'substring'
    | 'sum'
    | 'toBigDecimal'
    | 'toBigInteger'
    | 'toDouble'
    | 'toFloat'
    | 'toInteger'
    | 'toLong'
    | 'trim'
    | 'upper';
parameters: parameter (parameterSeparator parameter)*;
parameter: attributeName | numericLiteral | stringLiteral | booleanLiteral | nullLiteral | operation;
parameterSeparator: (WS)? ',' (WS)?;
attributeName: ATRIBUTE_NAME;
stringLiteral: STRING_LITERAL;
numericLiteral: NUMERIC_LITERAL;
booleanLiteral: 'true' | 'false';
nullLiteral: 'null';
logicalOperator: '&&' | '||';

NUMERIC_LITERAL: ('-')?[0-9]+('.'[0-9]+)*;
STRING_LITERAL: '"' (~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\'))+ '"';
ATRIBUTE_NAME: [A-Za-z0-9._-]+;
WS: [ \t\r\n]+ -> skip;
