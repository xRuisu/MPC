module xruisu.project.mpc {

    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;

    exports xruisu.project.mpc;
    exports xruisu.project.mpc.app;
    exports xruisu.project.mpc.data;
    exports xruisu.project.mpc.logic;
    exports xruisu.project.mpc.utility;
    exports xruisu.project.mpc.controllers;

    opens xruisu.project.mpc.controllers to javafx.fxml;
    opens xruisu.project.mpc.app to javafx.fxml;
    opens xruisu.project.mpc to javafx.fxml;
}
