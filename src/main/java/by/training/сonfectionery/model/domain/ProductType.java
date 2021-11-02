package by.training.сonfectionery.model.domain;

public class ProductType extends Entity {
    String type;

    public ProductType() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static class ProductTypeBuilder {
        ProductType productType;

        public ProductTypeBuilder() {
            productType = new ProductType();
        }

        public ProductType.ProductTypeBuilder setId(int id) {
            productType.setId(id);
            return this;
        }

        public ProductType.ProductTypeBuilder setType(String type) {
            productType.setType(type);
            return this;
        }

        public ProductType createProductType() {
            return productType;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        ProductType productType = (ProductType) o;
        return productType.type == null ? type == null : type.equals(productType.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
