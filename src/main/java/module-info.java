module ch.muehlemann.schiffversenken {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.muehlemann.schiffversenken to javafx.fxml;
    exports ch.muehlemann.schiffversenken;
}