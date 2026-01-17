def testFiles = '**/src/test/**'

ruleset {
  ruleset('rulesets/basic.xml') {
    EmptyMethod {
      doNotApplyToFileNames = testFiles
    }
  }
  ruleset('rulesets/braces.xml')
  ruleset('rulesets/imports.xml') {
    MisorderedStaticImports {
      doNotApplyToFileNames = testFiles
    }
    NoWildcardImports {
      doNotApplyToFileNames = testFiles
    }
    UnusedImport {
      doNotApplyToFileNames = testFiles
    }
  }
  ruleset('rulesets/unused.xml') {
    UnusedVariable {
      doNotApplyToFileNames = testFiles
    }
  }
}
