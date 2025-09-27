module com.example.ql_shopcoffee {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.xerial.sqlitejdbc;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;

    opens com.example.ql_shopcoffee to javafx.fxml;
    exports com.example.ql_shopcoffee;
    exports com.example.ql_shopcoffee.Controllers;
}