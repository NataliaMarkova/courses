package ua.epamcourses.natalia_markova.homework.problem08.task02.wiki;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by natalia_markova on 12.06.2016.
 */

class Article implements Cloneable {
    private String name;
    private String context;

    public Article(String name) {
        this.name = name;
        this.context = "";
    }

    public Article(String name, String context) {
        this.name = name;
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return name.equals(article.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Article article = new Article(name, context);
        return article;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}

enum Result {
    OK, CANCEL
}

public class WikipediaPrototype {
    private Map<String, Article> articles;
    private Map<String, Article> articlesCopy;

    public WikipediaPrototype() {
        initializeArticles();
    }

    private void initializeArticles() {
        articles = new HashMap<>();
        articlesCopy = new HashMap<>();
    }


    public Article getArticle(String name) throws CloneNotSupportedException {
        Article article = articles.get(name);
        if (article == null) {
            return new Article(name);
        }
        articlesCopy.put(article.getName(), (Article) article.clone());
        return article;
    }

    public void updateArticle(Article article, Result result) {
        if (result == Result.CANCEL) {
            Article originArticle = articlesCopy.get(article.getName());
            if (originArticle != null) {
                articles.put(article.getName(), originArticle);
            }
        } else {
            articles.put(article.getName(), article);
        }
        articlesCopy.remove(article.getName());
    }

    @Override
    public String toString() {
        return "WikipediaPrototype{" +
                "articles=" + articles +
                '}';
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        WikipediaPrototype wikipediaPrototype = new WikipediaPrototype();
        Article article = wikipediaPrototype.getArticle("Java");
        article.setContext("Java programming language");
        wikipediaPrototype.updateArticle(article, Result.OK);
        System.out.println(wikipediaPrototype);
        article = wikipediaPrototype.getArticle("Java");
        article.setContext("New context");
        wikipediaPrototype.updateArticle(article, Result.CANCEL);
        System.out.println(wikipediaPrototype);
        article = wikipediaPrototype.getArticle("Java");
        article.setContext("New context");
        wikipediaPrototype.updateArticle(article, Result.OK);
        System.out.println(wikipediaPrototype);

    }
}
