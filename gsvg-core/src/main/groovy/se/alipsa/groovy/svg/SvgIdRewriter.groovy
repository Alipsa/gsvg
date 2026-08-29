package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Attribute
import org.dom4j.Element

/** Rewrites SVG ids and the references that target them. */
@CompileStatic
class SvgIdRewriter {

  /**
   * Prefix all ids in an SVG subtree and update id references in attributes and style text.
   *
   * @param element root of the owned SVG subtree
   * @param prefix namespace prefix
   * @return the supplied element
   */
  static <T extends SvgElement> T prefixIds(T element, String prefix) {
    if (prefix == null || prefix.isEmpty()) {
      return element
    }
    Map<String, String> replacements = new LinkedHashMap<>()
    collectIds(element.element, prefix, replacements)
    rewrite(element.element, replacements, prefix)
    element
  }

  private static void collectIds(Element element, String prefix, Map<String, String> replacements) {
    String id = element.attributeValue('id')
    if (id != null && !id.startsWith(prefix)) {
      replacements[id] = prefix + id
      element.addAttribute('id', prefix + id)
    }
    element.elements().each { Element child -> collectIds(child, prefix, replacements) }
  }

  private static void rewrite(Element element, Map<String, String> replacements, String prefix) {
    element.attributes().each { Attribute attribute ->
      String value = attribute.value
      String rewritten = rewriteAttributeReferences(attribute, value, replacements)
      if (rewritten != value) {
        attribute.value = rewritten
      }
    }
    if (element.name == 'style' && element.text != null) {
      element.text = rewriteStyle(element.text, replacements, prefix)
    }
    element.elements().each { Element child -> rewrite(child, replacements, prefix) }
  }

  private static String rewriteStyle(String css, Map<String, String> replacements, String prefix) {
    Map<String, String> keyframes = [:]
    java.util.regex.Matcher matcher = (css =~ /@keyframes\s+([A-Za-z_][A-Za-z0-9_-]*)/)
    while (matcher.find()) {
      String name = matcher.group(1)
      keyframes[name] = prefix + name
    }
    String result = rewriteUrlReferences(css, replacements)
    replacements.each { String id, String replacement ->
      // ID selectors occur before a rule's opening brace. Rewriting only this
      // portion avoids treating CSS colors such as '#abc' as fragment references.
      result = result.replaceAll("(?m)(^|[\\x7D])([^\\x7B\\x7D]*)#${java.util.regex.Pattern.quote(id)}(?![A-Za-z0-9_-])(?=[^\\x7B\\x7D]*\\x7B)", "${'$'}1${'$'}2#${replacement}")
    }
    keyframes.each { String name, String replacement ->
      result = result.replaceAll("(@keyframes\\s+)${java.util.regex.Pattern.quote(name)}(?![A-Za-z0-9_-])", "\$1${replacement}")
          .replaceAll("(animation-name\\s*:\\s*)${java.util.regex.Pattern.quote(name)}(?![A-Za-z0-9_-])", "\$1${replacement}")
          .replaceAll("(animation\\s*:\\s*)${java.util.regex.Pattern.quote(name)}(?![A-Za-z0-9_-])", "\$1${replacement}")
    }
    result
  }

  private static String rewriteAttributeReferences(Attribute attribute, String value, Map<String, String> replacements) {
    String result = rewriteUrlReferences(value, replacements)
    if (attribute.qualifiedName == 'href' || attribute.qualifiedName == 'xlink:href') {
      replacements.each { String id, String replacement ->
        if (result == "#${id}") {
          result = "#${replacement}"
        }
      }
    }
    result
  }

  private static String rewriteUrlReferences(String value, Map<String, String> replacements) {
    String result = value
    replacements.keySet().sort { String left, String right -> right.length() <=> left.length() }.each { String id ->
      String replacement = replacements[id]
      result = result.replaceAll("url\\(\\s*(['\\\"]?)#${java.util.regex.Pattern.quote(id)}\\1\\s*\\)", "url(#${replacement})")
    }
    result
  }
}
