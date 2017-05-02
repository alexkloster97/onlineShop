package by.bsuir.shop.domain;

/**
 * Good domain
 */
public class Good {
    private Integer goodId;
    private Integer price;
    private String name;
    private String about;
    private String path;
    private Category category;

    public Good() {
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (about != null ? !about.equals(good.about) : good.about != null) return false;
        if (category != null ? !category.equals(good.category) : good.category != null) return false;
        if (goodId != null ? !goodId.equals(good.goodId) : good.goodId != null) return false;
        if (name != null ? !name.equals(good.name) : good.name != null) return false;
        if (path != null ? !path.equals(good.path) : good.path != null) return false;
        if (price != null ? !price.equals(good.price) : good.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = goodId != null ? goodId.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
