module chat.server {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.thomasleberre.chat.client.controller to javafx.fxml;

    exports fr.thomasleberre.chat.client to javafx.graphics;
}