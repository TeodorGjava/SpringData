package hasEntitiesExample;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @OneToMany(mappedBy = "author", targetEntity = Article.class
            , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Article> articleList;


    public Author() {
        this.articleList = new ArrayList<>();
    }

    public Author(String name) {
        this();
        this.name = name;
    }

    public void addArticle(Article article) {
        articleList.add(article);
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
