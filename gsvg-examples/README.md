# gsvg-examples

Example gallery for gsvg. Scripts live in `src/main/groovy/examples` and are compiled during the Maven build to validate syntax, but they are not executed.

## Layout

- Examples: `gsvg-examples/src/main/groovy/examples`
- Shared helpers: `gsvg-examples/src/main/groovy/examples/shared/ExampleSupport.groovy`
- Catalog: `gsvg-examples/src/main/resources/examples/catalog.md`
- Output location (when run manually or via Maven): `gsvg-examples/target/examples-output`

## Running an Example

Examples are standalone Groovy scripts that use `@Grab` so they can be run directly:

```bash
cd gsvg-examples

groovy src/main/groovy/examples/10-practical-use-cases/Example01ExportFormats.groovy
```

## Running All Examples via Maven

Use the `run-examples` profile to execute all scripts in one go. Outputs are written to `target/examples-output` and removed with `mvn clean`.

```bash
mvn -pl gsvg-examples -am -Prun-examples verify
```

## Build Notes

The Maven build compiles scripts to validate syntax. Grape is disabled for compilation via `config/gmavenplus-config.groovy` to avoid network access; it is enabled by default when you run scripts directly with `groovy`.
