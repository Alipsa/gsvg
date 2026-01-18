# Repository Guidelines

## Project Structure & Module Organization
- Multi-module Maven build:
  - `gsvg-core/` is the core SVG model, parsing, and utilities.
  - `gsvg-export/` contains rendering/formatting/optimization utilities.
  - `gsvg-examples/` contains runnable usage examples.
- Source code lives in `**/src/main/groovy` (primary) and `**/src/main/java` (benchmarks/utilities).
- Tests live in `**/src/test/groovy` and `**/src/test/java`.
- Documentation is in `doc/`; tooling config is in `config/`.

## Build, Test, and Development Commands
- `mvn clean test` runs the full multi-module build and unit tests.
- `mvn -pl gsvg-core test` runs only the core tests (swap module name as needed).
- `mvn -P codenarc verify` runs Groovy static checks with the repo ruleset in `config/codenarc/ruleset.groovy`.
- `mvn -P spotbugs spotbugs:spotbugs` runs SpotBugs with exclusions in `config/spotbugs/exclude.xml`.
- `mvn -P dependency-check dependency-check:check` runs OWASP Dependency-Check using `config/dependency-check/suppressions.xml`.

## Coding Style & Naming Conventions
- Use 2-space indentation and idiomatic Groovy patterns (method chaining, property accessors, concise syntax).
- Use American spelling in identifiers and prose (e.g., `optimize`, `color`, `behavior`).
- Provide GroovyDoc for all classes and public methods.
- Prefer single quotes for plain strings; use double quotes only when interpolation is needed.
- Avoid wildcard imports; keep static imports after regular imports (CodeNarc enforces this).
- Class names are `UpperCamelCase`, methods/fields `lowerCamelCase`, packages lowercase.

## Testing Guidelines
- Tests use JUnit Jupiter; keep test names descriptive and end with `*Test`.
- Place tests alongside modules under `src/test/groovy` or `src/test/java`.
- Always create tests for new classes and cover new behavior and edge cases.
- Run tests whenever features are added or changed (module-scoped is fine).

## Commit & Pull Request Guidelines
- Commit history favors short, imperative summaries (e.g., “Add export options builder”), usually without prefixes.
- PRs should include a brief change summary, tests run, and any doc/example updates.
- Link relevant issues or milestones when applicable; note any behavior changes or migration impact.

## Planning & Kanban Updates
- Add plans to `kanban/in-progress` using task lists with empty checkboxes `[ ]`.
- Mark tasks as completed with `[x]` as work finishes.
- Move completed plans from `kanban/in-progress` to `kanban/done`.

## Environment Notes
- Java 17+ and Maven 3.9.9+ are required by the parent enforcer rules.
