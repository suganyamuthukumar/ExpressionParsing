# ExpressionParsing

The intent of the project is to convert an Expression into a JSON structure using Expression Tree Algorithm

Example 1: Input: Expression to Convert : (((ABC=ABD))AND((GRE=AXE)))

Expected Output:

{ "exp": { "typ": "and", "sbe": [ { "exp": { "typ": "eq", "vtp": "str", "vst": "ABD", "key": "ABC" } }, { "exp": { "typ": "eq", "vtp": "str", "vst": "AXE", "key": "GRE" } } ] } }

Example 2: Improper Expression to Convert : (((AS=AX))AND((GR=AX))

Expected Output: Invalid Expression
