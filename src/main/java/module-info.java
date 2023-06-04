module com.example.products {
    requires javafx.controls;
    requires javafx.fxml;
            
                requires net.synedra.validatorfx;
                requires org.kordamp.bootstrapfx.core;
            
    opens com.example.products to javafx.fxml;
    exports com.example.products;
}