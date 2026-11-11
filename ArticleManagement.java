import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArticleManagement {
    private ArticleDAO articleDAO;

    public ArticleManagement(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<Article> getAllArticles() {
        return articleDAO.getArticleList();
    }

    public Article getArticle(int id) {
        Article article = articleDAO.getArticle(id);
        if (article == null)
            throw new IllegalArgumentException("Error: Article not found. (id=" + id + ")");
        return article;
    }

    public void addArticle(Article article) {
        articleDAO.saveArticle(article);
    }

    public void deleteArticle(int id) {
        articleDAO.deleteArticle(id);
    }

    public int count(Predicate<Article> p) {
        return (int) articleDAO.getArticleList().stream().filter(p).count();
    }

    public int countArticles() { return count(a -> a instanceof Article); }
    public int countBooks() { return count(a -> a instanceof Book); }
    public int countDVDs() { return count(a -> a instanceof DVD); }

    public double meanPrice() {
        return articleDAO.getArticleList().stream().mapToDouble(Article::getPrice).average().orElse(0.0);
    }

    public List<Integer> oldestArticleIds() {
        List<Article> all = articleDAO.getArticleList();
        OptionalInt maxAge = all.stream().mapToInt(Article::getAge).max();
        if (!maxAge.isPresent())
            return new ArrayList<>();
        int oldest = maxAge.getAsInt();
        return all.stream().filter(a -> a.getAge() == oldest).map(Article::getId).collect(Collectors.toList());
    }
}
