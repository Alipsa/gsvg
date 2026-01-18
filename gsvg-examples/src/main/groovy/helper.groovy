import groovy.transform.Field

@Field
File scriptDir = new File(System.getProperty("user.dir"))

File outputFile(String fileName) throws IOException {
  File targetDir = findTargetDir(scriptDir)
  File dir = new File (targetDir, "examples-output")
  dir.mkdirs()
  return new File(dir, fileName)
}

File findTargetDir(File dir) {
  if (dir == null) {
    throw new IOException("Could not find a directory containing 'target'")
  }
  if (new File(dir, 'target').exists()) {
    return new File(dir, 'target')
  }
  return findTargetDir(dir.parentFile)
}

return this