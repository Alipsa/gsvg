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
    java.util.Set<String> existingIds = new LinkedHashSet<>()
    collectExistingIds(element.element, existingIds)
    collectIds(element.element, prefix, replacements, existingIds)
    Map<String, String> keyframes = new LinkedHashMap<>()
    collectKeyframes(element.element, prefix, keyframes)
    rewrite(element.element, replacements, keyframes)
    element
  }

  private static void collectExistingIds(Element element, java.util.Set<String> ids) {
    String id = element.attributeValue('id')
    if (id != null) {
      ids.add(id)
    }
    element.elements().each { Element child -> collectExistingIds(child, ids) }
  }

  private static void collectIds(Element element, String prefix, Map<String, String> replacements, java.util.Set<String> existingIds) {
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

  private static void collectKeyframes(Element element, String prefix, Map<String, String> keyframes) {
    if (element.name == 'style') {
      java.util.regex.Matcher matcher = (element.text =~ /@keyframes\s+([A-Za-z_][A-Za-z0-9_-]*)/)
      while (matcher.find()) {
        String name = matcher.group(1)
        keyframes[name] = prefix + name
      }
    }
    element.elements().each { Element child -> collectKeyframes(child, prefix, keyframes) }
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
      rewriteStyleContent(element, rewriteStyle(element.text, replacements, keyframes))
    }
    element.elements().each { Element child -> rewrite(child, replacements, keyframes) }
  }

  private static String rewriteStyle(String css, Map<String, String> replacements, Map<String, String> keyframes) {
    String result = rewriteUrlReferences(css, replacements)
    result = rewriteCssSelectors(result, replacements)
    keyframes.each { String name, String replacement ->
      result = result.replaceAll("(@keyframes\\s+)${java.util.regex.Pattern.quote(name)}(?![A-Za-z0-9_-])", "\$1${replacement}")
    }
    rewriteAnimationReferences(result, keyframes)
  }

  private static String rewriteCssSelectors(String css, Map<String, String> replacements) {
    css.replaceAll(/([^{}]+)(\{)/) { String match, String selector, String brace ->
      String rewritten = selector
      replacements.each { String id, String replacement ->
        rewritten = rewritten.replaceAll("#${java.util.regex.Pattern.quote(id)}(?![A-Za-z0-9_-])", "#${replacement}")
      }
      rewritten + brace
    }
  }

  private static String rewriteAnimationReferences(String css, Map<String, String> keyframes) {
    String result = css
    keyframes.each { String name, String replacement ->
      result = result.replaceAll("(?i)(animation(?:-name)?\\s*:\\s*[^;{}]*)\\b${java.util.regex.Pattern.quote(name)}\\b", "\$1${replacement}")
    }
    result
  }

  /** Updates a single direct text or CDATA node without converting CDATA to escaped text. */
  private static void rewriteStyleContent(Element element, String rewritten) {
    List content = element.content()
    List textNodes = content.findAll { it instanceof org.dom4j.Text || it instanceof org.dom4j.CDATA }
    if (textNodes.size() == 1) {
      int index = content.indexOf(textNodes[0])
      if (textNodes[0] instanceof org.dom4j.CDATA) {
        content[index] = org.dom4j.DocumentHelper.createCDATA(rewritten)
      } else {
        (textNodes[0] as org.dom4j.Text).setText(rewritten)
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
      result = result.replaceAll("url\\(\\s*(['\\\"]?)#${java.util.regex.Pattern.quote(id)}\\1\\s*\\)", "url(#${replacement})")
    }
    result
  }
}
