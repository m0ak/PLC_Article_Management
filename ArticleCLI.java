import java.text.DecimalFormat;

public class ArticleCLI {

    public static void main(String[] args) {
        try {
            run(args);
        } catch (NumberFormatException e) {
            error(Article.ERR_INVALID_PARAMETER);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        } catch (RuntimeException e) {
            error(Article.ERR_INVALID_PARAMETER);
        }
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(1);
    }

    private static void invalid() {
        throw new IllegalArgumentException(Article.ERR_INVALID_PARAMETER);
    }

    private static void run(String[] args) {
        if (args.length < 2)
            invalid();

        ArticleManagement management = new ArticleManagement(new SerializedArticleDAO(args[0]));
        DecimalFormat df = Article.getDecimalFormat();
        String command = args[1];

        switch (command) {
            case "add":
                add(management, args);
                break;

            case "list":
                if (args.length == 2) {
                    management.getAllArticles().forEach(System.out::println);
                } else if (args.length == 3) {
                    System.out.println(management.getArticle(Integer.parseInt(args[2])));
                } else {
                    invalid();
                }
                break;

            case "delete":
                if (args.length != 3)
                    invalid();
                int id = Integer.parseInt(args[2]);
                management.deleteArticle(id);
                System.out.println("Info: Article " + id + " deleted.");
                break;

            case "count":
                if (args.length == 2) {
                    System.out.println(management.countArticles());
                } else if (args.length == 3 && args[2].equals("book")) {
                    System.out.println(management.countBooks());
                } else if (args.length == 3 && args[2].equals("dvd")) {
                    System.out.println(management.countDVDs());
                } else {
                    invalid();
                }
                break;

            case "meanprice":
                if (args.length != 2)
                    invalid();
                System.out.println(df.format(management.meanPrice()));
                break;

            case "oldest":
                if (args.length != 2)
                    invalid();
                management.oldestArticleIds().forEach(i -> System.out.println("Id: " + i));
                break;

            default:
                invalid();
        }
    }

    private static void add(ArticleManagement management, String[] args) {
        if (args.length < 3)
            invalid();
        Article article;
        switch (args[2]) {
            case "book":
                if (args.length != 9)
                    invalid();
                article = new Book(Integer.parseInt(args[3]), args[4], Integer.parseInt(args[6]),
                        args[5], Double.parseDouble(args[7]), Integer.parseInt(args[8]));
                break;
            case "dvd":
                if (args.length != 10)
                    invalid();
                article = new DVD(Integer.parseInt(args[3]), args[4], Integer.parseInt(args[6]), args[5],
                        Double.parseDouble(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]));
                break;
            default:
                invalid();
                return;
        }
        management.addArticle(article);
        System.out.println("Info: Article " + article.getId() + " added.");
    }
}
