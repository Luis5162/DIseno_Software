module mx.edu.uacm.is.slt.ds.autorefacsystem1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens mx.edu.uacm.is.slt.ds.autorefacsystem1 to javafx.fxml;
    exports mx.edu.uacm.is.slt.ds.autorefacsystem1;
}
