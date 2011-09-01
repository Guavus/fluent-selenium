/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.*;

import java.util.*;

public final class FluentWebElements extends BaseFluentWebElement implements List<WebElement> {

    private final List<WebElement> currentElements;

    public FluentWebElements(WebDriver delegate, List<WebElement> currentElements, String context) {
        super(delegate, context);
        this.currentElements = currentElements;
    }

    protected WebElement findIt(By by) {
        throw new UnsupportedOperationException("not applicable to this impl");
    }

    @Override
    protected List<WebElement> findThem(By by) {
        throw new UnsupportedOperationException("not applicable to this impl");
    }

    public FluentWebElements click() {
        String ctx = context + ".click()";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                for (WebElement webElement : FluentWebElements.this) {
                    webElement.click();
                }
                return true;
            }
        }, ctx);
        return getFluentWebElements(this, ctx);
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */
    public FluentWebElements clearField() {
        String ctx = context + ".clearField()";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                for (WebElement webElement : FluentWebElements.this) {
                    webElement.clear();
                }
                return true;
            }
        }, ctx);
        return getFluentWebElements(this, ctx);
    }

    public FluentWebElements submit() {
        String ctx = context + ".submit()";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                for (WebElement webElement : FluentWebElements.this) {
                    webElement.submit();
                }
                return true;
            }
        }, ctx);
        return getFluentWebElements(this, ctx);
    }

    // These are as they would be in the WebElement API

    public FluentWebElements sendKeys(final CharSequence... keysToSend) {
        String ctx = context + ".sendKeys(" + charSeqArrayAsHumanString(keysToSend) + ")";
        execute(new Execution<Boolean>() {
            public Boolean execute() {
                for (WebElement webElement : FluentWebElements.this) {
                    webElement.sendKeys(keysToSend);
                }
                return true;
            }
        }, ctx);
        return getFluentWebElements(this, ctx);
    }

    public boolean isSelected() {
        String ctx = context + ".isSelected()";
        boolean areSelected = execute(new Execution<Boolean>() {
            public Boolean execute() {
                boolean areSelected = true;
                for (WebElement webElement : FluentWebElements.this) {
                    areSelected = areSelected & webElement.isSelected();
                }
                return areSelected;
            }
        }, ctx);
        return areSelected;
    }

    public boolean isEnabled() {
        String ctx = context + ".isEnabled()";
        return execute(new Execution<Boolean>() {
            public Boolean execute() {
                boolean areSelected = true;
                for (WebElement webElement : FluentWebElements.this) {
                    areSelected = areSelected & webElement.isEnabled();
                }
                return areSelected;
            }
        }, ctx);
    }

    public boolean isDisplayed() {
        String ctx = context + ".isDisplayed()";
        return execute(new Execution<Boolean>() {
            public Boolean execute() {
                boolean areSelected = true;
                for (WebElement webElement : FluentWebElements.this) {
                    areSelected = areSelected & webElement.isDisplayed();
                }
                return areSelected;
            }
        }, ctx);
    }

    public String getText() {
        String ctx = context + ".getText()";
        return execute(new Execution<String>() {
            public String execute() {
                String text = "";
                for (WebElement webElement : FluentWebElements.this) {
                    text = text + webElement.getText();
                }
                return text;
            }
        }, ctx);
    }

    @Override
    public Point getLocation() {
        throw new UnsupportedOperationException("getLocation() has no meaning for multiple elements");
    }

    @Override
    public String getCssValue(String cssName) {
        throw new UnsupportedOperationException("getCssValue() has no meaning for multiple elements");
    }

    @Override
    public String getAttribute(String attrName) {
        throw new UnsupportedOperationException("getAttribute() has no meaning for multiple elements");
    }

    @Override
    public String getTagName() {
        throw new UnsupportedOperationException("getTagName() has no meaning for multiple elements");
    }

    @Override
    public Dimension getSize() {
        throw new UnsupportedOperationException("getSize() has no meaning for multiple elements");
    }

    public FluentWebElements filter(final FluentMatcher matcher) {
        String ctx = context + ".filter(" + matcher + ")";
        final List<WebElement> subset = execute(new Execution<List<WebElement>>() {
            public List<WebElement> execute() {
                ArrayList<WebElement> results = new ArrayList<WebElement>();
                for (WebElement webElement : FluentWebElements.this) {
                    if (matcher.matches(webElement)) {
                        results.add(webElement);
                    }
                }
                return results;
            }
        }, ctx);
        return getFluentWebElements(subset, ctx);
    }

    public FluentWebElement first(final FluentMatcher matcher) {
        String ctx = context + ".filter(" + matcher + ")";

        WebElement first = execute(new Execution<WebElement>() {
            public WebElement execute() {
                WebElement result = null;
                for (WebElement webElement : FluentWebElements.this) {
                    if (matcher.matches(webElement)) {
                        result = webElement;
                        break;
                    }
                }
                if (result == null) {
                    throw new NothingMatches();
                } else {
                    return result;
                }
            }
        }, ctx);

        return getFluentWebElement(first, context, FluentWebElement.class);
    }


    // From java.util.List

    public void clear() {
        currentElements.clear();
    }

    public int size() {
        return currentElements.size();
    }

    public boolean isEmpty() {
        return currentElements.isEmpty();
    }

    public boolean contains(Object o) {
        return currentElements.contains(o);
    }

    public Iterator<WebElement> iterator() {
        return currentElements.iterator();
    }

    public Object[] toArray() {
        return currentElements.toArray();
    }

    public <WebElement> WebElement[] toArray(WebElement[] ts) {
        return currentElements.toArray(ts);
    }

    public boolean add(WebElement webElement) {
        return currentElements.add(webElement);
    }

    public boolean remove(Object o) {
        return currentElements.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return currentElements.containsAll(objects);
    }

    public boolean addAll(Collection<? extends WebElement> webElements) {
        return currentElements.addAll(webElements);
    }

    public boolean addAll(int i, Collection<? extends WebElement> webElements) {
        return currentElements.addAll(webElements);
    }

    public boolean removeAll(Collection<?> objects) {
        return currentElements.removeAll(objects);
    }

    public boolean retainAll(Collection<?> objects) {
        return currentElements.retainAll(objects);
    }

    public WebElement get(int i) {
        return currentElements.get(i);
    }

    public WebElement set(int i, WebElement webElement) {
        return currentElements.set(i, webElement);
    }

    public void add(int i, WebElement webElement) {
        currentElements.add(i,webElement);
    }

    public WebElement remove(int i) {
        return currentElements.remove(i);
    }

    public int indexOf(Object o) {
        return currentElements.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return currentElements.lastIndexOf(o);
    }

    public ListIterator<WebElement> listIterator() {
        return currentElements.listIterator();
    }

    public ListIterator<WebElement> listIterator(int i) {
        return currentElements.listIterator(i);
    }

    public List<WebElement> subList(int i, int i1) {
        return currentElements.subList(i, i1);
    }

}