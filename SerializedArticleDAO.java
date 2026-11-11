import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializedArticleDAO implements ArticleDAO {
    private String fileName;

    public SerializedArticleDAO(String fileName) {
        this.fileName = fileName;
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error during serialization.");
                System.exit(1);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<Article> load() {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0)
            return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Article>) in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.out.println("Error during deserialization.");
            System.exit(1);
            return null;
        }
    }

    private void store(List<Article> articles) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(articles);
        } catch (IOException e) {
            System.out.println("Error during serialization.");
            System.exit(1);
        }
    }

    @Override
    public List<Article> getArticleList() {
        return load();
    }

    @Override
    public Article getArticle(int id) {
        return load().stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void saveArticle(Article article) {
        if (article == null)
            throw new IllegalArgumentException(Article.ERR_INVALID_PARAMETER);
        List<Article> articles = load();
        if (articles.stream().anyMatch(a -> a.getId() == article.getId()))
            throw new IllegalArgumentException("Error: Article already exists. (id=" + article.getId() + ")");
        articles.add(article);
        store(articles);
    }

    @Override
    public void deleteArticle(int id) {
        List<Article> articles = load();
        boolean removed = articles.removeIf(a -> a.getId() == id);
        if (!removed)
            throw new IllegalArgumentException("Error: Article not found. (id=" + id + ")");
        store(articles);
    }
}
