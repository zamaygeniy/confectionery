package by.training.сonfectionery.domain;

public class ProductType extends Entity {
    String type;

    public ProductType(){

    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }


    public static class ProductTypeBuilder{
        ProductType productType;

        public ProductTypeBuilder(){
            productType = new ProductType();
        }

        public ProductType.ProductTypeBuilder setId(int id) {
            productType.setId(id);
            return this;
        }

        public ProductType.ProductTypeBuilder setType(String type){
            productType.setType(type);
            return this;
        }

        public ProductType createProductType() {
            return productType;
        }
    }
}
