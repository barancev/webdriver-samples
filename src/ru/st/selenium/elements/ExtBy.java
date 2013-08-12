package ru.st.selenium.elements;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

public class ExtBy extends By {

  private final String selector;

  public static By sizzle(final String selector) {
    return new ExtBy(selector);
  }
  
  public ExtBy(final String selector) {
    this.selector = selector;
  }

  /*
   * Sizzle CSS Selector Engine - v1.0
   *  Copyright 2009, The Dojo Foundation
   *  Released under the MIT, BSD, and GPL Licenses.
   *  More information: http://sizzlejs.com/
   *  
   *  Compiled using the Google closure compiler
   */
  private final String sizzleJavascript = "(function(){function w(a,b,c,d,f,e){for(var f=0,g=d.length;f<g;f++){var h=d[f];if(h){for(var h=h[a],i=!1;h;){if(h.sizcache===c){i=d[h.sizset];break}if(h.nodeType===1&&!e)h.sizcache=c,h.sizset=f;if(h.nodeName.toLowerCase()===b){i=h;break}h=h[a]}d[f]=i}}}function x(a,b,c,d,f,e){for(var f=0,g=d.length;f<g;f++){var h=d[f];if(h){for(var h=h[a],i=!1;h;){if(h.sizcache===c){i=d[h.sizset];break}if(h.nodeType===1){if(!e)h.sizcache=c,h.sizset=f;if(typeof b!==\"string\"){if(h===b){i=!0;break}}else if(j.filter(b,[h]).length>0){i=h;break}}h=h[a]}d[f]=i}}}var u=/((?:\\((?:\\([^()]+\\)|[^()]+)+\\)|\\[(?:\\[[^\\[\\]]*\\]|['\"][^'\"]*['\"]|[^\\[\\]'\"]+)+\\]|\\\\.|[^ >+~,(\\[\\\\]+)+|[>+~])(\\s*,\\s*)?((?:.|\\r|\\n)*)/g,v=0,y=Object.prototype.toString,s=!1,z=!0;[0,0].sort(function(){z=!1;return 0});var j=function(a,b,c,d){var c=c||[],f=b=b||document;if(b.nodeType!==1&&b.nodeType!==9)return[];if(!a||typeof a!==\"string\")return c;var e=[],g,h,k,m,o=!0,r=j.isXML(b),p=a,l;do if(u.exec(\"\"),g=u.exec(p))if(p=g[3],e.push(g[1]),g[2]){m=g[3];break}while(g);if(e.length>1&&B.exec(a))if(e.length===2&&i.relative[e[0]])h=A(e[0]+e[1],b);else for(h=i.relative[e[0]]?[b]:j(e.shift(),b);e.length;)a=e.shift(),i.relative[a]&&(a+=e.shift()),h=A(a,h);else if(!d&&e.length>1&&b.nodeType===9&&!r&&i.match.ID.test(e[0])&&!i.match.ID.test(e[e.length-1])&&(g=j.find(e.shift(),b,r),b=g.expr?j.filter(g.expr,g.set)[0]:g.set[0]),b){g=d?{expr:e.pop(),set:n(d)}:j.find(e.pop(),e.length===1&&(e[0]===\"~\"||e[0]===\"+\")&&b.parentNode?b.parentNode:b,r);h=g.expr?j.filter(g.expr,g.set):g.set;for(e.length>0?k=n(h):o=!1;e.length;)g=l=e.pop(),i.relative[l]?g=e.pop():l=\"\",g==null&&(g=b),i.relative[l](k,g,r)}else k=[];k||(k=h);k||j.error(l||a);if(y.call(k)===\"[object Array]\")if(o)if(b&&b.nodeType===1)for(a=0;k[a]!=null;a++)k[a]&&(k[a]===!0||k[a].nodeType===1&&j.contains(b,k[a]))&&c.push(h[a]);else for(a=0;k[a]!=null;a++)k[a]&&k[a].nodeType===1&&c.push(h[a]);else c.push.apply(c,k);else n(k,c);m&&(j(m,f,c,d),j.uniqueSort(c));return c};j.uniqueSort=function(a){if(t&&(s=z,a.sort(t),s))for(var b=1;b<a.length;b++)a[b]===a[b-1]&&a.splice(b--,1);return a};j.matches=function(a,b){return j(a,null,null,b)};j.find=function(a,b,c){var d;if(!a)return[];for(var f=0,e=i.order.length;f<e;f++){var g=i.order[f],h;if(h=i.leftMatch[g].exec(a)){var j=h[1];h.splice(1,1);if(j.substr(j.length-1)!==\"\\\\\"&&(h[1]=(h[1]||\"\").replace(/\\\\/g,\"\"),d=i.find[g](h,b,c),d!=null)){a=a.replace(i.match[g],\"\");break}}}d||(d=b.getElementsByTagName(\"*\"));return{set:d,expr:a}};j.filter=function(a,b,c,d){for(var f=a,e=[],g=b,h,k,m=b&&b[0]&&j.isXML(b[0]);a&&b.length;){for(var o in i.filter)if((h=i.leftMatch[o].exec(a))!=null&&h[2]){var r=i.filter[o],p,l;l=h[1];k=!1;h.splice(1,1);if(l.substr(l.length-1)!==\"\\\\\"){g===e&&(e=[]);if(i.preFilter[o])if(h=i.preFilter[o](h,g,c,e,d,m)){if(h===!0)continue}else k=p=!0;if(h)for(var n=0;(l=g[n])!=null;n++)if(l){p=r(l,h,n,g);var q=d^!!p;c&&p!=null?q?k=!0:g[n]=!1:q&&(e.push(l),k=!0)}if(p!==void 0){c||(g=e);a=a.replace(i.match[o],\"\");if(!k)return[];break}}}if(a===f)if(k==null)j.error(a);else break;f=a}return g};j.error=function(a){throw\"Syntax error, unrecognized expression: \"+a;};var i=j.selectors={order:[\"ID\",\"NAME\",\"TAG\"],match:{ID:/#((?:[\\w\\u00c0-\\uFFFF\\-]|\\\\.)+)/,CLASS:/\\.((?:[\\w\\u00c0-\\uFFFF\\-]|\\\\.)+)/,NAME:/\\[name=['\"]*((?:[\\w\\u00c0-\\uFFFF\\-]|\\\\.)+)['\"]*\\]/,ATTR:/\\[\\s*((?:[\\w\\u00c0-\\uFFFF\\-]|\\\\.)+)\\s*(?:(\\S?=)\\s*(['\"]*)(.*?)\\3|)\\s*\\]/,TAG:/^((?:[\\w\\u00c0-\\uFFFF\\*\\-]|\\\\.)+)/,CHILD:/:(only|nth|last|first)-child(?:\\((even|odd|[\\dn+\\-]*)\\))?/,POS:/:(nth|eq|gt|lt|first|last|even|odd)(?:\\((\\d*)\\))?(?=[^\\-]|$)/,PSEUDO:/:((?:[\\w\\u00c0-\\uFFFF\\-]|\\\\.)+)(?:\\((['\"]?)((?:\\([^\\)]+\\)|[^\\(\\)]*)+)\\2\\))?/},leftMatch:{},attrMap:{\"class\":\"className\",\"for\":\"htmlFor\"},attrHandle:{href:function(a){return a.getAttribute(\"href\")}},relative:{\"+\":function(a,b){var c=typeof b===\"string\",d=c&&!/\\W/.test(b),c=c&&!d;d&&(b=b.toLowerCase());for(var d=0,f=a.length,e;d<f;d++)if(e=a[d]){for(;(e=e.previousSibling)&&e.nodeType!==1;);a[d]=c||e&&e.nodeName.toLowerCase()===b?e||!1:e===b}c&&j.filter(b,a,!0)},\">\":function(a,b){var c=typeof b===\"string\",d,f=0,e=a.length;if(c&&!/\\W/.test(b))for(b=b.toLowerCase();f<e;f++){if(d=a[f])c=d.parentNode,a[f]=c.nodeName.toLowerCase()===b?c:!1}else{for(;f<e;f++)(d=a[f])&&(a[f]=c?d.parentNode:d.parentNode===b);c&&j.filter(b,a,!0)}},\"\":function(a,b,c){var d=v++,f=x,e;typeof b===\"string\"&&!/\\W/.test(b)&&(e=b=b.toLowerCase(),f=w);f(\"parentNode\",b,d,a,e,c)},\"~\":function(a,b,c){var d=v++,f=x,e;typeof b===\"string\"&&!/\\W/.test(b)&&(e=b=b.toLowerCase(),f=w);f(\"previousSibling\",b,d,a,e,c)}},find:{ID:function(a,b,c){if(typeof b.getElementById!==\"undefined\"&&!c)return(a=b.getElementById(a[1]))&&a.parentNode?[a]:[]},NAME:function(a,b){if(typeof b.getElementsByName!==\"undefined\"){for(var c=[],d=b.getElementsByName(a[1]),f=0,e=d.length;f<e;f++)d[f].getAttribute(\"name\")===a[1]&&c.push(d[f]);return c.length===0?null:c}},TAG:function(a,b){return b.getElementsByTagName(a[1])}},preFilter:{CLASS:function(a,b,c,d,f,e){a=\" \"+a[1].replace(/\\\\/g,\"\")+\" \";if(e)return a;for(var e=0,g;(g=b[e])!=null;e++)g&&(f^(g.className&&(\" \"+g.className+\" \").replace(/[\\t\\n]/g,\" \").indexOf(a)>=0)?c||d.push(g):c&&(b[e]=!1));return!1},ID:function(a){return a[1].replace(/\\\\/g,\"\")},TAG:function(a){return a[1].toLowerCase()},CHILD:function(a){if(a[1]===\"nth\"){var b=/(-?)(\\d*)n((?:\\+|-)?\\d*)/.exec(a[2]===\"even\"&&\"2n\"||a[2]===\"odd\"&&\"2n+1\"||!/\\D/.test(a[2])&&\"0n+\"+a[2]||a[2]);a[2]=b[1]+(b[2]||1)-0;a[3]=b[3]-0}a[0]=v++;return a},ATTR:function(a,b,c,d,f,e){b=a[1].replace(/\\\\/g,\"\");!e&&i.attrMap[b]&&(a[1]=i.attrMap[b]);a[2]===\"~=\"&&(a[4]=\" \"+a[4]+\" \");return a},PSEUDO:function(a,b,c,d,f){if(a[1]===\"not\")if((u.exec(a[3])||\"\").length>1||/^\\w/.test(a[3]))a[3]=j(a[3],null,null,b);else return a=j.filter(a[3],b,c,1^f),c||d.push.apply(d,a),!1;else if(i.match.POS.test(a[0])||i.match.CHILD.test(a[0]))return!0;return a},POS:function(a){a.unshift(!0);return a}},filters:{enabled:function(a){return a.disabled===!1&&a.type!==\"hidden\"},disabled:function(a){return a.disabled===!0},checked:function(a){return a.checked===!0},selected:function(a){return a.selected===!0},parent:function(a){return!!a.firstChild},empty:function(a){return!a.firstChild},has:function(a,b,c){return!!j(c[3],a).length},header:function(a){return/h\\d/i.test(a.nodeName)},text:function(a){return\"text\"===a.type},radio:function(a){return\"radio\"===a.type},checkbox:function(a){return\"checkbox\"===a.type},file:function(a){return\"file\"===a.type},password:function(a){return\"password\"===a.type},submit:function(a){return\"submit\"===a.type},image:function(a){return\"image\"===a.type},reset:function(a){return\"reset\"===a.type},button:function(a){return\"button\"===a.type||a.nodeName.toLowerCase()===\"button\"},input:function(a){return/input|select|textarea|button/i.test(a.nodeName)}},setFilters:{first:function(a,b){return b===0},last:function(a,b,c,d){return b===d.length-1},even:function(a,b){return b%2===0},odd:function(a,b){return b%2===1},lt:function(a,b,c){return b<c[3]-0},gt:function(a,b,c){return b>c[3]-0},nth:function(a,b,c){return c[3]-0===b},eq:function(a,b,c){return c[3]-0===b}},filter:{PSEUDO:function(a,b,c,d){var f=b[1],e=i.filters[f];if(e)return e(a,c,b,d);else if(f===\"contains\")return(a.textContent||a.innerText||j.getText([a])||\"\").indexOf(b[3])>=0;else if(f===\"not\"){b=b[3];c=0;for(d=b.length;c<d;c++)if(b[c]===a)return!1;return!0}else j.error(\"Syntax error, unrecognized expression: \"+f)},CHILD:function(a,b){var c=b[1],d=a;switch(c){case \"only\":case \"first\":for(;d=d.previousSibling;)if(d.nodeType===1)return!1;if(c===\"first\")return!0;d=a;case \"last\":for(;d=d.nextSibling;)if(d.nodeType===1)return!1;return!0;case \"nth\":var c=b[2],f=b[3];if(c===1&&f===0)return!0;var e=b[0],g=a.parentNode;if(g&&(g.sizcache!==e||!a.nodeIndex)){for(var h=0,d=g.firstChild;d;d=d.nextSibling)if(d.nodeType===1)d.nodeIndex=++h;g.sizcache=e}d=a.nodeIndex-f;return c===0?d===0:d%c===0&&d/c>=0}},ID:function(a,b){return a.nodeType===1&&a.getAttribute(\"id\")===b},TAG:function(a,b){return b===\"*\"&&a.nodeType===1||a.nodeName.toLowerCase()===b},CLASS:function(a,b){return(\" \"+(a.className||a.getAttribute(\"class\"))+\" \").indexOf(b)>-1},ATTR:function(a,b){var c=b[1],c=i.attrHandle[c]?i.attrHandle[c](a):a[c]!=null?a[c]:a.getAttribute(c),d=c+\"\",f=b[2],e=b[4];return c==null?f===\"!=\":f===\"=\"?d===e:f===\"*=\"?d.indexOf(e)>=0:f===\"~=\"?(\" \"+d+\" \").indexOf(e)>=0:!e?d&&c!==!1:f===\"!=\"?d!==e:f===\"^=\"?d.indexOf(e)===0:f===\"$=\"?d.substr(d.length-e.length)===e:f===\"|=\"?d===e||d.substr(0,e.length+1)===e+\"-\":!1},POS:function(a,b,c,d){var f=i.setFilters[b[2]];if(f)return f(a,c,b,d)}}},B=i.match.POS,C=function(a,b){return\"\\\\\"+(b-0+1)},m;for(m in i.match)i.match[m]=RegExp(i.match[m].source+/(?![^\\[]*\\])(?![^\\(]*\\))/.source),i.leftMatch[m]=RegExp(/(^(?:.|\\r|\\n)*?)/.source+i.match[m].source.replace(/\\\\(\\d+)/g,C));var n=function(a,b){a=Array.prototype.slice.call(a,0);if(b)return b.push.apply(b,a),b;return a};try{Array.prototype.slice.call(document.documentElement.childNodes,0)}catch(D){n=function(a,b){var c=b||[],d=0;if(y.call(a)===\"[object Array]\")Array.prototype.push.apply(c,a);else if(typeof a.length===\"number\")for(var f=a.length;d<f;d++)c.push(a[d]);else for(;a[d];d++)c.push(a[d]);return c}}var t,q;document.documentElement.compareDocumentPosition?t=function(a,b){if(a===b)return s=!0,0;if(!a.compareDocumentPosition||!b.compareDocumentPosition)return a.compareDocumentPosition?-1:1;return a.compareDocumentPosition(b)&4?-1:1}:(t=function(a,b){var c=[],d=[],f=a.parentNode,e=b.parentNode,g=f;if(a===b)return s=!0,0;else if(f===e)return q(a,b);else if(f){if(!e)return 1}else return-1;for(;g;)c.unshift(g),g=g.parentNode;for(g=e;g;)d.unshift(g),g=g.parentNode;f=c.length;e=d.length;for(g=0;g<f&&g<e;g++)if(c[g]!==d[g])return q(c[g],d[g]);return g===f?q(a,d[g],-1):q(c[g],b,1)},q=function(a,b,c){if(a===b)return c;for(a=a.nextSibling;a;){if(a===b)return-1;a=a.nextSibling}return 1});j.getText=function(a){for(var b=\"\",c,d=0;a[d];d++)c=a[d],c.nodeType===3||c.nodeType===4?b+=c.nodeValue:c.nodeType!==8&&(b+=j.getText(c.childNodes));return b};(function(){var a=document.createElement(\"div\"),b=\"script\"+(new Date).getTime();a.innerHTML=\"<a name='\"+b+\"'/>\";var c=document.documentElement;c.insertBefore(a,c.firstChild);if(document.getElementById(b))i.find.ID=function(a,b,c){if(typeof b.getElementById!==\"undefined\"&&!c)return(b=b.getElementById(a[1]))?b.id===a[1]||typeof b.getAttributeNode!==\"undefined\"&&b.getAttributeNode(\"id\").nodeValue===a[1]?[b]:void 0:[]},i.filter.ID=function(a,b){var c=typeof a.getAttributeNode!==\"undefined\"&&a.getAttributeNode(\"id\");return a.nodeType===1&&c&&c.nodeValue===b};c.removeChild(a);c=a=null})();(function(){var a=document.createElement(\"div\");a.appendChild(document.createComment(\"\"));if(a.getElementsByTagName(\"*\").length>0)i.find.TAG=function(a,c){var d=c.getElementsByTagName(a[1]);if(a[1]===\"*\"){for(var f=[],e=0;d[e];e++)d[e].nodeType===1&&f.push(d[e]);d=f}return d};a.innerHTML=\"<a href='#'></a>\";if(a.firstChild&&typeof a.firstChild.getAttribute!==\"undefined\"&&a.firstChild.getAttribute(\"href\")!==\"#\")i.attrHandle.href=function(a){return a.getAttribute(\"href\",2)};a=null})();document.querySelectorAll&&function(){var a=j,b=document.createElement(\"div\");b.innerHTML=\"<p class='TEST'></p>\";if(!(b.querySelectorAll&&b.querySelectorAll(\".TEST\").length===0)){j=function(b,c,e,g){c=c||document;if(!g&&c.nodeType===9&&!j.isXML(c))try{return n(c.querySelectorAll(b),e)}catch(h){}return a(b,c,e,g)};for(var c in a)j[c]=a[c];b=null}}();(function(){var a=document.createElement(\"div\");a.innerHTML=\"<div class='test e'></div><div class='test'></div>\";if(a.getElementsByClassName&&a.getElementsByClassName(\"e\").length!==0&&(a.lastChild.className=\"e\",a.getElementsByClassName(\"e\").length!==1))i.order.splice(1,0,\"CLASS\"),i.find.CLASS=function(a,c,d){if(typeof c.getElementsByClassName!==\"undefined\"&&!d)return c.getElementsByClassName(a[1])},a=null})();j.contains=document.compareDocumentPosition?function(a,b){return!!(a.compareDocumentPosition(b)&16)}:function(a,b){return a!==b&&(a.contains?a.contains(b):!0)};j.isXML=function(a){return(a=(a?a.ownerDocument||a:0).documentElement)?a.nodeName!==\"HTML\":!1};var A=function(a,b){for(var c=[],d=\"\",f,e=b.nodeType?[b]:b;f=i.match.PSEUDO.exec(a);)d+=f[0],a=a.replace(i.match.PSEUDO,\"\");a=i.relative[a]?a+\"*\":a;f=0;for(var g=e.length;f<g;f++)j(a,e[f],c);return j.filter(d,c)};window.Sizzle=j})();";

  @Override
  public WebElement findElement(final SearchContext context) {
    List<WebElement> elements = findElements(context);
    if (elements.size() > 0) {
      return elements.get(0);
    } else {
      throw new NoSuchElementException("Sizzle: " + selector);
    }
  }
  
  @Override
  public List<WebElement> findElements(final SearchContext context) {
    if (context instanceof JavascriptExecutor) {
      final JavascriptExecutor javascriptContext = (JavascriptExecutor) context;
      injectSizzle(javascriptContext);
      return (List<WebElement>) javascriptContext.executeScript("return Sizzle(arguments[0])", selector);
    }
    
    if (context instanceof WebElement && context instanceof WrapsDriver) {
      final WrapsDriver wrapsdriverContext = (WrapsDriver)context;
      final WebDriver driver = wrapsdriverContext.getWrappedDriver();
      final JavascriptExecutor javascriptContext = (JavascriptExecutor)driver;
      injectSizzle(javascriptContext);
      if (driver instanceof JavascriptExecutor) {
        return (List<WebElement>) javascriptContext.executeScript("return Sizzle(arguments[0], arguments[1])", selector, context);
      }
    }
    throw new UnsupportedOperationException("Can only search using sizzle on JavascriptExecutors, or WebElements which wrap JavascriptExecutors");
  }

  private void injectSizzle(final JavascriptExecutor javascriptContext) {
    try {
      if ((Boolean) javascriptContext.executeScript("return Sizzle()!=null")) {
        // already injected
        return;
      }
    } catch (WebDriverException ex) {
      // ReferenceError: Sizzle is not defined
    }
    javascriptContext.executeScript(sizzleJavascript);
  }

}
