<p align="center">
  <img src="https://capsule-render.vercel.app/api?text=Spoke&type=waving&color=gradient&height=120"/>
</p>

A work-in-progress spoken-word programming language built in Java to evaluate whether English-like syntax can improve developer readability, ease of use, and coding efficiency.

## Motivation

Traditional Programming languages often trade in readability and ease-of-understanding for faster programming speed and overall a more experienced programmer biased programming experience. Spoke is a language that i think might bridge the gap between childish implementation like Scratch and Python which is well known as the simplest/easiest to understand.

"Spoke" is concieved to to resolve the inherent bridge between syntactic conciseness which favors experienced programmers and semantic transperency which is crucial for novice programmers to ingratiate themselves with the methodologies needed to understand programming methodologies and to apply them to other languages.

## Key Concepts

Spoke is designed around:

- **Lexer**: word tokenizer with keyword mapping and explicit newline tokens (Lexer.java).  
- **Tokens**: spoken keywords, integer literals, identifiers, `NEWLINE`, `EOF` (Token.java).  
- **AST (statements)**: `Stmt` marker; `Let` and `Jump` accept expressions; `Loop` and `Condition` supported (LetStmt.java, JumpStmt.java, LoopStmt.java, Condition.java).  
- **AST (expressions)**: `Literal`, `Variable`, `Binary` (ADD, SUB, MUL, DIV) (Expr.java, BinaryExpr.java).  
- **Parser**: statements and expression parsing with precedence; expressions usable in `let`, `jump`, and conditions (Parser.java).  
- **Parse errors**: `ParseException` with line info (ParseException.java).  
- **Interpreter**: typed `Value` (int, bool), expression evaluation, division-by-zero check, global environment, snapshot (Interpreter.java, Value.java).  
- **Runtime errors**: `RuntimeError` for undefined variables, type issues, division by zero (RuntimeError.java).  
- **Spoken arithmetic**: supports `plus`, `minus`, `times`, `divided`/`over` (lexer + parser).  
- **CLI runner**: `Main` reads a `.spoke` file, runs it, prints environment snapshot (Main.java).  
- **Not implemented**: unit tests, build/CI, REPL, string/bool literal parsing, functions/local scope, richer error recovery, `examples/` folder.

---

## Example

```spoke
let x be number 10

loop while x less than number 100
    jump x by number 10
end loop
```
## To Do:
- **Expansion on Language Capabilites** - Specifically to include basic operations of harder topics.
- **Providing Errors that provide better context**
- **Step by Step Mode** - To provide a better point of view on how computers think.

