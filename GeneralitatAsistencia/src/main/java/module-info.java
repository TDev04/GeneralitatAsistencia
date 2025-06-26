module org.vinyes.asistencia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.smartcardio;
    requires java.sql;
    requires java.desktop;


    opens org.vinyes.asistencia to javafx.fxml;
    exports org.vinyes.asistencia;
    exports org.vinyes.asistencia.Controllers;
    opens org.vinyes.asistencia.Controllers to javafx.fxml;
}