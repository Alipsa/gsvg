# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2026-01-17

### Added
- `gsvg-export` module for rendering/optimization.
- CSS selector queries (`css`, `cssFirst`) alongside XPath.
- Rendering benchmarks and expanded performance documentation.

### Changed
- Parsing now preserves raw attribute strings; numeric precision formatting only applies to programmatic attribute setting.
- Merge/cloning paths optimized for lower overhead.

## [0.9.0] - 2026-01-16

### Added
- Numeric precision control via `NumberFormatter`.
- Accessibility helpers and validation rules.
- Builder presets (effects, shapes) and template support.
- DSL-style element configuration closures.

## [0.8.0] - 2026-01-14

### Added
- Initial public release with creation, parsing, and manipulation APIs.
