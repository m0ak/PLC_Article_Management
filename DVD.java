public class DVD extends Article {
    private int length;
    private int ageRating;

    public DVD(int id, String title, int releaseYear, String publisher, double basePrice, int length, int ageRating) {
        super(id, title, releaseYear, publisher, basePrice);
        if (length <= 0)
            throw new IllegalArgumentException(ERR_INVALID_PARAMETER);
        if (ageRating != 0 && ageRating != 6 && ageRating != 12 && ageRating != 16 && ageRating != 18)
            throw new IllegalArgumentException(ERR_INVALID_AGE_RATING);
        this.length = length;
        this.ageRating = ageRating;
    }

    public int getLength() { return length; }

    public int getAgeRating() { return ageRating; }

    @Override
    public double getDiscount() {
        return switch (ageRating) {
            case 0 -> 0.2;
            case 6 -> 0.15;
            case 12 -> 0.1;
            case 16 -> 0.05;
            default -> 0.0;
        };
    }

    public String toString() {
        return super.toString() + "\n" +
                "Length:     " + length + "\n" +
                "Age rating: " + ageRating + "\n";
    }
}
