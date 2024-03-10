grammar DynamicQueryDsl;
@header {
package br.com.dillmann.dynamicquery.core.grammar.dsl;
}

root: expressions EOF;
expressions: expression (logicalOperator expression)*;
expression: negation | predicates | '(' expressions ')';
negation: 'not(' expressions ')';
predicates: predicate (logicalOperator predicate)*;
predicate: attributeName ':' operation ('[' parameters ']')?;
attributeName: ATRIBUTE_NAME;
operation
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
parameters: parameter (',' parameter)*;
parameter: (parameterNumericValue | parameterStringValue | parameterBooleanLiteral);
parameterStringValue: PARAMETER_STRING_VALUE;
parameterNumericValue: PARAMETER_NUMERIC_VALUE;
parameterBooleanLiteral: 'true' | 'TRUE' | 'false' | 'FALSE';
logicalOperator: '&&' | '||';

PARAMETER_NUMERIC_VALUE: ('-')?[0-9]+('.'[0-9]+)*;
PARAMETER_STRING_VALUE: '"' (~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\'))+ '"';
ATRIBUTE_NAME: [A-Za-z0-9._-]+;
WS: [ \t\r\n]+ -> skip;
