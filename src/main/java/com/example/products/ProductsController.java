package com.example.products;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductsController {
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, Integer> stockColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField quantityField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Product> products;

    public ProductsController() {
        products = FXCollections.observableArrayList();
    }

    @FXML
    protected void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        stockColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        tableView.setItems(products);

        categoryComboBox.getItems().addAll(
                "Buscuits",
                "Drinks",
                "SoftDrinks",
                "HardDrinks",
                "BallPens",
                "Junk Foods",
                "Water",
                "Appliances"
        );
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    nameField.setText(selectedProduct.getName());
                    categoryComboBox.setValue(selectedProduct.getCategory());
                    stockField.setText(String.valueOf(selectedProduct.getStock()));
                    quantityField.setText(String.valueOf(selectedProduct.getQuantity()));
                }
            }
        });
    }

    @FXML
    protected void onAddButtonClick() {
        String name = nameField.getText();
        String category = categoryComboBox.getValue();
        String stockText = stockField.getText();
        String quantityText = quantityField.getText();

        if (name.isEmpty() || category == null || stockText.isEmpty() || quantityText.isEmpty()) {
            showErrorAlert("Please fill in all fields.");
            return;
        }

        try {
            int stock = Integer.parseInt(stockText);
            int quantity = Integer.parseInt(quantityText);

            Product product = new Product(name, category, stock, quantity);
            products.add(product);
            clearFields();
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid stock or quantity value. Please enter valid numbers.");
        }
    }

    @FXML
    protected void onUpdateButtonClick() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String name = nameField.getText();
            String category = categoryComboBox.getValue();
            String stockText = stockField.getText();
            String quantityText = quantityField.getText();

            if (name.isEmpty() || category == null || stockText.isEmpty() || quantityText.isEmpty()) {
                showErrorAlert("Please fill in all fields.");
                return;
            }

            try {
                int stock = Integer.parseInt(stockText);
                int quantity = Integer.parseInt(quantityText);

                selectedProduct.setName(name);
                selectedProduct.setCategory(category);
                selectedProduct.setStock(stock);
                selectedProduct.setQuantity(quantity);
                tableView.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid stock or quantity value. Please enter valid numbers.");
            }
        } else {
            showErrorAlert("No product selected.");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete the selected product?");

            confirmationAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    products.remove(selectedProduct);
                    clearFields();
                }
            });
        } else {
            showErrorAlert("No product selected.");
        }
    }
    private void clearFields() {
        nameField.clear();
        categoryComboBox.setValue(null);
        stockField.clear();
        quantityField.clear();
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
