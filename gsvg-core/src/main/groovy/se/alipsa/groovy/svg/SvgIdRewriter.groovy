package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Attribute
import org.dom4j.Element
import java.util.Set
import java.util.regex.Matcher
import java.util.regex.Pattern

/** Rewrites SVG ids and the references that target them. */
@CompileStatic
class SvgIdRewriter {

  /**
   * Prefix all ids in an SVG subtree and update id references in attributes and style text.
   * This operation mutates the supplied subtree in place.
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
    Set<String> existingIds = new LinkedHashSet<>()
    collectExistingIds(element.element, existingIds)
    collectIds(element.element, prefix, replacements, existingIds)
    Map<String, String> keyframes = new LinkedHashMap<>()
    Set<String> existingKeyframes = new LinkedHashSet<>()
    collectExistingKeyframes(element.element, existingKeyframes)
    collectKeyframes(element.element, prefix, keyframes, existingKeyframes)
    rewrite(element.element, replacements, keyframes)
    element
  }

  private static void collectExistingIds(Element element, Set<String> ids) {
    String id = element.attributeValue('id')
    if (id != null) {
      ids.add(id)
    }
    element.elements().each { Element child -> collectExistingIds(child, ids) }
  }

  private static void collectIds(Element element, String prefix, Map<String, String> replacements, Set<String> existingIds) {
    String id = element.attributeValue('id')
    if (id != null && !id.startsWith(prefix)) {
      String replacement = prefix + id
      int suffix = 1
      while (existingIds.contains(replacement)) {
        replacement = "${prefix}${id}-${suffix++}"
      }
      replacements[id] = replacement
      existingIds.remove(id)
      existingIds.add(replacement)
      element.addAttribute('id', replacement)
    }
    element.elements().each { Element child -> collectIds(child, prefix, replacements, existingIds) }
  }

  private static void collectExistingKeyframes(Element element, Set<String> keyframes) {
    if (element.name == 'style') {
      Matcher matcher = (element.text =~ /@keyframes\s+([A-Za-z_][A-Za-z0-9_-]*)/)
      while (matcher.find()) {
        keyframes.add(matcher.group(1))
      }
    }
    element.elements().each { Element child -> collectExistingKeyframes(child, keyframes) }
  }

  private static void collectKeyframes(Element element, String prefix, Map<String, String> keyframes,
      Set<String> existingKeyframes) {
    if (element.name == 'style') {
      Matcher matcher = (element.text =~ /@keyframes\s+([A-Za-z_][A-Za-z0-9_-]*)/)
      while (matcher.find()) {
        String name = matcher.group(1)
        if (!name.startsWith(prefix) && !keyframes.containsKey(name)) {
          String replacement = prefix + name
          int suffix = 1
          while (existingKeyframes.contains(replacement)) {
            replacement = "${prefix}${name}-${suffix++}"
          }
          keyframes[name] = replacement
          existingKeyframes.remove(name)
          existingKeyframes.add(replacement)
        }
      }
    }
    element.elements().each { Element child -> collectKeyframes(child, prefix, keyframes, existingKeyframes) }
  }

  private static void rewrite(Element element, Map<String, String> replacements, Map<String, String> keyframes) {
    element.attributes().each { Attribute attribute ->
      String value = attribute.value
      String rewritten = rewriteAttributeReferences(attribute, value, replacements)
      if (attribute.qualifiedName == 'style') {
        rewritten = rewriteAnimationReferences(rewritten, keyframes)
      }
      if (rewritten != value) {
        attribute.value = rewritten
      }
    }
    if (element.name == 'style' && element.text != null) {
      rewriteStyleContent(element, replacements, keyframes)
    }
    element.elements().each { Element child -> rewrite(child, replacements, keyframes) }
  }

  private static String rewriteStyle(String css, Map<String, String> replacements, Map<String, String> keyframes) {
    String result = rewriteUrlReferences(css, replacements)
    result = rewriteCssSelectors(result, replacements)
    keyframes.each { String name, String replacement ->
      result = result.replaceAll("(@keyframes\\s+)${Pattern.quote(name)}(?![A-Za-z0-9_-])", '\$1' + Matcher.quoteReplacement(replacement))
    }
    rewriteAnimationReferences(result, keyframes)
  }

  private static String rewriteCssSelectors(String css, Map<String, String> replacements) {
    css.replaceAll(/([^{}]+)(\{)/) { String match, String selector, String brace ->
      String rewritten = selector
      replacements.each { String id, String replacement ->
        rewritten = rewritten.replaceAll("#${Pattern.quote(id)}(?![A-Za-z0-9_-])", Matcher.quoteReplacement("#${replacement}"))
      }
      rewritten + brace
    }
  }

  private static String rewriteAnimationReferences(String css, Map<String, String> keyframes) {
    Matcher matcher = Pattern
        .compile('(?i)(animation(?:-name)?\\s*:\\s*)([^;{}]*)')
        .matcher(css)
    StringBuffer result = new StringBuffer()
    while (matcher.find()) {
      String value = matcher.group(2)
      keyframes.each { String name, String replacement ->
        value = value.replaceAll("(?<![A-Za-z0-9_-])${Pattern.quote(name)}(?![A-Za-z0-9_-])", Matcher.quoteReplacement(replacement))
      }
      matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(1) + value))
    }
    matcher.appendTail(result)
    result.toString()
  }

  /** Rewrites contiguous style text runs without moving comments or other content nodes. */
  private static void rewriteStyleContent(Element element, Map<String, String> replacements, Map<String, String> keyframes) {
    List content = element.content()
    List originalContent = new ArrayList(content)
    List textNodes = []
    StringBuilder css = new StringBuilder()
    for (int index = 0; index <= originalContent.size(); index++) {
      Object node = index < originalContent.size() ? originalContent[index] : null
      if (node instanceof org.dom4j.Text || node instanceof org.dom4j.CDATA) {
        textNodes << node
        css.append((node as org.dom4j.CharacterData).text)
      } else if (!textNodes.isEmpty()) {
        int insertionIndex = content.indexOf(textNodes[0])
        boolean containsCdata = textNodes.any { it instanceof org.dom4j.CDATA }
        content.removeAll(textNodes)
        String rewritten = rewriteStyle(css.toString(), replacements, keyframes)
        content.add(insertionIndex, containsCdata ? org.dom4j.DocumentHelper.createCDATA(rewritten) : org.dom4j.DocumentHelper.createText(rewritten))
        textNodes = []
        css = new StringBuilder()
      }
    }
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
      result = result.replaceAll("url\\(\\s*(['\\\"]?)#${Pattern.quote(id)}\\1\\s*\\)", Matcher.quoteReplacement("url(#${replacement})"))
    }
    result
  }
}
