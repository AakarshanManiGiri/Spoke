<p align="center">
  <img src="https://capsule-render.vercel.app/api?text=Spoke&type=waving&color=gradient&height=120"/>
</p>

A work-in-progress spoken-word programming language built in Java to evaluate whether English-like syntax can improve developer readability, ease of use, and coding efficiency.

## Motivation

Traditional Programming languages often trade in readability and ease-of-understanding for faster programming speed and overall a more experienced programmer biased programming experience. Spoke is a language that i think might bridge the gap between childish implementation like Scratch and Python which is well known as the simplest/easiest to understand.

"Spoke" is concieved to to resolve the inherent bridge between syntactic conciseness which favors experienced programmers and semantic transperency which is crucial for novice programmers to ingratiate themselves with the methodologies needed to understand programming methodologies and to apply them to other languages.

## Key Concepts

Spoke is designed around:

- **Lexer:** word-based tokenizer with keyword mapping and newline preservation — Lexer.java.  
- **Token set:** spoken keywords (let, be, number, loop, while, less/greater/than, equal/to, jump, by, end), integer literals, identifiers, NEWLINE, EOF — Token.java.  
- **AST (statements):** `Stmt` marker; `LetStmt` and `JumpStmt` updated to hold expression initializers/deltas; `LoopStmt` and `Condition` support — LetStmt.java, JumpStmt.java, LoopStmt.java, Condition.java.  
- **AST (expressions):** `Expr` hierarchy with `LiteralExpr`, `VariableExpr`, `BinaryExpr` (ADD/SUB/MUL/DIV) — Expr.java, BinaryExpr.java.  
- **Parser:** statement parsing for `let`/`jump`/`loop` (`while` ... `end loop`), expression parsing with operator precedence (plus/minus, times/divide), and expressions allowed in `let`/`jump`/conditions — Parser.java.  
- **Structured parse errors:** `ParseException` with line info for better diagnostics — ParseException.java.  
- **Interpreter:** typed runtime `Value` (int, bool), expression evaluation, binary ops with division-by-zero check, environment storage, loop execution, and snapshot support — Interpreter.java, Value.java.  
- **Runtime errors:** `RuntimeError` for clearer runtime diagnostics (undefined variables, type issues, division by zero) — RuntimeError.java.  
- **Spoken arithmetic support:** lexer and parser accept spoken operators (`plus`, `minus`, `times`, `divided`/`over`) and map them to binary operations — (see Lexer.java and Parser.java).  
- **CLI runner:** `Main` program to read a `.spoke` file, run it, and print the final environment snapshot — Main.java.  
- **Not implemented yet:** unit tests, build/CI configuration, REPL, strings/booleans as first-class parser literals (parser currently parses integer literals only), functions/local scope, richer error recovery, and an `examples/` folder.

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

