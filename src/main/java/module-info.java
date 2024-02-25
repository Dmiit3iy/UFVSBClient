module org.dmiit3iy {
    requires static lombok;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires retrofit2;
    requires com.fasterxml.jackson.databind;
    requires okhttp3;
    requires retrofit2.converter.jackson;
    requires java.prefs;

    opens org.dmiit3iy to javafx.fxml;
    opens org.dmiit3iy.сontroller to javafx.fxml;
    exports org.dmiit3iy;
    exports org.dmiit3iy.dto;
    exports org.dmiit3iy.model;
    exports  org.dmiit3iy.retrofit;
    exports org.dmiit3iy.util;

}
